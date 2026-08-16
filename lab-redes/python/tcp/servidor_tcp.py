import socket

HOST = "0.0.0.0"
PORTA = 5001

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as servidor:
    servidor.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    servidor.bind((HOST, PORTA))
    servidor.listen(1)
    print(f"[TCP] Servidor aguardando conexões na porta {PORTA}...")

    conexao, endereco = servidor.accept()
    with conexao:
        print(f"[TCP] Cliente conectado: {endereco}")
        while True:
            dados = conexao.recv(1024).decode("utf-8").strip()
            if not dados:
                break
            if dados.lower() == "hora":
                from datetime import datetime
                hora_atual = datetime.now().strftime("%H:%M:%S")
                conexao.sendall(f"Hora atual: {hora_atual}\n".encode("utf-8"))
                print(f"[TCP] Enviado: {hora_atual}")
                continue
            print(f"[TCP] Recebido: {dados}")
            if dados.lower() == "sair":
                conexao.sendall("Encerrando conexão. Até mais!\n".encode("utf-8"))
                break
            resposta = f'Monitor responde: recebi sua mensagem -> "{dados}"\n'
            conexao.sendall(resposta.encode("utf-8"))

print("[TCP] Servidor encerrado.")