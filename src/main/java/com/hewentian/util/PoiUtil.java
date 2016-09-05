package com.hewentian.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;

/**
 * 
 * <p>
 * <b>PoiUtil</b> 是 POI常用工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月5日 下午2:54:17
 * @since JDK 1.7
 *
 */
public class PoiUtil {
	/**
	 * 创建一个HSSFWorkbook, 目前仅支持简单数据类型
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年8月31日 下午12:03:32
	 * @param sheetName sheet的名字
	 * @param data 要保存到sheet中的数据，默认下标为0的是标题
	 * @return
	 */
	public static HSSFWorkbook createHSSFWorkbook(String sheetName, List<String[]> data) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;

		// 生成标题
		String[] values = data.get(0);
		for (int i = 0, len = values.length; i < len; i++) {
			cell = row.createCell(i);
			cell.setCellValue(values[i]);
		}

		// 生成数据行
		for (int i = 1, len = data.size(); i < len; i++) {
			row = sheet.createRow(i);
			values = data.get(i);
			for (int j = 0, lenJ = values.length; j < lenJ; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[j]);
			}
		}

		return wb;
	}
	
	/**
	 * 将简报导出到WORD
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年9月2日 下午2:40:20
	 * @param data
	 *            要保存到word中的数据，默认下标为0的是标题
	 * @return
	 * @throws Exception
	 */
	public static XWPFDocument exportWord(List<String[]> data) throws Exception {
		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph pTitle = doc.createParagraph();
		pTitle.setAlignment(ParagraphAlignment.CENTER);
		pTitle.setVerticalAlignment(TextAlignment.TOP);
		pTitle.setBorderBottom(Borders.DOUBLE);
		pTitle.setBorderTop(Borders.DOUBLE);

		pTitle.setBorderRight(Borders.DOUBLE);
		pTitle.setBorderLeft(Borders.DOUBLE);
		pTitle.setBorderBetween(Borders.SINGLE);

		XWPFRun rTitleName = pTitle.createRun();
		rTitleName.setFontSize(30);
		rTitleName.setColor("FF0000");
		rTitleName.setBold(true);
		rTitleName.setText("每日简报");
		rTitleName.setFontFamily("Courier");
		rTitleName.setTextPosition(100);
		rTitleName.addBreak();

		XWPFParagraph p2 = doc.createParagraph();
		p2.setAlignment(ParagraphAlignment.RIGHT);
		p2.setVerticalAlignment(TextAlignment.TOP);
		p2.setBorderBottom(Borders.DOUBLE);
		p2.setBorderTop(Borders.DOUBLE);

		p2.setBorderRight(Borders.DOUBLE);
		p2.setBorderLeft(Borders.DOUBLE);
		p2.setBorderBetween(Borders.SINGLE);

		XWPFRun rTime = p2.createRun();
		rTime.setBold(true);
		String now = "2016-10-01";
		rTime.setText(now);
		rTime.setFontFamily("Courier");
		// rTime.setUnderline(UnderlinePatterns.SINGLE);
		rTime.setTextPosition(-10);

		// CTText ctText = CTText.Factory.newInstance();
		// CTRPr rpr = getRunCTRPr(p2, rTime);
		// CTUnderline udLine = rpr.addNewU();
		// udLine.setVal(STUnderline.Enum.forInt(1));
		// udLine.setColor("FF0000");

		if (null == data || data.size() == 1) {
			return doc;
		}

		// "原文标题", "中文标题", "中文摘要", "来源", "链接", "时间" });
		String[] title = data.get(0);
		for (int i = 1, len = data.size(); i < len; i++) {
			XWPFParagraph pContent = doc.createParagraph();
			pContent.setAlignment(ParagraphAlignment.LEFT);

			// BORDERS
			pContent.setBorderBottom(Borders.DOUBLE);
			pContent.setBorderTop(Borders.DOUBLE);
			pContent.setBorderRight(Borders.DOUBLE);
			pContent.setBorderLeft(Borders.DOUBLE);
			pContent.setBorderBetween(Borders.SINGLE);

			String[] values = data.get(i);
			XWPFRun rTitle = pContent.createRun();
			rTitle.setBold(true);
			rTitle.setFontSize(15);
			rTitle.setText(i + "、" + values[1]);
			rTitle.addBreak();

			XWPFRun rContent = pContent.createRun();
			rContent.setFontSize(10);
			rContent.setText(title[0] + "：" + values[0]);
			rContent.addBreak();

			rContent.setText(title[5] + "：" + values[5]);
			rContent.addBreak();

			rContent.setText(title[3] + "：" + values[3]);
			rContent.addBreak();

			rContent.setText(title[4] + "：");
			addHyperlink(pContent, values[4]);

			rContent = pContent.createRun();
			rContent.setFontSize(10);
			rContent.addBreak();
			rContent.setText(title[2] + "：" + values[2]);
			rContent.addBreak();
		}

//		doc = simpleNumberFooter(doc);

//		FileOutputStream out = new FileOutputStream("d://simple_" + System.currentTimeMillis() + ".docx");
//		doc.write(out);
//		out.close();

		return doc;
	}

	/**
	 * 添加超链接
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年9月5日 下午2:26:22
	 * @param paragraph
	 * @param url
	 */
	public static void addHyperlink(XWPFParagraph paragraph, String url) {
		String id = paragraph.getDocument().getPackagePart().addExternalRelationship(url, XWPFRelation.HYPERLINK.getRelation()).getId();
		// Append the link and bind it to the relationship
		CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
		cLink.setId(id);

		// Create the linked text
		CTText ctText = CTText.Factory.newInstance();
		ctText.setStringValue(url);
		CTR ctr = CTR.Factory.newInstance();
		CTRPr rpr = ctr.addNewRPr();

		// 设置超链接样式
		CTColor color = CTColor.Factory.newInstance();
		color.setVal("0000FF");
		rpr.setColor(color);
		rpr.addNewU().setVal(STUnderline.SINGLE);

		ctr.setTArray(new CTText[] { ctText });
		cLink.setRArray(new CTR[] { ctr });
	}

	public static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
		CTRPr pRpr = null;
		if (pRun.getCTR() != null) {
			pRpr = pRun.getCTR().getRPr();
			if (pRpr == null) {
				pRpr = pRun.getCTR().addNewRPr();
			}
		} else {
			pRpr = p.getCTP().addNewR().addNewRPr();
		}
		return pRpr;
	}

	public static XWPFDocument simpleNumberFooter(XWPFDocument document) throws Exception {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph codePara = new XWPFParagraph(ctp, document);
		XWPFRun r1 = codePara.createRun();
		r1.setText("第");
		r1.setFontSize(11);
		CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		r1 = codePara.createRun();
		CTFldChar fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

		r1 = codePara.createRun();
		CTText ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("PAGE  \\* MERGEFORMAT");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

		r1 = codePara.createRun();
		r1.setText("页 总共");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		r1 = codePara.createRun();
		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

		r1 = codePara.createRun();
		ctText = r1.getCTR().addNewInstrText();
		ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
		ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		fldChar = r1.getCTR().addNewFldChar();
		fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

		r1 = codePara.createRun();
		r1.setText("页");
		r1.setFontSize(11);
		rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
		fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("宋体");
		fonts.setEastAsia("宋体");
		fonts.setHAnsi("宋体");

		codePara.setAlignment(ParagraphAlignment.CENTER);
		codePara.setVerticalAlignment(TextAlignment.CENTER);
		codePara.setBorderTop(Borders.THICK);
		XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
		newparagraphs[0] = codePara;
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
		headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newparagraphs);
		return document;
	}
}
