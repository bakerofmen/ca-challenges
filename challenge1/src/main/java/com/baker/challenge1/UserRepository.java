package com.baker.challenge1;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // inherit everything from CrudRepository
    // this creates the REST API for interacting with Employees
}
