# Currency Exchange Application README

## Table of Contents
- ## Introduction
- ## Features
- ## Technologies Used
- ## Setup and Deployment
- ## Usage
- ## Available Currencies
- ## Contributing

## Introduction

The Currency Exchange Application is a Java Spring-based project that enables users to perform currency exchange calculations based on real-time exchange rates. Unlike traditional applications that update currency data periodically, this application updates the data with every new request to ensure the most accurate exchange rates. It leverages the FreeCurrencyAPI to retrieve currency data.

## Features

- Real-time currency exchange calculations.
- Dynamic updating of currency data with each new request.
- Containerized using Docker for easy deployment.

## Technologies Used

- Java Spring
- Docker
- FreeCurrencyAPI

## Setup and Deployment

To set up and deploy the Currency Exchange Application:

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd currency-exchange-app`
3. Build the Docker image: `docker build -t currency-exchange-app .`
4. Run the Docker container: `docker run -p 9090:9090 currency-exchange`

## Usage

1. Access the application through your web browser or API client at `http://localhost:9090`.
2. Select the source and target currencies from the available options.
3. Enter the amount in the source currency that you wish to convert.
4. The application will display the converted amount in the target currency based on the real-time exchange rate.

## Available Currencies

Here is the list of available currencies that you can use in the application:

- EUR Euro
- USD US Dollar
- JPY Japanese Yen
- BGN Bulgarian Lev
- CZK Czech Republic Koruna
- DKK Danish Krone
- GBP British Pound Sterling
- HUF Hungarian Forint
- PLN Polish Zloty
- RON Romanian Leu
- SEK Swedish Krona
- CHF Swiss Franc
- ISK Icelandic Króna
- NOK Norwegian Krone
- HRK Croatian Kuna
- RUB Russian Ruble
- TRY Turkish Lira
- AUD Australian Dollar
- BRL Brazilian Real
- CAD Canadian Dollar
- CNY Chinese Yuan
- HKD Hong Kong Dollar
- IDR Indonesian Rupiah
- ILS Israeli New Sheqel
- INR Indian Rupee
- KRW South Korean Won
- MXN Mexican Peso
- MYR Malaysian Ringgit
- NZD New Zealand Dollar
- PHP Philippine Peso
- SGD Singapore Dollar
- THB Thai Baht
- ZAR South African Rand

## Contributing

Contributions are welcome! If you find any issues or want to enhance the application, please create a pull request.
