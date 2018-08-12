# The Publisher

* Ferramenta que facilita a criação de blogs.
* No mesmo Publisher podemos ter mais de um blog, basta configurar os hosts no arquivo "WEB-INF/config-files/hosts.properties".
* A ferramenta possui um sistema de cache das páginas, consultas no banco de dados e outros elementos utilizando o freamwork EhCache.
* Alta performance nas consultas no banco de dados por conta do POOL de conexões que o C3P0 cria.
* Também possui uma alta performance na busca de contéudos, isso porque todo conteúdo do tipo texto é indexado em arquivos pelo Hibernate-Search que utiliza o Lucene como motor de busca/indexação.


### FreameWorks

* [Struts 2](https://struts.apache.org/) (2.3.24.1)
* [Hibernate](http://hibernate.org/) (5.0.2.Final)
* [Hibernate EhCache](http://hibernate.org/) (5.0.2.Final)
* [Hibernate Search](http://hibernate.org/) (5.0.2.Final)
* [Spring ORM](https://spring.io/) (4.2.1.RELEASE)
* [Spring Web](https://spring.io/) (4.2.1.RELEASE)
* [Apache Tiles](https://tiles.apache.org/) (2.3.24.1)
* [EhCache](http://www.ehcache.org/) (2.6.11)
* [JavaMelody](https://github.com/javamelody/javamelody/wiki) (1.57.0)
* [Groovy-All](http://groovy-lang.org/) (2.4.5)
* [C3P0](https://www.mchange.com/projects/c3p0/) (0.9.5.1)
* [MySql](https://dev.mysql.com/downloads/connector/j/) (5.1.36)
* [slf4j-log4j12](https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12) (1.7.12)

#### Pré-requisitos para ter a aplicação instalada

* [Java](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) 7 ou superior.
* [Maven](https://maven.apache.org/download.cgi) 3 ou superior.
* [MySql](https://dev.mysql.com/downloads/mysql/) 5 ou superior.
* [ImageMagick](https://www.imagemagick.org/script/download.php) 4 ou superior.

#### Compilando o projeto
```shell
mvn clean install
```

#### Configurando usuário e senha no MySql
```shell
mysql -u root -p
```
```shell
mysql> create database thepublisher;
```
```shell
mysql> use thepublisher;
```
```shell
mysql> grant all privileges on thepublisher.* to thepublisher@localhost identified by 'thepublisher';
```

#### Configurando o ImageMagick
Basta criar um link simbólico na pasta "bin" na home do projeto. A pasta será criada na home da aplicação, exemplo:
```shell
cd /home/SEU_USUARIO/the-publisher-files/bin
ln -s /usr/bin/convert .
```

#### Rodando aplicação com o Maven Jetty Plugin
```shell
mvn clean jetty:run-exploded
```

#### Principais arquivos de configuração
* WEB-INF/config-files/default-folders.properties: Responsável pelas configurações das pastas que aplicação usa para armazenar arquivos estáticos.
* WEB-INF/config-files/hosts.properties: Responsável por fazer o mapeamento das homes dos blogs.
* WEB-INF/config-files/realm.properties: Configuração de realm de segurança do Jetty para o JavaMelody.
* WEB-INF/jetty/jetty.xml: Configurações para o Jetty.
* WEB-INF/jetty/jetty-gzip.xml: Configuração para o Jetty usar o GZIP nas resposta.
* WEB-INF/spring/config/dataSource.dev.properties: Configurações do pool de conexões e usuário/senha do MySql para ambiente local/dev.
* WEB-INF/spring/config/dataSource.hlg.properties: Configurações do pool de conexões e usuário/senha do MySql para ambiente de hlg.
* WEB-INF/spring/config/dataSource.prd.properties: Configurações do pool de conexões e usuário/senha do MySql para ambiente de prd.