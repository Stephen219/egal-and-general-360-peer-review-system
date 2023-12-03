package uk.cf.ac.LegalandGeneralTeam11.answers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Long id;
    private String answer;
    private Long questionId;
    private String formId;
    private String username;

}
