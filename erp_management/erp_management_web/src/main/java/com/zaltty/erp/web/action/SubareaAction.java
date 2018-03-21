package com.zaltty.erp.web.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.zaltty.erp.doamin.base.SubArea;
import com.zaltty.erp.service.base.SubareaService;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午6:07:00 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends CommonAction<SubArea>{

	public SubareaAction( ) {
		  
		super(SubArea.class);  
		}
	
	@Autowired
	private SubareaService subareaService;
	
	@Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
	
	public String save(){
		subareaService.save(getModel());
		
		return SUCCESS;
	}
	
	

@Action(value="subareaAction_pageQuery")
//AJAX不需要跳转页面
	public String pageQuery() throws IOException{
	// easyui页码从1开始,而SpringDataJPA的页面从0开始,所有page要减1
	Pageable pageable = new PageRequest(page-1, rows);
	Page<SubArea> page = subareaService.findAll(pageable);
	
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.setExcludes(new String[] {"subareas"});
	
	page2json(page, jsonConfig);
	
	
	return NONE;
}
}
  
