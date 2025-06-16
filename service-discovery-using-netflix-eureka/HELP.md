# POST
## /product-composite
```json
curl -X 'POST' \
  'http://localhost:8080/product-composite' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": 111,
  "name": "Wireless Mouse",
  "weight": 200,
  "recommendations": [
    {
      "recommendationId": 1,
      "author": "Alice",
      "rate": 5,
      "content": "Great mouse, very responsive!"
    },
    {
      "recommendationId": 2,
      "author": "Bob",
      "rate": 4,
      "content": "Good value for the price."
    }
  ],
  "reviews": [
    {
      "reviewId": 1,
      "author": "Charlie",
      "subject": "Awesome product",
      "content": "Used it for weeks with no issues."
    },
    {
      "reviewId": 2,
      "author": "Dana",
      "subject": "Battery life",
      "content": "Lasts long, but a bit heavy."
    }
  ]}
'
```
Response code: 200


# GET 
## /product-composite/111
```json
curl -X 'GET' \
  'http://localhost:8080/product-composite/111' \
  -H 'accept: application/json'
```

Response body
```json
{
  "productId": 111,
  "name": "Wireless Mouse",
  "weight": 200,
  "recommendations": [
    {
      "recommendationId": 1,
      "author": "Alice",
      "rate": 5,
      "content": "Great mouse, very responsive!"
    },
    {
      "recommendationId": 2,
      "author": "Bob",
      "rate": 4,
      "content": "Good value for the price."
    }
  ],
  "reviews": [
    {
      "reviewId": 1,
      "author": "Charlie",
      "subject": "Awesome product",
      "content": "Used it for weeks with no issues."
    },
    {
      "reviewId": 2,
      "author": "Dana",
      "subject": "Battery life",
      "content": "Lasts long, but a bit heavy."
    }
  ],
  "serviceAddresses": {
    "cmp": "1e1f054dc104/172.23.0.6:8080",
    "pro": "ca8d208d053f/172.23.0.7:8080",
    "rev": "1cd031ef772d/172.23.0.8:8080",
    "rec": "d7fd99788ba0/172.23.0.5:8080"
  }
}
```
# DELETE 
## /product-composite/111
```json
curl -X 'DELETE' \
  'http://localhost:8080/product-composite/111' \
  -H 'accept: */*'
```
Response code: 200

huh