package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GraphServiceImpl implements GraphService{

    @Autowired
    GraphRepo graphRepo;
    public Float getAverageScore(String formid, String category) {

        return graphRepo.getAverageScore(formid, category);
    }


    public List<Map<String, Object>> getCategoryAverages(String formid){
        return graphRepo.getCategoryAverages(formid);
    }

    public Map<String, List<String>> getFormTextAnswer(String formid){
        return graphRepo.getFormTextAnswer(formid);
    }

    /**
     * This method returns the data for the chart which has the average score for each category grouped by the relationship
     *   for now , it does not include self
     * @param formId
     * @return
     */


    public List<Map<String, Object>> getChartData(String formId){
        return graphRepo.getChartData(formId);
    }

    public List<Map<String, Object>> getAverageAnswersForUser(String username){
        return graphRepo.getAverageAnswersForUser(username);
    }
}
