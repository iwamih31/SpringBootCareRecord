package com.iwamih31;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	@Autowired
	private ToDoRepository toDoRepository;


	public List<User> userList() {
		return userRepository.userList();
	}

	public User user(int id) {
		return userRepository.getReferenceById(id);
	}

	public String User_Insert(User user, int id) {
		System.out.println("recordInsert開始");
		String message = "ID" + user.getId() + " のデータ";
		user.setId(id);
		List<User>userList = new ArrayList<User>();
		userList.add(user);
		try {
			userRepository.saveAllAndFlush(userList);
			message += " を登録しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		System.out.println("recordInsert終了");
		return message;
	}

	public int nextUserID() {
		List<User> userList = userRepository.findAll();
		int userListSize = userList.size();
		int lastId = 0;
		if (userList.size() > 0) {
			lastId = userList.get(userListSize - 1).getId();
		}
		__consoleOut__("lastId = " + lastId);
		return lastId + 1;
	}

	public List<Action> records(int id, String date) {
		return recordRepository.getRecords(id, date);
	}

	public List<Action> recordsAll() {
		return recordRepository.findAll();
	}

	public String record_Insert(Action record, int id, String date) {
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

	public Action insert_record(int id, String date) {
		System.out.println("insert_record開始");
		return new Action(1, id,date, "", "", "", "", "", "", "", "", "", "" );
	}

	public OptionData options() {
		return new OptionData();
	}

	public Detail detail(int id) {
		Detail detail = null;
		if(detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
		} else {
			__consoleOut__("テーブル detail に Id(" + id + ") がありません");
			detail = new Detail(id, "", "", "");
		}
		return detail;
	}

	public String detailUpdate(Detail detail, int id) {
		detail.setId(id);
		List<Detail>detailList = new ArrayList<Detail>();
		detailList.add(detail);
		System.out.println("detailUpdate開始");
		String message = "";
		try {
			detailRepository.saveAll(detailList);
			message = "ID = " + detail.getId() + " のデータを更新しました";
		} catch (Exception e) {
			message = "登録に失敗しました " + e.getMessage();
		}
		System.out.println("routineUpdate終了");
		return message;
	}

	public List<Routine> routineList(String date) {
		return routineRepository.routineList(date);
	}

	public List<Routine> routineAll() {
		return routineRepository.findAll();
	}

	public Routine routine(int id) {
		return routineRepository.getReferenceById(id);
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

	public Integer[] dateOptions(int count, String input) {
		Integer[] options = null;
		switch(count) {
			case 1:
				options = OptionData.years(today(), 1900, -1);
				break;
			case 2:
				options = OptionData.month();
				break;
			case 3:
				options = OptionData.days(input);
				break;
		}
		return options;
	}

	public String dateString(int count) {

		switch(count) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
		}
		return null;
	}

	public String dateInputUrl(int count) {
		String dateInputUrl = "/CareRecord/Birthday";
		if (count == 3) dateInputUrl = "/CareRecord/BirthdayUpdate";
		return dateInputUrl;
	}

	public String dateInputLabel(int count) {
		String data = null;
		switch(count) {
			case 1:
				data = "年";
				break;
			case 2:
				data = "月";
				break;
			case 3:
				data = "日にち";
				break;
		}
		return "生まれた" + data + "を入力して下さい";
	}

	/** 年、月、日の順番で受け取ったそれぞれの数字の桁を合わせ/で繋げる */
	public String dateStringConnect(int count, String string, String input) {
		String delimiter = "/";
		int word_count = 2;
		switch(count) {
			case 1:
				delimiter = "";
				word_count = 0;
				break;
			case 2:
				delimiter = "";
				word_count = 4;
				break;
		}
		// 文字数が word_count になる様に input の前を "0" で埋める
		input = stringAlignment(input, word_count, "0");
		// delimiter で連結し返す
		return string + delimiter + input;
	}

	/** 文字数が word_count になる様に object の前を fill で埋める */
	public static String stringAlignment(Object object, int word_count , String fill) {
		// fill を word_count の数だけ繋げる
		String fills = "";
		for (int i = 0; i < word_count; i++) {
			fills += fill;
		}
		// object の先頭に fills を付け 文字列変数 alignmentString に代入
		String alignmentString = (fills + object);
		//alignmentString の末尾から word_count の数だけ文字を抜き出し 自身に代入
		alignmentString = alignmentString.substring(alignmentString.length() - word_count);
		return alignmentString;
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

	public void birthdayUpdate(int id, String string, String input) {
		// 文字数が 2 になる様に input の前を "0" で埋める
		input = stringAlignment(input, 2, "0");
		String birthday = string + "/" + input;
		__consoleOut__("birthday = " + birthday);
		Detail detail;
		if (detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
			detail.setBirthday(birthday);
		} else {
			detail = new Detail(id, birthday, "", "");
		}
		detailRepository.save(detail);
	}

	public void todoSave(ToDo todo) {
		toDoRepository.save(todo);

	}

	public String[] names() {
		List<User> users = userRepository.findAll();
		String[] names = new String[users.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = users.get(i).getName();
		}
		return names;
	}

	public  List<ToDo> todoList() {
		return toDoRepository.findAll();
	}

	public void setRoutineList(String date) {
		List<Routine> routineList = routineList(date);
		if (routineList.size() == 0) {
			List<ToDo> todoList = todoList();
			for (ToDo todo : todoList) {
				List<User> userList = userList();
				for (User user : userList) {
					List<Routine> routineAll = routineAll();
					int lastRoutineID = 0;
					if (routineAll.size() > 0) {
						int lastRoutineNum = routineAll.size() - 1;
						lastRoutineID = routineAll.get(lastRoutineNum).getId();
					}
					int insertID = lastRoutineID + 1;
					__consoleOut__("insertID = " + insertID);
					Routine routine = new Routine(
							lastRoutineID + 1,
							date,
							todo.getTime(),
							todo.getAction(),
							user.getRoom(),
							user.getName(),
							"");
					routineRepository.save(routine);
				}
			}
		}

	}

	public void __consoleOut__(String message) {
		System.out.println("");
		System.out.println(message);
		System.out.println("");
	}

	public String routineUpdate(Routine routine, int id) {
		routine.setId(id);
		List<Routine>routineList = new ArrayList<Routine>();
		routineList.add(routine);
		System.out.println("routineUpdate開始");
		String message = "";
		try {
			routineRepository.saveAll(routineList);
			message = "ID = " + routine.getId() + " のデータを更新しました";
		} catch (Exception e) {
			message = "登録に失敗しました " + e.getMessage();
		}
		System.out.println("routineUpdate終了");
		return message;
	}

	public String[] todoNames() {
		return toDoRepository.todoNames();
	}

	public List<Integer> blankRooms() {
		List<Integer> blankRooms = new ArrayList<Integer>();
		List<User> userList = userRepository.userList();
		Integer[] roomNumbers = OptionData.room;
		for (Integer roomNumber : roomNumbers) {
			boolean isUse = false;
			for (User user : userList) {
				if(roomNumber == user.getRoom()) isUse = true;
			}
			if (isUse == false) blankRooms.add(roomNumber);
		}
		return blankRooms;
	}

	public User newUser() {
		User newUser = new User(nextUserID(),0, "","" );
		return newUser;
	}

}
