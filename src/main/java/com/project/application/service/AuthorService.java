package com.project.application.service;

import com.project.application.domain.Author;

import java.util.List;

public interface AuthorService {

    public void saveAuthor(Author author);

    public Author findByEmail(String email);

    List<Author> getAuthors();
}
