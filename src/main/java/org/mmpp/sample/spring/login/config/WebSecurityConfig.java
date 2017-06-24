package org.mmpp.sample.spring.login.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/css/**","/img/**","/js/**", "/h2/**", "/login").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/admin.html").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?message=error")
                .permitAll()
                    .and()
                .rememberMe()
                    .tokenValiditySeconds(86400) // 1ヶ月（秒）
                .and()
                .logout()
                // ログアウトがパス(GET)の場合設定する（CSRF対応）
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // ログアウトがPOSTの場合設定する
                //.logoutUrl("/logout")
                // ログアウト後の遷移先
                .logoutSuccessUrl("/")
                // セッションを破棄する
                .invalidateHttpSession(true)
                // ログアウト時に削除するクッキー名
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT LOGIN_ID, PASSWORD, ENABLED FROM USERS WHERE LOGIN_ID=?")
                .authoritiesByUsernameQuery("SELECT LOGIN_ID, ROLE FROM AUTHORITIES WHERE LOGIN_ID=?");
    }


}
