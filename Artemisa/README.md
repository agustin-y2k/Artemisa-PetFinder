![madewith](https://img.shields.io/badge/made%20with-SpringBoot-green?logo=spring&style=for-the-badge)

# How to use
## Set data
***Create a file named `secret.properties` in resources folder with the following content:***

    # Database
    username= <your_username>
    password= <your_password>
    
    # Google key
    client_id= <your_client_id>
    client_secret= <your_client_secret>

## Run ArtemisaApplication class

### Endpoints and request body are detailed in swagger

http://localhost:8080/swagger-ui.html

### You can import all the collections in Postman from JSON files in the doc folder


### Authentication on Postman

![Authentication-example](doc/bearer-token-example.jpg)


**Methods without authentication:**

![GET](https://img.shields.io/badge/method-GET-%3CCOLOR%3E.svg)

**Methods how need authentication:**

![GET](https://img.shields.io/badge/method-POST-yellow.svg)


![GET](https://img.shields.io/badge/method-PUT-blueviolet.svg)


![GET](https://img.shields.io/badge/method-DELETE-red.svg)





