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
	private ActionRepository actionRepository;
	@Autowired
	private DetailRepository detailRepository;
	@Autowired
	private ToDoRepository todoRepository;
	@Autowired
	private OfficeRepository officeRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private IdeaRepository ideaRepository;


	public List<User> user_All() {
		return userRepository.findAll();
	}

	public List<User> userList() {
		return userRepository.userList();
	}

	public Idea idea(int id) {
		return ideaRepository.getReferenceById(id);
	}

	public Event event(int id) {
		return eventRepository.getReferenceById(id);
	}

	public List<Event> eventList() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
		return eventRepository.eventList(localDateTime);
	}

	public List<Event> event_All() {
		return eventRepository.eventList();
	}

	public String event_Insert(Event event, int id, String datetime_Str) {
		__consoleOut__("event_Insert開始");
		String message = "ID = " + event.getId() + " の行事予定";
		try {
			event.setId(id);
			if (!datetime_Str.isBlank()) event.setDatetime(localDateTime(datetime_Str));
			eventRepository.save(event);
			message += " を登録しました";
			//行事計画情報初期化
			Idea idea = new Idea(id, "");
			message += "　" + idea_Update(idea, id);
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("event_Insert終了");
		return message;
	}

	public User user(int id) {
		return userRepository.getReferenceById(id);
	}

	public String user_Insert(User user, int id) {
		__consoleOut__("user_Insert開始");
		user.setId(id);
		String message = "ID = " + user.getId() + " の利用者データ";
		try {
			userRepository.save(user);
			message += " を登録しました";
			//詳細情報初期化
			Detail detail = new Detail(id, "", "", "");
			message += "　" + detail_Update(detail, id);
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("user_Insert終了");
		return message;
	}

	public String office_Insert(Office office, int id) {
		__consoleOut__("office_Insert開始");
		office.setId(id);
		String message = "ID = " + office.getId() + " の事業所データ";
		try {
			officeRepository.save(office);
			message += " を登録しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("office_Insert終了");
		return message;
	}

	public String user_Update(User user, int id) {
		__consoleOut__("user_Update開始");
		user.setId(id);
		String message = "ID = " + user.getId() + " の利用者データ";
		List<User>userList = new ArrayList<User>();
		userList.add(user);
		try {
			userRepository.saveAllAndFlush(userList);
			message += " を更新しました";
		} catch (Exception e) {
			message += "更新に失敗しました " + e.getMessage();
		}
		__consoleOut__("user_Update終了");
		return message;
	}

	public String office_Update(Office office, int id) {
		__consoleOut__("office_Update開始");
		office.setId(id);
		String message = "ID = " + office.getId() + " の事業所データ";
		try {
			officeRepository.save(office);
			message += " を更新しました";
		} catch (Exception e) {
			message += "更新に失敗しました " + e.getMessage();
		}
		__consoleOut__("office_Update終了");
		return message;
	}

	public Object action(int id) {
		return actionRepository.getReferenceById(id);
	}

	public List<Action> actions(int id, String date) {
		return actionRepository.getActions(id, date);
	}

	public List<Action> actionAll() {
		return actionRepository.findAll();
	}

	private Integer actionNextID() {
		List<Action> List = actionRepository.findAll();
		int userListSize = List.size();
		int lastId = 0;
		if (List.size() > 0) {
			lastId = List.get(userListSize - 1).getId();
		}
		__consoleOut__("lastId = " + lastId);
		return lastId + 1;
	}

	public String action_Insert(Action action, int user_id, String date) {
		__consoleOut__("actionInsert開始");
		action.setId(actionNextID());
		action.setUser_id(user_id);
		action.setDate(date);
		String message = "ID = " + action.getId() + " のデータ";
		try {
			actionRepository.save(action);
			message += " を登録しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("actionInsert終了");
		return message;
	}

	public String action_Update(Action action, int id) {
		__consoleOut__("actionSave開始");
		action.setId(id);
		String message = "ID = " + action.getId() + " の常時入力データ";
		try {
			actionRepository.save(action);
			message += " を更新しました";
		} catch (Exception e) {
			message += "更新に失敗しました " + e.getMessage();
		}
		__consoleOut__("actionSave終了");
		return message;
	}

	public Action insert_Action(int id, String date) {
		__consoleOut__("insert_Action開始");
		return new Action(1, id,date, "", "", "", "", "", "", "", "", "", "" );
	}

	public OptionData options() {
		return new OptionData();
	}

	public Detail detail(int id) {
		Detail detail = null;
		if(detailRepository.existsById(id)) {
			__consoleOut__("テーブル detail に Id(" + id + ") があります");
			detail = detailRepository.getReferenceById(id);
		} else {
			__consoleOut__("テーブル detail に Id(" + id + ") がありません");
			detailRepository.save(new Detail(id, "", "", ""));
		}
		__consoleOut__("" + detail);
		return detail;
	}

public Object detailAll() {
		return detailRepository.findAll();
	}

	public String detail_Update(Detail detail, int id) {
		__consoleOut__("detail_Update開始 id = " + id +  detail);
		detail.setId(id);
		String message = "ID = " + detail.getId() + " の詳細データ";
		try {
			detailRepository.save(detail);
			message += "を更新しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("detail_Update終了");
		return message;
	}

	public String idea_Update(Idea idea, int id) {
		__consoleOut__("idea_Update開始 id = " + id +  idea);
		idea.setId(id);
		String message = "ID = " + idea.getId() + " の行事計画データ";
		try {
			ideaRepository.save(idea);
			message += "を更新しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("idea_Update終了");
		return message;
	}

	public String event_Update(Event event, int id, String datetime_Str) {
		__consoleOut__("event_Update開始 " +  event);
		String message = "ID = " + event.getId() + " の行事予定データ";
		try {
			event.setId(id);
			if (!datetime_Str.isBlank()) event.setDatetime(localDateTime(datetime_Str));
			eventRepository.save(event);
			message += "を更新しました";
		} catch (Exception e) {
			message += "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("event_Update終了");
		return message;
	}

	private LocalDateTime localDateTime(String datetime_Str) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		return LocalDateTime.parse(datetime_Str, formatter);
	}

	/** 利用者情報データをExcelとして出力 */
	public void detail_Output_Excel() {
		// TODO 自動生成されたメソッド・スタブ
	}

	public void setRoutineList(String date) {
		List<ToDo> todoList = todoList();
		for (ToDo todo : todoList) {
			List<User> userList = userList();
			for (User user : userList) {
				List<Routine> routineAll = routine_All();
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

	public List<Routine> routineList(String date) {
		int listSize = routineRepository.routineList(date).size();
		if (listSize == 0) setRoutineList(date);
		return routineRepository.routineList(date);
	}

	public List<Routine> routine_All() {
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

	public String dateInputUrl(int count, int completion_count, String normal_Url, String completion_Url) {
		String dateInputUrl = normal_Url;
		if (count == completion_count) dateInputUrl = completion_Url;
		return dateInputUrl;
	}

	public String date_Input_Stage(int count) {
		String stage = null;
		switch(count) {
		case 1:
			stage = "年";
			break;
		case 2:
			stage = "月";
			break;
		case 3:
			stage = "日";
			break;
		}
		return stage;
	}

	public String dateInputLabel(int count, String label_head) {
		String stage = date_Input_Stage(count);
		return label_head + stage + "を選択して下さい";
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

	public void birthday_Update(int id, String string, String input) {
		// 文字数が 2 になる様に input の前を "0" で埋める
		input = stringAlignment(input, 2, "0");
		String birthday = string + "/" + input;
		__consoleOut__("birthday = " + birthday);
		Detail detail;
	// idが在れば birthday を書き換え 無ければ birthday 入りの detail を新規作成
		if (detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
			detail.setBirthday(birthday);
		} else {
			detail = new Detail(id, birthday, "", "");
		}
		// save() 実行
		detailRepository.save(detail);
	}

	public void move_in_Update(int id, String string, String input) {
		// 文字数が 2 になる様に input の前を "0" で埋める
		input = stringAlignment(input, 2, "0");
		String move_in = string + "/" + input;
		__consoleOut__("move_in = " + move_in);
		Detail detail;
		// idが在れば move_in を書き換え 無ければ move_in 入りの detail を新規作成
		if (detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
			detail.setMove_in(move_in);
		} else {
			detail = new Detail(id, "", "", "move_in");
		}
		// save() 実行
		detailRepository.save(detail);
	}

	public void todoSave(ToDo todo) {
		todoRepository.save(todo);

	}

	public String[] names() {
		return userRepository.names();
	}

	public  List<ToDo> todoList() {
		return todoRepository.findAll();
	}

	public Object todo(int id) {
		return todoRepository.getReferenceById(id);
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
		__consoleOut__("routineUpdate開始");
		String message = "";
		try {
			routineRepository.saveAll(routineList);
			message = "ID = " + routine.getId() + " の定期入力データを更新しました";
		} catch (Exception e) {
			message = "登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("routineUpdate終了");
		return message;
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

	public int next_User_Id() {
		int nextId = 1;
		User lastElement = getLastElement(userRepository.findAll());
		if (lastElement != null) nextId = lastElement.getId() + 1;
		__consoleOut__("nextId = " + nextId);
		return nextId;
	}

	private Integer next_Routine_Id() {
		int nextId = 1;
		Routine lastElement = getLastElement(routineRepository.findAll());
		if (lastElement != null) nextId = lastElement.getId() + 1;
		__consoleOut__("nextId = " + nextId);
		return nextId;
	}

	public int next_Office_Id() {
		int nextId = 1;
		Office lastElement = getLastElement(officeRepository.findAll());
		if (lastElement != null) nextId = lastElement.getId() + 1;
		__consoleOut__("nextId = " + nextId);
		return nextId;
	}

	public int next_Event_Id() {
		int nextId = 1;
		Event lastElement = getLastElement(eventRepository.findAll());
		if (lastElement != null) nextId = lastElement.getId() + 1;
		__consoleOut__("nextId = " + nextId);
		return nextId;
	}

	public User new_User() {
		return new User(next_User_Id(),0, "","" );
	}

	public Routine new_Routine(String date) {
		return new Routine(next_Routine_Id(), date, "", "", 0, "", "");
	}

	public Office new_Office() {
		Office new_Office = new Office(next_Office_Id(),"","" );
		if (new_Office.getId() == 1) set_Office();
		return  new Office(next_Office_Id(),"","" );
	}

	public Object new_Event() {
		return new Event(next_Event_Id(), "", null, "");
	}

	private void set_Office() {
		String[] item_Names = office_Item_Names();
		officeRepository.save(new Office(1, item_Names[0], ""));
		officeRepository.save(new Office(2, item_Names[1], ""));
	}

	/** List の最後の Element を返すジェネリックメソッド */
	public static <E> E getLastElement(List<E> list){
		E lastElement = null;
		if (list.size() > 0) {
			int lastIdx = list.size() - 1;
			lastElement = list.get(lastIdx);
		}
		return lastElement;
	}

	public String[] todo_actions() {
		return todoRepository.actions();
	}

	public void routine_Append(int id, String date) {
		User user = user(id);
		List<ToDo> todoList = todoList();
		for (ToDo todo : todoList) {
			int insertID = next_Routine_Id();
			__consoleOut__("insertID = " + insertID);
			Routine routine = new Routine(
					insertID,
					date,
					todo.getTime(),
					todo.getAction(),
					user .getRoom(),
					user.getName(),
					"");
			routineRepository.save(routine);
		}
	}

	public String routine_Delete(int id, int select) {
		__consoleOut__("routine_Delete(int id, int select)開始");
		String message = "キャンセルしました";
		if (select == 1) message = routine_Delete(id);
		__consoleOut__("routine_Delete(int id, int select)終了");
		return message;
	}

	public String routine_Delete(int id) {
		__consoleOut__("routine_Delete(int id)開始");
		String message = "ID = " + id + " のデータ";
		try {
			routineRepository.deleteById(id);
			message += "を削除しました";
		} catch (Exception e) {
			message += "削除に失敗しました " + e.getMessage();
		}
		__consoleOut__("routine_Delete(int id)終了");
		return message;
	}

	public String action_Delete(int id, int select) {
		__consoleOut__("action_Delete(int id, int select)開始");
		String message = "キャンセルしました";
		if (select == 1) message = action_Delete(id);
		__consoleOut__("action_Delete(int id, int select)終了");
		return message;
	}

	public String action_Delete(int id) {
		__consoleOut__("action_Delete(int id)開始");
		String message = "ID = " + id + " のデータ";
		try {
			actionRepository.deleteById(id);
			message += "を削除しました";
		} catch (Exception e) {
			message += "削除に失敗しました " + e.getMessage();
		}
		__consoleOut__("action_Delete(int id)終了");
		return message;
	}

	public List<Office> office_data() {
		List<Office> office_data = office_All();
		String[] item_Names = office_Item_Names();
		for (int i = 0; i < office_data.size(); i++) {
			for ( String item_Name : item_Names) {
				String remove_Name = office_data.get(i).getItem_name();
				if (remove_Name.equals(item_Name)) {
					office_data.remove(i);
					__consoleOut__(remove_Name + "を除外しました");
				}
			}
		}
		return office_data;
	}

	public String office_item_value (String item_name) {
		return officeRepository.item_value(item_name);
	}

	public List<Office> office_All() {
		if (next_Office_Id() == 1) set_Office();
		return officeRepository.findAll();
	}

	public Office office(int id) {
		return officeRepository.getReferenceById(id);
	}

	public String[] office_Item_Names() {
		 String[] item_Names =  {"事業所名","部署名"};
		return item_Names;
	}

	public Object date(String date) {
		if (date == null) date = today();
		return date;
	}

	public String event_Copy(int id) {
		String message = "ID = " + id + " の";
		try {
			int next_Id = next_Event_Id();
			Event originalE = eventRepository.getReferenceById(id);
			Event event = new Event(next_Id, originalE.getName(), originalE.getDatetime(), originalE.getDetail());
			eventRepository.save(event);
			message += "行事予定を複製しました 行事計画の";
			Idea originalI = ideaRepository.getReferenceById(id);
			Idea idea = new Idea(next_Id, originalI.getContents());
			ideaRepository.save(idea);
			message += "複製も行いました ";
		} catch (Exception e) {
			message += "複製に失敗しました " + e.getMessage() ;
		}

		return message;
	}

}
