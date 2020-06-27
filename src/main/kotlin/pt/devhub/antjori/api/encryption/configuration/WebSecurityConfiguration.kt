package pt.devhub.antjori.api.encryption.configuration

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import pt.devhub.antjori.api.encryption.configuration.properties.AdminConfigProperties
import pt.devhub.antjori.api.encryption.configuration.properties.UserConfigProperties
import pt.devhub.antjori.api.encryption.security.EncryptionBasicAuthenticationEntryPoint

/**
 * Web security configuration that allows access to actuator endpoints with basic auth.
 *
 * @author antjori
 */
@EnableWebSecurity
class WebSecurityConfiguration(
        private val adminConfigProperties: AdminConfigProperties,
        private val userConfigProperties: UserConfigProperties,
        private val authenticationEntryPoint: EncryptionBasicAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // permit requests to all endpoints including **/health and **/info except /admin/* and gcm-encryption/*
        // @formatter:off
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/admin/health", "/admin/info").permitAll()
                .antMatchers(HttpMethod.GET,"/admin/**").hasRole(this.adminConfigProperties.role)
                .antMatchers(HttpMethod.GET,"/gcm-encryption/**").hasRole(this.userConfigProperties.role)
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors().disable()
                .httpBasic()
                .authenticationEntryPoint(this.authenticationEntryPoint);
        // @formatter:on
    }

    @Bean
    fun users(): UserDetailsService {
        return InMemoryUserDetailsManager(
                User.builder()
                        .username(this.adminConfigProperties.name)
                        .password(passwordEncoder().encode(this.adminConfigProperties.password))
                        .roles(this.adminConfigProperties.role)
                        .build(),
                User.builder()
                        .username(this.userConfigProperties.name)
                        .password(passwordEncoder().encode(this.userConfigProperties.password))
                        .roles(this.userConfigProperties.role)
                        .build()
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}