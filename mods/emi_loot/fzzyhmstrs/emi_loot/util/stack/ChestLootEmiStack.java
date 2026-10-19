package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.resources.ResourceLocation;

public class ChestLootEmiStack extends EmiStackWithTitle {
   public ChestLootEmiStack(ResourceLocation id) {
      super(id, "chest", "unknown_chest", EMILoot.Type.CHEST, 138, EMILoot.config.getLinkFormatting());
   }

   public EmiStack copy() {
      return new ChestLootEmiStack(this.id);
   }
}
