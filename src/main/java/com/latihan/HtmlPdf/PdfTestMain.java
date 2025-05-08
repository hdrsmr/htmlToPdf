package com.latihan.HtmlPdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;

public class PdfTestMain {

    public static void main(String[] args) throws IOException{
//       genareteHtmlBasicToPdf();
//        jsoupExtract();
        generateHtmlFlyingSaucer();
    }



    public static void generateHtmlFlyingSaucer() throws IOException {
        File htmlFile = new File("coba.html");
        Document doc = Jsoup.parse(htmlFile,"UTF-8");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try (OutputStream os = new FileOutputStream("coba_output.pdf")){
            ITextRenderer renderer = new ITextRenderer();
            SharedContext cntxt = renderer.getSharedContext();
            cntxt.setPrint(true);
            cntxt.setInteractive(false);
            String baseUrl = FileSystems.getDefault().getPath("HtmlPdf")
                    .toUri().toURL().toString();
            renderer.setDocumentFromString(doc.html(), baseUrl);
            renderer.layout();
            renderer.createPDF(os);
            System.out.println("done");


        }
    }



    public static void jsoupExtract(){
        try {
//            Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
            Document doc = Jsoup.connect(" https://www.google.com/").get();

//            Elements newsHeadlines = doc.select("#mp-itn b a");
            Elements newsHeadlines = doc.select("body");
//            for (Element headline : newsHeadlines) {
////                System.out.println(headline.attr("title") + "\n\t" + headline.absUrl("href"));
//                System.out.println(headline.attr("title") + "\n\t" + headline.absUrl("href"));
//            }

            for (Element paragraph : newsHeadlines) {
                String text = paragraph.text();
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createBasicPDFwithPdfBox(){
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Hello, PDFBox!");
            contentStream.endText();

            contentStream.close();

            document.save("HelloPDFBox.pdf");
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void genareteHtmlBasicToPdf(){
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load your HTML document
            String html = "<html><body><h1>Hello, PDFBox with HTML content!</h1></body></html>";
            Document doc = Jsoup.parse(html);

            // Set initial Y position and line height
            float yPosition = 750;
            float lineHeight = 15;

            // Set font and font size
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Parse and draw the text from HTML
            Elements paragraphs = doc.select("h1");
            for (Element paragraph : paragraphs) {
                String text = paragraph.text();
                contentStream.newLineAtOffset(100, yPosition);
                contentStream.showText(text);
                yPosition -= lineHeight;
            }
            contentStream.endText();
            contentStream.close();

            document.save("HtmlToPdf_PDFBOX.pdf");
            System.out.println("PDF created successfully from HTML.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
