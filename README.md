# CardWarGame

The project focuses on the implementation of an application presenting a card war game. The application allows two players to play on two different views in the application. The mentioned gameplay is possible through the use of network communication and multithreading. It takes place on the server to which clients connect. In order to connect to the server, it must be started in another process. In this context, a client is an application that sends queries or requests to a server, waiting for a response. The server, on the other hand, is the application that receives these requests, processes them and responds to clients. The server provides ongoing information about its status and players joining the game. Thanks to this, you can monitor the operating status of the server. The GameHandler class in the project allows you to handle the logic of the card war game. At the same time, it implements the Runnable interface to support multithreading. Players' applications communicate via network sockets.
  
----------------------------------------

**Technologies used in the project**
- Network communication protocol: TCP
- Backend language: Java
- Window application framework: JavaFX
- Frontend technology: FXML
- Communication with the database: JDBC
- Database management system: MySQL

----------------------------------------

**Features of the project**

- Viewing player statistics recently
- Gameplay for both players

----------------------------------------

**Sample photos showing how the application works**

*Start window:*
![Start window](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/1.png)

*Statistics window for recent games:*
![Statistics window for recent games](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/2.png)

*Game window - starting view:*
![Game window - starting view](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/3.png)

*Game window - the final view of the game:*
![Game window - the final view of the game](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/4.png)

----------------------------------------

