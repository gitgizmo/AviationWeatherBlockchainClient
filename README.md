Aviation Weather Blockchain Client
==================================

The Aviation Weather Blockchain is a data store with the mandate to store aviation weather in such a way that the data is non-reputable, the chronology of the adding to the data store is guaranteed, access to the data is performant and highly available.

Currently Aviation Weather Blockchain stores only METARs but may add support for TAFs and PIREPs in the future.

The Aviation Weather Blockchain Client is a Java API that queries a local copy of the Aviation Weather Blockchain. It serves as a wrapper to a subset of the functionality made available in the Multichain JSON RPC interface. 

What is a METAR
---------------

METAR is an abbreviated weather report predominantly used by pilots for the purposes of flight planning. METAR is an abbreviation for Meteorological Terminal Aviation Routine

MEATRs follow a prescribed format and at first glance challenging to read. However, with a little practice it soon becomes second hand.

Here is an example of a METAR in raw text format.

**CYYZ 072100Z 11004KT 6SM BR BKN012 BKN034 02/00 A2968**

The above METAR is for the ICAO station identifier CYYZ for the 7th day of the month at 2100 Zulu. The winds are from 110 degrees at 4 knots. There is 6 statute miles of visibility, with light mist. The clouds cover is broken at 1,200 feet AGL and again at 3,400 feet AGL. The temperature is 2 degrees Celsius and the dewpoint is 0 degrees Celsius. The altimeter reading is 29.68. Often the METAR appends remarks denoted with RMK and contains other relevant abbreviated information.

https://en.wikipedia.org/wiki/METAR

Usage
-----


Using the RPC port (and not the network port), username and password you can create AviationWeatherBlockchainClient as follows. Note that the RPC port is found in the params.dat file in the metar directory in the default-rpc-port property.

```java		
AviationWeatherBlockchainClient awbc = new AviationWeatherBlockchainClient("http://127.0.0.1:2652/", "foouser", "barpassword");
List<String> metarsForStationCountryStationDate = awbc.getMetarsForCountryAndStationIdAndObservationDate("CA", "CYYZ", new Date());
```
		
See [SampleClient.java](https://github.com/gitgizmo/AviationWeatherBlockchainClient/blob/master/AviationWeatherBlockchainClient/src/ca/nexcel/awbc/client/SampleClient.java) in the code base for a fulsome example

Installation Instructions
-------------------------
https://github.com/gitgizmo/AviationWeatherBlockchainClient/blob/master/AviationWeatherBlockchainClient/installation.txt
