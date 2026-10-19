package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class FriendsAndFoesStatusEffects {
   public static final ResourcefulRegistry<MobEffect> STATUS_EFFECTS = ResourcefulRegistries.create(BuiltInRegistries.MOB_EFFECT, "friendsandfoes");
   public static final HolderRegistryEntry<MobEffect> REACH = STATUS_EFFECTS.registerHolder(
      "reach", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 16685131)
   );
}
