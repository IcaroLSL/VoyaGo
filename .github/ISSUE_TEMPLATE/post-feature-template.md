---
name: POST Feature Template
about: Template of POST
title: POST - [SECTION] - [PAGE] - [OBJECT]
labels: enhancement
assignees: IcaroLSL, Vittas

---

## INFORMAÇÕES ENDPOINT:



### Method: POST


### URL: https://localhost:8080/v1/[PAGE]/[OBJECT]/


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

### Request Json (statusCode: 201):
```
{
    message: "[OBJECT] has been created successfully!"
}
``` 

Error Cases:
```
    401 Unauthorized - Invalid token/missing

    400 Bad Request - Invalid parameters

    500 Internal Server Error - API error
```
