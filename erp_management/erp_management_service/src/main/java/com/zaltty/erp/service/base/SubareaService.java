package com.zaltty.erp.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zaltty.erp.doamin.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午6:03:17 <br/>       
 */
public interface SubareaService {

	void save(SubArea model);

	Page<SubArea> findAll(Pageable pageable);

}
  
