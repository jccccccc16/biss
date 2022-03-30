package com.atguigu.crowd.mvc.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.atguigu.crowd.exception.CanNotRemoveCurrentUser;
import com.atguigu.crowd.monitor.annotation.BusinessType;
import com.atguigu.crowd.mvc.config.SecurityAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

@Controller
public class AdminHandler {
	
	@Autowired
	private AdminService adminService;

	/**
	 * 更新
	 * @param admin
	 * @param pageNum
	 * @param keyword
	 * @return
	 */
	@BusinessType("更新用户")
	@PreAuthorize("hasAuthority('user:update')")
	@RequestMapping("/admin/update.html")
	public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
		
		adminService.update(admin);
		
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}

	/**
	 * 跳转到更新页面
	 * @param adminId
	 * @param modelMap
	 * @return
	 */

	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
				@RequestParam("adminId") Integer adminId,
				ModelMap modelMap
			) {
		
		// 1.根据adminId查询Admin对象
		Admin admin = adminService.getAdminById(adminId);
		
		// 2.将Admin对象存入模型
		modelMap.addAttribute("admin", admin);
		
		return "admin-edit";
	}

	/**
	 * 新增用户
	 * @param admin
	 * @return
	 */
	@BusinessType("新增用户")
	@PreAuthorize("hasAuthority('user:add')")
	@RequestMapping("/admin/save.html")
	public String save(Admin admin) {
		
		adminService.saveAdmin(admin);
		
		return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
	}

	/**
	 * 删除用户
     *
	 * @param adminId
	 * @param pageNum
	 * @param keyword
	 * @return
	 */
	@BusinessType("删除用户")
	@PreAuthorize("hasAuthority('user:delete')")
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(
				@PathVariable("adminId") Integer adminId,
				@PathVariable("pageNum") Integer pageNum,
				@PathVariable("keyword") String keyword
			) {

        Admin admin = adminService.getAdminById(adminId);
        // 是否为当前用户
        if(isCurrentUser(admin.getLoginAcct())){
            // 如果是
            throw new CanNotRemoveCurrentUser(CrowdConstant.MESSAGE_CAN_NOT_REMOVE_CURRENT_USER);
        }
		// 执行删除
		adminService.remove(adminId);

		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}

	/**
	 * 是否为当前用户
     *
	 * @param loginAcct
	 * @return
	 */

	private  boolean isCurrentUser(String loginAcct){
        SecurityAdmin securityAdmin = (SecurityAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginAcctSource = securityAdmin.getOriginalAdmin().getLoginAcct();
        if(loginAcctSource.equals(loginAcct)){
			return true;
		}
		return false;
	}

	/**
	 *  用户列表
	 * @param keyword
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	@BusinessType("查看用户")
    @PreAuthorize("hasAuthority('user:get')")
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(
				
				// 使用@RequestParam注解的defaultValue属性，指定默认值，在请求中没有携带对应参数时使用默认值
				// keyword默认值使用空字符串，和SQL语句配合实现两种情况适配
				@RequestParam(value="keyword", defaultValue="") String keyword,
				
				// pageNum默认值使用1
				@RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
				
				// pageSize默认值使用5
				@RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
				
				ModelMap modelMap
			
			) {
		
		// 调用Service方法获取PageInfo对象
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		
		// 将PageInfo对象存入模型
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		
		return "admin-page";
	}
	
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {
		
		// 强制Session失效
		session.invalidate();
		
		return "redirect:/admin/to/login/page.html";
	}

	/**
	 * 废弃，交由spring security管理
	 * @param loginAcct
	 * @param userPswd
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(
				@RequestParam("loginAcct") String loginAcct,
				@RequestParam("userPswd") String userPswd,
				HttpSession session
			) {
		
		// 调用Service方法执行登录检查
		// 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
		
		// 将登录成功返回的admin对象存入Session域
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
		
		return "redirect:/admin/to/main/page.html";
	}
	

//
//	@ResponseBody
//	@RequestMapping("/admin/test/pre/filter")
//	public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList) {
//		return ResultEntity.successWithData(valueList);
//	}

}
