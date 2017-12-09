package uutissivusto.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uutissivusto.domain.Category;
import uutissivusto.domain.Writer;
import uutissivusto.repository.CategoryRepository;
import uutissivusto.repository.NewsItemRepository;
import uutissivusto.repository.WriterRepository;

@Controller
public class DefaultController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WriterRepository writerRepository;
    
    @Autowired
    private NewsItemRepository newsItemRepository;
    
    @Autowired
    private HttpSession session;

    @PostConstruct
    public void init() {
        Category ca1 = new Category();
        ca1.setName("Home");
        Category ca2 = new Category();
        ca2.setName("World");
        Category ca3 = new Category();
        ca3.setName("Sports");
        Category ca4 = new Category();
        ca4.setName("Culture");
        Category ca5 = new Category();
        ca5.setName("Science");
        categoryRepository.save(ca1);
        categoryRepository.save(ca2);
        categoryRepository.save(ca3);
        categoryRepository.save(ca4);
        categoryRepository.save(ca5);
        Writer wr = new Writer();
        wr.setName("Jonne");
        wr.setPassword("jonne");
        writerRepository.save(wr);
        

    }

    @GetMapping("*")
    public String redir() {
        return "redirect:/";
    }
    
    @GetMapping("/")
    public String frontPage(Model model) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "created");
        model.addAttribute("newsItems", newsItemRepository.findAll(pageable));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("writers", writerRepository.findAll());
        return "frontpage";
    }
  
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "login";
    }

}
