package com.example.demo.persistenceStore;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
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
		//repository classes to be scanned
		basePackages="com.example.demo.dbOne.repository",
		//same name as the method name that returns entity manager
		entityManagerFactoryRef="studentEntityManager",
		transactionManagerRef="studentTransactionManager",
		excludeFilters={@Filter(type =FilterType.REGEX, pattern = ".*dbTwo.*")}
)
public class PersistenceStudentConfiguration {

	@Autowired
	private Environment env;
	
	//to denote this as a primary entity manager globally
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean studentEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(studentDataSource());
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setPackagesToScan(
				//model classes to be scanned as an array
				new String[] {"com.example.demo.dbOne.model"});
		HashMap<String,Object> connectionProperties = new HashMap<String,Object>();
		//set the dialect and ddl flag as the properties for the entityManager
		connectionProperties.put("spring.jpa.properties.hibernate.dialect", 
				env.getProperty("spring.jpa.properties.hibernate.dialect"));
		connectionProperties.put("spring.jpa.hibernate.ddl-auto", 
				env.getProperty("spring.jpa.hibernate.ddl-auto"));
		connectionProperties.put("spring.jpa.show-sql", 
				env.getProperty("spring.jpa.show-sql"));
		connectionProperties.put("spring.sql.init.mode", 
				env.getProperty("spring.sql.init.mode"));
		em.setJpaPropertyMap(connectionProperties);
		return em;
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager studentTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(studentEntityManager().getObject());
		return transactionManager;
	}
	
	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource studentDataSource() {
		/* These details will be filled in the datasource for builder
		 * DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 * dataSource.setUrl(env.getProperty("student.jdbc.url"));
		 * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		 * dataSource.setUsername(env.getProperty("jdbc.user"));
		 * dataSource.setPassword(env.getProperty("jdbc.password")); return dataSource;
		 */
		return DataSourceBuilder.create().build();
	}
	
	
}
