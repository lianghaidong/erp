package com.zaltty.erp.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zaltty.erp.doamin.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:31:40 <br/>       
 */
//JpaSpecificationExecutor不能单独使用
//JpaSpecificationExecutor要和JpaRepository一起使用
public interface CourierRepository extends JpaRepository<Courier,Long>,JpaSpecificationExecutor<Courier> {

	// 根据ID更改删除的标志位
    @Modifying
    @Query("update Courier set deltag = 1 where id = ?")
	void updateDelTagById(long id);

    
  //查询在职快递员方法二(使用springdateJpa方式)
    List<Courier> findBydeltagIsNull();
}
  
