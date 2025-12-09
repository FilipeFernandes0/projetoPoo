IMPORTANTE!!

NÃO TRABALHEM NA MAIN, O GIT SERVE PARA JUNTARMOS O TRABALHO NO FINAL.
PENSEM NUMA ARVORE COM RAMOS A RAIZ É O MAIN E NÓS VAMOS DESENVOLVER OS RAMOS.

SE TIVEREM DÚVIDAS FALEM COMIGO.
# Guia de Git do Grupo

## Regra de Ouro ⚠️
**NUNCA fazer commit direto na main!**

## Fluxo de Trabalho

### 1. Atualizar o projeto
Sempre que começares a trabalhar, atualiza a tua main local:
git checkout main
git pull origin main


### 2. Criar a tua branch
Cria uma branch para a funcionalidade que vais fazer:
git checkout -b nome-da-funcionalidade
Para veres que estas a trabalhar na tua branch
git branch


### 3. Trabalhar e gravar
git add .
git commit -m "mensagem descritiva"


### 4. Enviar para o GitHub
git push -u origin nome-da-funcionalidade
