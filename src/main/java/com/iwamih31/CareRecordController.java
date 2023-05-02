package com.iwamih31;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/CareRecord")
public class CareRecordController {

	@Autowired
	private CareRecordService careRecordService;

	@GetMapping("/UserList")
	public String userList(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")開始");
		String template = "userList";
		model.addAttribute("title", "利用者一覧表");
		model.addAttribute("date", careRecordService.today());
		model.addAttribute("userList", careRecordService.userList());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")終了");
		return "view";
	}

	@GetMapping("/UserSetting")
	public String userSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserSetting\")開始");
		String template = "userSetting";
		model.addAttribute("title", "利用者設定");
		model.addAttribute("userList", careRecordService.userAll());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/UserSetting\")終了");
		return "view";
	}

	@PostMapping("/User")
	public String user(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/User\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/User\")終了");
		return "redirect:/CareRecord/User?id=" + id + "&date=" + date;
	}

	@GetMapping("/User")
	public String user(
			@Param("id")int id,
			@Param("date")String date,
			@ModelAttribute("action")Action action,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/User\")開始");
		String template = "user";
		model.addAttribute("title", "常時入力");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("actions", careRecordService.actions(id, date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/User\")終了");
		return "view";
	}

	@GetMapping("/UserInsert")
	public String userInsert(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserInsert\")開始");
		model.addAttribute("title", "新規利用者登録");
		model.addAttribute("user", careRecordService.newUser());
		model.addAttribute("id", careRecordService.nextUserID());
		model.addAttribute("blankRooms", careRecordService.blankRooms());
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "userInsert::library");
		model.addAttribute("main", "userInsert::main");
		careRecordService.__consoleOut__("@GetMapping(\"/UserInsert\")終了");
		return "view";
	}

	@PostMapping("/User/Insert")
	public String user_Insert(
			@RequestParam("post_id")int id,
			@ModelAttribute("user")User user,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/User/Insert\")開始");
		String message = careRecordService.user_Insert(user, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/User/Insert\")終了");
		return "redirect:/CareRecord/UserSetting";
	}

	@PostMapping("/UserUpdate")
	public String userUpdate(
			@RequestParam("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")開始");
		String template = "userUpdate";
		model.addAttribute("title", "利用者情報更新");
		model.addAttribute("id", id);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("blankRooms", careRecordService.blankRooms());
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")終了");
		return "view";
	}

	@PostMapping("/User/Update")
	public String user_Update(
			@RequestParam("post_id")int id,
			@ModelAttribute("user")User user,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/User/Update\")開始");
		String message = careRecordService.user_Update(user, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/User/Update\")終了");
		return "redirect:/CareRecord/UserSetting";
	}

	@PostMapping("/Action/Insert")
	public String action_Insert(
			@RequestParam("user_id")int user_id,
			@RequestParam("date")String date,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("action")Action action) {
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Insert\")開始 date=" + date);
		String message = careRecordService.action_Insert(action, user_id, date);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Insert\")終了");
		return "redirect:/CareRecord/User?id=" + user_id + "&date=" + date;
	}

	@PostMapping("/ActionUpdate")
	public String actionUpdate(
			@RequestParam("action_id")int action_id,
			@RequestParam("user_id")int user_id,
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/ActionUpdate\")開始 date=" + date);
		String template = "actionUpdate";
		model.addAttribute("title", "常時入力更新");
		model.addAttribute("user_id", user_id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(user_id));
		model.addAttribute("action", careRecordService.action(action_id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@PostMapping(\"/ActionUpdate\")終了 date=" + date);
		return "view";
	}

	@PostMapping("/Action/Update")
	public String action_Update(
			@RequestParam("action_id")int action_id,
			@RequestParam("user_id")int user_id,
			@RequestParam("post_date")String post_date,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("action")Action action) {
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Update\")開始 date=" + post_date);
		String message = careRecordService.action_Update(action, action_id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Update\")終了");
		return "redirect:/CareRecord/User?id=" + user_id + "&date=" + post_date;
	}

	@PostMapping("/ActionDelete")
	public String actionDelete(
			@RequestParam("action_id")int action_id,
			@RequestParam("user_id")int user_id,
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/actionDelete\")開始 date=" + date);
		String template = "actionDelete";
		model.addAttribute("title", "常時入力データ削除");
		model.addAttribute("user_id", user_id);
		model.addAttribute("action_id", action_id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(user_id));
		model.addAttribute("action", careRecordService.action(action_id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@PostMapping(\"/actionDelete\")終了 date=" + date);
		return "view";
	}

	@PostMapping("/Detail")
	public String detail(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")終了");
		return "redirect:/CareRecord/Detail?id=" + id;
	}

	@GetMapping("/Detail")
	public String detail(
			@Param("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")開始");
		model.addAttribute("title", "詳細情報");
		model.addAttribute("id", id);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "detail::library");
		model.addAttribute("main", "detail::main");
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")終了");
		return "view";
	}

	@PostMapping("/DetailInsert")
	public String detailInsert(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/DetailInsert\")開始");
		String string = "";
		String input = "";
		careRecordService.__consoleOut__("@PostMapping(\"/DetailInsert\")終了");
		return "redirect:/CareRecord/Birthday"
				+ "?id=" + id + "&count=" + 1 + "&string=" + string + "&input=" + input;
	}

	@PostMapping("/DetailUpdate")
	public String detailUpdate(
			@RequestParam("post_id")int id,
			@ModelAttribute("detail")Detail detail,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/DetailUpdate\")開始");
		String message = careRecordService.detailUpdate(detail, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/DetailUpdate\")終了");
		return "redirect:/CareRecord/Detail?id=" + id;
	}

	@PostMapping("/RoutineList")
	public String routineList(
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineList\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineList\")終了");
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@GetMapping("/RoutineList")
	public String routineList(
			@Param("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineList\")開始");
		if(date == null) date = careRecordService.today();
		careRecordService.setRoutineList(date);
		model.addAttribute("title", "定期入力");
		model.addAttribute("date", date);
		model.addAttribute("routineList", careRecordService.routineList(date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "routineList::library");
		model.addAttribute("main", "routineList::main");
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineList\")終了");
		return "view";
	}

	@PostMapping("/RoutineClicked")
	public String routineClicked(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineClicked\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineClicked\"終了");
		return "redirect:/CareRecord/RoutineClicked?id=" + id + "&date=" + date;
	}

	@GetMapping("/RoutineClicked")
	public String routineClicked(
			@Param("id")int id,
			@Param("date")String date,
			@ModelAttribute("routine")Routine routine,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineClicked\")開始");
		if(date == null) date = careRecordService.today();
		careRecordService.setRoutineList(date);
		model.addAttribute("title", "定期入力");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("routineList", careRecordService.routineList(date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "routineClicked::library");
		model.addAttribute("main", "routineClicked::main");
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineClicked\")終了");
		return "view";
	}

	@PostMapping("/Routine")
	public String routine(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/Routine\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Routine\")終了");
		return "redirect:/CareRecord/Routine?id=" + id + "&date=" + date;
	}

	@GetMapping("/Routine")
	public String routine(
			@Param("id")int id,
			@Param("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Routine\")開始");
		String template = "routine";
		model.addAttribute("title", "定時入力データ編集");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("routine", careRecordService.routine(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("todo_actions", careRecordService.todo_actions());
		model.addAttribute("names", careRecordService.names());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/Routine\")終了");
		return "view";
	}

	@PostMapping("/RoutineInsert")
	public String routineInsert(
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineInsert\")開始");
		String template = "routineInsert";
		model.addAttribute("title", "定期入力新規データ作成");
		model.addAttribute("date", date);
		model.addAttribute("routine", careRecordService.newRoutine(date));
		model.addAttribute("todo_actions", careRecordService.todo_actions());
		model.addAttribute("names", careRecordService.names());
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineInsert\")終了");
		return "view";
	}

	@PostMapping("/RoutineAppend")
	public String routineAppend(
			@RequestParam("date")String date,
			Model model) {
			careRecordService.__consoleOut__("@PostMapping(\"/RoutineAppend\")開始");
			String template = "routineAppend";
			model.addAttribute("title", "定期入力データ追加");
			model.addAttribute("date", date);
			model.addAttribute("userList", careRecordService.userAll());
			model.addAttribute("library", template + "::library");
			model.addAttribute("main", template + "::main");
			careRecordService.__consoleOut__("@PostMapping(\"/RoutineAppend\")終了");
		return "view";
	}

	@PostMapping("/RoutineUpdate")
	public String routineUpdate(
			@RequestParam("post_id")int id,
			@RequestParam("post_date")String date,
			@ModelAttribute("routine")Routine routine,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineUpdate\")開始");
		String message = careRecordService.routineUpdate(routine, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineUpdate\")終了");
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@PostMapping("/RoutineDelete")
	public String routineDelete(
			@RequestParam("post_id")int id,
			@RequestParam("post_date")String date,
			Model model) {
			careRecordService.__consoleOut__("@PostMapping(\"/RoutineDelete\")開始");
			String template = "routineDelete";
			model.addAttribute("title", "定期入力データ削除");
			model.addAttribute("id", id);
			model.addAttribute("date", date);
			model.addAttribute("routine", careRecordService.routine(id));
			model.addAttribute("library", template + "::library");
			model.addAttribute("main", template + "::main");
			careRecordService.__consoleOut__("@PostMapping(\"/RoutineDelete\")終了");
		return "view";
	}

	@PostMapping("/Routine/Delete")
	public String routine_Delete(
			@RequestParam("post_date")String date,
			@RequestParam("post_id")int id,
			@RequestParam("select")int select,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Routine/Delete\")開始");
		String message = careRecordService.routine_Delete(id, select);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Routine/Delete\")終了");
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@PostMapping("/Routine/Append")
	public String routine_Append(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/Routine/Append\")開始");
		careRecordService.routine_Append(id, date);
		careRecordService.__consoleOut__("@PostMapping(\"/Routine/Append\")終了");
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@PostMapping("/RoutineDate")
	public String routineDate(
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineDate\")開始");
		String template = "routineDate";
		model.addAttribute("title", "日付変更");
		model.addAttribute("date", date);
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineDate\")終了");
		return "view";
	}

	@GetMapping("/Setting")
	public String setting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")開始");
		model.addAttribute("title", "設定");
		model.addAttribute("library", "setting::library");
		model.addAttribute("main", "setting::main");
		return "view";
	}

	@GetMapping("/ToDoSetting")
	public String todoSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/ToDoSetting\")開始");
		String template = "todoSetting";
		model.addAttribute("title", "ToDo設定");
		model.addAttribute("todo", new ToDo());
		model.addAttribute("todo_list", careRecordService.todoList());
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/ToDoSetting\")終了");
		return "view";
	}

	@PostMapping("/ToDoInsert")
	public String todoInsert(
			@ModelAttribute("todo")ToDo todo){
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoInsert\")開始");
		careRecordService.todoSave(todo);
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoInsert\")終了");
		return "redirect:/CareRecord/ToDoSetting";
	}

	@PostMapping("/ToDoUpdate")
	public String todoUpdate(
			@RequestParam("post_id")int id,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoUpdate\")開始");
		String template = "todoUpdate";
		model.addAttribute("title", "ToDo情報更新");
		model.addAttribute("id", id);
		model.addAttribute("todo", careRecordService.todo(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoUpdate\")終了");
		return "view";
	}

	@PostMapping("/Birthday")
	public String birthday(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/Birthday\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Birthday\")終了");
		return "redirect:/CareRecord/Birthday?id=" + id + "&count=" + count + "&string=" + string + "&input=" + input;
	}

	@GetMapping("/Birthday")
	public String birthday(
			@Param("id")int id,
			@Param("count")int count,
			@Param("string")String string,
			@Param("input")String input,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Birthday\")開始");
		careRecordService.__consoleOut__("string = " + careRecordService.dateStringConnect(count, string, input));
		careRecordService.__consoleOut__("url = " + careRecordService.dateInputUrl(count));
		model.addAttribute("title", "入力");
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("count", count + 1);
		model.addAttribute("label", careRecordService.dateInputLabel(count));
		model.addAttribute("string", careRecordService.dateStringConnect(count, string, input));
		model.addAttribute("options", careRecordService.dateOptions(count, input));
		model.addAttribute("url", careRecordService.dateInputUrl(count));
		model.addAttribute("library", "input::library");
		model.addAttribute("main", "input::main");
		careRecordService.__consoleOut__("@GetMapping(\"/Birthday\")" + count + "終了");
		return "view";
	}

	@PostMapping("/BirthdayUpdate")
	public String birthdayUpdate(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/BirthdayUpdate\")開始");
		careRecordService.__consoleOut__("count = " + count);
		careRecordService.birthdayUpdate(id, string, input);
		careRecordService.__consoleOut__("@PostMapping(\"/BirthdayUpdate\")終了");
		return "redirect:/CareRecord/Detail?id=" + id;
	}

	@PostMapping("/ActionDate")
	public String actionDate(
			@RequestParam("id")int id,
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/ActionDate\")開始");
		String template = "actionDate";
		model.addAttribute("title", "日付変更");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		careRecordService.__consoleOut__("@GetMapping(\"/ActionDate\")終了");
		return "view";
	}

}
