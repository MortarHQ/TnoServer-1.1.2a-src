package net.blay09.mods.waystones.api.requirement;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface WarpRequirement {
   boolean canAfford(Player var1);

   void consume(Player var1);

   void rollback(Player var1);

   void appendHoverText(Player var1, List<Component> var2);

   default boolean isEmpty() {
      return false;
   }
}
