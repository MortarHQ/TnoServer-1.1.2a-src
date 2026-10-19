package net.unusual.block_factorys_bosses.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.InvulWispParticle;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class ClientParticleHandler {
   protected static Vec3 toVec3(Vector3d vec) {
      return new Vec3(vec.x, vec.y, vec.z);
   }

   @Nullable
   public static Particle addParticle(ParticleOptions particleData, Vec3 position, Vec3 velocity) {
      return Minecraft.getInstance().level instanceof ClientLevel level
         ? level.levelRenderer.addParticleInternal(particleData, true, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z)
         : null;
   }

   public static void renderInvulWisp(GeoModel<UnderworldKnightEntity> geoModel, String boneName, boolean inactive) {
      if (geoModel.getBone(boneName).orElse(null) instanceof GeoBone bone) {
         Vec3 pos = toVec3(bone.getWorldPosition());
         addParticle(
            ColorParticleOption.create((ParticleType)BossesRiseParticleTypes.PIXEL.get(), inactive ? 16740689 : 62969), pos.subtract(0.0, 0.1, 0.0), Vec3.ZERO
         );
         if (addParticle(
            inactive ? (ParticleOptions)BossesRiseParticleTypes.INVUL_WISP_INACTIVE.get() : (ParticleOptions)BossesRiseParticleTypes.INVUL_WISP.get(),
            pos,
            Vec3.ZERO
         ) instanceof InvulWispParticle particle) {
            particle.bone = bone;
         }
      }
   }
}
