package com.latihan.HtmlPdf.controller;

import com.latihan.HtmlPdf.model.Transaksi;
import com.latihan.HtmlPdf.model.User;
import com.latihan.HtmlPdf.service.TransaksiService;
import com.latihan.HtmlPdf.service.UserService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;



    @GetMapping(value = "/generatePdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf() throws IOException, DocumentException {
        // 1. Data untuk template
        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("transaksi",  transaksiService.getAllTransaksi());
        context.setVariable("nama", "HENDRA SUMARNO");
        context.setVariable("departemen", "Technology & Operations");
        context.setVariable("divisi", "Digital Delivery");
        context.setVariable("unit", "Back Office Development");
        context.setVariable("periode", "01 SEPTEMBER 2024 - 30 SEPTEMBER 2024");
        context.setVariable("tanggalLaporan", "1 OKTOBER 2024");
        context.setVariable("cif", "T026080");
        context.setVariable("rekening", "1111111111111111");
        context.setVariable("cabang", "SDM KANTOR PUSAT JAKARTA");
        context.setVariable("produk", "Tabungan Karyawan");
        context.setVariable("mataUang", "IDR");
        context.setVariables(data);
        // 2. Render HTML dari template
        String renderedHtml = springTemplateEngine.process("template", context);

        // 3. Convert HTML ke PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(renderedHtml);
        renderer.layout();
        renderer.createPDF(baos);

        // 4. Kembalikan sebagai response
        byte[] pdfBytes = baos.toByteArray();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=transaksi.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping(value = "/generate")
    public String generateDocument() {

        String finalHtml = null;

        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("transaksi",  transaksiService.getAllTransaksi());
        context.setVariable("nama", "HENDRA SUMARNO");
        context.setVariable("departemen", "Technology & Operations");
        context.setVariable("divisi", "Digital Delivery");
        context.setVariable("unit", "Back Office Development");
        context.setVariable("periode", "01 SEPTEMBER 2024 - 30 SEPTEMBER 2024");
        context.setVariable("tanggalLaporan", "1 OKTOBER 2024");
        context.setVariable("cif", "T026080");
        context.setVariable("rekening", "1111111111111111");
        context.setVariable("cabang", "SDM KANTOR PUSAT JAKARTA");
        context.setVariable("produk", "Tabungan Karyawan");
        context.setVariable("mataUang", "IDR");
        context.setVariables(data);
        finalHtml = springTemplateEngine.process("template", context);


//        documentGenerator.htmlToPdf(finalHtml);
        return "Success";
    }

    @PostMapping
    public Transaksi createUser(@RequestBody Transaksi transaksi) {
        return transaksiService.createTransaksi(transaksi);
    }

    @GetMapping
    public List<Transaksi> getAllTransaksi() {
        return transaksiService.getAllTransaksi();
    }

    @GetMapping("/{id}")
    public Transaksi getUserById(@PathVariable Long id) {
        return transaksiService.getTransID(id);
    }

    @PutMapping("/{id}")
    public Transaksi updateUser(@PathVariable Long id, @RequestBody Transaksi user) {
        return transaksiService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        transaksiService.deleteTransaksi(id);
    }

}
