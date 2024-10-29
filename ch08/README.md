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

- Form Login
- Basic
- Custom Authentication Provider
- Spring Security Contextr

Hellow com.example.ch0506.controller - HTTP BASIC

Response
WWW-Authenticate: Basic realm="OTHER"

< HTTP/1.1 401
< Set-Cookie: JSESSIONID=459BAFA7E0E6246A463AD19B07569C7B; Path=/; HttpOnly
< message: Luke, I am your father!

Example:

HelloController.java
```bash
curl http://localhost:8080/hola

curl -u john:12345 http://localhost:8080/hola
```

TestController.java
```bash
curl -XPOST http://localhost:8080/a

curl -XGET http://localhost:8080/a

curl -u john:12345 -XGET http://localhost:8080/a

curl -u john:12345 -XGET http://localhost:8080/a/b  
```

ProductController.java
```bash
curl http://localhost:8080/product/1234a

curl http://localhost:8080/product/12345
```

VideoController
```
curl http://localhost:8080/email/jane@example.com

curl http://localhost:8080/email/jane@example.net

curl -u john:12345 http://localhost:8080/video/us/en

curl -u john:12345 http://localhost:8080/video/fr/fr
```
