package net.witherspawnanimation.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneXEntity;
import net.witherspawnanimation.entity.AnimatedAltarBygoneZEntity;
import net.witherspawnanimation.entity.AnimatedAltarMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilXEntity;
import net.witherspawnanimation.entity.AnimatedAltarSoilZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilMinZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormSoilZEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormXEntity;
import net.witherspawnanimation.entity.AnimatedAltarStormZEntity;
import net.witherspawnanimation.entity.AnimatedAltarXEntity;
import net.witherspawnanimation.entity.AnimatedAltarZEntity;

@EventBusSubscriber
public class EntityAnimationFactory {
   @SubscribeEvent
   public static void onEntityTick(Pre event) {
      if (event != null && event.getEntity() != null) {
         if (event.getEntity() instanceof AnimatedAltarZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarMinZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarMinXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarSoilZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarSoilMinZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarSoilXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarSoilMinXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormMinZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormMinXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarBygoneZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarBygoneMinZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarBygoneXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarBygoneMinXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormSoilZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormSoilMinZEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormSoilXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }

         if (event.getEntity() instanceof AnimatedAltarStormSoilMinXEntity syncable) {
            String animation = syncable.getSyncedAnimation();
            if (!animation.equals("undefined")) {
               syncable.setAnimation("undefined");
               syncable.animationprocedure = animation;
            }
         }
      }
   }
}
