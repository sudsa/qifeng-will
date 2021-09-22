package com.hanxiaozhang.pdfutil;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;


/**
 * 功能描述: <br>
 * 〈协议基类〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/17
 */
@Slf4j
public class ProtocolHelper {
	/**
	 * 
	 * @param str
	 * @param pdf
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	protected static void templateHtml(String str, PdfHelper pdf) throws IOException, DocumentException {

		final List<Element> pdfeleList = new ArrayList<Element>();

		ElementHandler elemH = new ElementHandler() {
			@Override
			public void add(final Writable w) {
				if (w instanceof WritableElement) {
					pdfeleList.addAll(((WritableElement) w).elements());
				}
			}
		};

		log.info("Charset.defaultCharset(): {}",Charset.defaultCharset());
		InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(str.getBytes("UTF-8")), "UTF-8");

		XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);

		List<Element> list = new ArrayList<Element>();

		pdfeleList.forEach(x->{
			if (x instanceof LineSeparator || x instanceof WritableDirectElement) {
				return;
			}
			list.add(x);
		});

		pdf.addHtmlList(list);

	}

}
