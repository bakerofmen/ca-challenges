package com.baker.challenge1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    // inherit everything from PagingAndSortingRepository

    // Spring's reflection magic will build ORM queries from the function names
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findByAge(Integer age, Pageable pageable);
    Page<User> findByNameContainingIgnoreCaseAndAge(String name, Integer age, Pageable pageable);
}
