#!/bin/bash

while true; do
  echo ""
  echo "Escolha uma opção:"
  echo "1 - Subir o Docker Compose Completo"
  echo "2 - Parar container 'gateway'"
  echo "3 - Parar container 'service1'"
  echo "4 - Parar container 'service2'"
  echo "5 - Retomar container 'gateway'"
  echo "6 - Retomar container 'service1'"
  echo "7 - Retomar container 'service2'"
  echo "9 - Escutar logs do docker"
  echo "0 - Parar tudo (docker compose down)"
  echo "s - Sair"
  echo ""
  read -p "Digite o número da ação: " numero

  case $numero in
    1)
      echo "Subindo docker compose..."
      sudo docker compose up -d --build
      sudo docker compose logs -f
      ;;
    2)
      echo "Parando container 'gateway'..."
      sudo docker stop comment-manager-gateway-1
      ;;
    3)
      echo "Parando container 'service1'..."
      sudo docker stop comment-manager-service1-1
      ;;
    4)
      echo "Parando container 'service2'..."
      sudo docker stop comment-manager-service2-1
      ;;
    5)
      echo "Retomando container 'gateway'..."
      sudo docker start comment-manager-gateway-1
      ;;
    6)
      echo "Retomando container 'service1'..."
      sudo docker start comment-manager-service1-1
      ;;
    7)
      echo "Retomando container 'service2'..."
      sudo docker start comment-manager-service2-1
      ;;
    0)
      echo "Parando e removendo todos os containers do docker compose..."
      sudo docker compose down
      break
      ;;
    9)
      echo "Ativando logs do docker compose..."
      sudo docker compose logs -f
      # break
      ;;
    s)
      echo "Saindo do script."
      break
      ;;
    *)
      echo "Opção inválida, tente novamente."
      ;;
  esac
done
