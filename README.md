#Descrição do Problema#
O desafio consistiu em criar uma automação (RPA) em Java, utilizando Selenium, para extrair dados de feriados municipais e nacionais do site feriados.com.br, com base em uma planilha Excel fornecida, que contém as colunas de estado e cidade. Os dados extraídos deveriam ser salvos em uma tabela PostgreSQL e, posteriormente, enviados para uma API específica que validaria a operação, retornando uma mensagem de sucesso ou erro.

##Solução Implementada##

Para resolver o desafio, desenvolvi o projeto utilizando três classes principais:

###API (APIClient):### Esta classe gerencia a comunicação com a API externa, realizando requisições HTTP e lidando com o tratamento de erros.

###Database (DatabaseManager):### Responsável por todas as operações no banco de dados, como conexão, inserção e consulta dos dados dos feriados extraídos.

###Automation (FeriadoAutomation e Feriado):### Aqui foi implementada a automação com Selenium. A classe FeriadoAutomation é responsável por toda a lógica de automação que coleta os dados do site feriados.com.br para cada cidade e estado. 
A classe Feriado é utilizada para gerenciar os getters e setters dos atributos relacionados aos feriados, como data, tipo e nome do feriado.
