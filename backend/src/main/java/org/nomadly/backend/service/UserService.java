package org.nomadly.backend.service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.nomadly.backend.model.ChangePasswordLink;
import org.nomadly.backend.model.Location;
import org.nomadly.backend.model.PhotosClasses.UserProfilePhoto;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.ChangePasswordLinkRepository;
import org.nomadly.backend.repository.UserProfilePhotoRepository;
import org.nomadly.backend.repository.UserRepository;
import org.nomadly.backend.s3.S3Buckets;
import org.nomadly.backend.s3.S3Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final MailService mailService;
    private final QuestionPostService questionPostService;
    private final PasswordEncoder passwordEncoder;
    private final ChangePasswordLinkService changePasswordLinkService;
    private final UserProfilePhotoRepository userProfilePhotoRepository;
    private final ChangePasswordLinkRepository changePasswordLinkRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No User Found!"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("No User Found!"));
    }

    public boolean isUserFriend(Long idSecondUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) auth.getPrincipal();
        User secondUser = getUserById(idSecondUser);

        return secondUser.getFriends().stream().anyMatch(e -> Objects.equals(e.getId(), currentUser.getId()));
    }

    public byte[] getUserProfilePhoto(Long userId) {
        User user = getUserById(userId);

        if (user.getProfilePhoto() == null) {
            return null;
        }

        return s3Service.getObject(s3Buckets.getCustomer(), "%s/%s_Profile_Image".formatted(userId, userId));
    }

    public List<Location> getCurrentUserLocationPreferences() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        return user.getVisitedLocations();
    }

    public List<Location> getOtherUserLocationPreferences() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        return user.isVisitedLocationsVisibility() ? user.getVisitedLocations() : null;
    }

    public void updateUserVisits(Location location, boolean command) {
        //  As for the command boolean: "true" is for adding and "false" for removing

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (command) {
            user.getVisitedLocations().add(location);
        } else {
            user.getVisitedLocations().remove(location);
        }

        userRepository.save(user);
    }

    public void forgottenPassword(String email) {
        try {
            mailService.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new IllegalStateException("An Unexpected Error Has Occurred!");
        }
    }

    public void setPassword(String email, String newPassword, String uuid) {
        User user = getUserByEmail(email);
        ChangePasswordLink changePasswordLink = changePasswordLinkRepository.findByUuid(uuid);

        if (changePasswordLink.getEmail().equals(email) && !changePasswordLinkService.isExpiredByTime(uuid) && !changePasswordLink.isExpired()) {
            changePasswordLink.setExpired(true);
            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);
            changePasswordLinkRepository.save(changePasswordLink);

        } else {
            throw new IllegalStateException("Error! Request a 'New Password Change Request' by Email and Try again!");
        }
    }

    public void friendReqOrUnfriend(Long idSecondUser, Long command) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) auth.getPrincipal();
        User secondUser = getUserById(idSecondUser);

        if (command == 1L) {
            if (!isUserFriend(idSecondUser) && !currentUser.getId().equals(idSecondUser)) {
                currentUser.getFriends().add(secondUser);
                secondUser.getFriends().add(currentUser);
            } else {
                throw new IllegalStateException("User is already subscribed or trying to subscribe to themselves!");
            }
        } else if (command == 0L) {
            if (isUserFriend(idSecondUser) && !currentUser.getId().equals(idSecondUser)) {
                currentUser.getFriends().remove(secondUser);
                secondUser.getFriends().remove(currentUser);
            } else {
                throw new IllegalStateException("User is not subscribed or trying to unsubscribe from themselves!");
            }
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }

        userRepository.save(currentUser);
        userRepository.save(secondUser);
    }

    public void updateUserById(User updatedUser) {
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user data cannot be null");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        if (updatedUser.getName().isBlank() || updatedUser.getPhoneNumber().isBlank() || updatedUser.getBornIn().getId() == null) {
            throw new IllegalArgumentException("All Fields Should Be Completed!");
        }
        user.setName(updatedUser.getName());
        user.setCurrentlyIn(updatedUser.getCurrentlyIn());
        user.setBornIn(updatedUser.getBornIn());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setShortAutoDescription(updatedUser.getShortAutoDescription());
        userRepository.save(user);
    }

    public void updateUserProfilePhoto(MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        UserProfilePhoto userProfilePhoto = new UserProfilePhoto();

        if (user.getProfilePhoto() == null) {
            userProfilePhoto.setUser(user);
            userProfilePhoto.setBucket(s3Buckets.getCustomer());
            userProfilePhoto.setKey("%s/%s_Profile_Image".formatted(user.getId(), user.getId()));

            user.setProfilePhoto(userProfilePhoto);

            userProfilePhotoRepository.save(userProfilePhoto);
            userRepository.save(user);
        }

        s3Service.putObject(s3Buckets.getCustomer(), "%s/%s_Profile_Image".formatted(user.getId(), user.getId()), file.getBytes());
    }

    public void deleteUserProfilePhoto() throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (user.getProfilePhoto() == null) {
            return;
        } else {
            user.setProfilePhoto(null);
            s3Service.deleteAnObject(s3Buckets.getCustomer(), "%s/%s_Profile_Image".formatted(user.getId(), user.getId()));
        }

        userRepository.save(user);
    }

    public void changePasswordAndVerifyOldPassword(Long userId, String newPassword, String actualPassword) {
        User user = getUserById(userId);

        if (passwordEncoder.matches(actualPassword, user.getPassword()) && !passwordEncoder.matches(newPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else if (!passwordEncoder.matches(actualPassword, user.getPassword())) {
            throw new IllegalStateException("Fill The `Current Password` Input Correctly By Typing Your Actual Password!");
        } else if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalStateException("The New Password Shouldn't Be The Same As The Actual One!");
        } else {
            throw new IllegalStateException("An Error Has Occurred! Refresh The Page And Try Again");
        }
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
