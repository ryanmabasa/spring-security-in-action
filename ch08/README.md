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
