package edu.poly.shop.controller.admin;

import java.util.List;
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

import edu.poly.shop.domain.Category;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.CategoryService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}

	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {

		Optional<Category> opt = categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if (opt.isPresent()) {
			Category entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);//cho biết đây là thao tác chỉnh sửa

			model.addAttribute("category", dto);

			return new ModelAndView("admin/categories/addOrEdit", model);
		}
		model.addAttribute("message", "Category is not existed");
		return new ModelAndView("forward:/admin/categories", model);
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		categoryService.deleteById(categoryId);

		model.addAttribute("message", "Category is deleted!");

		return new ModelAndView("forward:/admin/categories/search", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,

			@Valid @ModelAttribute("category") CategoryDto dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}

		Category entity = new Category();
		BeanUtils.copyProperties(dto, entity);

		categoryService.save(entity);
		System.out.printf("Name:" + entity.getName());
		model.addAttribute("message", "Category is saved!");

		return new ModelAndView("redirect:/admin/categories/search", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();

		model.addAttribute("categories", list);
		System.out.println("Categories: " + list); // Log the categories
		return "admin/categories/list";
	}

	@GetMapping("search")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage; //Tạo một Pageableđối tượng thể hiện thông tin phân trang.

        if (StringUtils.hasText(name)) {//kiểm tra xem name có null ko (nếu true sẽ trả về một dối tượng,false sẽ trả về khoảng trắng
            categoryPage = categoryService.findByNameContaining(name, pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("name", name); // to retain the search term in the form

        return "admin/categories/search";
    }

	

}
