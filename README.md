# vslab

## Service Overview

|    Service    |    Type   | Port |                       Description                       |
|:-------------:|:---------:|:----:|:-------------------------------------------------------:|
| Eureka        | Discovery | 8761 |                                                         |
| Zuul          | Edge      |      | /user-api/ -> user-proxy /product-api/ -> product-proxy |
|               |           |      |                                                         |
| usersrv       | Core      | 8001 | contains user data                                      |
| productsrv    | Core      | 8002 | contains product data                                   |
| categorysrv   | Core      | 8003 | contains category data                                  |
|               |           |      |                                                         |
| user-proxy    | Composite | 8010 | proxy for usersrv                                       |
| product-proxy | Composite | 8020 | composite service / proxy for productsrv & categorysrv  |
