package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

@EventBusSubscriber(modid = "tensura_tno")
public class SummonAffinityEventHandler {
   private static final int SCAN_INTERVAL = 20;

   @SubscribeEvent
   public static void onMobEffectApply(Applicable event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         if (!(entity instanceof Player)) {
            if (isFoxSpiritSummon(entity)) {
               MobEffectInstance effectInstance = event.getEffectInstance();
               if (effectInstance != null && effectInstance.getEffect().equals(TensuraMobEffects.MIND_CONTROL)) {
                  event.setResult(Result.DO_NOT_APPLY);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(Pre event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         if (entity instanceof LivingEntity living) {
            if (!(living instanceof Player)) {
               if (living.tickCount % 20 == 0) {
                  if (isFoxSpiritSummon(living)) {
                     Holder<MobEffect> mindControl = TensuraMobEffects.MIND_CONTROL;
                     if (living.hasEffect(mindControl)) {
                        living.removeEffect(mindControl);
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isFoxSpiritSummon(LivingEntity entity) {
      Player owner = findOwner(entity);
      return owner == null ? false : FoxSpiritSummonBonus.isFoxSpiritRace(owner);
   }

   private static Player findOwner(LivingEntity entity) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         UUID summonerUUID = existence.getSummoner();
         if (summonerUUID != null && entity.level() instanceof ServerLevel sl && sl.getEntity(summonerUUID) instanceof Player player) {
            return player;
         }
      } catch (Exception var6) {
      }

      if (entity instanceof OwnableEntity ownable) {
         UUID ownerUUID = ownable.getOwnerUUID();
         if (ownerUUID != null && entity.level() instanceof ServerLevel sl && sl.getEntity(ownerUUID) instanceof Player player) {
            return player;
         }
      }

      return null;
   }
}
