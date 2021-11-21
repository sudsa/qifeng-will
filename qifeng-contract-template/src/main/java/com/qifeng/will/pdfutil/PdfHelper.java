package com.hanxiaozhang.pdfutil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/17
 */
public class PdfHelper {

	private Document document;

	private BaseFont bfChinese;

	/**
	 * 字体
	 */
	@SuppressWarnings("unused")
	private Font font;

	public PdfHelper(String path) {
		document = new Document();
		try {
			// 建立一个PdfWriter对象
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			// 设置中文字体
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			// 设置字体大小
			font = new Font(bfChinese, 10, Font.NORMAL);
		} catch (DocumentException de) {

		} catch (IOException ioe) {

		}
	}

	public static PdfHelper instance(String path) {
		return new PdfHelper(path);
	}

	public void exportPdf() {
		document.close();
	}

	public void addHtmlList(List<Element> list) throws DocumentException {
		for (Element e : list) {
			document.add(e);
		}
	}

}
