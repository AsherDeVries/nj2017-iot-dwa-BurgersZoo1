# Onderzoek SPI Theorie
Hoe kan je meerdere aparaten op een enkele SPI bus aansluiten waarbij de Arduino de master is?

### Hypothese
Ik verwacht dat met een bepaalde set van methode aanroepen makkelijk is om meerdere aparaten op een enkele SPI bus aan te sluiten.

### Testopzet
Online literatuur-onderzoek (Volgens CMD een `Design Pattern Search: Library` methode)naar het gebruik van SPI met meerdere devices.

## Literatuuronderzoek
SPI gebruikt vier kanalen om te verbinden tussen master en client :
* SCLK: Serial Clock (output vanuit master).
* MOSI: Master Output Slave Input, or Master Out Slave In (data output vanuit master).
* MISO: Master Input Slave Output, or Master In Slave Out (data output vanuit slave).
* SS: Slave Select (output vanuit master).

Bij Independent slave configuration worden meerder aparaten op de SCLK, MOSI en MISO paralell aangelsoten. Elk aparaat krijgt een eigen SS verbinding met de master. Dit ziet er als volgt uit:

![Aansluitschema SPI in Independent slave configuration](https://upload.wikimedia.org/wikipedia/commons/f/fc/SPI_three_slaves.svg)

Door een signiaal op de SS lijn te zetten kan worden gekozen naar welk client device de master communiseert.

Dit zou allemaal heel makkelijk gaan als alle aparaten op exact de zelfde manier werken. Helaas is dit niet het geval... De Arduino documentatie zegt hierover het volgende:

> The SPI standard is loose and each device implements it a little differently. This means you have to pay special attention to the device's datasheet when writing your code.

Concreet betekent dit dat de volgende dingen kunnen verschillen per SPI client device:
* Maximale kloksnelheid.
* [Most Significant Bit](https://en.wikipedia.org/wiki/Most_significant_bit) (MSB) of [Least Significant Bit](https://en.wikipedia.org/wiki/Least_significant_bit) (LSB) eerst
* [SPI Model](https://en.wikipedia.org/wiki/Serial_Peripheral_Interface_Bus#Mode_numbers)

Om te kunnen voorspellen of een SPI SD kaar lezer, NRF chip en RFID lezer samen op 1 SPI kunnen werken is in onderstaande tabel een opsommig gemaakt van maximale kloksnelheid, dataformaat en SPI mode voor elk van de aparaten.

|                       | SD-kaar                                                                                     | NRF24L01+                                                                                                                     | RFID-RC522                                                      |
| --------------------- | ------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| maximale kloksnelheid | niet gevonden in datasheet                                                                  | SCLK up to 8MHz                                                                                                               | niet gevonden in datasheet                                      |
| MSB First vs LSB First | MSB first                                                                                   | MSBit first en LSByte first                                                                                                   | MSB first                                                      |
| SPI Model             | niet gevonden in datasheet                                                                  | SPI MODE 0                                                                                                                    | SPI MODE 0                                                      |
| Datasheet bron             | [klik hier](http://alumni.cs.ucr.edu/~amitra/sdcard/Additional/sdcard_appnote_foust.pdf)    | [klik hier](https://www.sparkfun.com/datasheets/Components/SMD/nRF24L01Pluss_Preliminary_Product_Specification_v1_0.pdf)      | [klik hier](https://www.nxp.com/docs/en/data-sheet/MFRC522.pdf) |

## Conclusie
Het zou dus theoretisch mogelijk moeten zijn om de NRF, RFID en SD kaart met de zelfde instellingen parallel op de zelfde pinnen aan te sluiten.

## Bronnen
 * [Wikipedia](https://en.wikipedia.org/wiki/Serial_Peripheral_Interface_Bus)
 * [ELECTRONICS HUB](http://www.electronicshub.org/basics-serial-peripheral-interface-spi/)
 * [Arduino Documentatie over SPI](https://www.arduino.cc/en/Reference/SPI)
 
