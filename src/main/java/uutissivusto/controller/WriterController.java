package uutissivusto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uutissivusto.domain.Writer;
import uutissivusto.repository.WriterRepository;

@Controller
public class WriterController {
    
    @Autowired
    private WriterRepository writerRepository;
    
    
    @GetMapping("/writers")
    public String listWriters(Model model) {
        model.addAttribute("writers", writerRepository.findAll());
        return "writers";
    }
    
    @PostMapping("/writers")
    public String addWriter(@RequestParam String name) {
        if (!name.trim().isEmpty()) {
            Writer wr = new Writer();
            wr.setName(name);
            writerRepository.save(wr);
        }
        
        return "redirect:/writers";
    }
    
    @GetMapping("/writers/{id}")
    public String getWriter(@PathVariable Long id, Model model) {
        Writer wr = writerRepository.getOne(id);
        if (wr != null) {
            model.addAttribute("writer", wr);
            return "writer";
        }
        return "redirect:/writers";
    }
    
    
}
