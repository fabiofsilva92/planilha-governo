package com.estudo.planilhagoverno.resources;

import com.estudo.planilhagoverno.model.Transacao;
import com.estudo.planilhagoverno.services.FileService;
import com.estudo.planilhagoverno.services.TransacaoService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    TransacaoService transacaoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {

        List<Transacao> list = fileService.dealFile(file);
        transacaoService.saveTransacaoFromFile(list);

        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.saveTransacaoFromFile(list));
    }

}
