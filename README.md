# Prerequisites 
- Docker - version 19 or higher
- docker-compose -  version 3 or higher
-  NodeJS -  version 12 or higher
- yarn - version 1.7 or higher


# How to run

1. Switch to the `service-portal` directory
2. Execute `docker-compose up` command
3. This will spin up the db, and service-poller, service-portal services
   While the service layer is running switch to another cli tab
4. Switch to `web-app` directory
5. Execute `yarn && yarn start` command
   This will start the web app in port 3000
6. Go to http://localhost:3000 in a web browser

# Features & Functionalities

## service-portal service
Implements a query, mutation based api and exposes it to the public. Enables query, create, and make modifications to the user created service entries.

- `createService` mutation to create  a new service entry.
- `updateService` mutation to update  an existing service entry.
- `deleteService` mutation to delete an existing service entry.
- `updateService` mutation to update  an existing service entry.
- `service` query to get an existing service entry.
- `services` query to get all service entries. Not paginated.


## service-poller service
Performs periodic updating the status of user entered services. Stores the results in denormalized service, service_healthcheck_log tables in the database. Supports only making http  requests  to external http based services.

- Making external HTTP service requests every 30s for all user entered services.
- Persist the result from each HTTP request.


## web-app client
Consumes the publicly exposed service-portal service to provide a user friendly interface to the system for end users.

- Lists all the added service entries by all the users in `/` route
- View the detailed information about a selected service in `/view/:id` route
- Adding a new service entry with `/create` route
- Removing an existing service entry with `/delete/:id` route
- Updating existing service entries is not supported from  the web client. Mutation query exists in  the `service-portal`