package com.zaltty.erp.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.CourierRepository;
import com.zaltty.erp.dao.base.StandardRepository;
import com.zaltty.erp.doamin.base.Courier;
import com.zaltty.erp.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:08:30 <br/>       
 */
@Transactional
@Service
public class CourierServiceImpl implements CourierService{

	@Autowired
	private CourierRepository courierRepository;
	
	//保存
	@Override
	public void save(Courier courier) {
		  
		courierRepository.save(courier);
		
	}

	//查询
	@Override
	public Page<Courier> findAll(Pageable pageable) {
		  
		 
		return courierRepository.findAll(pageable);
	}

	//批量删除
	@Override
	public void babatchDel(String ids) {
		// 真实开发中只有逻辑删除
        // null " "
        // 判断数据是否为空
		if (StringUtils.isNotEmpty(ids)) {
			//切割数据
			String[] split = ids.split(",");
			for (String id : split) {
				 courierRepository.updateDelTagById(Long.parseLong(id));
				 }
					
		}
		
	}

	@Override
	public Page<Courier> findAll(Specification<Courier> specification,
			Pageable pageable) {
	
		return courierRepository.findAll(specification, pageable);
	}

	
//	//查询在职快递员方法二(使用springdateJpa命名规范方式,进入courierRepository接口自定义方法)
	
	@Override
	public List<Courier> findAvaible() {
		
		return courierRepository.findBydeltagIsNull();
	}


	
}
  
