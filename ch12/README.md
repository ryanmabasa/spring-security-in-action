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
curl -u nikolai:12345 http://localhost:8080/sell

[
  {"name":"beer","owner":"nikolai"},
  {"name":"candy","owner":"nikolai"}
]

curl -u julien:12345 http://localhost:8080/find

[
  {"name":"chocolate","owner":"julien"}
]
curl -u nikolai:12345 http://localhost:8080/find

[
  {"name":"beer","owner":"nikolai"},
  {"name":"candy","owner":"nikolai"}
]

curl -u julien:12345 http://localhost:8080/sell

[
  {"name":"chocolate","owner":"julien"}
]


curl -u nikolai:12345 http://localhost:8080/products/c

[
  {"id":2,"name":"candy","owner":"nikolai"}
]
```


