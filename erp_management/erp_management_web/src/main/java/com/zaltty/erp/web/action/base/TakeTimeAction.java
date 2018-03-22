package com.zaltty.erp.web.action.base;


import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zaltty.erp.doamin.base.TakeTime;
import com.zaltty.erp.service.base.TakeTimeService;
import com.zaltty.erp.web.action.CommonAction;

@Namespace("/")
@Controller
@ParentPackage("struts-default")
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime>{

	public TakeTimeAction() {
		super(TakeTime.class);
	}
	
	@Autowired
	private TakeTimeService takeTimeService;
	

	@Action(value="takeTimeAction_listajax")
	public String listajax() throws IOException{
		
		
		List<TakeTime> list = takeTimeService.findAll();
		
		
		
		list2json(list, null);
		
		return NONE;
	}

}
