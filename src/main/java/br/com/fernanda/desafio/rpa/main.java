package br.com.fernanda.desafio.rpa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.fernanda.desafio.rpa.Automation.Feriado;
import br.com.fernanda.desafio.rpa.Automation.FeriadoAutomation;
import br.com.fernanda.desafio.rpa.Api.ApiClient;
import br.com.fernanda.desafio.rpa.Database.DatabaseManager;
import java.util.List;


@SpringBootApplication
public class main {
	public static void main(String[] args) {
		try {

			FeriadoAutomation automation = new FeriadoAutomation();
			List<Feriado> feriados = automation.getFeriadosFromExcel();
//			automation.close();


			DatabaseManager dbManager = new DatabaseManager();
			dbManager.createTable();
			dbManager.insertFeriados(feriados);


			ApiClient apiClient = new ApiClient();
			apiClient.sendFeriados(feriados);

			System.out.println("Processo concluído com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}