package com.atguigu.crowd.mvc.handler;

import java.util.List;

import com.atguigu.crowd.monitor.annotation.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

/**
 * 角色控制器
 */
@RestController
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 删除角色
	 * @param roleIdList
	 * @return
	 */
	@BusinessType("删除角色")
	@PreAuthorize("hasAuthority('role:delete')")
	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdAarry(@RequestBody List<Integer> roleIdList) {
		
		roleService.removeRole(roleIdList);
		
		return ResultEntity.successWithoutData();
	}

	/**
	 * 更新角色
	 *
	 * @param role
	 * @return
	 */
	@BusinessType("更新角色")
	@PreAuthorize("hasAuthority('role:udpate')")
	@RequestMapping("/role/update.json")
	public ResultEntity<String> updateRole(Role role) {
		
		roleService.updateRole(role);
		
		return ResultEntity.successWithoutData();
	}

	/**
	 * 新增角色
	 *
	 * @param role
	 * @return
	 */
	@BusinessType("新增角色")
	@PreAuthorize("hasAuthority('role:add')")
	@RequestMapping("/role/save.json")
	public ResultEntity<String> saveRole(Role role) {
		
		roleService.saveRole(role);
		
		return ResultEntity.successWithoutData();
	}

	/**
	 * 获取角色页面
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@BusinessType("查询角色")
	@PreAuthorize("hasAuthority('role:get')")
	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
				@RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
				@RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
				@RequestParam(value="keyword", defaultValue="") String keyword
			) {
		
		// 调用Service方法获取分页数据
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
		
		// 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
		return ResultEntity.successWithData(pageInfo);
	}

}
