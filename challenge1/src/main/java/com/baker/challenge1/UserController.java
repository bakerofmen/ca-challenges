package com.baker.challenge1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    // Spring's magic will instantiate this properly
    @Autowired
    private UserRepository userRepo;

    // Get a logger so we can debug
    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    // The one true function for getting User data from the database
    @GetMapping
    public Page<User> findUsers(@RequestParam(value = "age", defaultValue = "") String age,
                                @RequestParam(value = "name", defaultValue = "") String name,
                                Pageable pageable) {

        logger.info(String.format("findUsers(age=<%s>, name=<%s>)", age, name));

        // demux by optional arguments to the appropriate query
        if (age.isEmpty() && name.isEmpty()) {
            return userRepo.findAll(pageable);
        } else if (age.isEmpty()) {
            return userRepo.findByNameContainingIgnoreCase(name, pageable);
        } else if (name.isEmpty()) {
            return userRepo.findByAge(Integer.parseInt(age), pageable);
        } else {
            return userRepo.findByNameContainingIgnoreCaseAndAge(name, Integer.parseInt(age), pageable);
        }
    }
}