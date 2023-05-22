package com.example.demo;

import java.util.TimeZone;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
public class DBConnection {
    HikariDataSource dataSource;
    void getConnection(){
        String databaseName = "progettojavafx";
        String databaseUser = "progettojavafx";
        String databasePassword = "siSperabene!";
        String url = "jdbc:mysql://localhost:3306/"+databaseName+"?user="+databaseUser+"&password="+databasePassword+"&serverTimezone=" + TimeZone.getDefault().getID();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setLeakDetectionThreshold(2000);
        dataSource = new HikariDataSource(config);
    }
}
