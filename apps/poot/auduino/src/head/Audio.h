#ifndef AUDIO
#define AUDIO

#include <TMRpcm.h>
#include <SD.h>

#define SPEAKER_PIN 9
#define SD_CS_PIN 4

class Audio {
public:
  Audio();
  void loop();
  void play();
private:
  TMRpcm* tmrpcm;
};

#endif
