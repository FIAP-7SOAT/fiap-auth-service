#!/bin/bash

NETWORK_SHARED="fiap-shared-network"

# Criar a rede compartilhada se não existir
if ! docker network ls --format '{{.Name}}' | grep -q "^${NETWORK_SHARED}\$"; then
  echo "Criando rede compartilhada ${NETWORK_SHARED}..."
  docker network create ${NETWORK_SHARED}
else
  echo "Rede compartilhada ${NETWORK_SHARED} já existe."
fi

echo "Subindo o fiap-auth-service..."
docker-compose up -d
