package uutissivusto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/")
    public String frontPage(Model model) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "created");
        model.addAttribute("newsItems", newsItemRepository.findAll(pageable));
        return "frontpage";
    }

    @GetMapping("/news/{id}")
    public String showNewsItem(Model model, @PathVariable Long id) {
        NewsItem ni = newsItemRepository.getOne(id);
        if (ni != null) {
            model.addAttribute("newsItem", ni);
            return "newsitem";
        }
        return "redirect:/";
    }

    @PostMapping("/news")
    public String addNewsItem(@RequestParam String headline, @RequestParam String lead, @RequestParam(value = "writersId[]") Long[] writers,
            @RequestParam(value = "categoriesId[]") Long[] categoriesId, @RequestParam String text) {
        
        if (!headline.trim().isEmpty() && !text.trim().isEmpty() && !lead.trim().isEmpty()) {
            NewsItem ni = new NewsItem();
            ni.setHeadline(headline);
            ni.setLead(lead);
            ni.setReadCount(0);
            ni.setText(text);
            for (Long id : writers) {
                ni.addWriter(writerRepository.getOne(id));
            }
            for (Long id : categoriesId) {
                ni.addCategory(categoryRepository.getOne(id));
            }
            newsItemRepository.save(ni);
        }
        return "redirect:/";
    }
    
    
}
