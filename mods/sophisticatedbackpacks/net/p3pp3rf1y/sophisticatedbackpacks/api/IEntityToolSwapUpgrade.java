package net.p3pp3rf1y.sophisticatedbackpacks.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IEntityToolSwapUpgrade {
   default boolean canProcessEntityInteract() {
      return true;
   }

   boolean onEntityInteract(Level var1, Entity var2, Player var3);
}
