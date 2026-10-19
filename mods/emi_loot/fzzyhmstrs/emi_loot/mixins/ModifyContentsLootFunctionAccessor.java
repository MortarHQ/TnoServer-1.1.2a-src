package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.ModifyContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModifyContainerContents.class)
public interface ModifyContentsLootFunctionAccessor {
   @Accessor
   LootItemFunction getModifier();
}
