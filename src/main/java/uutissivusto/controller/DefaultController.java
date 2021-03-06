package uutissivusto.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uutissivusto.domain.Category;
import uutissivusto.domain.NewsItem;
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
    @Transactional
    public void init() throws IOException {
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
        ca3 = categoryRepository.save(ca3);
        categoryRepository.save(ca4);
        categoryRepository.save(ca5);
        NewsItem ni = new NewsItem();
        ni.addCategory(ca3);
        ni.setHeadline("FantasyPremier, almost halfway");
        ni.setLead("Fantasy Premier League is close to halfway of the season. Many players still got chance to shine and win overall competition.");
        ni.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ligula dui, condimentum vel ex vitae, consequat scelerisque sapien. Curabitur odio enim, facilisis vel hendrerit eget, accumsan eu augue. Nullam dictum felis vel ante rhoncus sagittis. Vivamus luctus varius sapien. Curabitur fermentum diam luctus, iaculis quam eu, pulvinar neque. Donec fermentum posuere tempor. Ut eu urna at metus cursus venenatis. Vestibulum molestie eros orci, pharetra egestas felis sollicitudin eget. Suspendisse vel est rhoncus sapien cursus egestas quis consectetur justo. Quisque maximus id ligula non lacinia. In venenatis tincidunt consequat. Aenean at porta sapien, vitae dictum diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae");
//        ni.setImage(getClass().getClassLoader().getResource("/resources/images/fantasy.png").getFile().getBytes());
        ni = newsItemRepository.save(ni);

        Writer wr = new Writer();
        wr.setName("Jonne");
        wr.setPassword("jonne");
        wr.addNewsItem(ni);
        ni.addWriter(wr);
        writerRepository.save(wr);
        Writer wr1 = new Writer();
        wr1.setName("Krist");
        wr1.setPassword("Krist");
        writerRepository.save(wr1);
        newsItemRepository.save(ni);

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
        model.addAttribute("ordertype", "5 Most recent news");
        return "frontpage";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (session.getAttribute("current") != null) {
            return "redirect:/";
        }
        model.addAttribute("categories", categoryRepository.findAll());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String name, @RequestParam String password, Model model) {
        Writer writer = writerRepository.findByNameAndPassword(name, password);
        if (writer == null) {
            List<String> messages = new ArrayList();
            messages.add("Name or password wrong");
            model.addAttribute("messages", messages);
            return "login";
        }
        session.setAttribute("current", writer.getId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("current");
        return "redirect:/login";
    }

}
