package com.project.application.service;

import com.project.application.domain.Tag;
import com.project.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> saveTag(List<String> tagNames) {
        List<Tag> tags = new ArrayList<Tag>();
        for(String tagName : tagNames) {
            Tag tag = new Tag();
            if(!checkTag(tagName)) {
                tag.setName(tagName);
                tags.add(tag);
            }
            else {
                tag = tagRepository.findByName(tagName);
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public String getAllTags(long questionId) {
        List<Tag> listTags = tagRepository.findAllByQuestionsId(questionId);
        String tagNames = "";
        for(Tag tag:listTags) {
            tagNames += tag.getName() + ",";
        }
        return tagNames.substring(0, tagNames.length()-1);
    }

    @Override
    public boolean checkTag(String tagName) {
        List<Tag> listTags = getAllTags();
        boolean check = false;
        for(Tag theTag : listTags) {
            if(theTag.getName().equals(tagName)) {
                check = true;
            }
        }
        System.out.println("check : " + check);
        return check;
    }
}
