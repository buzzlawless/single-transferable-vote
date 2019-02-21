package edu.utexas.stv.creation;

import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectionInfoParser {

    public static List<Race> parseElectionInfo(final String pathname) {
        final File file = new File(pathname);
        String jsonStr = null;
        try {
            jsonStr = FileUtils.readFileToString(file);
        } catch (final IOException e) {
            System.out.println("Failed to read " + pathname);
            e.printStackTrace();
            System.exit(1);
        }
        final JSONArray electionInfo = new JSONArray(jsonStr);
        final List<Race> races = new ArrayList<>();
        for (int i = 0; i < electionInfo.length(); i++) {
            final JSONObject raceInfo = electionInfo.getJSONObject(i);
            final JSONArray candidateNames = raceInfo.getJSONArray("Candidates");
            final Set<Candidate> candidates = new HashSet<>();
            for (int j = 0; j < candidateNames.length(); j++) {
                candidates.add(new Candidate(candidateNames.getString(j)));
            }
            races.add(new Race(raceInfo.getString("Position"), raceInfo.getInt("Seats"), candidates));
        }
        return races;
    }
}
