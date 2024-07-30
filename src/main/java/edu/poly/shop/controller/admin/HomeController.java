package edu.poly.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("admin/homes")
public class HomeController {
	@RequestMapping("home")
	public String home() {
		return "admin/homes/home";
	}

}
