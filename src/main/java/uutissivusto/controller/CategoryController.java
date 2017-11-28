package uutissivusto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uutissivusto.domain.Category;
import uutissivusto.repository.CategoryRepository;

@Controller
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @GetMapping("/categories/{id}")
    public String showCategory(@PathVariable Long id, Model model) {
        Category ca = categoryRepository.getOne(id);
        if (ca != null) {
            model.addAttribute("category", ca);
            return "category";
        }
        return "redirect:/";
    }
    
    @PostMapping("/categories")
    public String addCategory(@RequestParam String name) {
        if (!name.trim().isEmpty()) {
            Category ca = new Category();
            ca.setName(name);
            categoryRepository.save(ca);
        }
        return "redirect:/";
    }
}
