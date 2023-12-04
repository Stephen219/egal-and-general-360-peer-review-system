package uk.cf.ac.LegalandGeneralTeam11.questions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Long id;

    private String content;

    private String category;

}
