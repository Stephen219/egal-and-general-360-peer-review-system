package uk.cf.ac.LegalandGeneralTeam11.domain;

import java.util.List;

public interface DomainService {

        public void addDomain(Domain domain);
        public void updateDomain(Domain domain);
        public void disableDomain(Long Id);
        public List<Domain> getAllDomains();
}
