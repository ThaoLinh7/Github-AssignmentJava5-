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

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/products")
public class ProductController2 {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService; // Assuming you have a service for categories

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/addOrEdit";
    }
    @GetMapping("delete/{productId}")
    public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) {
    	productService.deleteById(productId);

    	model.addAttribute("message", "Product is deleted!");

    	return new ModelAndView("forward:/admin/products/search", model);
    }

    @GetMapping("edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
        Optional<Product> opt = productService.findById(productId);
        ProductDto dto = new ProductDto();
        if (opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);

            if (entity.getCategory() != null) {
                dto.setCategoryId(entity.getCategory().getCategoryId());
            } else {
                dto.setCategoryId(null); // Handle as appropriate for your application
            }

            model.addAttribute("product", dto);
            model.addAttribute("categories", categoryService.findAll());
            return new ModelAndView("admin/products/addOrEdit", model);
        }
        model.addAttribute("message", "Product does not exist");
        return new ModelAndView("forward:/admin/products", model);
    }


    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return new ModelAndView("admin/products/addOrEdit");
        }

        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getCategoryId() != null) {
            Optional<Category> categoryOpt = categoryService.findById(dto.getCategoryId());
            if (categoryOpt.isPresent()) {
                entity.setCategory(categoryOpt.get());
            } else {
                result.rejectValue("categoryId", "error.product", "Invalid category ID");
                model.addAttribute("categories", categoryService.findAll());
                return new ModelAndView("admin/products/addOrEdit");
            }
        } else {
            result.rejectValue("categoryId", "error.product", "Category is required");
            model.addAttribute("categories", categoryService.findAll());
            return new ModelAndView("admin/products/addOrEdit");
        }

        productService.save(entity);
        model.addAttribute("message", "Product is saved!");
        return new ModelAndView("redirect:/admin/products/search", model);
    }


    @GetMapping("search")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;
        if (StringUtils.hasText(name)) {
            productPage = productService.findByNameContaining(name, pageable);
        } else {
            productPage = productService.findAll(pageable);
        }
        model.addAttribute("productPage", productPage);
        model.addAttribute("name", name); // to retain the search term in the form
        return "admin/products/search";
    }
}
