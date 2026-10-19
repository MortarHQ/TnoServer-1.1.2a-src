package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.Modifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetAttributesFunction.class)
public interface SetAttributesLootFunctionAccessor {
   @Accessor
   List<Modifier> getModifiers();
}
