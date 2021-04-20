package com.baker.challenge1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component  // this annotation automagically binds us to the SpringBootApplication
public class DatabaseLoader implements CommandLineRunner {

    // some constants for generating test data
    private static final String FIRSTNAME_FILEPATH = "classpath:config/firstnames.txt";
    private static final String LASTNAME_FILEPATH = "classpath:config/lastnames.txt";
    private static final String STREET_NAME_FILEPATH = "classpath:config/streetnames.txt";
    private static final String STREET_TYPE_FILEPATH = "classpath:config/streettypes.txt";
    private static final String CITY_NAME_FILEPATH = "classpath:config/citynames.txt";
    private static final String STATE_NAME_FILEPATH = "classpath:config/statenames.txt";
    private static final int MAX_AGE = 90;
    private static final int MIN_AGE = 18;
    private static final int MIN_ADDRESS_NUMBER = 1000;
    private static final int MAX_ADDRESS_NUMBER = 99999;
    private static final int MIN_ZIP_CODE = 10000;
    private static final int MAX_ZIP_CODE = 99999;

    // load files with names
    private static final List<String> firstNames;
    private static final List<String> lastNames;
    private static final List<String> streetNames;
    private static final List<String> streetTypes;
    private static final List<String> cityNames;
    private static final List<String> stateNames;
    private static final Random random = new Random();

    static {
        // a little temporary function to get all the nonempty lines from a file
        Function<String, List<String>> getNonemptyLines = (String filename) -> {
            try {
                return Files.lines(ResourceUtils.getFile(filename).toPath())
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                // we definitely can't generate fake data without our files
                throw new ExceptionInInitializerError(e);
            }
        };

        // store up all the file contents for later
        firstNames = getNonemptyLines.apply(FIRSTNAME_FILEPATH);
        lastNames = getNonemptyLines.apply(LASTNAME_FILEPATH);
        streetNames = getNonemptyLines.apply(STREET_NAME_FILEPATH);
        streetTypes = getNonemptyLines.apply(STREET_TYPE_FILEPATH);;
        cityNames = getNonemptyLines.apply(CITY_NAME_FILEPATH);;
        stateNames = getNonemptyLines.apply(STATE_NAME_FILEPATH);;
    }

    private final UserRepository repository;

    @Autowired
    public DatabaseLoader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        // go over every combination of name. This will be exactly one million names
        firstNames.forEach((fname) -> {
            lastNames.forEach((lname) -> {
                String name = String.format("%s %s", fname, lname.charAt(0) + lname.toLowerCase().substring(1));
                int randomAge = random.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE;
                this.repository.save(new User(name, randomAge, randomAddress1(), randomAddress2()));
            });
        });
    }

    private static String randomAddress1() {
        // number streetname streettype
        return String.format("%d %s %s",
                random.nextInt(MAX_ADDRESS_NUMBER - MIN_ADDRESS_NUMBER) + MIN_ADDRESS_NUMBER,
                streetNames.get(random.nextInt(streetNames.size())),
                streetTypes.get(random.nextInt(streetTypes.size()))
        );
    }

    private static String randomAddress2() {
        // city, state, zip
        return String.format("%s, %s, %d",
                cityNames.get(random.nextInt(cityNames.size())),
                stateNames.get(random.nextInt(stateNames.size())),
                random.nextInt(MAX_ZIP_CODE - MIN_ZIP_CODE) + MIN_ZIP_CODE
        );
    }
}
