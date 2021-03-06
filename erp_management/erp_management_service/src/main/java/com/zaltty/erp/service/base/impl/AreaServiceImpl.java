package com.zaltty.erp.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.AreaRepository;
import com.zaltty.erp.doamin.base.Area;
import com.zaltty.erp.doamin.base.Standard;
import com.zaltty.erp.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:05:03 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void save(List<Area> list) {
		  
    areaRepository.save(list);		
	}

	@Override
	public Page<Area> findAll(Pageable pageable) {
		  
		
		return areaRepository.findAll(pageable);
	}

	@Override
	public List<Area> findByQ(String q) {
		
		q="%"+q.toUpperCase()+"%";
		return areaRepository.findQ(q);
	}
	
	
}
  
