# Relay API Service

## How tos

## Build & Test

This project uses gradle and uses the default tasks to compile and run unit tests.

```bash
./gradlew clean assemble check
```

### Build and run locally on Docker
1. Build the docker container
```bash
./gradlew clean assemble check docker
```

2. Run Docker Compose to start up the application and MySQL service.
```bash
docker-compose up --build
```

3. Run Docker Compose to stop the application and MySQL service
```bash
docker-compose down --rmi all
```

## Explore REST APIS

The app defines following CRUD APIs.

BaseURL: <http://localhost:8080>

Test the endpoints using Postman or any other REST Client.

### Invoices

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /relay/api/invoices | Create new invoices | [JSON](#invoicecreate) | |

##### <a id="invoicecreate">Create Invoices -> /relay/api/invoices</a>
```json
[
  {
    "invoiceNumber": "INVC-01",
    "value": "100.00"
  },
  {
    "invoiceNumber": "INVC-02",
    "value": "110.00"
  },
  {
    "invoiceNumber": "INVC-03",
    "value": "120.00"
  }
]
```

### CreditNotes

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /relay/api/creditnotes | Create new credit notes | [JSON](#creditnotescreate) | |

##### <a id="creditnotescreate">Create CreditNotes -> /relay/api/creditnotes</a>
```json
[
  {
    "creditNumber": "CRN-01",
    "value": "50.00"
  },
  {
    "creditNumber": "CRN-02",
    "value": "55.00"
  },
  {
    "creditNumber": "CRN-03",
    "value": "60.00"
  }
]
```

### GetAggregatedView

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /relay/api/getAggregatedView | Get Aggregated List of invoices and credit notes | |

## For more tasks run
```bash
./gradlew tasks
```



