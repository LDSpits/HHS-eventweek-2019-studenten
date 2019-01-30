package nl.quintor.solitaire.game;

import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
import nl.quintor.solitaire.models.state.GameState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Library class for GameState initiation and status checks that are called from {@link nl.quintor.solitaire.Main}.
 * The class is not instantiable, all constructors are private and all methods are static.
 */
public class GameStateController {
    private GameStateController(){}

    /**
     * Creates and initializes a new GameState object. The newly created GameState is populated with shuffled cards. The
     * stack pile and column maps are filled with headers and Deck objects. The column decks have an appropriate number
     * of invisible cards set.
     *
     * @return a new GameState object, ready to go
     */
    public static GameState init(){
        GameState state = new GameState();

        // Create a new default deck, shuffle and assign the deck to the GameState
        Deck defaultDeck = Deck.createDefaultDeck();
        Collections.shuffle(defaultDeck);

        for (int i = 0; i < 24; i++){ //Loop to fill up stockpile with 28 cards
            Card stockCard = defaultDeck.get(0);
            state.getStock().add(stockCard);
            defaultDeck.remove(0); //Remove card from defaultdeck because it is now part of the stockpile
        }

        //TODO add remaining cards to (new) columndecks; then add new decks to map with stringkeys, such as: "column 1", etc

        Map<String, Deck> columns = state.getColumns();

        for (int i = 0; i < 7; i++){

            Deck aColumn = new Deck(DeckType.COLUMN);
            aColumn.addAll(defaultDeck.subList(0,i+1));
            defaultDeck.removeAll(defaultDeck.subList(0,i+1));

            String key = "Column " + Integer.toString(i+1);

            columns.put(key, aColumn);

        }



        // TODO: Write implementation
        return state;
    }

    /**
     * Applies a score penalty to the provided GameState object based on the amount of time passed.
     * The following formula is applied : "duration of game in seconds" / 10 * -2
     *
     * @param gameState GameState object that the score penalty is applied to
     */
    public static void applyTimePenalty(GameState gameState){
        long duration = gameState.getEndTime().getSecond() - gameState.getStartTime().getSecond();
        gameState.setTimeScore((duration / 10) * -2);
    }

    /**
     * Applies a score bonus to the provided GameState object based on the amount of time passed. Assumes the game is won.
     * When the duration of the game is more than 30 seconds then apply : 700000 / "duration of game in seconds"
     *
     * @param gameState GameState object that the score penalty is applied to
     */
    public static void applyBonusScore(GameState gameState){
        long gameDuration = gameState.getEndTime().getSecond() - gameState.getStartTime().getSecond();
        if(gameDuration > 30)
            gameState.setTimeScore(700000 / gameDuration);
    }

    /**
     * Detects if the game has been won, and if so, sets the gameWon flag in the GameState object.
     * The game is considered won if there are no invisible cards left in the GameState object's columns and the stock
     * is empty.
     *
     * @param gameState GameState object of which it is determined if the game has been won
     */
    public static void detectGameWin(GameState gameState){
        // TODO: Write implementation
    }
}
