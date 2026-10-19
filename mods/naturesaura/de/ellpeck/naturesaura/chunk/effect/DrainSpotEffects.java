package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;

public final class DrainSpotEffects {
   public static void init() {
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(GrassDieEffect.NAME, GrassDieEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(PlantBoostEffect.NAME, PlantBoostEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(ReplenishingEffect.NAME, ReplenishingEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(BalanceEffect.NAME, BalanceEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(ExplosionEffect.NAME, ExplosionEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(BreathlessEffect.NAME, BreathlessEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(SpreadEffect.NAME, SpreadEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(CacheRechargeEffect.NAME, CacheRechargeEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(AnimalEffect.NAME, AnimalEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(OreSpawnEffect.NAME, OreSpawnEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(NetherGrassEffect.NAME, NetherGrassEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(NetherDecayEffect.NAME, NetherDecayEffect::new);
      NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(AngerEffect.NAME, AngerEffect::new);
      NaturesAuraAPI.EFFECT_POWDERS.put(PlantBoostEffect.NAME, 12776514);
      NaturesAuraAPI.EFFECT_POWDERS.put(CacheRechargeEffect.NAME, 2076881);
      NaturesAuraAPI.EFFECT_POWDERS.put(AnimalEffect.NAME, 13124340);
      NaturesAuraAPI.EFFECT_POWDERS.put(OreSpawnEffect.NAME, 7663064);
      NaturesAuraAPI.EFFECT_POWDERS.put(NetherGrassEffect.NAME, 14701094);
   }
}
