package uutissivusto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uutissivusto.domain.Category;
import uutissivusto.domain.NewsItem;
import uutissivusto.domain.Writer;
import uutissivusto.repository.CategoryRepository;
import uutissivusto.repository.NewsItemRepository;
import uutissivusto.repository.WriterRepository;

@Controller
public class NewsItemController {

    @Autowired
    private NewsItemRepository newsItemRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/news/{id}")
    public String showNewsItem(Model model, @PathVariable Long id) {
        NewsItem ni = newsItemRepository.getOne(id);
        if (ni == null) {
            return "redirect:/";
        }
        model.addAttribute("newsItem", ni);
        model.addAttribute("categories", categoryRepository.findAll());
        return "newsitem";
    }

    @GetMapping("/news/addnew")
    public String getAddPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("writers", writerRepository.findAll());
        return "addnewsitem";
    }

    @PostMapping("/news")
    public String addNewsItem(Model model, @RequestParam String headline, @RequestParam String lead, @RequestParam Long writerId,
            @RequestParam Long categoryId, @RequestParam String text, @RequestParam("file") MultipartFile file) throws IOException {

        List<String> messages = new ArrayList();
        if (headline.trim().isEmpty() || text.trim().isEmpty() || lead.trim().isEmpty()) {
            messages.add("Headline, lead text and/or text can't be empty");
        }
        if (!messages.isEmpty()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("writers", writerRepository.findAll());
            model.addAttribute("messages", messages);
            return "addnewsitem";
        }

        NewsItem ni = new NewsItem();
        ni.setHeadline(headline);
        ni.setLead(lead);
        ni.setReadCount(0);
        ni.setText(text);
        ni = newsItemRepository.save(ni);
        Writer wr = writerRepository.getOne(writerId);
        ni.addWriter(wr);
        wr.addNewsItem(ni);
        writerRepository.save(wr);

        Category ca = categoryRepository.getOne(categoryId);
        ni.addCategory(ca);
        ca.addNewsItem(ni);
        categoryRepository.save(ca);
        if(file.getContentType().equals("image/gif") || file.getContentType().equals("image/png") || file.getContentType().equals("image/jpg")) {
            ni.setImage(file.getBytes());
        }

        newsItemRepository.save(ni);

        return "redirect:/";
    }

}
