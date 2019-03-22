# Homework 4
## By Unaiza Faiz (651052450)

## Steps to run the program
1. Clone the repository to your local machine using 
``git clone git@bitbucket.org:unaizafaiz/unaiza_faiz_hw4.git ``
2. In the cloned repository open/go to the folder chessengine-restapi
3. Run the chessengine rest service by using the following command
``./gradlew clean build && java -jar build/libs/chess-game-service-0.1.0.jar``

OR using the capstan file for OSv
    `capstan run`
    
OR using docker container
    `docker run` ???
    
4. Once the server is up and running then cd to chess-mediator project in a new terminal
`cd path_to_unaiza_faiz_hw4/chess-mediator`
5. Build and run the test app by running the following command
`./gradlew clean build && java -jar build/libs/chess-meditor.jar` ????
6. OR start your own session and play the game by passing moves using the REST API services as described below

##ChessEngine REST API services 

The REST API provides 4 services to play the game

- /newgame
    - Create a new game session for a user. 
    - Takes json as input with attribute playerName - enter the player name and isFirsMove = true/false 
      Eg.
        {
          "playerName": "player1",
          "isFirstMove":true
         }
    - Using curl example
     `curl -X POST localhost:8080/newgame -H 'Content-type:application/json' -d '{"playerName": "playerName", "isFirstMove": true}'`

- /move
    - Make a move for the user. 
    - Takes json as input with attributes sessionid for the user, move from square value, and move to square value in algebric notaions
    of the for 'a8' for row a and column 8.
      Eg.
        {
        "sessionid": 0,
        "squareone":"g8" ,
        "squaretwo": "h6"
        }
    - Using curl example
     ` curl -X POST localhost:8080/move -H 'Content-type:application/json' -d '{"sessionid": 0,"squareone":"g8" ,"squaretwo": "h6"}'
`

- /getsessions
    - Gets list of all sessions as a String JSON 
    - Curl example:
    `curl localhost:8080/getsessions`
    
- /getallmoves/{sessionid}
    -Get all the moves made in a particular game (by both the players in game)
    - Curl example
        `curl localhost:8080/getallmoves/1`
        
- /endgame&sessionid={sessionid}
    - End a user session 
    - Using curl example
         ` curl -X DELETE localhost:8080/endgame&sessionid=1'`
    

## Overview 

Assignment project structure

- chess-engine: Wrapper for the javaopenchess project. Extracts the methods calls from the GUI, to be used locally in rest API service
- chessengine-restapi: Contains the REST API implementation of chess-engine. 
- chess-mediator: project to test the chessengine-restapi. Creates two instances of chessengine using the REST APIs and makes them play against each other by passing moves between the two players.

## OSv Image and Docker Container on AWS

The chessengine-restapi OSv image and docker contain was deployed to AWS EC2. 
Please find the video of the same here:                               
    ???        [Amazon EMR-Youtube](https://youtu.be/T5GnfcQZaTU) 
 