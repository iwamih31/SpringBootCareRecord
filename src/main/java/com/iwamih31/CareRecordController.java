package com.iwamih31;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
		System.out.println("@GetMapping(\"/UserList\")開始");
		model.addAttribute("title", "利用者一覧表");
		model.addAttribute("date", careRecordService.today());
		model.addAttribute("userList", careRecordService.userList());
		model.addAttribute("library", "userList::library");
		model.addAttribute("main", "userList::main");
		return "view";
	}

	@PostMapping("/User")
	public String user(
			@RequestParam("id")int id,
			@RequestParam("date")String date) {
		System.out.println("@PostMapping(\"/User\")開始");
		String message = "";
		return "redirect:/CareRecord/User?id=" + id + "&date=" + date + "&message=" + message;
	}

	@GetMapping("/User")
	public String user(
			@Param("id")int id,
			@Param("date")String date,
			@Param("message")String message,
			@ModelAttribute("insert_record")Private insert_record,
			Model model) {
		System.out.println("@GetMapping(\"/User\")開始");
		model.addAttribute("title", "常時入力");
		model.addAttribute("date", date);
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("records", careRecordService.records(id, date));
		model.addAttribute("options", careRecordService.options());
		model.addAttribute("message", message);
		model.addAttribute("library", "user::library");
		model.addAttribute("main", "user::main");
		System.out.println("@GetMapping(\"/User\")終了");
		return "view";
	}

	@PostMapping("/Detail")
	public String detail(@RequestParam("id")int id, Model model) {
		System.out.println("detail開始");
		model.addAttribute("title", "詳細情報");
		model.addAttribute("user", careRecordService.user(id));
		model.addAttribute("detail", careRecordService.detail(id));
		model.addAttribute("library", "detail::library");
		model.addAttribute("main", "detail::main");
		return "view";
	}

	@PostMapping("/Record")
	public String record(
			@RequestParam("post_id")int id,
			@RequestParam("post_date")String date,
			@ModelAttribute("insert_record")Private insert_record) {
		System.out.println("record開始");
		String message = careRecordService.recordInsert(insert_record);
		return "redirect:/CareRecord/User?id=" + id + "&date=" + date + "&message=" + message;
	}

	@PostMapping("/Routine")
	public String routine(RedirectAttributes redirectAttributes, @RequestParam("id") int id) {
		CareRecord user = careRecordService.user(id);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("title", user.getName());
		redirectAttributes.addFlashAttribute("model", modelMap);
		return "redirect:/CareRecord/User";
	}

	@GetMapping("/Routine")
	public String routine(Model model) {
		model.addAttribute("title", "定期入力");
		model.addAttribute("routineList", careRecordService.routineList());
		model.addAttribute("library", "routineList::library");
		model.addAttribute("main", "routineList::main");
		return "view";
	}
}
