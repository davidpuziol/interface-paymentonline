package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	//interface responsavel gerar o valor das parcelas baseados nos seus servicos e juros
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	//Metodo para processar contrato
	public void processContract(Contract contract, int months) {
		//valor da parcela padrao sem reajustes
		double basicQuota = contract.getTotalValue() / months;
		//Vai adicionar os meses nas datas e atualizar as parcelas
        for (int i = 1; i <= months; i++) {
        	//Adiciona o mes
            Date date = addMonths(contract.getDate(), i);
            //Atualiza a parcela fixa baseada no juros mensal
            double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
            //Acrescenta na parcela o valor do servico de pagamento
            updatedQuota += onlinePaymentService.paymentFee(updatedQuota);
            //Adiciona ao contrato a parcela
            contract.addInstallment(new Installment(date, updatedQuota));
        }
	}
	
	//Metodo para acrescentar meses na data informada.
	private Date addMonths(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
}
