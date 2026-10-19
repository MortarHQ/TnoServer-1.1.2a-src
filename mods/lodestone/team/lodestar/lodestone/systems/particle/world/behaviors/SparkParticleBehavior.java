package team.lodestar.lodestone.systems.particle.world.behaviors;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class SparkParticleBehavior implements LodestoneParticleBehavior {
   private static final VFXBuilders.WorldVFXBuilder SPARK_BUILDER = VFXBuilders.createWorld().setFormat(DefaultVertexFormat.PARTICLE);
   public static final Vec3 UP = new Vec3(0.0, 1.0, 0.0);
   public static final Vec3 DOWN = new Vec3(0.0, -1.0, 0.0);
   protected Vec3 forcedDirection;
   protected float lengthCenter;
   private Vec3 cachedDirection;

   public static SparkParticleBehavior sparkBehavior() {
      return new SparkParticleBehavior();
   }

   protected SparkParticleBehavior() {
   }

   public SparkParticleBehavior setForcedDirection(Vec3 forcedDirection) {
      this.forcedDirection = forcedDirection;
      return this;
   }

   public SparkParticleBehavior setLengthCenter(float lengthCenter) {
      this.lengthCenter = lengthCenter;
      return this;
   }

   @Override
   public void tick(LodestoneWorldParticle particle) {
      Vec3 direction = particle.getParticleSpeed().normalize();
      if (!direction.equals(Vec3.ZERO)) {
         this.cachedDirection = direction;
      }
   }

   @Override
   public void render(LodestoneWorldParticle particle, VertexConsumer consumer, Camera camera, float partialTicks) {
      Vec3 vec3 = camera.getPosition();
      float x = (float)(Mth.lerp(partialTicks, particle.getXOld(), particle.getX()) - vec3.x());
      float y = (float)(Mth.lerp(partialTicks, particle.getYOld(), particle.getY()) - vec3.y());
      float z = (float)(Mth.lerp(partialTicks, particle.getZOld(), particle.getZ()) - vec3.z());
      Vec3 pos = new Vec3(x, y, z);
      float length = particle.getQuadLength(partialTicks);
      Vec3 offset = this.getDirection(particle).scale(length);
      Vec3 movingFrom = this.getStartPos(pos, offset);
      Vec3 movingTo = this.getEndPos(pos, offset);
      SPARK_BUILDER.setVertexConsumer(consumer)
         .setUV(particle.getU0(), particle.getV0(), particle.getU1(), particle.getV1())
         .setColor(particle.getRed(), particle.getGreen(), particle.getBlue())
         .setAlpha(particle.getAlpha())
         .renderBeam(null, movingFrom, movingTo, particle.getQuadSize(partialTicks), Vec3.ZERO);
   }

   public Vec3 getDirection(LodestoneWorldParticle particle) {
      if (this.forcedDirection != null) {
         return this.forcedDirection;
      } else {
         return this.cachedDirection != null ? this.cachedDirection : particle.getParticleSpeed().normalize();
      }
   }

   public Vec3 getStartPos(Vec3 pos, Vec3 offset) {
      return pos.add(offset.scale(this.lengthCenter - 1.0F));
   }

   public Vec3 getEndPos(Vec3 pos, Vec3 offset) {
      return pos.add(offset.scale(this.lengthCenter + 1.0F));
   }
}
