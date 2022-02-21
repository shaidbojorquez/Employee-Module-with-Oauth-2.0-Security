# Employee-Module-with-Oauth-2.0-Security
Microservice to manage employees and their benefits.

This is an implementation using Oauth 2.0 as a client server for security. The service has the same funcionality of view, create and edit employees and its benefits with scopes defined as roles ROLE_USER, ROLE_MANAGER, ROLE_ADMIN.

How to run it
#### 1. Install Docker and Docker Desktop
It is neccesary to have docker installed in your computer in order to run the container

#### 2. Run setup commands
It is neccesary to run the following commands in order before running the application

###### netsh int ip sh int
###### netsh int ip add addr 1 10.5.0.4/32 st=ac sk=tr
###### netsh int ip add addr 1 10.5.0.5/32 st=ac sk=tr
###### netsh int ip add addr 1 10.5.0.6/32 st=ac sk=tr
###### netsh int ip add addr 1 10.5.0.7/32 st=ac sk=tr

#### 3. Open a command prompt in the main root
In the main root, run the command "docker-compose up" in order to create the container, wait until finished

#### 4. Connect to database server
Once the container is up, the next step is to go to the Pgadmin container in http://localhost:5050 The password for pgadmin is "admin". Once loaded, connect to server with the following parameters

###### HOST: app-db
###### PORT: 5432
###### User: itk
###### Password: itk2022

#### 5. Load the database
Once connected, create two databases called "authorization-db" and "EmployeesModule" and run the query tool in each one using the "authorization-db.sql" and "EmployeesModule.sql" SQL files.

#### 6. Log in
Now, login in the redirect login form found in http://localhost:8080. The following users and passwords are present:

Username: julio.vargas@theksquaregroup.com, Password: user, ROLE_USER
Username: guillermo.ceme@theksquaregroup.com, Password: manager, ROLE_MANAGER
Username: carlos.reyes@theksquaregroup.com, Password: admin, ROLE_ADMIN

# Endpoints

##### Role: ADMIN
###### Employees
###### GET: /api/employees
###### GET: /api/employees/basic-info
###### GET: /api/employees/{id}
###### GET: /api/employees/basic-info/{id}
###### POST: /api/employees
###### PUT: /api/employees/{id}
###### PATCH: /api/employees/partial-update/{id}
###### PATCH: /api/employees/{id} (Disable an employee)
###### PATCH: /api/employees/add-benefit
###### PATCH: /api/employees/remove-benefit
###### Benefits
###### GET: /api/benefits
###### GET: /api/benefits/{id}
###### POST: /api/benefits
###### PUT: /api/benefits/{id}
###### PATCH: /api/benefits/partial-update/{id}
###### PATCH: /api/benefits/{id} (Disable a benefit)

##### Role: ADMIN, MANAGER, USER
###### GET: /api/employees/user-info/{username}
###### GET: /api/benefits/user-benefits/{username}
