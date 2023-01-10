package com.project.application.service;

import com.project.application.domain.Author;
import com.project.application.domain.Tag;

import java.util.List;

public interface AuthorService {

    public void saveAuthor(Author author);

    public Author findByEmail(String email);

    List<Author> getAuthors();

    List<Tag> addTagWatched(String name, Tag watchTag);
}
