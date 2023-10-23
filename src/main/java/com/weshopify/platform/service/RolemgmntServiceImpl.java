package com.weshopify.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weshopify.platform.bean.RoleBean;
import com.weshopify.platform.outbound.RoleMgmtClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RolemgmntServiceImpl implements RoleMgmtService {

	@Autowired
	private RoleMgmtClient roleMgmtClient;

	@Override
	public List<RoleBean> getAllRoles() {
		return roleMgmtClient.findallRoles();
	}

	@Override
	public List<RoleBean> createRoles(RoleBean roleBean) {

		return roleMgmtClient.createRole(roleBean);
	}

}
