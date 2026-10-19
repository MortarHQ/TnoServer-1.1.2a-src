package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.functions.FunctionReference;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FunctionReference.class)
public interface ReferenceLootFunctionAccessor {
   @Accessor
   ResourceKey<LootItemFunction> getName();
}
