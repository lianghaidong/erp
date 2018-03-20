package com.zaltty.erp.web.action.base;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import com.opensymphony.xwork2.inject.Scoped;
import com.zaltty.erp.doamin.base.Area;
import com.zaltty.erp.doamin.base.Standard;
import com.zaltty.erp.service.base.AreaService;
import com.zaltty.erp.web.action.CommonAction;
import com.zaltty.utils.PinYin4jUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午9:10:57 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

	

	public AreaAction() {
		  
		super(Area.class);  
		
		
	}

	@Autowired
	private AreaService areaService;

	// 使用属性驱动获取用户上传的文件
	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action(value = "areaAction_importXLS", results = {@Result(name = "success",
			location = "/pages/base/area.html", type = "redirect")})

	public String importXLS() {
		try {
			HSSFWorkbook hssfWorkbook =
					new HSSFWorkbook(new FileInputStream(file));
			// 读取第一个工作簿
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

			List<Area> list = new ArrayList<>();
			
			for (Row row : sheet) {
				if (row.getRowNum()==0) {
					continue;//跳过第一行,break:跳出整个循环	
				}
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);

				String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
				String[] headByString = PinYin4jUtils
						.getHeadByString(province + city + district);
				String shortcode =
						PinYin4jUtils.stringArrayToString(headByString);

				Area area = new Area();
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setCitycode(citycode);
				area.setPostcode(postcode);
				area.setShortcode(shortcode);

				list.add(area);

			}

			areaService.save(list);
			hssfWorkbook.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return SUCCESS;
	}
	
	

		// AJAX请求不需要跳转页面
		@Action(value = "areaAction_pageQuery")
		public String pageQuery() throws IOException {

			Pageable pageable = new PageRequest(page-1, rows);
			Page<Area> page = areaService.findAll(pageable);
			
			//出现懒加载问题解决方法
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] {"subareas"}); 
			
			page2json(page, jsonConfig);
			
			
			return NONE;
		}
		
		@Action(value = "areaAction_findAll")
		public String findAll() throws IOException {
			
			//找所有数据不需要传参数,使用原来的findAll方法
			Page<Area> page = areaService.findAll(null);
			List<Area> list = page.getContent();
			
			//需要忽略的配置信息,为了避免出现懒加载错误
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"subareas"});
			
			json2json(list, jsonConfig);
			
			return NONE;
		}
	
}
