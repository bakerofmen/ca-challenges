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
                                @RequestParam(value = "lastname", defaultValue = "") String lastname,
                                Pageable pageable) {

        // This regex ensures that the string is only match in the last name
        // Assuming there is at most one space in a name
        String lnamePattern = "% %" + lastname + "%";
        logger.info(String.format("findUsers(age=<%s>, lastname=<%s>, pattern=<%s>)", age, lastname, lnamePattern));

        // handle non-numeric and negative age
        try {
            if (Integer.parseInt(age) < 0) {
                age = "";
            }
        } catch (Throwable t) {
            age = "";
        }

        // demux by optional arguments to the appropriate query
        if (age.isEmpty() && lastname.isEmpty()) {
            return userRepo.findAll(pageable);
        } else if (age.isEmpty()) {
            return userRepo.findByNameLikeIgnoreCase(lnamePattern, pageable);
        } else if (lastname.isEmpty()) {
            return userRepo.findByAge(Integer.parseInt(age), pageable);
        } else {
            return userRepo.findByNameLikeIgnoreCaseAndAge(lnamePattern, Integer.parseInt(age), pageable);
        }
    }
}