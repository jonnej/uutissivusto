package uutissivusto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        ni.setReadCount(ni.getReadCount() + 1);
        ni = newsItemRepository.save(ni);
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

    @PostMapping("/news/addnew")
    public String addNewsItem(Model model, @RequestParam String headline, @RequestParam String lead, @RequestParam(required=false) List<Long> writerList,
            @RequestParam(required=false) List<Long> categoryList, @RequestParam String text, @RequestParam("file") MultipartFile file) throws IOException {

        if (session.getAttribute("current") == null) {
            return "redirect:/";
        }
        List<String> messages = new ArrayList();
        if (headline.trim().isEmpty() || text.trim().isEmpty() || lead.trim().isEmpty() || writerList == null || categoryList == null) {
            messages.add("Headline, lead text and/or text can't be empty");
            messages.add("You must choose atleast one writer and one category");
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
        for (Long writerId : writerList) {
            Writer wr = writerRepository.getOne(writerId);
            wr.addNewsItem(ni);
            ni.addWriter(wr);
            writerRepository.save(wr);
        }
        for (Long categoryId : categoryList) {
            Category ca = categoryRepository.getOne(categoryId);
            ni.addCategory(ca);
            ca.addNewsItem(ni);
            categoryRepository.save(ca);
        }
        if (file.getContentType().equals("image/gif") || file.getContentType().equals("image/png") || file.getContentType().equals("image/jpg")) {
            ni.setImage(file.getBytes());
        }

        newsItemRepository.save(ni);

        return "redirect:/";
    }

    @GetMapping(path = "/news/{id}/image", produces = {"image/png", "image/gif", "image/jpg"})
    @ResponseBody
    public byte[] getContent(@PathVariable Long id) {
        return newsItemRepository.getOne(id).getImage();
    }

    @GetMapping("/news/{id}/edit")
    public String editNewsItem(@PathVariable Long id, Model model) {
        NewsItem ni = newsItemRepository.getOne(id);
        Writer wr = writerRepository.getOne((Long) session.getAttribute("current"));
        if (ni == null || !wr.getNewsItems().contains(ni)) {
            return "redirect:/";
        }
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("writers", writerRepository.findAll());
        model.addAttribute("newsitem", ni);
        return "edit";

    }

    @PostMapping("/news/{id}/edit")
    public String editNewsItem(@PathVariable Long id, Model model, @RequestParam String headline, @RequestParam String lead, @RequestParam(required=false) List<Long> writerList,
            @RequestParam(required=false) List<Long> categoryList, @RequestParam String text, @RequestParam("file") MultipartFile file) throws IOException {
        NewsItem ni = newsItemRepository.getOne(id);
        Writer wr = writerRepository.getOne((Long) session.getAttribute("current"));
        if (ni == null || !wr.getNewsItems().contains(ni)) {
            return "redirect:/";
        }

        List<String> messages = new ArrayList();
        if (headline.trim().isEmpty() || text.trim().isEmpty() || lead.trim().isEmpty() || writerList.isEmpty() || categoryList.isEmpty()) {
            messages.add("Headline, lead text and/or text can't be empty");
            messages.add("You must choose atleast one writer and one category");
        }
        if (!messages.isEmpty()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("writers", writerRepository.findAll());
            model.addAttribute("messages", messages);
            return "edit";
        }

        ni.setHeadline(headline);
        ni.setLead(lead);
        ni.setText(text);
        ni = newsItemRepository.save(ni);
        for (Long writerId : writerList) {
            wr = writerRepository.getOne(writerId);
            if (!ni.getWriters().contains(wr)) {
                wr.addNewsItem(ni);
                ni.addWriter(wr);
                writerRepository.save(wr);
            }
        }
        for (Long categoryId : categoryList) {
            Category ca = categoryRepository.getOne(categoryId);
            if (!ni.getCategories().contains(ca)) {
                ni.addCategory(ca);
                ca.addNewsItem(ni);
                categoryRepository.save(ca);
            }
        }
        if (file.getContentType().equals("image/gif") || file.getContentType().equals("image/png") || file.getContentType().equals("image/jpg")) {
            ni.setImage(file.getBytes());
        }

        newsItemRepository.save(ni);

        return "redirect:/news/" + ni.getId();
    }

    @DeleteMapping("/news/{id}/edit")
    @Transactional
    public String deleteNewsItem(@PathVariable Long id) {
        NewsItem ni = newsItemRepository.getOne(id);
        Writer wr = writerRepository.getOne((Long) session.getAttribute("current"));
        if (ni == null || !wr.getNewsItems().contains(ni)) {
            return "redirect:/";
        }
        for (Category ca : ni.getCategories()) {
            ca.getNewsItems().remove(ni);
        }
        for (Writer writer : ni.getWriters()) {
            writer.getNewsItems().remove(ni);
        }
        newsItemRepository.delete(ni);
        return "redirect:/";
    }

}
