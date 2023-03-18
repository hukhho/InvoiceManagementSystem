package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dto.Invoice;
import com.example.invoicemanagementsystem.dto.Product;
import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.repository.ExcelTemplateRepository;
import com.example.invoicemanagementsystem.ultils.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/convert")
public class XmlToExcelController {
    @Autowired
    ExcelTemplateRepository excelTemplateRepository;


    @RequestMapping("")
    public String convertXmlToExcelView(Model model) {
        List<ExcelTemplate> excelTemplateList = excelTemplateRepository.findAll();
        model.addAttribute("excelTemplateList", excelTemplateList);

        return "convert";
    }

    @PostMapping("")
    public void convertXmlToExcel(HttpServletResponse response, @RequestParam Long templateId, @RequestParam("xmlFile") MultipartFile xmlFile) throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Invoice.class);
        marshaller.afterPropertiesSet();

//        ClassPathResource xmlResource = new ClassPathResource("input.xml");
//        InputStream xmlInputStream = xmlResource.getInputStream();
        InputStream xmlInputStream = xmlFile.getInputStream();


        Invoice invoice = (Invoice) marshaller.unmarshal(new StreamSource(xmlInputStream));

        List<Product> products = invoice.getContent().getProducts();

        ExcelTemplate excelTemplate = excelTemplateRepository.findById(templateId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;
        XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelTemplate.getTemplateFile()));
        XSSFSheet worksheet = workbook.getSheetAt(0); // Assuming the cell is in the first sheet


        excelTemplate.getCells().forEach(excelCell -> {
            String prefix = "products.";
            if (excelCell.getCellValue().startsWith(prefix)) {
                String result = excelCell.getCellValue().substring(prefix.length());

                String cellReference = excelCell.getCellReference();
                int[] rowAndColumn = ExcelUtils.cellReferenceToRowAndColumn(cellReference);
                int row = rowAndColumn[0];
                int col = rowAndColumn[1];

                for (Product pro : products) {
                    XSSFCell cell = worksheet.getRow(row).getCell(col);
                    cell.setCellValue(pro.getCellValue(result).toString());
                    System.out.print("Set value " + pro.getCellValue(result).toString() + " to row " + row  + " col  " + col);
                    row++; // increment row index for next cell
                }
            } else {
                String cellReference = excelCell.getCellReference();
                int[] rowAndColumn = ExcelUtils.cellReferenceToRowAndColumn(cellReference);
                int row = rowAndColumn[0];
                int col = rowAndColumn[1];
                XSSFCell cell = worksheet.getRow(row).getCell(col);
                cell.setCellValue(invoice.getContent().getCellValue(excelCell.getCellValue()).toString());
            }
        });
        // Write Excel data to output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment; filename=output.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
