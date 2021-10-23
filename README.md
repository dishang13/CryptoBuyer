# CryptoTrader
A simple website for trading Bitcoin and Ethereum using React.js and JAX-RS. Developed as part of the assesment at Chainalysis.
## Live Version - http://35.170.62.236:8080/CryptoTrader/


## Questionnaire
1. Are there any sub-optimal choices( or short cuts taken due to limited time ) in your implementation?\
Calling Exchange APIs to get currency data uses a 3rd-party JSON package to store the responses. Ideally I would create a class for the each of the API response types.

2. Is any part of it over-designed? ( It is fine to over-design to showcase your skills as long as you are clear about it)]\
The only part I can think of was how the combined exchange information is sent back to the client. It sends the information grouped by exchange and the client breaks/inverts it so that it is grouped by currencies. I could have showed it as it is grouped by exchanges but that would not make any sense when viewing it from a user perspective as you are comparing exchanges for the same currency.

3. If you have to scale your solution to 100 users/second traffic what changes would you make, if any?\
I would try to implement some form of caching for the API services, so that if 100 users call the server, the server sends out only one API call to each exchange. There are also other options of having horizontal and vertical scaling, moving API calls to client side so that client itself can call the exchanges and get data directly without having it to route through the server.

4. What are some other enhancements you would have made, if you had more time to do this implementation\
    - Use websockets to show real-time data.
    - Allow the user to select what currency they wanted to see the prices in (USD, EUR, INR, etc.)

## Build and Run Instructions
#### Software Requirements
1. IntelliJ IDEA Ultimate - https://www.jetbrains.com/student/ I am using IntelliJ IDEA Ultimate 2021.1.3
2. Java 11 SDK - https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html. Use the latest version that your system supports. OpenJDK and Java 13 should also be fine.
3. Tomcat 9 - https://tomcat.apache.org/download-90.cgi I am using Tomcat 9. Download the core binary distribution. If you have a Mac, download the tar.gz file. If you have a Windows machine, download the 64-bit Windows zip file. To install Tomcat, simply put the archive file in your working directory and unzip it.\

#### Build and Run
1. Download/Clone this Repository to a folder on your PC.
2. Open the repository folder in IntelliJ IDEA as a project. It will take some time for it to load and perform its setup.
3. Add a new run configuration by clicking the "Add Configuration" button in the top right 
4. Click on the "+" icon.
5. Select Tomcat Local Server.
![image](https://user-images.githubusercontent.com/26704547/138542233-974cd53e-f9d3-4cb6-8d41-32972d7877f2.png)
6. Under Application Server, choose the folder where you extracted the Tomcat server.
7. Click on Fix
![image](https://user-images.githubusercontent.com/26704547/138542427-71117156-439f-4bf4-abed-88c932a14e0a.png)

8. Select "Gradle : CryptoTrader : server.war"
![image](https://user-images.githubusercontent.com/26704547/138542514-097c677f-9273-4e06-ad54-5e3ce26283c5.png)

9. Scroll down and update Application Context to "/CryptoTrader"
![image](https://user-images.githubusercontent.com/26704547/138542586-7b71ceae-58e1-47ae-a5ed-4a9e9eb02328.png)

10. Click Apply and Ok
11. Hit the run button (Green Play icon). The server should build the app and open the browser automatically once done.
![image](https://user-images.githubusercontent.com/26704547/138542653-d5216a54-cc92-4010-8706-a579fd58a480.png)
