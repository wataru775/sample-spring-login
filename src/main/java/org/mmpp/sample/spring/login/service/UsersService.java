package org.mmpp.sample.spring.login.service;

import org.mmpp.sample.spring.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public User findByUsername(String username){
        RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
        List<User> dtoList = jdbcTemplate.query("SELECT LOGIN_ID, PASSWORD, ENABLED, NAME FROM USERS WHERE LOGIN_ID=?", mapper,username);
        return dtoList.get(0);
    }
}
