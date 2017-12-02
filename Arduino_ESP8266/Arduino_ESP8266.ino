#include <SoftwareSerial.h>
SoftwareSerial ESP8266(2,3);

#define DEBUG true

boolean FAIL_8266 = false;
boolean headerSent = false;
int connectionId = 0 ; // the connection id is O only if there is one client 

#define BUFFER_SIZE 128
char buffer[BUFFER_SIZE];

String ssid="\"ayoub\"";
String pass="\"ayoub1234\"";

void setup() {
  
  Serial.begin(9600);
  ESP8266.begin(115200);
  
  do{
  ESP8266.println("AT");
    delay(1000);
    if(ESP8266.find((char*)"OK"))
    {
      Serial.println("Module is ready");
      delay(1000);
      clearESP8266SerialBuffer();

      //configure ESP as station 
      sendESP8266Cmdln("AT+CWMODE=1",1000);

      //Join Wifi network
      sendESP8266Cmdln("AT+CWJAP="+ssid+","+pass,6500);

      //Get and display my IP
      sendESP8266Cmdln("AT+CIFSR", 1000);  

      //Set multi connections
      sendESP8266Cmdln("AT+CIPMUX=1", 1000);
      
      //Setup web server on port 80
      sendESP8266Cmdln("AT+CIPSERVER=1,3333",1000);

      Serial.println("Server setup finish");

      FAIL_8266 = false;

    }
    else{
      Serial.println("Module have no response.");
      delay(500);
      FAIL_8266 = true;
    }
  }while(FAIL_8266);

  ESP8266.setTimeout(100); 

}

void loop() {

  if(ESP8266.available()){

    String msg = ESP8266.readString();
    Serial.println("MSG: "+msg);

    // advance cursor to "pin="  and if you want this code to work you have to comment the two lines above
    if(ESP8266.find((char*)"pin=") || ESP8266.find((char*)"pio=") || ESP8266.find((char*)"phn=") || ESP8266.find((char*)"pin<")){

        int firstNumber = (ESP8266.read()-48); // get first number as integer 
        int secondNumber = (ESP8266.read()-48); //get the second number as integer

        if (firstNumber == 1 || secondNumber == 1){
      
          Serial.println("MSG: the first led is ON");
      
        }else if(firstNumber == 2 || secondNumber == 2){
      
          Serial.println("MSG: the first led is ON");
    
        }else if(firstNumber == 0 && secondNumber == 1){
      
          Serial.println("MSG: the first led is ON");
    
        }else if(firstNumber == 0 && secondNumber == 2){
      
          Serial.println("MSG: the first led is ON");
    
        }
   }
    
  }
  
  if ( Serial.available() ){  
        String s = Serial.readString();
        sendStringResponse(connectionId , s + "\r\n"); 
  }
}

void clearESP8266SerialBuffer()
{
  Serial.println("= clearESP8266SerialBuffer() =");
  while (ESP8266.available() > 0) {
    char a = ESP8266.read();
    Serial.write(a);
  }
  Serial.println("==============================");
}



String sendData(String command, const int timeout, boolean debug)
{
    String response = "";
    
    int dataSize = command.length();
    char data[dataSize];
    command.toCharArray(data,dataSize);
           
    ESP8266.write(data,dataSize); // send the read character to the ESP8266
    
    return response;
}
 
/*
* Name: sendHTTPResponse
* Description: Function that sends HTTP 200, HTML UTF-8 response
*/
void sendHTTPResponse(int connectionId, String content)
{     
     // build HTTP response
     String httpResponse;
     String httpHeader;
     // HTTP Header
     httpHeader = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n"; 
     httpHeader += "Content-Length: ";
     httpHeader += content.length();
     httpHeader += "\r\n";
     httpHeader += "Connection: close\r\n";
     httpResponse = httpHeader + content + " r\n"; 
     sendCIPData(connectionId,httpResponse);
}

void sendStringResponse(int connectionId, String content)
{     
     sendCIPData(connectionId,content);
}
 
/*
* Name: sendCIPDATA
* Description: sends a CIPSEND=<connectionId>,<data> command
*
*/
void sendCIPData(int connectionId, String data)
{
   String cipSend = "AT+CIPSEND=";
   cipSend += 0;
   cipSend += ",";
   cipSend +=data.length();
   cipSend +="\r\n";
   sendCommand(cipSend,1000,DEBUG);
   sendData(data,100,DEBUG);
}
 
/*
* Name: sendCommand
* Description: Function used to send data to ESP8266.
* Params: command - the data/command to send; timeout - the time to wait for a response; debug - print to Serial window?(true = yes, false = no)
* Returns: The response from the ESP8266 (if there is a reponse)
*/
String sendCommand(String command, const int timeout, boolean debug)
{
    String response = "";
           
    ESP8266.print(command); // send the read character to the ESP8266
    
    long int time = millis();
    
    while( (time+timeout) > millis())
    {
      while(ESP8266.available())
      {
        
        // The esp has data so display its output to the serial window 
        char c = ESP8266.read(); // read the next character.
        response+=c;
      }  
    }
    
    if(debug)
    {
      Serial.print(response);
    }
    
    return response;
}

boolean waitOKfromESP8266(int timeout)
{
  do{
    Serial.println("wait OK...");
    delay(1000);
    if(ESP8266.find((char*)"OK"))
    {
      return true;
    }

  }while((timeout--)>0);
  return false;
}


//Send command to ESP8266, assume OK, no error check
//wait some time and display respond
void sendESP8266Cmdln(String cmd, int waitTime)
{
  ESP8266.println(cmd);
  delay(waitTime);
  clearESP8266SerialBuffer();
}
