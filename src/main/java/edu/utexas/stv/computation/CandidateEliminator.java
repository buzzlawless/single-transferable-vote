package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static edu.utexas.stv.computation.VoteTransferer.transferVotes;

class CandidateEliminator {

    static void eliminateLastPlace(final Set<Candidate> candidates) {
        eliminateCandidate(getLastPlace(candidates), candidates);
    }

    private static Candidate getLastPlace(final Set<Candidate> candidates) {
        Candidate lastPlace = Collections.min(candidates);
        final List<Candidate> lastPlaceList = new ArrayList<>();
        for (final Candidate c : candidates) {
            if (c.compareTo(lastPlace) == 0) {
                lastPlaceList.add(c);
            }
        }
        if (lastPlaceList.size() > 1) {
            final int randomIndex = ThreadLocalRandom.current().nextInt(lastPlaceList.size());
            lastPlace = lastPlaceList.get(randomIndex);
        }
        return lastPlace;
    }

    private static void eliminateCandidate(final Candidate toEliminate, final Set<Candidate> candidates) {
        System.out.println(String.format("Eliminated %s for being in last place. Transferring votes to next preferred.",
                toEliminate.getName()));
        candidates.remove(toEliminate);
        for (final Ballot b : toEliminate.getVotes()) {
            transferVotes(toEliminate, b, candidates);
        }
    }

}
