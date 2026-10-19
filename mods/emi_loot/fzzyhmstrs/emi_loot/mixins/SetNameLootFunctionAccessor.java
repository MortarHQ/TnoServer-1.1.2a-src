package fzzyhmstrs.emi_loot.mixins;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetNameFunction.class)
public interface SetNameLootFunctionAccessor {
   @Accessor("name")
   Optional<Component> getName();
}
