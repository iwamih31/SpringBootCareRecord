package com.iwamih31;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareRecordService {

	// リポジトリ格納の為のプライベートフィールド
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoutineRepository routineRepository;
	@Autowired
	private ActionRepository recordRepository;
	@Autowired
	private DetailRepository detailRepository;


	public List<User> userList() {
		return userRepository.userList();
	}

	public User user(int id) {
		return userRepository.getReferenceById(id);
	}

	public List<Action> records(int id, String date) {
		return recordRepository.getRecords(id, date);
	}

	public List<Action> recordsAll() {
		return recordRepository.findAll();
	}

	public String recordInsert(Action record, int id, String date) {
		System.out.println("recordInsert開始");
		String message = "";
		record.setUser_id(id);
		record.setDate(date);
		try {
			recordRepository.save(record);
			message = record.getTime() + " のデータを登録しました";
		} catch (Exception e) {
			message = "登録に失敗しました " + e.getMessage();
		}
		System.out.println("recordInsert終了");
		return message;
	}

	public OptionData options() {
		return new OptionData();
	}

	public Detail detail(int id) {
		return detailRepository.getReferenceById(id);
	}

	public List<Routine> routineList() {
		return routineRepository.findAll();
	}

	public String now() {
		// 現在日時を取得
		LocalDateTime now = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
		return dateTimeFormatter.format(now);
	}

	public String today() {
		// 今日の日付を取得
		LocalDateTime now = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return dateTimeFormatter.format(now);
	}

	public String time() {
		// 現在時刻を取得
		LocalDateTime now = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = dateTimeFormatter.format(now);
		//末尾を0に変換して返す
		return time.substring(0, time.length()-1) + 0;
	}

	public Action insert_record(int id, String date) {
		System.out.println("insert_record開始");
		return new Action(1, id,date, "", "", "", "", "", "", "", "", "", "" );
	}
}
