package com.xplusz.trackr.conf.security;

import com.xplusz.trackr.conf.ApplicationConfigurationProperties;
import com.xplusz.trackr.security.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private ApplicationConfigurationProperties configuration;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(configuration.getClientId())
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("USER")
                .scopes("read", "write")
                .resourceIds(RestApiResourceServerConfiguration.RESOURCE_ID)
                .secret(configuration.getClientSecret());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(this.tokenStore);
        return defaultTokenServices;
    }
}
