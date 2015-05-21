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
package org.broadleafcommerce.openadmin.server.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@SuppressWarnings("deprecation")
public class PunchoutUserDetailServiceImpl implements
    AuthenticationUserDetailsService<Authentication> {

@Override
public UserDetails loadUserDetails(Authentication token)
        throws UsernameNotFoundException {
    UserDetails userDetails = null;

            String[] credentials = (String[]) token.getPrincipal();
    boolean principal = Boolean.valueOf(token.getCredentials().toString());

    if (credentials != null && principal == true) {
        String name = credentials[0];
        if ("admin".equalsIgnoreCase(name)) {
            userDetails = getAdminUser(name);
        } else if ("händler".equalsIgnoreCase(name)) {
            userDetails = getRetailerUser(name);
        } else if ("user".equalsIgnoreCase(name)) {
            userDetails = getUserUser(name);
        }
    }

    if (userDetails == null) {
        throw new UsernameNotFoundException("Could not load user : "
                + token.getName());
    }

    return userDetails;
}

private UserDetails getAdminUser(String username) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_RETAILER"));
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
    return new User(username, "notused", true, true, true, true,
            grantedAuthorities);
}

private UserDetails getRetailerUser(String username) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_RETAILER"));
    return new User(username, "notused", true, true, true, true,
            grantedAuthorities);
}

private UserDetails getUserUser(String username) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
    return new User(username, "notused", true, true, true, true,
            grantedAuthorities);
}
}
