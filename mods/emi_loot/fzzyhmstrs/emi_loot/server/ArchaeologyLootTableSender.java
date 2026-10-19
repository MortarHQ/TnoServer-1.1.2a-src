package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.networking.ArchaeologyLootPayload;
import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ArchaeologyLootTableSender implements LootSender<SimpleLootPoolBuilder> {
   private final String idToSend;
   final List<SimpleLootPoolBuilder> builderList = new LinkedList<>();
   HashMap<ItemStack, Float> floatMap = new HashMap<>();

   public ArchaeologyLootTableSender(ResourceLocation id) {
      this.idToSend = LootSender.getIdToSend(id);
   }

   @Override
   public String getId() {
      return this.idToSend;
   }

   @Override
   public void send(ServerPlayer player) {
      if (ConfigApi.INSTANCE.network().canSend(ArchaeologyLootPayload.TYPE.id(), player)) {
         FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
         buf.writeUtf(this.idToSend);
         buf.writeShort(this.floatMap.size());
         this.floatMap.forEach((item, floatWeight) -> {
            this.writeItemStack(buf, item, player.serverLevel());
            buf.writeFloat(floatWeight);
         });
         ConfigApi.INSTANCE.network().send(new ArchaeologyLootPayload(buf), player);
      }
   }

   public void addBuilder(SimpleLootPoolBuilder builder) {
      this.builderList.add(builder);
   }

   @Override
   public List<SimpleLootPoolBuilder> getBuilders() {
      return this.builderList;
   }

   @Override
   public void build() {
      this.builderList.forEach(builder -> {
         builder.build();
         builder.builtMap.forEach((item, weight) -> {
            if (this.floatMap.containsKey(item)) {
               float oldWeight = this.floatMap.getOrDefault(item, 0.0F);
               this.floatMap.put(item, oldWeight + weight);
            } else {
               this.floatMap.put(item, weight);
            }
         });
      });
   }
}
