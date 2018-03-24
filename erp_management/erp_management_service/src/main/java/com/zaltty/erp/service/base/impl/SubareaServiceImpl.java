package com.zaltty.erp.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.SubareaRepository;
import com.zaltty.erp.doamin.base.FixedArea;
import com.zaltty.erp.doamin.base.SubArea;
import com.zaltty.erp.service.base.SubareaService;


/**  
 * ClassName:subareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午6:00:25 <br/>       
 */
@Transactional
@Service
public class SubareaServiceImpl implements SubareaService{
	
	@Autowired
	private SubareaRepository subareaRepository;

	@Override
	public void save(SubArea subArea) {
		  
		subareaRepository.save(subArea);
		
	}

	@Override
	public Page<SubArea> findAll(Pageable pageable) {
		  
		
		return subareaRepository.findAll(pageable);
	}

	// 查询未关联定区的分区
	@Override
	public List<SubArea> findUnAssociatedSubAreas() {
		
		return subareaRepository.findByFixedAreaIsNull();
	}

	 // 查询关联到指定定区的分区
	@Override
	public List<SubArea> findAssociatedSubAreas(Long fixedAreaId) {
		
		FixedArea fixedArea = new FixedArea();
		
		fixedArea.setId(fixedAreaId);
		
	return subareaRepository.findByFixedArea(fixedArea);
	}

}
  
