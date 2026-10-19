package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.world.level.storage.loot.functions.SetOminousBottleAmplifierFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetOminousBottleAmplifierFunction.class)
public interface SetOminousBottleAmplifierLootFunctionAccessor {
   @Accessor
   NumberProvider getAmplifierGenerator();
}
