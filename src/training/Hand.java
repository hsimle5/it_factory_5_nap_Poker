package training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand(Card card1, Card card2) {
        super();
        cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);
    }

    private static boolean allSameColor(List<Card> cards) {
        if (cards.isEmpty()) {
            return true;
        }

        for (Card card : cards) {
            if (card.getColor() != cards.get(0).getColor()) {
                return false;
            }
        }

        return true;
    }

    private static boolean isStraight(List<Card> cards) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'cards' cannot be empty.");
        }

        Collections.sort(cards);

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getRank() != cards.get(i - 1).getRank() + 1) {
                return false;
            }
        }

        return true;
    }

    private static int evaluate(List<Card> fiveCards) {
        if (fiveCards.size() != 5) {
            throw new IllegalArgumentException("Parameter 'fiveCards' should contain exactly five cards.");
        }


        int handValue = 0;
        if (isStraight(fiveCards) && allSameColor(fiveCards) && highestRank(fiveCards) == 12) {
            handValue = 100000;
        } else if (isStraight(fiveCards) && allSameColor(fiveCards)) {
            handValue = 90000;
        } else if (maxCountOfSameRank(fiveCards) == 4) {
            handValue = 80000;
        } else if (maxCountOfSameRank(fiveCards) == 3 && secondMaxCountOfSameRank(fiveCards) == 2) {
            handValue = 70000;
        } else if (allSameColor(fiveCards)) {
            handValue = 60000;
        } else if (isStraight(fiveCards)) {
            handValue = 50000;
        } else if (maxCountOfSameRank(fiveCards) == 3) {
            handValue = 40000;
        } else if (maxCountOfSameRank(fiveCards) == 2 && secondMaxCountOfSameRank(fiveCards) == 2) {
            handValue = 30000;
        } else if (maxCountOfSameRank(fiveCards) == 2) {
            handValue = 20000;
        } else {
            handValue = highestRank(fiveCards);
        }
        return handValue;
    }

    private static int maxCountOfSameRank(List<Card> fiveCards) {
        List<Integer> countsOfSameRank = new ArrayList<Integer>(13);
        for (int i = 0; i < Value.values().length; i++) {
            countsOfSameRank.add(0);
        }

        for (int i = 0; i < fiveCards.size(); i++) {
            countsOfSameRank.set(fiveCards.get(i).getRank(), countsOfSameRank.get(fiveCards.get(i).getRank()) + 1);
        }
        Collections.sort(countsOfSameRank);
        return countsOfSameRank.get(countsOfSameRank.size() - 1);
    }

    private static int highestRank(List<Card> fiveCards) {
        Collections.sort(fiveCards);
        return fiveCards.get(fiveCards.size() - 1).getRank();
    }

    private static int secondMaxCountOfSameRank(List<Card> fiveCards) {
        // secondMaxCountOfSameRank start
        List<Integer> countsOfSameRank1 = new ArrayList<Integer>(13);
        for (int i = 0; i < Value.values().length; i++) {
            countsOfSameRank1.add(0);
        }

        for (int i = 0; i < fiveCards.size(); i++) {
            countsOfSameRank1.set(fiveCards.get(i).getRank(), countsOfSameRank1.get(fiveCards.get(i).getRank()) + 1);
        }
        Collections.sort(countsOfSameRank1);
        // secondMaxCountOfSameRank end
        return countsOfSameRank1.get(countsOfSameRank1.size() - 2);
    }

    public int findMaxValueSubset(List<Card> cardsOnTable) {
        if (cardsOnTable.size() != 5) {
            throw new IllegalArgumentException("Parameter 'cardsOnTable' should contain exactly five cards.");
        }

        int allSize = cardsOnTable.size() + cards.size();

        List<Card> allCards = new ArrayList<Card>();
        allCards.addAll(cardsOnTable);
        allCards.addAll(cards);

        int maxValue = 0;
        for (int i = 0; i < allSize; i++) {
            for (int j = i; j < allSize - 1; j++) {
                List<Card> allCardsCopy = new ArrayList<Card>(allCards);
                allCardsCopy.remove(i);
                allCardsCopy.remove(j);

                int value = evaluate(allCardsCopy);
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        }

        return maxValue;
    }
}
