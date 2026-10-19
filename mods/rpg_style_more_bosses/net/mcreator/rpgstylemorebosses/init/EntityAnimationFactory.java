package net.mcreator.rpgstylemorebosses.init;

import net.mcreator.rpgstylemorebosses.entity.BeaconActiveEntity;
import net.mcreator.rpgstylemorebosses.entity.BeaconEntity;
import net.mcreator.rpgstylemorebosses.entity.BlockBarrierEntity;
import net.mcreator.rpgstylemorebosses.entity.BossCounterEntity;
import net.mcreator.rpgstylemorebosses.entity.ExoframeBeamEntity;
import net.mcreator.rpgstylemorebosses.entity.ExoframeTickEntity;
import net.mcreator.rpgstylemorebosses.entity.KriStrarzEntity;
import net.mcreator.rpgstylemorebosses.entity.RunicSpiderEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeSpawnEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFX2Entity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeVFXEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

@EventBusSubscriber
public class EntityAnimationFactory {
   @SubscribeEvent
   public static void onEntityTick(Pre event) {
      if (event != null && event.getEntity() != null) {
         if (event.getEntity() instanceof RunicSpiderEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof KriStrarzEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof TheExoframeEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof TheExoframeVFXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof TheExoframeVFX2Entity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof BeaconEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof BeaconActiveEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof BossCounterEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof BlockBarrierEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof TheExoframeSpawnEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof ExoframeBeamEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof ExoframeTickEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }
      }
   }
}
