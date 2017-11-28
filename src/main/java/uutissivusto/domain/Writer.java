
package uutissivusto.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Writer extends AbstractPersistable<Long> {
    
    private String name;
    @ManyToMany
    private List<NewsItem> newsItems;
}
