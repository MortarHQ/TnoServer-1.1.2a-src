package fzzyhmstrs.emi_loot.server.function;

import com.mojang.serialization.MapCodec;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class SetAnyDamageLootFunction implements LootItemFunction {
   public static final SetAnyDamageLootFunction INSTANCE = new SetAnyDamageLootFunction();
   public static final MapCodec<SetAnyDamageLootFunction> CODEC = MapCodec.unit(INSTANCE);

   public LootItemFunctionType<?> getType() {
      return EMILoot.SET_ANY_DAMAGE.get();
   }

   public ItemStack apply(ItemStack stack, LootContext lootContext) {
      return ItemStack.EMPTY;
   }
}
