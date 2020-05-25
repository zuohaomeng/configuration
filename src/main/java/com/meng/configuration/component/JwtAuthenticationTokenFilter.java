package com.meng.configuration.component;

import com.meng.configuration.entity.vo.UserVo;
import com.meng.configuration.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 *
 * @author HAO.ZUO
 * @date 2020/4/4--23:19
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = (String) request.getSession().getAttribute(this.tokenHeader);
        Integer userRoleId = (Integer) request.getSession().getAttribute("userRoleId");

        if(authHeader==null||userRoleId==null){
            System.out.println("authoruser"+userRoleId+"123"+authHeader);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && authHeader == null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //JWT
                if (cookie.getName().equals(this.tokenHeader)) {
                    authHeader = cookie.getValue();
                }
                //用户角色
                if(cookie.getName().equals("userRoleId")){
                    userRoleId = Integer.parseInt(cookie.getValue());
                }
            }
        }
        if(request.getSession().getAttribute(this.tokenHeader)==null&&authHeader!=null){
            request.getSession().setAttribute(this.tokenHeader,authHeader);
        }
        if(request.getSession().getAttribute("userRoleId")==null&&userRoleId!=null){
            request.getSession().setAttribute("userRoleId",userRoleId);
        }

        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {

            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
//        request.setAttribute("userId", );
        chain.doFilter(request, response);
    }
}
