# ShopCLI
A CLI shopping application made in Java that implements unit, integration, system, and acceptance testing using JUnit.

## Features

- Add items to shopping cart
- Get current total of items in shopping cart
- Remove items from shopping cart
- Edit item quantities
- View shopping cart contents
- Calculate subtotal, tax, shipping, and final total
- Checkout with cart items

## Technology
- Java
- Maven
- JUnit

## How to Run

**Clone the repository:**

```bash
git clone git@github.com:bycait27/se433-testing-project.git
```

**Navigate to the project directory:**

```bash
cd se433-testing-project
```

**Package, compile, and run with Maven:**

```bash
mvn clean package
mvn clean compile
mvn exec:java -Dexec.mainClass="com.caitlinash.shoppingapp.App"
```

## Taxes and Shipping 

IL, CA, and NY require a sales tax of 6%. All other states have no tax.

"standard" and "next day" shipping are offered. Standard shipping costs $10, but is free if the user purchases over $50 worth of items. Next day shipping costs $25 no matter what.

## License 

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)  

see LICENSE file for more details

