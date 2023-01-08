package com.project.application.service;

import com.project.application.domain.Author;
import com.project.application.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomAuthorDetailService implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = this.authorRepository.findByEmail(username);
        if(author == null) {
            throw new UsernameNotFoundException("No User Found");
        }
        return new CustomAuthorDetail(author);
    }
}
