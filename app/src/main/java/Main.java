import jp.ac.uryukyu.ie.e245748.*;
/**
 * カードゲームのメインクラス。
 */
public class Main {
    public static void main(String[] args) {
        //デッキの作成とシャッフル
        Card deck = new Card();
        deck.prepareCards();

        //プレーヤー人数の取得
        CommandSelector commandSelector = new CommandSelector();
        int playerCount = commandSelector.waitForUsersCommand();

        //ゲーム開始
        commandSelector.gameStart();

        //プレーヤーリストとポイントリストの取得
        deck.preparePlayerLists(playerCount);
        deck.preparePlayerPointsLists(playerCount);

        //ゲーム進行
        deck.playGame(playerCount);
    }
}