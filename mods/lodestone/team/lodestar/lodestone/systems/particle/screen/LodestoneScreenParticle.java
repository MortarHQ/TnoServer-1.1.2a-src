package team.lodestar.lodestone.systems.particle.screen;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import org.joml.Vector3d;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.base.TextureSheetScreenParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public class LodestoneScreenParticle extends TextureSheetScreenParticle {
   protected final MutableSpriteSet spriteSet;
   protected final SimpleParticleOptions.ParticleSpritePicker spritePicker;
   private final LodestoneScreenParticleRenderType renderType;
   protected final ColorParticleData colorData;
   protected final GenericParticleData transparencyData;
   protected final GenericParticleData scaleData;
   @Nullable
   protected final GenericParticleData lengthData;
   protected final SpinParticleData spinData;
   public final Collection<Consumer<LodestoneScreenParticle>> tickActors;
   public final Collection<Consumer<LodestoneScreenParticle>> renderActors;
   private final boolean tracksStack;
   private final double stackTrackXOffset;
   private final double stackTrackYOffset;
   private int lifeDelay;
   private float quadLength;
   float[] hsv1 = new float[3];
   float[] hsv2 = new float[3];

   public LodestoneScreenParticle(
      ClientLevel world, ScreenParticleOptions options, MutableSpriteSet spriteSet, double x, double y, double xMotion, double yMotion
   ) {
      super(world, x, y);
      this.spriteSet = spriteSet;
      this.spritePicker = options.spritePicker;
      this.renderType = options.renderType;
      this.colorData = options.colorData;
      this.transparencyData = options.transparencyData;
      this.scaleData = options.scaleData;
      this.lengthData = options.lengthData != WorldParticleOptions.DEFAULT_GENERIC ? options.lengthData : null;
      this.spinData = options.spinData;
      this.tickActors = options.tickActors;
      this.renderActors = options.renderActors;
      this.tracksStack = options.tracksStack;
      this.stackTrackXOffset = options.stackTrackXOffset;
      this.stackTrackYOffset = options.stackTrackYOffset;
      this.roll = options.spinData.spinOffset + options.spinData.startingValue;
      this.setLifetime(options.getLifetime());
      this.lifeDelay = options.getLifeDelay();
      this.gravity = options.getGravity();
      this.friction = options.getFriction();
      this.xMotion = xMotion;
      this.yMotion = yMotion;
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

      options.spawnActors.forEach(actor -> actor.accept(this));
      this.updateTraits();
   }

   @Override
   public float getQuadLength(float partialTicks) {
      return this.lengthData != null ? this.quadLength : super.getQuadLength(partialTicks);
   }

   public SimpleParticleOptions.ParticleSpritePicker getSpritePicker() {
      return this.spritePicker;
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
      if (!(this.scaleData.getProgress(this.age, this.lifetime) > 0.8F) && !(this.transparencyData.getProgress(this.age, this.lifetime) > 0.8F)) {
         this.pickColor(this.colorData.getColorCurve().clamped(this.colorData.getProgress(this.age, this.lifetime), 0.0F, 1.0F));
         this.quadSize = this.scaleData.getValue(this.age, this.lifetime);
         this.quadLength = this.lengthData != null ? this.lengthData.getValue(this.age, this.lifetime) : this.quadSize;
         this.alpha = Mth.clamp(this.transparencyData.getValue(this.age, this.lifetime), 0.0F, 1.0F);
         this.oRoll = this.roll;
         this.roll = this.roll + this.spinData.getValue(this.age, this.lifetime);
         if (!this.tickActors.isEmpty()) {
            this.tickActors.forEach(a -> a.accept(this));
         }
      } else {
         if (this.alpha <= 0.0F || this.getQuadSize(0.0F) <= 0.0F || this.getQuadLength(0.0F) <= 0.0F) {
            this.remove();
         }
      }
   }

   @Override
   public void render(BufferBuilder bufferBuilder, @Nullable PoseStack poseStack) {
      if (this.lifeDelay <= 0) {
         if (this.tracksStack) {
            this.x = ScreenParticleHandler.currentItemX + this.stackTrackXOffset + this.xMoved;
            this.y = ScreenParticleHandler.currentItemY + this.stackTrackYOffset + this.yMoved;
         }

         this.renderActors.forEach(actor -> actor.accept(this));
         super.render(bufferBuilder, poseStack);
      }
   }

   @Override
   public void tick() {
      if (this.lifeDelay > 0) {
         this.lifeDelay--;
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

   @Override
   public LodestoneScreenParticleRenderType getRenderType() {
      return this.renderType;
   }

   public void setSpriteFromInverseAge(SpriteSet sprite) {
      if (!this.removed) {
         this.setSprite(sprite.get(this.lifetime - this.age, this.lifetime));
      }
   }

   public void setParticleSpeed(Vector3d speed) {
      this.setParticleSpeed(speed.x, speed.y);
   }
}
