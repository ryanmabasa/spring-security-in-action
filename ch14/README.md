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

Basic is client:secret
```
http://localhost:8080/.well-known/openid-configuration

http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.manning.com/authorized&code_challenge=Ys2R6lAx2idjbr_mVPYzweT2loaYVBPvUKBaeu3zDgo&code_challenge_method=S256

curl -X POST 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=https://www.manning.com/authorized&grant_type=authorization_code&code=lCC4um4ivSSAvXRtldrs8bWZV-Lre7HGOKjFnpifnZMTtZA6FGR7nxVXfNwVYX0koYfX0V7ejU9hm3birOzt_3nO1MAhiBuIx2rcWPQ9YXVqnUuWxGmWVrRfo0Q3gvR2&code_verifier=Uj0Kh6iiJvuEPKQcEnejWB9__bxCY-XwglkymMyXlJo' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA=='

{
    "access_token": "eyJraWQiOiI4ODlhNGFmO…",
    "scope": "openid",
    "id_token": "eyJraWQiOiI4ODlhNGFmOS1…",
    "token_type": "Bearer",
    "expires_in": 299
}
```


Client Credentials

```
curl -X POST 'http://localhost:8080/oauth2/token' --header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' --header 'Content-Type: application/x-www-form-urlencoded' --data 'grant_type=client_credentials&scope=CUSTOM'

Non Opaque
{
    "access_token": "eyJraWQiOiI4N2E3YjJiNS…",
    "scope": "CUSTOM",
    "token_type": "Bearer",
    "expires_in": 300
}

Opaque
{
    "access_token": "iED8-...",
    "scope": "CUSTOM",
    "token_type": "Bearer",
    "expires_in": 299
}
```

From non opaque token, we can validate/introspect request to auth server

```
curl -X POST 'http://localhost:8080/oauth2/introspect' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data 'token=LDdv6TTCQuRKuF9CFslASVXPcTOqzfDgDMzHx3NgfC7eAVHJZrP6TXRihtjYcODwUc5c5R_2DZSvyzyJ4iWnYmCkiIO1uM8y4aaszQgmmwKzQmJTxCUg0mhmAD9uhjK_'

{
    "active": true,
    "sub": "client",
    "aud": [
        "client"
    ],
    "nbf": 1682941720,
    "scope": "CUSTOM",
    "iss": "http://localhost:8080",
    "exp": 1682942020,
    "iat": 1682941720,
    "jti": "ff14b844-1627-4567-8657-bba04cac0370",
    "client_id": "client",
    "token_type": "Bearer"
}

If the token doesn’t exist or has expired, its active status is false, as shown in the next snippet:

{
    "active": false,
    
}
```

Revoke token
```
curl -X POST 'http://localhost:8080/oauth2/revoke' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data 'token=LDdv6TTCQuRKuF9CFslASVXPcTOqzfDgDMzHx3NgfC7eAVHJZrP6TXRihtjYcODwUc5c5R_2DZSvyzyJ4iWnYmCkiIO1uM8y4aaszQgmmwKzQmJTxCUg0mhmAD9uhjK_'

```

Summary
The Spring Security authorization server framework helps you build a custom OAuth 2/OpenID Connect authorization server from scratch.

Since the authorization server manages the user and client details, you must implement components defining how the app collects this data:

To manage the user details, the authorization server needs a similar Spring Security component like any other web app: an implementation of a UserDetailsService.

To manage the client details, the authorization server provides another contract you must implement: the RegisteredClientRepository.

You can register clients that use various authentication flows (grant types). Preferably, the same client shouldn’t use both user-dependent (like the authorization code grant type) and user-independent (like client credentials grant type) flows.

When using non-opaque tokens (usually JWTs), you must also configure a component to manage the key pairs the authorization server uses to sign the tokens. This component is named the JWKSource.

When using opaque tokens (tokens that don’t contain data), the resource server must use the introspection endpoint to verify a token’s validity and collect data necessary for authorization.

Sometimes, you’d need a way to invalidate already issued tokens. The authorization server offers the revocation endpoint for this capability. When using revocation, the resource server must always introspect the tokens (even non-opaque ones) to verify their validity.