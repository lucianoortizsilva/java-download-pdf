package com.lucianoortizsilva.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lucianoortizsilva.api.dto.Column;
import com.lucianoortizsilva.api.dto.Line;

import lombok.experimental.UtilityClass;

/**
 * 
 * API para gerar PDF
 * 
 * @author lucianoortizsilva@gmail.com
 * @since set/2020
 *
 */
@UtilityClass
public class PdfApi {

	/**
	 * 
	 * @param lines list de objetos com dados de cada linha
	 * @param headers nomes para exibir no cabecalho, em ordem de exibicao
	 * @param author identificacao do author
	 * 
	 * @return {@link File} do arquivo gerado
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public File create(final List<Line> lines, final String[] headers, final String author) throws IOException, DocumentException {
		final Document document = createDocument(author);
		final String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		final File file = File.createTempFile(filename, ".pdf");
		final FileOutputStream fos = new FileOutputStream(file);
		final PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
		document.open();
		final PdfPTable table = new PdfPTable(headers.length);
		createHeader(table, headers);
		createData(table, lines);
		document.add(table);
		document.close();
		fos.close();
		pdfWriter.close();
		return file;
	}

	
	
	/**
	 * 
	 * @param lines list de objetos com dados de cada linha
	 * @param headers nomes para exibir no cabecalho, em ordem de exibicao
	 * @param author identificacao do author
	 * 
	 * @return {@link String} base64 do arquivo gerado
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public String createBase64(final List<Line> lines, final String[] headers, final String author) throws IOException, DocumentException {
		final File file = create(lines, headers, author);
		String base64 = null;
		if (!Objects.isNull(file)) {
			final byte[] fileInBytes = FileUtils.readFileToByteArray(file);
			final byte[] bytesBase64 = Base64.encodeBase64(fileInBytes);
			base64 = StringUtils.newStringUtf8(bytesBase64);
		}
		return base64;
	}
	
	
	
	private static Document createDocument(final String author) throws DocumentException, FileNotFoundException {
		final Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
		document.addAuthor(author);
		document.newPage();
		return document;
	}

	
	
	private static void createHeader(final PdfPTable table, final String[] headers) {
		table.setWidthPercentage(100);
		for (final String header : headers) {
			final PdfPCell cell = new PdfPCell(new Phrase(header));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
		}
	}

	
	
	private static void createData(final PdfPTable table, final List<Line> lines) {
		final Iterator<Line> it = lines.iterator();
		while (it.hasNext()) {
			final Line line = it.next();
			for (final Column column : line.getColumns()) {
				column.getFormat().formatCell(table, column);
			}
		}
	}

}