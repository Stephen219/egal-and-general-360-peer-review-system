package uk.cf.ac.LegalandGeneralTeam11.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements QuestionServiceInter {
     @Autowired
        private QuestionRepoInter QuestionRepoInter;

        public List<Question> getAllQuestions() {
            return QuestionRepoInter.getAllQuestions();
        }
        public List<Question> getTextAreaQuestions() {
            return QuestionRepoInter.getTextAreaQuestions();
        }
}
