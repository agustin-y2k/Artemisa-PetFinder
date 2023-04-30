![madewith](https://img.shields.io/badge/made%20with-SpringBoot-green?logo=spring&style=for-the-badge)

# How to use
## Set data
***Create a file named `secret.properties` in resources folder with the following content:***

    # Database
    username= <your_username>
    password= <your_password>
    
    # Email Sender
    email_username= <your_email>
    email_password= <your_email_password>

## Run ArtemisaApplication class

### Endpoints and request body are detailed in swagger

http://localhost:8080/swagger-ui.html


### Authentication on Postman

![Register](https://img.shields.io/badge/Post-Register-blue) **Send an email with a link to confirm the user**

![ConfirmAccount](https://img.shields.io/badge/Get-Confirm%20Account-blue) **Confirm the user**

![Login](https://img.shields.io/badge/Post-Login-blue) **Give the auth token**

![Logout](https://img.shields.io/badge/Post-Logout-blue)  **Need the auth token**

![Authentication-example](doc/bearer-token-example.jpg)


**Methods without authentication:**

![GET](https://img.shields.io/badge/method-GET-%3CCOLOR%3E.svg)

**Methods how need authentication:**

![GET](https://img.shields.io/badge/method-POST-yellow.svg)


![GET](https://img.shields.io/badge/method-PUT-blueviolet.svg)


![GET](https://img.shields.io/badge/method-DELETE-red.svg)





