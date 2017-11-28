/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uutissivusto.domain;

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
public class NewsItem extends AbstractPersistable<Long> {
    
    private String headline;
    @ManyToMany
    private List<Writer> writers;
    @ManyToMany
    private List<Category> categories;
    private Long readCount;
   
}
