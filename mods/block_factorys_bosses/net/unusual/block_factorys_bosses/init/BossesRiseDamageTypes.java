package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.unusual.block_factorys_bosses.BossesRise;

public class BossesRiseDamageTypes {
   public static final ResourceKey<DamageType> CANNONBALL_HIT = ResourceKey.create(Registries.DAMAGE_TYPE, BossesRise.prefix("cannonball_hit"));
   public static final ResourceKey<DamageType> KRAKEN_TENTACLE_SMASH = ResourceKey.create(Registries.DAMAGE_TYPE, BossesRise.prefix("kraken_tentacle_smash"));
}
