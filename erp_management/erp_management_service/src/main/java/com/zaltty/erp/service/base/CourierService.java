package com.zaltty.erp.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.zaltty.erp.doamin.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:07:49 <br/>       
 */
public interface CourierService {

	void save(Courier courier);

	Page<Courier> findAll(Pageable pageable);

	void babatchDel(String ids);

	Page<Courier> findAll(Specification<Courier> specification,
			Pageable pageable);

}
  
