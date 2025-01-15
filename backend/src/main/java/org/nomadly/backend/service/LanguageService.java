package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.Language;
import org.nomadly.backend.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    public void addLanguage(Language language) {
        languageRepository.save(language);
    }

    public void updateLanguageById(Long id, Language newLanguage) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Language Found!"));

        assert language != null;

        language.setLanguageNameEnglish(newLanguage.getLanguageNameEnglish());
        language.setLanguageNameNative(newLanguage.getLanguageNameNative());
        language.setCca2(newLanguage.getCca2());

        languageRepository.save(language);
    }

    public void deleteLanguage(Long id) {
        languageRepository.deleteById(id);
    }
}
