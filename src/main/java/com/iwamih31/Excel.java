package com.iwamih31;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletOutputStream;

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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {


	/** ファイル取得 */
	public File file(String file_Name) {
		File file = null;
		try {
			// Fileオブジェクトの生成
			file = new File(file_Name);
			// ファイルの存在を確認
			Boolean fileExists = file.exists();
			if (fileExists == false) {
				System.out.println("ファイル " + file_Name + "が無いので作成します");
				file = create_File(file_Name);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Fileオブジェクトの生成
			file = new File(file_Name);
		}
		return file;
	}

	/** Excelファイル作成 */
	public File create_File(String file_Name) {
		File file = null;
		try {
			System.out.println("ファイル " + file_Name + "が無いので作成します");
			Workbook workbook = workbook();
//			// 出力用のストリームを用意
//			FileOutputStream out = new FileOutputStream(file_Name);
//			// ファイルへ出力
//			workbook.write(out);
			write(workbook, file_Name);
			file = new File(file_Name);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return file;
	}

	/** Excelファイルの書き込み */
	public boolean write(Workbook workbook, String file_Name) {
		boolean is_Safe = false;
		try (FileOutputStream fileOutputStream = new FileOutputStream(new File(file_Name))) {
			workbook.write(fileOutputStream);
			is_Safe = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is_Safe;
	}

	public boolean write(Workbook workbook, ServletOutputStream outputStream) {
		boolean is_Safe = false;
		try (outputStream) {
			workbook.write(outputStream);
			is_Safe = true;
			workbook.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is_Safe;

	}

	/** Workbook 作成 */
	public Workbook workbook() {
		Workbook workbook = new XSSFWorkbook();
		return workbook;
	}

	/** Workbook 取得 */
	public Workbook workbook(String file_Name) {
		Workbook workbook = null;
		create_File(file_Name);
		try (FileInputStream file = new FileInputStream(new File(file_Name))) {
	    workbook = WorkbookFactory.create(file);
		} catch (Exception e) {
		  System.out.println(e.getMessage());
		}
		return workbook;
	}

	/** ワークシート 取得（Workbook から sheet_Name で取得） */
	public Sheet sheet(Workbook workbook, String sheet_Name) {
		Sheet sheet = workbook.createSheet(sheet_Name);
		return sheet;
	}

	/** ワークシート 取得（file_Name という名の Workbook から sheet_Name で取得） */
	public Sheet sheet(String file_Name, String sheet_Name) {
		Workbook workbook = workbook(file_Name);
		Sheet sheet = workbook.createSheet(sheet_Name);
		return sheet;
	}

	/** ワークシート 取得（Workbook から sheet_Number で取得） */
	public Sheet getSheetAt(Workbook workbook, int sheet_Number) {
		Sheet sheet = workbook.getSheetAt(sheet_Number);
		return sheet;
	}

	/** ワークシート 取得（file_Name という名の Workbook から sheet_Number で取得） */
	public Sheet getSheetAt(String file_Name, int sheet_Number) {
		Workbook workbook = workbook(file_Name);
		Sheet sheet = workbook.getSheetAt(sheet_Number);
		return sheet;
	}

	/** 行 取得 */
	public Row row(Sheet sheet, int row_Number) {
		Row row = sheet.createRow(row_Number);
		return row;
	}

	/** セル入力（行以下指定）*/
	public boolean setCellValue(Row row, int column_Number, String set_Value) {
		boolean is_Safe = false;
		try {
			Cell cell = row.createCell(column_Number);
			cell.setCellValue(set_Value);
			is_Safe = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is_Safe;
	}

	/** セル入力（シート以下指定）*/
	public boolean setCellValue(Sheet sheet, int row_Number, int column_Number, String set_Value) {
		boolean is_Safe = false;
		try {
			Row row = sheet.createRow(row_Number);
			Cell cell = row.createCell(column_Number);
			cell.setCellValue(set_Value);
			is_Safe = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is_Safe;
	}

	/** 列幅の設定 */
	public int setColumnWidth(Sheet sheet, int column_Number, int x_Word_Count, int tuning_Width) {
		int columnWidth = -1;
		try {
			// 1文字分の横幅 × 文字数 ＋ 微調整分の幅
			columnWidth = 256 * x_Word_Count + tuning_Width;
			sheet.setColumnWidth(column_Number - 1, columnWidth);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			columnWidth = -1;
		}
		return columnWidth;
	}

	/** 行の高さ設定 */
	public float setColumnWidth(Sheet sheet, int row_Number, float height) {
		try {
			Row row = sheet.createRow(row_Number);
			row.setHeightInPoints(height);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			height = -1;
		}
		return height;
	}

	/** セルのデータ書式設定 */
	public String setCellStyle(Cell cell, String format_Pattern) {
		try {
			if (format_Pattern == null) format_Pattern = "#,##0.00";
			Workbook workbook = workbook();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(workbook.createDataFormat().getFormat(format_Pattern));
			cell.setCellStyle(cellStyle);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			format_Pattern = null;
		}
		return format_Pattern;
	}

	/** セルのフォントスタイル（太字）設定 */
	public boolean setBold(Cell cell, boolean use) {
		boolean is_Safe = false;
		try {
			Workbook workbook = workbook();
			CellStyle boldCellStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(use);
			boldCellStyle.setFont(font);
			cell.setCellStyle(boldCellStyle);
			is_Safe = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return is_Safe;
	}

	//文字寄せ
	public String setAlignment(Cell cell,String horizontalAlignment) {
		HorizontalAlignment align = null;
		try {
			Workbook workbook = workbook();
			CellStyle centerCellStyle = workbook.createCellStyle();
			switch (horizontalAlignment) {
				case "CENTER":
					align = HorizontalAlignment.CENTER;
					break;
				case "LEFT":
					align = HorizontalAlignment.LEFT;
					break;
				case "RIGHT":
					align = HorizontalAlignment.RIGHT;
					break;
				case "FILL":
					align = HorizontalAlignment.FILL;
					break;
				case "JUSTIFY":
					align = HorizontalAlignment.JUSTIFY;
					break;
				default:
					align = HorizontalAlignment.LEFT;
			}
			centerCellStyle.setAlignment(align);
			cell.setCellStyle(centerCellStyle);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			horizontalAlignment = null;
		}
		return horizontalAlignment;
	}

	// 文字寄せ
	public String setAlignment(Cell cell,HorizontalAlignment horizontalAlignment) {
		String align = null;
		try {
			Workbook workbook = workbook();
			CellStyle centerCellStyle = workbook.createCellStyle();
			centerCellStyle.setAlignment(horizontalAlignment);
			cell.setCellStyle(centerCellStyle);
			align = horizontalAlignment.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return align;
	}

	//色指定
	public String setFillForegroundColor(Cell cell, IndexedColors indexedColors) {
		String color_Name = null;
		try {
			Workbook workbook = workbook();
			CellStyle coloredCellStyle = workbook.createCellStyle();
			coloredCellStyle.setFillForegroundColor(indexedColors.getIndex());
			coloredCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(coloredCellStyle);
			color_Name = indexedColors.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return color_Name;
	}

	// 罫線指定
	public String setBorder(
			Cell cell,
			BorderStyle borderStyle_Top,
			BorderStyle borderStyle_Bottom,
			BorderStyle borderStyle_Left,
			BorderStyle borderStyle_Right) {
		String set_Border = null;
		try {
			Workbook workbook = workbook();
			CellStyle borderedCellStyle = workbook.createCellStyle();
			borderedCellStyle.setBorderTop(borderStyle_Top);
			borderedCellStyle.setBorderBottom(borderStyle_Bottom);
			borderedCellStyle.setBorderLeft(borderStyle_Left);
			borderedCellStyle.setBorderRight(borderStyle_Right);
			cell.setCellStyle(borderedCellStyle);
			set_Border =
					"BorderTop = " + borderStyle_Top.toString() +
					"BorderBottom = " + borderStyle_Bottom.toString() +
					"BorderLeft = " + borderStyle_Left.toString() +
					"BorderRight = " + borderStyle_Right.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return set_Border;
	}

}