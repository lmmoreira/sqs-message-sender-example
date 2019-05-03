# README #

Fleet Telemetry Driver project is a reactive service responsible for handle tracks sent via Fleet App.

To accomplish its goal, this service exposes an endpoint that accepts telemetry data in `JSON` format (as follows):
```
{
    "latitude": -20.8227839,
    "longitude": -49.4035672,
    "batteryStatus": 70,
    "trackDate": "2019-01-29T18:17:00.896-02:00",
    "orders": [{"orderId": 50001, "externalId": "1XYZ"}, {"id": 50002, "externalId": "2VTW"}, {"orderId": 50003, "externalId": "3WWT"}],
    "currentOrders": [{"orderId": 50001, "externalId": "1XYZ"}, {"orderId": 50003, "externalId": "3WWT"}],
    "routeId": 50000,
    "workerId": 1,
    "workerName": "Zé da Silva",
    "workerPhone": "+11988458249",
    "legCorrectionFactor": 1,
    "legType": "DESTINATION",
    "serviceLatitude": -20.8238983,
    "serviceLongitude": -49.3981783,
    "speed": 37,
    "tenant": "BR"
}
```

After receiving the request, it filters the payload and publishes messages on the following queues for later async processing:

- DEV_FLEET_ORDER_TRACKING_QUEUE_DYNAMODB (handled by [fleet-telemetry-order](https://bitbucket.org/lorem/fleet-telemetry-order) service)
- DEV_IDP_ORDER_TRACKING_QUEUE (handled by [fleet-telemetry-idp](https://bitbucket.org/lorem/fleet-telemetry-idp) service)
- DEV_FLEET_WORKER_AVAILABILITY_QUEUE (handled by [Fleet worker](https://bitbucket.org/lorem/lorem-delivery-platform-fleet-worker) service)

## Technologies and Dependencies ##

* Async Communication
    * SQS
* Java Frameworks
    * Spring Boot
    * Spring Webflux

## Build and deploy ##

Build and deploy are made via [Jenkins job](http://dev-jenkins1.dc.lorem.com.br:8080/view/Logistics/job/telemetry/job/fleet-telemetry-driver-pipeline/).

## Developing locally using `localstack`

Locally we use `localstack` to simulate AWS services. Before start, make sure you have
[lorem's local environment project](https://bitbucket.org/lorem/lorem-delivery-platform-local-environment/src/master/) up and running on your development machine
following its instructions.

All the SQS queues required for running this application are configured on project's above.# sqs-message-sender-example
