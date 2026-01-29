# NDDS Stub

This is a stub for the _National Direct Debit System_ digital service

## Functionality

### Stubbing API for BARS

#### BARS Metadata

- Method: `GET`
- Template URL: `/metadata/*ignore`

#### BARS Verify

- Method: `POST`
- Template URL: `/verify/*ignore`


### Stubbing API for Rate Limited Allow List (`rate-limited-allow-list`)

#### Check if user is allowed to access a feature

##### Request

- Method: `POST`
- Template: `/rate-limited-allow-list/services/:service/features/:feature`
- Example: `/rate-limited-allow-list/services/ndds-frontend/features/private-beta-2026`
- Request payload: `{ "identifier": "111111" }`

##### Scenarios
The different responses can be simulated by sending different values for the identifier. 

- Identifier ending in `5` will simulate a 500 with no response body.
- Identifier ending in `4` will simulate a 500 with no response body.
- Identifier ending in `2` will simulate a 200 with response body `{ "included": true }`
- Otherwise, any other identifier will simulate a 200 with response body `{ "included": false }`


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").