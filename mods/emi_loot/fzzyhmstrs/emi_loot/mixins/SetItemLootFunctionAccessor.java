package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.functions.SetItemFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetItemFunction.class)
public interface SetItemLootFunctionAccessor {
   @Accessor
   Holder<Item> getItem();
}
