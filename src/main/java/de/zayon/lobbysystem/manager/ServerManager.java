package de.zayon.lobbysystem.manager;

import de.zayon.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerManager {

	private final LobbySystem lobbySystem;
	public ServerManager(LobbySystem lobbySystem) {
		this.lobbySystem = lobbySystem;
	}

	public void send(Player p, String server){
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try{
			out.writeUTF("Connect");
			out.writeUTF(server);
		}catch(IOException e){

		}
		p.sendPluginMessage(this.lobbySystem, "BungeeCord", b.toByteArray());
	}
	
}
