package com.lucianoortizsilva.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lucianoortizsilva.model.CopaDoMundo;
import com.lucianoortizsilva.pdf.Column;
import com.lucianoortizsilva.pdf.Format;
import com.lucianoortizsilva.pdf.Line;
import com.lucianoortizsilva.pdf.PDF;
import com.lucianoortizsilva.repository.CopaDoMundoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CopaDoMundoService {

	private CopaDoMundoRepository copaDoMundoRepository;

	public File createReportInFile() throws IOException, DocumentException {
		File file = null;
		final List<Line> lines = this.generateLines();
		if (CollectionUtils.isNotEmpty(lines)) {
			file = this.generateFile(lines);
		}
		return file;
	}

	
	
	private File generateFile(final List<Line> lines) throws DocumentException, IOException {
		final Document document = PDF.createDocument();

		final String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		final File file = File.createTempFile(filename, ".pdf");
		final FileOutputStream fos = new FileOutputStream(file);

		final PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
		
		document.open();
		final PdfPTable table = new PdfPTable(PDF.HEADERS.length);
		PDF.createHeader(table);
		PDF.createData(table, lines);
		document.add(table);
		
		document.close();
		fos.close();
		pdfWriter.close();

		return file;
	}

	
	
	private List<Line> generateLines() {
		final List<CopaDoMundo> copas = this.copaDoMundoRepository.findAll();
		final List<Line> lines = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(copas)) {
			for (final CopaDoMundo copaDoMundo : copas) {
				final Column column0 = new Column(0, copaDoMundo.getAno().toString(), Format.TEXT);
				final Column column1 = new Column(1, copaDoMundo.getCampeao(), Format.TEXT);
				final Column column2 = new Column(2, copaDoMundo.getVice(), Format.TEXT);
				final Column column3 = new Column(4, copaDoMundo.getEstadio(), Format.TEXT);
				final Column column4 = new Column(5, copaDoMundo.getLocal(), Format.TEXT);
				final Column column5 = new Column(6, copaDoMundo.getPublico().toString(), Format.TEXT);
				final Line line = new Line(column0, column1, column2, column3, column4, column5);
				lines.add(line);
			}
		}
		return lines;
	}

}