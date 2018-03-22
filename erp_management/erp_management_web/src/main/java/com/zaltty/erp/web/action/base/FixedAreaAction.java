package com.zaltty.erp.web.action.base;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.zaltty.erp.doamin.base.Customer;
import com.zaltty.erp.doamin.base.FixedArea;
import com.zaltty.erp.service.base.FixedAreaService;
import com.zaltty.erp.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午4:45:02 <br/>
 */
@Namespace("/")
@Controller
@ParentPackage("struts-default")
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

	public FixedAreaAction() {

		super(FixedArea.class);
	}

	@Autowired
	private FixedAreaService fixedAreaService;

	@Action(value = "fixedAreaAction_save",
			results = {@Result(name = "success",
					location = "/pages/base/fixed_area.html",
					type = "redirect")})
	public String save() {
		fixedAreaService.save(getModel());
		return SUCCESS;
	}

	@Action(value = "fixedAreaAction_pageQuery")

	public String pageQuery() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> page = fixedAreaService.findAll(pageable);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] {"subareas", "couriers"});

		page2json(page, jsonConfig);

		return NONE;
	}

	// 向crm系统发起请求,查询未关联定区的客户

	@Action(value = "fixedAreaAction_findUnAssociatedCustomers")

	public String findUnAssociatedCustomers() throws IOException {

		WebClient.create("").type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);

		return NONE;

	}

}
