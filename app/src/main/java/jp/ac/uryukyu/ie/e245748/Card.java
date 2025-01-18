package jp.ac.uryukyu.ie.e245748;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * カードゲームのデッキおよびゲームを動かすクラス。
 */
public class Card {
    private ArrayList<Integer> cards = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> playerLists = new ArrayList<>();
    private ArrayList<Integer> playerPoints = new ArrayList<>();
    private ArrayList<Integer> drawPlayer = new ArrayList<>();

    /**
     * コンストラクタ。52枚のカードを生成してデッキに追加する。
     */
    public Card() {
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i <= 13; i++) {
                cards.add(i);
            }
        }
    }

    /**
     * デッキをシャッフルする。
     */
    public void prepareCards() {
        Random r = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int k = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, k);
        }
    }

    /**
     * デッキの一番上のカードを引く。
     * @return 引いたカードの値。
     */
    public int drawCard() {
        return cards.get(0);
    }

    /**
     * デッキの一番上のカードを削除する。
     */
    public void removeCard() {
        cards.remove(0);
    }

    /**
     * デッキに残っているカードの枚数を取得する。
     * @return デッキのカード枚数。
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * プレイヤーごとの手札リストを取得する。
     * @return プレーヤーごとの手札リスト。
     */
    public List<List<Integer>> getPlayerLists() {
        return Collections.unmodifiableList(playerLists);
    }

    /**
     * プレイヤーのポイントを取得する。
     * @return プレイヤーのポイント。
     */
    public List<Integer> getPlayerPoints() {
        return Collections.unmodifiableList(playerPoints);
    }

    /**
     * 引き分けとなったプレイヤーを取得する。
     * @return 引き分けとなったプレイヤー。
     */
    public List<Integer> getDrawPlayer() {
        return Collections.unmodifiableList(drawPlayer);
    }
    /**
     * プレイヤーごとの手札リストを準備します。
     * @param playerCount プレイヤーの人数。
     */
    public void preparePlayerLists(int playerCount) {
        for (int l = 0; l < playerCount; l++) {
            playerLists.add(new ArrayList<>());
        }
    }

    /**
     * プレイヤーごとのスコアリストを準備します。
     * @param playerCount プレイヤーの人数。
     */
    public void preparePlayerPointsLists(int playerCount) {
        for (int m = 0; m < playerCount; m++) {
            playerPoints.add(0);
        }
    }
    /**
     * 引き分けプレイヤーのインデックスをリストに追加する。
     * @param playerIndex プレイヤーのインデックス。
     */
    public void addDrawPlayer(int playerIndex) {
        drawPlayer.add(playerIndex);
    }

    /**
     * 引き分け時の再ゲーム処理を行う。
     */
    public void playGameDraw() {
        ArrayList<Integer> newDrawPlayers = new ArrayList<>();
        int highestCard = 0;

        System.out.println("引き分けが発生しました。再度カードを引きます。");
        for (int player : drawPlayer) {
            if (getCardCount() == 0) {
                System.out.println("デッキが空になりました。");
                return;
            }
            int drawnCard = drawCard();
            System.out.println("プレイヤー " + (player + 1) + " が引いたカード: " + drawnCard);
            playerLists.get(player).add(drawnCard);
            removeCard();

            if (drawnCard > highestCard) {
                highestCard = drawnCard;
                newDrawPlayers.clear();
                newDrawPlayers.add(player);
            } else if (drawnCard == highestCard) {
                newDrawPlayers.add(player);
            }
        }

        drawPlayer.clear();
        drawPlayer.addAll(newDrawPlayers);

        if (drawPlayer.size() == 1) {
            int winner = drawPlayer.get(0);
            System.out.println("勝者: プレイヤー " + (winner + 1));
            playerPoints.set(winner, playerPoints.get(winner) + 1);
            drawPlayer.clear();
        } else {
            playGameDraw();
        }
    }

    /**
     * 現在のラウンドでのカードの強さを比較し、引き分けプレイヤーを更新します。
     * @param player プレイヤー番号。
     * @param drawnCard 引いたカードの値。
     * @param highestCard 現在の最高値を格納した配列。
     * @param drawPlayer 引き分けとなったプレイヤーリスト。
     */
    public void judgmentCard(int player, int drawnCard, int[] highestCard, ArrayList<Integer> drawPlayer) {
        if (drawnCard > highestCard[0]) {
            highestCard[0] = drawnCard;
            drawPlayer.clear();
            drawPlayer.add(player);
        } else if (drawnCard == highestCard[0]) {
            drawPlayer.add(player);
        }
    }

    /**
     * ゲーム全体の勝者を判定する。
     * @param playerPoints 各プレイヤーのスコアリスト。
     * @return 勝者または同点プレイヤーの文字列。
     */
    public String judgmentGameWinner(ArrayList<Integer> playerPoints) {
        int highestPoint = 0;
        ArrayList<Integer> highestPlayers = new ArrayList<>();

        for (int i = 0; i < playerPoints.size(); i++) {
            if (playerPoints.get(i) > highestPoint) {
                highestPoint = playerPoints.get(i);
                highestPlayers.clear();
                highestPlayers.add(i + 1);
            } else if (playerPoints.get(i) == highestPoint) {
                highestPlayers.add(i + 1);
            }
        }
        if (highestPlayers.size() == 1) {
            return "プレイヤー " + highestPlayers.get(0);
        } else {
            return "同点プレイヤー: " + highestPlayers;
        }
    }

    /**
     * ゲームを開始し、ラウンドごとに進行する。
     * @param playerCount プレイヤーの人数。
     */
    public void playGame(int playerCount) {
        int round = 1;

        while (getCardCount() > 0) {
            System.out.println("ラウンド " + round + ":");
            int[] highestCard = {0};
            drawPlayer.clear();

            for (int player = 0; player < playerCount; player++) {
                if (getCardCount() == 0) {
                    break;
                }
                int drawnCard = drawCard();
                System.out.println("プレイヤー " + (player + 1) + " が引いたカード: " + drawnCard);
                playerLists.get(player).add(drawnCard);
                removeCard();
                judgmentCard(player, drawnCard, highestCard, drawPlayer);
            }

            if (drawPlayer.size() > 1) {
                playGameDraw();
            } else if (drawPlayer.size() == 1) {
                int winner = drawPlayer.get(0);
                System.out.println("ラウンドの勝者: プレイヤー " + (winner + 1));
                playerPoints.set(winner, playerPoints.get(winner) + 1);
            }
            round++;
        }
        System.out.println("すべてのカードが引かれました。ゲーム終了！");
        System.out.println("各プレイヤーの手札: " + playerLists);
        int b = 1;
        for (int playerPoint : playerPoints) {
            System.out.println("プレイヤー" + b + "のスコア: " + playerPoint);
            b += 1;
        }
        System.out.println("ゲームの勝者 : " + judgmentGameWinner(playerPoints));
    }
}
