package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.Location;
import org.nomadly.backend.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
