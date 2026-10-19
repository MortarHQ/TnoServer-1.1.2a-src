package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.storage.effect.EffectStorage;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class GodlySmiteVfx {
   public static final int CHARGE_DURATION_TICKS = 1200;
   public static final int STRIKE_COMPRESSION_TICKS = 12;
   public static final int BEAM_DESCENT_TICKS = 10;
   public static final int RELEASE_WHITEOUT_DURATION_TICKS = 20;
   public static final int FIREBALL_FALL_DURATION_TICKS = 100;
   public static final int IMPACT_DELAY_TICKS = 120;
   public static final int IMPACT_AFTERMATH_TICKS = 64;
   public static final int STRIKE_DURATION_TICKS = 184;
   public static final float DEFAULT_VISUAL_RADIUS = 96.0F;
   public static final float DEFAULT_SKY_HEIGHT = 100.0F;
   public static final float FIREBALL_RADIUS = 60.0F;
   public static final float IMPACT_HEIGHT = 0.08F;

   private GodlySmiteVfx() {
   }

   public static DragonRoarCircle spawnCharge(ServerLevel level, Entity caster) {
      DragonRoarCircle vfx = create(level, caster.getBoundingBox().getCenter());
      vfx.setFollowTargetId(caster.getId());
      vfx.setYRot(caster.getYRot());
      vfx.yRotO = caster.getYRot();
      vfx.setGodlySmiteChargeVfx();
      vfx.setVfxDuration(1200);
      vfx.setLife(1220);
      level.addFreshEntity(vfx);
      return vfx;
   }

   public static DragonRoarCircle spawnStrike(ServerLevel level, Vec3 center, Vec3 origin) {
      DragonRoarCircle vfx = create(level, center);
      beginStrike(level, vfx, center, origin);
      level.addFreshEntity(vfx);
      return vfx;
   }

   public static void beginStrike(ServerLevel level, DragonRoarCircle vfx, Vec3 center, Vec3 origin) {
      vfx.setFollowTargetId(-1);
      vfx.setPos(center);
      vfx.setVfxOriginOffset(origin.subtract(center));
      vfx.setVfxStartGameTime(level.getGameTime());
      vfx.setVfxDuration(184);
      vfx.setLife(187);
      vfx.setGodlySmiteStrikeVfx();
      EffectStorage.setCameraShake(level, center, 192.0, 0.02F, 120, null);
   }

   public static Vec3 getReleaseOrigin(Entity caster) {
      return new Vec3(caster.getX(), caster.getY() + caster.getBbHeight() + 100.0, caster.getZ());
   }

   public static Vec3 getReleaseImpactSurface(Entity caster) {
      Vec3 releasePosition = caster.position();
      BlockHitResult ground = caster.level()
         .clip(
            new ClipContext(
               new Vec3(releasePosition.x, caster.getBoundingBox().minY + 0.5, releasePosition.z),
               new Vec3(releasePosition.x, caster.level().getMinBuildHeight() - 1.0, releasePosition.z),
               Block.COLLIDER,
               Fluid.NONE,
               caster
            )
         );
      return ground.getType() == Type.MISS ? releasePosition : ground.getLocation();
   }

   public static void spawnImpactParticles(ServerLevel level, Vec3 center) {
      EffectStorage.setCameraShake(level, center, 192.0, 0.05F, 28, null);
      TensuraParticleHelper.spawnServerParticles(level, TensuraParticleUtils.getColorlessWave(0.9F, 82.0F), center.x, center.y + 0.2, center.z);
      TensuraParticleHelper.spawnServerParticles(level, TensuraParticleUtils.getGoldWave(0.72F, 68.0F, 0.12F, true), center.x, center.y + 0.24, center.z);
      level.sendParticles(ParticleTypes.FLASH, center.x, center.y + 1.0, center.z, 2, 1.5, 1.0, 1.5, 0.0);
      level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y + 0.25, center.z, 3, 3.5, 1.8, 3.5, 0.0);
      level.sendParticles(ParticleTypes.FLAME, center.x, center.y + 1.0, center.z, 180, 14.0, 6.0, 14.0, 0.32);
      level.sendParticles(ParticleTypes.LAVA, center.x, center.y + 0.75, center.z, 64, 10.0, 4.0, 10.0, 0.24);
   }

   private static DragonRoarCircle create(ServerLevel level, Vec3 center) {
      DragonRoarCircle vfx = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), level);
      vfx.setPos(center);
      vfx.setSize(96.0F);
      vfx.setVfxSeed(level.random.nextInt());
      vfx.setVfxStartHeight(100.0F);
      vfx.setVfxOriginOffset(new Vec3(0.0, 100.0, 0.0));
      vfx.setVfxStartGameTime(level.getGameTime());
      return vfx;
   }
}
