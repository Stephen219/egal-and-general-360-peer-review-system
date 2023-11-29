package uk.cf.ac.LegalandGeneralTeam11.SelfAssessment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelfAssessment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String oneOne;
    private String oneTwo;
    private String oneThree;
    private String oneFour;
    private String twoOne;
    private String twoTwo;
    private String twoThree;
    private String twoFour;
    private String threeOne;
    private String threeTwo;
    private String threeThree;
    private String threeFour;
    private String fourOne;
    private String fourTwo;
    private String fourThree;
    private String fourFour;
    private String fiveOne;
    private String fiveTwo;
    private String fiveThree;
    private String fiveFour;
    private String fiveFive;
    private String sixOne;
    private String sixTwo;
    private String sixThree;
    private String sixFour;
    private String superpower;
    private String impact;
    private String firstName;
    private String lastName;



    public SelfAssessment(String firstName, String lastName, String impact, String superpower) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.impact = impact;
        this.superpower = superpower;
    }


}
