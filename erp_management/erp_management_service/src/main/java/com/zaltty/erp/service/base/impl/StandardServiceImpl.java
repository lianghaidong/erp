package com.zaltty.erp.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.StandardRepository;
import com.zaltty.erp.doamin.base.Standard;
import com.zaltty.erp.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:14:41 <br/>       
 */
@Transactional
@Service  //spring注解 业务层代码
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardRepository standardRepository;
	
	//保存
	@Override
	public void save(Standard standard) {
		  
		
		standardRepository.save(standard);
	}

	//分页查询
	@Override
	public Page<Standard> findAll(Pageable pageable) {
		  
		
		return standardRepository.findAll(pageable);
	}

}
  
