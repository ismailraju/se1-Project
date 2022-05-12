package client.gameLogic;

import MessagesBase.MessagesFromClient.ERequestState;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.MessagesFromServer.EPlayerGameState;
import MessagesBase.MessagesFromServer.GameState;
import MessagesBase.MessagesFromServer.PlayerState;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Main {

    // ADDITIONAL TIPS ON THIS MATTER ARE GIVEN THROUGHOUT THE TUTORIAL SESSION!

    public static String baseUrl = "UseValueFromARGS_1 FROM main";
    public static String gameId = "UseValueFromARGS_2 FROM main";
    public static String playerId = "From the client registration";
    public static String gameMode = "";
//    http://swe1.wst.univie.ac.at:18235/games?enableDummyCompetition=true
    public static void main(String[] args) throws Exception {
        gameMode = args[0];
        baseUrl = args[1];
        gameId = args[2];


        Network network = new Network(baseUrl, gameId);

        //player register
        Player player = network.registerPlayer(new GameID(gameId), "Arsen", "Keshishyan",
                "keshishyaa92", "");

        playerId = player.getPlayerId().getiD();

        //wait calling game status [loop]
        GameState gameState = network.getGameState(new GameID(gameId), new PlayerID(playerId), "");
        while ( gameState==null || gameState.getPlayers().size() != 2) {
            TimeUnit.SECONDS.sleep(10);
            gameState = network.getGameState(new GameID(gameId), new PlayerID(playerId), "");
        }


        //send halfMap
        network.sendHalfMap(new PlayerID(playerId), Map.generateMap(), "");
        //
        EPlayerGameState state = getePlayerGameState(network);

        while (!(state.equals(EPlayerGameState.Won) || (state.equals(EPlayerGameState.Lost)))) {

            //wait for client 2
            state = getePlayerGameState(network);
            while (state.equals(EPlayerGameState.MustWait)) {

                state = getePlayerGameState(network);
            }

            //send movement
            if (state.equals(EPlayerGameState.MustAct)) {

                network.sendMove(new PlayerID(playerId), Move.Right, "");
            }


            state = getePlayerGameState(network);
        }


    }

    private static EPlayerGameState getePlayerGameState(Network network) {
        GameState gameState;
        gameState = network.getGameState(new GameID(gameId), new PlayerID(playerId), "");
        Optional<PlayerState> playerState = gameState.getPlayers().stream().filter(p -> p.getUniquePlayerID().equals(playerId)).findAny();
        return playerState.get().getState();
    }
}
