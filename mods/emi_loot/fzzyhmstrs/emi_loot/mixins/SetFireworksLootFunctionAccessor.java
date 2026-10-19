package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.storage.loot.functions.SetFireworksFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SetFireworksFunction.class)
public interface SetFireworksLootFunctionAccessor {
   @Invoker
   Fireworks callApply(Fireworks var1);
}
