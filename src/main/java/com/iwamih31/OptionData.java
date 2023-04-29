package com.iwamih31;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OptionData {

  // 部屋番号
  public static Integer[] room = nums(1, 20 , 1);

  // 名前
  public static String[] name = names();

  // 日付
  public static String[] date = dates();

  // 時間
  public static String[] time = times();

  // 睡眠
  public static String[] sleep = {"起床","臥床","傾眠"};

  // 水分
  public static Integer[] water = nums(50, 500 , 50);

  // 排尿1
  public static String[] pee1 = {"パ交", "リ交", "ト有", "ト無", "ト未", "失禁", "拒否"};

  // 排尿2
  public static String[] pee2 = {"パ交", "リ交", "ト有", "ト無", "ト未", "失禁", "拒否"};

  // 排便
  public static String[] poop = {"普多", "普中", "普少", "軟多", "軟中", "軟少"};

  // 下剤
  public static Integer[] laxative = nums(5, 20 , 1);

  // 服薬
  public static String[] medicine = {"〇", "×", "拒否"};

  // 処置
  public static String[] ointment = {"〇", "×", "拒否"};

  // 様子
  public static String[] situation = {"特変なし", "要確認！"};

  // 利用状況
  public static String[] use = {"利用中", "利用終了"};

  /** start から end まで add ずつ増やした数を代入した配列を作成 */
  public static Integer[] nums(int start, int end, int add) {
		Integer[] nums = new Integer[(end - start) / add + 1];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i * add + start;
		}
		return nums;
	}

	public static String[] names() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/** 現在時刻の末尾を 0 または 5 に変換し、そこから5分ずつ減らした時刻の配列を作成 */
	public static String[] times() {
		// 現在日時を取得
		LocalDateTime dateTime = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		// 配列を作成し5分刻みの時刻を代入
		String[] times = new String[6 * 2 * 24];
		for (int i = 0; i < times.length; i++) {
			// フォーマット
			String data = dateTimeFormatter.format(dateTime);
			// 末尾を 0 または 5 に変換して times[i] に代入
			// minutes の1の位を取得
			String minutesLast = data.substring(data.length()-1, data.length());
			// 0 にするか 5 にするか判定
			if (Integer.parseInt(minutesLast) < 5) {
				minutesLast = "0";
			} else {
				minutesLast = "5";
			}
			// 末尾を変換
			times[i] = data.substring(0, data.length()-1) + minutesLast;
			// dateTimeを5分減らす
			dateTime = dateTime.minusMinutes(5);
		}
		return times;
	}

	public static String[] dates() {
		// 現在日時を取得
		LocalDateTime dateTime = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		// 配列を作成し今日から1年前迄の日付を代入
		String[] data = new String[366];
		for (int i = 0; i < data.length; i++) {
			// フォーマット
			data[i] = dateTimeFormatter.format(dateTime);
			// dateTimeを1日減らす
			dateTime = dateTime.minusDays(1);
		}
		return data;
	}

	public static Integer[] days(String month) {
		int lastDay = 31;
		switch(month) {
			case "2":
				lastDay = 29;
				break;
			case "4":
			case "6":
			case "9":
			case "11":
				lastDay = 30;
				break;
		}
		Integer[] days = nums(1, lastDay, 1);
		return days;
	}

	public static Integer[] month() {
		// 1から12の配列を作成
		Integer[] month = nums(1, 12, 1);
		return month;
	}

  /** String date の年数部分から end までを代入した配列を作成 */
	public static Integer[] years(String date, int end, int add) {
		int year = Integer.parseInt(date.split("/")[0]);
		Integer[] years = nums(year, end, add);
		return years;
	}

	/** 配列の中身を同じ文字数に揃える */
	public static String[] arrayAlignment(Object[] array, int word_count , String fill) {
		// fill を word_count の数だけ繋げる
		String fills = "";
		for (int i = 0; i < word_count; i++) {
			fills += fill;
		}
		// Object[] array と同じ length の String 配列 alignmentArray を作成
		String[] alignmentArray = new String[array.length];
		for (int i = 0; i < alignmentArray.length; i++) {
			// 先頭に fills を付ける
			String string = (fills + array[i]);
			//末尾から word_count の数だけ文字を抜き出し alignmentArray[i] に代入
			alignmentArray[i] = string.substring(string.length() - word_count);
		}
		return alignmentArray;
	}

}
