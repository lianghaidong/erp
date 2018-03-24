package com.itheima.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zaltty.utils.MailUtils;
import com.zaltty.utils.SmsUtils;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

	private Customer model = new Customer();

	@Override
	public Customer getModel() {

		return model;
	}

	@Action(value = "customerAction_sendSMS")
	public String sendSMS() {

		// 随机生成验证码
		String code = RandomStringUtils.randomNumeric(6);
		System.out.println(code);
		// 将生成的验证码储存在session里面
		ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);

		try {
			// 发送验证码
			SmsUtils.sendSms(getModel().getTelephone(), code);
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return NONE;
	}

	/* customerAction_regist */
	// 使用属性驱动获取用户输入的验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Action(value = "customerAction_regist", results = {
			@Result(name = "success", location = "signup-success.html", type = "redirect"),
			@Result(name = "error", location = "signup-fail.html", type = "redirect") })
	public String regist() {
		// 校验验证码
		String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");

		if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(serverCode) && checkcode.equals(serverCode)) {

			// 注册
			WebClient.create("http://localhost:8180/crm/webService/customerService/save")
					.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(model);

			// 随机生成的邮件激活码
			String activeCode = RandomStringUtils.randomNumeric(32);
			// 将邮件激活码存储在redis中
			redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);
			;

			String emailBody = "感谢您注册本网站的账号,请在24小时内点击<a href='http://localhost:8280/portal/customerAction_active.action?activeCode="
					+ activeCode + "&telephone=" + model.getTelephone() + "'>本链接<a/>激活您的账号";
			// 发送激活邮件
			MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);

			return SUCCESS;
		}
		return ERROR;
	}

	// 使用属性驱动获取激活码
	private String activeCode;

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	// 激活
	@Action(value = "customerAction_active", results = {
			@Result(name = "success", location = "login.html", type = "redirect"),
			@Result(name = "error", location = "signup-fail.html", type = "redirect") })
	public String active() {

		// 比对激活码
		String serverCode = redisTemplate.opsForValue().get(model.getTelephone());

		if (StringUtils.isNotEmpty(activeCode) && StringUtils.isNoneEmpty(serverCode)
				&& activeCode.equals(serverCode)) {
			// 判断用户是否已经激活
			// 激活,请求客户服务器地址
			WebClient.create("http://localhost:8180/crm/webService/customerService/active")
					.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.query("telephone", model.getTelephone()).put(null);

			return SUCCESS;
		}
		return ERROR;
	}

					
	@Action(value = "customerAction_login", results = {
			@Result(name = "success", location = "index.html", type = "redirect"),
			@Result(name = "error", location = "login.html", type = "redirect"),
			//提示用户跳转到激活页面
			@Result(name = "unactived", location = "login.html", type = "redirect") })
	public String login() {
		String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");

		if (StringUtils.isNotEmpty(serverCode) 
				&& StringUtils.isNotEmpty(checkcode) 
				&& serverCode.equals(checkcode)) {
			
		// 校验用户是否激活
		Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isactive")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.query("telephone", model.getTelephone())
				.get(Customer.class);


			if (customer != null && customer.getType() != null) {
				if (customer.getType()==1) {
					//激活了 就登录
					Customer c = WebClient.create(
                            "http://localhost:8180/crm/webService/customerService/login")
                            .type(MediaType.APPLICATION_JSON)
                            .query("telephone", model.getTelephone())
                            .query("password", model.getPassword())
                            .accept(MediaType.APPLICATION_JSON)
                            .get(Customer.class);
					
					if (c!=null) {
						ServletActionContext.getRequest().getSession().setAttribute("user", c);
						return SUCCESS;
					}else {
						return  ERROR;
					}
				}else {
					//用户已注册成功,但是还没有激活,提示用户跳转到激活页面
					return "unactived";
				}
			}
		}

		return ERROR;
	}

}
