package com.baker.challenge1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Unlike UserController which is a RestController, this one serves the frontend
@Controller
public class FrontendController {

    // The index goes to "/", intuitively
    @RequestMapping(value = "/")
    public String index() {
        // resolves to src/main/resources/templates/index.html
        return "index";
    }

}