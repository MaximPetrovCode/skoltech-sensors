# [<- Back](../Readme.md)

# DevOps (Docker or Local)

## 1. Docker

Set in [application.properties file](../src/main/resources/application.properties) new data source path. Please, notice that dbpostgresql is name of PostgreSQL service in [docker-compose](../docker-compose.yaml) file:
    
    spring.datasource.url=jdbc:postgresql://dbpostgresql:5432/sk_db

Check your Postgres service status. Please, turn of PostgreSQL service to avoid conflict between docker and postgress services  

    systemctl status postgres
    
    systemctl stop postgres


Build a project file 

    ./gradlew build -x test


## 2. Local

### Create database, user with premissions and tables
Login as the default admin
    
    sudo -u postgres psql


Create user and database executing start.sql

    \i create_db_and_user.sql	
    \q
	
Connect to sk_db as the created user (sk_user)

    psql -h localhost -d sk_db -U sk_user

Generate tables using create_tables.sql

    \i create_tables.sql
    \q

    
### Create build of the Skoltech project
Build project
    
    ./gradlew build -x test


You can check the connection to database 

	java -jar build/libs/task-0.0.1-SNAPSHOT.jar  
	
### Check 
You can check project launch
    
    ./tests.py
    
### Clear tables 
    
    \i clear_tables.sql
    \q

