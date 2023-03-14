//package com.example.invoicemanagementsystem.dao;
//
//import com.example.invoicemanagementsystem.entity.Files;
//import com.example.invoicemanagementsystem.repository.FilesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class ExcelFileDaoImpl implements ExcelFileDao {
//
//    private static final String FILES_DIRECTORY = "templates/";
//
//    @Autowired
//    private FilesRepository filesRepository;
//
//    @Override
//    public List<Files> getAllFiles() {
//        List<Files> files = new ArrayList<>();
//        File folder = new File(FILES_DIRECTORY);
//        if (folder.exists()) {
//            File[] listOfFiles = folder.listFiles();
//            if (listOfFiles != null) {
//                for (File file : listOfFiles) {
//                    if (file.isFile()) {
//                        Files excelFile = new Files();
//                        excelFile.setFileName(file.getName());
//                        //excelFile.setUploadedDate(new Date(file.lastModified()));
//                        excelFile.setFilePath(file.getAbsolutePath());
//                        files.add(excelFile);
//                    }
//                }
//            }
//        }
//        return files;
//    }
//
//    @Override
//    public void addFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//        String filePath = FILES_DIRECTORY + fileName;
//        File dest = new File(filePath);
//        file.transferTo(dest);
//
////sua o day
////        Files files = new Files();
////        files.setFileName(fileName);
////       // excelFile.setUploadedDate(new Date());
////        files.setFilePath(dest.getAbsolutePath());
////        filesRepository.save(files);
//
//
//    }
//
//    @Override
//    public Files getFileById(int id) {
//        Files excelFile = new Files();
//        File file = new File(FILES_DIRECTORY + excelFile.getFileName());
//        if (file.exists()) {
//            excelFile.setFileName(file.getName());
//            //excelFile.setUploadedDate(new Date(file.lastModified()));
//            excelFile.setFilePath(file.getAbsolutePath());
//        }
//        return excelFile;
//    }
//
//    @Override
//    public boolean deleteFile(int id) {
//        Files excelFile = getFileById(id);
//        File file = new File(excelFile.getFilePath());
//        return file.delete();
//    }
//}