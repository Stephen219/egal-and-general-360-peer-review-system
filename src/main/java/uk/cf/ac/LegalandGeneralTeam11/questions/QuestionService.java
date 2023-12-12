package uk.cf.ac.LegalandGeneralTeam11.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;

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
        public List<String> getAllCategories(){
            return QuestionRepoInter.getAllCategories();
        }



}
