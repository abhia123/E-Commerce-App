package com.bikkadit.electronic.store.repositories;

import com.bikkadit.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
