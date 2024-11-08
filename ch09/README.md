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
INFO 21412 --- [nio-8080-exec-1] c.l.ssia.filters.CsrfTokenLogger : CSRF token tAlE3LB_R_KN48DFlRChc…


curl -X POST http://localhost:8080/hello

curl -X POST   http://localhost:8080/hello 
-H 'Cookie: JSESSIONID=21ADA55E10D70BA81C338FFBB06B0206'   
-H 'X-CSRF-TOKEN: tAlE3LB_R_KN48DFlRChc…'
```


