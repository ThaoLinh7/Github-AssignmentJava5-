package edu.poly.shop.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.shop.domain.Account;
import edu.poly.shop.service.AccountService;
import jakarta.validation.Valid;

@RequestMapping("register")
@Controller
public class RegisterController {
	@Autowired
	AccountService accountservice;
	
	@GetMapping("index")
	public String index(Model model) {
		model.addAttribute("account", new Account());
		return "register";
	}
	
	@PostMapping("index")
	public String index(@Valid @ModelAttribute("account") Account item, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                    @RequestParam("password") String pw, @RequestParam("repassword") String rpw) {

		System.out.println("Repassword: "+rpw);
	    if (result.hasErrors()) {
	        return "register";
	    }

	    if (rpw == null || rpw.isEmpty()) {
	        model.addAttribute("errorrepassword", "Please enter the repassword!");
	        return "register";
	    }

	    if (!rpw.equals(pw)) {
	        model.addAttribute("errorrepassword", "Passwords do not match!");
	        return "login/register";
	    }

	    accountservice.save(item);
	    redirectAttributes.addFlashAttribute("successMessage", "Registration Success!");
	    return "redirect:/login/index";
	}

	
}

