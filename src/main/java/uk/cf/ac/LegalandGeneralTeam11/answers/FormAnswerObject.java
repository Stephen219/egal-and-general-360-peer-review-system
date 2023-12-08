package uk.cf.ac.LegalandGeneralTeam11.answers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor


public class FormAnswerObject {

    private final String category;
    private final String question;
    private final String answer;
    private final String responder;

}
