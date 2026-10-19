package com.bobmowzie.mowziesmobs.client.particle.types;

import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.server.message.NetworkHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class AdvancedParticleType implements ParticleOptions {
   public static final MapCodec<AdvancedParticleType> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            BuiltInRegistries.PARTICLE_TYPE.holderByNameCodec().fieldOf("type").forGetter(AdvancedParticleType::type),
            ParticleRotation.CODEC.fieldOf("rotation").forGetter(AdvancedParticleType::rotation),
            Codec.FLOAT.fieldOf("red").forGetter(AdvancedParticleType::red),
            Codec.FLOAT.fieldOf("green").forGetter(AdvancedParticleType::green),
            Codec.FLOAT.fieldOf("blue").forGetter(AdvancedParticleType::blue),
            Codec.FLOAT.fieldOf("alpha").forGetter(AdvancedParticleType::alpha),
            Codec.FLOAT.fieldOf("scale").forGetter(AdvancedParticleType::scale),
            Codec.FLOAT.fieldOf("duration").forGetter(AdvancedParticleType::duration),
            Codec.FLOAT.fieldOf("air_drag").forGetter(AdvancedParticleType::airDrag),
            Codec.BOOL.fieldOf("emissive").forGetter(AdvancedParticleType::emissive),
            Codec.BOOL.fieldOf("can_collide").forGetter(AdvancedParticleType::canCollide)
         )
         .apply(instance, AdvancedParticleType::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, AdvancedParticleType> STREAM_CODEC = NetworkHandler.composite(
      ByteBufCodecs.holderRegistry(Registries.PARTICLE_TYPE),
      AdvancedParticleType::type,
      ByteBufCodecs.fromCodecWithRegistries(ParticleRotation.CODEC),
      AdvancedParticleType::rotation,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::red,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::green,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::blue,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::alpha,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::scale,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::duration,
      ByteBufCodecs.FLOAT,
      AdvancedParticleType::airDrag,
      ByteBufCodecs.BOOL,
      AdvancedParticleType::emissive,
      ByteBufCodecs.BOOL,
      AdvancedParticleType::canCollide,
      AdvancedParticleType::new
   );
   @NotNull
   private final Holder<ParticleType<?>> type;
   @NotNull
   private final ParticleRotation rotation;
   @NotNull
   private final ParticleComponent[] components;
   private final float red;
   private final float green;
   private final float blue;
   private final float alpha;
   private final float scale;
   private final float duration;
   private final float airDrag;
   private final boolean emissive;
   private final boolean canCollide;

   public AdvancedParticleType(AdvancedParticleType base) {
      this(
         base.type(),
         base.rotation(),
         base.components(),
         base.red(),
         base.green(),
         base.blue(),
         base.alpha(),
         base.scale(),
         base.duration(),
         base.airDrag(),
         base.emissive(),
         base.canCollide()
      );
   }

   public AdvancedParticleType(
      @NotNull Holder<ParticleType<?>> type,
      @NotNull ParticleRotation rotation,
      float red,
      float green,
      float blue,
      float alpha,
      float scale,
      float duration,
      float airDrag,
      boolean emissive,
      boolean canCollide
   ) {
      this(type, rotation, new ParticleComponent[0], red, green, blue, alpha, scale, duration, airDrag, emissive, canCollide);
   }

   public AdvancedParticleType(
      @NotNull Holder<ParticleType<?>> type,
      @NotNull ParticleRotation rotation,
      @NotNull ParticleComponent[] components,
      float red,
      float green,
      float blue,
      float alpha,
      float scale,
      float duration,
      float airDrag,
      boolean emissive,
      boolean canCollide
   ) {
      this.type = type;
      this.rotation = rotation;
      this.components = components;
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.scale = scale;
      this.duration = duration;
      this.airDrag = airDrag;
      this.emissive = emissive;
      this.canCollide = canCollide;
   }

   @NotNull
   public ParticleType<?> getType() {
      return (ParticleType<?>)this.type.value();
   }

   public Holder<ParticleType<?>> type() {
      return this.type;
   }

   public float red() {
      return this.red;
   }

   public float green() {
      return this.green;
   }

   public float blue() {
      return this.blue;
   }

   public float alpha() {
      return this.alpha;
   }

   public float airDrag() {
      return this.airDrag;
   }

   public float scale() {
      return this.scale;
   }

   public boolean emissive() {
      return this.emissive;
   }

   public boolean canCollide() {
      return this.canCollide;
   }

   public float duration() {
      return this.duration;
   }

   public ParticleRotation rotation() {
      return this.rotation;
   }

   public ParticleComponent[] components() {
      return this.components;
   }
}
