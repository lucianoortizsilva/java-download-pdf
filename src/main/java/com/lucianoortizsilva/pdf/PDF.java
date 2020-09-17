package com.lucianoortizsilva.pdf;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PDF {

	public static final String[] HEADERS = { "Ano", "Campeão", "Vice", "Estádio", "Local", "Público" };

	public static Document createDocument() throws DocumentException, FileNotFoundException {
		final Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
		document.addAuthor("lucianoortizsilva@gmail.com");
		document.newPage();
		return document;
	}

	public static void createHeader(final PdfPTable table) {
		table.setWidthPercentage(100);
		for (final String header : HEADERS) {
			final PdfPCell cell = new PdfPCell(new Phrase(header));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
		}
	}

	public static void createData(final PdfPTable table, final List<Line> lines) {
		final Iterator<Line> it = lines.iterator();
		while (it.hasNext()) {
			final Line line = it.next();
			for (final Column column : line.getColumns()) {
				column.getFormat().formatCell(table, column);
			}
		}
	}

}