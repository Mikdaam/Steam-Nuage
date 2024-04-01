# Project: Steam-Nuage

## Prerequisites

In order to run, you must fulfill the following requirements:

- Java 21+
- Maven 3.8+
- Docker engine 25+
- Docker Compose 2.23+

## Running the application in development environment
### Setting up ENV

> This step needs to be done only once.

First of all, you need to edit the `.env` file inside the `docker` folder to set up your own variable.

### Starting the development environment

In order to start the development environment, you need to start the docker containers using the following command:

```bash
docker compose -f docker/docker-compose-DEV.yml up
```

To stop id, you need to run:

```bash
docker compose -f docker/docker-compose-DEV.yml down
```

To start the application, it depends on your choice if IDE, refer to its documentation.

### Accessing and setting up PgAdmin

For development, the application comes with a bundled PgAdmin instance.

The instance is running under `http://localhost:5050`.

The default credentials are:

- Login: `root@domain.com`
- Password: `root`

Once you are connected to PgAdmin UI you can add the local database. To do so, in the `Connection` tab, provide the
following information:

- Host name / address: `database`
- Username: Refer the value of the key `DB_USER` in the `.env` file
- Password: Refer the value of the key `DB_PASSWORD` in the `.env` file

## Deploy

> Every command describe in this chapter are performed from the root directory of the project.

### Clearing previous deployment

If you previously deployed the application, do the following:

Shut down the running instance:

```bash
docker compose -f docker/docker-compose.yml down
```

Delete the current Docker image:

```bash
docker image rm docker-steam-nuage
```

### Building the new version

In order to build the new version of the application, compile it using `maven`:

```bash
mvn -B package --file pom.xml
```

### Run the new version

Finally, to run the new version, execute the docker compose file:

```bash
docker compose -f docker/docker-compose.yml up -d
```

Finally, access the API documentation at : http://localhost:5050/swagger-ui/index.html

## Troubleshooting

**A docker command failed to execute**

Depending on the configuration of the system, you might need to run the docker's command with `sudo`.

**The application crashed at startup**

Make sure that every needed environments variables are set in the
`.env` file.

Alternatively, if the latest version introduced changes to the local database, you might need to erase the docker
volume.

```bash
docker volume rm docker_steam_nuage_data
```

> This operation will **delete** all the data.
