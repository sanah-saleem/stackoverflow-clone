package com.project.application.service;

import com.project.application.domain.Answer;
import com.project.application.domain.Author;
import com.project.application.domain.Question;
import com.project.application.repository.AnswerRepository;
import com.project.application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AuthorService authorService;


    @Override
    public void saveAnswer(Answer answer, long questionId, String email) {
        Question question=questionService.getQuestionById(questionId);
//        answerRepository.save(answer);
        question.addAnswer(answer);

        Author author = authorService.findByEmail(email);
        author.addAnswer(answer);

        answerRepository.save(answer);

    }

    @Override
    public void updateAnswer(Answer answer, long answerId) {
        answer.setId(answerId);
        answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(long answerId) {
        Answer answer=answerRepository.findById(answerId).get();
        return answer;
    }

    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answers=answerRepository.findAll();
        return answers;
    }

    @Override
    public void deleteAnswerById(long answerId) {
        answerRepository.deleteById(answerId);
    }


}
