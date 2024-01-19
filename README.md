# CardWarGame

The project focuses on the implementation of an application presenting a card war game. The application allows two players to play on two different views in the application. The mentioned gameplay is possible through the use of network communication and multithreading. It takes place on the server to which clients connect. In order to connect to the server, it must be started in another process. In this context, a client is an application that sends queries or requests to a server, waiting for a response. The server, on the other hand, is the application that receives these requests, processes them and responds to clients. The server provides ongoing information about its status and players joining the game. Thanks to this, you can monitor the operating status of the server. The GameHandler class in the project allows you to handle the logic of the card war game. At the same time, it implements the Runnable interface to support multithreading. Players' applications communicate via network sockets.

The project is also a window application written using JavaFX technology. The mentioned technology offers cross-platform functionality. It can also work with the FXML language, which, with the help of SceneBuilder, can modify the appearance of the window without using code. The application has access to a database in various windows where the results of the games are saved. The project was carried out using MYSQL, which enables cooperation with relational databases. The task also uses the SQL query language. JDBC also helped in communicating the database with the said application. Using these technologies, it was possible to insert the results of the games into the table and display these results in the statistics table window. The project also used TCP network technology. It offers certainty of data delivery and maintains the order of sent information, which is crucial in applications where communication reliability is a priority.

----------------------------------------

**Features of the project**

*Start window:*
![Start window](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/1.png)

*Statistics window for recent games:*
![Statistics window for recent games](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/2.png)

*Game window - starting view:*
![Game window - starting view](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/3.png)

*Game window - the final view of the game:*
![Game window - the final view of the game](https://github.com/jakubdziadkowiec17/CardWarGame/blob/master/photos/4.png)

----------------------------------------

