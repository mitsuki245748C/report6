package jp.ac.uryukyu.ie.e245748;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CardTest {
    private Card deck;

    @BeforeEach
    public void setup() {
        deck = new Card();
        deck.prepareCards();
    }

    @Test
    public void testDeckSize() {
        // デッキのサイズが52であることを確認
        assertEquals(52, deck.getCardCount(), "デッキには52枚のカードが含まれているはずです。");
    }

    @Test
    public void testShuffleDeck() {
        // デッキをシャッフルした後に、最初のカードの値が元のものと異なることを確認
        int firstCardBeforeShuffle = deck.drawCard();
        deck.prepareCards();
        int firstCardAfterShuffle = deck.drawCard();
        
        assertNotEquals(firstCardBeforeShuffle, firstCardAfterShuffle, "デッキはシャッフルされるべきです。");
    }

    @Test
    public void testDrawCard() {
        // デッキから1枚カードを引き、そのカードがデッキに存在することを確認
        int initialSize = deck.getCardCount();
        deck.removeCard();
        
        assertEquals(initialSize - 1, deck.getCardCount(), "カードを引いた後、デッキのサイズは1減少するべきです。");
        assertTrue(deck.getCardCount() > 0, "デッキにはまだカードが残っているべきです。");
    }

    @Test
    public void testPlayerListInitialization() {
        int playerCount = 4;
        deck.preparePlayerLists(playerCount);
        
        // プレイヤーごとの手札リストのサイズがプレイヤー数と一致するか確認
        assertEquals(playerCount, deck.getPlayerLists().size(), "プレイヤーの手札リストが正しく初期化されているべきです。");
    }

    @Test
    public void testPlayerPointsInitialization() {
        int playerCount = 4;
        deck.preparePlayerPointsLists(playerCount);

        // プレイヤーのスコアリストがプレイヤー数と一致するか確認
        assertEquals(playerCount, deck.getPlayerPoints().size(), "プレイヤーのスコアリストが正しく初期化されているべきです。");
    }

    @Test
    public void testGameDrawScenario() {
        // 引き分けのシナリオをテスト
        deck.preparePlayerLists(2); // 2人のプレイヤー
        deck.preparePlayerPointsLists(2);

        deck.addDrawPlayer(0);
        deck.addDrawPlayer(1);
        
        // 引き分け処理が行われることを確認
        deck.playGameDraw();
        assertTrue(deck.getDrawPlayer().size() <= 2, "引き分け処理後、引き分けプレイヤーリストが更新されているべきです。");
    }

    @Test
    public void testJudgmentCard() {
        int[] highestCard = {0};
        ArrayList<Integer> drawPlayers = new ArrayList<>();
        
        // 3つのカードを比較
        deck.judgmentCard(0, 5, highestCard, drawPlayers);
        deck.judgmentCard(1, 8, highestCard, drawPlayers);
        deck.judgmentCard(2, 8, highestCard, drawPlayers);
        
        assertEquals(8, highestCard[0], "最高のカードは8であるべきです。");
        assertTrue(drawPlayers.contains(1), "プレイヤー2が引き分けプレイヤーリストに含まれるべきです。");
        assertTrue(drawPlayers.contains(2), "プレイヤー3が引き分けプレイヤーリストに含まれるべきです。");
    }

    @Test
    public void testGameWinner() {
        ArrayList<Integer> playerPoints = new ArrayList<>();
        playerPoints.add(3);
        playerPoints.add(5);
        playerPoints.add(2);
        
        // プレイヤー2が勝者となるはず
        String winner = deck.judgmentGameWinner(playerPoints);
        assertEquals("プレイヤー 2", winner, "プレイヤー2が勝者であるべきです。");
    }
}