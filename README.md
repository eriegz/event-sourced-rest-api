# **Event-Sourced REST API**

Simple REST API that uses an event-sourced architecture via the Axon framework.

### **Prerequisites:**

This application talks to redis and Axon servers. To spin up instances of these on your local machine, follow these steps:

- First, install [Docker Desktop](https://www.docker.com/products/docker-desktop)
    - If you're on Windows, you may then need to [manually update WSL to version 2](https://docs.microsoft.com/en-us/windows/wsl/install-manual#step-4---download-the-linux-kernel-update-package)
- Then, pull and run both the [official redis Docker image](https://hub.docker.com/_/redis) and the [official axon Docker image](https://hub.docker.com/r/axoniq/axonserver) by opening a terminal and running:
    - `docker pull redis`
    - `docker pull axoniq/axonserver`
- Finally, run the images via the following commands:
    - `docker run --name eriegz-redis -p 6379:6379 -d redis`
    - `docker run -d --platform linux/amd64 --name eriegz-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver`

### **How to run:**

> Note: Ensure you've completed all of the "prerequisites" listed above first.

To run this application, open the repo in a terminal and execute the following command:

```
mvn spring-boot:run
```

## **How to use:**

To try out the REST API, you can copy-paste any of the following cURL request examples into your terminal, or into an application such as [Postman](https://www.postman.com/).

### User CRUD endpoints:
________________

**Create** user:

```
curl --location --request POST 'localhost:3000/api/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "YOUR_USERNAME_HERE",
    "password": "YOUR_PASSWORD_HERE"
}'
```

**Read** user:

```
curl --location --request GET 'localhost:3000/api/user/YOUR_USERNAME_HERE'
```

**Update** user:

```
curl --location --request PUT 'localhost:3000/api/user/YOUR_USERNAME_HERE' \
--header 'Content-Type: application/json' \
--data-raw '{
    "password": "YOUR_PASSWORD_HERE"
}'
```

**Delete** user:

```
curl --location --request DELETE 'localhost:3000/api/user/YOUR_USERNAME_HERE'
```
