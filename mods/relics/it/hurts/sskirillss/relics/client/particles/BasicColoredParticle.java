package it.hurts.sskirillss.relics.client.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.init.ParticleRegistry;
import java.awt.Color;
import javax.annotation.Nonnull;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class BasicColoredParticle extends TextureSheetParticle {
   private final BasicColoredParticle.Constructor constructor;
   private float oldQuadSize;
   private float currentQuadSize;
   public static final ParticleRenderType RENDERER_NO_DEPTH = new ParticleRenderType() {
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.disableDepthTest();
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
         return "relics:basic_colored_no_depth";
      }
   };

   public BasicColoredParticle(
      ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BasicColoredParticle.Constructor constructor
   ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.setColor(constructor.getColor().getRed() / 255.0F, constructor.getColor().getGreen() / 255.0F, constructor.getColor().getBlue() / 255.0F);
      this.setSize(constructor.getDiameter(), constructor.getDiameter());
      this.setAlpha(constructor.getColor().getAlpha() / 255.0F);
      this.setLifetime(constructor.getLifetime());
      this.constructor = constructor;
      this.quadSize = constructor.getDiameter();
      this.hasPhysics = constructor.isPhysical();
      this.oldQuadSize = this.quadSize;
      this.currentQuadSize = this.quadSize;
      this.xd = velocityX;
      this.yd = velocityY;
      this.zd = velocityZ;
   }

   public void tick() {
      this.oldQuadSize = this.quadSize;
      this.currentQuadSize = this.currentQuadSize * this.constructor.getScaleModifier();
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.oRoll = this.roll;
      this.roll = this.roll + this.constructor.getRoll();
      this.move(this.xd, this.yd, this.zd);
      if (this.age++ >= this.lifetime) {
         this.remove();
      }
   }

   protected int getLightColor(float partialTick) {
      return 15728880;
   }

   @Nonnull
   public ParticleRenderType getRenderType() {
      return this.constructor.isVisibleThroughWalls() ? RENDERER_NO_DEPTH : ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      this.quadSize = Mth.lerp(partialTicks, this.oldQuadSize, this.currentQuadSize);
      RenderSystem.blendFunc(770, 1);
      super.render(buffer, renderInfo, partialTicks);
   }

   public static class Constructor {
      private Color color;
      private float diameter;
      private float roll;
      private boolean physical;
      private boolean visibleThroughWalls;
      private int lifetime;
      private float scaleModifier;

      private static float $default$diameter() {
         return 1.0F;
      }

      private static float $default$roll() {
         return 0.0F;
      }

      private static boolean $default$physical() {
         return true;
      }

      private static boolean $default$visibleThroughWalls() {
         return false;
      }

      private static int $default$lifetime() {
         return 20;
      }

      private static float $default$scaleModifier() {
         return 1.0F;
      }

      Constructor(Color color, float diameter, float roll, boolean physical, boolean visibleThroughWalls, int lifetime, float scaleModifier) {
         this.color = color;
         this.diameter = diameter;
         this.roll = roll;
         this.physical = physical;
         this.visibleThroughWalls = visibleThroughWalls;
         this.lifetime = lifetime;
         this.scaleModifier = scaleModifier;
      }

      public static BasicColoredParticle.Constructor.ConstructorBuilder builder() {
         return new BasicColoredParticle.Constructor.ConstructorBuilder();
      }

      public Color getColor() {
         return this.color;
      }

      public float getDiameter() {
         return this.diameter;
      }

      public float getRoll() {
         return this.roll;
      }

      public boolean isPhysical() {
         return this.physical;
      }

      public boolean isVisibleThroughWalls() {
         return this.visibleThroughWalls;
      }

      public int getLifetime() {
         return this.lifetime;
      }

      public float getScaleModifier() {
         return this.scaleModifier;
      }

      public void setColor(Color color) {
         this.color = color;
      }

      public void setDiameter(float diameter) {
         this.diameter = diameter;
      }

      public void setRoll(float roll) {
         this.roll = roll;
      }

      public void setPhysical(boolean physical) {
         this.physical = physical;
      }

      public void setVisibleThroughWalls(boolean visibleThroughWalls) {
         this.visibleThroughWalls = visibleThroughWalls;
      }

      public void setLifetime(int lifetime) {
         this.lifetime = lifetime;
      }

      public void setScaleModifier(float scaleModifier) {
         this.scaleModifier = scaleModifier;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof BasicColoredParticle.Constructor other)) {
            return false;
         } else {
            if (!other.canEqual(this)) {
               return false;
            }

            if (Float.compare(this.getDiameter(), other.getDiameter()) != 0) {
               return false;
            }

            if (Float.compare(this.getRoll(), other.getRoll()) != 0) {
               return false;
            }

            if (this.isPhysical() != other.isPhysical()) {
               return false;
            }

            if (this.isVisibleThroughWalls() != other.isVisibleThroughWalls()) {
               return false;
            }

            if (this.getLifetime() != other.getLifetime()) {
               return false;
            }

            if (Float.compare(this.getScaleModifier(), other.getScaleModifier()) != 0) {
               return false;
            }

            Object this$color = this.getColor();
            Object other$color = other.getColor();
            return this$color == null ? other$color == null : this$color.equals(other$color);
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof BasicColoredParticle.Constructor;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + Float.floatToIntBits(this.getDiameter());
         result = result * 59 + Float.floatToIntBits(this.getRoll());
         result = result * 59 + (this.isPhysical() ? 79 : 97);
         result = result * 59 + (this.isVisibleThroughWalls() ? 79 : 97);
         result = result * 59 + this.getLifetime();
         result = result * 59 + Float.floatToIntBits(this.getScaleModifier());
         Object $color = this.getColor();
         return result * 59 + ($color == null ? 43 : $color.hashCode());
      }

      @Override
      public String toString() {
         return "BasicColoredParticle.Constructor(color="
            + this.getColor()
            + ", diameter="
            + this.getDiameter()
            + ", roll="
            + this.getRoll()
            + ", physical="
            + this.isPhysical()
            + ", visibleThroughWalls="
            + this.isVisibleThroughWalls()
            + ", lifetime="
            + this.getLifetime()
            + ", scaleModifier="
            + this.getScaleModifier()
            + ")";
      }

      public static class ConstructorBuilder {
         private boolean diameter$set;
         private float diameter$value;
         private boolean roll$set;
         private float roll$value;
         private boolean physical$set;
         private boolean physical$value;
         private boolean visibleThroughWalls$set;
         private boolean visibleThroughWalls$value;
         private boolean lifetime$set;
         private int lifetime$value;
         private boolean scaleModifier$set;
         private float scaleModifier$value;
         private Color color = new Color(-1, true);

         public BasicColoredParticle.Constructor.ConstructorBuilder color(int color) {
            this.color = new Color(color, true);
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder color(float r, float g, float b, float a) {
            return this.color(new Color(r, g, b, a).getRGB());
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder color(float r, float g, float b) {
            return this.color(r, g, b, 1.0F);
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder color(int r, int g, int b, int a) {
            return this.color(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder color(int r, int g, int b) {
            return this.color(r, g, b, 255);
         }

         ConstructorBuilder() {
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder diameter(float diameter) {
            this.diameter$value = diameter;
            this.diameter$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder roll(float roll) {
            this.roll$value = roll;
            this.roll$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder physical(boolean physical) {
            this.physical$value = physical;
            this.physical$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder visibleThroughWalls(boolean visibleThroughWalls) {
            this.visibleThroughWalls$value = visibleThroughWalls;
            this.visibleThroughWalls$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder lifetime(int lifetime) {
            this.lifetime$value = lifetime;
            this.lifetime$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor.ConstructorBuilder scaleModifier(float scaleModifier) {
            this.scaleModifier$value = scaleModifier;
            this.scaleModifier$set = true;
            return this;
         }

         public BasicColoredParticle.Constructor build() {
            float diameter$value = this.diameter$value;
            if (!this.diameter$set) {
               diameter$value = BasicColoredParticle.Constructor.$default$diameter();
            }

            float roll$value = this.roll$value;
            if (!this.roll$set) {
               roll$value = BasicColoredParticle.Constructor.$default$roll();
            }

            boolean physical$value = this.physical$value;
            if (!this.physical$set) {
               physical$value = BasicColoredParticle.Constructor.$default$physical();
            }

            boolean visibleThroughWalls$value = this.visibleThroughWalls$value;
            if (!this.visibleThroughWalls$set) {
               visibleThroughWalls$value = BasicColoredParticle.Constructor.$default$visibleThroughWalls();
            }

            int lifetime$value = this.lifetime$value;
            if (!this.lifetime$set) {
               lifetime$value = BasicColoredParticle.Constructor.$default$lifetime();
            }

            float scaleModifier$value = this.scaleModifier$value;
            if (!this.scaleModifier$set) {
               scaleModifier$value = BasicColoredParticle.Constructor.$default$scaleModifier();
            }

            return new BasicColoredParticle.Constructor(
               this.color, diameter$value, roll$value, physical$value, visibleThroughWalls$value, lifetime$value, scaleModifier$value
            );
         }

         @Override
         public String toString() {
            return "BasicColoredParticle.Constructor.ConstructorBuilder(color="
               + this.color
               + ", diameter$value="
               + this.diameter$value
               + ", roll$value="
               + this.roll$value
               + ", physical$value="
               + this.physical$value
               + ", visibleThroughWalls$value="
               + this.visibleThroughWalls$value
               + ", lifetime$value="
               + this.lifetime$value
               + ", scaleModifier$value="
               + this.scaleModifier$value
               + ")";
         }
      }
   }

   public static class Factory implements ParticleProvider<BasicColoredParticle.Options> {
      private final SpriteSet sprites;

      public Factory(SpriteSet sprites) {
         this.sprites = sprites;
      }

      @Nullable
      public Particle createParticle(
         BasicColoredParticle.Options options, ClientLevel world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity
      ) {
         BasicColoredParticle particle = new BasicColoredParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity, options.getData());
         particle.pickSprite(this.sprites);
         return particle;
      }
   }

   public static class Options implements ParticleOptions {
      private final BasicColoredParticle.Constructor data;
      public static final MapCodec<BasicColoredParticle.Options> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.INT.fieldOf("color").forGetter(options -> options.getData().getColor().getRGB()),
               Codec.FLOAT.fieldOf("diameter").forGetter(options -> options.getData().getDiameter()),
               Codec.INT.fieldOf("lifetime").forGetter(options -> options.getData().getLifetime()),
               Codec.FLOAT.fieldOf("roll").forGetter(options -> options.getData().getRoll()),
               Codec.FLOAT.fieldOf("scaleModifier").forGetter(options -> options.getData().getScaleModifier()),
               Codec.BOOL.fieldOf("visibleThroughWalls").forGetter(options -> options.getData().isVisibleThroughWalls())
            )
            .apply(instance, BasicColoredParticle.Options::new)
      );
      public static final StreamCodec<ByteBuf, BasicColoredParticle.Options> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.INT,
         options -> options.getData().getColor().getRGB(),
         ByteBufCodecs.FLOAT,
         options -> options.getData().getDiameter(),
         ByteBufCodecs.INT,
         options -> options.getData().getLifetime(),
         ByteBufCodecs.FLOAT,
         options -> options.getData().getRoll(),
         ByteBufCodecs.FLOAT,
         options -> options.getData().getScaleModifier(),
         ByteBufCodecs.BOOL,
         options -> options.getData().isVisibleThroughWalls(),
         BasicColoredParticle.Options::new
      );

      private Options(int color, float diameter, int lifetime, float roll, float scaleModifier, boolean visibleThroughWalls) {
         this.data = BasicColoredParticle.Constructor.builder()
            .color(color)
            .diameter(diameter)
            .lifetime(lifetime)
            .roll(roll)
            .scaleModifier(scaleModifier)
            .visibleThroughWalls(visibleThroughWalls)
            .build();
      }

      public Options(BasicColoredParticle.Constructor data) {
         this.data = data;
      }

      @Nonnull
      public ParticleType<BasicColoredParticle.Options> getType() {
         return (ParticleType<BasicColoredParticle.Options>)ParticleRegistry.BASIC_COLORED.get();
      }

      public BasicColoredParticle.Constructor getData() {
         return this.data;
      }
   }

   public static class Type extends ParticleType<BasicColoredParticle.Options> {
      public Type() {
         super(false);
      }

      public MapCodec<BasicColoredParticle.Options> codec() {
         return BasicColoredParticle.Options.CODEC;
      }

      public StreamCodec<? super RegistryFriendlyByteBuf, BasicColoredParticle.Options> streamCodec() {
         return BasicColoredParticle.Options.STREAM_CODEC;
      }
   }
}
