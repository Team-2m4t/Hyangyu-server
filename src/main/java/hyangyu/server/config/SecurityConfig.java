package hyangyu.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import hyangyu.server.jwt.JwtAccessDeniedHandler;
import hyangyu.server.jwt.JwtAuthenticationEntryPoint;
import hyangyu.server.jwt.JwtSecurityConfig;
import hyangyu.server.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*	@Override
        public void configure(WebSecurity web) {
            web
                        .ignoring()
                        .antMatchers(h2console?????????????????????????);
        }*/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token??? ???????????? ???????????? ????????? csrf??? disable?????????.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // ????????? ???????????? ?????? ????????? STATELESS??? ??????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/username").permitAll()
                .antMatchers("/api/user/password").permitAll()
                .antMatchers("/api/user").permitAll()
                .antMatchers("/api/user/image").permitAll()
                .antMatchers("/api/block/{reportedId}").permitAll()
                .antMatchers("/api/display/{displayId}").permitAll()
                .antMatchers("/api/authnum").permitAll()
                .antMatchers("/api/send-email/{email}").permitAll()
                .antMatchers("/api/fair/{fairId}").permitAll()
                .antMatchers("/api/festival/{festivalId}").permitAll()
                .antMatchers("/api/display/{order}/{page}").permitAll()
                .antMatchers("/api/myPage").permitAll()
                .antMatchers("/api/review/display/{displayId}").permitAll()
                .antMatchers("/api/review/fair/{fairId}").permitAll()
                .antMatchers("/api/review/festival/{festivalId}").permitAll()
                .antMatchers("/api/review/change/display/{displayId}").permitAll()
                .antMatchers("/api/review/change/fair/{fairId}").permitAll()
                .antMatchers("/api/review/change/festival/{festivalId}").permitAll()
                .antMatchers("/api/review/accuse/{event}/{reviewId}").permitAll()
                .antMatchers("/api/show/review/display/{displayId}").permitAll()
                .antMatchers("/api/show/review/fair/{fairId}").permitAll()
                .antMatchers("/api/show/review/festival/{festivalId}").permitAll()
                .antMatchers("/api/search").permitAll()
                .antMatchers("/api/myreview").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }

}