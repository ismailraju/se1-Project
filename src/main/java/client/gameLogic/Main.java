package client.gameLogic;

import MessagesBase.MessagesFromClient.ERequestState;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.MessagesFromServer.GameState;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Main {

    // ADDITIONAL TIPS ON THIS MATTER ARE GIVEN THROUGHOUT THE TUTORIAL SESSION!

    public static String baseUrl = "UseValueFromARGS_1 FROM main";
    public static String gameId = "UseValueFromARGS_2 FROM main";
    public static String playerId = "From the client registration";
    public static String gameMode = "";

    public static void main(String[] args) throws Exception {
        gameMode=args[0];
        baseUrl=args[1];
        gameId=args[2];


//        String serverBaseUrl = "http://swe1.wst.univie.ac.at:18235/";


//        String gameId = "vuW8j";


        Network network=new Network(baseUrl,gameId);
        WebClient baseWebClient = network.getBaseWebClient(baseUrl);

        PlayerRegistration playerReg = new PlayerRegistration("Arsen", "Keshishyan",
                "keshishyaa92");
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/players")
                .body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        // WebClient support asynchronous message exchange. In SE1 we use a synchronous
        // one for the sake of simplicity. So calling block is fine.
        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

        // always check for errors, and if some are reported, at least print them to the
        // console (logging should always be preferred!)
        // so that you become aware of them during debugging! The provided server gives
        // you constructive error messages.
        if (resultReg.getState() == ERequestState.Error) {
            // typically happens if you forgot to create a new game before the client
            // execution or
            // forgot to adapt the run configuration so that it supplies the id of the new
            // game to the client
            // open http://swe1.wst.univie.ac.at:18235/games in your browser to create a new
            // game and obtain its game id
            System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
        } else {
            UniquePlayerIdentifier uniqueID = resultReg.getData().get();
            System.out.println("My Player ID: " + uniqueID.getUniquePlayerID());
//2583e877-131c-4994-a07e-6032aa570be8
            exampleForGetRequests(baseUrl,
                    gameId,
                    uniqueID.getUniquePlayerID());
        }

        /*
         * TIP: Check out the network protocol documentation. It shows you with a nice
         * sequence diagram all the steps which are required to be executed by your
         * client along with a general overview on the required behavior (e.g., when it
         * is necessary to repeatedly ask the server for its state to determine if
         * actions can be sent or not). When the client will need to wait for the other
         * client and when you client should stop sending any more messages to the
         * server.
         */

        /*
         * TIP: A game consists of two clients. How can I get two clients for testing
         * purposes? Start your client two times. You can do this in Eclipse by hitting
         * the green start button twice. Or you can start your jar file twice in two
         * different terminals. When you hit the debug button twice, you can even debug
         * both clients "independently" from each other.
         */

        /*
         * TIP: To ease debugging and development, you can create special games. Such
         * games can get assigned a dummy competitor, or you can stop and debug them
         * without violating the maximum turn time limit. Check out the network protocol
         * documentation for details on how to do so.
         */
    }

    /*
     * This example method shall show you how to create a GET request. Here, it
     * shows you how to use a GET request to request the state of a game. You can
     * define all GET requests accordingly.
     *
     * The only reason to use GET requests in the Client should be to request
     * states. We strongly advise NOT to use the Client to create new games
     * programmatically. This is because multiple students before you failed to
     * integrate this properly into their Client logic - and subsequently struggled
     * with the automatic evaluation.
     */
    public static void exampleForGetRequests(
            String baseUrl,
            String gameId,
            String playerId
    ) throws Exception {
        // you will need to fill the variables with the appropriate information
//        String baseUrl = "UseValueFromARGS_1 FROM main";
//        String gameId = "UseValueFromARGS_2 FROM main";
//        String playerId = "From the client registration";

        // TIP: Use a global instance of the base WebClient throughout each
        // communication
        // you can init it once in the CTOR and use it in each of the network
        // communication methods in your networking class
        WebClient baseWebClient = WebClient.builder().baseUrl(baseUrl + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                // XML
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class); // specify the
        // object
        // returned
        // by the
        // server

        // WebClient support asynchronous message exchange. In SE1 we use a synchronous
        // one for the sake of simplicity. So calling block is fine.
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        // always check for errors, and if some are reported, at least print them to the
        // console (logging should always be preferred!)
        // so that you become aware of them during debugging! The provided server gives
        // you constructive error messages.
        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
        } else {
            GameState uniqueID = requestResult.getData().get();
            //f7c84e38-0973-4e15-bef1-30426ffc2586
            System.out.println("My GameState ID: " + uniqueID.getGameStateId());


        }
    }
}
