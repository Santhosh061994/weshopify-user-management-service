package com.weshopify.platform.bean;

import java.io.File;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @author santh
 * @since 09-09-2023
 * @apiNote : WeshopifyPlatformUser {@summary} : PlatformUserBean is used to
 *          hold user data and will be saved as users in ws02 IAM server
 */
@Data
@Builder
public class WeshopifyPlatformUserBean implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 4769001606536343970L;
	
	private String userId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	private boolean status;
	private File photos;

}
