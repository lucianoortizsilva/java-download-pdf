package com.lucianoortizsilva.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {

	public static File generateFile(final Document document) throws IOException, DocumentException {
		final String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		final File file = File.createTempFile(filename, ".pdf");
		final OutputStream os = new FileOutputStream(file);
		final PdfDocument pdfDocument = new PdfDocument();
		document.addDocListener(pdfDocument);
		PdfWriter.getInstance(pdfDocument, os);
		return file;
	}
	
}