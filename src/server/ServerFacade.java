package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import model.Game;
import server.input.UserLoginInput;

/**
 * Created by Jesse on 5/26/2016. This Facade will be called upon by the commands and will be the only class to modify the model on the Server's side.
 */
public class ServerFacade {

	private static ServerFacade singleton = null;
	private Game model = null;

	public static ServerFacade getSingleton() {
		if (singleton == null) {
			singleton = new ServerFacade();
		}
		return singleton;
	}

	public Game getModel() {
		return this.model;
	}

	public ServerFacade() {
		String data;

		File file = new File("testmodel.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO IMPORT MODEL");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Scanner scanner = new Scanner(bufferedReader);
		data = scanner.useDelimiter("\\Z").next();
		scanner.close();

	}

	public Object UserLogin(Object input) {
		// TODO: This is where the magic happens
		UserLoginInput uli = (UserLoginInput) input;
		System.out.println(uli.getUsername());
		System.out.println(uli.getPassword());

		// TODO: Maybe make UserLoginOutput to return?

		return input;
	}

}
