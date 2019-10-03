package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import services.ContractService;
import services.PaypalService;

public class Program {

	public static void main(String[] args) throws ParseException {

		//seta o idioma para usar o . ao inves da ,
		Locale.setDefault(Locale.US);
		//Defini a entrada para o padrao que e o teclado
		Scanner sc = new Scanner(System.in);
		//formatar a data a partir da entrada
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//Pega numero de contrato
		System.out.println("Enter contract data");
		System.out.print("Number: ");
		int number = sc.nextInt();
		//Data do contrato
		System.out.print("Date (dd/MM/yyyy): ");
		Date date = sdf.parse(sc.next());
		//Valor do contrato
		System.out.print("Contract value: ");
		double totalValue = sc.nextDouble();
		
		//Instancia um contrato com numero data e valor
		Contract contract = new Contract(number, date, totalValue);
		
		//Pega o numero de parcelas
		System.out.print("Enter number of installments: ");
		int n = sc.nextInt();
		
		//Instancia um servico que irá processar o contrato baseado em qual pagamento online sera usado
		//Nesse ponto usa uma interface de pagamento especifica
		ContractService contractService = new ContractService(new PaypalService());
		
		//Processa o contrato de acordo com o numero de parcelas lidas
		contractService.processContract(contract, n);
		
		//Imprimi as parcelas
		System.out.println("Installments:");
		for (Installment x : contract.getInstallments()) {
			System.out.println(x);
		}
		
		sc.close();
	}
}
