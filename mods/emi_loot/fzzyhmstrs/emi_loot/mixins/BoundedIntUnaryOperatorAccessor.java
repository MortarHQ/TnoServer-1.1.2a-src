package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(IntRange.class)
public interface BoundedIntUnaryOperatorAccessor {
   @Accessor("min")
   NumberProvider getMin();

   @Accessor("max")
   NumberProvider getMax();
}
