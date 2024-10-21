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

Hellow controller - HTTP BASIC

Response
WWW-Authenticate: Basic realm="OTHER"

< HTTP/1.1 401
< Set-Cookie: JSESSIONID=459BAFA7E0E6246A463AD19B07569C7B; Path=/; HttpOnly
< message: Luke, I am your father!

Example:

```bash
curl -v http://localhost:8080/hello
```

Ciao controller - DelegatingSecurityContextCallable

Example:

```bash
curl -u user:2eb3f2e8-debd-420c-9680-48159b2ff905 http://localhost:8080/ciao
```

Hola controller - DelegatingSecurityContextExecutorService

Example:

```bash
curl -u user:5a5124cc-060d-40b1-8aad-753d3da28dca http://localhost:8080/hola
```

Form login controller
```bash
curl -u user:cdd430f6-8ebc-49a6-9769-b0f3ce571d19 http://localhost:8080/home
```
