package com.zaltty.erp.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午2:57:25 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport {

    
    
    @Action(value="standard_save",results={@Result(name="success",location="standard.html",type="redirect")})
    public String save(){
        System.out.println("Standard Action 收到请求!!");
        return SUCCESS;
    }
}
  
