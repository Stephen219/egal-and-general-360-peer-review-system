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
}
