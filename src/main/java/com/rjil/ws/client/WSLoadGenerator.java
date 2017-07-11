package com.rjil.ws.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class WSLoadGenerator {

	private WSClient client;
	
	private static String webSocketAddress = "ws://localhost:3002/testWsSocket";
	
	private void initializeWebSocket() throws URISyntaxException {
		//ws://localhost:7101/CinemaMonitor/cinemaSocket/
		System.out.println("REST service: open websocket client at " + webSocketAddress);
		client = new WSClient(new URI(webSocketAddress + "/0"));
	}
	
	private void sendMessageOverSocket(String message) {
			try {
				initializeWebSocket();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		client.sendMessage(message);

	}
	
	public static void main(String[] args) throws ParseException {
		int numConcurrentConnections = 1000;
		Options options = new Options();
		//options.addOption("c", false, "# of concurrent connections");
		options.addOption("c", "connections", true, "# of concurrent connections.");
		options.addOption("a", "address", false, "websocket address.");
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
		
		if(cmd.hasOption("c")) {
			System.out.println(cmd.getOptionValue("c"));
			numConcurrentConnections = Integer.parseInt(cmd.getOptionValue("c"));
		}
		if(cmd.hasOption("a")) {
			System.out.println(cmd.getOptionValue("a"));
			webSocketAddress = cmd.getOptionValue("a");
		}
		
		WSLoadGenerator lg = new WSLoadGenerator();
		for(int i =0; i< numConcurrentConnections; i++){
			lg.sendMessageOverSocket(""+i);
		}
		
	}
}
