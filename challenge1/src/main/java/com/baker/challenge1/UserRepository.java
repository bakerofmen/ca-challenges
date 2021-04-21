package com.baker.challenge1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// inherit everything from PagingAndSortingRepository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    // Spring's reflection magic will build ORM queries from the function names
    Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);
    Page<User> findByAge(Integer age, Pageable pageable);
    Page<User> findByNameLikeIgnoreCaseAndAge(String name, Integer age, Pageable pageable);
}
