package uutissivusto.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Writer extends AbstractPersistable<Long> {

    
    @Size(min=2, max=30)
    private String name;
    
    @Size(min = 5, max = 20)
    private String password;
    @ManyToMany
    private List<NewsItem> newsItems;

    public void addNewsItem(NewsItem ni) {
        if (newsItems == null) {
            newsItems = new ArrayList();
        }
        if (!newsItems.contains(ni)) {
            newsItems.add(ni);
        }
    }
}
