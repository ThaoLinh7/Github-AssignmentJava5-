package edu.poly.shop.controller.admin;

import java.util.Optional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.service.CustomerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/customers")
public class CustomerController {
	
	
	@Autowired
	CustomerService customerService;

	@GetMapping("add")
	public String ad(Model model) {
		model.addAttribute("customer", new CustomerDto());
		return "admin/customers/addOrEdit";
	}

	@GetMapping("edit/{customerId}")
	public ModelAndView edit(ModelMap model, @PathVariable("customerId") Long customerId) {

		Optional<Customer> opt = customerService.findById(customerId);
		CustomerDto dto = new CustomerDto();
		if (opt.isPresent()) {
			Customer entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);

			model.addAttribute("customer", dto);

			return new ModelAndView("admin/customers/addOrEdit", model);
		}
		model.addAttribute("message", "customer is not existed");
		return new ModelAndView("forward:/admin/customers", model);
	}
	
	

	@GetMapping("delete/{customerId}")
	public ModelAndView delete(ModelMap model, @PathVariable("customerId") Long customerId) {
		customerService.deleteById(customerId);

		model.addAttribute("message", "customer is deleted!");

		return new ModelAndView("forward:/admin/customers/search", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,

			@Valid @ModelAttribute("customer") CustomerDto dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/customers/addOrEdit");
		}

		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);

		customerService.save(entity);
		System.out.printf("Name:" + entity.getName());
		model.addAttribute("message", "customer is saved!");

		return new ModelAndView("redirect:/admin/customers/search", model);
	}


//
//	@GetMapping("search")
//	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
//
//		List<customer> list = null;
//
//		if (StringUtils.hasText(name)) {
//			list = customerService.findByNameContaining(name);
//		} else {
//			list = customerService.findAll();
//		}
//
//		model.addAttribute("customers", list);
//
//		return "admin/customers/search";
//	}
	 
	 @GetMapping("search")
	    public String search(ModelMap model,
	                         @RequestParam(name = "name", required = false) String name,
	                         @RequestParam(name = "page", defaultValue = "0") int page,
	                         @RequestParam(name = "size", defaultValue = "10") int size) {
	
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Customer> customerPage;

	        if (StringUtils.hasText(name)) {
	            customerPage = customerService.findByNameContaining(name, pageable);
	        } else {
	            customerPage = customerService.findAll(pageable);
	        }

	        model.addAttribute("customerPage", customerPage);
	        model.addAttribute("name", name); // to retain the search term in the form

	        return "admin/customers/search";
	    }



}
