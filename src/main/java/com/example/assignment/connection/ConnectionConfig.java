package com.example.assignment.connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class ConnectionConfig {
	 @Bean
	    public DataSource createDataSource() throws Exception {
	        ComboPooledDataSource ds = new ComboPooledDataSource();
	        
	        
	        ds.setJdbcUrl("jdbc:ucanaccess://src/main/resources/accountsdb.accdb;openExclusive=false;ignoreCase=true;showSchema=true");
	        ds.setDriverClass("net.ucanaccess.jdbc.UcanaccessDriver");
	        
	        return ds;
	        
	    }
}
