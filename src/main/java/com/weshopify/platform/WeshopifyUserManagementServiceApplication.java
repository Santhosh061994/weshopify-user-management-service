package com.weshopify.platform;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class WeshopifyUserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyUserManagementServiceApplication.class, args);
	}
	
	@Bean
	RestTemplate resTemplate() {
		
		return new RestTemplate();
		
	}
	
	 @Bean
	 ObjectMapper objectMapper() {
		 
	    	return new ObjectMapper();
	    }
	 
	 @Bean
	 RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
			SSLContext sslContext = SSLContextBuilder.create()
					.loadTrustMaterial((X509Certificate[] certificateChain, String authType) -> true)  // <--- accepts each certificate
					.build();

				Registry<ConnectionSocketFactory> socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext))
					.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory())
					.build();

				HttpClient httpClient = HttpClientBuilder.create()
					.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
					.setConnectionManagerShared(true)
					.build();

		        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		        return new RestTemplate(requestFactory);

				
			
		} 

}
