package com.zaltty.erp.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zaltty.erp.doamin.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:12:42 <br/>       
 */
public interface StandardService {

	void save(Standard standard);

	Page<Standard> findAll(Pageable pageable);
	

}
  
