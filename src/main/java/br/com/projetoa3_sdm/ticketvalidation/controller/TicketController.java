package br.com.projetoa3_sdm.ticketvalidation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetoa3_sdm.ticketvalidation.model.entities.Bank;
import br.com.projetoa3_sdm.ticketvalidation.model.entities.boleto;
import br.com.projetoa3_sdm.ticketvalidation.model.services.TicketService;
import br.com.projetoa3_sdm.ticketvalidation.model.services.dto.TicketRequest;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/boletos")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/validate")
	public boleto validate(@RequestBody TicketRequest request) {
		return ticketService.validate(request);
	}

	@PostMapping("/parse")
	public boleto parse(@RequestBody TicketRequest request) {
		return ticketService.parse(request);
	}

	@GetMapping("/fraud")
	public String getMethodName() {
		return new String("Orientações para denunciar suspeita de fraude em boleto bancário\n" + //
						"\n" + //
						"Pare tudo e não pague\n" + //
						"Se já pagou, não apague nada. Guarde o comprovante.\n" + //
						"\n" + //
						"Separe as informações\n" + //
						"Tenha em mãos: linha digitável, valor, comprovante (se houver), boleto recebido e prints da conversa/e-mail.\n" + //
						"\n" + //
						"Contate seu banco imediatamente\n" + //
						"Use apenas os canais oficiais. Informe que suspeita de fraude e peça abertura de ocorrência e análise/estorno. Anote o protocolo.\n" + //
						"\n" + //
						"Confirme com a empresa emissora\n" + //
						"Entre em contato pelo site oficial da empresa e confirme se o boleto foi realmente emitido por eles.\n" + //
						"\n" + //
						"Registre um Boletim de Ocorrência\n" + //
						"Pode ser online. Anexe todas as evidências.\n" + //
						"\n" + //
						"Envie tudo ao banco para estorno\n" + //
						"Encaminhe BO, comprovante e prints ao banco e acompanhe o protocolo até a conclusão.");
	}


	@GetMapping("/codes")
	public List<Bank> getCodes() {
		return ticketService.getBankCodes();
	}
	
}
