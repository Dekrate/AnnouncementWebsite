package pl.diakowski.announcementwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.diakowski.announcementwebsite.admin.AdminService;
import pl.diakowski.announcementwebsite.announcement.AnnouncementRepository;
import pl.diakowski.announcementwebsite.announcement.exception.AnnouncementNotFoundException;
import pl.diakowski.announcementwebsite.category.Category;
import pl.diakowski.announcementwebsite.category.CategoryRepository;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;
import pl.diakowski.announcementwebsite.category.exception.CategoryExistsException;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {
	@Autowired
	private AdminService adminService;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private AnnouncementRepository announcementRepository;


	@Test
	void removeAnnouncementByIdThrowsExceptionWhenAnnouncementDoesNotExist() {
		when(announcementRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(AnnouncementNotFoundException.class, () -> adminService.removeAnnouncementById(1L));
	}

	@Test
	void addCategoryAddsNewCategoryWhenItDoesNotExist() {
		when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);

		adminService.addCategory("name", "description");

		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	@Test
	void addCategoryThrowsExceptionWhenCategoryExists() {
		when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

		assertThrows(CategoryExistsException.class, () -> adminService.addCategory("name", "description"));
	}

	@Test
	void getAllCategoriesReturnsAllCategories() {
		List<Category> categories = List.of(new Category("Category1", "Description1"), new Category("Category2", "Description2"));
		when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDto> result = adminService.getAllCategories();

		assertEquals(2, result.size());
		assertEquals("Category1", result.get(0).name());
		assertEquals("Category2", result.get(1).name());
	}

	@Test
	void updateCategoryThrowsExceptionWhenCategoryDoesNotExist() {
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(CategoryNotFoundException.class, () -> adminService.updateCategory(1L, "New Name", "New Description"));
	}
}