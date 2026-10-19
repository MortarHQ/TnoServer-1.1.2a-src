package team.creative.creativecore.client;

import com.mojang.brigadier.CommandDispatcher;

public interface ClientLoader {
   void onInitializeClient();

   default <T> void registerClientCommands(CommandDispatcher<T> dispatcher) {
   }
}
