package pl.diakowski.announcementwebsite.category;

import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    Category findByName(String name);
}