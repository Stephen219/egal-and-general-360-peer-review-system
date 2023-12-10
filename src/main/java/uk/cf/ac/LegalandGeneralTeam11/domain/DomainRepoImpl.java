package uk.cf.ac.LegalandGeneralTeam11.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DomainRepoImpl implements DomainRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;
    private RowMapper<Domain> domainMapper;

    public DomainRepoImpl() {
        setDomainMapper();
    }

    private void setDomainMapper() {

        domainMapper = (rs, i) -> new Domain(
                rs.getLong("id"),
                rs.getString("domain_name"),
                rs.getBoolean("enabled"));
    }

    /**
     * Get all domains
     * @return list of form requests
     */


    public List<Domain> getAllDomains() {
        String sql = "SELECT * FROM domains";
        return jdbcTemplate.query(sql, domainMapper);
    }

    public void  addDomain(Domain domain) {
        String sql = "INSERT INTO domains (domain_name, enabled) VALUES (?, ?)";
        jdbcTemplate.update(sql, domain.getDomain(),domain.getEnabled());
    }
    public void updateDomain(Domain domain) {
        String sql = "UPDATE domains SET domain = ?, enabled = ? WHERE id = ?";
        jdbcTemplate.update(sql, domain.getDomain(), domain.getEnabled(),domain.getId());
    }

    public void deleteDomain(Long id) {
        String sql = "DELETE FROM domains WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Domain getDomainById(Long id) {
        String sql = "SELECT * FROM domains WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, domainMapper, id);
    }

}
