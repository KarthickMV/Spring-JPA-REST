package com.example.demo.persistenceStore;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:double-databases.properties"})
@EnableJpaRepositories(
		basePackages="com.example.demo.dbTwo.repository",
		entityManagerFactoryRef="userEntityManager",
		transactionManagerRef="userTransactionManager"
		)
public class PersistenceUserConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean userEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan(
				new String[] {"com.example.demo.dbTwo.model"});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("spring.jpa.properties.hibernate.dialect"
				, env.getProperty("spring.jpa.properties.hibernate.dialect"));
		propertiesMap.put("spring.jpa.hibernate.ddl-auto"
				, env.getProperty("spring.jpa.hibernate.ddl-auto"));
		propertiesMap.put("spring.jpa.show-sql"
				, env.getProperty("spring.jpa.show-sql"));
		propertiesMap.put("spring.sql.init.mode"
				, env.getProperty("spring.sql.init.mode"));
		propertiesMap.put("spring.jpa.defer-datasource-initialization"
				 , env.getProperty("spring.jpa.defer-datasource-initialization"));
		em.setJpaPropertyMap(propertiesMap);
		return em;
	}
	
	@Bean
	public PlatformTransactionManager userTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
		return transactionManager;
	}
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource-h2")
	public DataSource getDataSource() {
		return DataSourceBuilder.create().build();
		
	}

}
