package pl.diakowski.announcementwebsite.category;

import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDtoMapper::map).toList();
    }
}
