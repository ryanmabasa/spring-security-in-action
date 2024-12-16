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


Example:

Authorization Code
```
http://localhost:8080/.well-known/openid-configuration

http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.manning.com/authorized&code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&code_challenge_method=S256
```


```
curl -X POST 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=https://www.manning.com/authorized&grant_type=authorization_code&code=ao2oz47zdM0D5gbAqtZVB&code_verifier=qPsH306' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
```


Client Credentials
```
curl -X POST 'http://localhost:8080/oauth2/token?grant_type=client_credentials&scope=CUSTOM' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='
```