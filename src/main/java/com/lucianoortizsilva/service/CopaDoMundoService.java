package com.lucianoortizsilva.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.lucianoortizsilva.api.PdfApi;
import com.lucianoortizsilva.api.dto.Column;
import com.lucianoortizsilva.api.dto.Format;
import com.lucianoortizsilva.api.dto.Line;
import com.lucianoortizsilva.model.CopaDoMundo;
import com.lucianoortizsilva.repository.CopaDoMundoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CopaDoMundoService {

	private CopaDoMundoRepository copaDoMundoRepository;

	public static final String[] HEADERS = { "Ano", "Campeão", "Vice", "Estádio", "Local", "Público" };
	private static final String AUTHOR = "lucianoortizsilva@gmail.com";

	public File createReportInFile() throws IOException, DocumentException {
		File file = null;
		final List<Line> lines = this.generateLines();
		if (CollectionUtils.isNotEmpty(lines)) {
			file = PdfApi.create(lines, HEADERS, AUTHOR);
		}
		return file;
	}

	public String createReportStringInBase64() throws IOException, DocumentException {
		String base64 = null;
		final List<Line> lines = this.generateLines();
		if (CollectionUtils.isNotEmpty(lines)) {
			base64 = PdfApi.createBase64(lines, HEADERS, AUTHOR);
		}
		return base64;
	}

	private List<Line> generateLines() {
		final List<CopaDoMundo> copas = this.copaDoMundoRepository.findAll();
		final List<Line> lines = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(copas)) {
			for (final CopaDoMundo copaDoMundo : copas) {
				final Column column0 = new Column(0, copaDoMundo.getAno().toString(), Format.TEXT);
				final Column column1 = new Column(1, copaDoMundo.getCampeao(), Format.TEXT);
				final Column column2 = new Column(2, copaDoMundo.getVice(), Format.TEXT);
				final Column column3 = new Column(3, copaDoMundo.getEstadio(), Format.TEXT);
				final Column column4 = new Column(4, copaDoMundo.getLocal(), Format.TEXT);
				final Column column5 = new Column(5, copaDoMundo.getPublico().toString(), Format.TEXT);
				final Line line = new Line(column0, column1, column2, column3, column4, column5);
				lines.add(line);
			}
		}
		return lines;
	}

}