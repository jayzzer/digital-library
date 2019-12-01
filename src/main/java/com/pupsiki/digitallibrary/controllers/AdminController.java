package com.pupsiki.digitallibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Hello, admin!</h1>");
    }
}
