package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午3:18:01 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	//查询未关联定区的客户
    List<Customer> findByFixedAreaIdIsNull();
}
  
