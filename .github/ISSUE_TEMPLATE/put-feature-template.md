---
name: PUT Feature Template
about: Template of PUT
title: PUT - [SECTION] - [PAGE] - [OBJECT]
labels: enhancement
assignees: IcaroLSL, Vittas

---

## INFORMAÇÕES ENDPOINT:



### Method: PUT


### URL: https://localhost:8080/v1/[OBJECT]?idOBJECT={idOBJECT}


### Header:
```
{
    "Authorization": "Bearer ${token}"
}
```


### Body  Json:
```
{
    SET OF FIELDS FOR BODY
}
```


### Request Json (statusCode: 200):
```
{
    message: "[OBJECT] has been update successfully!"
}
``` 

Error Cases:
```
    401 Unauthorized - Invalid token/missing

    400 Bad Request - Invalid parameters

    500 Internal Server Error - API error
```
