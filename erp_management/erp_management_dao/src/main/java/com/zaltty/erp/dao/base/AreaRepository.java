package com.zaltty.erp.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zaltty.erp.doamin.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:02:34 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area, Long>{

	@Query("from Area where province like ?1 or city like ?1 or postcode like ?1 or district like ?1 or citycode like ?1 or shortcode like ?1")
	List<Area> findQ(String q);

}
  
