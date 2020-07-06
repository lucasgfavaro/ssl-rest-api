package com.lgf.restapi.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class WebServerConfig {

	private static final Logger log = LoggerFactory.getLogger(WebServerConfig.class);

	@Value("${ssl-port}")
	private int httpsPort;
	@Value("${ssl.keystore.name}")
	private String keyStoreName;
	@Value("${ssl.keystore.alias}")
	private String alias;
	@Value("${ssl.keystore.secret}")
	private String password;
	@Value("${ssl.keystore.type}")
	private String storeType;

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() throws IOException {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

		// ACTIVATE SECONDARY SSL CONNECTOR
		this.createSslConnector(factory);

		return factory;
	}

	private void createSslConnector(TomcatServletWebServerFactory factory) throws IOException {

		File keystore = new ClassPathResource(keyStoreName).getFile();
	
		// Create connector only if keystore is found
		if (keystore.exists()) {
			Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(httpsPort);
			protocol.setKeystoreType(storeType);
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keystore.getAbsolutePath());
			protocol.setKeystorePass(password);
			protocol.setKeyAlias(alias);
			factory.addAdditionalTomcatConnectors(connector);
		} else {
			log.error("Can't access keystore: [" + keyStoreName + "]");
		}
	}
}
