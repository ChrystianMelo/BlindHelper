
#include <Ultrasonic.h>
#include "SoftwareSerial.h"

SoftwareSerial bluetooth(9, 8); //TX, RX

//Sensor frente
#define PINO_TRG1  5
#define PINO_ECHO1 4
//Sensor baixo
#define PINO_TRG2  7
#define PINO_ECHO2 6
//Sensor direita
#define AN_R A0
//Sensor esquerda
#define AN_L A1

#define HOLE 30
#define STEP 30
#define SIDE 256

int state; //0: No obstacle | 1: Obstacle, turn right | 2: Obstacle, turn left | 3: Hole ahead | 4: Step ahead | 5: Obstacle, no way out

float bAnt;

Ultrasonic frente(PINO_TRG1, PINO_ECHO1);
Ultrasonic baixo(PINO_TRG2, PINO_ECHO2);

void setup() {
  pinMode(AN_R, INPUT);
  pinMode(AN_L, INPUT);
  bluetooth.begin(38400);
  Serial.begin(9600);
}

void loop() {
  float sFrente, sBaixo = 0;
  bool sDir, sEsq;

  sFrente = readSensor(PINO_TRG1, PINO_ECHO1, frente);
  sBaixo  = readSensor(PINO_TRG2, PINO_ECHO2, baixo);
  sDir    = readSensor2(AN_R);
  sEsq    = readSensor2(AN_L);

  state = 0;
  if(sFrente <= 40) {
    if(sDir && sEsq)
      state = 5;
    else if(sDir)
      state = 2;
    else
      state = 1;
  }
  else if(sBaixo >= bAnt + HOLE) state = 3;
  else if(sBaixo <= bAnt - STEP) state = 4;
  bAnt = sBaixo;

  Serial.println(sFrente);
  Serial.println(sBaixo);
  Serial.println(sDir);
  Serial.println(sEsq);
  sendMessage();
  delay(1000);
}

void sendMessage() {
  bluetooth.print("S ");
  bluetooth.print(state);
  bluetooth.print(" .");

  Serial.print("S ");
  Serial.print(state);
  Serial.println(" .");
}

float readSensor(int trg, int echo, Ultrasonic s) {
  return s.convert(s.timing(), Ultrasonic::CM);
}

bool readSensor2(int an) {
  return analogRead(an) <= SIDE;
}

