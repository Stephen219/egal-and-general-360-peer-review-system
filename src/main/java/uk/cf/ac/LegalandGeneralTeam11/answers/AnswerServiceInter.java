package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface AnswerServiceInter {
    @Autowired
    public void processAndSaveAnswers(List<Answer> answerList);
    public Map<String, String> getFormAnswersForaUser(String responder, String formId);
    public Map<String, List<String>> getQuestionAnswersUser(String responder, String formId);
}
