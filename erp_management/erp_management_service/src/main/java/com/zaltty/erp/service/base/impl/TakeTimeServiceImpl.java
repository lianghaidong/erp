package com.zaltty.erp.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaltty.erp.dao.base.TakeTimeRepository;
import com.zaltty.erp.doamin.base.TakeTime;
import com.zaltty.erp.service.base.TakeTimeService;

@Transactional
@Service
public class TakeTimeServiceImpl implements TakeTimeService {

	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public List<TakeTime> findAll() {
	
		return takeTimeRepository.findAll();
	}
}
