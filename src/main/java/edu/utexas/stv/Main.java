package edu.utexas.stv;

import edu.utexas.stv.computation.ElectionCalculator;
import edu.utexas.stv.election.Race;

import java.util.List;

import static edu.utexas.stv.creation.BallotsParser.addBallotsToRaces;
import static edu.utexas.stv.creation.ElectionInfoParser.parseElectionInfo;

public class Main {

    public static void main(final String[] args) {
        if (args.length < 2) {
            System.out.println("Too few arguments");
            System.exit(1);
        }
        final String electionInfoFilename = args[0];
        final String ballotsFilename = args[1];
        final List<Race> races = parseElectionInfo(electionInfoFilename);
        addBallotsToRaces(ballotsFilename, races);
        for (final Race r : races) {
            new ElectionCalculator(r).calculateWinners();
        }
    }
}
