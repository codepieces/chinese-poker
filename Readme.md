# Chinese Poker Score Calculator
The task for this programming exercise is to implement a spring boot web application to calculate Chinese Poker score.

Chinese Poker rules and scoring can be found at <https://en.wikipedia.org/wiki/Chinese_poker>.

## Summary of the game:
Up to 4 players can play. At the beginning of each round, each player is dealt 13 cards. Each player needs to arrange his 13 cards into a stack of 3 cards at the front, 5 cards in the middle, and 5 cards at the back. The cards at the back must form a higher rank than the cards in the middle, and the cards in the middle must form a higher rank than the cards at the front. The ranks follow poker rules:

1. Royal Flush (highest rank). Eg, 10 Spades, J Spades, Q Spades, K Spades, A Spades.
2. Straight Flush. Eg, 7 Diamonds, 8 Diamonds, 9 Diamonds, 10 Diamonds, J Diamonds.
3. Four of a Kind. Eg, K Diamonds, K Hearts, K Clubs, K Spades, 2 Diamonds.
4. Full House. Eg, 6 Hearts, 6 Diamonds, 6 Clubs, 2 Spades, 2 Clubs
5. Flush. Eg, 3 Spades, 5 Spades, 8 Spades, J Spades, A spades.
6. Straight. Eg, A Hearts, 2 Diamonds, 3 Spades, 4 Diamonds, 5 Clubs.
7. Three of a Kind. Eg, 4 Hearts, 4 Spades, 4 Clubs, Q Hearts, 2 Hearts.
8. Two pairs. Eg, 10 Spades, 10 Clubs, 2 Diamonds, 2 Clubs, K Hearts.
9. Pair. Eg, 8 Diamonds, 8 Clubs, 2 Diamonds, 7 Spades, A Spades.
10. High Cards (lowest rank). Eg, 2 Hearts, 4 Spades, 10 Spades, Q Spades, A Spades.

The front cards can only form Three of a Kind, Pair & High Cards.

When all players have arranged their cards, score is calculated using 1-6 method (see Scoring section in Wikipedia). Example:

- North Player:
    - K Clubs, 10 Spades, 2 Clubs
    - 9 Hearts, 8 Diamonds, 7 Hearts, 6 Diamonds, 5 Diamonds
    - Q Diamonds, J Spades, 10 Clubs, 9 Diamonds, 8 Clubs
- East Player:
    - Q Hearts, Q Clubs, 9 Clubs
    - 8 Spades, 8 Hearts, 3 Spades, 3 Diamonds, 5 Spades
    - J Hearts, J Clubs, 2 Hearts, 2 Diamonds, 4 Clubs
- South Player:
    - J Diamonds, K Hearts, K Spades
    - 6 Clubs, 9 Spades, 10 Diamonds, 10 Hearts, Q Spades
    - 2 Spades, 3 Clubs, 4 Diamonds, 5 Clubs, 6 Spades
- West Player:
    - K Diamonds, 6 Hearts, 5 Hearts
    - 7 Spades, 7 Diamonds, 7 Clubs, 4 Spades, 4 Hearts
    - A Spades, A Hearts, A Diamonds, A Clubs, 3 Hearts

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

- West Player: 2 Spades, 6 Diamonds, 8 Clubs, 10 Diamonds, K Spades
- North Player: 2 Diamonds, 6 Clubs, 8 Hearts, 10 Diamonds, K Clubs

The suits of the highest card (K) will be compared. In this case West Player wins, since his suit (Spades) is higher than North Player's suit (Clubs). The rankings of suits are Spades, Hearts, Clubs, Diamonds. Spades is the highest suit and diamonds is the lowest suit.

When comparing 2 sets fo Pair cards, and the pairs happen to be of the same numbers, the highest card that accompanies the pair will be compared. Eg:

- South Player: 4 Spades, 7 Diamonds, 9 Spades, Q Hearts, Q Clubs
- North Player: 2 Diamonds, 5 Clubs, 10 Diamonds, Q Spades, Q Diamonds

Both players have pair of Queens. In this case North Player wins, since the highest card accompanying his pair is a 10, whereas the highest card accompanying South Player's pair is a 9.

In rare occasion where the cards have the same numbers, the suits of the pairs will be compared. Eg:

- South Player: 4 Spades, 7 Diamonds, 9 Spades, Q Hearts, Q Clubs
- North Player: 4 Clubs, 7 Hearts, 9 Clubs, Q Diamonds, Q Spades.

The rules to compare Pairs above are also applicable to comparing Two Pairs.

A start up gradle Java project has been provided in this repo that can be used to complete the programming exercise. To complete the exercises below, please do the followings:

- Install JDK and development environment of choice
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

7. (Optional) Implement a simulator to simulate 4 players playing the game where the system will randomly allocate the cards, automatically arrange the cards and calculate the score.