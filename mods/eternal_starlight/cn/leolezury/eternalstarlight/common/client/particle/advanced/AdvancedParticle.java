package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class AdvancedParticle extends TextureSheetParticle implements ParticleOperator {
   protected SpriteSet sprites;
   private final AdvancedParticleOptions options;

   public AdvancedParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet, AdvancedParticleOptions options) {
      super(clientLevel, x, y, z);
      this.sprites = spriteSet;
      this.options = options;
      this.options.operateSpawn(this);
      this.setSpriteFromAge(this.sprites);
   }

   public void tick() {
      this.stoppedByCollision = false;
      super.tick();
      this.setSpriteFromAge(this.sprites);
      this.options.operateTick(this);
   }

   protected int getLightColor(float f) {
      return 15728880;
   }

   public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
      this.options.operateRender(this);
      super.render(ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(this.options.getRenderType()), camera, f);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   @Override
   public void remove() {
      this.options.operateRemove(this);
      super.remove();
   }

   @Override
   public Vec3 getOldPos() {
      return new Vec3(this.xo, this.yo, this.zo);
   }

   @Override
   public void setOldPos(Vec3 pos) {
      this.xo = pos.x;
      this.yo = pos.y;
      this.zo = pos.z;
   }

   @Override
   public Vec3 getPos() {
      return new Vec3(this.x, this.y, this.z);
   }

   @Override
   public void setPos(Vec3 pos) {
      this.x = pos.x;
      this.y = pos.y;
      this.z = pos.z;
   }

   @Override
   public Vec3 getSpeed() {
      return new Vec3(this.xd, this.yd, this.zd);
   }

   @Override
   public void setSpeed(Vec3 speed) {
      this.xd = speed.x;
      this.yd = speed.y;
      this.zd = speed.z;
   }

   @Override
   public float getOldRoll() {
      return this.oRoll;
   }

   @Override
   public void setOldRoll(float roll) {
      this.oRoll = roll;
   }

   @Override
   public float getRoll() {
      return this.roll;
   }

   @Override
   public void setRoll(float roll) {
      this.roll = roll;
   }

   @Override
   public float getQuadSize() {
      return this.quadSize;
   }

   @Override
   public void setQuadSize(float quadSize) {
      this.quadSize = quadSize;
   }

   @Override
   public float getAge() {
      return this.age
         + Minecraft.getInstance()
            .getTimer()
            .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
   }

   @Override
   public void setAge(int age) {
      this.age = age;
   }

   @Override
   public int getLifetime() {
      return super.getLifetime();
   }

   @Override
   public void setLifetime(int lifetime) {
      super.setLifetime(lifetime);
   }

   @Override
   public Vector4f getColor() {
      return new Vector4f(this.rCol, this.gCol, this.bCol, this.alpha);
   }

   @Override
   public void setColor(Vector4f color) {
      this.rCol = color.x;
      this.gCol = color.y;
      this.bCol = color.z;
      this.alpha = color.w;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
         return new AdvancedParticle(clientLevel, d, e, f, this.spriteSet, new AdvancedParticleOptions().defaultOperators());
      }
   }
}
