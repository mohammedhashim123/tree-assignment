package com.example.tree.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tree.assignment.entity.User;
import com.example.tree.assignment.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOp = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (!userOp.isEmpty()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
					userOp.isEmpty() ? null : userOp.get().getRole());
			authorities.add(grantedAuthority);
			return userOp.isEmpty() ? null
					: new org.springframework.security.core.userdetails.User(userOp.get().getUsername(),
							userOp.get().getPassword(), authorities);
		}else {
			return null;
		}

	}

}
