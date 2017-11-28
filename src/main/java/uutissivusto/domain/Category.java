/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uutissivusto.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author joju
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category extends AbstractPersistable<Long> {
    
    private String name;
    
    @ManyToMany
    private List<NewsItem> newsItems;
    
    public void addNewsItem(NewsItem ni) {
        if (this.newsItems == null) {
            this.newsItems = new ArrayList();
        }
        if (!this.newsItems.contains(ni)) {
            this.newsItems.add(ni);
        }
    }
}
