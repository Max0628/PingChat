# API Spec

## SignUp

### Native Only

#### request
  
  ```json
  {
  "name":"test",
  "email":"test@test.com",
  "password":"test"
  }
  ```

#### response
  
  ```json
  {
  "data": {
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6joiYXJ0aHVIjoxNjEzNTY3MzA0fQ.6EPCOfBGynidAfpVqlvbHGWHCJ5LZLtKvPaQ",
    "access_expired": 3600,
    "user": {
      "id": 11245642,
      "provider": "facebook",
      "name": "Pei",
      "email": "pei@appworks.tw",
      "picture": "https://schoolvoyage.ga/images/123498.png"
    }
  }
}
  ```
  
## Login 

### Native 

#### Native Request 

```json
{
  "provider":"native",
  "email":"test@test.com",
  "password":"test"
}
```

#### Native Response 

```json
{
  "data": {
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6joiYXJ0aHVIjoxNjEzNTY3MzA0fQ.6EPCOfBGynidAfpVqlvbHGWHCJ5LZLtKvPaQ",
    "access_expired": 3600,
    "user": {
      "id": 11245642,
      "provider": "native",
      "name": "Pei",
      "email": "pei@appworks.tw",
      "picture": "https://schoolvoyage.ga/images/123498.png"
    }
  }
}
```

### OAuth 

#### OAuth Request 


```json
{
  "provider":"facebook",
  "access_token": "EAACEdEose0cBAHc6hv9kK8bMNs4XTrT0kVC1RgDZCVBptXW12AI"
}
```

#### OAuth Response 

```json
{
  "data": {
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6joiYXJ0aHVIjoxNjEzNTY3MzA0fQ.6EPCOfBGynidAfpVqlvbHGWHCJ5LZLtKvPaQ",
    "access_expired": 3600,
    "user": {
      "id": 11245642,
      "provider": "facebook",
      "name": "Pei",
      "email": "pei@appworks.tw",
      "picture": "https://schoolvoyage.ga/images/123498.png"
    }
  }
}
```

## Error Response

```json
{
  "code": "400",
  "message" "your request format is wrong"
}
```

```json
{
  "code": "500",
  "message" "server error"
}
```