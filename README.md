Android wifi is demo aplication that shows you how to connect and send messages to Arduino via wifi communication , the demo android app is also provided in google play under the link https://play.google.com/store/apps/details?id=com.tofaha.Android_wifi .
the communication system based on sever/client communication , in which android app act like client and the arduino program as server.


# Android side (Kotlin) :
in the android side the steps to communicate to arduino is very easy , the socket client is used for that , the socket client needs two parameters to find the tareget wifi module the first parameter is the wifi module IP address , and the seconde parameter is the port number , well the first thing you need to do is to open a socket connection , the server side (arduino with wifi module) has to be launched first and then the client (android device) will reach the server by the ip address and port number , you should be aware of where you instanciate the socket because it has to be instanciated in diffrent thread then the UI thread , if you do it in UI thread the app will crash , here sample how to open socket connection inside asyncTask:

## Open socket connection

    **class OpenConnection(private val ipAddress: String, private val portNumber: Int) : AsyncTask<Void, String, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            try {
                MyData.socket = Socket(ipAddress, portNumber)
                System.out.println("connection opened")

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }
    }**
 
## Send messages

and to send messages to server , you will need printWriter object to write in socket ibject that you already created and the socket will do the rest , here how you can do it :

class SendMessages(msg: String) : AsyncTask<Void, String, Void>() {

    internal var msg = ""

    init {
        this.msg = msg
    }

    override fun doInBackground(vararg voids: Void): Void? {
        try {

            val out = PrintWriter(BufferedWriter(OutputStreamWriter(MyData.socket
                    .getOutputStream())), true)

            out.println(msg)
            println("message send")

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}

## Receive messages

and to recieve messages from server , all you need to do is create an instance of bufferReader and pass the socket to it as parameter , and of course you need to do that inside loop with time out as optional , so whenever the server send message the socket object will receive it and bufferdReader will read the message from socket and you write it to the UI thread , here is the code (I used anko library in this code):

 doAsync {

            input = BufferedReader(InputStreamReader(MyData.socket.getInputStream()))
            var msgText = "waiting ...."
            MyData.THREAD_RUNNING = true

            while (true) {

                //println(input?.readLine())
                Thread.sleep(300)
                msgText = input?.readLine().toString()

                uiThread {
                    serverResponse.text = msgText
                }
            }
        }


to close the connection that what you do : socket.close() method :

## Close connection

class CLoseConnection : AsyncTask<Void , String , Void>(){

    override fun doInBackground(vararg params: Void?): Void? {
        try {

            MyData.socket?.close()
            System.out.println("connection closed")

        }catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}
 

# Server Side (Arduino with wifi module)
in the server side I use arduino with wifi module(ESP8266) , well the first thing you need to consider is how to communicate arduino with the module , the communication will be done by the serial communication(Rx/Tx) , arduino board contains UART(Universal Asynchronious Receiver Transmmiter) , and the number of UART that arduino board contain is depend on the type of arduino like arduino uno has only one UART , so we are going to use the library of the serialSoftwar in arduino and by this library we can write and read to serial buffer (Rx/Tx) , after that we attach the Rx and Tx pin to Tx and Rx pin of the ESP8266 , this is how to communicate arduino with ESP8266 :

## Communicate arduino with esp module

    #include <SoftwareSerial.h>
    SoftwareSerial ESP8266(2,3); //(Rx/Tx) that means you have to attach pin 2 with esp Tx and pin 3 with esp Rx
    
## ESP configuration and setup with AT commands

after the communication is established you now need to configure ESP as you need it to work , and that will be done by the ATcommand , in arduino IDE we will type the ATcommand and write it to the serialSoftware :

    void sendESP8266Cmdln(String cmd, int waitTime)
    {
      ESP8266.println(cmd);
      delay(waitTime);
      clearESP8266SerialBuffer();
    }
    
    void setup() {
  
      Serial.begin(9600); 
      ESP8266.begin(115200);  // change this value to your wifi module (esp8266) baud rate

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

## Receive messages from client 

after the configuration and setup of the ESP now you are ready to send and recieve data from client :
to recieve data from client is very easy step , first you need to prepare the server and that will be done in the setup part , after that you write this code inside the loop method :

    void loop() {

      if(ESP8266.available()){

        String msg = ESP8266.readString();
        Serial.println("MSG: "+msg);

      }
    }

## Send messages to client 

to send data to client , well in this case to establish the client you need communication id , so if there are for exemple 5 client , so you need 5 communication id to send to each client , and in the case there is only one client you can use 0 as the value of the communication id :

    
    connectionId = 0 ;
    void sendStringResponse(int connectionId, String content)
    {     
         sendCIPData(connectionId,content);
    }
    
    void loop() {

        if ( Serial.available() ){  
            String s = Serial.readString();
            sendStringResponse(connectionId , s + "\r\n"); 
        }
    }
