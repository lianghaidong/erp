package com.zaltty.erp.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaltty.erp.doamin.base.FixedArea;
import com.zaltty.erp.doamin.base.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午6:05:29 <br/>       
 */
public interface SubareaRepository extends JpaRepository<SubArea, Long>{

	// 查询未关联定区的分区
	List<SubArea> findByFixedAreaIsNull();

	
	// 查询关联到指定定区的分区
    // 使用SpringDataJPA的命名规范进行查询的时候，
    // 如果字段是对象，必须是单一对象，不能是集合
    // 传入的参数必须指定id属性
	List<SubArea> findByFixedArea(FixedArea fixedArea);

}
  
