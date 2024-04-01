# Project: Steam-Nuage

## Running the application in development environment

### Prerequisites

In order to run, you must fulfill the following requirements:

- Java 21+
- Maven 3.8+
- Docker engine 25+
- Docker Compose 2.23+

### Setting up ENV

> This step needs to be done only once.

First of all, you need to edit the `.env` file inside the `docker` folder to set up your own variable.

### Starting the development environment

In order to start the development environment, you need to start the docker containers using the following command:

```bash
docker compose -f docker/docker-compose.yml up
```

To stop id, you need to run:

```bash
docker compose -f docker/docker-compose.yml down
```

To start the application, it depends on your choice if IDE, refer to its documentation.
