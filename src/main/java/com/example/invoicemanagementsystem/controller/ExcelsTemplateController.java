package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.entity.*;
import com.example.invoicemanagementsystem.service.ExcelFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/excel-files")
public class ExcelsTemplateController {

    @Autowired
    private ExcelFileService excelFileService;

    @GetMapping
    public String list(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        String username = user.getUsername();

        boolean isAdmin = user.isAdmin();

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("excelFiles", excelFileService.getAllExcelFiles());
        List<ExcelTemplate> excelFiles = excelFileService.getAllExcelFiles();
        model.addAttribute("excelFiles", excelFiles);
        return "excel-file/list";
    }

    @RequestMapping("/create")
    public String createForm(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/auth/login";
        }

        ExcelTemplate excelFile = new ExcelTemplate();
        excelFile.setName("hello");
        model.addAttribute("excelFile", excelFile);

        return "excel-file/form";
    }
    @PostMapping("/create")
    public String create(HttpSession session, @ModelAttribute("excelFile") ExcelTemplate excelFile,
                         @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/auth/login";
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                excelFile.setTemplateFile(bytes);
                excelFileService.createExcelFile(excelFile);
                redirectAttributes.addFlashAttribute("success", "Excel file has been created successfully.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Failed to create Excel file.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
        }
        return "redirect:/excel-files";
    }

    @GetMapping("/{id}/update")
    public String updateForm(HttpSession session, @PathVariable("id") Long id, Model model) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/auth/login";
        }

        Optional<ExcelTemplate> excelFile = excelFileService.getExcelFileById(id);
        if(excelFile.isPresent()) {
            model.addAttribute("excelFile", excelFile.get());
            return "excel-file/form";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(HttpSession session, @PathVariable("id") Long id, @ModelAttribute("excelFile") ExcelTemplate excelFile,
                         @RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/auth/login";
        }

        Optional<ExcelTemplate>  existingExcelFile = excelFileService.getExcelFileById(id);
        if (existingExcelFile.isPresent()) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    excelFile.setTemplateFile(bytes);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", "Failed to update Excel file.");
                    return "redirect:/excel-files";
                }
            } else {
                excelFile.setTemplateFile(existingExcelFile.get().getTemplateFile());
            }
            excelFile.setId(id);
            excelFileService.updateExcelFile(excelFile);
            redirectAttributes.addFlashAttribute("success", "Excel file has been updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Excel file not found.");
        }
        return "redirect:/excel-files";
    }

    @GetMapping("/{id}/delete")
    public String delete(HttpSession session, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/auth/login";
        }

        Optional<ExcelTemplate> excelFile = excelFileService.getExcelFileById(id);
        if (excelFile.isPresent()) {
            excelFileService.deleteExcelFileById(excelFile.get().getId());
            redirectAttributes.addFlashAttribute("success", "Excel file has been deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Excel file not found.");
        }
        return "redirect:/excel-files";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadExcelFile(HttpSession session, @PathVariable Long id) {
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        ExcelTemplate excelFile = excelFileService.getExcelFileById(id) .orElseThrow(() -> new IllegalArgumentException("Invalid file Id:" + id));

        if (excelFile == null || excelFile.getTemplateFile() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileData = excelFile.getTemplateFile();
        ByteArrayResource resource = new ByteArrayResource(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + excelFile.getName() + ".xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }

}

