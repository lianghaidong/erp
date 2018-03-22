package com.zaltty.erp.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zaltty.erp.doamin.base.FixedArea;

/**  
 * ClassName:fixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:41:37 <br/>       
 */
public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> findAll(Pageable pageable);

	void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId);

}
  
