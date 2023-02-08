package com.estudo.planilhagoverno.services;

import com.estudo.planilhagoverno.model.Transacao;
import com.estudo.planilhagoverno.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    TransacaoRepository transacaoRepository;

    public List<Transacao> saveTransacaoFromFile(List<Transacao> transacaoList){
        transacaoList.forEach(t -> {
            saveTransacao(t);
        });
        return transacaoList;
    }

    public Transacao saveTransacao(Transacao transacao){
        return transacaoRepository.save(transacao);
    }

}
