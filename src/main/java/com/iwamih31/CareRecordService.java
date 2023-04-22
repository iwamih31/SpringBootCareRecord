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
	private CareRecordRepository careRecordRepository;
	@Autowired
	private RoutineRepository routineRepository;
	@Autowired
	private PrivateRepository privateRepository;
	@Autowired
	private DetailRepository detailRepository;


	public List<CareRecord> userList() {
		return careRecordRepository.userList();
	}

	public CareRecord user(int id) {
		return careRecordRepository.getReferenceById(id);
	}

	public List<Private> records(int id, String date) {
		CareRecord user = user(id);
		return privateRepository.getPrivate(user.getRoom(), user.getName(), date);
	}

	public List<Private> recordsAll() {
		return privateRepository.findAll();
	}

	public String recordInsert(Private insert_record) {
		System.out.println("recordInsert開始");
		String message = "";
		if (privateRepository.save(insert_record) == null) {
			message = "登録に失敗しました";
		} else {
			message = insert_record.getTime() + " のデータを登録しました";
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

	public Private insert_record(int id, String date) {
		System.out.println("insert_record開始");
		CareRecord user = user(id);
		return new Private(null, user.getRoom(), user.getName(),date, "", "", "", "", "", "", "", "", "", "" );
	}
}
