#include <Arduino.h>
#include "./head/MaduinoPortal.h"

MaduinoPortal* portal;
Audio* audio;

void setup () {
  audio = new Audio();
  portal = new MaduinoPortal(audio);
}

void loop() {
    portal->loop();
    audio->loop();
}