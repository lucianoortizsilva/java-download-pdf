package com.lucianoortizsilva.api.dto;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public enum Format {

	TEXT {
		@Override
		public void formatCell(final PdfPTable table, final Column column) {
			final PdfPCell cell = new PdfPCell(new Phrase(column.getValue()));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
	};

	public abstract void formatCell(final PdfPTable table, final Column column);
}