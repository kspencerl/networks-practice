# Respostas *Central de Comunicação da Turma*

## PARTE A 
1. O que acontece se você iniciar o **cliente** antes do **servidor**? Por que isso ocorre, considerando o funcionamento do TCP?

- R: O cliente tenta abrir uma conexão com o host e porta especificados, mas como não existe nenhum servidor escutando, o sistema retorna `Connection refused`. Isso ocorre porque o TCP exige que o servidor esteja ativo para responder ao pedido de conexão.

2. O TCP garante que as mensagens cheguem **na ordem** em que foram enviadas. Qual mecanismo do protocolo é responsável por isso?

- R: O TCP utiliza números de sequência, que identificam a posição de cada byte dentro do fluxo de dados enviado, e ACKs, que enviam uma mensagem de confirmação em relação ao byte recebido e enviam o numero do próximo segmento esperado. O receptor usa esses números para reorganizar os pacotes e confirmar a entrega. Assim, mesmo que os pacotes cheguem fora de ordem, o protocolo garante que sejam entregues corretamente à aplicação.

3. Na sua implementação, o que aconteceria se dois clientes tentassem se conectar ao mesmo tempo? O código atual suporta isso? Justifique observando o código do servidor.

- R: Apenas o primeiro cliente conectado iria conseguir comunicação com o servidor, visto que o código chama `servidor.accept()` apenas uma vez e entra em um loop exclusivo para esse cliente. Para suportar múltiplos clientes, seria necessário implementar concorrência, por exemplo com threads, permitindo que o servidor aceite novas conexões enquanto continua atendendo as existentes.

## PARTE B

1. No passo 2 da tarefa, o que aconteceu quando você enviou uma mensagem com o servidor desligado? Compare com o que aconteceria em TCP e explique a diferença observada, relacionando com o conceito de "sem conexão".


2. Cite **dois exemplos de aplicações reais** que usam UDP e explique, para cada uma, por que a confiabilidade do TCP não é essencial (ou até atrapalharia).


3. No código, o servidor UDP não mantém nenhum registro de "quem está conectado". Isso seria possível de implementar? O que mudaria na arquitetura da aplicação?





## PARTE C

## PARTE D


COLOCAR Evidências de teste (prints de tela)