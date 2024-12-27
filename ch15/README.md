Need to run auth server for it to work

The list of steps we need to follow to make our resource server understand the custom claims in the access token is the following:

1. Create a custom authentication object. This object will define the new shape, including the custom data.

2. Create a JWT authentication converter object. This object will define the logic for translating the JWT into the custom authentication object.

3. Configure the JWT authentication converter you created in step 2 to be used by the authentication mechanism.

4. Change the /demo endpoint to return the authentication object from the security context.

5. Test the endpoint, and check that the authentication object contains the custom "priority" field.


Make sure to run CH14. Get the access token from ch14

Opaque
```
curl 'http://localhost:9090/demo' --header 'Authorization: Bearer 2uzbQIODe31d4IdwV3zTjMjWqCGgRRymeXTKke_StabjgtjX-gpZOAPeY6p65qSPMfmOpNdw2Z2UsExrshOIOVmkhGD4SyD8X0fSIYK0rUZgs1qpCc8trrU5MgE9HKQ6'

{
    "principal": {
        "name": "client",
        "attributes": {
            "active": true,
            "sub": "client",
            "aud": [
                "client"
            ],
            "nbf": "2024-12-24T17:50:39Z",
            "scope": [
                "CUSTOM"
            ],
            "iss": "http://localhost:8080",
            "exp": "2024-12-25T05:50:39Z",
            "iat": "2024-12-24T17:50:39Z",
            "jti": "2c0d4b27-56e2-4bc9-9af9-1a4f5856db6f",
            "client_id": "client",
            "token_type": "Bearer"
        },
        "authorities": [
            {
                "authority": "SCOPE_CUSTOM"
            }
        ],
        "claims": {
            "active": true,
            "sub": "client",
            "aud": [
                "client"
            ],
            "nbf": "2024-12-24T17:50:39Z",
            "scope": [
                "CUSTOM"
            ],
            "iss": "http://localhost:8080",
            "exp": "2024-12-25T05:50:39Z",
            "iat": "2024-12-24T17:50:39Z",
            "jti": "2c0d4b27-56e2-4bc9-9af9-1a4f5856db6f",
            "client_id": "client",
            "token_type": "Bearer"
        },
        "id": "2c0d4b27-56e2-4bc9-9af9-1a4f5856db6f",
        "active": true,
        "subject": "client",
        "username": null,
        "clientId": "client",
        "expiresAt": "2024-12-25T05:50:39Z",
        "audience": [
            "client"
        ],
        "notBefore": "2024-12-24T17:50:39Z",
        "issuedAt": "2024-12-24T17:50:39Z",
        "issuer": "http://localhost:8080",
        "scopes": [
            "CUSTOM"
        ],
        "tokenType": "Bearer"
    },
    "credentials": {
        "tokenValue": "fARkF0ABbQ5FY4ZrzWaOH20idNcRWDXobq1aAMdOzOnGMpRKsUTqFgvfIRRPQL5GKPYTKhvUYcPSLOn8lh_rRGu9siMv5grWr8kbFK1kMnyxQBxxEKmeHwjtclLUU-mV",
        "issuedAt": "2024-12-24T17:50:39Z",
        "expiresAt": "2024-12-25T05:50:39Z",
        "tokenType": {
            "value": "Bearer"
        },
        "scopes": []
    },
    "authorities": [
        {
            "authority": "SCOPE_CUSTOM"
        }
    ],
    "details": {
        "remoteAddress": "0:0:0:0:0:0:0:1",
        "sessionId": null
    },
    "authenticated": true,
    "token": {
        "tokenValue": "fARkF0ABbQ5FY4ZrzWaOH20idNcRWDXobq1aAMdOzOnGMpRKsUTqFgvfIRRPQL5GKPYTKhvUYcPSLOn8lh_rRGu9siMv5grWr8kbFK1kMnyxQBxxEKmeHwjtclLUU-mV",
        "issuedAt": "2024-12-24T17:50:39Z",
        "expiresAt": "2024-12-25T05:50:39Z",
        "tokenType": {
            "value": "Bearer"
        },
        "scopes": []
    },
    "tokenAttributes": {
        "active": true,
        "sub": "client",
        "aud": [
            "client"
        ],
        "nbf": "2024-12-24T17:50:39Z",
        "scope": [
            "CUSTOM"
        ],
        "iss": "http://localhost:8080",
        "exp": "2024-12-25T05:50:39Z",
        "iat": "2024-12-24T17:50:39Z",
        "jti": "2c0d4b27-56e2-4bc9-9af9-1a4f5856db6f",
        "client_id": "client",
        "token_type": "Bearer"
    },
    "name": "client"
}
```