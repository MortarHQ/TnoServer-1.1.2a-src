package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.world.level.block.Block;

public record RegisterFlammabilityEvent(RegisterFlammabilityEvent.Registrar registrar) {
   public static final EventHandler<RegisterFlammabilityEvent> EVENT = new EventHandler<>();

   public void register(Block block, int igniteOdds, int burnOdds) {
      this.registrar.register(block, igniteOdds, burnOdds);
   }

   @FunctionalInterface
   public interface Registrar {
      void register(Block var1, int var2, int var3);
   }
}
