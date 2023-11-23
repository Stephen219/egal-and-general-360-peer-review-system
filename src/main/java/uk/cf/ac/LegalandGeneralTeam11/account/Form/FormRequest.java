package uk.cf.ac.LegalandGeneralTeam11.account.Form;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class FormRequest {
    private Long formId;
    private Long userId;
    private String formName;
    private String approvalStatus = "pending";
}
