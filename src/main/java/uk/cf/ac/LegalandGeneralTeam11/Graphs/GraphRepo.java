package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import java.util.List;
import java.util.Map;

public interface GraphRepo {
    public Float getAverageScore(String formid, String category);
    public List<Map<String, Object>> getCategoryAverages(String formid);
    public Map<String, List<String>> getFormTextAnswer(String formid);

    public List<Map<String, Object>> getChartData(String formId);
    public List<Map<String, Object>> getAverageAnswersForUser(String username);

    public List<Map<String, Object>> getRelationshipCounts();


}
