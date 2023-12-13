package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnswerService implements AnswerServiceInter{
    private AnswerRepo answerRepo;
    @Autowired

    public AnswerService(AnswerRepo answersRepository) {
        this.answerRepo = answersRepository;
    }

    public void processAndSaveAnswers(List<Answer> answerList) {
        for (Answer answer : answerList) {

            answerRepo.saveAnswer(answer);
        }
    }

    public Map<String, String> getFormAnswersForaUser(String responder, String formId) {
        return answerRepo.getFormAnswersForaUser(responder, formId);
    }


    public Map<String, List<String>> getQuestionAnswersUser(String responder, String formId) {
        return answerRepo.getQuestionAnswersUser(responder, formId);
    }

    public List<String> GetAllReviewers(String formId) {
        return answerRepo.GetAllReviewers(formId);
    }



}
