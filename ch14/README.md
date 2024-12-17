## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)

## Installation

### Prerequisites

- Java Development Kit (JDK) version 8 or higher
- Maven or Gradle (for dependency management)

## Usage

https://www.baeldung.com/spring-authentication-single-page-application-pkce

https://tonyxu-io.github.io/pkce-generator/
Example:

Authorization Code - first authorize then curl
```
http://localhost:8080/.well-known/openid-configuration

http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.manning.com/authorized&code_challenge=Ys2R6lAx2idjbr_mVPYzweT2loaYVBPvUKBaeu3zDgo&code_challenge_method=S256

curl -X POST 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=https://www.manning.com/authorized&grant_type=authorization_code&code=lCC4um4ivSSAvXRtldrs8bWZV-Lre7HGOKjFnpifnZMTtZA6FGR7nxVXfNwVYX0koYfX0V7ejU9hm3birOzt_3nO1MAhiBuIx2rcWPQ9YXVqnUuWxGmWVrRfo0Q3gvR2&code_verifier=Uj0Kh6iiJvuEPKQcEnejWB9__bxCY-XwglkymMyXlJo' --header 'Authorization: Basic YmlsbDpwYXNzd29yZA=='
```


Client Credentials
```
curl -X POST 'http://localhost:8080/oauth2/token?grant_type=client_credentials&scope=CUSTOM' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
```