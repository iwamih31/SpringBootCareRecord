package com.iwamih31;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {

	@Autowired
	private CareRecordService careRecordService;

	@GetMapping("/")
	public String index() {
		careRecordService.__consoleOut__("@PostMapping(\"/\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/\")終了");
		return "redirect:/CareRecord/Main";
	}

	@GetMapping("/main")
	public String main0() {
		careRecordService.__consoleOut__("@PostMapping(\"/\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/\")終了");
		return "redirect:/CareRecord/Main";
	}

	@GetMapping("/Main")
	public String main() {
		careRecordService.__consoleOut__("@PostMapping(\"/\")開始");
		careRecordService.__consoleOut__("@PostMapping(\"/\")終了");
		return "redirect:/CareRecord/Main";
	}

	/** view 表示に必要な属性データをモデルに登録 */
	private void add_View_Data_(Model model, String template, String title) {
		model.addAttribute("library", template + "::library");
		model.addAttribute("main", template + "::main");
		model.addAttribute("title", title);
	}

}
