package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.networking.MobLootPayload;
import fzzyhmstrs.emi_loot.util.TextKey;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class MobLootTableSender implements LootSender<ComplexLootPoolBuilder> {
   private final String idToSend;
   private final String mobIdToSend;
   final List<ComplexLootPoolBuilder> builderList = new LinkedList<>();
   boolean isEmpty = true;

   public MobLootTableSender(ResourceLocation id, ResourceLocation mobId) {
      this.idToSend = LootSender.getIdToSend(id);
      this.mobIdToSend = LootSender.getIdToSend(mobId);
   }

   @Override
   public void build() {
      this.builderList.forEach(builder -> {
         builder.build();
         if (!builder.isEmpty) {
            this.isEmpty = false;
         }
      });
   }

   @Override
   public String getId() {
      return this.idToSend;
   }

   @Override
   public void send(ServerPlayer player) {
      if (ConfigApi.INSTANCE.network().canSend(MobLootPayload.TYPE.id(), player)) {
         if (this.isEmpty) {
            if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
               EMILoot.LOGGER.info("avoiding empty mob: " + this.idToSend);
            }
         } else {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUtf(this.idToSend);
            buf.writeUtf(this.mobIdToSend);
            if (this.builderList.size() == 1 && this.builderList.get(0).isSimple) {
               if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
                  EMILoot.LOGGER.info("sending simple mob: " + this.idToSend);
               }

               buf.writeShort(-1);
               buf.writeResourceKey(this.builderList.get(0).simpleStack.getItem().builtInRegistryHolder().key());
               ConfigApi.INSTANCE.network().send(new MobLootPayload(buf), player);
            } else if (this.builderList.isEmpty()) {
               if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
                  EMILoot.LOGGER.info("avoiding empty mob: " + this.idToSend);
               }
            } else {
               buf.writeShort(this.builderList.size());
               this.builderList.forEach(builder -> {
                  List<TextKey> totalConditions = new ArrayList<>();
                  builder.conditions.forEach(lootConditionResult -> totalConditions.add(lootConditionResult.text()));
                  builder.functions.forEach(lootFunctionResult -> totalConditions.addAll(lootFunctionResult.conditions()));
                  buf.writeShort(totalConditions.size());
                  totalConditions.forEach(lootConditionResult -> lootConditionResult.toBuf(buf));
                  buf.writeShort(builder.functions.size());
                  builder.functions.forEach(lootFunctionResult -> lootFunctionResult.text().toBuf(buf));
                  Map<List<TextKey>, SimpleLootPoolBuilder> lootPoolBuilderMap = builder.builtMap;
                  buf.writeShort(lootPoolBuilderMap.size());
                  lootPoolBuilderMap.forEach((key, chestBuilder) -> {
                     buf.writeShort(key.size());
                     key.forEach(textKey -> textKey.toBuf(buf));
                     Map<ItemStack, Float> keyPoolMap = lootPoolBuilderMap.getOrDefault(key, new SimpleLootPoolBuilder(1.0F)).builtMap;
                     buf.writeShort(keyPoolMap.size());
                     keyPoolMap.forEach((stack, weight) -> {
                        this.writeItemStack(buf, stack, player.serverLevel());
                        buf.writeFloat(weight);
                     });
                  });
               });
               ConfigApi.INSTANCE.network().send(new MobLootPayload(buf), player);
            }
         }
      }
   }

   public void addBuilder(ComplexLootPoolBuilder builder) {
      this.builderList.add(builder);
   }

   @Override
   public List<ComplexLootPoolBuilder> getBuilders() {
      return this.builderList;
   }
}
