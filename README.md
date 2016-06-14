# vslab

## Service Overview

|    Service    |    Type   | Port |                       Description                       |
|:-------------:|:---------:|:----:|:-------------------------------------------------------:|
| Eureka        | Discovery | 8761 |                                                         |
| Zuul          | Edge      | 8100 | /user-api/ -> user-proxy /product-api/ -> product-proxy |
| Turbine       | Monitoring| 8200 |                                                         |
|               |           |      |                                                         |
| usersrv       | Core      | 8001 | contains user data                                      |
| productsrv    | Core      | 8002 | contains product data                                   |
| categorysrv   | Core      | 8003 | contains category data                                  |
|               |           |      |                                                         |
| user-proxy    | Composite | 8010 | proxy for usersrv                                       |
| product-proxy | Composite | 8020 | composite service / proxy for productsrv & categorysrv  |

## Host File Adjustments

In order to multiple streams from one host, you have to adjust your hostfile as follows:

```
127.0.0.1       productdomain
127.0.0.1       userdomain
```

A detailed description can be found [here](https://stackoverflow.com/questions/29934171/spring-cloud-turbine-unable-to-handle-multiple-clients).

You can access the Monitoring here: [http://localhost:8200/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8200%2Fturbine.stream](http://localhost:8200/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8200%2Fturbine.stream)