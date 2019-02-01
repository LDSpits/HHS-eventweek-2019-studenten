package nl.quintor.solitaire.game;

import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
import nl.quintor.solitaire.models.state.GameState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
        Map<String, Deck> stacks = state.getStackPiles();

        String letters = "ABCDEFG";

        for (int i = 0; i < 7; i++){

            Deck aColumn = new Deck(DeckType.COLUMN);
            aColumn.addAll(defaultDeck.subList(0,i+1));
            defaultDeck.removeAll(defaultDeck.subList(0,i+1));
            aColumn.setInvisibleCards(i);

            String key = Character.toString(letters.charAt(i));

            columns.put(key, aColumn);

        }

        String [] stackSymbols = {"SA","SB", "SC", "SD" };

        for (int j=0; j <4; j++){
            Deck stackDeck = new Deck(DeckType.STACK);
            stackDeck.setInvisibleCards(0);
            stacks.put(stackSymbols[j], stackDeck);
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
        long duration = ChronoUnit.SECONDS.between(gameState.getStartTime(), gameState.getEndTime());
        gameState.setTimeScore((duration / 10) * -2);
    }

    /**
     * Applies a score bonus to the provided GameState object based on the amount of time passed. Assumes the game is won.
     * When the duration of the game is more than 30 seconds then apply : 700000 / "duration of game in seconds"
     *
     * @param gameState GameState object that the score penalty is applied to
     */
    public static void applyBonusScore(GameState gameState){
        long gameDuration = ChronoUnit.SECONDS.between(gameState.getStartTime(), gameState.getEndTime());
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
