package io.github.Memoires.mysticism.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.Memoires.mysticism.registry.item.misc.MysticismEnchantmentEffectComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ApplyMobEffectEntity(Holder<MobEffect> effect, LevelBasedValue durationSeconds, LevelBasedValue amplifier) implements EnchantmentEntityEffect {
   public static final MapCodec<ApplyMobEffectEntity> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(ApplyMobEffectEntity::effect),
            LevelBasedValue.CODEC.fieldOf("durationSeconds").forGetter(ApplyMobEffectEntity::durationSeconds),
            LevelBasedValue.CODEC.fieldOf("amplifier").forGetter(ApplyMobEffectEntity::amplifier)
         )
         .apply(instance, ApplyMobEffectEntity::new)
   );

   public void apply(ServerLevel level, int enchLevel, EnchantedItemInUse inUse, Entity entity, Vec3 pos) {
      if (entity instanceof LivingEntity living) {
         int secs = Math.max(0, Math.round(this.durationSeconds.calculate(enchLevel)));
         int amp = Math.max(0, Math.round(this.amplifier.calculate(enchLevel)));
         if (secs > 0) {
            living.addEffect(new MobEffectInstance(this.effect, secs * 20, amp));
         }
      }
   }

   @NotNull
   public MapCodec<? extends EnchantmentEntityEffect> codec() {
      return (MapCodec<? extends EnchantmentEntityEffect>)MysticismEnchantmentEffectComponents.APPLY_MOB_EFFECT.get();
   }
}
