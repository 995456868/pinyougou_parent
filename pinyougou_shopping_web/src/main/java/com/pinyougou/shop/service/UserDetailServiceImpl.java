package com.pinyougou.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.manager.service.SellerService;
import com.pinyougou.pojo.TbSeller;

public class UserDetailServiceImpl implements UserDetailsService {
	
	private SellerService sellerService;
	
	public void setSellerService(SellerService sellerService){
		this.sellerService = sellerService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>(); 
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER")); 
		
        //从数据库获取的、username=sellerId——>seller
        TbSeller seller = sellerService.findOne(username);
        if(seller != null){
        	if("1".equals(seller.getStatus())){
        		return new User(username, seller.getPassword(), grantedAuths);
        	}
        }
		return null;
	}

}
