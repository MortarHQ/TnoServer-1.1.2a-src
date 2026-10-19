package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPoolSingletonContainer.class)
public interface LeafEntryAccessor {
   @Accessor("weight")
   int getWeight();

   @Accessor("functions")
   List<LootItemFunction> getFunctions();
}
