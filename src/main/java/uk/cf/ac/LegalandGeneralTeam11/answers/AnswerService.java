package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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





}
