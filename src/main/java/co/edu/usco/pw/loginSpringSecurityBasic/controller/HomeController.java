package co.edu.usco.pw.loginSpringSecurityBasic.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/loginForm")
	public String login(){
		return "loginForm";
	}

	@PostMapping("/loginForm")
	public String loginPost(){
		return "loginForm";
	}

	@GetMapping("/home")
	public String home(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		return "home";
	}

	@GetMapping("/user")
	public String user(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		return "user";
	}

	@GetMapping("/admin")
	public String admin(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		return "admin";
	}
}

