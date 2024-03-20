package mx.com.gm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author ralfs
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    protected void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}ralfs.8310")
                .roles("ADMIN", "VENDEDOR")
                .and()
                .withUser("user")
                .password("{noop}1234")
                .roles("VENDEDOR");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/auth").permitAll()
                //.antMatchers("/").permitAll()
                .antMatchers("/agregar/**", "/guardar", 
                        "/editar/**", "/eliminar").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole("ADMIN", "VENDEDOR")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/errores/403")
                //.anyRequest().permitAll()
                ;
    }
    
}
