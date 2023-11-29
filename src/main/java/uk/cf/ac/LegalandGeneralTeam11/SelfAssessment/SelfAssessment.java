package uk.cf.ac.LegalandGeneralTeam11.SelfAssessment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "self_assessment")

public class SelfAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "self_assessment_SEQ")
    @SequenceGenerator(name = "self_assessment_SEQ", sequenceName = "self_assessment_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "responder_id")
    private String responder;
    @Column(name = "form_id")
    private String formId;
    @Column(name = "one_one")
    private String oneOne;
    @Column(name = "one_two")
    private String oneTwo;
    @Column(name = "one_three")
    private String oneThree;
    @Column(name = "one_four")
    private String oneFour;
    @Column(name = "two_one")
    private String twoOne;
    @Column(name = "two_two")
    private String twoTwo;
    @Column(name = "two_three")
    private String twoThree;
    @Column(name = "two_four")
    private String twoFour;
    @Column(name = "three_one")
    private String threeOne;
    @Column(name = "three_two")
    private String threeTwo;
    @Column(name = "three_three")
    private String threeThree;
    @Column(name = "three_four")
    private String threeFour;
    @Column(name = "four_one")
    private String fourOne;
    @Column(name = "four_two")
    private String fourTwo;
    @Column(name = "four_three")
    private String fourThree;
    @Column(name = "four_four")
    private String fourFour;
    @Column(name = "five_one")
    private String fiveOne;
    @Column(name = "five_two")
    private String fiveTwo;
    @Column(name = "five_three")
    private String fiveThree;
    @Column(name = "five_four")
    private String fiveFour;
    @Column(name = "five_five")
    private String fiveFive;
    @Column(name = "six_one")
    private String sixOne;
    @Column(name = "six_two")
    private String sixTwo;
    @Column(name = "six_three")
    private String sixThree;
    @Column(name = "six_four")
    private String sixFour;
    @Column(name = "superpower")
    private String superpower;
    @Column(name = "impact")
    private String impact;



    public SelfAssessment(String impact, String superpower) {
        this.impact = impact;
        this.superpower = superpower;
    }


}
