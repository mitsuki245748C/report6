package jp.ac.uryukyu.ie.e245748;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Card {
    private ArrayList<Integer> cards = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> playerLists = new ArrayList<>();

    public Card() {
        for (int j = 0; j < 4; j ++) {
            for (int i = 1; i <= 13; i ++) {
                cards.add(i);
            }
            
        }
    }

    public void prepareCards() {
        Random r = new Random();
        for (int i = cards.size() -1; i > 0; i --) {
            int j = r.nextInt(i + 1);
            int k = cards.get(i);
            cards.set(i,cards.get(j));
            cards.set(j,k);
        }
    }

    public int drawCard() {
        return cards.get(0);
    }

    public List<Integer> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void removeCard() {
        cards.remove(0);
    }

    public int getCardCount() {
        return cards.size();
    }

    public List<List<Integer>> getPlayerLists() {
        return Collections.unmodifiableList(playerLists);
    }

    public void preparePlayerLists(int playerCount) {
        for (int l = 0; l < playerCount; l ++) {
            playerLists.add(new ArrayList<>());
        }
    }

    public void playGame(int playerCount) {
        int round = 1;
        while (getCardCount() > 0) {
            System.out.println("ラウンド " + round + ":");
            for (int player = 0; player < playerCount; player ++) {
                if (getCardCount() == 0) {
                    System.out.println("デッキが空になりました");
                    return;
                }
                int drawnCard = drawCard();
                System.out.println("プレイヤー " + (player + 1) + "が引いたカード:" + drawnCard);
                playerLists.get(player).add(drawnCard);
                removeCard();
            }
            round ++;
        }
        System.out.println("すべてのカードが引かれました");
        System.out.println(playerLists);
    }

    public void endJudgment() {
        
    }
}
