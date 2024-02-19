# Team11 AD Project

## Project Structure
- Java Backend is under root of repository.
- React Frontend is under `./react/frontend/`
- Android is under `./android`
- ML model and server is under `/machinelearning`

## Local env setup
- Requirements:
  - node.js version 18.xx
  - Eclipse or SprintToolSuite
  - MySQL server
    - default password for DB is 'password123'. Please make sure your DB password is correctly set in `application.properties`.
- Steps:
  - To start up backend, import repository as a maven project. In your IDE, run project as a spring boot application.
    - There is one secret env variable requried for running the backend service - `AZURE_STORAGE_CONNECTION_STRING`. Please contact our teammates if you want to use it as we cannot commit it to this public repo.
  - To start up frontend:
    - `cd ./react/frontend/`
    - `npm install`
    - `npm run start`

