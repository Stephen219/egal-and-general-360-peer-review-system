package uk.cf.ac.LegalandGeneralTeam11.SelfAssessment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SelfAssessController {
    @GetMapping("/self-assessment")
    public ModelAndView getSelfAssessment(){
        ModelAndView modelAndView = new ModelAndView("forms/360form");
        return modelAndView;
    }
    @PostMapping("/selfAssessment")
    public ModelAndView submitSelfAssessment(SelfAssessment selfAssessment,@RequestParam("oneOne") Integer oneOne,@RequestParam("oneTwo") Integer oneTwo,@RequestParam("oneThree") Integer oneThree,@RequestParam("oneFour") Integer oneFour,
                                             @RequestParam("twoOne") Integer twoOne,@RequestParam("twoTwo") Integer twoTwo,@RequestParam("twoThree") Integer twoThree,@RequestParam("twoFour") Integer twoFour,
                                             @RequestParam("threeOne") Integer threeOne,@RequestParam("threeTwo") Integer threeTwo, @RequestParam("threeThree") Integer threeThree,@RequestParam("threeFour") Integer threeFour,
                                             @RequestParam("fourOne") Integer fourOne,@RequestParam("fourTwo") Integer fourTwo, @RequestParam("fourThree") Integer fourThree,@RequestParam("fourFour") Integer fourFour,
                                             @RequestParam("fiveOne") Integer fiveOne,@RequestParam("fiveTwo") Integer fiveTwo,@RequestParam("fiveThree") Integer fiveThree,@RequestParam("fiveFour") Integer fiveFour,@RequestParam("fiveFive") Integer fiveFive,
                                             @RequestParam("sixOne") Integer sixOne,@RequestParam("sixTwo") Integer sixTwo,@RequestParam("sixThree") Integer sixThree,@RequestParam("sixFour") Integer sixFour){
        System.out.println(selfAssessment);
        System.out.println(selfAssessment.getOneOne());
        System.out.println(oneOne);

        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }

}
