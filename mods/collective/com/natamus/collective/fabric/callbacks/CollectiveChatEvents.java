package com.natamus.collective.fabric.callbacks;

import com.mojang.datafixers.util.Pair;
import java.util.UUID;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_2556;
import net.minecraft.class_2561;
import net.minecraft.class_3222;

public class CollectiveChatEvents {
   public static final Event<CollectiveChatEvents.On_Client_Chat> CLIENT_CHAT_RECEIVED = EventFactory.createArrayBacked(
      CollectiveChatEvents.On_Client_Chat.class, callbacks -> (chatType, message, senderUUID) -> {
         for (CollectiveChatEvents.On_Client_Chat callback : callbacks) {
            class_2561 newMessage = callback.onClientChat(chatType, message, senderUUID);
            if (newMessage != message) {
               return newMessage;
            }
         }

         return message;
      }
   );
   public static final Event<CollectiveChatEvents.On_Server_Chat> SERVER_CHAT_RECEIVED = EventFactory.createArrayBacked(
      CollectiveChatEvents.On_Server_Chat.class, callbacks -> (serverPlayer, message, senderUUID) -> {
         for (CollectiveChatEvents.On_Server_Chat callback : callbacks) {
            Pair<Boolean, class_2561> pair = callback.onServerChat(serverPlayer, message, senderUUID);
            if (pair != null && (Boolean)pair.getFirst()) {
               return pair;
            }
         }

         return null;
      }
   );

   private CollectiveChatEvents() {
   }

   @FunctionalInterface
   public interface On_Client_Chat {
      class_2561 onClientChat(class_2556 var1, class_2561 var2, UUID var3);
   }

   @FunctionalInterface
   public interface On_Server_Chat {
      Pair<Boolean, class_2561> onServerChat(class_3222 var1, class_2561 var2, UUID var3);
   }
}
