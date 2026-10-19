package fzzyhmstrs.emi_loot.server;

import com.mojang.serialization.DataResult;
import java.util.List;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface LootSender<T extends LootBuilder> {
   String getId();

   void send(ServerPlayer var1);

   void addBuilder(T var1);

   List<T> getBuilders();

   void build();

   static String getIdToSend(ResourceLocation id) {
      if (id.getNamespace().equals("minecraft")) {
         String path = id.getPath();
         if (path.contains("blocks/")) {
            return "b/" + path.substring(7);
         } else if (path.contains("entities/")) {
            return "e/" + path.substring(9);
         } else if (path.contains("chests/")) {
            return "c/" + path.substring(7);
         } else if (path.contains("gameplay/")) {
            return "g/" + path.substring(9);
         } else {
            return path.contains("archaeology/") ? "a/" + path.substring(12) : path;
         }
      } else {
         return id.toString();
      }
   }

   default void writeItemStack(FriendlyByteBuf buf, ItemStack stack, Level world) {
      buf.writeVarInt(BuiltInRegistries.ITEM.getId(stack.getItem()));
      buf.writeVarInt(stack.getCount());
      DataComponentPatch changes = stack.getComponentsPatch();
      if (changes.isEmpty()) {
         buf.writeBoolean(false);
      } else {
         DataResult<Tag> nbtResult = DataComponentPatch.CODEC.encodeStart(NbtOps.INSTANCE, changes);
         nbtResult.ifSuccess(nbt -> {
            buf.writeBoolean(true);
            buf.writeNbt(nbt);
         }).ifError(err -> buf.writeBoolean(false));
      }
   }
}
