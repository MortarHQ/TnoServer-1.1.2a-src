package team.lodestar.lodestone.systems.particle.world;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Color;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.config.ClientConfig;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;

public class LodestoneWorldParticle extends TextureSheetParticle {
   public final LodestoneParticleBehavior behavior;
   public final MutableSpriteSet spriteSet;
   public final SimpleParticleOptions.ParticleSpritePicker spritePicker;
   public final ParticleRenderType renderType;
   public final LodestoneRenderLayer renderLayer;
   public final ColorParticleData colorData;
   public final GenericParticleData transparencyData;
   public final GenericParticleData scaleData;
   @Nullable
   public final GenericParticleData lengthData;
   public final SpinParticleData spinData;
   public final Collection<Consumer<LodestoneWorldParticle>> spawnActors;
   public final Collection<Consumer<LodestoneWorldParticle>> tickActors;
   public final Collection<Consumer<LodestoneWorldParticle>> renderActors;
   public final int particleLight;
   public int lifeDelay;
   private float quadLength;
   float[] hsv1 = new float[3];
   float[] hsv2 = new float[3];

   public LodestoneWorldParticle(
      ClientLevel world, WorldParticleOptions options, MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd
   ) {
      super(world, x, y, z);
      this.behavior = options.behavior;
      this.spriteSet = spriteSet;
      this.spritePicker = options.spritePicker;
      this.renderType = options.renderType;
      this.renderLayer = options.renderLayer;
      this.colorData = options.colorData;
      this.transparencyData = options.transparencyData;
      this.scaleData = options.scaleData;
      this.lengthData = options.lengthData != WorldParticleOptions.DEFAULT_GENERIC ? options.lengthData : null;
      this.spinData = options.spinData;
      this.spawnActors = options.spawnActors;
      this.tickActors = options.tickActors;
      this.renderActors = options.renderActors;
      this.particleLight = options.particleLight;
      this.roll = options.spinData.spinOffset + options.spinData.startingValue;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.setLifetime(options.getLifetime());
      this.lifeDelay = options.getLifeDelay();
      this.gravity = options.getGravity();
      this.friction = options.getFriction();
      this.hasPhysics = !options.noClip;
      this.colorData.rgbToHsv(this.hsv1, this.hsv2);
      if (spriteSet != null) {
         switch (this.spritePicker) {
            case FIRST_INDEX:
            case WITH_AGE:
               this.pickSprite(0);
               break;
            case LAST_INDEX:
            case WITH_AGE_INVERSE:
               this.pickSprite(spriteSet.sprites.size() - 1);
               break;
            case RANDOM_SPRITE:
               this.pickSprite(this.random.nextInt(spriteSet.sprites.size()));
         }
      }

      if (this.lifeDelay == 0) {
         options.spawnActors.forEach(actor -> actor.accept(this));
      }

      this.updateTraits();
   }

   public SimpleParticleOptions.ParticleSpritePicker getSpritePicker() {
      return this.spritePicker;
   }

   public VertexConsumer getVertexConsumer(VertexConsumer original) {
      VertexConsumer consumerToUse = original;
      if (ClientConfig.DELAYED_PARTICLE_RENDERING.getConfigValue() && this.renderType instanceof LodestoneWorldParticleRenderType lodestoneRenderType) {
         consumerToUse = this.renderLayer.getParticleTarget().getBuffer(lodestoneRenderType.renderType);
      }

      return consumerToUse;
   }

   public void pickSprite(int spriteIndex) {
      this.setSprite((TextureAtlasSprite)this.spriteSet.sprites.get(Mth.clamp(spriteIndex, 0, this.spriteSet.sprites.size() - 1)));
   }

   public void pickColor(float colorCoeff) {
      float h = Mth.rotLerp(colorCoeff, 360.0F * this.hsv1[0], 360.0F * this.hsv2[0]) / 360.0F;
      float s = Mth.lerp(colorCoeff, this.hsv1[1], this.hsv2[1]);
      float v = Mth.lerp(colorCoeff, this.hsv1[2], this.hsv2[2]);
      int packed = Color.HSBtoRGB(h, s, v);
      float r = ARGB32.red(packed) / 255.0F;
      float g = ARGB32.green(packed) / 255.0F;
      float b = ARGB32.blue(packed) / 255.0F;
      this.setColor(r, g, b);
   }

   protected void updateTraits() {
      if (!(this.scaleData.getProgress(this.age, this.lifetime) > 0.8F) && !(this.transparencyData.getProgress(this.age, this.lifetime) > 0.8F)
         || !(this.alpha <= 0.0F) && !(this.getQuadSize(0.0F) <= 0.0F) && !(this.getQuadLength(0.0F) <= 0.0F)) {
         this.pickColor(this.colorData.getColorCurve().clamped(this.colorData.getProgress(this.age, this.lifetime), 0.0F, 1.0F));
         this.quadSize = this.scaleData.getValue(this.age, this.lifetime);
         this.quadLength = this.lengthData != null ? this.lengthData.getValue(this.age, this.lifetime) : this.quadSize;
         this.alpha = Mth.clamp(this.transparencyData.getValue(this.age, this.lifetime), 0.0F, 1.0F);
         this.oRoll = this.roll;
         this.roll = this.roll + this.spinData.getValue(this.age, this.lifetime);
         if (!this.tickActors.isEmpty()) {
            this.tickActors.forEach(a -> a.accept(this));
         }

         if (this.behavior != null) {
            this.behavior.tick(this);
         }
      } else {
         this.remove();
      }
   }

   public int getLightColor(float pPartialTick) {
      return this.particleLight == -1 ? super.getLightColor(pPartialTick) : this.particleLight;
   }

   public void tick() {
      if (this.lifeDelay > 0) {
         this.lifeDelay--;
         this.spawnActors.forEach(actor -> actor.accept(this));
         this.xo = this.x;
         this.yo = this.y;
         this.zo = this.z;
      } else {
         this.updateTraits();
         super.tick();
         if (this.spriteSet != null) {
            switch (this.spritePicker) {
               case WITH_AGE:
                  this.setSpriteFromAge(this.spriteSet);
                  break;
               case WITH_AGE_INVERSE:
                  this.setSpriteFromInverseAge(this.spriteSet);
            }
         }
      }
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      if (this.lifeDelay <= 0) {
         this.renderActors.forEach(actor -> actor.accept(this));
         if (this.behavior != null) {
            this.behavior.render(this, this.getVertexConsumer(consumer), camera, partialTicks);
         }
      }
   }

   public ParticleRenderType getRenderType() {
      return this.renderType;
   }

   public void setSpriteFromInverseAge(SpriteSet sprite) {
      if (!this.removed) {
         this.setSprite(sprite.get(this.lifetime - this.age, this.lifetime));
      }
   }

   public float getQuadLength(float partialTicks) {
      return this.quadLength;
   }

   public float getU0() {
      return super.getU0();
   }

   public float getU1() {
      return super.getU1();
   }

   public float getV0() {
      return super.getV0();
   }

   public float getV1() {
      return super.getV1();
   }

   public float getRoll() {
      return this.roll;
   }

   public float getORoll() {
      return this.oRoll;
   }

   public float getRed() {
      return this.rCol;
   }

   public float getGreen() {
      return this.gCol;
   }

   public float getBlue() {
      return this.bCol;
   }

   public float getAlpha() {
      return this.alpha;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public double getXOld() {
      return this.xo;
   }

   public double getYOld() {
      return this.yo;
   }

   public double getZOld() {
      return this.zo;
   }

   public double getXMotion() {
      return this.xd;
   }

   public double getYMotion() {
      return this.yd;
   }

   public double getZMotion() {
      return this.zd;
   }

   public Vec3 getParticlePosition() {
      return new Vec3(this.getX(), this.getY(), this.getZ());
   }

   public void setParticlePosition(Vec3 pos) {
      this.setPos(pos.x, pos.y, pos.z);
   }

   public Vec3 getOldParticlePosition() {
      return new Vec3(this.xo, this.yo, this.zo);
   }

   public void setOldParticlePosition(Vec3 pos) {
      this.xo = pos.x;
      this.yo = pos.y;
      this.zo = pos.z;
   }

   public Vec3 getParticleSpeed() {
      return new Vec3(this.getXMotion(), this.getYMotion(), this.getZMotion());
   }

   public void setParticleSpeed(Vec3 speed) {
      this.setParticleSpeed(speed.x, speed.y, speed.z);
   }

   public int getAge() {
      return this.age;
   }

   public RandomSource getRandom() {
      return this.random;
   }

   public void tick(int times) {
      for (int i = 0; i < times; i++) {
         this.tick();
      }
   }
}
