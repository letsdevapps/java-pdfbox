package com.pro.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdfbox")
public class PDFBoxApi {

	@GetMapping({ "", "/" })
	public String index() {
		return "API PDFBox esta acessível!";
	}

	@GetMapping("/show")
	public ResponseEntity<byte[]> showOnBrowser() throws IOException {
		// Criar um novo documento PDF
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		// Adicionar conteúdo no PDF
		try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
			contentStream.beginText();
			PDType1Font helveticaBoldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
			contentStream.setFont(helveticaBoldFont, 12);
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("Este é o conteúdo do PDF gerado!");
			contentStream.endText();
		}

		// Criar um ByteArrayOutputStream para enviar os bytes do PDF
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		document.save(byteArrayOutputStream);
		document.close();

		// Converter para bytes e retornar como resposta
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();

		// Quando Conten-Disposition contem inline ele mostra no browser
		return ResponseEntity.ok().header("Content-Type", "application/pdf")
				.header("Content-Disposition", "inline; filename=documento.pdf").body(pdfBytes);
	}

	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadPdf() throws IOException {
		// Criar um novo documento PDF
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		// Adicionar conteúdo no PDF
		try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
			contentStream.beginText();
			PDType1Font helveticaBoldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
			contentStream.setFont(helveticaBoldFont, 12);
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("Este é o conteúdo do PDF gerado!");
			contentStream.endText();
		}

		// Criar um ByteArrayOutputStream para enviar os bytes do PDF
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		document.save(byteArrayOutputStream);
		document.close();

		// Converter para bytes e retornar como resposta
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();

		// Quando Conten-Disposition contem attachment ele faz o download automatico
		return ResponseEntity.ok().header("Content-Type", "application/pdf")
				.header("Content-Disposition", "attachment; filename=documento.pdf").body(pdfBytes);
	}
}