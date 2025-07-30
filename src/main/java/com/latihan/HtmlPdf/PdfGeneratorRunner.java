package com.latihan.HtmlPdf;


import com.latihan.HtmlPdf.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Base64;

@Component
public class PdfGeneratorRunner implements CommandLineRunner {



    @Autowired
    private TransaksiService repoService;

    public String getBase64Logo() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/images/logo.png");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
//        System.out.println("data:image/png;base64," + Base64.getEncoder().encodeToString(bytes));
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public void run(String... args) throws Exception {
//
//        System.out.println("START DB");
//       Header header = repoService.getHeader("dadsdsd");
//       List<Detail> details = repoService.getDetails(3000);
//        System.out.println("END DB");
//
//
//
//       Map<String, Object> data = new HashMap<>();
//        data.put("logoBase64", getBase64Logo());
//        data.put("header",header);
//        data.put("details", details);
////        System.out.println("START GENERATE==>PDF");
//         pdfGenerateService.generatePdfFile("testLoad", data, "dataDetail.pdf");
////        System.out.println("ENDDDDD GENERATE==>PDF");
    }
}
