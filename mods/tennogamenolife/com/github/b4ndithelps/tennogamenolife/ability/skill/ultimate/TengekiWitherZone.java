package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.FourthProtectionManager;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.IExMachinaDecorativeRace;
import com.github.b4ndithelps.tennogamenolife.race.Flugel.IFlugelRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class TengekiWitherZone {
   private static final int ZONE_DURATION_TICKS = 3600;
   private static final int PULSE_INTERVAL_TICKS = 20;
   private static final int WITHER_DURATION_TICKS = 40;
   private static final List<TengekiWitherZone.Zone> ACTIVE_ZONES = new ArrayList<>();

   private TengekiWitherZone() {
   }

   public static void start(ServerLevel level, Vec3 center, float radius) {
      if (radius > 0.0F) {
         ACTIVE_ZONES.add(new TengekiWitherZone.Zone(level, center, radius));
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if (!ACTIVE_ZONES.isEmpty()) {
         Iterator<TengekiWitherZone.Zone> iterator = ACTIVE_ZONES.iterator();

         while (iterator.hasNext()) {
            TengekiWitherZone.Zone zone = iterator.next();
            if (zone.isExpired()) {
               iterator.remove();
            } else {
               zone.tick();
            }
         }
      }
   }

   @SubscribeEvent
   public static void onServerStopping(ServerStoppingEvent event) {
      ACTIVE_ZONES.clear();
   }

   private static boolean isImmuneRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(instance -> instance.getRace() instanceof IFlugelRace || instance.getRace() instanceof IExMachinaDecorativeRace)
         .orElse(false);
   }

   private static final class Zone {
      private final ServerLevel level;
      private final Vec3 center;
      private final float radius;
      private final double radiusSquared;
      private final long expiresAt;
      private long nextPulse;

      private Zone(ServerLevel level, Vec3 center, float radius) {
         this.level = level;
         this.center = center;
         this.radius = radius;
         this.radiusSquared = radius * radius;
         this.expiresAt = level.getGameTime() + 3600L;
         this.nextPulse = level.getGameTime();
      }

      private boolean isExpired() {
         return this.level.getGameTime() >= this.expiresAt;
      }

      private void tick() {
         long gameTime = this.level.getGameTime();
         if (gameTime >= this.nextPulse) {
            this.nextPulse = gameTime + 20L;
            int effectDuration = (int)Math.min(40L, Math.max(1L, this.expiresAt - gameTime));
            AABB bounds = new AABB(this.center, this.center).inflate(this.radius);

            for (LivingEntity target : this.level
               .getEntitiesOfClass(
                  LivingEntity.class,
                  bounds,
                  targetx -> targetx.isAlive()
                     && targetx.getBoundingBox().distanceToSqr(this.center) <= this.radiusSquared
                     && !FourthProtectionManager.isInsideBarrier(this.level, targetx.getBoundingBox().getCenter())
                     && !TengekiWitherZone.isImmuneRace(targetx)
               )) {
               target.addEffect(new MobEffectInstance(MobEffects.WITHER, effectDuration, 0, false, true, true));
            }
         }
      }
   }
}
