//package com.example.invoicemanagementsystem.controller;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/convert")
//public class XmlToExcelController {
//
//    @GetMapping("/xml-to-excel")
//    public void convertXmlToExcel(
//            HttpServletResponse response,
//            @RequestParam(name = "xmlData") String xmlData
//    ) throws IOException {
//        // Create Excel workbook and sheet
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Invoice Data");
//
//        // Parse XML data and populate Excel sheet
//        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());
//        XmlInvoice invoice = XmlUtils.parseXml(inputStream);
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Product Code");
//        headerRow.createCell(1).setCellValue("Product Name");
//        headerRow.createCell(2).setCellValue("Quantity");
//        headerRow.createCell(3).setCellValue("Unit");
//        headerRow.createCell(4).setCellValue("Price");
//        headerRow.createCell(5).setCellValue("Amount");
//        int rowNum = 1;
//        for (XmlProduct product : invoice.getProducts()) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(product.getCode());
//            row.createCell(1).setCellValue(product.getName());
//            row.createCell(2).setCellValue(product.getQuantity());
//            row.createCell(3).setCellValue(product.getUnit());
//            row.createCell(4).setCellValue(product.getPrice());
//            row.createCell(5).setCellValue(product.getAmount());
//        }
//
//        // Set response headers
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=invoice.xlsx");
//
//        // Write workbook to response output stream
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }
//
//}
