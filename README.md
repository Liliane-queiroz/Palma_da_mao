//================================//
Marketplace Rural - palma do campo
//================================//
Plataforma web para conectar produtores rurais e consumidores urbanos,
facilitando a divulgação de produtos e serviços. Projeto desenvolvido
como trabalho final do curso formação em tecnologia.
Arquitetura MVC em Java EE clássico (Servlet + JSP), sem framework,
com persistência via JDBC em MySQL.

---

Pré-requisitos:
Antes de rodar o projeto, cada integrante do grupo precisa ter instalado
na própria máquina:
Ferramenta Versão usada no projeto Onde baixar
JDK 26 https://jdk.java.net/ (ou Adoptium: https://adoptium.net/)
Maven 3.9.16 https://maven.apache.org/download.cgi (Binary zip archive)
Apache Tomcat 11.x https://tomcat.apache.org/download-11.cgi
MySQL 8.x https://dev.mysql.com/downloads/mysql/
VS Code qualquer versão recente https://code.visualstudio.com/

!!!Extensões do VS Code necessárias!!!
Extension Pack for Java (Microsoft) — inclui suporte a Java, Debugger e Maven;
Community Server Connectors — para conectar e rodar o Tomcat direto do VS Code;

---

//Configurando o ambiente (uma vez por máquina)//

1. Configurar JAVA_HOME

Descubra onde o JDK foi instalado (ex: `C:\Program Files\Java\jdk-26`)
1.1 Descobrir o caminho onde o Java 26 está instalado
1.2 Abra o terminal (pode ser o mesmo do VS Code) e rode:
where java
Isso vai mostrar um caminho tipo:
C:\Program Files\Java\jdk-26\bin\java.exe
O que você precisa é a pasta --sem-- o \bin\java.exe no final, ou seja, algo como:
C:\Program Files\Java\jdk-26

////Depois de confirmar o caminho:////

2.  Configurar a variável JAVA_HOME no Windows

        2.1 Aperte a tecla windows e digite "Variáveis de Ambiente" (ou "Environment Variables") → abra "Editar as variáveis de ambiente do sistema"
        2.2 Clique em "Variáveis de Ambiente..." (botão embaixo)
        2.3 Na seção "Variáveis do sistema" (a de baixo, não na variaveis do usuário), clique em "Novo..."
        2.4 Nome da variável: JAVA_HOME
        2.5 Valor da variável: o caminho que você confirmou (ex: C:\Program Files\Java\jdk-26)
        2.6 Clique OK

        Agora edite o Path:

        2.7 Selecione a variável Path (nas variáveis do sistema) → Editar...
        2.8 Novo → adicione: %JAVA_HOME%\bin
        2.9 OK em tudo

3.  Reiniciar o terminal (importante!)

Variáveis de ambiente só são lidas quando o terminal abre — feche todas as janelas do VS Code e abra de novo (não basta só abrir um novo terminal dentro do VS Code que já estava aberto). 5. Confirmar que funcionou

No terminal novo, rode:
echo %JAVA_HOME%

                    versão resumida:

Crie uma variável de ambiente do sistema chamada `JAVA_HOME` apontando para essa pasta
Adicione `%JAVA_HOME%\bin` à variável `Path`
Teste no terminal: `java -version`

2. Configurar MAVEN_HOME
   Mesmo processo do JAVA_HOME

   versão resumida:
   Extraia o Maven baixado em uma pasta fixa (ex: `C:\apache-maven-3.9.16`)
   Crie uma variável de ambiente do sistema chamada `MAVEN_HOME` apontando para essa pasta
   Adicione `%MAVEN_HOME%\bin` à variável `Path`
   Reinicie o terminal e teste: `mvn -version`

3. Configurar o Tomcat na extensão do VS Code

Instale a extensão Community Server Connectors
Na aba lateral do VS Code, expanda a seção SERVERS
Clique em Create New Server...
Escolha usar uma instalação já existente do Tomcat (ou deixe a extensão baixar uma, se preferir)
Selecione a versão Tomcat 11.x
-Escolha a opção de usar um servidor já existente no disco;
-Vai pedir o caminho da instalação — aponte para a pasta raiz do seu Tomcat (a que contém as pastas bin/, conf/, lib/, webapps/)
Se estiver em dúvida de qual pasta é essa;
-no terminal você pode achar rodando echo $CATALINA_HOME (Linux/Mac) ou verificando a variável de ambiente CATALINA_HOME (Windows);
Recomendação
(Use a porta padrão do Tomcat: 8080. Não tem motivo pra fugir dela a menos que algo já esteja rodando nessa porta na sua máquina.)

---

//Configurando o banco de dados//

1. Criar o banco e as tabelas

Abra o DBeaver (ou o cliente MySQL de sua preferência), conecte no seu MySQL local
e rode o script `database/schema.sql` (localizado na raiz do repositório).
Esse script cria o banco `marketplace_rural` e todas as tabelas necessárias.

a porta é 23318

Esse é o script do banco, vale ressaltar que:
TEM QUE RODAR PEDAÇO POR PEDAÇO, NÃO ADIANTA QUERER RODAR TUDO DE UMA VEZ, SELECIONA OS BLOCOS A ONDE ACABA O ";" E DA UM CTR + ENTER.

CREATE DATABASE IF NOT EXISTS palma_do_campo
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE palma_do_campo;

CREATE TABLE situacao (
sit_id INT AUTO_INCREMENT PRIMARY KEY,
sit_descricao VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO situacao (sit_descricao) VALUES
('ATIVO'),
('INATIVO'),
('SEM ESTOQUE'),
('A VENDA'),
('OCULTO');

CREATE TABLE usuario (
usu_id INT AUTO_INCREMENT PRIMARY KEY,
usu_cpfcnpj VARCHAR(20) NOT NULL UNIQUE,
usu_nome VARCHAR(150) NOT NULL,
usu_telefone VARCHAR(20) NOT NULL,
usu_email VARCHAR(150) NOT NULL UNIQUE,
usu_senha_hash VARCHAR(255) NOT NULL,
usu_endereco VARCHAR(255),
usu_cidade VARCHAR(100),
usu_regiao VARCHAR(100),
usu_nome_propriedade VARCHAR(150),
usu_tipo ENUM('PRODUTOR', 'ADMINISTRADOR') NOT NULL DEFAULT 'PRODUTOR',
situacao_id INT NOT NULL,
data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario_situacao
        FOREIGN KEY (situacao_id) REFERENCES situacao(sit_id)

);

CREATE TABLE categoria (
ctg_id INT AUTO_INCREMENT PRIMARY KEY,
ctg_descricao VARCHAR(100) NOT NULL UNIQUE,
situacao_id INT NOT NULL,
data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_categoria_situacao
        FOREIGN KEY (situacao_id) REFERENCES situacao(sit_id)

);

CREATE TABLE produto (
prod_id INT AUTO_INCREMENT PRIMARY KEY,
prod_nome VARCHAR(150) NOT NULL,
prod_descricao VARCHAR(1000),
prod_preco_estimado DECIMAL(10, 2),
prod_foto_url VARCHAR(500),
prod_data_prevista_entrega DATE,
categoria_id INT NOT NULL,
situacao_id INT NOT NULL,
data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria(ctg_id),
    CONSTRAINT fk_produto_situacao
        FOREIGN KEY (situacao_id) REFERENCES situacao(sit_id)

);

CREATE TABLE estoque (
est_id INT AUTO_INCREMENT PRIMARY KEY,
usuario_id INT NOT NULL,
produto_id INT NOT NULL,
est_qtd DECIMAL(10, 3) NOT NULL DEFAULT 0,
est_unidade ENUM('UN', 'KG', 'G', 'L', 'ML', 'DZ', 'MACO') NOT NULL DEFAULT 'UN',
situacao_id INT NOT NULL,
data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_estoque_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(usu_id),
    CONSTRAINT fk_estoque_produto
        FOREIGN KEY (produto_id) REFERENCES produto(prod_id),
    CONSTRAINT fk_estoque_situacao
        FOREIGN KEY (situacao_id) REFERENCES situacao(sit_id)

);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

2. Configurar as credenciais de conexão

O arquivo real de configuração (`db.properties`) não vem no repositório,
por conter senha. Você precisa criá-lo localmente:
Copie o arquivo `src/main/resources/db.properties.example`
Cole na mesma pasta e renomeie a cópia para `db.properties`
Abra o `db.properties` e substitua `COLOQUE_SUA_SENHA_AQUI` pela senha
do MySQL da sua própria máquina

---

Clonando e rodando o projeto

1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd marketplace-rural
```

2. Abrir no VS Code

```bash
code .
```

3. Baixar as dependências do projeto

No painel MAVEN (barra lateral do VS Code) → `marketplace-rural` → `Lifecycle` →
clique em run(seta que vai aparecer do lado).
Se aparecer `BUILD SUCCESS` no terminal, todas as dependências (driver do MySQL,
JSTL, Servlet API) foram baixadas corretamente.

4. Gerar o pacote da aplicação

No mesmo painel, clique duas vezes em package. Isso gera o arquivo
`target/marketplace-rural.war`.

5. Rodar no Tomcat

Na aba SERVERS, clique com o botão direito no servidor Tomcat configurado
e escolha a opção de implantar/rodar o projeto (deploy).

6. Acessar no navegador

```
http://localhost:8080/marketplace-rural
```

---

Estrutura do projeto

```
src/main/
├── java/br/com/palmadocampo/
│   ├── controller/    → Servlets (recebem requisições, chamam o Service)
│   ├── model/          → Entidades (Produtor, Anuncio, Categoria...)
│   ├── service/        → Regras de negócio
│   ├── dao/            → Acesso ao banco de dados (JDBC)
│   ├── filter/          → Filtros (autenticação, autorização)
│   ├── listener/       → Inicialização de recursos da aplicação
│   ├── exception/      → Exceções customizadas
│   └── util/           → Classes utilitárias
├── resources/
│   └── db.properties   → Credenciais do banco (NÃO versionado - ver acima)
└── webapp/
    ├── WEB-INF/
    │   ├── web.xml
    │   └── views/       → Páginas JSP, organizadas por área
    │       ├── auth/
    │       ├── produtor/
    │       ├── vitrine/
    │       └── admin/
    └── resources/        → CSS, JS, imagens
```

---

Problemas comuns
"Maven executable not found" no VS Code
→ Confirme que `MAVEN_HOME` está configurado e que você reiniciou o VS Code
depois de configurar a variável de ambiente.
Erro de conexão com o banco ao rodar a aplicação
→ Confirme que o MySQL está rodando na sua máquina, que o banco `marketplace_rural`
foi criado (rodou o `schema.sql`) e que o `db.properties` tem usuário/senha corretos.
Erro de porta em uso ao iniciar o Tomcat
→ Outra aplicação já está usando a porta 8080. Feche o que estiver usando essa porta,
ou configure o Tomcat para rodar em outra porta (ex: 8081).
Página em branco ou erro 404 ao acessar
→ Confirme se o WAR foi implantado com sucesso e se a URL usada bate com o
`finalName` do `pom.xml` (`marketplace-rural`).
