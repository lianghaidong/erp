package com.zaltty.erp.web.action.base;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.sym.Name;
import com.lowagie.text.Row;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.zaltty.erp.doamin.base.Courier;
import com.zaltty.erp.doamin.base.Standard;
import com.zaltty.erp.service.base.CourierService;
import com.zaltty.erp.web.action.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午7:40:32 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction <Courier>{

	public CourierAction() {
		super(Courier.class);
		
	}

	

	@Autowired
	private CourierService courierService;

	@Action(value = "courierAction_save", results = {
			@Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
	public String save() {

		courierService.save(getModel());
		return SUCCESS;
	}

	

	@Action(value = "courierAction_pageQuery")
	public String pageQuery() throws IOException {

		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 获取参数
				String courierNum = getModel().getCourierNum();
				String company = getModel().getCompany();
				String type = getModel().getType();
				Standard standard = getModel().getStandard();

				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotEmpty(courierNum)) {
					// 如果工号不为空,构建一个等值查询条件
					// where courierNum = "001"
					// 参数二:具体要比较的值
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
					list.add(p1);
				}
				if (StringUtils.isNotEmpty(company)) {
					// 如果工号不为空,构建一个等值查询条件
					// where courierNum = "002"
					// 参数二:具体要比较的值
					Predicate p2 = cb.like(root.get("company").as(String.class), "%" + company + "%");
					list.add(p2);
				}
				if (StringUtils.isNotEmpty(type)) {
					// 如果工号不为空,构建一个等值查询条件
					// where courierNum = "003"
					// 参数二:具体要比较的值
					Predicate p3 = cb.equal(root.get("type").as(String.class), type);
					list.add(p3);
				}

				if (standard != null) {
					String name = standard.getName();
					if (StringUtils.isNotEmpty(name)) {
						Join<Object, Object> join = root.join("standard");
						Predicate p4 = cb.equal(root.get("name").as(String.class), name);
						list.add(p4);

					}
				}

				// 用户没有输入查询条件
				if (list.size() == 0) {
					return null;
				}

				/* 用户输入了查询条件 */
				Predicate[] arr = new Predicate[list.size()];
				list.toArray(arr);

				Predicate predicate = cb.and(arr);

				return predicate;
			}
		};

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> page = courierService.findAll(specification, pageable);

	
		

		// 将map集合转换成json数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "fixedAreas", "takeTime" });

		page2json(page, jsonConfig);

		return NONE;
	}

	// 使用属性驱动获取要删除的快递员的Id
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	// 批量删除
	@Action(value = "courierAction_batchDel", results = {
			@Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
	public String batchDel() {
		courierService.babatchDel(ids);
		return SUCCESS;
	}

	//查询在职快递员方法一
	@Action(value = "courierAction_listajax")
	public String listajax() throws IOException {
		// 查询所有的在职的快递员
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 比较空值
				//root 需要判断的参数,character表示参数类型
				Predicate predicate = cb.isNull(root.get("deltag").as(Character.class));
				
				return predicate;
			}
		};
		
		Page<Courier> p = courierService.findAll(specification,null);
		
		List<Courier> list = p.getContent();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
		
		list2json(list, jsonConfig);
		return NONE;
	}

	
	//查询在职快递员方法二(使用springdateJpa命名规范方式)
	@Action(value = "courierAction_listajax2")
	public String listajax2() throws IOException {
		
		
		List<Courier> list = courierService.findAvaible();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
		
		list2json(list, jsonConfig);
		return NONE;
	}

}
