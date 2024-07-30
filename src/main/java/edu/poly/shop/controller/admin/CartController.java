package edu.poly.shop.controller.admin;


import edu.poly.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addOrEdit/{productId}")
    public String addToCart(@PathVariable Long productId, Model model) {
        cartService.addProduct(productId, 1); // Add one item to the cart
        return "redirect:/cart"; // Redirect to cart
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("total", cartService.getTotal());
        return "site/auth/view"; // Create a view for displaying cart items
    }

    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId, Model model) {
        cartService.removeCart(cartItemId);
        return "redirect:/cart"; // Redirect to cart page
    }
}

