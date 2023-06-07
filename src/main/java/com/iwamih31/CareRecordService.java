package com.iwamih31;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

	public List<User> user_List() {
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

	public String event_Insert(Event event, String datetime_Str) {
		__consoleOut__("event_Insert開始");
		String message = "行事予定";
		try {
			if (!datetime_Str.isBlank()) event.setDatetime(localDateTime(datetime_Str));
			event = eventRepository.save(event);
			Integer event_Id = event.getId();
			message = "ID = " + event_Id + " の行事予定を登録しました";
			//行事計画情報初期化
			Idea idea = new Idea(event_Id, "");
			message += "　" + idea_Update(idea, event_Id);
		} catch (Exception e) {
			message += "行事予定の登録が正常に行われませんでした " + e.getMessage();
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

	public String todo_Update(ToDo todo) {
		__consoleOut__("todo_Update開始");
		String message = null;
		try {
			todo = todoRepository.save(todo);
			message = "ID = " + todo.getId() + " のToDoデータを更新しました";
		} catch (Exception e) {
			message += "ToDoデータの更新に失敗しました " + e.getMessage();
		}
		__consoleOut__("todo_Update終了");
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

	public  List<Action> action_List(int user_id, String date_Like) {
		List<Action> action_List;
		if (user_id > 0) {
			action_List = actionRepository.action_List(user_id, date_Like);
		} else {
			action_List = actionRepository.action_List_All(date_Like);
		}
		return action_List;
	}

	public List<Routine> routine_Report(int user_id, String date_Like) {
		List<Routine> routineReport;
		if (user_id > 0) {
			User user = userRepository.getReferenceById(user_id);
			routineReport = routineRepository.routine_Report(user.getRoom(), user.getName(), date_Like);
		} else {
			routineReport = routineRepository.routine_Report_All(date_Like);
		}
		return routineReport;
	}

	public List<Office> office_Report() {
		return officeRepository.findAll();
	}

	/** 常時入力データをExcelとして出力 */
	public String action_Output_Excel(int user_id, String date_Like, HttpServletResponse response) {
		__consoleOut__("action_Output_Excel(int user_Id, String date_Like, HttpServletResponse response) 開始" + user_id + date_Like);
		String name_Head = "常時入力";
		String file_Name = with_Now(name_Head) + ".xlsx";
		String sheet_Name = with_Now(name_Head);
		String message = file_Name + " のダウンロード";
		try (Workbook workbook = new XSSFWorkbook();
							OutputStream outputStream = response.getOutputStream()){
			Sheet sheet = workbook.createSheet(sheet_Name);
			// 使用するフォントを定義
			Font font = workbook.createFont();
			font.setFontName("游ゴシック");
			// ヘッダー行
			Set[] label_Set = LabelSet.action_Set;
			Row row = sheet.createRow(0);
			// セルスタイルを定義
			CellStyle header_CellStyle = workbook.createCellStyle();
			// 色指定
			header_CellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			header_CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 罫線指定
			header_CellStyle.setBorderTop(BorderStyle.THIN);
			header_CellStyle.setBorderBottom(BorderStyle.THIN);
			header_CellStyle.setBorderLeft(BorderStyle.THIN);
			header_CellStyle.setBorderRight(BorderStyle.THIN);
			// 中央揃え
			header_CellStyle.setAlignment(HorizontalAlignment.CENTER);
			// フォントをセット
			header_CellStyle.setFont(font);
			// label_Set 分ループ
			for (int i = 0; i < label_Set.length; i++) {
				//	セルにバリューをセット
				Cell cell = row.createCell(i);
				cell.setCellValue(label_Set[i].name);
				// セルにセルスタイルを適用
				cell.setCellStyle(header_CellStyle);
				// 列幅設定（1文字分の横幅 × 文字数 ＋ 微調整分の幅）
				sheet.setColumnWidth(i, 512 * label_Set[i].value + 0);
			}
			// データ行
			List<Action> action_List = action_List(user_id, date_Like);
			// セルスタイルを定義
			CellStyle data_CellStyle = workbook.createCellStyle();
			// 罫線指定
			data_CellStyle.setBorderTop(BorderStyle.THIN);
			data_CellStyle.setBorderBottom(BorderStyle.THIN);
			data_CellStyle.setBorderLeft(BorderStyle.THIN);
			data_CellStyle.setBorderRight(BorderStyle.THIN);
			// フォントをセット
			data_CellStyle.setFont(font);
			for (int i = 0; i < action_List.size(); i++) {
				Action action = action_List.get(i);
				User user = user(action.getUser_id());
				String[] value = {
						String.valueOf(action.getId()),
						String.valueOf(user.getRoom()),
						user.getName(),
						action.getDate(),
						action.getTime(),
						action.getSleep(),
						action.getWater(),
						action.getPee1(),
						action.getPee2(),
						action.getPoop(),
						action.getLaxative(),
						action.getMedicine(),
						action.getOintment(),
						action.getSituation(),
				};
				// 行を指定
				row = sheet.createRow(i + 1);
				for (int j = 0; j < value.length; j++) {
					// セルを指定
					Cell cell = row.createCell(j);
					// セルに値をセット
					if(is_Double(value[j])){
						cell.setCellValue(Double.parseDouble(value[j]));
					} else {
						cell.setCellValue(value[j].toString());
					}
					// セルにセルスタイルを適用
					cell.setCellStyle(data_CellStyle);
					// 最後の行のみ
					if (i == action_List.size() - 1) {
						// 列幅設定（オート）
						sheet.autoSizeColumn(j);
					}
				}
			}
			// ファイル名を指定して保存
			String encodedFilename = URLEncoder.encode(file_Name, "UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFilename + "\"");
			response.setCharacterEncoding("UTF-8");
			workbook.write(outputStream);
			message += "が完了しました";
		} catch (IOException e) {
			message += "が正常に完了出来ませんでした";
			__consoleOut__(e.getMessage());
		}
		__consoleOut__("action_Output_Excel(int user_Id, String date_Like, HttpServletResponse response) 終了");
		return message;
	}

	/** 定期入力データをExcelとして出力 */
	public String routine_Output_Excel(int user_id, String date_Like, HttpServletResponse response) {
		__consoleOut__("routine_Output_Excel(int user_Id, String date_Like, HttpServletResponse response) 開始" + user_id + date_Like);
		String name_Head = "定期入力";
		String file_Name = with_Now(name_Head) + ".xlsx";
		String sheet_Name = with_Now(name_Head);
		String message = file_Name + " のダウンロード";
		try (Workbook workbook = new XSSFWorkbook();
							OutputStream outputStream = response.getOutputStream()){
			Sheet sheet = workbook.createSheet(sheet_Name);
			// ヘッダー行
			Row row = sheet.createRow(0);
			Set[] label_Set = LabelSet.routineReport_Set;
			for (int i = 0; i < label_Set.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(label_Set[i].name);
				// セルスタイルを定義
				CellStyle cellStyle = workbook.createCellStyle();
				// 色指定
				cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				// 罫線指定
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				// 中央揃え
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				// フォントを指定
				Font font = workbook.createFont();
				font.setFontName("游ゴシック");
				cellStyle.setFont(font);
				// セルにセルスタイルを適用
				cell.setCellStyle(cellStyle);
				// 列幅設定（1文字分の横幅 × 文字数 ＋ 微調整分の幅）
				sheet.setColumnWidth(i, 512 * label_Set[i].value + 0);
			}
			// データ行
			List<Routine> routine_Report = routine_Report(user_id, date_Like);
			for (int i = 0; i < routine_Report.size(); i++) {
				Routine routine = routine_Report.get(i);
				String[] value = {
						routine.getDate(),
						String.valueOf(routine.getRoom()),
						routine.getName(),
						routine.getTime(),
						routine.getTodo(),
						routine.getAction(),
						String.valueOf(routine.getId()),
				};
				// 行を指定
				row = sheet.createRow(i + 1);
				for (int j = 0; j < value.length; j++) {
					// セルを指定
					Cell cell = row.createCell(j);
					// セルに値をセット
					if(is_Double(value[j])){
						cell.setCellValue(Double.parseDouble(value[j]));
					} else {
						cell.setCellValue(value[j].toString());
					}
					// セルスタイルを定義
					CellStyle cellStyle = workbook.createCellStyle();
					// 罫線指定
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderBottom(BorderStyle.THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					// フォントを指定
					Font font = workbook.createFont();
					font.setFontName("游ゴシック");
					cellStyle.setFont(font);
					// セルにセルスタイルを適用
					cell.setCellStyle(cellStyle);
					// 最後の行のみ
					if (i == routine_Report.size() - 1) {
						// 列幅設定（オート）
						sheet.autoSizeColumn(j);
					}
				}
			}
			// ファイル名を指定して保存
			String encodedFilename = URLEncoder.encode(file_Name, "UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFilename + "\"");
			response.setCharacterEncoding("UTF-8");
			workbook.write(outputStream);
			message += "が完了しました";
		} catch (IOException e) {
			message += "が正常に完了出来ませんでした";
			__consoleOut__(e.getMessage());
		}
		__consoleOut__("routine_Output_Excel(int user_Id, String date_Like, HttpServletResponse response) 終了");
		return message;
	}

	/** 事業所データをExcelとして出力 */
	public String office_Output_Excel(HttpServletResponse response) {
		__consoleOut__("office_Output_Excel(HttpServletResponse response) 開始");
		Excel excel = new Excel();
		String message = null;
		String[] column_Names = Set.get_Name_Set(LabelSet.officeReport_Set);
		int[] column_Width = Set.get_Value_Set(LabelSet.officeReport_Set);
		List<Office> office_Report = office_Report();
		String[][] table_Data = new String[office_Report.size()][];
		for (int i = 0; i < table_Data.length; i++) {
			Office office = office_Report.get(i);
			table_Data[i] = new String[] {
					String.valueOf(office.getId()),
					String.valueOf(office.getItem_name()),
					String.valueOf(office.getItem_value())
				};
		}
		message = excel.output_Excel("事業所", column_Names, column_Width, table_Data, response);
		__consoleOut__("office_Output_Excel(HttpServletResponse response) 終了");
		return message;
	}

	private boolean is_Double(String string) {
		boolean is_Double = true;
		try {
			__consoleOut__("String = " + Double.parseDouble(string) + " は Double に変換出来ます");
		} catch (Exception e) {
			__consoleOut__("string = " + string + " は Double に変換出来ません");
			is_Double = false;
		}
		return is_Double;
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

	public  List<Detail> detail_All() {
			return detailRepository.findAll();
		}

	public String this_Month() {
		// 今日の日付を取得
		LocalDateTime now = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM");
		return dateTimeFormatter.format(now);
	}

	public String last_Month() {
		// 今日の日付を取得
			LocalDateTime now = LocalDateTime.now();
		// 1つ前の月に変換
		LocalDateTime last_Month_Now = now.minusMonths(1);
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM");
		return dateTimeFormatter.format(last_Month_Now);
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
			message += "更新に失敗しました " + e.getMessage();
		}
		__consoleOut__("event_Update終了");
		return message;
	}

	private LocalDateTime localDateTime(String datetime_Str) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		return LocalDateTime.parse(datetime_Str, formatter);
	}

	public void setRoutineList(String date) {
		List<ToDo> todoList = todoList();
		for (ToDo todo : todoList) {
			List<User> userList = user_List();
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

	/** 現在時刻 */
	public String time() {
		// 現在時刻を取得
		LocalDateTime now = LocalDateTime.now();
		// 表示形式を指定
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		return dateTimeFormatter.format(now);
	}

	/** 現在時刻（5分刻み） */
	public String part_time() {
		String time = time();
		// 末尾を 0 または 5 に変換して time に代入
		// time の1の位を取得
		String minutesLast = time.substring(time.length()-1, time.length());
		// 0 にするか 5 にするか判定
		if (Integer.parseInt(minutesLast) < 5) {
			minutesLast = "0";
		} else {
			minutesLast = "5";
		}
		// 末尾を変換
		time = time.substring(0, time.length()-1) + minutesLast;
		__consoleOut__("part_time = " + time);
		return time;
	}

	public String[] dateOptions(int count, String input) {
		String[] options = null;
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

	public void birthday_Update(int id, String birthday) {
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

	public void level_Update(int id, String level) {
		Detail detail;
	// idが在れば level を書き換え 無ければ level 入りの detail を新規作成
		if (detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
			detail.setLevel(level);
		} else {
			detail = new Detail(id, "" ,level , "");
		}
		// save() 実行
		detailRepository.save(detail);
	}

	public void move_in_Update(int id, String move_in) {
		Detail detail;
		// idが在れば move_in を書き換え 無ければ move_in 入りの detail を新規作成
		if (detailRepository.existsById(id)) {
			detail = detailRepository.getReferenceById(id);
			detail.setMove_in(move_in);
		} else {
			detail = new Detail(id, "", "", move_in);
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

	public void todo_Insert(ToDo todo) {
		todoRepository.save(todo);
	}

	public String[] user_Names() {
		return userRepository.names();
	}

	public  List<ToDo> todoList() {
		return todoRepository.todoList();
	}

	public Object todo(int id) {
		return todoRepository.getReferenceById(id);
	}

	public String[] todo_actions() {
		return todoRepository.actions();
	}

	public List<Integer> blankRooms() {
		List<Integer> blankRooms = new ArrayList<>();
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

	public Event new_Event() {
		return new Event(next_Event_Id(), "", null, "");
	}

	public Object new_Action() {
		Action action = new Action();
		action.setTime(part_time());
		return action;
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

	public String event_Delete(int id) {
		__consoleOut__("event_Delete(int id)開始");
		String message = "ID = " + id + " のデータ";
		try {
			eventRepository.deleteById(id);
			message += "を削除しました";
		} catch (Exception e) {
			message += "削除に失敗しました " + e.getMessage();
		}
		__consoleOut__("event_Delete(int id)終了");
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

	public void __consoleOut__(String message) {
		System.out.println("");
		System.out.println(message);
		System.out.println("");
	}

	private String with_Now(String head_String) {
		String now = now().replaceAll("[^0-9]", ""); // 現在日時の数字以外を "" に変換
//	String now = now().replaceAll("[^\\d]", "");  ←こちらでもOK
		now = now.substring(0, now.length()-3); // 後ろから3文字を取り除く
		return head_String + now;
	}

	/** 利用者情報データをExcelとして出力 */
	public String detail_Output_Excel(HttpServletResponse response) {
		__consoleOut__("detail_Output_Excel(HttpServletResponse response) 開始");
		String name_Head = "利用者情報";
		String file_Name = with_Now(name_Head) + ".xlsx";
		String sheet_Name = with_Now(name_Head);
		String message = file_Name + " のダウンロード";
		String[] label_Set = LabelSet.detail_List;
		int[] width_Set = LabelSet.detail_List_width;
		try (Workbook workbook = new XSSFWorkbook();
        	OutputStream outputStream = response.getOutputStream()){
			Sheet sheet = workbook.createSheet(sheet_Name);
			// 使用するフォントを定義
			Font font = workbook.createFont();
			font.setFontName("游ゴシック");
			// ヘッダー行
			// セルスタイルを定義
			CellStyle header_CellStyle = workbook.createCellStyle();
			// 色指定
			header_CellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			header_CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 罫線指定
			header_CellStyle.setBorderTop(BorderStyle.THIN);
			header_CellStyle.setBorderBottom(BorderStyle.THIN);
			header_CellStyle.setBorderLeft(BorderStyle.THIN);
			header_CellStyle.setBorderRight(BorderStyle.THIN);
			// 中央揃え
			header_CellStyle.setAlignment(HorizontalAlignment.CENTER);
			// フォントをセット
			header_CellStyle.setFont(font);
			// label_Set 分ループ
			Row row = sheet.createRow(0);
			for (int i = 0; i < label_Set.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(label_Set[i]);
				// セルにセルスタイルを適用
				cell.setCellStyle(header_CellStyle);
				// 列幅設定（1文字分の横幅 × 文字数 ＋ 微調整分の幅）
				sheet.setColumnWidth(i, 512 * width_Set[i] + 0);
			}
			// データ行
			List<User> userList = user_All();
			List<Detail> detailList = detail_All();
			// セルスタイルを定義
			CellStyle data_CellStyle = workbook.createCellStyle();
			// 罫線指定
			data_CellStyle.setBorderTop(BorderStyle.THIN);
			data_CellStyle.setBorderBottom(BorderStyle.THIN);
			data_CellStyle.setBorderLeft(BorderStyle.THIN);
			data_CellStyle.setBorderRight(BorderStyle.THIN);
			// フォントをセット
			data_CellStyle.setFont(font);
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				Detail detail = detailList.get(i);
				String[] value = {
						String.valueOf(user.getId()),
						String.valueOf(user.getRoom()),
						user.getName(),
						detail.getBirthday(),
						detail.getLevel(),
						detail.getMove_in(),
						user.getUse()
						};
				// 行を指定
				row = sheet.createRow(i + 1);
				for (int j = 0; j < value.length; j++) {
					// セルを定義
					Cell cell = row.createCell(j);
					// セルに値をセット
					if(is_Double(value[j])){
						cell.setCellValue(Double.parseDouble(value[j]));
					} else {
						cell.setCellValue(value[j].toString());
					}
					// セルにセルスタイルを適用
					cell.setCellStyle(data_CellStyle);
					// 最後の行のみ
					if (i == userList.size() - 1) {
						// 列幅設定（オート）
						sheet.autoSizeColumn(j);
					}
				}
			}
	    // ファイル名を指定して保存
			String encodedFilename = URLEncoder.encode(file_Name, "UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFilename + "\"");
	    response.setCharacterEncoding("UTF-8");
	    workbook.write(outputStream);
			message += "が完了しました";
			workbook.close();
			message += " workbook を close() しました";
		} catch (IOException e) {
			message += "が正常に完了出来ませんでした";
			__consoleOut__(e.getMessage());
		}
		__consoleOut__("detail_Output_Excel(HttpServletResponse response) 終了");
		return message;
	}

	public String routine_insert(Routine routine) {
		__consoleOut__("routineUpdate開始");
		String data = "定期入力データ";
		String message = null;
		try {
			routine = routineRepository.save(routine);
			message = "ID = " + routine.getId() + " の" + data + "を更新しました";
		} catch (Exception e) {
			message = data + "の登録に失敗しました " + e.getMessage();
		}
		__consoleOut__("routineUpdate終了");
		return message;
	}

	public int year(String date) {
		return Integer.parseInt(date.split("/")[0].split("-")[0].split("年")[0]);
	}

	public String month_day(String date) {
		String month_day = date.split("/")[1] + "/" + date.split("/")[2];
		__consoleOut__(month_day);
		return month_day;
	}

	public String user_Name(int id) {
		User user = user(id);
		return user.getRoom() + " " + user.getName();
	}

}
