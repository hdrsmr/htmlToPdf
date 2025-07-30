package com.latihan.HtmlPdf.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.latihan.HtmlPdf.entity.Detail;
import com.latihan.HtmlPdf.entity.Header;
import com.latihan.HtmlPdf.service.TransaksiService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class TransaksiController {

    private Logger logger = LoggerFactory.getLogger(TransaksiController.class);

    private final TransaksiService transaksiService;



    private final TemplateEngine templateEngine;

    public TransaksiController(TransaksiService transaksiService, TemplateEngine templateEngine) {
        this.transaksiService = transaksiService;
        this.templateEngine = templateEngine;
    }

    public String getBase64Logo(String head) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(head.equals("footer")?"static/images/logo-lps.png":"static/images/logo.png");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }

//    public String getBase64LogoLPS() throws IOException {
//        ClassPathResource imgFile = new ClassPathResource("static/images/logo-lps.png");
//        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
//        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
//    }


    @GetMapping(value = "/openhtml/{limit}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateWithOpenHtml(@PathVariable  int limit) throws  Exception {
        // 1. Data untuk template

        logger.info("START DB===>");
        long startTimeDB = System.currentTimeMillis();
        Header header = transaksiService.getHeader("dadsdsd");
        List<Detail> details = transaksiService.getDetails(limit);
        long endTimeDB = System.currentTimeMillis();
        double durationDBInSeconds = (endTimeDB - startTimeDB) / 1000.0;
        logger.info("ENDDDD DB==> Waktu eksekusi DB: " + durationDBInSeconds + " detik");
        logger.info("==============");

        Map<String, Object> data = new HashMap<>();
        data.put("logoBase64", getBase64Logo("header"));
        data.put("header",header);
        data.put("details", details);
        Context context = new Context();
        context.setVariables(data);
        logger.info("START PARSING HTML FROM THYMLEAF===>");
        long startTimeHTML = System.currentTimeMillis();
        String renderedHtml = templateEngine.process("estatement", context);
        long endTimeHTML = System.currentTimeMillis();
        double durationEndHTML = (endTimeHTML - startTimeHTML) / 1000.0;
        logger.info("END PARSING HTML FROM THYMLEAF===> Waktu eksekusi PARSING HTML: " + durationEndHTML + " detik");
        logger.info("==============");

        logger.info("START CREATE openhtmltopdf PDF with ==>"+limit+" row");
        long startTimepdf = System.currentTimeMillis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);
        builder.withHtmlContent(renderedHtml, null);
        builder.toStream(baos);
        builder.run();
        long endTimePdf = System.currentTimeMillis();
        double durationInSeconds = (endTimePdf - startTimepdf) / 1000.0;
        logger.info("END CREATED openhtmltopdf PDF with "+limit+" row,  ==> Waktu eksekusi: " + durationInSeconds + " detik");
        logger.info("==============");
        // 4. Kembalikan sebagai response
        byte[] pdfBytes = baos.toByteArray();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=estatement.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


//    @GetMapping("/jasper/{limit}")
//    public void reportList(HttpServletResponse response, @PathVariable  int limit) throws Exception {
//        Locale indonesia = new Locale("id", "ID");
//        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(indonesia);
//        formatRupiah.setMaximumFractionDigits(0); // tanpa koma
//        formatRupiah.setMinimumFractionDigits(0);
//        String hasil = formatRupiah.format(limit).substring(2);
//
//        logger.info("START CREATE PDF "+hasil+" row with Jasper report==>");
//        long startTimepdf = System.currentTimeMillis();
//        byte[] pdfBytes = transaksiService.generateListReport(limit);
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=list.pdf");
//        response.getOutputStream().write(pdfBytes);
//        response.getOutputStream().flush();
//        long endTimePdf = System.currentTimeMillis();
//        double durationInSeconds = (endTimePdf - startTimepdf) / 1000.0;
//
//        logger.info("END CREATE PDF =>"+hasil+" row with Jasper report==> Waktu eksekusi: " + durationInSeconds + " detik");
//        logger.info("==============");
//    }


    private void mergePdfs(List<File> inputFiles, File outputFile) throws IOException {
        File outputDir = outputFile.getParentFile();
        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            if (!created) {
                throw new IOException("Gagal membuat folder: " + outputDir.getAbsolutePath());
            }
        }

        PDFMergerUtility merger = new PDFMergerUtility();
        merger.setDestinationFileName(outputFile.getAbsolutePath());

        for (File file : inputFiles) {
            if (file.exists()) {
                merger.addSource(file);
            } else {
                System.err.println("File tidak ditemukan: " + file.getAbsolutePath());
            }
        }

        merger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

    }

    @GetMapping(value = "/generateWithChunk/{limit}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateWithChunk(@PathVariable  int limit) throws  Exception {
        // 1. Data untuk template
        logger.error("START LOAD DB===>");
        long startTimeDB= System.currentTimeMillis();
        Header header = transaksiService.getHeader("dadsdsd");
        List<Detail> details = transaksiService.getDetails(limit);
        long endTimeDB = System.currentTimeMillis();
        double durationInDB = (endTimeDB - startTimeDB) / 1000.0;



        int totalRows = details.size();
        int batchSize = limit < 2500?limit:2500;
        logger.error("END LOAD DB with "+totalRows+" row,  ==> Waktu eksekusi: " + durationInDB + " detik");
//        5000 --> 12detik
        int batchCount = totalRows / batchSize;

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<File> generatedFiles = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(batchCount); // gantikan sleep
        logger.error("START RENDER HTML TO PDF===>");
        long startTimeHTML = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();
        Context context = new Context();
        data.put("logoBase64", getBase64Logo("header"));
        data.put("logoBaseLps", getBase64Logo("footer"));
        data.put("header",header);
        for (int i = 0; i < batchCount; i++) {
            final int batch = i;
            executor.submit(() -> {
                try {
                    int fromIndex = batch * batchSize;
                    int toIndex = Math.min(fromIndex + batchSize, totalRows);
                    List<Detail> batchData = details.subList(fromIndex, toIndex);
                    data.put("details", batchData);
                    context.setVariables(data);
                    // Render HTML dari Thymeleaf
                    String renderedHtml = templateEngine.process("estatement", context);

                    // Simpan hasil PDF sementara
                    File file = new File("output/batch-" + batch + ".pdf");
                    file.getParentFile().mkdirs();
                    try (OutputStream os = new FileOutputStream(file)) {
                        PdfRendererBuilder builder = new PdfRendererBuilder();
                        builder.useFastMode();
                        builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);
                        builder.withHtmlContent(renderedHtml, null);
                        builder.toStream(os);
                        builder.run();
                        generatedFiles.add(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // Menandakan 1 batch selesai
                }
            });
        }

        latch.await(); // Tunggu semua thread selesai
        executor.shutdown();
        // Gabungkan semua PDF hasil batch
        File finalPdf = new File("output/final-report.pdf");
        mergePdfs(generatedFiles, finalPdf);
        //hapuse temp file
        for (File tempFile : generatedFiles) {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        long endTimePdf = System.currentTimeMillis();
        double durationInSeconds = (endTimePdf - startTimeHTML) / 1000.0;
        logger.error("END CREATED openhtmltopdf PDF with "+limit+" row,  ==> Waktu eksekusi: " + durationInSeconds + " detik");

        try {
            File file = new File("output/final-report.pdf");
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }


//            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//            BufferedOutputStream bos = new BufferedOutputStream(new FileInputStream(file),8192));
            InputStreamResource resource = new InputStreamResource(
                    new BufferedInputStream(new FileInputStream(file), 8192)
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=final-report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



    @GetMapping("/memory/{limit}")
    public ResponseEntity<byte[]> memoryV(@PathVariable int limit) throws Exception {

        long startTimeDB = System.currentTimeMillis();
        Header header = transaksiService.getHeader("dadsdsd");
        List<Detail> details = transaksiService.getDetails(limit);
        long endTimeDB = System.currentTimeMillis();
        logger.error("END LOAD DB with " + details.size() + " rows, Waktu eksekusi: " + (endTimeDB - startTimeDB) / 1000.0 + " detik");

        // Batch size lebih dinamis
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int batchSize = Math.min(5000, Math.max(2000, limit / (availableProcessors * 4)));
        logger.error("availableProcessors : " + availableProcessors + " batchSize: " + batchSize);
        int totalRows = details.size();
        int batchCount = (int) Math.ceil((double) totalRows / batchSize);

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        CountDownLatch latch = new CountDownLatch(batchCount);
        List<byte[]> pdfBatches = Collections.synchronizedList(new ArrayList<>());

        // Siapkan data umum yang immutable
        Map<String, Object> baseData = Map.of(
                "logoBase64", getBase64Logo("header"),
                "logoBaseLps", getBase64Logo("footer"),
                "header", header
        );

        logger.error("START RENDER HTML PDF TO MEMORY===>");
        long startTimeRender = System.currentTimeMillis();

        for (int i = 0; i < batchCount; i++) {
            final int batch = i;
            executor.submit(() -> {
                try {
                    int fromIndex = batch * batchSize;
                    int toIndex = Math.min(fromIndex + batchSize, totalRows);
                    List<Detail> batchData = details.subList(fromIndex, toIndex);

                    // Gunakan Map dan Context per thread
                    Map<String, Object> localData = new HashMap<>(baseData);
                    localData.put("details", batchData);
                    Context localContext = new Context();
                    localContext.setVariables(localData);

                    String renderedHtml = templateEngine.process("estatement", localContext);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (BufferedOutputStream bos = new BufferedOutputStream(baos, 16 * 1024)) {
                        PdfRendererBuilder builder = new PdfRendererBuilder();
                        builder.useFastMode();
                        builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);
                        builder.withHtmlContent(renderedHtml, null);
                        builder.toStream(bos);
                        builder.run();
                        bos.flush();
                    }

                    pdfBatches.add(baos.toByteArray());
                } catch (Exception e) {
                    logger.error("Error rendering batch " + batch, e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        // Merge PDF
        ByteArrayOutputStream finalBaos = new ByteArrayOutputStream();
        try (BufferedOutputStream finalBos = new BufferedOutputStream(finalBaos, 16 * 1024);
             PdfWriter writer = new PdfWriter(finalBos);
             PdfDocument merged = new PdfDocument(writer)) {

            PdfMerger merger = new PdfMerger(merged);
            for (byte[] pdfBytes : pdfBatches) {
                try (PdfReader reader = new PdfReader(new BufferedInputStream(new ByteArrayInputStream(pdfBytes), 16 * 1024));
                     PdfDocument src = new PdfDocument(reader)) {
                    merger.merge(src, 1, src.getNumberOfPages());
                }
            }
            finalBos.flush();
        }

        long endTimeRender = System.currentTimeMillis();
        logger.error("END CREATED PDF MEMORY with " + totalRows + " rows, Waktu: " + (endTimeRender - startTimeRender) / 1000.0 + " detik");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=final-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(finalBaos.size())
                .body(finalBaos.toByteArray());
    }









}
