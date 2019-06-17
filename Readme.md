# Chinese Poker Score Calculator
The task for this programming exercise is to implement a spring boot web application to calculate Chinese Poker score.

Chinese Poker rules and scoring can be found at <https://en.wikipedia.org/wiki/Chinese_poker>.

## Summary of the game:
Up to 4 players can play. At the beginning of each round, each player is dealt 13 cards. Each player needs to arrange his 13 cards into a stack of 3 cards at the front, 5 cards in the middle, and 5 cards at the back. The cards at the back must form a higher rank than the cards in the middle, and the cards in the middle must form a higher rank than the cards at the front. The ranks follow poker rules:

1. Royal Flush (highest rank). Eg, 10 :spades:, J :spades:, Q :spades:, K :spades:, A :spades:.
2. Straight Flush. Eg, 7 :diamonds:, 8 :diamonds:, 9 :diamonds:, 10 :diamonds:, J :diamonds:.
3. Four of a Kind. Eg, K :diamonds:, K :hearts:, K :clubs, K :spades:, 2 :diamonds:.
4. Full House. Eg, 6 :hearts:, 6 :diamonds:, 6 :clubs:, 2 :spades:, 2 :clubs:
5. Flush. Eg, 3 :spades:, 5 :spades:, 8 :spades:, J :spades:, A :spades:.
6. Straight. Eg, A :hearts:, 2 :diamonds:, 3 :spades:, 4 :diamonds:, 5 :clubs:.
7. Three of a Kind. Eg, 4 :hearts:, 4 :spades:, 4 :clubs:, Q :hearts:, 2 :hearts:.
8. Two pairs. Eg, 10 :spades:, 10 :clubs:, 2 :diamonds:, 2 :clubs:, K :hearts:.
9. Pair. Eg, 8 :diamonds:, 8 :clubs:, 2 :diamonds:, 7 :spades:, A :spades:.
10. High Cards (lowest rank). Eg, 2 :hearts:, 4 :spades:, 10 :spades:, Q :spades:, A :spades:.

The front cards can only form Three of a Kind, Pair & High Cards.

When all players have arranged their cards, score is calculated using 1-6 method (see Scoring section in Wikipedia). Example:

- North Player:
    - K :clubs:, 10 :spades:, 2 :clubs:
    - 9 :hearts:, 8 :diamonds:, 7 :hearts:, 6 :diamonds:, 5 :diamonds:
    - Q :diamonds:, J :spades:, 10 :clubs:, 9 :diamonds:, 8 :clubs:
- East Player:
    - Q :hearts:, Q :clubs:, 9 :clubs:
    - 8 :spades:, 8 :hearts:, 3 :spades:, 3 :diamonds:, 5 :spades:
    - J :hearts:, J :clubs:, 2 :hearts:, 2 :diamonds:, 4 :clubs:
- South Player:
    - J :diamonds:, K :hearts:, K :spades:
    - 6 :clubs:, 9 :spades:, 10 :diamonds:, 10 :hearts:, Q :spades:
    - 2 :spades:, 3 :clubs:, 4 :diamonds:, 5 :clubs:, 6 :spades:
- West Player:
    - K :diamonds:, 6 :hearts:, 5 :hearts:
    - 7 :spades:, 7 :diamonds:, 7 :clubs:, 4 :spades:, 4 :hearts:
    - A :spades:, A :hearts:, A :diamonds:, A :clubs:, 3 :hearts:

||North Player|East Player|South Player|West Player|Score|
|-|-|-|-|-|-:|
|North Player||L-W-W|South Mis-set|W-L(R)-L(R)|-2|
|East Player|W-L-L||South Mis-set|W-L(R)-L(R)|-4|
|South Player|South Mis-set|South Mis-set||South Mis-set|-34|
|West Player|L-W(R)-W(R)|L-W(R)-W(R)|South Mis-set||40|
|Total|||||0|

L = Lose  
W = Win  
(R) = Lose or Win with Royalty

South Player mis-set his cards since his front cards contain pair of Kings, which is higher than his middle cards, which only contains pair of Tens.

## Sample calculation:
North Player vs East Player: North Player lose front cards, wins middle and back cards. In total he wins 1 unit against East Player.

North Player vs South Player: South Player mis-set his cards, so it's as good as getting scooped (loses all front, middle and back). Scooped player loses extra units, usually the same amount of units he loses. So South Player loses 3 units (front, middle and back), and loses additional 3 units (because he gets scooped). In total North Player wins 6 units against South Player.

North Player vs West Player: North Player wins front cards, loses middle and back cards against West Player. Note that West Player has royalties in his middle and back cards (see Royalties section in Wikipedia). So North Player gets 1 + (-2) + (-8) units against West Player. In other words, he loses 9 units to West Player.

Royalties are taken into consideration when calculation scoop. Eg, South Player is considered to lose front (1 unit), middle (2 units) and back (8 units) to West Player. Since he's scooped, in total he loses 2 * (1 + 2 + 8) = 22 units to West Player.

Naturals are also taken into consideration when calculating scores (see Naturals section in Wikipedia). When one player has Naturals and another player has Royalties, the player with Naturals wins.

For simplicity, there's no option for a player to surrender (see Surrendering action in Wikipedia).

When comparing 2 sets of high cards with exactly the same numbers, the suits of the highest card will be compared to determine the winner. Eg:

- West Player: 2 :spades:, 6 :diamonds:, 8 :clubs:, 10 :diamonds:, K :spades:
- North Player: 2 :diamonds:, 6 :clubs:, 8 :hearts:, 10 :diamonds:, K :clubs:

The suits of the highest card (K) will be compared. In this case West Player wins, since his suit (Spades) is higher than North Player's suit (Clubs). The rankings of suits are :spades: (Spades), :hearts: (Hearts), :clubs: (Clubs), :diamonds: (Diamonds). :spades: is the highest suit and :diamonds: is the lowest suit.

When comparing 2 sets fo Pair cards, and the pairs happen to be of the same numbers, the highest card that accompanies the pair will be compared. Eg:

- South Player: 4 :spades:, 7 :diamonds:, 9 :spades:, Q :hearts:, Q :clubs:
- North Player: 2 :diamonds:, 5 :clubs:, 10 :diamonds:, Q :spades:, Q :diamonds:

Both players have pair of Queens. In this case North Player wins, since the highest card accompanying his pair is a 10, whereas the highest card accompanying South Player's pair is a 9.

In rare occasion where the cards have the same numbers, the suits of the pairs will be compared. Eg:

- South Player: 4 :spades:, 7 :diamonds:, 9 :spades:, Q :hearts:, Q :clubs:
- North Player: 4 :clubs:, 7 :hearts:, 9 :clubs:, Q :diamonds:, Q :spades:.

The rules to compare Pairs above are also applicable to comparing Two Pairs.

The exercises below consists of 5 Java programming exercises + 1 optional Web UI exercise. As the aim is to assess your Java 8 knowledge and skills, question 6 has been made optional. If you're feel challenged and would like to showcase your skills as full stack developer, feel free attempt question 6 after you've completed questions 1 - 5. You're encouraged to use as many Java 8 features as possible where it makes sense. Though take care to balance readibility and maintainability when you're using lambda and Streams.  

A start up gradle Java project has been provided in this repo that can be used to complete the programming exercise. To complete the exercises below, please do the followings:

- Install JDK 8 and development environment of your choice
- Clone this repo
- Build and run the app by executing `gradlew bootRun`

You can submit your solution either by uploading your completed solution to your github account or email as a zipped up packaged (please don't include any build artifacts).

To check your solution, we'll do the followings:

- Execute `gradlew clean`
- Execute `gradlew check`
- Execute `gradlew jacocoTestConverageVerification`
- Execute `gradlew bootRun`
- Go to http://localhost:8080/api/score?params=&lt;testdata&gt;
- Go to http://localhost:8080/ (if web client is provided as part of the solution)

Thus before you submit your solution, please make sure that the above steps can be completed successfully without any error.

## Exercises:
1. When you run the application using `gradlew bootRun`, spring boot application will be started. But when you go to <http://localhost:8080/> you'll get either page not found or error page. Your first task is to hook up HomeController.java, so that when you go to <http://localhost:8080/> getGreeting function in HomeController will be called and the text "Welcome to Chinese Poker Assignment." will be displayed on the browser.

2. Implement calcScore method in PokerCalculatorService to calculate the score according to the rule of the game above. Please demonstrate good design principles and understanding of best practices when implementing the function. Eg, avoid writing long function, make sure code is easy to read, have full test coverage, proper error handling, proper input validation, etc. Feel free to implement extra helper classes to support your solution.

3. Expose the calculate score functionality as a REST endpoint. The endpoint should be accessible from http://localhost:8080/api/score?params=&lt;testdata&gt;.
    
    testdata is the decks of cards for all players starting from North, East, South & West ordered from front, middle and back stacks separated by &semi; (semicolon).

4. Implement parser to parse the testdata input above to a List&lt;PlayerHand&gt; that can be passed in to the calcScore function you implement for question 2. Again, please demonstrate good design principles and understanding of best practices when implementing the parser.

    One of the testdata could be: KC;10S;2C;9H;8D;7H;6D;5D;QD;JS;10C;9D;8C;QH;QC;9C;8S;8H;3S;3D;5S;JH;JC;2H;2D;4C;JD;KH;KS;6C;9S;10D;10H;QS;2S;3C;4D;5C;6S;KD;6H;5H;7S;7D;7C;4S;4H;AS;AH;AD;AC;3H;.

5. Hook up the parser and CalculatorService to the endpoint so that the score can be returned to the browser as a list of JSON array containing the scores for each player.

    As a test, when we go to http://localhost:8080/api/score?params=KC;10S;2C;9H;8D;7H;6D;5D;QD;JS;10C;9D;8C;QH;QC;9C;8S;8H;3S;3D;5S;JH;JC;2H;2D;4C;JD;KH;KS;6C;9S;10D;10H;QS;2S;3C;4D;5C;6S;KD;6H;5H;7S;7D;7C;4S;4H;AS;AH;AD;AC;3H; the api should return [-2, -4, -34, 40].

6. (Optional) Implement a web user interface where the user can enter the deck of cards for each player and it'll display a table listing the score. Feel free to any JavaScript framework (eg. ReactJS, Angular, etc) or just plain HTML & JavaScript.