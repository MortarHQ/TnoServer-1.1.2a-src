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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VolleyAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final LichConfig mobConfig;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   public static final int missileThrowDelay = 46;
   public static final int missileThrowCooldown = 80;
   public static final int missileParticleSummonDelay = 16;

   public VolleyAction(LichEntity entity, LichConfig mobConfig, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.mobConfig = mobConfig;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      }

      this.performVolley((ServerPlayer)target);
      return 80;
   }

   public void performVolley(ServerPlayer target) {
      Optional<MobEffect> missileMobEffect = Optional.ofNullable(
         (MobEffect)BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(this.mobConfig.missile.mobEffectId))
      );
      Function<Vec3, ProjectileThrower> missileThrower = this.getMissileThrower(missileMobEffect);
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         Vec3 targetPos = target.getBoundingBox().getCenter();

         for (Vec3 offset : getMissileLaunchOffsets(this.entity)) {
            missileThrower.apply(offset).throwProjectile(targetPos.add(VecUtils.planeProject(offset, VecUtils.yAxis)));
         }

         SoundUtils.playSound(target.serverLevel(), this.entity.position(), (SoundEvent)BMDSounds.MISSILE_SHOOT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);
      }, 46, 1, this.shouldCancel));
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> SoundUtils.playSound(
                  target.serverLevel(), this.entity.position(), (SoundEvent)BMDSounds.MISSILE_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 64.0, null
               ),
               10,
               1,
               this.shouldCancel
            )
         );
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

   public static List<Vec3> getMissileLaunchOffsets(Entity entity) {
      List<Vec3> offsets = new ArrayList<>();
      offsets.add(MathUtils.axisOffset(entity.getLookAngle(), VecUtils.yAxis.add(VecUtils.zAxis.scale(2.0))));
      offsets.add(MathUtils.axisOffset(entity.getLookAngle(), VecUtils.yAxis.scale(1.5).add(VecUtils.zAxis)));
      offsets.add(MathUtils.axisOffset(entity.getLookAngle(), VecUtils.yAxis.scale(2.0)));
      offsets.add(MathUtils.axisOffset(entity.getLookAngle(), VecUtils.yAxis.scale(1.5).add(VecUtils.negateServer(VecUtils.zAxis))));
      offsets.add(MathUtils.axisOffset(entity.getLookAngle(), VecUtils.yAxis.add(VecUtils.negateServer(VecUtils.zAxis).scale(2.0))));
      return offsets;
   }
}
