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

1. Qual é a diferença fundamental entre enviar a mesma mensagem para 3 clientes usando **unicast repetido 3 vezes** e enviar **uma única vez** via multicast? Pense em termos de tráfego de rede.

- R: A diferença entre eles é que no unicast repetido o servidor envia 3 cópias idênticas da mensagem, uma para cada cliente. Isso gera tráfego proporcional ao número de destinatários (mais pacotes circulando na rede). Já no multicast, o servidor envia apenas uma vez para o grupo multicast. A rede se encarrega de entregar a mensagem a todos os clientes inscritos. Assim o tráfego é muito menor, pois há apenas um envio, independentemente do número de clientes. Dessa maneira, o multicast é mais eficiente e escalável, especialmente quando há muitos receptores.

2. O que é o **TTL** (time-to-live) configurado no socket multicast e por que ele é importante para controlar o alcance dos pacotes na rede?

- R: O TTL é um valor que controla até onde um pacote multicast pode se propagar pela rede. Ele funciona como um limite para a quantidade de roteadores que o pacote pode atravessar. Um TTL baixo restringe o pacote a uma área menor, enquanto um TTL maior permite que ele alcance redes mais distantes. Isso é importante para evitar que os pacotes multicast sejam enviados para redes onde não são necessários, reduzindo o tráfego e controlando o alcance da comunicação.

3. Se um dos clientes ficar temporariamente offline e voltar depois, ele recebe os avisos que perdeu? Por quê? Relacione com a arquitetura de comunicação em grupo.

- R: Não. Se um dos clientes ficar temporariamente offline, ele não vai receber os avisos enviados durante esse período. Isso acontece porque o multicast é uma comunicação em grupo baseada em UDP, em que as mensagens são enviadas aos membros que estão inscritos no grupo e disponíveis naquele momento. Enquanto o cliente estiver offline, ele não recebe os pacotes enviados e quando voltar e se inscrever novamente no grupo receberá apenas as novas mensagens. As mensagens perdidas não são armazenadas nem retransmitidas automaticamente, pois o multicast não mantém um histórico das mensagens para clientes que estavam offline.

## PARTE D

1. O WebSocket começa com uma requisição HTTP contendo o cabeçalho `Upgrade: websocket`. O que exatamente "muda" na conexão depois que esse handshake é concluído?

- R: Quando o servidor aceita o cabeçalho Upgrade: websocket, a conexão deixa de ser tratada como HTTP tradicional e passa a ser um canal contínuo e bidirecional. Isso significa que não existe mais o modelo de requisição e resposta do HTTP, mas sim uma troca de mensagens em tempo real usando frames próprios do WebSocket. O TCP continua sendo a base, mas agora a comunicação é interativa e persistente, permitindo que cliente e servidor enviem dados a qualquer momento.

2. Compare o mural via WebSocket (Parte D) com o aviso via Multicast (Parte C). Ambos entregam uma mensagem a vários destinatários — qual a diferença na forma como cada um descobre e alcança os destinatários?

- R: No mural via WebSocket, cada cliente abre uma conexão direta com o servidor, e o servidor mantém a lista de conexões ativas para enviar as mensagens a todos. Já no multicast, o servidor envia uma única mensagem para um endereço de grupo específico e a própria rede replica essa mensagem para todos os dispositivos que se inscreveram nesse grupo. Assim no WebSocket o servidor controla quem recebe, enquanto no multicast a entrega é feita pela infraestrutura de rede para todos que se juntaram ao grupo.

3. Por que o WebSocket é mais adequado do que TCP "cru" (como o da Parte A) para este cenário de mural em tempo real, mesmo os dois sendo, no fundo, conexões TCP contínuas?

- R: Embora ambos usem TCP como base, o WebSocket já oferece um protocolo padronizado para mensagens em tempo real, com suporte nativo em navegadores e bibliotecas. Ele já define como abrir, manter e encerrar conexões, além de incluir recursos de controle como ping/pong para verificar se a conexão continua ativa. Se fosse usado apenas TCP puro, cada aplicação teria que criar seu próprio protocolo de mensagens. O WebSocket simplifica essa troca contínua de dados, sendo uma a escolha ideal para um mural de avisos em tempo real.