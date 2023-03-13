package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dao.ExcelFileDao;
import com.example.invoicemanagementsystem.entity.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ExcelsTemplateController {
    @Autowired
    private ExcelFileDao excelFileDao;

    @GetMapping("/")
    public String home(Model model) {
        List<Files> files = excelFileDao.getAllFiles();
        model.addAttribute("files", files);
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("excelFile", new Files());
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("file") MultipartFile file) throws IOException {
        excelFileDao.addFile(file);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Files excelFile = excelFileDao.getFileById(id);
        model.addAttribute("excelFile", excelFile);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("excelFile") Files excelFile, @RequestParam("file") MultipartFile file) throws IOException {
        Files existingExcelFile = excelFileDao.getFileById(id);
        File existingFile = new File(existingExcelFile.getFilePath());
        existingFile.delete();
       // excelFileDao.addFile(excelFile, file);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        excelFileDao.deleteFile(id);
        return "redirect:/";
    }
}

