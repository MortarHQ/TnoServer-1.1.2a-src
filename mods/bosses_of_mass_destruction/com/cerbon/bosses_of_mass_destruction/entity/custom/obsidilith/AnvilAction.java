package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.cerbons_api.api.general.event.Event;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.ParticleUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class AnvilAction implements IActionWithCooldown {
   private final Mob actor;
   private final float explosionPower;
   private final EventScheduler eventScheduler;
   private final List<Vec3> circlePoints;

   public AnvilAction(Mob actor, float explosionPower) {
      this.actor = actor;
      this.explosionPower = explosionPower;
      this.eventScheduler = CapabilityUtils.getLevelEventScheduler(actor.level());
      this.circlePoints = MathUtils.buildBlockCircle(2.0);
   }

   @Override
   public int perform() {
      LivingEntity target = this.actor.getTarget();
      Level level = this.actor.level();
      if (!(level instanceof ServerLevel)) {
         return 80;
      }

      this.performAttack(target, (ServerLevel)level);
      return 80;
   }

   private void performAttack(LivingEntity target, ServerLevel level) {
      SoundUtils.playSound(level, this.actor.position(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 1.0F, 64.0, null);
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> {
                  Vec3 targetPos = target.position();
                  Vec3 teleportPos = targetPos.add(VecUtils.yAxis.scale(24.0));
                  Vec3 originalPos = this.actor.position();
                  this.actor.moveTo(teleportPos.x, teleportPos.y, teleportPos.z, this.actor.getYRot(), this.actor.getXRot());
                  SoundUtils.playSound(level, teleportPos, (SoundEvent)BMDSounds.OBSIDILITH_TELEPORT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);

                  for (Vec3 pos : this.circlePoints) {
                     BlockPos particlePos = BMDUtils.findGroundBelow(this.actor.level(), BlockPos.containing(pos.add(targetPos)).above(3), pos1 -> true)
                        .above();
                     if (particlePos.getY() != 0) {
                        ParticleUtils.spawnParticle(
                           level,
                           (ParticleOptions)BMDParticles.OBSIDILITH_ANVIL_INDICATOR.get(),
                           VecUtils.asVec3(particlePos).add(new Vec3(0.5, 0.1, 0.5)),
                           Vec3.ZERO,
                           0,
                           0.0
                        );
                     }
                  }

                  this.eventScheduler
                     .addEvent(
                        new TimedEvent(
                           () -> {
                              Supplier<Boolean> shouldLand = () -> this.actor.onGround() || this.actor.getY() < 0.0;
                              Supplier<Boolean> shouldCancelLand = () -> !this.actor.isAlive() || shouldLand.get();
                              this.eventScheduler
                                 .addEvent(
                                    new Event(
                                       shouldLand,
                                       () -> {
                                          this.actor
                                             .level()
                                             .explode(
                                                this.actor,
                                                this.actor.getX(),
                                                this.actor.getY(),
                                                this.actor.getZ(),
                                                this.explosionPower,
                                                ExplosionInteraction.MOB
                                             );
                                          this.eventScheduler
                                             .addEvent(
                                                new TimedEvent(
                                                   () -> {
                                                      this.actor
                                                         .moveTo(originalPos.x, originalPos.y, originalPos.z, this.actor.getYRot(), this.actor.getXRot());
                                                      SoundUtils.playSound(
                                                         level,
                                                         this.actor.position(),
                                                         (SoundEvent)BMDSounds.OBSIDILITH_TELEPORT.get(),
                                                         SoundSource.HOSTILE,
                                                         1.0F,
                                                         64.0,
                                                         null
                                                      );
                                                   },
                                                   20,
                                                   1,
                                                   () -> !this.actor.isAlive()
                                                )
                                             );
                                       },
                                       shouldCancelLand
                                    )
                                 );
                           },
                           1,
                           1,
                           () -> !this.actor.isAlive()
                        )
                     );
               },
               20,
               1,
               () -> !this.actor.isAlive()
            )
         );
   }
}
