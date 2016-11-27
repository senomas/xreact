package com.senomas.react.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.common.base.Predicates;
import com.senomas.common.persistence.RepositoryFactoryBean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@ComponentScan({ "com.senomas.boot", "com.senomas.react.demo", "com.senomas.common.loggerfilter" })
@EnableSwagger2
@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = RepositoryFactoryBean.class)
public class Application {
	private final static Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	ResourcePatternResolver resolver;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(Predicates.or(PathSelectors.ant("/api/**"), PathSelectors.ant("/auth/**"))).build();
	}

	public static void main(String[] args) {
		try {
			log.info("CHANGE-LOG:\n{}",
					IOUtils.toString(Application.class.getResourceAsStream("/META-INF/changelog.txt"), "UTF-8"));
		} catch (IOException e) {
			log.warn("Error reading changelog {}", e.getMessage(), e);
		}
		String profiles = System.getProperty("spring.profiles.active");
		if (profiles != null) {
			String px[] = profiles.split(",");
			if (px.length > 0) {
				StringBuilder sb = new StringBuilder();
				File gitcfg = new File(new File(System.getProperty("user.home")), ".gitconfig");
				String user = null;
				if (gitcfg.exists()) {
					try (InputStream in = new FileInputStream(gitcfg)) {
						Properties prop = new Properties();
						prop.load(in);
						user = prop.getProperty("name");
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				}
				if (user == null) {
					user = System.getProperty("user.name");
				}
				for (int i = 0, il = px.length; i < il; i++) {
					if (i > 0)
						sb.append(',');
					sb.append(px[i]);
					if ("dev".equals(px[i])) {
						sb.append(",dev-").append(user);
					}
					if ("test".equals(px[i])) {
						sb.append(",test-").append(user);
					}
				}
				System.setProperty("spring.profiles.active", sb.toString());
			}
		}
		SpringApplication.run(Application.class, args);
	}

}
