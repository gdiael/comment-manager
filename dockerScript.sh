#!/bin/bash

while true; do
  echo ""
  echo "Escolha uma opção:"
  echo "1 - Subir o Docker Compose (gateway + service1)"
  echo "2 - Parar somente o container 'gateway'"
  echo "3 - Parar somente o container 'service1'"
  echo "4 - Retomar (start) o container 'gateway'"
  echo "5 - Retomar (start) o container 'service1'"
  echo "8 - Parar tudo (docker compose down)"
  echo "9 - Escutar logs do docker"
  echo "0 - Sair"
  echo ""
  read -p "Digite o número da ação: " numero

  case $numero in
    1)
      echo "Subindo docker compose..."
      sudo docker compose up -d --build
      ;;
    2)
      echo "Parando container 'gateway'..."
      sudo docker stop comment-manager-gateway-1
      ;;
    3)
      echo "Parando container 'service1'..."
      sudo docker stop service1
      ;;
    4)
      echo "Retomando container 'gateway'..."
      sudo docker start comment-manager-gateway-1
      ;;
    5)
      echo "Retomando container 'service1'..."
      sudo docker start service1
      ;;
    8)
      echo "Parando e removendo todos os containers do docker compose..."
      sudo docker compose down
      break
      ;;
    9)
      echo "Ativando logs do docker compose..."
      sudo docker compose logs -f
    #   break
      ;;
    0)
      echo "Saindo do script."
      break
      ;;
    *)
      echo "Opção inválida, tente novamente."
      ;;
  esac
done
