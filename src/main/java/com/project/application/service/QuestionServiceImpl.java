package com.project.application.service;

import com.project.application.domain.Author;
import com.project.application.domain.Question;
import com.project.application.domain.Tag;
import com.project.application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
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
        List<String> allTagNames = Arrays.asList(tagName.split(","));
        Set<String> uniqueTagNames=new HashSet<>(allTagNames);
        List<String> tagNames=new ArrayList<>(uniqueTagNames);
//        System.out.println(tagNames);
        List<Tag> tags = tagService.saveTag(tagNames);
//        System.out.println(tags);
        question.setTags(tags);

        Author author = authorService.findByEmail(email);
        author.addQuestion(question);
        questionRepository.save(question);

        System.out.println(author.getEmail());
    }

    @Override
    @Transactional
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
    public void addUpVote(long questionId, String email) {

        Question theQuestion = getQuestionById(questionId);

        System.out.println("Question score" + theQuestion.getScore() + "------------------------------------------------");

        Author currentAuthor = authorService.findByEmail(email);
        if(theQuestion.getDownVotes().contains(currentAuthor)) {
            removeDownVote(questionId, email);
        }
        theQuestion.addUpVote(currentAuthor);

        theQuestion.setScore(getUpdateScore(theQuestion));
        questionRepository.save(theQuestion);
    }

    @Override
    public void removeUpVote(long questionId, String email) {
        Question theQuestion = getQuestionById(questionId);
        System.out.println("Question score" + theQuestion.getScore() + "------------------------------------------------");

        Author currentAuthor = authorService.findByEmail(email);
        theQuestion.removeUpVote(currentAuthor);

        theQuestion.setScore(getUpdateScore(theQuestion));
        questionRepository.save(theQuestion);
    }

    @Override
    public void addDownVote(long questionId, String email) {
        Question theQuestion = getQuestionById(questionId);

        System.out.println("Question score" + theQuestion.getScore() + "------------------------------------------------");

        Author currentAuthor = authorService.findByEmail(email);
        if(theQuestion.getUpVotes().contains(currentAuthor)) {
            removeUpVote(questionId, email);
        }
        theQuestion.addDownVote(currentAuthor);

        theQuestion.setScore(getUpdateScore(theQuestion));
        questionRepository.save(theQuestion);
    }

    @Override
    public void removeDownVote(long questionId, String email) {
        Question theQuestion = getQuestionById(questionId);
        System.out.println("Question score" + theQuestion.getScore() + "------------------------------------------------");

        Author currentAuthor = authorService.findByEmail(email);
        theQuestion.removeDownVote(currentAuthor);

        theQuestion.setScore(getUpdateScore(theQuestion));
        questionRepository.save(theQuestion);
    }

    //not needed
    @Override
    public Set<Question> getFilteredQuestions(String searchKey, String filterByTags, boolean filterByNoAnswer, boolean noAcceptedAnswer) {

        Set<Question> resultedQuestions = null;
        if(searchKey != null){

//           List<Question> resultedQuestionsWithSearchKey = questionRepository.findAllWithSearchKey(searchKey);
//           resultedQuestions.addAll(questionRepository.findAllQuestionsWithSearchKey(searchKey));
        }

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
    public Page<Question> findPaginatedQuestions(int pageNo, int pageSize, String filters, String sortField, String tags, String tagMode) {


        switch (sortField){
            case "RecentActivity":
                sortField = "updatedAt";
                break;
            case "HighestScore":
                sortField = "score";
                break;
            default:
                sortField = "createdAt";

        }

        Sort sort = Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        if(tagMode.equals("Watched")){
            Author author = authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            List<Tag> watchedTags= author.getTagsWatched();
            System.out.println("-----------------" + watchedTags + "-------------------------");
            for(Tag tag : watchedTags){
                tags += tag.getName() + ",";
                System.out.println("-----------------" + tags + "-------------------------");
            }
            if(tags.length()>0)
                tags = tags.substring(0, tags.length()-1);
        }

            if (tags == "") {
                tags = null;
            }
            if (filters == "") {
                filters = null;
            }
            if (tags != null && filters != null) {
                List<String> tagList = Arrays.asList(tags.split(","));
                if (filters.contains("NoAnswers")) {
                    return questionRepository.findQuestionsWithNoAnswersWithTags(tagList, pageable);
                } else {
                    return questionRepository.findQuestionsWithNoAcceptedAnswersWithTags(tagList, pageable);
                }
            } else if (tags != null) {
                List<String> tagList = Arrays.asList(tags.split(","));
                return questionRepository.findQuestionsWithTags(tagList, pageable);
            } else if (filters != null) {

                if (filters.contains("NoAnswers")) {
                    return questionRepository.findQuestionsWithNoAnswers(pageable);
                } else {
                    return questionRepository.findAllQuestionsWithNoAcceptedAnswers(pageable);
                }
            }


        return this.questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> getPaginatedSearchRelatedQuestions(int pageNo, int pageSize, String searchKey) {

        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return questionRepository.findAllQuestionsWithSearchKey(searchKey, pageable);
    }


    public long getUpdateScore(Question question){

        long updatedScore = 0;

        if(!(question.getUpVotes() == null && question.getDownVotes() == null)){

            if(question.getUpVotes() != null && question.getDownVotes() == null){
                updatedScore = question.getUpVotes().size();
            }
            else if (question.getUpVotes() == null && question.getDownVotes() != null){
                updatedScore = 0 - question.getDownVotes().size();
            }
            else {
                updatedScore = question.getUpVotes().size() - question.getDownVotes().size();
            }
        }
        return updatedScore;
    }

}
