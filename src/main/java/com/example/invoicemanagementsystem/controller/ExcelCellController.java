package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dto.Content;
import com.example.invoicemanagementsystem.dto.Product;
import com.example.invoicemanagementsystem.entity.ExcelCell;
import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.repository.ExcelCellRepository;
import com.example.invoicemanagementsystem.repository.ExcelTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/excel-cells")
public class ExcelCellController {
    @Autowired
    private ExcelCellRepository excelCellRepository;

    @Autowired
    private ExcelTemplateRepository excelTemplateRepository;

    @GetMapping("/{templateId}")
    public String list(Model model, @PathVariable Long templateId) {
        return createForm(model, templateId);
    }

    @GetMapping("/create/{templateId}")
    public String createForm(Model model, @PathVariable Long templateId) {
        List<ExcelTemplate> excelTemplates = excelTemplateRepository.findAll();
        Content content = new Content();
        ExcelTemplate excelTemplate = excelTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;
        List<String> fieldNames = Arrays.stream(Content.class.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals("serialVersionUID")).toList();
        List<ExcelCell> excelCells = excelCellRepository.findAllByTemplateId(templateId);

        model.addAttribute("excelCells", excelCells);
        model.addAttribute("excelTemplate", excelTemplate);
        model.addAttribute("content", content);
        model.addAttribute("fieldNames", fieldNames);
        return "excel-cell/form";
    }

    @PostMapping("/create/{templateId}")
    public String create(@RequestParam String cellReference, @RequestParam String value, @PathVariable Long templateId) {
//        ExcelCell save = excelCellRepository.save(excelCell);
        ExcelTemplate excelTemplate = excelTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;

        ExcelCell excelCell = new ExcelCell();
        excelCell.setTemplate(excelTemplate);
        excelCell.setCellReference(cellReference);
        excelCell.setCellValue(value);
        excelCellRepository.save(excelCell);

//        System.out.println(cellReference);

        return "redirect:/excel-cells/" + templateId;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,  Model model) {
        ExcelCell excelCell = excelCellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ExcelCell ID: " + id));
        List<ExcelTemplate> excelTemplates = excelTemplateRepository.findAll();
//        System.out.println(excelCell.());

        List<String> fieldNames = Arrays.stream(Content.class.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals("serialVersionUID")).toList();

        List<String> fieldNameProducts = Arrays.stream(Product.class.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals("serialVersionUID")).toList();


        model.addAttribute("excelCell", excelCell);
        model.addAttribute("fieldNames", fieldNames);
        model.addAttribute("fieldNameProducts", fieldNameProducts);
        model.addAttribute("excelTemplates", excelTemplates);
        return "excel-cell/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @RequestParam String fieldName, @RequestParam(required = false) String fieldNameProducts, @RequestParam String cellReference) {

        ExcelCell existingExcelCell = excelCellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ExcelCell ID: " + id));

        if (fieldName.trim().equals("products")) {
            existingExcelCell.setCellValue("products." + fieldNameProducts);
        } else {
            existingExcelCell.setCellValue(fieldName);
        }
        existingExcelCell.setCellReference(cellReference.trim());
        excelCellRepository.save(existingExcelCell);
        return "redirect:/excel-cells/" + existingExcelCell.getTemplate().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ExcelCell existingExcelCell = excelCellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ExcelCell ID: " + id));
        excelCellRepository.deleteById(id);
        return "redirect:/excel-cells/" + existingExcelCell.getTemplate().getId();
    }
}
