package com.zaltty.erp.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zaltty.erp.doamin.base.Standard;
import com.zaltty.erp.service.base.StandardService;

import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午2:57:25 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport
		implements ModelDriven<Standard> {

	private Standard model = new Standard();

	@Override
	public Standard getModel() {

		return model;
	}

	@Autowired
	private StandardService standardService;

	@Action(value = "standardAction_save", results = {@Result(name = "success",
			location = "/pages/base/standard.html", type = "redirect")})
	public String save() {

		// System.out.println("Standard Action 收到请求!!");
		standardService.save(model);
		return SUCCESS;
	}

	// 使用属性驱动获取数据
	private int page;// 第几页
	private int rows;// 每一页显示多少条数据

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	// AJAX请求不需要跳转页面
	@Action(value = "standardAction_pageQuery")
	public String pageQuery() throws IOException {

		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page = standardService.findAll(pageable);
		
		//总数据条数
		long total = page.getTotalElements();
		//当前页要显示的内容
		List<Standard> list = page.getContent();
		//封装数据
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		
		// JSONObject : 封装对象或map集合
        // JSONArray : 数组,list集合
        // 把对象转化为json字符串
		String json = JSONObject.fromObject(map).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		//防止中文乱码
		response.setContentType("application/json;charset=UTF-8");
		//将数据传回页面
		response.getWriter().println(json);
		
		return NONE;
	}

}
