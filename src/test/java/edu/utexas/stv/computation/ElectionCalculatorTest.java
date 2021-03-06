package edu.utexas.stv.computation;

import edu.utexas.stv.election.Ballot;
import edu.utexas.stv.election.Candidate;
import edu.utexas.stv.election.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ElectionCalculatorTest {

    private Candidate oranges;
    private Candidate pears;
    private Candidate chocolate;
    private Candidate strawberries;
    private Candidate sweets;
    private Set<Candidate> candidates;

    @BeforeEach
    void setup() {
        oranges = new Candidate("Oranges");
        pears = new Candidate("Pears");
        chocolate = new Candidate("Chocolate");
        strawberries = new Candidate("Strawberries");
        sweets = new Candidate("Sweets");
        candidates = new HashSet<>();
        candidates.add(oranges);
        candidates.add(pears);
        candidates.add(chocolate);
        candidates.add(strawberries);
        candidates.add(sweets);
    }

    @Test
    void calculateWinnersTest() {
//      O P C  St Sw
//      4 2 12 1  1 <-- C wins, distribute surplus
//      4 2 6  5  3 <-- P last, eliminate and distribute votes
//      6 0 6  5  3 <-- Sw last, eliminate
//      6 0 6  5  0 <-- Winners and final vote totals
        final Race r = new Race("WikipediaPartyFood", 3, candidates);

        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();
        final Queue<Candidate> ranking4 = new ArrayDeque<>();
        final Queue<Candidate> ranking5 = new ArrayDeque<>();
        final Queue<Candidate> ranking6 = new ArrayDeque<>();

        ranking1.add(oranges);
        ranking2.add(pears);
        ranking2.add(oranges);
        ranking3.add(chocolate);
        ranking3.add(strawberries);
        ranking4.add(chocolate);
        ranking4.add(sweets);
        ranking5.add(strawberries);
        ranking6.add(sweets);

        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));

        }
        for (int i = 0; i < 2; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));

        }
        for (int i = 0; i < 8; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));

        }
        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking4)));
        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking5)));
        r.addBallot(new Ballot(new ArrayDeque<>(ranking6)));

        final ElectionCalculator ec = new ElectionCalculator(r);
        assertThat(ec.calculateWinners()).containsExactlyInAnyOrder(chocolate, oranges, strawberries);
    }

    @Test
    void calculateWinnersTieTest() {
//      O P C  St Sw
//      4 2 12 1  1 <-- C wins, distribute surplus
//      4 2 6  5  3 <-- P last place, eliminate and distribute votes
//      4 0 6  6  4 <-- St wins. Sw last place (tied with O this round but had less prev round)
//      4 0 6  6  0 <-- Winners and final vote totals
        final Race r = new Race("WikipediaPartyFoodTie", 3, candidates);

        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();
        final Queue<Candidate> ranking4 = new ArrayDeque<>();
        final Queue<Candidate> ranking5 = new ArrayDeque<>();
        final Queue<Candidate> ranking6 = new ArrayDeque<>();
        final Queue<Candidate> ranking7 = new ArrayDeque<>();

        ranking1.add(oranges);
        ranking2.add(pears);
        ranking2.add(strawberries);
        ranking3.add(pears);
        ranking3.add(sweets);
        ranking4.add(chocolate);
        ranking4.add(strawberries);
        ranking5.add(chocolate);
        ranking5.add(sweets);
        ranking6.add(strawberries);
        ranking7.add(sweets);

        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));

        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));
        r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));
        for (int i = 0; i < 8; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking4)));

        }
        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking5)));

        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking6)));
        r.addBallot(new Ballot(new ArrayDeque<>(ranking7)));

        final ElectionCalculator ec = new ElectionCalculator(r);
        assertThat(ec.calculateWinners()).containsExactlyInAnyOrder(chocolate, strawberries, oranges);
    }

    @Test
    void calculateWinnersTieRandomTest() {
//      O   P C  St Sw
//      4   2 12 1  1 <-- C wins, distribute surplus
//      4   8 6  1  1 <-- P wins, distribute surplus
//      4.5 6 6  1  1 <-- St and Sw tied for last, eliminate random, then eliminate the other next round
//      4.5 6 6  0  0<-- Winners and final vote totals
        final Race r = new Race("WikipediaPartyFoodTieRandom", 3, candidates);
        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();
        final Queue<Candidate> ranking4 = new ArrayDeque<>();
        final Queue<Candidate> ranking5 = new ArrayDeque<>();

        ranking1.add(oranges);
        ranking2.add(pears);
        ranking2.add(oranges);
        ranking3.add(chocolate);
        ranking3.add(pears);
        ranking4.add(strawberries);
        ranking5.add(sweets);

        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));
        }
        for (int i = 0; i < 2; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));
        }
        for (int i = 0; i < 12; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));
        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking4)));
        r.addBallot(new Ballot(new ArrayDeque<>(ranking5)));

        final ElectionCalculator ec = new ElectionCalculator(r);
        assertThat(ec.calculateWinners()).containsExactlyInAnyOrder(chocolate, pears, oranges);
    }

    @Test
    void calculateWinnersTieRandomTest2() {
//      O   P C  St Sw
//      0   2 12 3  3 <-- C wins, distribute surplus
//      0   8 6  3  3 <-- P wins, distribute surplus
//      0.5 6 6  3  3 <-- O last, eliminate
//      0   6 6  3  3 <-- St and Sw tied from beginning, eliminate random
        final Race r = new Race("WikipediaPartyFoodTieRandom2", 3, candidates);

        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();
        final Queue<Candidate> ranking4 = new ArrayDeque<>();

        ranking1.add(pears);
        ranking1.add(oranges);
        ranking2.add(chocolate);
        ranking2.add(pears);
        ranking3.add(strawberries);
        ranking4.add(sweets);

        for (int i = 0; i < 2; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));
        }
        for (int i = 0; i < 12; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));
        }
        for (int i = 0; i < 3; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));
        }
        for (int i = 0; i < 3; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking4)));
        }

        final Set<Candidate> winners = new ElectionCalculator(r).calculateWinners();
        assertThat(winners).contains(chocolate, pears);
        assertThat(winners).isSubsetOf(chocolate, pears, strawberries, sweets);
        assertThat(winners).hasSize(3);
    }

    @Test
    void calculateWinnersSingleWinnerTest() {
//      O  P C  St Sw
//      9  1 10 0  0 <-- Sw, St eliminated. P last place, eliminated, distribute surplus
//      9  0 11 0  0 <-- C wins
        final Race r = new Race("Single Winner Race", 1, candidates);

        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();

        ranking1.add(chocolate);
        ranking2.add(oranges);
        ranking3.add(pears);
        ranking3.add(chocolate);

        for (int i = 0; i < 10; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));
        }
        for (int i = 0; i < 9; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));
        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));

        final ElectionCalculator ec = new ElectionCalculator(r);
        assertThat(ec.calculateWinners()).containsExactlyInAnyOrder(chocolate);
    }

    @Test
    void calculateWinnersMultipleSurplusTest() {
//      O P C St Sw
//      8 4 7 1  0 <-- O and C win, O surplus distributed first
//      6 6 7 1  0 <-- P wins
        final Race r = new Race("Multiple Surplus", 3, candidates);

        final Queue<Candidate> ranking1 = new ArrayDeque<>();
        final Queue<Candidate> ranking2 = new ArrayDeque<>();
        final Queue<Candidate> ranking3 = new ArrayDeque<>();
        final Queue<Candidate> ranking4 = new ArrayDeque<>();

        ranking1.add(chocolate);
        ranking1.add(strawberries);
        ranking2.add(oranges);
        ranking2.add(pears);
        ranking3.add(pears);
        ranking4.add(strawberries);

        for (int i = 0; i < 7; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking1)));
        }
        for (int i = 0; i < 8; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking2)));
        }
        for (int i = 0; i < 4; i++) {
            r.addBallot(new Ballot(new ArrayDeque<>(ranking3)));
        }
        r.addBallot(new Ballot(new ArrayDeque<>(ranking4)));

        final ElectionCalculator ec = new ElectionCalculator(r);
        assertThat(ec.calculateWinners()).containsExactlyInAnyOrder(oranges, chocolate, pears);
    }
}