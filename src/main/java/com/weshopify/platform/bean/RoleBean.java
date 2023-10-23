package com.weshopify.platform.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class RoleBean implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 2457769865242262632L;
	
	private String id;
	private String displayName;
	private List<String> permissions;
	private String[] schemas;

}
