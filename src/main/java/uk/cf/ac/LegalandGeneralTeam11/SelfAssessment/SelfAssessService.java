package uk.cf.ac.LegalandGeneralTeam11.SelfAssessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class SelfAssessService {

    private final SelfAssessRepo selfAssessmentRepository;

    @Autowired
    public SelfAssessService(SelfAssessRepo selfAssessmentRepository) {
        this.selfAssessmentRepository = selfAssessmentRepository;
    }

    public void saveSelfAssessment(SelfAssessment selfAssessment) {
        selfAssessmentRepository.save(selfAssessment);
    }


}
