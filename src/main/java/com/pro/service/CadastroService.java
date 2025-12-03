package com.pro.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

	public PDDocument getCadastroSample() {
		// Criando um novo documento PDF
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		try {
			// Obtendo o fluxo de conteúdo da página
			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			// Adicionando uma imagem no topo esquerdo
			File imageFile = new File("src/main/resources/static/images/avatar/avatar-image.png");
			String caminho = imageFile.getAbsolutePath();
			if (imageFile.exists()) {
				PDImageXObject image = PDImageXObject.createFromFile(caminho, document);
				contentStream.drawImage(image, 50, 700, 100, 100); // posição e tamanho da imagem
			} else {
				System.out.println("Imagem não encontrada.");
			}

//			PDImageXObject image = PDImageXObject.createFromFile("images/avatar/avatar-image.png", document);
//			contentStream.drawImage(image, 50, 700, 100, 100); // posição e tamanho da imagem

			// Criação da fonte geral
			PDType1Font helveticaBoldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

			// Adicionando nome e data ao lado da imagem
			contentStream.beginText();
			contentStream.setFont(helveticaBoldFont, 12);
			contentStream.newLineAtOffset(160, 750); // Posição
			contentStream.showText("Nome: João da Silva");
			contentStream.newLineAtOffset(0, -20); // Deslocando para baixo
			contentStream.showText("Data: 03/12/2025");
			contentStream.endText();

			// Adicionando a lista de tarefas
			contentStream.beginText();
			contentStream.setFont(helveticaBoldFont, 10);
			contentStream.newLineAtOffset(50, 650); // Posição inicial para a lista
			contentStream.showText("Tarefas:");
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText("1. Tarefa 1");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("2. Tarefa 2");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("3. Tarefa 3");
			contentStream.endText();

			// Adicionando a descrição
			contentStream.beginText();
			contentStream.setFont(helveticaBoldFont, 12);
			contentStream.newLineAtOffset(50, 500); // Posição inicial para a descrição
			contentStream.showText("Descrição detalhada do projeto...");
			contentStream.endText();

			// Adicionando uma tabela simples
			float yStart = 300;
			float tableWidth = 500;
			float yPosition = yStart;
			float margin = 50;
			float cellMargin = 5f;
			float rowHeight = 20f;
			float tableBottomMargin = 100f;

			contentStream.setFont(helveticaBoldFont, 10);
			String[][] tableData = { { "ID", "Nome", "Data", "Status" }, { "1", "Tarefa 1", "03/12/2025", "Concluído" },
					{ "2", "Tarefa 2", "04/12/2025", "Pendente" }, { "3", "Tarefa 3", "05/12/2025", "Pendente" } };

			// Desenhando as linhas da tabela
			for (int i = 0; i < tableData.length; i++) {
				float xPosition = margin;
				for (int j = 0; j < tableData[i].length; j++) {
					contentStream.beginText();
					contentStream.newLineAtOffset(xPosition + cellMargin, yPosition + cellMargin);
					contentStream.showText(tableData[i][j]);
					contentStream.endText();
					xPosition += tableWidth / tableData[i].length;
				}
				yPosition -= rowHeight;
			}

			// Fechando o content stream e salvando o PDF
			contentStream.close();
			document.save("src/main/resources/static/images/avatar/saida/layout_complexo.pdf");
			// document.close();

			System.out.println("PDF criado com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document;
	}
}