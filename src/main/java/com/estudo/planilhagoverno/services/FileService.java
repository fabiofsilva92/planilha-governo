package com.estudo.planilhagoverno.services;

import com.estudo.planilhagoverno.PlanilhaGovernoApplication;
import com.estudo.planilhagoverno.model.Transacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class FileService {


    public static void main(String[] args) throws IOException {
        fixFile();
    }

    private static void fixFile() throws IOException {

        String filePath = "C:\\temp\\Planilha12003a2022.csv";
        System.out.println(filePath);
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        BufferedReader brAuxiliar = new BufferedReader(fileReader);
        br.readLine();
        brAuxiliar.readLine();
        brAuxiliar.readLine();
        String line = br.readLine();
//        List<Transacao> list = new ArrayList<>();

        int numeroLinha = 1;
        StringBuilder fileString = new StringBuilder();

        while (line != null){

            String[] split = line.split(";");
            if(split.length == 8){
                if(line.endsWith("R.08.")){
                    System.out.println("Linha termina com R.08.: "+line);
                    String proximaLinha = brAuxiliar.readLine();
                    System.out.println("Proxima Linha: "+proximaLinha);
                    String linhaPronta = line+proximaLinha;
                    System.out.println("Como fica nova linha: " +linhaPronta);
                    fileString.append(linhaPronta);
//                    fileString.append(brAuxiliar.readLine());
                    fileString.append("\n");
                }
                else{
                    fileString.append(line);
                    fileString.append("\n");
                }
            }
            line = br.readLine();
            brAuxiliar = br;
            numeroLinha++;
        }

        br.close();
        fileReader.close();

//        System.out.println("String construida: " +fileString.toString());

        String novoArquivoPath = "C:\\temp\\Planilhateste.csv";

        File dbFile = new File(novoArquivoPath);
        if(dbFile.createNewFile()){
            log.info("Arquivo criado");
        }
        FileWriter myWriter = new FileWriter(dbFile, true);
        BufferedWriter brTeste = new BufferedWriter(myWriter);
        PrintWriter pr = new PrintWriter(brTeste);

        pr.print(fileString.toString());
        log.info("Successfully wrote to the file.");


        pr.close();
        br.close();
        myWriter.close();

//        list.forEach(i -> System.out.println("imprimindo da lista : "+i));

    }

    private static void readFile() throws IOException {

        String filePath = "C:\\temp\\Planilha12003a2022-sample.csv";
        System.out.println(filePath);
        File dbFile = new File(filePath);
        FileReader fileReader = new FileReader(dbFile);
        BufferedReader br = new BufferedReader(fileReader);

        String line = br.readLine();
        line = br.readLine();
        List<Transacao> list = new ArrayList<>();

        int numeroLinha = 1;
        while (line != null){
//            System.out.println(line);

            list.add(lineToObj(List.of(line.split(";")), numeroLinha));

            line = br.readLine();
            numeroLinha++;
        }

        br.close();
        fileReader.close();

        list.forEach(i -> System.out.println("imprimindo da lista : "+i));

    }

    public List<Transacao> dealFile(MultipartFile file) throws IOException {

        InputStream is = new ByteArrayInputStream(file.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        br.readLine();
        String line = br.readLine();
        List<Transacao> list = new ArrayList<>();
        int numeroLinha = 1;
        while (line != null){
//            System.out.println(line);

            list.add(lineToObj(List.of(line.split(";")), numeroLinha));

            line = br.readLine();
            numeroLinha++;
        }

        br.close();
        is.close();

        list.forEach(i -> System.out.println("imprimindo da lista : "+i));

        return list;
    }



    public static String objToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("Error when convert Obj to JSON:"+e.getMessage());
        }
        return "";
    }

    public static Object jsonToObj(String json, Class<?> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            System.out.println("Error when convert Obj to JSON:"+e.getMessage());
        }
        return null;
    }



    private static Transacao lineToObj(List<String> split, int numero){
        if(split.size() != 8) throw new RuntimeException("received list different from expected during lineToObj, line "+numero + ", size : " +split.size());
        Map<String, String> map = new HashMap<>();
        map.put("dataPgto", split.get(0));
        map.put("cpfServidor", split.get(1));
        map.put("docFornecedor", split.get(2));
        map.put("nomeFornecedor", split.get(3));
        map.put("valor", split.get(4));
        map.put("tipo", split.get(5));
        map.put("sublemento", split.get(6));
        map.put("cdic", split.get(7));
        String json = objToJson(map);
        Object o = jsonToObj(json, Transacao.class);
        Transacao transacao = (Transacao)o;
        return transacao;
    }
}
