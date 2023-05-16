package com.iwamih31;

public class LabelSet {

	public static String[] detail_List = 		{"ID", "部屋番号", "名前", "誕生日", "介護度", "入居日", "利用状況"};
	public static int[] detail_List_width = 	{3   , 5         , 10    , 8       , 5       , 8       , 8         };

	public static Set[]
			detail_Set = {
					new Set("ID",				3),
					new Set("部屋番号",	5),
					new Set("氏名",		 10),
					new Set("誕生日",		8),
					new Set("介護度",		5),
					new Set("入居日",		8),
					new Set("利用状況",	8)};

	public static Set[]
			action_Set = {
					new Set("ID",				3),
					new Set("部屋番号",	4),
					new Set("氏名",			8),
					new Set("日付",			6),
					new Set("時間",			5),
					new Set("睡眠",			5),
					new Set("水分",			5),
					new Set("排尿1",		5),
					new Set("排尿2",		5),
					new Set("排便",			5),
					new Set("下剤",			5),
					new Set("服薬",			5),
					new Set("処置",			5),
					new Set("様子",		 20)};

	public static Set[]
			routineReport_Set = {
					new Set("日付",			6),
					new Set("部屋番号",	4),
					new Set("氏名",			8),
					new Set("時間",			5),
					new Set("予定",			8),
					new Set("内容",		 20),
					new Set("ID",				3)};

	public static Set[]
			officeReport_Set = {
					new Set("ID",				6),
					new Set("項目",			4),
					new Set("内容",			8)};
}
