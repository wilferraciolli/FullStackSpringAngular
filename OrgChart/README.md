# Org Chart Spring Andgular project

This project uses docker to deploy

## Running the app
The command below will
* Download any tools needed to build/run the app
* Build all the services needed to run the app

```bash
    docker-compose up -d --build
```

## Services

### Org Chart UI
Running Angular on http://localhost:4200

### Org Chart API
Running Spring Boot on http://localhost:5100

###  DB and EB interface
Running MySQL port 3306 and MyPHP Admin on http://localhost:8081/
