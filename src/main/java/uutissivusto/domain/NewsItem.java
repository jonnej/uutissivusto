/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uutissivusto.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author joju
 */
@Entity
@AllArgsConstructor
@Data
public class NewsItem extends AbstractPersistable<Long> {
    
    @Size(min=3, max=100)
    private String headline;
    @Size(min=10, max=220)
    private String lead;
    private LocalDateTime created;
    @Lob
    @Size(min=10)
    private String text;
    private int readCount;
    
    @Lob
    private byte[] image;
    
    @ManyToMany
    private List<Writer> writers;
    
    @ManyToMany
    private List<Category> categories;
    
    
    public NewsItem() {
        this.created = LocalDateTime.now();
        this.readCount = 0;
    }
    
    public void addWriter(Writer wr) {
        if (this.writers == null) {
            this.writers = new ArrayList();
        }
        if (!this.writers.contains(wr)) {
            this.writers.add(wr);
        }
    }
    
    public void addCategory(Category ca) {
        if (this.categories == null) {
            this.categories = new ArrayList();
        }
        if (!this.categories.contains(ca)) {
            this.categories.add(ca);
        }
    }
    
    public boolean isWrittenBy(Long writerId) {
        for (Writer wr : this.writers) {
            if (Objects.equals(wr.getId(), writerId)) {
                return true;
            }
        }
        return false;
    }
   
}
