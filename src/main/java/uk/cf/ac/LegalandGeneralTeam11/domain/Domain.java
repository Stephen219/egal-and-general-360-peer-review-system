package uk.cf.ac.LegalandGeneralTeam11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Domain {
    private Long id;
    private String domain;
    private Boolean enabled;

    public Domain(String domain) {
        this.domain = domain;
    }
}
