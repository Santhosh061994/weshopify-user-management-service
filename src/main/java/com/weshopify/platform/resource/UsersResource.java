package com.weshopify.platform.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.bean.RoleBean;
import com.weshopify.platform.bean.WeshopifyPlatformUserBean;
import com.weshopify.platform.service.RoleMgmtService;
import com.weshopify.platform.service.RolemgmntServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author santh
 * @Since 09-09-2023 {@summary} Create and Manage user Resources
 */

@Slf4j
@RestController
public class UsersResource {
	
	@Autowired
	private RolemgmntServiceImpl roleMgmtService;

	@PostMapping(value = "/users")
	public ResponseEntity<List<WeshopifyPlatformUserBean>> createUser(@RequestBody WeshopifyPlatformUserBean userBean) {
		log.info("weshopify user data is  : " + userBean.toString());

		return null;
	}

	@PutMapping(value = "/users")
	public ResponseEntity<List<WeshopifyPlatformUserBean>> updateUser(@RequestBody WeshopifyPlatformUserBean userBean) {
		log.info("weshopify user data is  : " + userBean.toString());

		return null;
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<WeshopifyPlatformUserBean>> findAllUsers() {

		return null;
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<WeshopifyPlatformUserBean> findbyId(@PathVariable("userId") String userID) {

		return null;
	}
	

	@GetMapping(value = "/users/roles")
	public ResponseEntity<List<RoleBean>> findAllRoles() {
		List<RoleBean> rolesList = roleMgmtService.getAllRoles();
		ResponseEntity<List<RoleBean>> rsb = null;
		if(null!= rolesList && rolesList.size()>0) {
			rsb.ok().body(rolesList);
		}
		else {
			rsb.noContent().build();
		}
		return rsb;
	}

	@DeleteMapping(value = "/users/{userId}")
	public ResponseEntity<List<WeshopifyPlatformUserBean>> deleteuserbyId(@PathVariable("userId") String userID) {
		
		return null;
	}
}
