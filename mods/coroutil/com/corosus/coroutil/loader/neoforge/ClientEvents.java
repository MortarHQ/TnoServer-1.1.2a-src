package com.corosus.coroutil.loader.neoforge;

import com.corosus.coroutil.command.CommandCoroConfigClient;
import com.corosus.modconfig.CoroConfigRegistry;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

public class ClientEvents {
   public void onGameTick(Post event) {
      CoroConfigRegistry.instance().allModsConfigsLoadedAndRegisteredHook();
   }

   public void onRegisterCommandsClient(RegisterClientCommandsEvent event) {
      CommandCoroConfigClient.register(event.getDispatcher());
   }
}
