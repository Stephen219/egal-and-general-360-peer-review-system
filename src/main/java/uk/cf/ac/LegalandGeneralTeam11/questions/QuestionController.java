package uk.cf.ac.LegalandGeneralTeam11.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;

import java.util.List;
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/admin/questions")
    public ModelAndView getQuestions() {
        ModelAndView modelAndView = new ModelAndView("Questions/formQuestion");
        List<Question> Questions = questionService.getAllQuestions();
        modelAndView.addObject("Questions", Questions);
        return modelAndView;
    }
    @GetMapping("/admin/questions/add")
    public ModelAndView addQuestion() {
        ModelAndView modelAndView = new ModelAndView("Questions/addQuestion");
        List<Question> Questions = questionService.getAllQuestions();
        List<String> Categories = questionService.getAllCategories();
        modelAndView.addObject("Categories", Categories);
        modelAndView.addObject("Questions", Questions);
        modelAndView.addObject("Question", new Question());
        return modelAndView;
    }
    @PostMapping("/addQuestion")
    public String addDomain(@ModelAttribute Question question) {

        questionService.addQuestion(question);
        return "redirect:/admin/questions";
    }
    @PostMapping ("/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/admin/questions";
    }
}
