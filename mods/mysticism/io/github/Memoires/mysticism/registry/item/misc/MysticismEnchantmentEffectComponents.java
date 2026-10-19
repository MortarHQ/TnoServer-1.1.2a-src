package io.github.Memoires.mysticism.registry.item.misc;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.enchantment.effect.ApplyMobEffectEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class MysticismEnchantmentEffectComponents {
   public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECTS = DeferredRegister.create(
      "mysticism", Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE
   );
   public static final RegistrySupplier<MapCodec<ApplyMobEffectEntity>> APPLY_MOB_EFFECT = ENTITY_EFFECTS.register(
      "apply_mob_effect", () -> ApplyMobEffectEntity.CODEC
   );

   public static void init() {
      ENTITY_EFFECTS.register();
   }
}
