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
import edu.poly.shop.domain.Order;
import edu.poly.shop.model.OrderDto;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("order", new OrderDto());
        model.addAttribute("customers", customerService.findAll());
        return "admin/orders/addOrEdit";
    }
    
    @GetMapping("delete/{orderId}")
    public ModelAndView delete(ModelMap model, @PathVariable("orderId") Long orderId) {
        orderService.deleteById(orderId);
        model.addAttribute("message", "Order is deleted!");
        return new ModelAndView("forward:/admin/orders/search", model);
    }

    @GetMapping("edit/{orderId}")
    public ModelAndView edit(ModelMap model, @PathVariable("orderId") Long orderId) {
        Optional<Order> opt = orderService.findById(orderId);
        OrderDto dto = new OrderDto();
        if (opt.isPresent()) {
            Order entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);

            if (entity.getCustomer() != null) {
                dto.setCustomerId(entity.getCustomer().getCustomerId());
            } else {
                dto.setCustomerId((Long) null); // Handle as appropriate for your application
            }

            model.addAttribute("order", dto);
            model.addAttribute("customers", customerService.findAll());
            return new ModelAndView("admin/orders/addOrEdit", model);
        }
        model.addAttribute("message", "Order does not exist");
        return new ModelAndView("forward:/admin/orders", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("order") OrderDto dto, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            return new ModelAndView("admin/orders/addOrEdit");
        }

        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getCustomerId() != null) {
            Optional<Customer> customerOpt = customerService.findById((long) dto.getCustomerId());
            if (customerOpt.isPresent()) {
                entity.setCustomer(customerOpt.get());
            } else {
                result.rejectValue("customerId", "error.order", "Invalid customer ID");
                model.addAttribute("customers", customerService.findAll());
                return new ModelAndView("admin/orders/addOrEdit");
            }
        } else {
            result.rejectValue("customerId", "error.order", "Customer is required");
            model.addAttribute("customers", customerService.findAll());
            return new ModelAndView("admin/orders/addOrEdit");
        }

        orderService.save(entity);
        model.addAttribute("message", "Order is saved!");
        return new ModelAndView("redirect:/admin/orders/search", model);
    }

    @GetMapping("search")
    public String search(ModelMap model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage;

            orderPage = orderService.findAll(pageable);

        model.addAttribute("orderPage", orderPage);
        return "admin/orders/search";
    }
}
