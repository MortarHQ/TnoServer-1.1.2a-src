package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import net.unusual.block_factorys_bosses.event.ClientEvents;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition10 extends SubSteadyParticleEmitter {
   private static final LinearCurve R_FACTOR = new LinearCurve(
      new float[]{0.33F, 1.0F, 0.71F}, (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime, (subParticle, partial) -> 1.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(11663871), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16716801), 0.4F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(1157693383), 0.75F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(60671), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static PhaseTransition10.Provider provider(SpriteSet spriteSet) {
      return new PhaseTransition10.Provider(spriteSet);
   }

   protected PhaseTransition10(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 60, 30, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 40;
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 0.5F * R_FACTOR.get(subParticle, partialTick);
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   public void tick() {
      if (ClientEvents.PARTICULARS.containsValue(this)) {
         this.age = 0;
      }

      super.tick();
   }

   @Override
   public boolean shouldAttach() {
      return true;
   }

   @Override
   public void render(VertexConsumer buffer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.getPosition();
      Vec3 pos = this.getCurrentPos(partialTick);
      Vector3f rot = null;
      if (this.attachment != null && this.attachment.locator() != null) {
         GeoBone geoBone = this.attachment.locator().bone();
         Vector3d vector = this.get(geoBone);

         while (true) {
            geoBone = geoBone.getParent();
            if (geoBone == null) {
               rot = new Vector3f(
                  (float)(this.attachment.entity().getViewXRot(partialTick) * (float) (Math.PI / 180.0) + vector.x * (float) (Math.PI / 180.0)),
                  (float)(-this.attachment.entity().getViewYRot(partialTick) * (float) (Math.PI / 180.0) + vector.y * (float) (Math.PI / 180.0)),
                  (float)vector.z * (float) (Math.PI / 180.0)
               );
               break;
            }

            vector.add(this.get(geoBone));
         }
      }

      for (SubParticle subParticle : this.proxies) {
         subParticle.onRender(partialTick);
         float ageInSeconds = (subParticle.age + partialTick) / 20.0F;
         Vec3 sub = this.getRelativeSubPos(subParticle, partialTick, ageInSeconds);
         if (rot != null) {
            sub = sub.xRot(rot.x);
            sub = sub.yRot(rot.y);
            sub = sub.zRot(rot.z);
         }

         Quaternionf quaternion = this.getQuaternion(subParticle, camera, partialTick, ageInSeconds);
         float x = (float)(pos.x - vec3.x() + sub.x);
         float y = (float)(pos.y - vec3.y() + sub.y);
         float z = (float)(pos.z - vec3.z() + sub.z);
         Vector3f size = this.getSubSizeVector(subParticle, partialTick, ageInSeconds);
         float u0 = subParticle.getU0();
         float u1 = subParticle.getU1();
         float v0 = subParticle.getV0();
         float v1 = subParticle.getV1();
         int lightColor = this.getLightColor(partialTick);
         ColorCurve.FloatRGBA color = this.getSubColor(subParticle, partialTick, ageInSeconds);
         this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0F, -1.0F, size, u1, v1, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0F, 1.0F, size, u1, v0, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0F, 1.0F, size, u0, v0, lightColor, color);
         this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0F, -1.0F, size, u0, v1, lightColor, color);
      }
   }

   private Vector3d get(GeoBone bone) {
      BoneSnapshot snapshot = bone.getInitialSnapshot();
      return snapshot == null ? bone.getRotationVector() : bone.getRotationVector().add(snapshot.getRotX(), snapshot.getRotY(), snapshot.getRotZ());
   }

   @Override
   public Vec3 getActualSubPos(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return super.getActualSubPos(subParticle, partialTick, ageInSeconds);
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random) {
         @Override
         public Vec3 getLinearAcceleration() {
            return new Vec3(Math.cos(this.age * 360.0F) * 2.0 * this.random2, this.random2, Math.cos(this.age * 360.0F) * 2.0 * this.random2);
         }
      };
      double distance = 0.1;
      double theta = this.random.nextDouble() * Math.PI;
      double phi = this.random.nextDouble() * 2.0 * Math.PI;
      Vec3 pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta))
         .add(0.0, 0.35, 0.25);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.setVelocity(new Vec3(Mth.randomBetween(this.random, -0.5F, 0.5F), 0.0, 0.0), 1.5F);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PhaseTransition10(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
