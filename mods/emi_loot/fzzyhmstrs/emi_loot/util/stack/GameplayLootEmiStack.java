package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.resources.ResourceLocation;

public class GameplayLootEmiStack extends EmiStackWithTitle {
   public GameplayLootEmiStack(ResourceLocation id) {
      super(id, "gameplay", "unknown_gameplay", EMILoot.Type.GAMEPLAY, EMILoot.config.isTooltipStyle() ? 138 : 148, EMILoot.config.getLinkFormatting());
   }

   public EmiStack copy() {
      return new GameplayLootEmiStack(this.id);
   }
}
