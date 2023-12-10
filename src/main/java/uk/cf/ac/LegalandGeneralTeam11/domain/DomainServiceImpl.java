package uk.cf.ac.LegalandGeneralTeam11.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DomainServiceImpl implements DomainService{
    @Autowired
    private DomainRepoImpl domainRepoImpl;

    public void addDomain(Domain domain) {
        domainRepoImpl.addDomain(domain);
    }

    public void updateDomain(Domain domain) {
        domainRepoImpl.updateDomain(domain);
    }

    public void disableDomain(Long Id) {
        domainRepoImpl.disableDomain(Id);
    }

    public List<Domain> getAllDomains() {
        List<Domain> domains = domainRepoImpl.getAllDomains();
        if (domains == null) {
            domains = Collections.emptyList(); // Return an empty list if null
        }
        return domains;
    }
}
