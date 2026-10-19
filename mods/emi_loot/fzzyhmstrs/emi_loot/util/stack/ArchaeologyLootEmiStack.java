package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.resources.ResourceLocation;

public class ArchaeologyLootEmiStack extends EmiStackWithTitle {
   public ArchaeologyLootEmiStack(ResourceLocation id) {
      super(id, "archaeology", "unknown_archaeology", EMILoot.Type.ARCHAEOLOGY, EMILoot.config.isTooltipStyle() ? 138 : 148, EMILoot.config.getLinkFormatting());
   }

   public EmiStack copy() {
      return new ArchaeologyLootEmiStack(this.id);
   }
}
