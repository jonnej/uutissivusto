/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uutissivusto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uutissivusto.domain.NewsItem;

/**
 *
 * @author joju
 */
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    
    List<NewsItem> findAllByOrderByCreatedDesc();
    List<NewsItem> findAllByOrderByReadCountDesc();
    
}
