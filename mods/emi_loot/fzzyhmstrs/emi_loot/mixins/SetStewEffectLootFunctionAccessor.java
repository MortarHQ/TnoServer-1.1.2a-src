package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction.EffectEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetStewEffectFunction.class)
public interface SetStewEffectLootFunctionAccessor {
   @Accessor("effects")
   List<EffectEntry> getEffects();
}
