package com.senai.ContaBancaria.Domain.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import org.springframework.stereotype.Service;

@Service
public class PagamentoDomainService {

    public PagamentoDTO realizarPagamento(){ //realiza o pagamento, retirando o dinheiro da conta

    }

    public PagamentoDTO pagamentoEfetivado(){ //Envia confirmação de que o pagamento foi realizado

    }

    public PagamentoDTO calcularValorFinalDebitado(){ //RN, mostrando como o saldo ficou após a retirada do dinheiro

    }

    public PagamentoDTO cancelarFaturaSaldoInsuficiente(){ //caso o saldo for insuficiente para realizar a operação, ele cancela

    }

    public PagamentoDTO listarPagamentos() {
    }
}

//fará aplicação da Taxa, as suas regras de negócio