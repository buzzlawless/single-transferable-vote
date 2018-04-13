package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;

import java.util.List;

public class VoteCounter {

    public static int countFirstChoiceVotes(List<Ballot> ballots) {
        int total = 0;
        for (Ballot b : ballots) {
            b.getNextPreferred().addVotes(b);
            total++;
        }
        return total;
    }
}
