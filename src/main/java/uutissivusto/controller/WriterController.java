package uutissivusto.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uutissivusto.domain.Writer;
import uutissivusto.repository.CategoryRepository;
import uutissivusto.repository.WriterRepository;

@Controller
public class WriterController {

    @Autowired
    private WriterRepository writerRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HttpSession session;


    @GetMapping("/writers")
    public String listWriters(Model model) {
        model.addAttribute("writers", writerRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "writers";
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
      session.setAttribute("current", writer);
      return "redirect:/";
    }

    @PostMapping("/writers")
    public String addWriter(@RequestParam String name, @RequestParam String password, Model model) {
        List<String> messages = new ArrayList();
        if (name.length() > 30 || name.trim().length() < 2) {
          messages.add("Name needs to contain atleast 2 characters excluding whitespace and maximum length is 30 characters including whitespace");
        }
        if (password.length() < 5 || password.length() > 20) {
          messages.add("Password length should be between 5 and 20");
        }
        if (messages.isEmpty()) {
            Writer writer = new Writer();
            writer.setName(name);
            writer = writerRepository.save(writer);
            return "redirect:/writers/" + writer.getId();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("categories", categoryRepository.findAll());
        return "login";
    }

    @GetMapping("/writers/{id}")
    public String getWriter(@PathVariable Long id, Model model) {
        Writer wr = writerRepository.getOne(id);
        if (wr != null) {
            model.addAttribute("writer", wr);
            model.addAttribute("categories", categoryRepository.findAll());
            return "writer";
        }
        return "redirect:/writers";
    }


}
