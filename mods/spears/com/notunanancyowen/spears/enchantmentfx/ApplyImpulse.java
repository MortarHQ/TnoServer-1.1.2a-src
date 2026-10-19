package com.notunanancyowen.spears.enchantmentfx;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public record ApplyImpulse(Vec3 direction, Vec3 coordinateScale, LevelBasedValue magnitude) implements EnchantmentEntityEffect {
   public static final MapCodec<ApplyImpulse> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Vec3.CODEC.fieldOf("direction").forGetter(ApplyImpulse::direction),
            Vec3.CODEC.fieldOf("coordinate_scale").forGetter(ApplyImpulse::coordinateScale),
            LevelBasedValue.CODEC.fieldOf("magnitude").forGetter(ApplyImpulse::magnitude)
         )
         .apply(instance, ApplyImpulse::new)
   );

   public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
      Vec3 vec3d2 = transformLocalPos(getYawAndPitch(user.getLookAngle()), this.direction)
         .multiply(this.coordinateScale)
         .scale(this.magnitude.calculate(level));
      user.addDeltaMovement(vec3d2);
      user.hurtMarked = true;
      user.hasImpulse = true;
   }

   private static Vec2 getYawAndPitch(Vec3 vec3d) {
      float f = (float)Math.atan2(-vec3d.x, vec3d.z) * (180.0F / (float)Math.PI);
      float g = (float)Math.asin(-vec3d.y / Math.sqrt(vec3d.x * vec3d.x + vec3d.y * vec3d.y + vec3d.z * vec3d.z)) * (180.0F / (float)Math.PI);
      return new Vec2(g, f);
   }

   private static Vec3 transformLocalPos(Vec2 rotation, Vec3 vec) {
      float f = Mth.cos((rotation.y + 90.0F) * (float) (Math.PI / 180.0));
      float g = Mth.sin((rotation.y + 90.0F) * (float) (Math.PI / 180.0));
      float h = Mth.cos(-rotation.x * (float) (Math.PI / 180.0));
      float i = Mth.sin(-rotation.x * (float) (Math.PI / 180.0));
      float j = Mth.cos((-rotation.x + 90.0F) * (float) (Math.PI / 180.0));
      float k = Mth.sin((-rotation.x + 90.0F) * (float) (Math.PI / 180.0));
      Vec3 vec3d = new Vec3(f * h, i, g * h);
      Vec3 vec3d2 = new Vec3(f * j, k, g * j);
      Vec3 vec3d3 = vec3d.cross(vec3d2).scale(-1.0);
      double d = vec3d.x * vec.z + vec3d2.x * vec.y + vec3d3.x * vec.x;
      double e = vec3d.y * vec.z + vec3d2.y * vec.y + vec3d3.y * vec.x;
      double l = vec3d.z * vec.z + vec3d2.z * vec.y + vec3d3.z * vec.x;
      return new Vec3(d, e, l);
   }

   public MapCodec<ApplyImpulse> codec() {
      return CODEC;
   }
}
