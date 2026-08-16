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

- R: Quando o servidor foi desligado, segui conseguindo enviar mensagens normalmente, porém sem chegar ao servidor e sem o cliente saber que a mensagem não foi recebida. Comparando com o TCP, com este último receberíamos mensagem de conexão rejeitada e não conseguiríamos mais realizar o envio de mensagens, visto que este protocolo exige uma conexão estabelecida. Já com o UDP não, visto que este protocolo não possui essa exigência.

2. Cite **dois exemplos de aplicações reais** que usam UDP e explique, para cada uma, por que a confiabilidade do TCP não é essencial (ou até atrapalharia).

2. Cite dois exemplos de aplicações reais que usam UDP e explique, para cada uma, por que a confiabilidade do TCP não é essencial (ou até atrapalharia).

- R: DNS utiliza UDP porque precisa de respostas rápidas para resolver nomes em endereços IP. O TCP seria mais lento devido ao handshake. Se uma resposta não chega, o cliente simplesmente refaz a consulta. O segundo exemplo seriam as transmissões ao vivo (áudio/vídeo) porque a prioridade é a baixa latência, e retransmitir pacotes perdidos atrasaria a reprodução, então é melhor perder alguns pacotes do que travar o fluxo contínuo.

3. No código, o servidor UDP não mantém nenhum registro de "quem está conectado". Isso seria possível de implementar? O que mudaria na arquitetura da aplicação?

- R: Sim, seria possível implementar um registro de clientes, armazenando os endereços e portas dos que já enviaram mensagens. Isso permitiria manter informações de sessão, mas mudaria a arquitetura, o servidor deixaria de ser totalmente sem estado e precisaria gerenciar uma tabela de clientes ativos, adicionando complexidade semelhante ao que o TCP já faz nativamente.

## PARTE C

## PARTE D


COLOCAR Evidências de teste (prints de tela)