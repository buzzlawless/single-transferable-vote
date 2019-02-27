package edu.utexas.stv.creation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BallotsParser {

    public static void addBallotsToRaces(final String filename, final List<Race> races) {
        Reader in = null;
        try {
            in = new FileReader(filename);
        } catch (final FileNotFoundException e) {
            System.out.println("Could not find " + filename);
            e.printStackTrace();
            System.exit(1);
        }
        Iterator<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.parse(in).iterator();
        } catch (final IOException e) {
            System.out.println(filename + " not properly formatted to RFC4180 CSV");
            e.printStackTrace();
            System.exit(1);
        }
        while (records.hasNext() && !records.next().get(0).contains("SubmissionId")) {
            // Skip CSVRecords that are just metadata, not ballots
        }
        // Parse CSVRecords containing ballots. Add those ballots to races.
        final Map<String, Candidate> nameToCandidates = getNameToCandidateMap(races);
        while (records.hasNext()) {
            parseRecordForBallots(records.next(), races, nameToCandidates);
        }
    }

    private static void parseRecordForBallots(final CSVRecord record, final List<Race> races,
                                              final Map<String, Candidate> nameToCandidate) {
        int offset = 1;
        for (final Race race : races) {
            getBallotFromRecord(race, record, offset, nameToCandidate).ifPresent(race::addBallot);
            offset += race.getCandidates().size();
        }
    }

    private static Optional<Ballot> getBallotFromRecord(final Race race, final CSVRecord record, final int offset,
                                                        final Map<String, Candidate> nameToCandidate) {
        final Set<Candidate> ranking = new LinkedHashSet<>(race.getCandidates().size());
        for (int i = 0; i < race.getCandidates().size(); i++) {
            final String candidateName = record.get(i + offset);
            if ("".equals(candidateName)) {
                continue;
            }
            if (!nameToCandidate.containsKey(candidateName)) {
                System.out.println(String.format("Race for %s does not have candidate %s in the running",
                        race.getPosition(), candidateName));
                System.exit(1);
            }
            ranking.add(nameToCandidate.get(candidateName));
        }
        if (ranking.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Ballot(new ArrayDeque<>(ranking)));
    }

    private static Map<String, Candidate> getNameToCandidateMap(final List<Race> races) {
        final Map<String, Candidate> nameToCandidate = new HashMap<>();
        for (final Race r : races) {
            for (final Candidate c : r.getCandidates()) {
                nameToCandidate.put(c.getName(), c);
            }
        }
        return nameToCandidate;
    }
}
