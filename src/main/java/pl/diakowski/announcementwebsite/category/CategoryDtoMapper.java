package pl.diakowski.announcementwebsite.category;

import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

public class CategoryDtoMapper {
    public static Category map(CategoryDto categoryDto) {
        return new Category(categoryDto.id(), categoryDto.name(), categoryDto.description());
    }

	public static CategoryDto map(Category category) {
		return new CategoryDto(category.getId(), category.getName(), category.getDescription());
	}
}
