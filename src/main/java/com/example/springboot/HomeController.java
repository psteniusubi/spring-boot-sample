package com.example.springboot;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RolesAllowed("ROLE_USER")
@Controller
public class HomeController {
	@GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("principal", principal);
		return "home";
	}
}
