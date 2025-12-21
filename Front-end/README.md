# VoyaGo — Front-end (React Native / Expo)

Visão geral
-----------
Aplicação mobile desenvolvida com React Native no fluxo gerenciado do Expo. O app permite ao usuário buscar destinos, configurar preferências e gerar um roteiro de viagem criado pelo back-end.

Tecnologias
-----------
- React Native (Expo)
- Expo CLI
- React Navigation
- Axios ou fetch para chamadas à API
- Jest / React Native Testing Library para testes

Pré-requisitos
--------------
- Node.js (>= 16 recomendado)
- npm ou yarn
- Expo CLI (opcional global): `npm install -g expo-cli`

Configuração e execução (desenvolvimento)
---------------------------------------
1. Entre na pasta `Front-end/`.
2. Instale dependências:

```bash
npm install
# ou
yarn install
```

3. Configure a variável `API_URL` (ex.: no arquivo `.env` ou em `app.config.js`) apontando para o back-end — ex: `http://192.168.1.100:8080`.

4. Inicie o projeto com Expo:

```bash
npm start
# ou
yarn start
```

Fluxo da aplicação
-------------------
- Tela de login / registro
- Tela para buscar destinos e selecionar datas
- Tela de preferências (ritmo, tipos de atividades)
- Tela de resultado do roteiro (lista de dias)
- Tela de detalhes de um dia (atividades + mapa)

Exemplo de chamada para gerar roteiro
------------------------------------
Usando `fetch`:

```js
fetch(`${API_URL}/api/itineraries/generate`, {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ userId: 1, destinations: ['Paraty'], startDate: '2026-01-10', endDate: '2026-01-15', preferences: { activityType: 'nature' } })
})
.then(res => res.json())
.then(data => console.log(data))
```

Builds e publicações
---------------------
- Para builds nativas, use `eas build` (Expo Application Services) ou `expo build` (Legacy).

Testes
------
Configurar Jest e escrever testes para componentes críticos e para manipulação das respostas da API (mocks).

Boas práticas e próximos passos
------------------------------
- Implementar tratamento robusto de erros e estados de carregamento.
- Sincronização offline básica com armazenamento local (AsyncStorage) para salvar rascunhos de roteiro.
- Integração com mapas (react-native-maps ou Mapbox) para exibir atividades no mapa.

