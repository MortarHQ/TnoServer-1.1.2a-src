package com.tensurabossstructure.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber
public class VillagerTradeHandler {
   @SubscribeEvent
   public static void onVillagerTrades(VillagerTradesEvent event) {
      if (event.getType() == VillagerProfession.CARTOGRAPHER) {
         Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
         ((List)trades.get(5))
            .add(new VillagerTradeHandler.BossMapTrade("tensura_boss_structure:rimuru_ogre_fight", "filled_map.tensura_boss_structure.rimuru_arena", 1.0));
         ((List)trades.get(5))
            .add(new VillagerTradeHandler.BossMapTrade("tensura_boss_structure:hinata_church", "filled_map.tensura_boss_structure.hinata_church", 1.0));
         ((List)trades.get(5))
            .add(
               new VillagerTradeHandler.BossMapTrade("tensura_boss_structure:orc_disaster_temple", "filled_map.tensura_boss_structure.orc_disaster_temple", 1.0)
            );
         ((List)trades.get(5))
            .add(new VillagerTradeHandler.BossMapTrade("tensura_boss_structure:night_rose", "filled_map.tensura_boss_structure.night_rose", 1.0));
         ((List)trades.get(5))
            .add(new VillagerTradeHandler.BossMapTrade("tensura_boss_structure:shizhu_school", "filled_map.tensura_boss_structure.shizhu_school", 1.0));
         ((List)trades.get(5))
            .add(
               new VillagerTradeHandler.BossMapTrade(
                  "tensura_boss_structure:beast_kingdom_of_eurazania", "filled_map.tensura_boss_structure.beast_kingdom_of_eurazania", 1.0
               )
            );
      }
   }

   static class BossMapTrade implements ItemListing {
      private final String structureId;
      private final String translationKey;
      private final double chance;

      public BossMapTrade(String structureId, String translationKey, double chance) {
         this.structureId = structureId;
         this.translationKey = translationKey;
         this.chance = chance;
      }

      @Nullable
      public MerchantOffer getOffer(Entity trader, RandomSource random) {
         if (random.nextFloat() >= this.chance) {
            return null;
         }

         if (trader.level() instanceof ServerLevel serverLevel) {
            BlockPos var9 = trader.blockPosition();
            TagKey structureTag = TagKey.create(Registries.STRUCTURE, ResourceLocation.parse(this.structureId));
            BlockPos structurePos = serverLevel.findNearestMapStructure(structureTag, var9, 500, false);
            if (structurePos == null) {
               return null;
            }

            int emeraldCost = 48 + random.nextInt(17);
            ItemStack mapStack = MapItem.create(serverLevel, structurePos.getX(), structurePos.getZ(), (byte)2, true, true);
            MapItem.renderBiomePreviewMap(serverLevel, mapStack);
            MapItemSavedData.addTargetDecoration(mapStack, structurePos, "+", MapDecorationTypes.RED_X);
            mapStack.set(DataComponents.CUSTOM_NAME, Component.translatable(this.translationKey));
            return new MerchantOffer(new ItemCost(Items.EMERALD, emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), mapStack, 10, 5, 0.2F);
         } else {
            return null;
         }
      }
   }
}
