# CryptoTrader
A simple website for trading Bitcoin and Ethereum using React.js and JAX-RS. Developed as part of the assesment at Chainalysis.


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
