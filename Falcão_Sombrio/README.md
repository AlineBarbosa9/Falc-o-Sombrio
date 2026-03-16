**PROPOSTA DE PROJETO**
**Sistema Falcão Sombrio**

**Arquitetura de Software e Banco de Dados**

**Alunas:** Aline Barbosa   RA: 10721348  /  Ilca Mária Luz  RA: 10444474
**Disciplina:** Projeto de Software
**Professor:** Rodrigo Silva

---

## **1. Introdução**

A evolução da tecnologia de **veículos aéreos não tripulados (UAVs)**, popularmente conhecidos como **drones**, tem transformado profundamente diversas áreas estratégicas, especialmente no contexto **militar e de segurança**. Empresas especializadas em **sistemas autônomos** vêm investindo fortemente em soluções que combinam **inteligência artificial**, **sistemas embarcados** e **infraestrutura distribuída** para garantir operações **seguras, eficientes e confiáveis**.

A empresa fictícia **Securus Dynamics** é uma multinacional dedicada ao desenvolvimento de **drones bélicos autônomos** utilizados em **missões táticas**, **reconhecimento de territórios hostis** e **operações de precisão**. Seu principal produto, o **Aquila-X**, consiste em uma frota de drones equipados com **sensores avançados**, **inteligência artificial** e **sistemas de navegação autônoma**.

Entretanto, o sistema atual apresenta diversos **problemas estruturais**, incluindo:

- **latência elevada** na comunicação entre drones e servidores;
- **ausência de mecanismos robustos de failover**;
- **vulnerabilidades de segurança**;
- **dificuldades na sincronização e armazenamento de dados em tempo real**.

Para solucionar essas limitações, está sendo desenvolvido o **Sistema Falcão Sombrio**, uma nova plataforma de **controle e gerenciamento de drones** baseada em **arquitetura distribuída**, **comunicação segura** e **banco de dados altamente disponível**.

---

## **2. Contexto do Sistema**

O sistema **Falcão Sombrio** tem como objetivo permitir o **controle remoto e autônomo** da frota de drones **Aquila-X** através de uma **infraestrutura tecnológica avançada** composta por:

- **servidores distribuídos**
- **inteligência artificial embarcada**
- **sensores ambientais**
- **sistemas de comunicação criptografados**
- **banco de dados distribuído**

A plataforma deve garantir **operação contínua** mesmo em **cenários críticos**, como:

- **perda de comunicação**
- **falha de servidor**
- **eventos inesperados durante a missão**

Esses mecanismos são fundamentais para assegurar **alta disponibilidade**, **segurança operacional** e **confiabilidade nas missões realizadas pelos drones autônomos**.

---

## **3. Visão Geral da Operação de Drones Autônomos**

A operação de **drones militares autônomos** envolve múltiplos **componentes tecnológicos** trabalhando simultaneamente. Esses elementos são responsáveis por garantir que as missões sejam executadas com **eficiência, segurança e precisão**.
![imagem](imagens/imagens/imagens/Captura%20de%20ecrã%20de%202026-03-16%2015-35-53.png)

Entre os principais elementos do sistema estão:

- **Central de Controle:** interface utilizada pelos **operadores humanos**, responsável pelo monitoramento das missões e envio de comandos para os drones.

- **Servidores de Processamento:** responsáveis pela **análise de dados**, processamento de informações recebidas dos drones e **gerenciamento das missões**.

- **Drones Autônomos:** executam tarefas como **reconhecimento**, **vigilância** e **operações estratégicas** em território hostil.

- **Sensores Inteligentes:** coletam **dados do ambiente em tempo real**, como posição geográfica, obstáculos, condições ambientais e possíveis ameaças.

- **Banco de Dados Distribuído:** responsável por armazenar **telemetria**, **histórico de missões** e **logs de auditoria** do sistema.

Essa arquitetura exige mecanismos avançados de **segurança**, **sincronização de dados** e **tolerância a falhas**, garantindo que o sistema continue operando mesmo em **cenários críticos ou falhas de infraestrutura**.

---

## **4. Problema Identificado**

Durante a análise do sistema atual da **Securus Dynamics**, foram identificados diversos **problemas estruturais** que comprometem a **eficiência** e a **segurança das operações**.

---

### **4.1 Problemas de Arquitetura**

O sistema atual apresenta:

- **alta latência** na comunicação entre drones e servidores;
- **ausência de mecanismos automáticos de recuperação de falhas**;
- **dificuldade de escalabilidade** para grandes frotas de drones.

Essas limitações podem comprometer **missões críticas**.

---

### **4.2 Problemas de Segurança**

Tentativas de **invasão ao sistema anterior** demonstraram a necessidade de um modelo mais **robusto de segurança**.

Entre os riscos identificados estão:

- **acesso não autorizado ao sistema**
- **interceptação de comunicação**
- **manipulação de comandos enviados aos drones**
- **alteração de registros de missão**

Para evitar esses problemas, o sistema deverá implementar:

- **autenticação multifator**
- **criptografia de ponta a ponta**
- **assinaturas digitais**
- **logs de auditoria imutáveis**

---

### **4.3 Problemas de Gerenciamento de Dados**

A quantidade de dados gerados pelos **drones** é **extremamente alta**.

Entre os dados coletados estão:

- **posição geográfica**
- **velocidade**
- **altitude**
- **telemetria do sistema**
- **eventos críticos**

Esses dados precisam ser:

- **armazenados em tempo real**
- **replicados entre servidores**
- **protegidos contra perda**
- **disponíveis para auditorias futuras**

---

## **5. Objetivo Geral**

Propor uma nova **arquitetura de software** e um **modelo de banco de dados** capazes de suportar o funcionamento do sistema **Falcão Sombrio**, garantindo:

- **alta disponibilidade**
- **comunicação segura**
- **baixa latência**
- **tolerância a falhas**

durante operações de **drones autônomos**.

---

## **6. Objetivos Específicos**

- **analisar os requisitos funcionais e não funcionais do sistema;**
- **propor uma arquitetura de software distribuída;**
- **definir mecanismos de comunicação segura entre drones e servidores;**
- **projetar um banco de dados distribuído para armazenamento de dados em tempo real;**
- **garantir integridade e auditoria das operações realizadas;**
- **suportar concorrência e múltiplos processos no sistema embarcado.**

---

## **7. Arquitetura Conceitual do Sistema**

A arquitetura proposta para o sistema **Falcão Sombrio** será baseada em uma **estrutura distribuída** composta por diferentes camadas.

### **Camada de Controle**

Responsável pela **interface utilizada pelos operadores humanos**.  
Nessa camada será possível:

- **visualizar a posição dos drones**
- **monitorar telemetria em tempo real**
- **iniciar ou encerrar missões**
- **receber alertas do sistema**

---

### **Camada de Processamento**

Essa camada será composta por **servidores responsáveis por**:

- **processar dados de sensores**
- **gerenciar comunicação com drones**
- **executar algoritmos de inteligência artificial**
- **tomar decisões automatizadas em situações críticas**

---

### **Camada de Persistência**

Responsável pelo **armazenamento de dados do sistema**.

Serão utilizados **dois tipos de banco de dados**:

#### **Banco relacional**

Para dados estruturados como:

- **operadores**
- **missões**
- **drones cadastrados**

#### **Banco NoSQL distribuído**

Para dados de **alto volume**, como:

- **telemetria**
- **dados de sensores**
- **eventos em tempo real**

---

## **8. Escopo do Projeto**

O escopo do sistema inclui:

- **gerenciamento de frotas de drones**
- **planejamento de missões**
- **comunicação segura com drones**
- **monitoramento em tempo real**
- **registro de telemetria**
- **armazenamento de histórico de missões**
- **geração de logs de auditoria**

### **Não faz parte do escopo deste projeto**

- **desenvolvimento físico dos drones**
- **desenvolvimento de sensores**
- **desenvolvimento de hardware militar**

---

## **9. Tecnologias Possíveis**

Para a implementação do sistema proposto, algumas **tecnologias podem ser consideradas**.

### **Backend**

- **Python**
- **Java**
- **Node.js**

### **Banco de Dados**

- **PostgreSQL**
- **MongoDB**
- **Cassandra**

### **Infraestrutura**

- **Docker**
- **Kubernetes**
- **Cloud Computing**

### **Segurança**

- **TLS**
- **autenticação multifator**
- **criptografia AES-256**

---

## **10. Organização do Repositório**

O projeto será organizado no **GitHub** da seguinte forma:

Falcao-Sombrio
│
├── docs
│ └── proposta_projeto.pdf
│
├── diagramas
│ └── diagramas UML
│
├── banco
│ └── modelo de dados
│
└── README.md

### **Repositório GitHub**

O repositório do projeto pode ser acessado através do seguinte link:

🔗 **https://github.com/AlineBarbosa9/Falc-o-Sombrio**

---

## **11. Conclusão**

O sistema **Falcão Sombrio** representa uma evolução significativa na forma como **drones autônomos** podem ser gerenciados em **operações críticas**.  

A adoção de uma **arquitetura distribuída**, combinada com **mecanismos avançados de segurança** e um **banco de dados escalável**, permitirá maior **confiabilidade**, **desempenho** e **segurança** nas missões realizadas pela frota **Aquila-X**.

A proposta apresentada nesta **etapa inicial** estabelece as **bases conceituais** para o desenvolvimento das próximas fases do projeto, que incluem:

- a **modelagem do sistema por meio de diagramas UML**
- a **integração do modelo de dados com a arquitetura de software**

Essas etapas permitirão uma **visão mais detalhada da estrutura do sistema**, garantindo que a solução proposta atenda aos **requisitos funcionais e não funcionais** definidos para o projeto.

---
