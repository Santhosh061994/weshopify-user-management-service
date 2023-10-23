package com.weshopify.platform.outbound;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weshopify.platform.bean.RoleBean;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleMgmtClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${iamserver.base-url}")
	private String iam_server_api_baseUrl;

	@Value("${iamserver.role-api}")
	private String iam_server_api_roleUrl;

	@Value("${iamserver.user-name}")
	private String iam_user_name;

	@Value("${iamserver.password}")
	private String iam_password;

	public List<RoleBean> findallRoles() {
		List<RoleBean> roleList = null;
		String role_api_url = iam_server_api_baseUrl + iam_server_api_roleUrl;
		log.info("constructed roleapi_url is:\t" + role_api_url);
		HttpEntity<String> request = prepareRequestBody(null);
		log.info("http entity request:\t" + request);

		ResponseEntity<Object> apiResposne = restTemplate.exchange(role_api_url, HttpMethod.GET, request, Object.class);
		log.info("response code of the role api is:\t" + apiResposne.getStatusCode().value());

		if (HttpStatus.OK.value() == apiResposne.getStatusCode().value()) {
			Object responseBody = apiResposne.getBody();
			roleList = parseResponse(responseBody);
			for (RoleBean role : roleList) {
				log.info("role id is:\t" + role.getId());
				log.info("role name is:\t" + role.getDisplayName());
			}
			log.info("the response body is:\t" + apiResposne);
			System.out.println("the response body is:\t" + apiResposne);
		}

		return Optional.ofNullable(roleList).get();
	}

	public List<RoleBean> createRole(RoleBean role) {
		List<RoleBean> roleLists = null;
		String role_api_url = iam_server_api_baseUrl + iam_server_api_roleUrl;
		log.info("constructed roleapi_url is:\t" + role_api_url);
		role.setSchemas(new String[]{"urn:ietf:params:scim:schemas:extension:2.0:Role"});
		String roleBean = null;
		try {
			roleBean = objectMapper.writeValueAsString(role);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity<String> requestbody = prepareRequestBody(roleBean);
		log.info("http entity request:\t" + requestbody);

		ResponseEntity<Object> apiResposne = restTemplate.exchange(role_api_url, HttpMethod.POST, requestbody,
				Object.class);
		log.info("response code of the role api is:\t" + apiResposne.getStatusCode().value());

		if (HttpStatus.CREATED.value() == apiResposne.getStatusCode().value()) {
			roleLists = findallRoles();

		}

		return Optional.ofNullable(roleLists).get();

	}

	private HttpEntity<String> prepareRequestBody(String rolePayload) {

		String admincreds = iam_user_name + ":" + iam_password;
		String encodedAdminCred = Base64.getEncoder().encodeToString(admincreds.getBytes());
		log.info("admin Creds Encoded are:\t" + encodedAdminCred);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + encodedAdminCred);
		HttpEntity<String> requestBody = null;
		if (StringUtils.isEmpty(rolePayload) || StringUtils.isBlank(rolePayload)) {
			requestBody = new HttpEntity<String>(headers);
		} else {
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			requestBody = new HttpEntity<String>(rolePayload, headers);
		}

		return requestBody;
	}

	private List<RoleBean> parseResponse(Object responseBody) {
		List<RoleBean> resourcesList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
			log.info("json  string response is:\t" + response);
			JSONObject jsonresponse = new JSONObject(response);
			JSONArray jsonarray = (JSONArray) Optional.ofNullable(jsonresponse)
					.filter(obj -> jsonresponse.has("Resources")).get().get("Resources");
			Gson gson = new Gson();
			Type type = new TypeToken<List<RoleBean>>() {
			}.getType();
			resourcesList = gson.fromJson(jsonarray.toString(), type);
			log.info("Resources lis are:\t" + resourcesList.size());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return Optional.ofNullable(resourcesList).get();
	}
}
