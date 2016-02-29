# pindrop
=========

## Implementation
------------------
Created a REST interface that handles GET requests.
A blank request to the service gets the current system performance
A request with a UNIX timestamp will get historical data if it exists
Did not use any external API. Used only inbuilt Java classes to collect system details.
A MySQL server backend stores all data that is captured - used for fetching historical data

## Known issues
------------------
Following are known issues because java limitations -
CPU performance does not work in Windows. Only works in Linux.
All disk drive details will be fetched in Windows system. Will fetch only the details of root in Linux.

## Challenges
------------------
First time working with creating restful service and with JAX RS and JERSEY
Initial system setup and trying to familiarize with Rest and JAX RS took a lot of time. Close to 2.5 hours.
Tried to use SIGAR API for collecting system information but could not continue due to difficulties in configuring maven with sigar.

## References
------------------
crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
stackoverflow.com/questions/47177/how-to-monitor-the-computers-cpu-memory-and-disk-usage-in-java

## What else can be done
------------------
Instead of user specifying timestamp, a time and data input can be obtained and matched with timestamp in the database
Average metrics can be calculated from reading multiple rows of the database for a given date or interval