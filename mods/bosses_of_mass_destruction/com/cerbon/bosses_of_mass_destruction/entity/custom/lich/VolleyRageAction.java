package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.MagicMissileProjectile;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VolleyRageAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final LichConfig mobConfig;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   public static final int ragedMissileVolleyInitialDelay = 60;
   public static final int ragedMissileVolleyBetweenVolleyDelay = 30;
   public static final int ragedMissileParticleDelay = 30;

   public VolleyRageAction(LichEntity entity, LichConfig mobConfig, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.mobConfig = mobConfig;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      return !(target instanceof ServerPlayer) ? 80 : this.performVolley((ServerPlayer)target);
   }

   public int performVolley(ServerPlayer target) {
      int rageMissileVolleys = getRageMissileVolleys(this.entity).size();
      Optional<MobEffect> missileMobEffect = Optional.ofNullable(
         (MobEffect)BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(this.mobConfig.missile.mobEffectId))
      );
      Function<Vec3, ProjectileThrower> missileThrower = this.getMissileThrower(missileMobEffect);
      SoundUtils.playSound(target.serverLevel(), this.entity.position(), (SoundEvent)BMDSounds.MISSILE_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 64.0, null);

      for (int i = 0; i < rageMissileVolleys; i++) {
         int i1 = i;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     Vec3 targetPos = target.getBoundingBox().getCenter();

                     for (Vec3 offset : getRageMissileVolleys(this.entity).get(i1)) {
                        missileThrower.apply(offset).throwProjectile(targetPos.add(offset));
                     }

                     SoundUtils.playSound(
                        target.serverLevel(), this.entity.position(), (SoundEvent)BMDSounds.MISSILE_SHOOT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null
                     );
                  },
                  60 + i1 * 30,
                  1,
                  this.shouldCancel
               )
            );
      }

      return 60 + rageMissileVolleys * 30;
   }

   @NotNull
   private Function<Vec3, ProjectileThrower> getMissileThrower(Optional<MobEffect> missileMobEffect) {
      int missileEffectDuration = this.mobConfig.missile.mobEffectDuration;
      int missileEffectAmplifier = this.mobConfig.missile.mobEffectAmplifier;
      return offset -> new ProjectileThrower(
         () -> {
            MagicMissileProjectile projectile = new MagicMissileProjectile(
               this.entity,
               this.entity.level(),
               livingEntity -> missileMobEffect.ifPresent(
                  effect -> livingEntity.addEffect(
                     new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), missileEffectDuration, missileEffectAmplifier)
                  )
               ),
               MinionAction.summonEntityType != null ? List.of(MinionAction.summonEntityType) : List.of()
            );
            MobUtils.setPos(projectile, MobUtils.eyePos(this.entity).add(offset));
            return new ProjectileThrower.ProjectileData(projectile, 1.6F, 0.0F, 0.2);
         }
      );
   }

   public static List<List<Vec3>> getRageMissileVolleys(LichEntity entity) {
      double xOffset = 3.0;
      double zOffset = 4.0;
      int numPoints = 9;
      Vec3 lineStart = MathUtils.axisOffset(entity.getLookAngle(), VecUtils.xAxis.scale(xOffset).add(VecUtils.zAxis.scale(zOffset)));
      Vec3 lineEnd = MathUtils.axisOffset(entity.getLookAngle(), VecUtils.xAxis.scale(xOffset).add(VecUtils.zAxis.scale(-zOffset)));
      ArrayList<Vec3> lineAcross = new ArrayList<>();
      MathUtils.lineCallback(lineStart, lineEnd, numPoints, (vec3, integer) -> lineAcross.add(vec3));
      List<Vec3> lineUpDown = lineAcross.stream().map(vec3 -> VecUtils.rotateVector(vec3, entity.getLookAngle(), 90.0)).toList();
      List<Vec3> cross = new ArrayList<>(lineAcross);
      cross.addAll(lineUpDown);
      List<Vec3> xVolley = cross.stream().map(vec3 -> VecUtils.rotateVector(vec3, entity.getLookAngle(), 45.0)).toList();
      return List.of(lineAcross, lineUpDown, cross, xVolley);
   }
}
