/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2015 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.broadleafcommerce.openadmin.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

public class AdminPunchoutFilter extends AbstractPreAuthenticatedProcessingFilter {

    protected static final Log LOG = LogFactory.getLog(AdminPunchoutFilter.class);

    protected List<String> excludedRequestPatterns;

    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService adminSecurityService;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        if (request.getParameterMap().size() == 2) {
            return true;
        }
        return true;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String[] credentials = new String[2];

        if (request.getRequestURI().contains("punchout")) {

            List<AdminUser> adminUserList = adminSecurityService.readAllAdminUsers();
            AdminUser adminUser = null;
            if (adminUserList == null || adminUserList.isEmpty()) {
                throw new UsernameNotFoundException("The user was not found");
            }
            for (AdminUser adminUserDetail : adminUserList) {
                adminUser = adminUserDetail;
                break;
            }

            credentials[0] = adminUser.getLogin();
            credentials[1] = adminUser.getPassword();
        } else {
            return null;
        }

        return credentials;
    }

  /*AnonymousAuthenticationFilter {

    protected static final Log LOG = LogFactory.getLog(AdminPunchoutFilter.class);

    protected List<String> excludedRequestPatterns;
    
    @Resource(name="blAdminSecurityService")
    protected AdminSecurityService adminSecurityService;
    
    

     @Override
     public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) baseRequest;
        HttpServletResponse response = (HttpServletResponse) baseResponse;
        if(request.getRequestURI().contains("punchout")){
       
                
                List<AdminUser> adminUserList = adminSecurityService.readAllAdminUsers();
                AdminUser adminUser = null;
                if (adminUserList == null || adminUserList.isEmpty()) {
                    throw new UsernameNotFoundException("The user was not found");
                }
                for (AdminUser adminUserDetail : adminUserList) {
                    adminUser = adminUserDetail;
                    break;
                }

  request.setAttribute("username", adminUser.getName());
  request.setAttribute("passowrd", adminUser.getPassword());
            chain.doFilter(request, response);
        }else{
            chain.doFilter(request, response);  
        }
    }*/
    

    
  /*  @Override
    protected Authentication createAuthentication(HttpServletRequest request) {
    if(request.getRequestURI().contains("punchout")){
        Authentication auth = super.createAuthentication(request);
        boolean excludedRequestFound = false;
        if (excludedRequestPatterns != null && excludedRequestPatterns.size() > 0) {
            for (String pattern : excludedRequestPatterns) {
                RequestMatcher matcher = new AntPathRequestMatcher(pattern);
                if (matcher.matches(request) && !request.getRequestURI().contains("punchout")) {
                    excludedRequestFound = true;
                    break;
                }
            }
        }

        if(!excludedRequestFound){
            
            List<AdminUser> adminUserList = adminSecurityService.readAllAdminUsers();
            AdminUser adminUser = null;
            if (adminUserList == null || adminUserList.isEmpty()) {
                throw new UsernameNotFoundException("The user was not found");
            }
            for (AdminUser adminUserDetail : adminUserList) {
                adminUser = adminUserDetail;
                break;
            }

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            GrantedAuthority authority = new GrantedAuthorityImpl("PERMISSION_OTHER_DEFAULT");
            authorities.add(authority);
            
            for (AdminRole role : adminUser.getAllRoles()) {
                for (AdminPermission permission : role.getAllPermissions()) {
                    if (permission.isFriendly()) {
                        for (AdminPermission childPermission : permission.getAllChildPermissions()) {
                            authorities.add(new SimpleGrantedAuthority(childPermission.getName()));
                        }
                    } else {
                        authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    }
                }
            }
            for (AdminPermission permission : adminUser.getAllPermissions()) {
                if (permission.isFriendly()) {
                    for (AdminPermission childPermission : permission.getAllChildPermissions()) {
                        authorities.add(new SimpleGrantedAuthority(childPermission.getName()));
                    }
                } else {
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                }
            }
            
            for (String perm : AdminSecurityService.DEFAULT_PERMISSIONS) {
                authorities.add(new GrantedAuthorityImpl(perm));
            }
            
            auth = new AnonymousAuthenticationToken("somekey", null, authorities);
            
       Cookie[] cookies = request.getCookies();
        for (int i=0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equalsIgnoreCase("JSESSIONIDADMIN")) {
                GrantedAuthority role = new GrantedAuthorityImpl("PERMISSION_OTHER_DEFAULT");
                List<GrantedAuthority> roles = new ArrayList();
                roles.add(role);
     
            }
        }
        }
        
        return auth;
    }
    return null;
    }*/



    public List<String> getExcludedRequestPatterns() {
        return excludedRequestPatterns;
    }

    /**
     * This allows you to declaratively set a list of excluded Request Patterns
     *
     * <bean id="blPunchoutFilter" class="org.broadleafcommerce.openadmin.web.filter.AdminPunchoutFilter" >
     *     <property name="excludedRequestPatterns">
     *         <list>
     *             <value>/punchout/*</value>
     *         </list>
     *     </property>
     * </bean>
     *
     **/
    public void setExcludedRequestPatterns(List<String> excludedRequestPatterns) {
        this.excludedRequestPatterns = excludedRequestPatterns;
    }
}
