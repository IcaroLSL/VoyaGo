---
name: DELETE Feature Template
about: Template of DELETE
title: DELETE - [SECTION] - [PAGE] - [OBJECT]
labels: enhancement
assignees: IcaroLSL, Vittas

---

## INFORMAÇÕES ENDPOINT:



### Method: DELETE


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
    message: "[OBJECT] has been deleted successfully!"
}
``` 

Error Cases:
```
    401 Unauthorized - Invalid token/missing

    400 Bad Request - Invalid parameters

    500 Internal Server Error - API error
```
