package uk.cf.ac.LegalandGeneralTeam11.account.FormRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class FormRequest {

    private Long formId;
    private Long userId;
    private String formName;
    private String approvalStatus = "pending";




}




