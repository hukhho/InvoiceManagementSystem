package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.dto.Invoice;
import com.example.invoicemanagementsystem.entity.ExcelTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.transform.stream.StreamSource;

@Controller
@RequestMapping("/xml")
public class XmlController {


    @PostMapping("/import")
    public String create(@ModelAttribute("template") String template,
                         @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
        } else if (template.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Please select a template to upload.");
        }else {
            try {
                Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
                marshaller.setClassesToBeBound(Invoice.class);
                marshaller.afterPropertiesSet();
                //invoice object đã đc convert
                Invoice invoice = (Invoice) marshaller.unmarshal(new StreamSource(file.getInputStream()));

                redirectAttributes.addFlashAttribute("success", "Excel file has been created successfully.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Failed to create Excel file.");
            }
        }
        return "redirect:/excel-files";
    }
}
