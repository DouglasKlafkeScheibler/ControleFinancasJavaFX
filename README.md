# Controle Finanças 

## Sobre o projeto
Esse projeto tem o intuito de auxiliar no controle de despesas como luz, água, etc. Para fazer esse controle, o usuário terá a opção de cadastrar contas mensais, e, após o cadastramento, ele terá os recursos de edição, deleção e busca.

## Tecnologias utilizadas:
Para o auxílio da criação da interface gráfica, foi utilizado o SceneBuilder, que monta toda a estrutura em código FXML. Para o armazenamento de dados, está sendo utilizado um banco remoto da AWS(Amazon Web Services) onde a escolha do banco foi o Postgres.

## Começando
Para a aplicação conseguir rodar em sua máquina, é preciso que algumas variáveis de ambiente sejam criadas. Também é necessário que o jdk e o javafx-jdk estejam instalados na máquina. Para facilitar esse processo de instalação, fiz um vídeo no YouTube, onde faço passo a passo daquilo que deve ser feito para deixar a aplicação rodando. Caso prefira ver o vídeo, entre nesse link: https://www.youtube.com/watch?v=6ks9rTY3amo&t=22s.

### COMO INSTALAR A APLICAÇÃO NA SUA MÁQUINA

1 - Abra esta pasta de compartilhamento (https://drive.google.com/drive/folders/1IDgRl_V2XJBCKF5Z8hV6uI8py1m_QxzC?usp=drive_open) e baixe o arquivo chamado "DistControleFinancas" e o abra.

2 - Abra o "Disco local (c:)" e vá para "arquivos e programas". A seguir, crie uma pasta chamada "java" e cole, dentro desta, a pasta a "jdk-11.0.2".
Dentro da pasta "jdk-11.0.2" copie o seu destino. ex: C:\Program Files\Java\jdk-11.0.2 (IMPORTANTE: O CAMINHO NÃO PODE ESTAR ERRADO!)

3* - Vá às "Variáveis de Ambiente" do seu PC, e então, na parte de variáveis do sistema, crie uma nova variável, nomeando-a "JAVA_HOME" 
(IMPORTANTE: NOME NÃO PODE ESTAR ERRADO!), e no campo "valor", cole o destino copiado no passo 2.

4 - Ainda nas variáveis do sistema, encontre a variável chamada "Path" e a edite. A seguir, crie uma nova variável, e a nomeie "%JAVA_HOME%\bin", caso não exista.

5 - Vá até "Disco local(c:)" e crie uma pasta chamada "java-libs" e, dentro dela, cole a pasta "javafx-sdk". Depois de colada, abra a pasta "javafx-sdk" e, em seguida,
abra a pasta "lib" e copie o local de destino. EX: C:\Java-Libs\javafx-sdk\lib

6 - Volte para "variáveis de ambiente" e crie uma nova variável chamada "PATH_TO_FX" (IMPORTANTE: NÃO PODE ESTAR ERRADO!), e, no campo referente ao valor,
cole o destino copiado no passo 5.

7 - Vá para a pasta disponibilizada chamada "DistControleFinancas" e copie os seguintes arquivos: "controlefinancas.bat", "controleFinancasJDBC.jar" e "db.properties".
A seguir, em "arquivos e programas", crie uma pasta chamada "Controle Finanças" e cole os arquivos.

8 - Agora, é só testar a aplicação clicando no arquivo "controlefinancas.bat"! Todo e qualquer feedback é incentivado e muito apreciado.
Qualquer dúvida ou feedback pode ser enviado para douglasks99@gmail.com.
Obrigado pelo interesse!

3*: Caso não saiba encontrar as variáveis de ambiente da sua máquina, pesquise, no campo de busca, "variáveis de ambiente".