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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.service.AccountService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/addOrEdit";
	}

	@GetMapping("edit/{name}")
	public ModelAndView edit(ModelMap model, @PathVariable("name") String name) {

		Optional<Account> opt = accountService.findById(name);
		AccountDto dto = new AccountDto();
		if (opt.isPresent()) {
			Account entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);

			model.addAttribute("account", dto);

			return new ModelAndView("admin/accounts/addOrEdit", model);
		}
		model.addAttribute("message", "account is not existed");
		return new ModelAndView("forward:/admin/accounts", model);
	}

	@GetMapping("delete/{name}")
	public ModelAndView delete(ModelMap model, @PathVariable("name") String name) {
		accountService.deleteByname(name);

		model.addAttribute("message", "account is deleted!");

		return new ModelAndView("forward:/admin/accounts/search", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,

			@Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
		}

		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);

		accountService.save(entity);
		System.out.printf("name:" + entity.getName());
		model.addAttribute("message", "account is saved!");

		return new ModelAndView("redirect:/admin/accounts/search", model);
	}
	 
	 @GetMapping("search")
	    public String search(ModelMap model,
	                         @RequestParam( name= "name", required = false) String name,
	                         @RequestParam(name = "page", defaultValue = "0") int page,
	                         @RequestParam(name = "size", defaultValue = "10") int size) {
	
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Account> accountPage;

	        if (StringUtils.hasText(name)) {
	            accountPage = accountService.findByNameContaining(name, pageable);
	        } else {
	            accountPage = accountService.findAll(pageable);
	        }

	        model.addAttribute("accountPage", accountPage);
	        model.addAttribute("name", name); // to retain the search term in the form

	        return "admin/accounts/search";
	    }

}
