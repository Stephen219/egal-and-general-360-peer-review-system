package uk.cf.ac.LegalandGeneralTeam11.questions;

import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;

import java.util.List;

public interface QuestionServiceInter {

        public List<Question> getAllQuestions();

        public List<Question> getTextAreaQuestions();
        public List<String> getAllCategories();
        public void addQuestion(Question question);
        public void deleteQuestion(Long Id);
}
