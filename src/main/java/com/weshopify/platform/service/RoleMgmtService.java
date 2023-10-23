package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.bean.RoleBean;

public interface RoleMgmtService {
	
	
	List<RoleBean> getAllRoles();
	List<RoleBean> createRoles(RoleBean roleBean);
}
