package br.com.projetoa3_sdm.ticketvalidation.model.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;
import br.com.projetoa3_sdm.ticketvalidation.model.entities.boleto;
import br.com.projetoa3_sdm.ticketvalidation.model.repositories.BankRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private BankRepository bankRepository;

    @Test
    void parseAux_populatesBankName_fromRequestWhenProvided() {
        String linha = "00190500954014481606906809350314337370000000100"; // exemplo 47 dígitos
        Bank bank = new Bank();
        bank.setCodigoDoBanco("001");
        bank.setNomeDoBanco("BANCO 001");
        when(bankRepository.findByCodigoDoBanco("001")).thenReturn(Optional.of(bank));

        boleto b = ticketService.parseAux(linha, "Banco 001");

        assertNotNull(b);
        assertEquals("Banco 001", b.getNomeDoBanco());
    }

    @Test
    void parseAux_usesBankRepository_whenRequestBankNull() {
        String linha = "00190500954014481606906809350314337370000000100";
        Bank bank = new Bank();
        bank.setCodigoDoBanco("001");
        bank.setNomeDoBanco("BANCO 001");
        when(bankRepository.findByCodigoDoBanco("001")).thenReturn(Optional.of(bank));

        boleto b = ticketService.parseAux(linha, null);

        assertNotNull(b);
        assertEquals("BANCO 001", b.getNomeDoBanco());
    }
}
