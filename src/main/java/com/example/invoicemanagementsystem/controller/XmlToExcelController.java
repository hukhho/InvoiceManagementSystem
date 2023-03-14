package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dto.Content;
import com.example.invoicemanagementsystem.dto.Invoice;
import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.repository.ExcelTemplateRepository;
import com.example.invoicemanagementsystem.ultils.ExcelUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.core.ApplicationContext;
import org.apache.poi.xssf.usermodel.*;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;

@Controller
@RequestMapping(value = "/convert")
public class XmlToExcelController {
    @Autowired
    ExcelTemplateRepository excelTemplateRepository;

    @RequestMapping("/")
    public void convertXmlToExcel(HttpServletResponse response) throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Invoice.class);
        marshaller.afterPropertiesSet();

        //Dang test, sua lai Multipart import xml
        ClassPathResource xmlResource = new ClassPathResource("input.xml");
        InputStream xmlInputStream = xmlResource.getInputStream();

        Invoice invoice = (Invoice) marshaller.unmarshal(new StreamSource(xmlInputStream));

        // Load Excel template file
        ClassPathResource excelTemplateStream = new ClassPathResource("template.xlsx");
        InputStream templateStream = excelTemplateStream.getInputStream();

        //InputStream templateStream = new FileInputStream("template.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(templateStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        ExcelTemplate excelTemplate = excelTemplateRepository.findById(3L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;

        excelTemplate.getCells().forEach(excelCell -> {
            String cellReference = excelCell.getCellReference();
            int[] rowAndColumn = ExcelUtils.cellReferenceToRowAndColumn(cellReference);
            int row = rowAndColumn[0];
            int col = rowAndColumn[1];
            XSSFCell cell = sheet.getRow(row).getCell(col);
            cell.setCellValue(invoice.getContent().getCellValue(excelCell.getCellValue()));
        });

        // Write Excel data to output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment; filename=output.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
