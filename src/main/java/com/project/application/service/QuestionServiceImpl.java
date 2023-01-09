package com.project.application.service;

import com.project.application.domain.Author;
import com.project.application.domain.Question;
import com.project.application.domain.Tag;
import com.project.application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private AuthorService authorService;


    @Override
    public List<Question> getAllQuestons(){

        return questionRepository.findAll();
    }
    @Override
    public void saveQuestion(Question question, String tagName, String email){

        System.out.println(tagName);
        List<String> tagNames = Arrays.asList(tagName.split(","));
        System.out.println(tagNames);
        List<Tag> tags = tagService.saveTag(tagNames);
        System.out.println(tags);
        question.setTags(tags);
        Author author = authorService.findByEmail(email);
        author.addQuestion(question);
        questionRepository.save(question);


        System.out.println(author.getEmail());
    }

    @Override
    public Question getQuestionById(long questionId){

        return questionRepository.findById(questionId).get();
    }

    @Override
    public void updateQuestion(Question question, long questionId){

        question.setId(questionId);
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(long questionId){

//        Question question = questionRepository.findById(questionId).get();
        questionRepository.deleteById(questionId);
    }

    @Override
    public Set<Question> getFilteredQuestions(String filterByTags, boolean filterByNoAnswer, boolean noAcceptedAnswer) {

        Set<Question> resultedQuestions = null;


        if(filterByNoAnswer){

//            List<Question> questionsWithNoAnswer = questionRepository.findAllQuestionsWithNoAnswer();
//            resultedQuestions.addAll(questionRepository.findAllQuestionsWithNoAnswer());
        }

        if(filterByTags != null){

//            resultedQuestions.addAll(questionRepository.findAllQuestionsWithTags(filterByTags));
        }
        return resultedQuestions;
    }

    @Override
    public List<Question> getSearchRelatedQuestions(String searchKey) {
        return questionRepository.findAllQuestionsWithSearchKey(searchKey);
    }


}
