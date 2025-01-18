package jp.ac.uryukyu.ie.e245748;

import java.util.Scanner;

/**
 * プレイヤーの人数を設定し、ゲームの開始を管理するクラス。
 */
public class CommandSelector {
    private int player_numbers; // プレイヤーの人数
    Scanner scanner; // ユーザー入力用のスキャナ

    /**
     * コンストラクタ。コマンドリストを初期化し、スキャナを準備します。
     */
    public CommandSelector() {
        scanner = new Scanner(System.in);
    }

    /**
     * プレイヤーの人数をユーザーから取得します。
     * @return 入力されたプレイヤーの人数。
     */
    public int waitForUsersCommand() {
        System.out.println("プレイヤーは何人ですか？");
        int player_numbers = scanner.nextInt();
        scanner.nextLine(); // 改行文字を消費
        System.out.println("プレイヤーは" + player_numbers + "人です。");
        return player_numbers;
    }

    /**
     * ゲーム開始前の待機メッセージを表示し、ユーザーがEnterキーを押すのを待つ。
     */
    public void gameStart() {
        System.out.println("エンターキーを押したらゲームを始めます...");
        scanner.nextLine();
        System.out.println("ゲームを始めます。");
    }

    /**
     * 設定されたプレイヤーの人数を取得する。
     * 
     * @return プレイヤーの人数。
     */
    public int getPlayerNumber() {
        return player_numbers;
    }
}
