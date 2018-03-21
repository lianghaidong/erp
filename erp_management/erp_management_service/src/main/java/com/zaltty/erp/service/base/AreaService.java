package com.zaltty.erp.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zaltty.erp.doamin.base.Area;
import com.zaltty.erp.doamin.base.Standard;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:04:40 <br/>       
 */
public interface AreaService {

	void save(List<Area> list);

	Page<Area> findAll(Pageable pageable);

	List<Area> findByQ(String q);

}
  
