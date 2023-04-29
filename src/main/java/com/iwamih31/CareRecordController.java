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
	public String userList(Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")開始");
		model.addAttribute("title", "利用者一覧表");
		model.addAttribute("date", careRecordService.today());
		model.addAttribute("userList", careRecordService.userList());
		model.addAttribute("library", "userList::library");
		model.addAttribute("main", "userList::main");
		return "view";
	}

	@GetMapping("/UserEntry")
	public String userEntry(Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserEntry\")開始");
		model.addAttribute("title", "利用者登録画面");
		model.addAttribute("userList", careRecordService.userList());
		model.addAttribute("library", "userEntry::library");
		model.addAttribute("main", "userEntry::main");
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
		model.addAttribute("title", "常時入力");
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("records", careRecordService.records(id, date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "user::library");
		model.addAttribute("main", "user::main");
		careRecordService.__consoleOut__("@GetMapping(\"/User\")終了");
		return "view";
	}

	@GetMapping("/UserInsert")
	public String userInsert(Model model) {
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

	@PostMapping("/UserInsert")
	public String userInsert(
			@RequestParam("post_id")int id,
			@ModelAttribute("user")User user,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/UserInsert\")開始");
		String message = careRecordService.User_Insert(user, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/UserInsert\")終了");
		return "redirect:/CareRecord/UserEntry";
	}

	@PostMapping("/Record")
	public String record(
			@RequestParam("post_id")int id,
			@RequestParam("post_date")String date,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("action")Action action) {
		System.out.println("record開始");
		String message = careRecordService.record_Insert(action, id, date);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/CareRecord/User?id=" + id + "&date=" + date;
	}

	@PostMapping("/Detail")
	public String detail(@RequestParam("id")int id) {
		return "redirect:/CareRecord/Detail?id=" + id;
	}

	@GetMapping("/Detail")
	public String detail(@Param("id")int id, Model model) {
		careRecordService.__consoleOut__("detail開始");
		model.addAttribute("title", "詳細情報");
		model.addAttribute("id", id);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("library", "detail::library");
		model.addAttribute("main", "detail::main");
		careRecordService.__consoleOut__("detail終了");
		return "view";
	}

	@PostMapping("/DetailInsert")
	public String detailInsert(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/DetailInsert\")開始");
		String string = "";
		String input = "";
		return "redirect:/CareRecord/Birthday"
				+ "?id=" + id + "&count=" + 1 + "&string=" + string + "&input=" + input;
	}

	@PostMapping("/DetailUpdate")
	public String detailUpdate(
			@RequestParam("post_id")int id,
			@ModelAttribute("detail")Detail detail,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("detailUpdate開始");
		String message = careRecordService.detailUpdate(detail, id);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/CareRecord/Detail?id=" + id;
	}

	@PostMapping("/RoutineList")
	public String routineList(@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/DetailUpdate\")開始");
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@GetMapping("/RoutineList")
	public String routineList(@Param("date")String date, Model model) {
		if(date == null) date = careRecordService.today();
		careRecordService.setRoutineList(date);
		model.addAttribute("title", "定期入力");
		model.addAttribute("date", date);
		model.addAttribute("routineList", careRecordService.routineList(date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "routineList::library");
		model.addAttribute("main", "routineList::main");
		return "view";
	}

	@PostMapping("/RoutineClicked")
	public String routineClicked(@RequestParam("id")int id,@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineClicked\")開始");
		return "redirect:/CareRecord/RoutineClicked?id=" + id + "&date=" + date;
	}

	@GetMapping("/RoutineClicked")
	public String routineClicked(
			@Param("id")int id,
			@Param("date")String date,
			@ModelAttribute("routine")Routine routine,
			Model model) {
		if(date == null) date = careRecordService.today();
		careRecordService.setRoutineList(date);
		model.addAttribute("title", "定期入力");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("routineList", careRecordService.routineList(date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "routineClicked::library");
		model.addAttribute("main", "routineClicked::main");
		return "view";
	}

	@PostMapping("/RoutineUpdate")
	public String routineUpdate(
			@RequestParam("post_id")int id,
			@RequestParam("post_date")String date,
			@ModelAttribute("routine")Routine routine,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("routineUpdate開始");
		String message = careRecordService.routineUpdate(routine, id);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/CareRecord/RoutineList?date=" + date;
	}

	@PostMapping("/Routine")
	public String routine(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		careRecordService.__consoleOut__("@PostMapping(\"/Routine\")開始");
		return "redirect:/CareRecord/Routine?id=" + id + "&date=" + date;
	}

	@GetMapping("/Routine")
	public String routine(
			@Param("id")int id,
			@Param("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Routine\")開始");
		model.addAttribute("title", "定時入力データ編集");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("routine", careRecordService.routine(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("names", careRecordService.names());
		model.addAttribute("todoNames", careRecordService.todoNames());
		model.addAttribute("library", "routine::library");
		model.addAttribute("main", "routine::main");
		careRecordService.__consoleOut__("@GetMapping(\"/Routine\")終了");
		return "view";
	}

	@GetMapping("/Setting")
	public String setting(Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")開始");
		model.addAttribute("title", "設定");
		model.addAttribute("library", "setting::library");
		model.addAttribute("main", "setting::main");
		return "view";
	}

	@PostMapping("/ToDoUpdate")
	public String todoUpdate(
			@ModelAttribute("todo")ToDo todo) {
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoUpdate\")開始");
		careRecordService.todoSave(todo);
		return "redirect:/CareRecord/ToDoEntry";
	}

	@GetMapping("/ToDoEntry")
	public String todoEntry(Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")開始");
		model.addAttribute("title", "設定");
		model.addAttribute("todo", new ToDo());
		model.addAttribute("todo_list", careRecordService.todoList());
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("library", "todoEntry::library");
		model.addAttribute("main", "todoEntry::main");
		return "view";
	}

	@PostMapping("/Birthday")
	public String birthday(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/Birthday\")開始");
		return "redirect:/CareRecord/Birthday?id=" + id + "&count=" + count + "&string=" + string + "&input=" + input;
	}

	@GetMapping("/Birthday")
	public String birthday(
			@Param("id")int id,
			@Param("count")int count,
			@Param("string")String string,
			@Param("input")String input,
			Model model) {
		careRecordService.__consoleOut__("birthday" + count + "開始");
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
		careRecordService.__consoleOut__("birthday" + count + "終了");
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
		return "redirect:/CareRecord/Detail?id=" + id;
	}

}
