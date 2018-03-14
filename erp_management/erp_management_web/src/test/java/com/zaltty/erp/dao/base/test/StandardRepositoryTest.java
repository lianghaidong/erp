package com.zaltty.erp.dao.base.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.util.concurrent.Service.State;
import com.zaltty.erp.dao.base.StandardRepository;
import com.zaltty.erp.doamin.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:39:55 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

    
    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void test() {
        List<Standard> list = standardRepository.findAll();
        
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void tesaat() {
        Standard standard = new Standard();
        standard.setName("张三三");
        standardRepository.save(standard);
    }
    
    @Test
    public void testUpdate() {
        Standard standard = new Standard();
        standard.setId(1L);
        standard.setName("张三三");
        standard.setMaxLength(555);
        standardRepository.save(standard);
        
    }

    @Test
    public void testUpdate1() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testUpdate11() {
        List<Standard> list = standardRepository.findByNameLike("%张三%");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testUpdate12() {
        List<Standard> list = standardRepository.findByNameAndMaxLength("张三三", 555);
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
}
  
