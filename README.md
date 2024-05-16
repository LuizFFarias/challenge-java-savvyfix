
# SavvyFix - Precificação dinâmica

## Lista dos integrantes:
#### 1. Douglas Magalhães de Araujo - 552008
<p>Responsável principal pelo desenvolvimento front e pesquisa.</p>

#### 2. Gustavo Argüello Bertacci - 551304
<p>Responsável pelo desenvolvimento back e pelo suporte.</p>

#### 3. Luiz Fillipe Farias - 99519
<p>Responsável principal pelo desenvolvimento back e pesquisa.</p>

#### 4. Rafaella Monique do Carmo Bastos - 552425
<p>Responsável pela documentação, banco de dados e desenvolvimento back/front.</p><br>

## Instrução de como rodar a aplicação
<p>1. Certificar as configurações da IDE.</p>
<p>2. Iniciar o projeto.</p>
<p>3. Verificar as respostas dos endpoints pelo endereço do localhost.</p><br>

## Imagens dos diagramas
### Diagrama Entidade-Relacionamento
![](documentacao/diagramas/WhatsApp%20Image%202024-04-11%20at%2019.10.19.jpeg)

### Modelo Entidade-Relacionamento
![](documentacao/diagramas/WhatsApp%20Image%202024-04-11%20at%2019.10.30.jpeg)

### Diagrama de Classes
![DiagramaClasses](https://github.com/LuizFFarias/challenge-java-savvyfix/assets/85761347/f579f462-41aa-4978-8108-e827c00831b2)

### Arquitetura da solução
![Arquitetura_Software_SavvyFix](https://github.com/LuizFFarias/challenge-java-savvyfix/assets/85761347/c52c0a08-ca81-4da3-9e36-8e4dcd84dc33)


## Link do pitch
https://youtu.be/ltPanBm_5ks <br>

## Endereços dos EndPoints
### Endereço
<p>localhost/enderecos - GET</p>
<p>localhost/enderecos?cep={cep} - GET</p> 
<p>localhost/enderecos?rua={rua} - GET</p> 
<p>localhost/enderecos?bairro={bairro} - GET</p> 
<p>localhost/enderecos/{id} - GET</p> 
<p>localhost/enderecos - POST</p> 

### Produtos
<p>localhost/produtos - GET</p> 
<p>localhost/produtos?nome={nome} - GET</p> 
<p>localhost/produtos?marca={marca} - GET</p> 
<p>localhost/produtos?preco={preco} - GET</p> 
<p>localhost/produtos/{id}  - GET</p> 
<p>localhost/produtos - POST</p> 

### Clientes
<p>localhost/clientes - GET</p>
<p>localhost/clientes?cpf={cpf} - GET</p> 
<p>localhost/clientes?nome={nome} - GET</p> 
<p>localhost/clientes?endereco.cep={cep} - GET</p>
<p>localhost/clientes?endereco.bairro={bairro} - GET</p> 
<p>localhost/clientes/{id} - GET </p> 
<p>localhost/clientes  - POST</p>

### Atividades
<p>localhost/atividades  - GET</p>
<p>localhost/atividades?localizacao{localizacao}  - GET</p>
<p>localhost/atividades?horario={horario}  - GET</p>
<p>localhost/atividades?demanda={demanda}  - GET</p>
<p>localhost/atividades?qntdProcura={qntdProcura}  - GET</p>
<p>localhost/atividades?clima={clima}  - GET</p>
<p>localhost/atividades?precoVariado={precoVariado}  - GET</p>
<p>localhost/atividades?cliente.cpf={cpf}  - GET</p>
<p>localhost/atividades/{id} - GET</p> 
<p>localhost/atividades - POST</p>

### Compra
<p>localhost/compras  - GET</p>
<p>localhost/compras?nomeProd={nomeProduto}  - GET</p>
<p>localhost/compras?valorCompra={valorCompra}  - GET</p>
<p>localhost/compras?especificacoes={especificacoes}  - GET</p>
<p>localhost/compras?marca={marca}  - GET</p>
<p>localhost/compras?cpf.clie={cpf}  - GET</p>
<p>localhost/compras?horario={horario}  - GET</p>
<p>localhost/compras/{id} GET</p> 
<p>localhost/compras  - POST</p> 

