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

curl -u emma:12345 http://localhost:8080/hello
curl -u natalie:12345 http://localhost:8080/hello

curl -u emma:12345 http://localhost:8080/secret/names/emma
curl -u natalie:12345 http://localhost:8080/secret/names/natalie

curl -u emma:12345 http://localhost:8080/book/details/natalie
curl -u natalie:12345 http://localhost:8080/book/details/natalie

curl -u natalie:12345 http://localhost:8080/documents/abc123
curl -u natalie:12345 http://localhost:8080/documents/asd555
curl -u emma:12345 http://localhost:8080/documents/asd555
curl -u emma:12345 http://localhost:8080/documents/abc123

```


