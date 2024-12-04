package client.main;

import client.main.Game.GameID;

public class MainClient {

	// ADDITIONAL TIPS ON THIS MATTER ARE GIVEN THROUGHOUT THE TUTORIAL SESSION!

	/*
	 * Below, you can find an example of how to use both required HTTP operations,
	 * i.e., POST and GET to communicate with the server.
	 * 
	 * Note, this is only an example. Hence, your own implementation should NOT
	 * place all the logic in a single main method!
	 * 
	 * Further, I would recommend that you check out: a) The JavaDoc of the network
	 * message library, which describes all messages, and their ctors/methods. You
	 * can find it here http://swe1.wst.univie.ac.at/ b) The informal network
	 * documentation is given in Moodle, which describes which messages must be used
	 * when and how.
	 */
	public static void main(String[] args) {
		/*
		 * IMPORTANT: Parsing/Handling of starting parameters.
		 * 
		 * args[0] = Game Mode, you Can use this to know that your code is running on
		 * the evaluation server (if this is the case args[0] = TR). If this is the
		 * case, only a command line interface must be displayed. Also, no JavaFX and
		 * Swing UI components and classes must be used/executed by your Client in any
		 * way IF args[0] = TR.
		 * 
		 * args[1] = Server URL, will hold the server URL your Client should use. Note,
		 * only use the server URL supplied here as the URL used by you during the
		 * development and by the evaluation server (for grading) is NOT the same!
		 * args[1] enables your Client always to get the correct one.
		 * 
		 * args[2] = Holds the game ID which your Client should use. For testing
		 * purposes, you can create a new one by accessing
		 * http://swe1.wst.univie.ac.at:18235/games with your web browser. IMPORTANT: If
		 * a value is stored in args[2], you MUST use it! DO NOT create new games in
		 * your code in such a case!
		 * 
		 * DON'T FORGET TO EVALUATE YOUR FINAL IMPLEMENTATION WITH OUR TEST SERVER. THIS
		 * IS ALSO THE BASE FOR GRADING. THE TEST SERVER CAN BE FOUND AT:
		 * http://swe1.wst.univie.ac.at/
		 * 
		 * HINT: The assignment section in Moodle also explains all the important
		 * aspects about the start parameters/arguments. Use the Run Configurations (as
		 * shown during the IDE Screencast) in Eclipse to simulate the starting of an
		 * application with start parameters or implement your argument parsing code to
		 * become more flexible (e.g., to mix hardcoded and supplied parameters whenever
		 * the one or the other is available).
		 */

		// parse the parameters, otherwise the automatic evaluation will not work on
		// http://swe1.wst.univie.ac.at
		String serverBaseUrl = args[1];
		GameID gameID = new GameID(args[2]);

		//String serverBaseUrl ="http://swe1.wst.univie.ac.at:18235";//args[1]
		// args[2];
		//GameID gameID = new GameID("aYNtE");
		Communication communication = new Communication(gameID, serverBaseUrl);
		Manager manager = new Manager(communication);
		manager.startGame();
	}
}
