package com.project.application.service;

import com.project.application.domain.Author;
import com.project.application.domain.Tag;
import com.project.application.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImplementation implements AuthorService{
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void saveAuthor(Author author) {
        if(!authorRepository.findAll().contains(author)) {
            author.setPassword(this.bCryptPasswordEncoder.encode(author.getPassword()));
            authorRepository.save(author);
        }
    }

    @Override
    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Tag> addTagWatched(String email, Tag watchTag) {
        Author user = authorRepository.findByEmail(email);
        List<Tag> tags = user.getTagsWatched();
        if(tags.contains(watchTag)){
            tags.remove(watchTag);
        }
        else{
            tags.add(watchTag);
        }
        return tags;
    }
}
