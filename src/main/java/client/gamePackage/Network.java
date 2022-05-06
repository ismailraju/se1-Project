package client.gamePackage;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.ResponseEnvelope;
import MessagesBase.MessagesFromClient.ERequestState;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.MessagesFromServer.GameState;
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

    WebClient baseWebClient;

    public Network() {
        baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                // XML
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

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
        baseWebClient = WebClient.builder().baseUrl(url + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                // XML
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

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


        baseWebClient = WebClient.builder().baseUrl(url + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                // XML
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

        PlayerRegistration playerReg = new PlayerRegistration(name, surname, uaccount);
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID.getiD() + "/players")
                .body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        ResponseEnvelope<Player> requestResult = webAccess.block();


        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }
        Optional<Player> pp = requestResult.getData();
        return pp.isPresent() ? pp.get() : null;
    }

    public void sendHalfMap(PlayerID playerID, Map map, String url) {

    }

    public void sendMove(PlayerID playerID, Move move, String url) {

    }

    public GameState getGameState(GameID gameID, PlayerID playerID, String url) {

        WebClient baseWebClient = WebClient.builder().baseUrl(url + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                // XML
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameID.getiD() + "/states/" + playerID.getiD())
                .retrieve().bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<GameState> requestResult = webAccess.block();

        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        }

        Optional<GameState> pp = requestResult.getData();
        return pp.isPresent() ? pp.get() : null;
    }

}
