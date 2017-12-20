Android wifi is demo aplication that shows you how to connect and send messages to Arduino via wifi communication , the demo android app is also provided in google play under the link https://play.google.com/store/apps/details?id=com.tofaha.Android_wifi .
the communication system based on sever/client communication , in which android app act like client and the arduino program as server.


Android side (Kotlin) :
in the android side the steps to communicate to arduino is very easy , the socket client is used for that , the socket client needs two parameters to find the tareget wifi module the first parameter is the wifi module IP address , and the seconde parameter is the port number , well the first thing you need to do is to open a socket connection , the server side (arduino with wifi module) has to be launched first and then the client (android device) will reach the server by the ip address and port number , you should be aware of where you instanciate the socket because it has to be instanciated in diffrent thread then the UI thread , if you do it in UI thread the app will crash , here sample how to open socket connection inside :


class OpenConnection(private val ipAddress: String, private val portNumber: Int) : AsyncTask<Void, String, Void>() {

    override fun doInBackground(vararg voids: Void): Void? {
        try {
            MyData.socket = Socket(ipAddress, portNumber)
            System.out.println("connection opened")

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
 

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
 
 
