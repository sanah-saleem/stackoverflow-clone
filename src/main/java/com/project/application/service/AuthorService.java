package com.project.application.service;

import com.project.application.domain.Author;

public interface AuthorService {

    public void saveAuthor(Author author);

    public Author findByEmail(String email);

}
