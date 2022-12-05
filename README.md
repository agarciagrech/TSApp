# TSApp - Server Manual
Program designed to monitor and record the physiological parameters of patients suffering from epilepsy and who suffer seizures triggered by a sleep disorder.

TSApp is a multi-thread server that is able to work with various clients at the same time. It follows the TCP protocol, since this medical application requires high reliability. The server manages the different actions performed by the clients, ensures a successful register of unique users, making a distinction between patient and doctor, and a secure login with an automatically generated username and password. The server stores the information of the clients in a database, and it allows the data recovery of each client.

**DOWNLOADING AND INTALLATION OF THE SERVER**

Please follow these steps carefully to download and install TSApp: 
1. Download and install NetBeans: free and open-source integrated development environment (IDE) for application development on Windows, Mac, Linux, and Solaris operating systems. 
2. Download the **TSApp program**. 
3. In NetBeans, go to File, on the upper right corner. Then click on **“Open Project”** and select the correct path where you downloaded TSApp, and click on **“Open Project”**. Now the TSApp program is at your disposition. 
4. In the Projects tab, select the program **PatientTS**, select the package **serverTSThreads**, and right click on the class **ServerTSThreads**, and then click on **“Run File”**. 
5. The server is now running. 

**PRESETTINGS**

The interaction will be different depending on who is accessing to the program. Before everyone can use the application, there will be a doctor user (username: admin, password: admin) defined by default, so it can introduce the data of new doctors and patients that will have access to the application.

In order to have access to the capabilities of the application, every doctor and patient must have been registered by a docotr user or, in the case of the patients, they can register themselves. This register operation will automatically generate a unique username and a specific password for each new doctor and patient. Once the doctors and patients receive their own username and password, they will be ready to use the corresponding interface.

**SERVER PROGRAM EXECUTING**

In the TSApp program, the main class to execute the program is **ServerTSThreads**. This class establishes communication with each client through sockets. Threads are used to allow the program to operate more efficiently by doing multiple actions simultaneously, this way the server is able to handle multiple clients at once. 

The server of the TSapp program sends either the information of a Patient, a Doctor or data from the biosignals by implementing the **Serialization Interface**. Serialization allows us to convert these objects into a stream that we can send over the network to the client or save it as a file or store it in a database. Serialization saves time, as deserialization requires less time to create an object than an actual object created from a class.

The **SeverTSThreads** class creates a ServerSocket, a class that provides a system-independent implementation of the server side of a client/server socket connection. This ServerSocket will be constantly waiting on the default port (port 9000), if the server successfully binds to this port, then the ServerSocket is successfully created, and the server continues to the connection with the client. The accept method waits until a client requests a connection on the host and port of the server. When a client is accepted a new thread is created, the ClientThread class will run, creating an active socket. When a client disconnects, the resources will be released, and the thread will terminate. 

**SERVER PROGRAM TERMINATION**

The server will finish every process whenever the server introduces an ‘X’. This option will be always available. When the server stops the program every client that was connected will have to close the program, as the following action the client makes that requires a connection with the server will fail. It releases the resources, including the ServerSocket, terminating the execution of the program.
