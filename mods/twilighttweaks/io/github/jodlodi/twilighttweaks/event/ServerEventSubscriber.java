package io.github.jodlodi.twilighttweaks.event;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import twilightforest.init.TFDimension;

@ParametersAreNonnullByDefault
@EventBusSubscriber(modid = "twilighttweaks")
public class ServerEventSubscriber {
   @SubscribeEvent
   public static void livingDropsEventListener(LivingDropsEvent event) {
      if (event.getEntity() instanceof Mob mob
         && mob instanceof Enemy
         && mob.level() instanceof ServerLevel serverLevel
         && serverLevel.dimension().location().equals(TFDimension.DIMENSION)
         && TweakConfig.remnantFlag
         && serverLevel.random.nextInt(200) == 0) {
         mob.spawnAtLocation((ItemLike)TwilightTweaks.TIME_POWDER.get());
      }
   }
}
