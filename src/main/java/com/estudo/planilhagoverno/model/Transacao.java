package com.estudo.planilhagoverno.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dataPgto;
    private String cpfServidor;
    private String docFornecedor;
    private String nomeFornecedor;
    private String valor;
    private String tipo;
    private String sublemento;
    private String cdic;

}
