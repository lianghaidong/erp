package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**
 * ClassName:CustomerRepository <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午3:18:01 <br/>
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// 查询未关联定区的客户
	List<Customer> findByFixedAreaIdIsNull();

	// 查询已关联到指定定区的客户
	List<Customer> findByFixedAreaId(String fixedAreaId);

	 // 把关联到指定定区的客户进行解绑操作
	@Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
	@Modifying
	void unbindCustomerByFixedArea(String fixedAreaId);

	// 把客户绑定到指定的定区 ?后面的数字代表参数的位置顺序
	 @Query("update Customer set fixedAreaId = ?2 where id = ?1")
	 @Modifying
	void bindCustomerByFixedArea(Long customerId, String fixedAreaId);
	 
	 //激活
	 @Query("update Customer set type=1 where telephone=?")
	 @Modifying
	 void active(String telephone);

	 //查询是否已激活
	Customer findByTelephone(String telephone);

	//登录
	Customer findByTelephoneAndPassword(String telephone, String password);
}
