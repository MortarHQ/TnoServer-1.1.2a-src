package net.witherspawnanimation.init;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class WitherSpawnAnimationModGameRules {
   public static Key<BooleanValue> WITHER_SPAWN_ANIMATION;

   @SubscribeEvent
   public static void registerGameRules(FMLCommonSetupEvent event) {
      WITHER_SPAWN_ANIMATION = GameRules.register("witherSpawnAnimation", Category.MOBS, BooleanValue.create(true));
   }
}
