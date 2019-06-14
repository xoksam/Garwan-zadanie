package oksa.marek.eshop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @GetMapping("/api/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
