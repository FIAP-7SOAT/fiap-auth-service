
# kubectl CLI Commands para o fiap-auth-service

## Os comandos abaixo devem ser executados na pasta do Kubernetes (`cd kubernetes`).

### 1. Aplicar nosso Secret

Crie e aplique o Secret necessário para o serviço:

```bash
kubectl apply -f secrets.yaml
```

### 2. Aplicar nosso ConfigMap

Crie e aplique o ConfigMap para configurar o banco de dados:

```bash
kubectl apply -f config-db.yaml
```

### 3. Aplicar nosso PersistentVolume

Crie e aplique o PersistentVolume para o armazenamento do banco de dados:

```bash
kubectl apply -f pv-db.yaml
```

### 4. Aplicar nosso PersistentVolumeClaim

Crie e aplique o PersistentVolumeClaim para o banco de dados:

```bash
kubectl apply -f pvc-db.yaml
```

### 5. Aplicar nosso Deployment do Banco de Dados

Crie e aplique o Deployment para o banco de dados Postgres:

```bash
kubectl apply -f deployment-db.yaml
```

### 6. Aplicar nosso Service do Banco de Dados

Crie e aplique o Service para o banco de dados. Aguarde até o Status do serviço estar como "Running":

```bash
kubectl apply -f service-db.yaml
```

### 7. Encaminhar a Porta do Serviço do Banco de Dados

Encaminhe a porta do serviço Postgres para a porta local 5432, para que você possa conectar ao banco de dados usando ferramentas como o DBeaver:

```bash
kubectl port-forward svc/postgres 5432:5432
```

### 8. Aplicar nosso Deployment do Aplicativo

Crie e aplique o Deployment para o aplicativo `fiap-auth-service`:

```bash
kubectl apply -f deployment-app.yaml
```

### 9. Aplicar nosso Service do Aplicativo

Crie e aplique o Service para o `fiap-auth-service`:

```bash
kubectl apply -f service-app.yaml
```

### 10. Aplicar o Horizontal Pod Autoscaler (HPA)

Crie e aplique o HPA para autoescalar os pods do serviço de acordo com a demanda de tráfego:

```bash
kubectl apply -f hpa-app.yaml
```

### 11. Encaminhar a Porta do Serviço fiap-auth-service

Encaminhe a porta do serviço `fiap-auth-service-app` para a porta local 8081, permitindo enviar requisições no Postman (que está configurado para usar a porta 8081):

```bash
kubectl port-forward svc/fiap-auth-service-app 8081:8081
```

### 12. Gerar um Arquivo de ConfigMap para o Postgres

Para gerar o ConfigMap para o Postgres com base em nossos SQL scripts (execute na pasta raiz do projeto). Esse comando deve ser executado novamente apenas se os scripts forem atualizados.

```bash
kubectl create configmap postgres-init-scripts --from-file=src/main/resources/sql_scripts/ -o yaml --dry-run=client > kubernetes/config-db.yaml
```

### Dicas

- Certifique-se de que o cluster Kubernetes esteja configurado corretamente.
- Todos os arquivos `.yaml` mencionados precisam estar na pasta `kubernetes`.
- O `kubectl port-forward` é útil para testes locais e comunicação com o banco de dados ou a aplicação sem precisar expor portas publicamente.
