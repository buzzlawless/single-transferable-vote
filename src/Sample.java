import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

//TODO builders for everything
public class Sample {

    public static void main(String[] args) {
        sample1();
        sample2();
        sample3();
        sample4();
    }

    private static void sample1() {
        Race r1 = new Race(3, "WikipediaPartyFoodExample");
        Candidate oranges = new Candidate("Oranges", r1);
        Candidate pears = new Candidate("Pears", r1);
        Candidate chocolate = new Candidate("Chocolate", r1);
        Candidate strawberries = new Candidate("Strawberries", r1);
        Candidate sweets = new Candidate("Sweets", r1);
        Queue<Candidate> ranking1 = new LinkedList<>();
        Queue<Candidate> ranking2 = new LinkedList<>();
        Queue<Candidate> ranking3 = new LinkedList<>();
        Queue<Candidate> ranking4 = new LinkedList<>();
        Queue<Candidate> ranking5 = new LinkedList<>();
        Queue<Candidate> ranking6 = new LinkedList<>();
        Queue<Candidate> ranking7 = new LinkedList<>();
        Queue<Candidate> ranking8 = new LinkedList<>();
        Queue<Candidate> ranking9 = new LinkedList<>();
        Queue<Candidate> ranking10 = new LinkedList<>();
        Queue<Candidate> ranking11 = new LinkedList<>();
        Queue<Candidate> ranking12 = new LinkedList<>();
        Queue<Candidate> ranking13 = new LinkedList<>();
        Queue<Candidate> ranking14 = new LinkedList<>();
        Queue<Candidate> ranking15 = new LinkedList<>();
        Queue<Candidate> ranking16 = new LinkedList<>();
        Queue<Candidate> ranking17 = new LinkedList<>();
        Queue<Candidate> ranking18 = new LinkedList<>();
        Queue<Candidate> ranking19 = new LinkedList<>();
        Queue<Candidate> ranking20 = new LinkedList<>();

        ranking1.add(oranges);
        ranking2.add(oranges);
        ranking3.add(oranges);
        ranking4.add(oranges);
        ranking5.add(pears);
        ranking5.add(oranges);
        ranking6.add(pears);
        ranking6.add(oranges);
        ranking7.add(chocolate);
        ranking7.add(strawberries);
        ranking8.add(chocolate);
        ranking8.add(strawberries);
        ranking9.add(chocolate);
        ranking9.add(strawberries);
        ranking10.add(chocolate);
        ranking10.add(strawberries);
        ranking11.add(chocolate);
        ranking11.add(strawberries);
        ranking12.add(chocolate);
        ranking12.add(strawberries);
        ranking13.add(chocolate);
        ranking13.add(strawberries);
        ranking14.add(chocolate);
        ranking14.add(strawberries);
        ranking15.add(chocolate);
        ranking15.add(sweets);
        ranking16.add(chocolate);
        ranking16.add(sweets);
        ranking17.add(chocolate);
        ranking17.add(sweets);
        ranking18.add(chocolate);
        ranking18.add(sweets);
        ranking19.add(strawberries);
        ranking20.add(sweets);

        Ballot b1 = new Ballot(ranking1, r1);
        Ballot b2 = new Ballot(ranking2, r1);
        Ballot b3 = new Ballot(ranking3, r1);
        Ballot b4 = new Ballot(ranking4, r1);
        Ballot b5 = new Ballot(ranking5, r1);
        Ballot b6 = new Ballot(ranking6, r1);
        Ballot b7 = new Ballot(ranking7, r1);
        Ballot b8 = new Ballot(ranking8, r1);
        Ballot b9 = new Ballot(ranking9, r1);
        Ballot b10 = new Ballot(ranking10, r1);
        Ballot b11 = new Ballot(ranking11, r1);
        Ballot b12 = new Ballot(ranking12, r1);
        Ballot b13 = new Ballot(ranking13, r1);
        Ballot b14 = new Ballot(ranking14, r1);
        Ballot b15 = new Ballot(ranking15, r1);
        Ballot b16 = new Ballot(ranking16, r1);
        Ballot b17 = new Ballot(ranking17, r1);
        Ballot b18 = new Ballot(ranking18, r1);
        Ballot b19 = new Ballot(ranking19, r1);
        Ballot b20 = new Ballot(ranking20, r1);


        VoteCounter vc = new VoteCounter(r1);
        vc.countVotes();
        r1.printWinners();
    }

    private static void sample2() {
        Race r1 = new Race(3, "WikiPartyFoodTie");
        Candidate oranges = new Candidate("Oranges", r1);
        Candidate pears = new Candidate("Pears", r1);
        Candidate chocolate = new Candidate("Chocolate", r1);
        Candidate strawberries = new Candidate("Strawberries", r1);
        Candidate sweets = new Candidate("Sweets", r1);
        Queue<Candidate> ranking1 = new LinkedList<>();
        Queue<Candidate> ranking2 = new LinkedList<>();
        Queue<Candidate> ranking3 = new LinkedList<>();
        Queue<Candidate> ranking4 = new LinkedList<>();
        Queue<Candidate> ranking5 = new LinkedList<>();
        Queue<Candidate> ranking6 = new LinkedList<>();
        Queue<Candidate> ranking7 = new LinkedList<>();
        Queue<Candidate> ranking8 = new LinkedList<>();
        Queue<Candidate> ranking9 = new LinkedList<>();
        Queue<Candidate> ranking10 = new LinkedList<>();
        Queue<Candidate> ranking11 = new LinkedList<>();
        Queue<Candidate> ranking12 = new LinkedList<>();
        Queue<Candidate> ranking13 = new LinkedList<>();
        Queue<Candidate> ranking14 = new LinkedList<>();
        Queue<Candidate> ranking15 = new LinkedList<>();
        Queue<Candidate> ranking16 = new LinkedList<>();
        Queue<Candidate> ranking17 = new LinkedList<>();
        Queue<Candidate> ranking18 = new LinkedList<>();
        Queue<Candidate> ranking19 = new LinkedList<>();
        Queue<Candidate> ranking20 = new LinkedList<>();

        ranking1.add(oranges);
        ranking2.add(oranges);
        ranking3.add(oranges);
        ranking4.add(oranges);
        ranking5.add(pears);
        ranking5.add(strawberries);
        ranking6.add(pears);
        ranking6.add(sweets);
        ranking7.add(chocolate);
        ranking7.add(strawberries);
        ranking8.add(chocolate);
        ranking8.add(strawberries);
        ranking9.add(chocolate);
        ranking9.add(strawberries);
        ranking10.add(chocolate);
        ranking10.add(strawberries);
        ranking11.add(chocolate);
        ranking11.add(strawberries);
        ranking12.add(chocolate);
        ranking12.add(strawberries);
        ranking13.add(chocolate);
        ranking13.add(strawberries);
        ranking14.add(chocolate);
        ranking14.add(strawberries);
        ranking15.add(chocolate);
        ranking15.add(sweets);
        ranking16.add(chocolate);
        ranking16.add(sweets);
        ranking17.add(chocolate);
        ranking17.add(sweets);
        ranking18.add(chocolate);
        ranking18.add(sweets);
        ranking19.add(strawberries);
        ranking20.add(sweets);

        Ballot b1 = new Ballot(ranking1, r1);
        Ballot b2 = new Ballot(ranking2, r1);
        Ballot b3 = new Ballot(ranking3, r1);
        Ballot b4 = new Ballot(ranking4, r1);
        Ballot b5 = new Ballot(ranking5, r1);
        Ballot b6 = new Ballot(ranking6, r1);
        Ballot b7 = new Ballot(ranking7, r1);
        Ballot b8 = new Ballot(ranking8, r1);
        Ballot b9 = new Ballot(ranking9, r1);
        Ballot b10 = new Ballot(ranking10, r1);
        Ballot b11 = new Ballot(ranking11, r1);
        Ballot b12 = new Ballot(ranking12, r1);
        Ballot b13 = new Ballot(ranking13, r1);
        Ballot b14 = new Ballot(ranking14, r1);
        Ballot b15 = new Ballot(ranking15, r1);
        Ballot b16 = new Ballot(ranking16, r1);
        Ballot b17 = new Ballot(ranking17, r1);
        Ballot b18 = new Ballot(ranking18, r1);
        Ballot b19 = new Ballot(ranking19, r1);
        Ballot b20 = new Ballot(ranking20, r1);


        VoteCounter vc = new VoteCounter(r1);
        vc.countVotes();
        r1.printWinners();
    }

    private static void sample3() {
        Race r1 = new Race(3, "WikiPartyFoodTieRandom");
        Candidate oranges = new Candidate("Oranges", r1);
        Candidate pears = new Candidate("Pears", r1);
        Candidate chocolate = new Candidate("Chocolate", r1);
        Candidate strawberries = new Candidate("Strawberries", r1);
        Candidate sweets = new Candidate("Sweets", r1);
        Queue<Candidate> ranking1 = new LinkedList<>();
        Queue<Candidate> ranking2 = new LinkedList<>();
        Queue<Candidate> ranking3 = new LinkedList<>();
        Queue<Candidate> ranking4 = new LinkedList<>();
        Queue<Candidate> ranking5 = new LinkedList<>();
        Queue<Candidate> ranking6 = new LinkedList<>();
        Queue<Candidate> ranking7 = new LinkedList<>();
        Queue<Candidate> ranking8 = new LinkedList<>();
        Queue<Candidate> ranking9 = new LinkedList<>();
        Queue<Candidate> ranking10 = new LinkedList<>();
        Queue<Candidate> ranking11 = new LinkedList<>();
        Queue<Candidate> ranking12 = new LinkedList<>();
        Queue<Candidate> ranking13 = new LinkedList<>();
        Queue<Candidate> ranking14 = new LinkedList<>();
        Queue<Candidate> ranking15 = new LinkedList<>();
        Queue<Candidate> ranking16 = new LinkedList<>();
        Queue<Candidate> ranking17 = new LinkedList<>();
        Queue<Candidate> ranking18 = new LinkedList<>();
        Queue<Candidate> ranking19 = new LinkedList<>();
        Queue<Candidate> ranking20 = new LinkedList<>();

        ranking1.add(oranges);
        ranking2.add(oranges);
        ranking3.add(oranges);
        ranking4.add(oranges);
        ranking5.add(pears);
        ranking5.add(oranges);
        ranking6.add(pears);
        ranking6.add(oranges);
        ranking7.add(chocolate);
        ranking7.add(pears);
        ranking8.add(chocolate);
        ranking8.add(pears);
        ranking9.add(chocolate);
        ranking9.add(pears);
        ranking10.add(chocolate);
        ranking10.add(pears);
        ranking11.add(chocolate);
        ranking11.add(pears);
        ranking12.add(chocolate);
        ranking12.add(pears);
        ranking13.add(chocolate);
        ranking13.add(pears);
        ranking14.add(chocolate);
        ranking14.add(pears);
        ranking15.add(chocolate);
        ranking15.add(pears);
        ranking16.add(chocolate);
        ranking16.add(pears);
        ranking17.add(chocolate);
        ranking17.add(pears);
        ranking18.add(chocolate);
        ranking18.add(pears);
        ranking19.add(strawberries);
        ranking20.add(sweets);

        Ballot b1 = new Ballot(ranking1, r1);
        Ballot b2 = new Ballot(ranking2, r1);
        Ballot b3 = new Ballot(ranking3, r1);
        Ballot b4 = new Ballot(ranking4, r1);
        Ballot b5 = new Ballot(ranking5, r1);
        Ballot b6 = new Ballot(ranking6, r1);
        Ballot b7 = new Ballot(ranking7, r1);
        Ballot b8 = new Ballot(ranking8, r1);
        Ballot b9 = new Ballot(ranking9, r1);
        Ballot b10 = new Ballot(ranking10, r1);
        Ballot b11 = new Ballot(ranking11, r1);
        Ballot b12 = new Ballot(ranking12, r1);
        Ballot b13 = new Ballot(ranking13, r1);
        Ballot b14 = new Ballot(ranking14, r1);
        Ballot b15 = new Ballot(ranking15, r1);
        Ballot b16 = new Ballot(ranking16, r1);
        Ballot b17 = new Ballot(ranking17, r1);
        Ballot b18 = new Ballot(ranking18, r1);
        Ballot b19 = new Ballot(ranking19, r1);
        Ballot b20 = new Ballot(ranking20, r1);


        VoteCounter vc = new VoteCounter(r1);
        vc.countVotes();
        r1.printWinners();
    }

    private static void sample4() {
        Race r1 = new Race(1, "WikiPartyFoodLotsOfBallots");
        Candidate oranges = new Candidate("Oranges", r1);
        Candidate pears = new Candidate("Pears", r1);
        Candidate chocolate = new Candidate("Chocolate", r1);
        Candidate strawberries = new Candidate("Strawberries", r1);
        Candidate sweets = new Candidate("Sweets", r1);

        for (int i = 0; i < 200000; i++) {
            Queue<Candidate> rankingi = new LinkedList<>();
            int min = 0;
            int max = r1.getCandidates().size() - 1;
            int randomIndex = ThreadLocalRandom.current().nextInt(min, max + 1);
            int randomIndex2 = ThreadLocalRandom.current().nextInt(min, max + 1);
            int randomIndex3 = ThreadLocalRandom.current().nextInt(min, max + 1);
            rankingi.add(r1.getCandidates().get(randomIndex));
            if (randomIndex2 != randomIndex) rankingi.add(r1.getCandidates().get(randomIndex2));
            if (randomIndex3 != randomIndex2 && randomIndex3 != randomIndex)
                rankingi.add(r1.getCandidates().get(randomIndex3));
            Ballot bi = new Ballot(rankingi, r1);
        }

        VoteCounter vc = new VoteCounter(r1);
        vc.countVotes();
        r1.printWinners();
    }
}
