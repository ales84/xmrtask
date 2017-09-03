package org.alesapps.xmrtask.config;

import org.alesapps.xmrtask.model.parser.ResumeParser;
import org.alesapps.xmrtask.model.parser.ZarplataRuResumeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.alesapps.xmrtask.service"})
@EnableJpaRepositories(basePackages = {"org.alesapps.xmrtask.repository"})
@PropertySource({"classpath:db.properties"})
public class AppConfig {

    @Value("${database.driverClassName}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Value("${database.init}")
    private Boolean initEnabled;
    @Value("classpath:initDB.sql")
    private Resource initDatabaseSqlScript;

    @Value("${jpa.showSql}")
    private Boolean jpaShowSql;
    @Value("${hibernate.format_sql}")
    private Boolean hibernateFormatSql;
    @Value("${hibernate.use_sql_comments}")
    private Boolean hibernateUseSqlComments;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocalOverride(true);
        return propertyConfigurer;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(getDriverManagerDataSource());
        initializer.setDatabasePopulator(getDatabasePopulator());
        initializer.setEnabled(initEnabled);
        return initializer;
    }

    private DatabasePopulator getDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("UTF-8");
        populator.addScript(initDatabaseSqlScript);
        return populator;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("org.alesapps.**.model");
        em.setDataSource(getDriverManagerDataSource());

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(jpaShowSql);
        em.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
        jpaProperties.put("hibernate.use_sql_comments", hibernateUseSqlComments);
        em.setJpaProperties(jpaProperties);

        return em;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getJpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public ResumeParser getResumeParser() {
        return new ZarplataRuResumeParser();
    }
}
