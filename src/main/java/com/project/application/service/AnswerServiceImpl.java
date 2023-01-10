package com.project.application.service;

import com.project.application.domain.Answer;
import com.project.application.domain.Author;
import com.project.application.domain.Question;
import com.project.application.repository.AnswerRepository;
import com.project.application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
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
    public void deleteAnswerById(long answerId,long questionId) {

        Answer answer=answerRepository.findById(answerId).get();
        if(answer.isAccepted()){
           Question question=questionService.getQuestionById(questionId);
           question.setHasAcceptedAnswer(false);
           questionRepository.save(question);
        }
        answerRepository.deleteById(answerId);
    }
    @Override
    public void acceptAnswer(long answerId,long questionId){
        Question question=questionService.getQuestionById(questionId);
        if(question.getHasAcceptedAnswer()){
                Answer previousAcceptedAnswer = answerWhichGotAccepted(questionId);
                previousAcceptedAnswer.setAccepted(false);
                answerRepository.save(previousAcceptedAnswer);
                Answer answer=getAnswerById(answerId);
                answer.setAccepted(true);
                answerRepository.save(answer);
                questionRepository.save(question);
            }
        else{
                Answer answer = getAnswerById(answerId);
                answer.setAccepted(true);
                answerRepository.save(answer);
                question.setHasAcceptedAnswer(true);
                questionRepository.save(question);
        }
    }
    public Answer answerWhichGotAccepted(long questionId){
        Question question=questionService.getQuestionById(questionId);
        for(Answer answer : question.getAnswers()){
            System.out.println(answer.isAccepted());
            if(answer.isAccepted()){
                return answer;
            }
        }
        return null;
    }
}


