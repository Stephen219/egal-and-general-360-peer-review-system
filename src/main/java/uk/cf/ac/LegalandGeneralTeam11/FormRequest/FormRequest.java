package uk.cf.ac.LegalandGeneralTeam11.FormRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class FormRequest {

    private Long Id;
    private String username;

    private Date requestDate = new Date();
    private Date updateDate;
    private String approvalStatus = "pending";


    public FormRequest(String username) {
        this.username = username;
    }




}




