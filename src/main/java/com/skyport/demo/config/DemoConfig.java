package com.skyport.demo.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DemoConfig {

	@Value("${app.database.user:dummy}")
	private String dbUser;
	
	@Value("${app.database.pswd:dummy}")
	private String dbPswd;
	
	@Value("${app.database.url:none}")
	private String dbUrl;
	
	@Bean(name = "postgreDataSource")
	public DataSource postgreDataSource() throws URISyntaxException {
		DataSourceBuilder builder = DataSourceBuilder.create();
		
		String driverClassName = "org.postgresql.Driver";
		builder.driverClassName(driverClassName);
		builder.username(dbUser);
		builder.password(dbPswd);
		builder.url(dbUrl);

		return builder.build();
	}
	
	@Bean(name = "postgreSqlSessionFactory")
	public SqlSessionFactory postgreSqlSessionFactory(@Qualifier("postgreDataSource")DataSource postgreDataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgreDataSource);
		sqlSessionFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:**/sql/*.xml")
				);
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name = "postgreSqlSessionTemplate")
	public SqlSessionTemplate postgreSqlSessionTemplate(@Qualifier("postgreSqlSessionFactory")SqlSessionFactory postgreSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(postgreSqlSessionFactory);
	}
}
