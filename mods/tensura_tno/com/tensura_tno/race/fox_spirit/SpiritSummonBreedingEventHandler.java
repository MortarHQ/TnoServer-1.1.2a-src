package com.tensura_tno.race.fox_spirit;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonBreedingEventHandler {
   private SpiritSummonBreedingEventHandler() {
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onEntityInteract(EntityInteract event) {
      if (shouldBlockBreedingFood(event, event.getTarget())) {
         event.setCancellationResult(InteractionResult.SUCCESS);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onEntityInteractSpecific(EntityInteractSpecific event) {
      if (shouldBlockBreedingFood(event, event.getTarget())) {
         event.setCancellationResult(InteractionResult.SUCCESS);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onBabySpawn(BabyEntitySpawnEvent event) {
      if (SpiritSummonEntityHelper.isSpiritSummon(event.getParentA()) || SpiritSummonEntityHelper.isSpiritSummon(event.getParentB())) {
         event.setCanceled(true);
      }
   }

   private static boolean shouldBlockBreedingFood(PlayerInteractEvent event, Entity target) {
      return event.getLevel().isClientSide() ? false : isBreedingFoodForSpiritSummon(target, event.getItemStack());
   }

   private static boolean isBreedingFoodForSpiritSummon(Entity target, ItemStack stack) {
      return target instanceof Animal animal && SpiritSummonEntityHelper.isSpiritSummon(animal) && animal.isFood(stack);
   }
}
