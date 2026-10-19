package lain.mods.cos.api.event;

import lain.mods.cos.api.inventory.CAStacksBase;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class CosArmorDeathDrops extends Event implements ICancellableEvent {
   private final Player player;
   private final CAStacksBase stacks;

   public CosArmorDeathDrops(Player player, CAStacksBase stacks) {
      this.player = player;
      this.stacks = stacks;
   }

   public CAStacksBase getCAStacks() {
      return this.stacks;
   }

   public Player getEntityPlayer() {
      return this.player;
   }
}
