package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class MobLootEmiStack extends EmiStackWithTitle {
   public MobLootEmiStack(ResourceLocation id) {
      super(id, "mob", "unknown_mob", EMILoot.Type.CHEST, 138, EMILoot.config.getLinkFormatting());
   }

   public EmiStack copy() {
      return new MobLootEmiStack(this.id);
   }

   @Override
   protected MutableComponent getRawTitle(ResourceLocation id, String unknownNamespace, String unknownPath, EMILoot.Type type) {
      return LText.empty();
   }
}
