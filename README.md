# fast-food-pagamento
Sistema de pagamento de pedidos 

## Tecnologias
* Kotlin
* Database Migration
* Spring Boot Data Jpa
* Spring Boot Web
* Docker
* Docker Compose

## Banco de Dados
* MongoDB

### Comandos para iniciar a aplicação
Iniciar a aplicação, a porta da aplicação é 8080(http://localhost:8090)
```bash
docker compose up -d
```
Parar a aplicação
```bash
docker compose down
```

## Endpoints
### Criação de QRCode de Pagamento
```bash
curl --request POST \
  --url http://localhost:8090/v1/pagamentos \
  --header 'Content-Type: application/json' \
  --data '{
	"id": "861baf8a-e60e-4eb6-8eab-3953e48d3822",
	"clienteId": "32d8e2f6-ac12-46cc-8f43-ad78805d116d",
	"valor": "80",
	"destinatarioPix": {
		"nomeDestinatario": "Teste",
		"chaveDestinatario": "4455522215841",
		"descricao": "Pedido 1",
		"cidade": "São Paulo"
	}
}'
```

### Consulta de Pagamento
```bash

curl --request GET \
  --url http://localhost:8090/v1/pagamentos/861baf8a-e60e-4eb6-8eab-3953e48d3822 \
  --header 'Content-Type: application/json'
```

### Notificação de Pagamento
```bash
curl --request PUT \
  --url http://localhost:8090/v1/notificacoes/pagamentos/9249a1a8-f8c5-462e-b26e-7cca5e29c804 \
  --header 'User-Agent: Insomnia/2023.5.7'
```

