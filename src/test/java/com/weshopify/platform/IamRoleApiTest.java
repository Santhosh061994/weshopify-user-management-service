package com.weshopify.platform;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.bean.RoleBean;
import com.weshopify.platform.outbound.RoleMgmtClient;

public class IamRoleApiTest extends WeshopifyUserManagementServiceApplicationTests {

	@Autowired
	private RoleMgmtClient roleClient;

	//@Test
	private void testFindAllRoles() {
		List<RoleBean> responseBody = roleClient.findallRoles();
		assertNotNull(responseBody);
		System.out.println(responseBody);
	}

}
