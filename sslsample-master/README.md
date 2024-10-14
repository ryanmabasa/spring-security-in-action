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

Describe how to use the project. Include code snippets if needed.

Example:

```bash
openssl req -newkey rsa:2048 -x509 -keyout key.pem -out cert.pem -days 365
```

```bash
openssl pkcs12 -export -in cert.pem -inkey key.pem -out certificate.p12 -name "certificate"
```

```
curl -k -u user:bf456446-1efd-49bc-a814-904dfaffa9f0  https://localhost:8080/test
```