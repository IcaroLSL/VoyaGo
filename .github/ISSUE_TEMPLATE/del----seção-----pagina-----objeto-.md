---
name: DEL - [SEÇÃO] - [PAGINA] - [OBJETO]
about: 'Template de DELETE '
title: DEL - [SEÇÃO] - [PAGINA] - [OBJETO]
labels: ''
assignees: ''

---

ADICIONAR A IMAGEM DA PÁGINA DO APLICATIVO ANTIGO

--------------------------------------------------------------------------------------------------------------------

INFORMAÇÕES ENDPOINT:

Método: DEL

URL: https://us-central1-earnest-cosmos-175020.cloudfunctions.net/{BACKEND-DECIDE}/v1/[OBJETO]/?idObjeto={idOBJETO}

JWT Header:
{
     "Authorization": "Bearer ${token}"
}

✅ Request Json (statusCode: 200):
```
{
    message: "O [OBJETO] foi deletado com sucesso!"
}

``` 

Casos de Erro:

    401 Unauthorized - Token inválido/missing

    400 Bad Request - Parâmetros inválidos
