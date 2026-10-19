package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetComponentsFunction.class)
public interface SetComponentsLootFunctionAccessor {
   @Accessor
   DataComponentPatch getComponents();
}
