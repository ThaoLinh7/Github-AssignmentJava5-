package edu.poly.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.Account;
import edu.poly.shop.repository.AccountRepository;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CookieService;
import edu.poly.shop.service.SessionService;


@Controller
@RequestMapping("login")
public class LoginController {
	@Autowired
	AccountRepository accountrepository;
	
	@Autowired
	AccountService accountservice;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@GetMapping("index")
	public String index() {
		return "login";
	}
	
	
	@PostMapping("index")
	public String index(Model model, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam(value = "remember", required = false, defaultValue = "false") Boolean remember){
		try {
            Account user = accountrepository.findByName(name);
            if (user != null && user.getPassword().equals(password)) {
                session.set("user", user);
                System.out.println("Admin: "+user.isPosition());
                
                session.set("checkadmin", user.isPosition());
                if(remember) {
                	cookie.add("name", user.getName(), 24);
                }else {
                	cookie.add("name", user.getName(), 0);
                }
                if(user.isPosition() == true) {
                	return "redirect:/admin/homes/home";
                }else {
                	return "redirect:/site/homes/home";
                }
            } else {
                model.addAttribute("message", "Invalid name or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error occurred!");
        }
		return "login";
	}
	
}
