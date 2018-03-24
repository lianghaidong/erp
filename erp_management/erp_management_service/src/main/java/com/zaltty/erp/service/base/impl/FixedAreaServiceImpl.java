package com.zaltty.erp.service.base.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.CourierRepository;
import com.zaltty.erp.dao.base.FixedAreaRepository;
import com.zaltty.erp.dao.base.SubareaRepository;
import com.zaltty.erp.dao.base.TakeTimeRepository;
import com.zaltty.erp.doamin.base.Courier;
import com.zaltty.erp.doamin.base.FixedArea;
import com.zaltty.erp.doamin.base.SubArea;
import com.zaltty.erp.doamin.base.TakeTime;
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
	
	@Autowired
	private CourierRepository courierRepository;
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	
	@Autowired
	private SubareaRepository  subAreaRepository;
	
	@Override
	public void save(FixedArea fixedArea) {
		  
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> findAll(Pageable pageable) {
		  
		
		return fixedAreaRepository.findAll(pageable);
	}

	@Override
	public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
		// 代码执行成功以后,快递员表发生update操作,快递员和定区中间表会发生insert操作

        // 持久态对象
		FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		// 建立快递员和时间的关联
		courier.setTakeTime(takeTime);
		  // 建立快递员和定区的关联
	        
		  // 下面的写法是错的,因为在Courier实体类中fixedAreas字段的上方添加了mappedBy属性
	        // 就代表快递员放弃了对关系的维护
	        // courier.getFixedAreas().add(fixedArea);
		
		fixedArea.getCouriers().add(courier);
	}

	@Override
	public void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds) {
		// 关系是由分区在维护
		FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
		Set<SubArea> subareas = fixedArea.getSubareas();
		for (SubArea subArea : subareas) {
			// 先解绑，把当前定区绑定的所有分区全部解绑
			subArea.setFixedArea(null);
		}
		//再绑定
		for (Long subAreaId : subAreaIds) {
			SubArea subArea = subAreaRepository.findOne(subAreaId);
			subArea.setFixedArea(fixedArea);
		}
		
	}

}
  
