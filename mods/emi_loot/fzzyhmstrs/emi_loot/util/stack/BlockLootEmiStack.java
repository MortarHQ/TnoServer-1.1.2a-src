package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class BlockLootEmiStack extends EmiStackWithTitle {
   public BlockLootEmiStack(ResourceLocation id) {
      super(id, "block", "unknown_block", EMILoot.Type.CHEST, 138, EMILoot.config.getLinkFormatting());
   }

   public EmiStack copy() {
      return new BlockLootEmiStack(this.id);
   }

   @Override
   protected MutableComponent getRawTitle(ResourceLocation id, String unknownNamespace, String unknownPath, EMILoot.Type type) {
      return LText.empty();
   }
}
