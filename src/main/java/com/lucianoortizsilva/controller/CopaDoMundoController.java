package com.lucianoortizsilva.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lucianoortizsilva.service.CopaDoMundoService;
import com.lucianoortizsilva.util.ResponseUtil;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping(value = "copas")
public class CopaDoMundoController {

	@Autowired
	private CopaDoMundoService copaDoMundoService;  
	
	@RequestMapping(value = "/download/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> download() {
		String filename = null;
		Resource resource = null;
		try {
			final File file = this.copaDoMundoService.createReportInFile();
			if (Objects.isNull(file)) {
				return ResponseEntity.notFound().build();
			}
			final Path path = Paths.get(file.getPath());
			resource = new UrlResource(path.toUri());
			filename = resource.getFilename();
		} catch (final Exception e) {
			return ResponseUtil.cathException(e);
		}
		return ResponseUtil.ok(resource, filename);
	}
	
	
}