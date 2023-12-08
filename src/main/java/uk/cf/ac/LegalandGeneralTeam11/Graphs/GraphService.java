package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface GraphService {
    public Float getAverageScore(String formid, String category);
    public List<Map<String, Object>> getCategoryAverages(String formid);
    public Map<String, List<String>> getFormTextAnswer(String formid);
}
