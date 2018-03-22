package com.zaltty.erp.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaltty.erp.doamin.base.TakeTime;

public interface TakeTimeRepository extends JpaRepository<TakeTime,Long> {

}
