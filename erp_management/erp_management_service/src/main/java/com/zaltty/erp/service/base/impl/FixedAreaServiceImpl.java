package com.zaltty.erp.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.FixedAreaRepository;
import com.zaltty.erp.doamin.base.FixedArea;
import com.zaltty.erp.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:42:16 <br/>       
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService{

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Override
	public void save(FixedArea fixedArea) {
		  
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> findAll(Pageable pageable) {
		  
		
		return fixedAreaRepository.findAll(pageable);
	}

}
  
