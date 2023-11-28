package uk.cf.ac.LegalandGeneralTeam11.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Form {
    String id;
    private String username;
    private LocalDate formDate;
    private String progressStatus;

}
