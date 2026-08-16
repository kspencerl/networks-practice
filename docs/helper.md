OFFSET = os dois últimos dígitos da sua matrícula/RA (ex.: matrícula 1234567 -> OFFSET = 67)
Matrícula: 8171094
`OFFSET` pessoal: 94


Use esse `OFFSET` somado à porta-base de **cada parte**, em todos os arquivos (Java e Python), substituindo o valor fixo sugerido no roteiro:

| Parte                  | Porta-base | Sua porta        |
|------------------------|------------|------------------|
| A — TCP                | 5000       | `5000 + OFFSET`  |
| B — UDP                | 5001       | `5001 + OFFSET`  |
| C — Multicast          | 4446       | `4446 + OFFSET`  |
| D — WebSocket (Java)   | 8887       | `8887 + OFFSET`  |
| D — WebSocket (Python) | 8888       | `8888 + OFFSET`  |
