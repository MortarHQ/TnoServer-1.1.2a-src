package fzzyhmstrs.emi_loot.client;

import com.mojang.serialization.DataResult;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface LootReceiver {
   boolean isEmpty();

   ResourceLocation getId();

   LootReceiver fromBuf(FriendlyByteBuf var1);

   default ItemStack readItemStack(FriendlyByteBuf buf) {
      Holder<Item> byId = BuiltInRegistries.ITEM.wrapAsHolder((Item)BuiltInRegistries.ITEM.byId(buf.readVarInt()));
      int count = buf.readVarInt();
      if (buf.readBoolean()) {
         Tag nbt = buf.readNbt();
         DataResult<DataComponentPatch> changesResult = DataComponentPatch.CODEC.parse(NbtOps.INSTANCE, nbt);
         DataComponentPatch changes = (DataComponentPatch)changesResult.mapOrElse(Function.identity(), err -> DataComponentPatch.EMPTY);
         return changes.isEmpty() ? new ItemStack(byId, count) : new ItemStack(byId, count, changes);
      } else {
         return new ItemStack(byId, count);
      }
   }
}
