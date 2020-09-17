package com.lucianoortizsilva.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PDF {

	public static Document createDocument() throws DocumentException, FileNotFoundException {
//		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		final Document document = new Document();
//		document.addTitle("Copas do Mundo");
//		document.setPageSize(PageSize.A4);
//		document.newPage();
//		PdfWriter.getInstance(document, baos);
//		document.open();
//		
		 Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		  try {
		   // creation of the different writers
		   //HtmlWrite.getInstance(document , System.out);
		   PdfWriter.getInstance(document , new FileOutputStream("text.pdf"));
		   // we add some meta information to the document
		   document.addAuthor("Ortiz"); 
		   document.addSubject("This is the result of a Test."); 
		   // we open the document for writing
		   document.open(); 
		   document.add(new Paragraph("Hello world"));
		  } catch(DocumentException de) {
		   System.err.println(de.getMessage());
		  }
		  document.close();
		  
		return document;
	}

}