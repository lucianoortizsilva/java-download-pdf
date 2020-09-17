package com.lucianoortizsilva.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.lucianoortizsilva.model.CopaDoMundo;
import com.lucianoortizsilva.pdf.Column;
import com.lucianoortizsilva.pdf.Format;
import com.lucianoortizsilva.pdf.Line;
import com.lucianoortizsilva.repository.CopaDoMundoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CopaDoMundoService {

	private CopaDoMundoRepository copaDoMundoRepository;

	private static final String[] HEADERS = { "Ano", "Campeão", "Vice", "Estádio", "Local", "Público" };

	public File createReportInFile() throws IOException, DocumentException {
		File file = null;
		final List<Line> lines = this.generateLines();
		// if (CollectionUtils.isNotEmpty(lines)) {
		file = this.generateFile(lines);
		// }
		return file;
	}

	private File generateFile(final List<Line> lines) throws IOException, DocumentException {
		File file = null;
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			final String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			file = File.createTempFile(filename, ".pdf");
			final OutputStream os = new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, os);
			document.open();
			document.add(new Paragraph("A Hello World"));
			document.close();
			writer.close();
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		return file;
	}

	private List<Line> generateLines() {
		final List<CopaDoMundo> copas = this.copaDoMundoRepository.findAll();
		final List<Line> lines = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(copas)) {
			for (final CopaDoMundo copaDoMundo : copas) {
				final Column column0 = new Column(0, 7000, copaDoMundo.getAno().toString(), Format.NUMERIC);
				final Column column1 = new Column(1, 4000, copaDoMundo.getCampeao(), Format.TEXT);
				final Column column2 = new Column(2, 4000, copaDoMundo.getVice(), Format.TEXT);
				final Column column3 = new Column(4, 4000, copaDoMundo.getEstadio(), Format.TEXT);
				final Column column4 = new Column(5, 4000, copaDoMundo.getLocal(), Format.TEXT);
				final Column column5 = new Column(6, 4000, copaDoMundo.getPublico().toString(), Format.NUMERIC);
				final Line line = new Line(column0, column1, column2, column3, column4, column5);
				lines.add(line);
			}
		}
		return lines;
	}

}