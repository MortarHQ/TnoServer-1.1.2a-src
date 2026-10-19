package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SequenceFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SequenceFunction.class)
public interface AndLootFunctionAccessor {
   @Accessor
   List<LootItemFunction> getFunctions();
}
