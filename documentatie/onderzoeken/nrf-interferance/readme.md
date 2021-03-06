# Onderzoek NRF storing
Ontstaat er storing op het nrf signiaal wanneer een groot aantal aparaten tegelijk proberen te communiseren op het zelfde kanaal?

## Opzet Test
Met behulp van een Proof of Concept (Workshop) bestaande uit zo veel mogelijk Arduino's met nrf's uitvinden of alle individuele nodes tegelijk kunnen zenden naar een mysensors gateway.

## Hypothese
Ik verwacht dat als er meer dan 10 aparaten tegelijk proberen te zenden dat dan het mysensors netwerk overbeladen raakt en dat dan geen enkel bericht meer doorkomt.

## Uitvoering
Met vier man sterk hebben we stuk voor stuk 14 Arduino's (uno en nano door elkaar heen) aangesloten aan NRF24L01+ [volgens de instructies van MySensors](https://www.mysensors.org/build/connect_radio). Vervolgens is op 1 Arduino de [gateway](code/gateway.ino) code geupload en op 13 Arduino's de [node](code/node.ino) code geuplaod. De node code verzend met een willekeurige wachtperiode tussen de 2 en 5 seconden een berichtje naar de gateway. De gateway stuurt dit bericht door naar de seriele monitor zodat gecontroleerd kan worden of de Arduino aankomt. Elke node fungeert ook als repeater in dat een node elk bericht van een andere node opnieuw zal versturen om het bericht dichterbij de gateway te laten komen.

Nadat alle nodes geprogrammeerd waren, zijn de nodes een voor een op stroom aangesloten om te controleren of elke node individueel goed geconfigureerd is. Toen dit gevalideerd was zijn alle noes aangezet en verplaatst over de verdieping. Tegelijk is in de gateway gekeken van welke nodes verbonden waren met de gateway.

Op het hele netwerk staat acknowledgements geactiveerd en elke node is tegelijk geconfigureerd als repeater. Dit zorgt minimaal voor twee keer zo veel berichten in het netwerk omdat elk bericht met een ander bericht geverifieerd moet worden met een acknowledgement bericht.

## Resultaat
Wanneer alle nodes binnen vijf meter afstand staan van de gateway zonder grote obstakels dan worden alle 13 nodes goed ontvangen. Ook wanneer de directe afstand tussen gateway en node te groot is komen de berichten goed aan als er een andere noden tussenin zit die als repeater dient. Ook deze repeater heeft geen merkbare last van de grote hoeveelheid data die deze doorgeeft.

## Conclusie
Er is geen verband gevonden tussen het aantal aangesloten nodes op het netwerk en het verlies van pakketten.
