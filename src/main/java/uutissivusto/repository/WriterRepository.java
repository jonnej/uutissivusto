/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uutissivusto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uutissivusto.domain.Writer;

/**
 *
 * @author joju
 */
public interface WriterRepository extends JpaRepository<Writer, Long>{
  Writer findByNameAndPassword(String name, String password);

}
