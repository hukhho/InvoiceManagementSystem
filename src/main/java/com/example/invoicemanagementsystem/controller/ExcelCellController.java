package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dto.Content;
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

    @GetMapping
    public String list(Model model) {
        List<ExcelCell> excelCells = excelCellRepository.findAll();

        model.addAttribute("excelCells", excelCells);

        return "excel-cell/list";
    }


    @GetMapping("/create")
    public String createForm(Model model) {
        List<ExcelTemplate> excelTemplates = excelTemplateRepository.findAll();
        Content content = new Content();
        ExcelTemplate excelTemplate = excelTemplateRepository.findById(5L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;
        List<String> fieldNames = Arrays.stream(Content.class.getDeclaredFields())
                .map(Field::getName)
                .filter(fieldName -> !fieldName.equals("serialVersionUID")).toList();
        List<ExcelCell> excelCells = excelCellRepository.findAll();

        model.addAttribute("excelCells", excelCells);
        model.addAttribute("excelTemplate", excelTemplate);
        model.addAttribute("content", content);
        model.addAttribute("fieldNames", fieldNames);
        return "excel-cell/form";
    }

    @PostMapping("/create")
    public String create(@RequestBody String cellReference, @RequestBody String value) {
//        ExcelCell save = excelCellRepository.save(excelCell);
        System.out.println(cellReference);
        return "redirect:/excel-cells";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ExcelCell excelCell = excelCellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ExcelCell ID: " + id));
        List<ExcelTemplate> excelTemplates = excelTemplateRepository.findAll();
        model.addAttribute("excelCell", excelCell);
        model.addAttribute("excelTemplates", excelTemplates);
        return "excel-cell/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute ExcelCell excelCell) {
        ExcelCell existingExcelCell = excelCellRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ExcelCell ID: " + id));
        existingExcelCell.setTemplate(excelCell.getTemplate());
        existingExcelCell.setCellReference(excelCell.getCellReference());
        existingExcelCell.setCellValue(excelCell.getCellValue());
        excelCellRepository.save(existingExcelCell);
        return "redirect:/excel-cells";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        excelCellRepository.deleteById(id);
        return "redirect:/excel-cells";
    }
}
