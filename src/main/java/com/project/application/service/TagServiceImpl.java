package com.project.application.service;

import com.project.application.domain.Tag;
import com.project.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<Tag> getAllTags(int questionId) {
        return tagRepository.findAll(questionId);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
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

    @Override
    public List<Tag> searchTagByname(String theSearchName) {
        return tagRepository.searchTags(theSearchName.toLowerCase());
    }

    @Override
    public List<Tag> sortTags(String sortValue) {
        if(sortValue.equals("name") ){
            return tagRepository.findAll(Sort.by(sortValue).descending());
        }
        else{
            return  tagRepository.findAll(Sort.by(sortValue).ascending());
        }
    }

    @Override
    public Tag findTagByName(String tagname) {
        return tagRepository.findByName(tagname);
    }
}
