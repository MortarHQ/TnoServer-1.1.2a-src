package com.tensura_tno.event;

import com.tensura_tno.registry.TensuraTNOItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class LuminousPlushDropsHandler {
   private static final ResourceLocation LUMINOUS = ResourceLocation.fromNamespaceAndPath("tensura_neb", "luminous_valentine");

   private LuminousPlushDropsHandler() {
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.level() instanceof ServerLevel level
         && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
         && LUMINOUS.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()))) {
         if (entity.saveWithoutId(new CompoundTag()).getInt("Phase") >= 2) {
            ItemEntity drop = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)TensuraTNOItems.LUMINOUS_PLUSH.get()));
            drop.setDefaultPickUpDelay();
            event.getDrops().add(drop);
         }
      }
   }
}
