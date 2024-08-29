package br.com.fernanda.desafio.rpa.Automation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FeriadoAutomation {

    public void pauseTerminal() {
        Scanner input = new Scanner(System.in);
        System.out.print("Press Enter to quit...");
        input.nextLine();
    }

    public WebDriver getWebdriver()  throws Exception {
        System.out.println("Selenium Start Open");
        WebDriver driver;



        ChromeOptions options = new ChromeOptions();
        System.out.println("Selenium Open chrome options");


        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);

        System.out.println("Selenium Finish Open");
        return driver;
    }


    public FeriadoAutomation() {
        System.out.println("Iniciado!");
    }

    public List<Feriado> getFeriadosFromExcel() throws Exception {
        System.out.println("Selenium Start getFeriadosFromExcel");
        String excelFilePath = "/files/Projeto vaga Ambiental.xlsx";

        List<Feriado> feriados = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                break;
            }

            System.out.println("List Estados: "+feriados.size());
            Cell estadoCell = row.getCell(0);
            System.out.println("Cell 0: "+estadoCell);
            Cell cidadeCell = row.getCell(1);
            System.out.println("Cell 1: "+cidadeCell);
            System.out.println("List Estados: "+feriados.size());

            if (estadoCell != null && cidadeCell != null) {
                String estado = estadoCell.getStringCellValue();
                String cidade = cidadeCell.getStringCellValue();
                List<Feriado> feriadosCidade = null;
                for (int i = 0; feriadosCidade == null && i < 5; i++) {
                    feriadosCidade = getFeriadosFromSite(estado, cidade);
                }
                if (feriadosCidade != null) {
                    feriados.addAll(feriadosCidade);
                }
            }
        }

        workbook.close();
        fis.close();
        System.out.println("Selenium Finish getFeriadosFromExcel");
        return feriados;
    }

    private List<Feriado> getFeriadosFromSite(String estado, String cidade) throws Exception {
        System.out.println("Selenium Start getFeridosFromSite (" + estado + ", " + cidade + ")");
        List<Feriado> feriados = null;
        WebDriver driver = null;
        System.out.println("getFeriadosFromSite: Option Estado");

        try {
            feriados = new ArrayList<>();
            driver = getWebdriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.get("https://feriados.com.br");

            WebElement estadoDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#estado"))); // visibilityOfElementLocated
            List<WebElement> estadoOptions = estadoDropdown.findElements(By.tagName("option"));
            for (WebElement option : estadoOptions) {
                if (option.getText().equalsIgnoreCase(estado)) {
                    option.click();
                    break;
                }
            }

            System.out.println("getFeriadosFromSite: Option Cidade");
            WebElement cidadeDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#cidade"))); // visibilityOfElementLocated
            List<WebElement> cidadeOptions = cidadeDropdown.findElements(By.tagName("option"));
            for (WebElement option : cidadeOptions) {
                if (option.getText().equalsIgnoreCase(cidade)) {
                    option.click();
                    break;
                }
            }

            System.out.println("getFeriadosFromSite: Option Estado");

            List<WebElement> feriadoElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("style_lista_feriados")));
            for (WebElement element : feriadoElements) {
                String feriadoText = element.getText();
                String[] parts = feriadoText.split(" - ");
                String dataString = parts[0].trim();
                String feriadoNome = parts[1].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataString, formatter);

                String tipo = feriadoNome.contains("Municipal") ? "MUNICIPAL" : "NACIONAL";

                feriados.add(new Feriado(estado, cidade, data, tipo, feriadoNome));
                System.out.println("getFeriadosFromSite: Achou Feriado! -> " + feriadoNome);
            }
            System.out.println("Selenium Finish getFeridosFromSite (" + estado + ", " + cidade + ")");
            driver.quit();
        } catch (WebDriverException e) {
            if (e.getMessage().contains("session deleted because of page crash")) {

                System.out.println("Caught a page crash exception: " + e.getMessage());


            } else {

                System.out.println("Caught a WebDriverException: " + e.getMessage());
            }

            driver.quit();
            return null;
        }

        return feriados;
    }


}
