package org.nomadly.backend.initDB;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.Category;
import org.nomadly.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InitDbCategories {

    private final CategoryRepository categoryRepository;

    public void seedDBCategory() {
        List<Category> categories = List.of(
                Category.builder().nameOfCategory("restaurants_and_bars").build(),
                Category.builder().nameOfCategory("historical_sites").build(),
                Category.builder().nameOfCategory("tourist_attractions").build(),
                Category.builder().nameOfCategory("adventure_and_outdoors").build(),
                Category.builder().nameOfCategory("culture_and_art").build(),
                Category.builder().nameOfCategory("beaches_and_islands").build(),
                Category.builder().nameOfCategory("shopping_and_markets").build(),
                Category.builder().nameOfCategory("nightlife").build(),
                Category.builder().nameOfCategory("local_cuisine").build(),
                Category.builder().nameOfCategory("accommodation").build(),
                Category.builder().nameOfCategory("social_media").build()
        );

        categoryRepository.saveAllAndFlush(categories);
    }
}
