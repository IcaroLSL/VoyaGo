---
name: GET (by Id) Feature Template
about: Template of GET (by Id)
title: GET (by Id) - [SECTION] - [PAGE] - [OBJECT]
labels: enhancement
assignees: IcaroLSL, Vittas

---

## INFORMAÇÕES ENDPOINT:



### Method: GET


### URL: https://localhost:8080/v1/[OBJECT]?idOBJECT={idOBJECT}


### Header:
```
{
    "Authorization": "Bearer ${token}"
}
```


### Request Json (statusCode: 200):
```
{
    SET RETURN
}
``` 

Error Cases:
```
    401 Unauthorized - Invalid token/missing

    400 Bad Request - Invalid parameters

    500 Internal Server Error - API error
```
