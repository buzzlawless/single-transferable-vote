package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static edu.utexas.stv.computation.ElectionCalculator.mc;
import static edu.utexas.stv.computation.VoteTransferer.transferVotes;

public class CandidateEliminator {

    public static void eliminateLastPlace(List<Candidate> candidates, int rounds) {
        int roundsAgo = 0;
        List<Candidate> lastPlaceList = getLastPlace(candidates, roundsAgo);
        while (lastPlaceList.size() > 1) {
            //Tied for last
            if (roundsAgo >= rounds) {
                eliminateRandomCandidate(lastPlaceList);
                return;
            }
            roundsAgo++;
            lastPlaceList = getLastPlace(lastPlaceList, roundsAgo);
        }
        eliminateCandidate(lastPlaceList.get(0));
    }

    private static List<Candidate> getLastPlace(List<Candidate> candidates, int roundsAgo) {
        BigDecimal fewestVotes = new BigDecimal(Integer.MAX_VALUE, mc);
        List<Candidate> lastPlaceList = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.isRunning()) {
                BigDecimal candidateVoteTotal = c.getVoteTotal(roundsAgo);
                if (candidateVoteTotal.compareTo(fewestVotes) == 0) {
                    //Tie for fewest votes
                    lastPlaceList.add(c);
                } else if (candidateVoteTotal.compareTo(fewestVotes) < 0) {
                    //New fewest votes
                    fewestVotes = candidateVoteTotal;
                    lastPlaceList.clear();
                    lastPlaceList.add(c);
                }
            }
        }
        return lastPlaceList;
    }

    private static void eliminateRandomCandidate(List<Candidate> lastPlaceList) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, lastPlaceList.size());
        eliminateCandidate(lastPlaceList.get(randomIndex));
    }

    private static void eliminateCandidate(Candidate toEliminate) {
        toEliminate.notRunning();
        for (Ballot b : toEliminate.getVotes()) {
            transferVotes(toEliminate, b);
        }
    }
}
