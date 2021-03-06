# Beslissingen

## Web development / deployment
Naar aanleiding van het [onderzoek](https://github.com/HANICA-MinorMulti/nj2017-iot-dwa-BurgersZoo1/blob/docs/documentatie/onderzoeken/docker/docker.md) is besloten dat Docker gebruikt zal worden omdat Docker de ontwikkeling van software op meerdere systemen versimpelt.

## Database
Uit [onderzoek](https://github.com/HANICA-MinorMulti/nj2017-iot-dwa-BurgersZoo1/blob/master/documentatie/onderzoeken/App%20Datastore/app%20datastore.md) blijkt dat MongoDB het meest geschikt is om snel mee te beginnen aangezien de datawens nog niet bekend is.
Het plan is om via een ORM te werken en dan maakt de onderliggende database, vooralsnog, weinig uit. Deze twee redenen gecombineerd zijn de beslissende factoren om te kiezen voor MongoDB.

## Prototyping hardware
Naar aanleiding van het [onderzoek](https://github.com/HANICA-MinorMulti/nj2017-iot-dwa-BurgersZoo1/blob/master/documentatie/onderzoeken/ArduinoVsRaspberry/ArduinoVsRaspberryOnderzoek.md) en ervaringen binnen de IoT groep is besloten om voor de gateway een Raspberry Pi te gebruiken. Deze kan een simpele gateway server hosten. Verder is besloten om gebruik te maken van Arduino's om te prototypen. Dit platform bied een snelle en makkelijke manier om de Hardware te testen en een uitgangspunt voor als er een echte paal gemaakt wordt. Hiervoor zou dan een custom board voor gemaakt kunnen worden.


## Gateway taal
Voor het schrijven van de gateway hebben we gekozen voor de taal Java. De mogelijkheden binnen de taal komen overeen met de eisen die gesteld worden aan de op te leveren software. Ook heeft de groep IoT'ers hier al ruime ervaring mee waardoor er risico's gemeden worden. Verder is er ook onderzoek naar gedaan: [onderzoek oplossings richtingen gateway taal](https://github.com/HANICA-MinorMulti/nj2017-iot-dwa-BurgersZoo1/tree/docs/documentatie/onderzoeken/oplossingsRichtingenGatewayTaal)

## Gateway framework
Voor het schrijven van de gateway hebben we de keuze gemaakt om met [Spark](http://sparkjava.com/) te werken. Dit is een licht framework waardoor het geschikt is voor de Raspberry Pi 3. Het volledige onderzoek is te lezen: `./documentatie/onderzoeken/gatewayFramework/`