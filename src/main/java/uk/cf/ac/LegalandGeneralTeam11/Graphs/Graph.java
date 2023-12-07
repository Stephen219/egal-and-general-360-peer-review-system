package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Graph {
private String username;
private String category;
private List<Float> average;
}
