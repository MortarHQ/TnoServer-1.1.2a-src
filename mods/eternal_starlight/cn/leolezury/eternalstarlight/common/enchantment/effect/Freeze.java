package cn.leolezury.eternalstarlight.common.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record Freeze(LevelBasedValue duration) implements EnchantmentEntityEffect {
   public static final MapCodec<Freeze> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(LevelBasedValue.CODEC.fieldOf("duration").forGetter(freeze -> freeze.duration)).apply(instance, Freeze::new)
   );

   public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
      entity.setTicksFrozen(Math.min((int)(entity.getTicksFrozen() + this.duration.calculate(enchantmentLevel) * 20.0F), 600));
   }

   public MapCodec<Freeze> codec() {
      return CODEC;
   }
}
