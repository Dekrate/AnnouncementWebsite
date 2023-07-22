package pl.diakowski.announcementwebsite.category;

import jakarta.persistence.EntityNotFoundException;
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

    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return CategoryDtoMapper.map(category);
    }
}
