package uk.cf.ac.LegalandGeneralTeam11.questions;



import java.util.List;

public interface QuestionRepoInter {

    public List<Question> getAllQuestions();

    public List<Question> getTextAreaQuestions();

    public List<String> getAllCategories();
    public void addQuestion(Question question);
    public void deleteQuestion(Long Id);
}
