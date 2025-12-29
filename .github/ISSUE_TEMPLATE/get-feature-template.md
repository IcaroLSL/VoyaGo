---
name: GET Feature Template
about: Template of GET
title: GET - [SECTION] - [PAGE] - [OBJECT]
labels: enhancement
assignees: IcaroLSL, Vittas

---

## INFORMAÇÕES ENDPOINT:



### Method: GET


### URL: https://localhost:8080/v1/[OBJECT]?page=${variavel}&limit=${variavel}&filters=${json_com_campo}


### The filters field will receive a JSON object containing the filtering fields:
```
{
    SET FILTERS
}
```
>[!NOTE]
> It should allow filtering of the OBJECT by:
> - FIELD -> OBJECT
>
> (If the filter field is empty, return the complete list)


### Header:
```
{
    "Authorization": "Bearer ${token}"
}
```


### Request Json (statusCode: 200):
```
{
    "total": INT,
    "page": INT,
    "limit": INT,
    "data": [   
                    {
                        SET RETURN
                    }
                 ]
}
``` 

Error Cases:
```
    401 Unauthorized - Invalid token/missing

    400 Bad Request - Invalid parameters

    500 Internal Server Error - API error
```
