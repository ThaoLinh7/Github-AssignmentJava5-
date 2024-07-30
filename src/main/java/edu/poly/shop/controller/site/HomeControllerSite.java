package edu.poly.shop.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.service.ProductService;

@Controller
@RequestMapping("site/homes")
public class HomeControllerSite {

	
	  @Autowired
	    private ProductService productService;

	    @RequestMapping("home")
	    public String home(Model model) {
	        model.addAttribute("products", productService.findAll());
	        return "site/homes/home";
	    }
}
