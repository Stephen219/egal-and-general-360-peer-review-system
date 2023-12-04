package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AnswerServiceInter {
    @Autowired
    public void processAndSaveAnswers(List<Answer> answerList);
}
