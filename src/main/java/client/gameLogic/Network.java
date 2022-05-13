package client.gameLogic;

import java.util.Collection;
import java.util.Optional;

import MessagesBase.MessagesFromServer.GameState;
import MessagesBase.UniquePlayerIdentifier;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.ResponseEnvelope;
import MessagesBase.MessagesFromClient.*;
import MessagesBase.MessagesFromServer.*;
import reactor.core.publisher.Mono;

public class Network {

    // parse the parameters, otherwise the automatic evaluation will not work on
    // http://swe1.wst.univie.ac.at

    // private String gameId;

    String gameId = "Es2mn";

    private String serverBaseUrl;

    // template WebClient configuration, will be reused/customized for each
    // individual endpoint
    // TIP: create it once in the CTOR of your network class and subsequently use it
    // in each communication method
//	WebClient baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
//			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
//																						// XML
//			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

    public Network(String serverBaseUrl, String gameId) {
        this.gameId = gameId;
        this.serverBaseUrl = serverBaseUrl;
        getBaseWebClient(this.serverBaseUrl);
    }

    static WebClient baseWebClient = null;

    public static WebClient getBaseWebClient(String serverBaseUrl) {
        if (baseWebClient == null) {
            baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
        }
        return baseWebClient;
    }

    public static void setBaseWebClient(WebClient baseWebClient) {
        Network.baseWebClient = baseWebClient;
    }



    /*
     * Note, EACH client must only register a SINGLE player (i.e., you) ONCE! It is
     * OK, if you hard code your private data in your code. Here, this example shows
     * you how to perform a POST request (and a client registration), you can build
     * on this example to implement all the other messages which use POST. An
     * example of how to use GET requests is given below.
     *
     * Always give your real UniView u:account username (e.g., musterm44) during the
     * registration phase. Otherwise, the automatic progress tracking will not be
     * able to determine and assign related bonus points. No, we will not assign
     * them manually if you fail to do so.
     */
//	playerReg=new PlayerRegistration("Arsen","Keshishyan","keshishyaa92");
//	Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/players")
//					.body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
//					.retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server
//
//	---------------------------------

    public GameID createGame(String url) {


        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/")
                .retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameID> requestResult = webAccess.block();


        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }
        Optional<GameID> pp = requestResult.getData();
        return pp.isPresent() ? pp.get() : null;
    }

    public Player registerPlayer(GameID gameID, String name, String surname, String uaccount, String url) {

        PlayerRegistration playerReg = new PlayerRegistration(name, surname, uaccount);
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID.getiD() + "/players")
                .body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        ResponseEnvelope<UniquePlayerIdentifier> requestResult = webAccess.block();


        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }


        Optional<UniquePlayerIdentifier> pp = requestResult.getData();


        Player player = new Player(name, surname, uaccount, new PlayerID(pp.isPresent() ? pp.get().getUniquePlayerID() : null));
        System.out.println("Player (after registration):" + player.toString());

        return player;

    }

    public void sendHalfMap(PlayerID playerID, Map map, String url) {


    	
    	HalfMap hp=new HalfMap(playerID.getiD() ,Map.generateHalfMap());
    	 
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/halfmaps")
                .body(BodyInserters.fromValue(hp)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        ResponseEnvelope<Map> requestResult = webAccess.block();


        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }


    }

    public void sendMove(PlayerID playerID, Move move, String url) {
        PlayerMove playerMove = PlayerMove.of(new UniquePlayerIdentifier(playerID.getiD()), EMove.valueOf(move.name()));


        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/moves")
                .body(BodyInserters.fromValue(playerMove)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        ResponseEnvelope<Map> requestResult = webAccess.block();


        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }


    }

    public GameState getGameState(GameID gameID, PlayerID playerID, String url) {

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameID.getiD() + "/states/" + playerID.getiD())
                .retrieve().bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<MessagesBase.MessagesFromServer.GameState> requestResult = webAccess.block();

        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }

        Optional<MessagesBase.MessagesFromServer.GameState> pp = requestResult.getData();
        GameState gameState = pp.isPresent() ? pp.get() : null;
        Gson g=new Gson();
        System.out.println("GameState  :" +g.toJson(gameState)  );
        return gameState;
    }

}
