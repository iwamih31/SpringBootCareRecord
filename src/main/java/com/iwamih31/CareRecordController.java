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
		add_View_Data_(model, "userList", "利用者一覧");
		model.addAttribute("date", careRecordService.today());
		model.addAttribute("userList", careRecordService.userList());
		careRecordService.__consoleOut__("@GetMapping(\"/UserList\")終了");
		return "view";
	}

	@GetMapping("/UserSetting")
	public String userSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserSetting\")開始");
		add_View_Data_(model, "userSetting", "利用者設定");
		model.addAttribute("userList", careRecordService.user_All());
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
		add_View_Data_(model, "user", "常時入力");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("actions", careRecordService.actions(id, date));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/User\")終了");
		return "view";
	}

	@GetMapping("/UserInsert")
	public String userInsert(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserInsert\")開始");
		add_View_Data_(model, "userInsert", "新規利用者登録");
		model.addAttribute("user", careRecordService.new_User());
		model.addAttribute("id", careRecordService.next_User_Id());
		model.addAttribute("blankRooms", careRecordService.blankRooms());
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/UserInsert\")終了");
		return "view";
	}

	@GetMapping("/EventList")
	public String eventList(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/EventList\")開始");
		add_View_Data_(model, "eventList", "行事予定一覧");
		model.addAttribute("eventList", careRecordService.eventList());
		careRecordService.__consoleOut__("@GetMapping(\"/EventList\")終了");
		return "view";
	}

	@GetMapping("/EventSetting")
	public String eventSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/EventSetting\")開始");
		add_View_Data_(model, "eventSetting", "行事設定");
		model.addAttribute("eventList", careRecordService.event_All());
		careRecordService.__consoleOut__("@GetMapping(\"/EventSetting\")終了");
		return "view";
	}

	@GetMapping("/EventInsert")
	public String eventInsert(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/EventInsert\")開始");
		add_View_Data_(model, "eventInsert", "新規行事登録");
		model.addAttribute("event", careRecordService.new_Event());
		model.addAttribute("id", careRecordService.next_Event_Id());
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/EventInsert\")終了");
		return "view";
	}

	@PostMapping("/Event/Insert")
	public String event_Insert(
			@RequestParam("id")int id,
			@RequestParam("date_time")String date_time,
			@ModelAttribute("event")Event event,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Insert\")開始");
		String message = careRecordService.event_Insert(event, id, date_time);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Insert\")終了");
		return "redirect:/CareRecord/EventSetting";
	}

	@PostMapping("/Event")
	public String event(
			@RequestParam("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Event\")開始 ");
		add_View_Data_(model, "event", "行事予定");
		model.addAttribute("id", id);
		model.addAttribute("event", careRecordService.event(id));
		model.addAttribute("idea", careRecordService.idea(id));
		careRecordService.__consoleOut__("@GetMapping(\"/Event\")終了");
		return "view";
	}
	
	@PostMapping("/EventUpdate")
	public String eventUpdate(
			@RequestParam("id")int id
			) {
		careRecordService.__consoleOut__("@PostMapping(\"/EventUpdate\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/EventUpdate\")終了");
		return "redirect:/CareRecord/EventUpdate?id=" + id;
	}

	@GetMapping("/EventUpdate")
	public String eventUpdate(
			@Param("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/EventUpdate\")開始 ");
		add_View_Data_(model, "eventUpdate", "行事予定更新");
		model.addAttribute("id", id);
		model.addAttribute("event", careRecordService.event(id));
		model.addAttribute("idea", careRecordService.idea(id));
		careRecordService.__consoleOut__("@GetMapping(\"/EventUpdate\")終了");
		return "view";
	}

	@PostMapping("/Event/Update")
	public String event_Update(
			@RequestParam("id")int id,
			@RequestParam("date_time")String date_time,
			@ModelAttribute("idea")Event event,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Update\")開始");
		String message = careRecordService.event_Update(event, id, date_time);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Update\")終了");
		return "redirect:/CareRecord/EventSetting";
	}

	@PostMapping("/Event/Copy")
	public String event_Copy(
			@RequestParam("id")int id,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Copy\")開始");
		String message = careRecordService.event_Copy(id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Event/Copy\")終了");
		return "redirect:/CareRecord/EventSetting";
	}

	@PostMapping("/Idea")
	public String idea(
			@RequestParam("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Idea\")開始 ");
		add_View_Data_(model, "idea", "行事計画");
		model.addAttribute("id", id);
		model.addAttribute("idea", careRecordService.idea(id));
		careRecordService.__consoleOut__("@GetMapping(\"/Idea\")終了");
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
			@RequestParam("id")int id
) {
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")終了");
		return "redirect:/CareRecord/UserUpdate?id=" + id;
	}

	@GetMapping("/UserUpdate")
	public String userUpdate(
			@Param("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/UserUpdate\")開始");
		add_View_Data_(model, "userUpdate", "利用者情報更新");
		model.addAttribute("id", id);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("blankRooms", careRecordService.blankRooms());
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/UserUpdate\")終了");
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

	@GetMapping("/Office")
	public String office(
			@Param("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Office\")開始");
		add_View_Data_(model, "office", "事業所情報");
		String[] item_Names = careRecordService.office_Item_Names();
		model.addAttribute("name", item_Names[0]);
		model.addAttribute("department", item_Names[1]);
		model.addAttribute("office_data", careRecordService.office_All());
		careRecordService.__consoleOut__("@GetMapping(\"/Office\")終了");
		return "view";
	}

	@GetMapping("/OfficeSetting")
	public String officeSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/OfficeSetting\")開始");
		add_View_Data_(model, "officeSetting", "事業所設定");
		model.addAttribute("officeList", careRecordService.office_All());
		careRecordService.__consoleOut__("@GetMapping(\"/OfficeSetting\")終了");
		return "view";
	}

	@GetMapping("/OfficeInsert")
	public String officeInsert(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/OfficeInsert\")開始");
		add_View_Data_(model, "officeInsert", "新規項目追加");
		model.addAttribute("office", careRecordService.new_Office());
		model.addAttribute("next_id", careRecordService.next_Office_Id());
		careRecordService.__consoleOut__("@GetMapping(\"/OfficeInsert\")終了");
		return "view";
	}

	@PostMapping("/Office/Insert")
	public String office_Insert(
			@RequestParam("post_id")int id,
			@ModelAttribute("office")Office office,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Office/Insert\")開始");
		String message = careRecordService.office_Insert(office, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Office/Insert\")終了");
		return "redirect:/CareRecord/OfficeSetting";
	}

	@PostMapping("/OfficeUpdate")
	public String officeUpdate(
			@RequestParam("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")開始");
		add_View_Data_(model, "officeUpdate", "事業所情報更新");
		model.addAttribute("id", id);
		model.addAttribute("office", careRecordService.office(id));
		careRecordService.__consoleOut__("@PostMapping(\"/UserUpdate\")終了");
		return "view";
	}

	@PostMapping("/Office/Update")
	public String office_Update(
			@RequestParam("post_id")int id,
			@ModelAttribute("office")Office office,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Office/Update\")開始");
		String message = careRecordService.office_Update(office, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Office/Update\")終了");
		return "redirect:/CareRecord/OfficeSetting";
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
		add_View_Data_(model, "actionUpdate", "常時入力更新");
		model.addAttribute("user_id", user_id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(user_id));
		model.addAttribute("action", careRecordService.action(action_id));
		model.addAttribute("options", careRecordService.options());
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
			@RequestParam("post_date")String post_date,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/actionDelete\")開始 date=" + post_date);
		add_View_Data_(model, "actionDelete", "常時入力データ削除");
		model.addAttribute("user_id", user_id);
		model.addAttribute("action_id", action_id);
		model.addAttribute("date", post_date);
		model.addAttribute("user", careRecordService.user(user_id));
		model.addAttribute("action", careRecordService.action(action_id));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@PostMapping(\"/actionDelete\")終了 date=" + post_date);
		return "view";
	}

	@PostMapping("/Action/Delete")
	public String action_Delete(
			@RequestParam("action_id")int action_id,
			@RequestParam("user_id")int user_id,
			@RequestParam("select")int select,
			@RequestParam("post_date")String post_date,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Update\")開始 date=" + post_date);
		String message = careRecordService.action_Delete(action_id ,select);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Action/Update\")終了");
		return "redirect:/CareRecord/User?id=" + user_id + "&date=" + post_date;
	}

	@PostMapping("/Detail")
	public String detail(
			@RequestParam("id")int id,
			@RequestParam("date")String date
			) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")終了");
		return "redirect:/CareRecord/Detail?id=" + id + "&date=" + date;
	}

	@GetMapping("/Detail")
	public String detail(
			@Param("id")int id,
			@Param("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")開始");
		add_View_Data_(model, "detail", "利用者詳細情報");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")終了");
		return "view";
	}

	@GetMapping("/DetailList")
	public String detailList(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/DetailList\")開始");
		add_View_Data_(model, "detailList", "利用者情報印刷");
		model.addAttribute("userList", careRecordService.user_All());
		model.addAttribute("detailList", careRecordService.detailAll());
		careRecordService.__consoleOut__("@GetMapping(\"/DetailList\")終了");
		return "view";
	}

	@PostMapping("/DetailSetting")
	public String detailSetting(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")終了");
		return "redirect:/CareRecord/DetailSetting?id=" + id;
	}

	@PostMapping("/Detail/Output/Excel")
	public String detail_Output_Excel(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")開始");
		careRecordService.detail_Output_Excel();
		careRecordService.__consoleOut__("@PostMapping(\"/Detail\")終了");
		return "redirect:/CareRecord/DetailSetting?id=" + id;
	}

	@GetMapping("/DetailSetting")
	public String detailSetting(
			@Param("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")開始");
		add_View_Data_(model, "detailSetting", "詳細情報");
		model.addAttribute("id", id);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/Detail\")終了");
		return "view";
	}

	@PostMapping("Detail/Birthday/Update")
	public String detail_Birthday_Update(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Birthday/Update\")開始");
		String string = "";
		String input = "";
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Birthday/Update\")終了");
		return "redirect:/CareRecord/Birthday"
				+ "?id=" + id + "&count=" + 1 + "&string=" + string + "&input=" + input;
	}

	@PostMapping("Detail/Move_in/Update")
	public String detail_Move_in_Update(
			@RequestParam("id")int id) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Move_in/Update\")開始");
		String string = "";
		String input = "";
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Move_in/Update\")終了");
		return "redirect:/CareRecord/Move_in"
		+ "?id=" + id + "&count=" + 1 + "&string=" + string + "&input=" + input;
	}

	@PostMapping("/Detail/Update")
	public String detail_Update(
			@RequestParam("id")int id,
			@ModelAttribute("detail")Detail detail,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Update\")開始");
		String message = careRecordService.detail_Update(detail, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Detail/Update\")終了");
		return "redirect:/CareRecord/UserUpdate?id=" + id;
	}

	@PostMapping("/Idea/Update")
	public String idea_Update(
			@RequestParam("id")int id,
			@ModelAttribute("idea")Idea idea,
			RedirectAttributes redirectAttributes) {
		careRecordService.__consoleOut__("@PostMapping(\"/Idea/Update\")開始");
		String message = careRecordService.idea_Update(idea, id);
		redirectAttributes.addFlashAttribute("message", message);
		careRecordService.__consoleOut__("@PostMapping(\"/Idea/Update\")終了");
		return "redirect:/CareRecord/EventUpdate?id=" + id;
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
		add_View_Data_(model, "routineList", "定期入力");
		model.addAttribute("date", date);
		model.addAttribute("routineList", careRecordService.routineList(date));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineList\")終了");
		return "view";
	}

//	@PostMapping("/RoutineClicked")
//	public String routineClicked(
//			@RequestParam("id")int id,
//			@RequestParam("date")String date) {
//		careRecordService.__consoleOut__("@PostMapping(\"/RoutineClicked\")開始");
//		careRecordService.__consoleOut__("@PostMapping(\"/RoutineClicked\"終了");
//		return "redirect:/CareRecord/RoutineClicked?id=" + id + "&date=" + date;
//	}
//
//	@GetMapping("/RoutineClicked")
//	public String routineClicked(
//			@Param("id")int id,
//			@Param("date")String date,
//			@ModelAttribute("routine")Routine routine,
//			Model model) {
//		careRecordService.__consoleOut__("@GetMapping(\"/RoutineClicked\")開始");
//		if(date == null) date = careRecordService.today();
//		add_View_Data_(model, "routineClicked", "定期入力");
//		model.addAttribute("id", id);
//		model.addAttribute("date", date);
//		model.addAttribute("routineList", careRecordService.routineList(date));
//		model.addAttribute("options", careRecordService.options());
//		careRecordService.__consoleOut__("@GetMapping(\"/RoutineClicked\")終了");
//		return "view";
//	}

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
		add_View_Data_(model, "routine", "定時入力データ編集");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("routine", careRecordService.routine(id));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("todo_actions", careRecordService.todo_actions());
		model.addAttribute("names", careRecordService.names());
		careRecordService.__consoleOut__("@GetMapping(\"/Routine\")終了");
		return "view";
	}

	@PostMapping("/RoutineInsert")
	public String routineInsert(
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineInsert\")開始");
		add_View_Data_(model, "routineInsert", "定期入力新規データ作成");
		model.addAttribute("date", date);
		model.addAttribute("routine", careRecordService.new_Routine(date));
		model.addAttribute("todo_actions", careRecordService.todo_actions());
		model.addAttribute("names", careRecordService.names());
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@PostMapping(\"/RoutineInsert\")終了");
		return "view";
	}

	@PostMapping("/RoutineAppend")
	public String routineAppend(
			@RequestParam("date")String date,
			Model model) {
			careRecordService.__consoleOut__("@PostMapping(\"/RoutineAppend\")開始");
			add_View_Data_(model, "routineAppend", "定期入力データ追加");
			model.addAttribute("date", date);
			model.addAttribute("userList", careRecordService.user_All());
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
			add_View_Data_(model, "routineDelete", "定期入力データ削除");
			model.addAttribute("id", id);
			model.addAttribute("date", date);
			model.addAttribute("routine", careRecordService.routine(id));
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
		add_View_Data_(model, "routineDate", "日付変更");
		model.addAttribute("date", date);
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/RoutineDate\")終了");
		return "view";
	}

	@GetMapping("/Report")
	public String report(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Setting\")開始");
		add_View_Data_(model, "report", "帳票印刷メニュー");
		careRecordService.__consoleOut__("@GetMapping(\"/Setting\")開始");
		return "view";
	}

		@GetMapping("/Setting")
		public String setting(
				Model model) {
			careRecordService.__consoleOut__("@GetMapping(\"/Setting\")開始");
			add_View_Data_(model, "setting", "設定メニュー");
			careRecordService.__consoleOut__("@GetMapping(\"/Setting\")開始");
			return "view";
	}

	@GetMapping("/ToDoSetting")
	public String todoSetting(
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/ToDoSetting\")開始");
		add_View_Data_(model, "todoSetting", "ToDo設定");
		model.addAttribute("todo", new ToDo());
		model.addAttribute("todo_list", careRecordService.todoList());
		model.addAttribute("options", careRecordService.options());
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
		add_View_Data_(model, "todoUpdate", "ToDo情報更新");
		model.addAttribute("id", id);
		model.addAttribute("todo", careRecordService.todo(id));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@PostMapping(\"/ToDoUpdate\")終了");
		return "view";
	}

	@PostMapping("/IdeaUpdate")
	public String ideaUpdate(
			@RequestParam("id")int id,
			Model model) {
		careRecordService.__consoleOut__("@PostMapping(\"/IdeaUpdate\")開始");
		add_View_Data_(model, "ideaUpdate", "行事計画更新");
		model.addAttribute("id", id);
		model.addAttribute("idea", careRecordService.idea(id));
		careRecordService.__consoleOut__("@PostMapping(\"/IdeaUpdate\")終了");
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
		add_View_Data_(model, "birthday", "誕生日登録");
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("count", count + 1);
		model.addAttribute("label", careRecordService.dateInputLabel(count, "生まれた"));
		model.addAttribute("string", careRecordService.dateStringConnect(count, string, input));
		model.addAttribute("options", careRecordService.dateOptions(count, input));
		String url = careRecordService.dateInputUrl(count, 3, "/CareRecord/Birthday", "/CareRecord/Birthday/Update");
		model.addAttribute("url", url);
		careRecordService.__consoleOut__(url);
		careRecordService.__consoleOut__("@GetMapping(\"/Birthday\")" + count + "終了");
		return "view";
	}

	@PostMapping("/Birthday/Update")
	public String birthday_Update(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/Birthday/Update\")開始");
		careRecordService.__consoleOut__("count = " + count);
		careRecordService.birthday_Update(id, string, input);
		careRecordService.__consoleOut__("@PostMapping(\"/Birthday/Update\")終了");
		return "redirect:/CareRecord/UserUpdate?id=" + id;
	}

	@PostMapping("/Move_in")
	public String move_in(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/Move_in\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/Move_in\")終了");
		return "redirect:/CareRecord/Move_in?id=" + id + "&count=" + count + "&string=" + string + "&input=" + input;
	}

	@GetMapping("/Move_in")
	public String move_in(
			@Param("id")int id,
			@Param("count")int count,
			@Param("string")String string,
			@Param("input")String input,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/Move_in\")開始");
		careRecordService.__consoleOut__("string = " + careRecordService.dateStringConnect(count, string, input));
		add_View_Data_(model, "input", "入居" + careRecordService.date_Input_Stage(count));
		model.addAttribute("id", id);
		model.addAttribute("count", count + 1);
		model.addAttribute("label", careRecordService.dateInputLabel(count, "入居した"));
		model.addAttribute("string", careRecordService.dateStringConnect(count, string, input));
		model.addAttribute("options", careRecordService.dateOptions(count, input));
		String url = careRecordService.dateInputUrl(count, 3, "/CareRecord/Move_in", "/CareRecord/Move_in/Update");
		model.addAttribute("url", url);
		careRecordService.__consoleOut__(url);
		careRecordService.__consoleOut__("@GetMapping(\"/Move_in\")" + count + "終了");
		return "view";
	}

	@PostMapping("/Move_in/Update")
	public String move_in_Update(
			@RequestParam("id")int id,
			@RequestParam("count")int count,
			@RequestParam("string")String string,
			@RequestParam("input")String input) {
		careRecordService.__consoleOut__("@PostMapping(\"/Move_in/Update\")開始");
		careRecordService.__consoleOut__("count = " + count);
		careRecordService.move_in_Update(id, string, input);
		careRecordService.__consoleOut__("@PostMapping(\"/Move_in/Update\")終了");
		return "redirect:/CareRecord/UserUpdate?id=" + id;
	}

	@PostMapping("/ActionDate")
	public String actionDate(
			@RequestParam("id")int id,
			@RequestParam("date")String date,
			Model model) {
		careRecordService.__consoleOut__("@GetMapping(\"/ActionDate\")開始");
		add_View_Data_(model, "actionDate", "日付変更");
		model.addAttribute("id", id);
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("options", careRecordService.options());
		careRecordService.__consoleOut__("@GetMapping(\"/ActionDate\")終了");
		return "view";
	}

	/** view 表示に必要な属性データをモデルに登録 */
	private void add_View_Data_(Model model, String template, String title) {
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		model.addAttribute("title", title);
	}

}
