package edu.utexas.stv.creation;

import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionInfoParser {

    public static List<Race> parseElectionInfo(String pathname) {
        File file = new File(pathname);
        String jsonStr = null;
        try {
            jsonStr = FileUtils.readFileToString(file);
        } catch (IOException e) {
            System.out.println("Failed to read " + pathname);
            e.printStackTrace();
            System.exit(1);
        }
        JSONArray electionInfo = new JSONArray(jsonStr);
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < electionInfo.length(); i++) {
            JSONObject raceInfo = electionInfo.getJSONObject(i);
            JSONArray candidateNames = raceInfo.getJSONArray("Candidates");
            List<Candidate> candidates = new ArrayList<>();
            for (int j = 0; j < candidateNames.length(); j++) {
                candidates.add(new Candidate(candidateNames.getString(j)));
            }
            races.add(new Race(raceInfo.getString("Position"), raceInfo.getInt("Seats"), candidates));
        }
        return races;
    }
}
