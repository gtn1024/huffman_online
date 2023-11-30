package com.example.huffman_online.controller;

import com.example.huffman_online.huffman.HuffmanCompress;
import com.example.huffman_online.huffman.HuffmanDecompress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping(value = "/encode", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> encode(@RequestPart("file") MultipartFile file) throws IOException {
        Path tempInputFile = Files.createTempFile("temp_input_file", ".dat");
        file.transferTo(tempInputFile);

        File tempOutputFile = Files.createTempFile("temp_output_file", ".dat").toFile();
        HuffmanCompress.compress(tempInputFile.toFile(), tempOutputFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "encoded_file.dat");
        byte[] encodedFileBytes = Files.readAllBytes(tempOutputFile.toPath());
        return new ResponseEntity<>(encodedFileBytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/decode", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> decode(@RequestPart("file") MultipartFile file) throws IOException {
        Path tempInputFile = Files.createTempFile("temp_input_file", ".dat");
        file.transferTo(tempInputFile);

        File tempOutputFile = Files.createTempFile("temp_output_file", ".dat").toFile();
        HuffmanDecompress.decompress(tempInputFile.toFile(), tempOutputFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "decoded_file.dat");
        byte[] encodedFileBytes = Files.readAllBytes(tempOutputFile.toPath());
        return new ResponseEntity<>(encodedFileBytes, headers, HttpStatus.OK);
    }
}
