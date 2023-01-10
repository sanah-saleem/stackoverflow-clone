package com.project.application.service;

import com.project.application.domain.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> saveTag(List<String> tagNames);

    List<Tag> getAllTags(int questionId);

    List<Tag> getAllTags();

    public boolean checkTag(String tagName);
}
