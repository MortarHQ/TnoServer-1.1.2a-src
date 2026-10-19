package artifacts.config;

import artifacts.Artifacts;
import artifacts.component.ability.ToolTierUpgrade;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.network.NetworkHandler;
import artifacts.network.UpdateItemConfigPacket;
import artifacts.registry.ModItems;
import artifacts.registry.RegistryHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class ItemConfigs extends ConfigManager {
   private final Map<ResourceKey<Item>, Value.ConfigValue<Boolean>> generateAsLoot = new HashMap<>();
   public final Value.ConfigValue<Boolean> antidoteVesselEnabled = this.defineBool(
      createKey(ModItems.ANTIDOTE_VESSEL, "enabled"), "Whether the Antidote Vessel reduces the duration of negative effects"
   );
   public final Value.ConfigValue<Boolean> aquaDashersEnabled = this.defineBool(
      createKey(ModItems.AQUA_DASHERS, "enabled"), "Whether the Aqua-Dashers allow the wearer to sprint on water"
   );
   public final Value.ConfigValue<Boolean> charmOfSinkingEnabled = this.defineBool(
      createKey(ModItems.CHARM_OF_SINKING, "enabled"), true, true, "Whether the Charm of Sinking removes the wearer's collision with water"
   );
   public final Value.ConfigValue<Boolean> cloudInABottleEnabled = this.defineBool(
      createKey(ModItems.CLOUD_IN_A_BOTTLE, "enabled"), "Whether the Cloud in a Bottle allows the wearer to double jump"
   );
   public final Value.ConfigValue<Boolean> eternalSteakEnabled = this.defineBool(
      createKey(ModItems.ETERNAL_STEAK, "enabled"), "Whether the Eternal Steak can be eaten"
   );
   public final Value.ConfigValue<Boolean> everlastingBeefEnabled = this.defineBool(
      createKey(ModItems.EVERLASTING_BEEF, "enabled"), "Whether the Everlasting Beef can be eaten"
   );
   public final Value.ConfigValue<Boolean> pickaxeHeaterEnabled = this.defineBool(
      createKey(ModItems.PICKAXE_HEATER, "enabled"), "Whether the Pickaxe Heater smelts mined ores"
   );
   public final Value.ConfigValue<Boolean> rootedBootsEnabled = this.defineBool(
      createKey(ModItems.ROOTED_BOOTS, "enabled"), "Whether the Rooted Boots replenish hunger when standing on grass"
   );
   public final Value.ConfigValue<Boolean> scarfOfInvisibilityEnabled = this.defineBool(
      createKey(ModItems.SCARF_OF_INVISIBILITY, "enabled"), true, true, "Whether the Scarf of Invisibility makes players invisible"
   );
   public final Value.ConfigValue<Boolean> striderShoesEnabled = this.defineBool(
      createKey(ModItems.STRIDER_SHOES, "enabled"), "Whether the Strider Shoes allow sneaking on lava"
   );
   public final Value.ConfigValue<Boolean> warpDriveEnabled = this.defineBool(
      createKey(ModItems.WARP_DRIVE, "enabled"), "Whether the Warp Drive causes ender pearls to not be consumed"
   );
   public final Value.ConfigValue<Boolean> chorusTotemConsumeOnUse = this.defineBool(
      createKey(ModItems.CHORUS_TOTEM, "consumeOnUse"), "Whether the Chorus Totem is consumed after activating"
   );
   public final Value.ConfigValue<Boolean> flamePendantGrantFireResistance = this.defineBool(
      createKey(ModItems.FLAME_PENDANT, "grantFireResistance"), "Whether the Flame Pendant grants Fire Resistance after igniting an entity"
   );
   public final Value.ConfigValue<Boolean> rootedBootsGrowPlantsAfterEating = this.defineBool(
      createKey(ModItems.ROOTED_BOOTS, "growPlantsAfterEating"), "Whether the Rooted Boots apply a bone meal effect after eating food"
   );
   public final Value.ConfigValue<Boolean> scarfOfInvisibilityHideWhenInvisible = this.defineBool(
      createKey(ModItems.SCARF_OF_INVISIBILITY, "hideWhenInvisible"), false, false, "Whether the Scarf of Invisibility is hidden when the wearer is invisible"
   );
   public final Value.ConfigValue<Boolean> shockPendantCancelLightningDamage = this.defineBool(
      createKey(ModItems.SHOCK_PENDANT, "cancelLightningDamage"), "Whether the Shock Pendant cancels damage from lightning"
   );
   public final Value.ConfigValue<Boolean> snorkelIsInfinite = this.defineBool(
      createKey(ModItems.SNORKEL, "isInfinite"), false, true, "Whether the Snorkel's water breathing effect depletes when underwater"
   );
   public final Value.ConfigValue<Boolean> snowshoesAllowWalkingOnPowderedSnow = this.defineBool(
      createKey(ModItems.SNOWSHOES, "allowWalkingOnPowderedSnow"), "Whether the Snowshoes allow the wearer to walk on powdered snow"
   );
   public final Value.ConfigValue<Boolean> striderShoesCancelHotFloorDamage = this.defineBool(
      createKey(ModItems.STRIDER_SHOES, "cancelHotFloorDamage"), "Whether the Strider Shoes make the wearer immune to hot floor damage"
   );
   public final Value.ConfigValue<Boolean> umbrellaIsShield = this.defineBool(
      createKey(ModItems.UMBRELLA, "isShield"), "Whether the Umbrella can be used as a shield"
   );
   public final Value.ConfigValue<Boolean> umbrellaIsGlider = this.defineBool(
      createKey(ModItems.UMBRELLA, "isGlider"), "Whether the Umbrella slows the player's falling speed when held"
   );
   public final Value.ConfigValue<Boolean> warpDriveNullifyEnderPearlDamage = this.defineBool(
      createKey(ModItems.WARP_DRIVE, "nullifyEnderPearlDamage"), "Whether the Warp Drive causes Ender Pearls not to deal any damage"
   );
   public final Value.ConfigValue<Boolean> bunnyHoppersModifyHurtSounds = this.defineBool(
      createKey(ModItems.BUNNY_HOPPERS, "modifyHurtSounds"), "Whether the Bunny Hoppers change the player's hurt sounds"
   );
   public final Value.ConfigValue<Boolean> kittySlippersModifyHurtSounds = this.defineBool(
      createKey(ModItems.KITTY_SLIPPERS, "modifyHurtSounds"), "Whether the Kitty Slippers change the player's hurt sounds"
   );
   public final Value.ConfigValue<Boolean> kittySlippersRepelCreepers = this.defineBool(
      createKey(ModItems.KITTY_SLIPPERS, "repelCreepers"), "Whether the Kitty Slippers scare nearby creepers"
   );
   public final Value.ConfigValue<Boolean> kittySlippersRepelPhantoms = this.defineBool(
      createKey(ModItems.KITTY_SLIPPERS, "repelPhantoms"), "Whether the Kitty Slippers hiss at nearby phantoms"
   );
   public final Value.ConfigValue<Boolean> charmOfSinkingUnderwaterFallDamage = this.defineBool(
      createKey(ModItems.CHARM_OF_SINKING, "underwaterFallDamage"),
      false,
      true,
      "Whether it is possible to take fall damage underwater when wearing the Charm of Sinking"
   );
   public final Value.ConfigValue<Double> cloudInABottleSprintJumpVerticalVelocity = this.defineNonNegativeDouble(
      createKey(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpVerticalVelocity"),
      0.25,
      "The amount of extra vertical velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle"
   );
   public final Value.ConfigValue<Double> cloudInABottleSprintJumpHorizontalVelocity = this.defineNonNegativeDouble(
      createKey(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpHorizontalVelocity"),
      0.25,
      "The amount of extra horizontal velocity that is applied to players that double jump while sprinting using the Cloud in a Bottle"
   );
   public final Value.ConfigValue<Double> vampiricGloveAbsorptionRatio = this.defineNonNegativeDouble(
      createKey(ModItems.VAMPIRIC_GLOVE, "absorptionRatio"), 0.2, "The proportion of melee damage dealt that is absorbed by the Vampiric Gloves"
   );
   public final Value.ConfigValue<Double> bunnyHoppersFallDamageMultiplier = this.defineAttributeModifier(
      createKey(ModItems.BUNNY_HOPPERS, "fallDamageMultiplier"),
      0.0,
      "How much the Bunny Hoppers reduce or increase fall damage",
      "Values between -1 and 0 reduce fall damage",
      "Values above 0 increase fall damage"
   );
   public final Value.ConfigValue<Double> bunnyHoppersJumpStrengthBonus = this.defineAttributeModifier(
      createKey(ModItems.BUNNY_HOPPERS, "jumpStrengthBonus"), 0.4, "The amount of extra jump strength the Bunny Hoppers apply to players"
   );
   public final Value.ConfigValue<Double> bunnyHoppersSafeFallDistanceBonus = this.defineAttributeModifier(
      createKey(ModItems.BUNNY_HOPPERS, "safeFallDistanceBonus"), 10.0, "The amount of extra safe fall distance in blocks that is granted by the Bunny Hoppers"
   );
   public final Value.ConfigValue<Double> charmOfShrinkingScaleModifier = this.defineAttributeModifier(
      createKey(ModItems.CHARM_OF_SHRINKING, "scaleModifier"),
      -0.5,
      "How much the Charm of Shrinking decreases or increases the player's Scale",
      "Values between -1 and 0 reduce the player's scale",
      "Values above 0 increase the player's scale"
   );
   public final Value.ConfigValue<Double> cloudInABottleSafeFallDistanceBonus = this.defineAttributeModifier(
      createKey(ModItems.CLOUD_IN_A_BOTTLE, "safeFallDistanceBonus"),
      3.0,
      "The amount of extra safe fall distance in blocks that is granted by the Cloud in a Bottle"
   );
   public final Value.ConfigValue<Double> cowboyHatMountSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.COWBOY_HAT, "mountSpeedBonus"), 0.4, "How much the Cowboy Hat increases the speed of ridden mounts"
   );
   public final Value.ConfigValue<Double> crossNecklaceBonusInvincibilityTicks = this.defineAttributeModifier(
      createKey(ModItems.CROSS_NECKLACE, "bonusInvincibilityTicks"),
      20.0,
      "The amount of extra ticks the player stays invincible for after taking damage while wearing the Cross Necklace"
   );
   public final Value.ConfigValue<Double> crystalHeartHealthBonus = this.defineAttributeModifier(
      createKey(ModItems.CRYSTAL_HEART, "healthBonus"), 10.0, "The amount of extra health points that are granted by the Crystal Heart"
   );
   public final Value.ConfigValue<Double> diggingClawsBlockBreakSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.DIGGING_CLAWS, "blockBreakSpeedBonus"), 0.3, "How much the Digging Claws increase the wearer's mining speed"
   );
   public final Value.ConfigValue<Double> feralClawsAttackSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.FERAL_CLAWS, "attackSpeedBonus"), 0.3, "How much the Feral Claws increase the wearer's attack speed"
   );
   public final Value.ConfigValue<Double> fireGauntletFireDuration = this.defineAttributeModifier(
      createKey(ModItems.FIRE_GAUNTLET, "fireDuration"),
      8.0,
      "How long an entity is set on fire for after being attacked by an entity wearing the Fire Gauntlet"
   );
   public final Value.ConfigValue<Double> flippersSwimSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.FLIPPERS, "swimSpeedBonus"), 0.7, "How much the Flippers increase the wearer's swim speed"
   );
   public final Value.ConfigValue<Double> goldenHookEntityExperienceBonus = this.defineAttributeModifier(
      createKey(ModItems.GOLDEN_HOOK, "entityExperienceBonus"),
      0.5,
      "The amount of extra experience dropped by entities that are killed by players wearing the Golden Hook"
   );
   public final Value.ConfigValue<Double> noveltyDrinkingHatDrinkingSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.NOVELTY_DRINKING_HAT, "drinkingSpeedBonus"), 1.5, "How much the Novelty Drinking Hat increases the wearer's drinking speed"
   );
   public final Value.ConfigValue<Double> noveltyDrinkingHatEatingSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.NOVELTY_DRINKING_HAT, "eatingSpeedBonus"), 0.5, "How much the Novelty Drinking Hat increases the wearer's eating speed"
   );
   public final Value.ConfigValue<Double> plasticDrinkingHatDrinkingSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.PLASTIC_DRINKING_HAT, "drinkingSpeedBonus"), 1.5, "How much the Plastic Drinking Hat increases the wearer's drinking speed"
   );
   public final Value.ConfigValue<Double> plasticDrinkingHatEatingSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.PLASTIC_DRINKING_HAT, "eatingSpeedBonus"), 0.5, "How much the Plastic Drinking Hat increases the wearer's eating speed"
   );
   public final Value.ConfigValue<Double> pocketPistonAttackKnockbackBonus = this.defineAttributeModifier(
      createKey(ModItems.POCKET_PISTON, "attackKnockbackBonus"), 0.75, "The amount of extra knockback that is granted by the Pocket Piston"
   );
   public final Value.ConfigValue<Double> powerGloveAttackDamageBonus = this.defineAttributeModifier(
      createKey(ModItems.POWER_GLOVE, "attackDamageBonus"),
      4.0,
      "The amount of extra damage that is dealt by melee attacks from players wearing the Power Glove"
   );
   public final Value.ConfigValue<Double> runningShoesSprintingSpeedBonus = this.defineAttributeModifier(
      createKey(ModItems.RUNNING_SHOES, "sprintingSpeedBonus"), 0.4, "How much the Running Shoes increase the wearer's sprinting speed"
   );
   public final Value.ConfigValue<Double> runningShoesSprintingStepHeightBonus = this.defineAttributeModifier(
      createKey(ModItems.RUNNING_SHOES, "sprintingStepHeightBonus"), 0.5, "How much the Running Shoes increase the wearer's step height while sprinting"
   );
   public final Value.ConfigValue<Double> snowshoesMovementSpeedOnSnowBonus = this.defineAttributeModifier(
      createKey(ModItems.SNOWSHOES, "movementSpeedOnSnowBonus"), 0.3, "How much the Snowshoes increase the wearer's movement speed on snow blocks"
   );
   public final Value.ConfigValue<Double> steadfastSpikesKnockbackResistance = this.defineAttributeModifier(
      createKey(ModItems.STEADFAST_SPIKES, "knockbackResistance"), 1.0, "How much knockback resistance is granted by the Steadfast Spikes"
   );
   public final Value.ConfigValue<Double> steadfastSpikesSlipperinessReduction = this.defineAttributeModifier(
      createKey(ModItems.STEADFAST_SPIKES, "slipperinessReduction"), 1.0, "How much the Steadfast Spikes reduce the slipperiness of ice"
   );
   public final Value.ConfigValue<Double> villagerHatReputationBonus = this.defineAttributeModifier(
      createKey(ModItems.VILLAGER_HAT, "reputationBonus"),
      75.0,
      "The amount of extra reputation that is granted by the Villager Hat when trading with villagers"
   );
   public final Value.ConfigValue<Double> charmOfSinkingOxygenBonus = this.defineAttributeModifier(
      createKey(ModItems.CHARM_OF_SINKING, "oxygenBonus"), 1.5, "How much longer players wearing the Charm of Sinking can stay underwater"
   );
   public final Value.ConfigValue<Double> whoopeeCushionFartChance = this.defineFraction(
      createKey(ModItems.WHOOPEE_CUSHION, "fartChance"),
      0.12,
      "The probability that a fart sound plays when sneaking or double jumping while wearing the Whoopee Cushion"
   );
   public final Value.ConfigValue<Double> chorusTotemTeleportationChance = this.defineFraction(
      createKey(ModItems.CHORUS_TOTEM, "teleportationChance"), 1.0, "The probability that the Chorus Totem activates when a player dies"
   );
   public final Value.ConfigValue<Double> flamePendantStrikeChance = this.defineFraction(
      createKey(ModItems.FLAME_PENDANT, "strikeChance"), 0.4, "The probability that the Flame Pendant lights an attacker on fire"
   );
   public final Value.ConfigValue<Double> nightVisionGogglesStrength = this.defineFraction(
      createKey(ModItems.NIGHT_VISION_GOGGLES, "strength"), 0.15, "The strength of the night vision effect applied by the Night Vision Goggles"
   );
   public final Value.ConfigValue<Double> thornPendantStrikeChance = this.defineFraction(
      createKey(ModItems.THORN_PENDANT, "strikeChance"), 0.5, "The probability that the Thorn Pendant damages an attacking entity"
   );
   public final Value.ConfigValue<Double> shockPendantStrikeChance = this.defineFraction(
      createKey(ModItems.SHOCK_PENDANT, "strikeChance"), 0.25, "The probability that the Shock Pendant strikes an attacking entity with lightning"
   );
   public final Value.ConfigValue<Double> vampiricGloveAbsorptionChance = this.defineFraction(
      createKey(ModItems.VAMPIRIC_GLOVE, "absorptionChance"), 1.0, "The probability that damage is absorbed when attacking an entity with the Vampiric Gloves"
   );
   public final Value.ConfigValue<Double> witheredBraceletWitherChance = this.defineFraction(
      createKey(ModItems.WITHERED_BRACELET, "witherChance"), 0.3, "The probability that the Withered Bracelet inflicts a wither effect"
   );
   public final Value.ConfigValue<Double> everlastingBeefDropRate = this.defineFraction(
      createKey(ModItems.EVERLASTING_BEEF, "dropRate"), 0.002, "The probability that Everlasting Beef drops when a cow or mooshroom is killed by a player"
   );
   public final Value.ConfigValue<Double> cloudInABottleFallDamageMultiplier = this.defineFraction(
      createKey(ModItems.CLOUD_IN_A_BOTTLE, "fallDamageMultiplier"), 0.0, "How much fall damage is dealt when double jumping with the Cloud in a Bottle"
   );
   public final Value.ConfigValue<Integer> chorusTotemHealthRestored = this.defineNonNegativeInt(
      createKey(ModItems.CHORUS_TOTEM, "healthRestored"), 10, "The amount of health points that are restored after the Chorus Totem activates"
   );
   public final Value.ConfigValue<Integer> thornPendantMaxDamage = this.defineNonNegativeInt(
      createKey(ModItems.THORN_PENDANT, "maxDamage"), 6, "The minimum amount of damage that is dealt when the Thorn Pendant activates"
   );
   public final Value.ConfigValue<Integer> thornPendantMinDamage = this.defineNonNegativeInt(
      createKey(ModItems.THORN_PENDANT, "minDamage"), 2, "The maximum amount of damage that is dealt when the Thorn Pendant activates"
   );
   public final Value.ConfigValue<Integer> warpDriveHungerCost = this.defineNonNegativeInt(
      createKey(ModItems.WARP_DRIVE, "hungerCost"), 2, "How many hunger points it costs to throw an Ender Pearl using the Warp Drive"
   );
   public final Value.ConfigValue<Integer> vampiricGloveMaxHealingPerHit = this.defineNonNegativeInt(
      createKey(ModItems.VAMPIRIC_GLOVE, "maxHealingPerHit"),
      6,
      "The maximum amount of healing that can be absorbed in a single hit when attacking an entity while wearing the Vampiric Glove"
   );
   public final Value.ConfigValue<Integer> antidoteVesselMaxEffectDuration = this.defineDuration(
      createKey(ModItems.ANTIDOTE_VESSEL, "maxEffectDuration"),
      5,
      "The maximum duration in seconds negative mob effects can last when wearing the Antidote Vessel"
   );
   public final Value.ConfigValue<Integer> chorusTotemCooldown = this.defineDuration(
      createKey(ModItems.CHORUS_TOTEM, "cooldown"), 0, "The duration in seconds the Chorus Totem goes on cooldown for after activating"
   );
   public final Value.ConfigValue<Integer> crossNecklaceCooldown = this.defineDuration(
      createKey(ModItems.CROSS_NECKLACE, "cooldown"), 0, "The duration in seconds the Cross Necklace goes on cooldown for after activating"
   );
   public final Value.ConfigValue<Integer> eternalSteakCooldown = this.defineDuration(
      createKey(ModItems.ETERNAL_STEAK, "cooldown"), 15, "The duration in seconds the Eternal Steak goes on cooldown for after being eaten"
   );
   public final Value.ConfigValue<Integer> everlastingBeefCooldown = this.defineDuration(
      createKey(ModItems.EVERLASTING_BEEF, "cooldown"), 15, "The duration in seconds the Everlasting Beef goes on cooldown for after being eaten"
   );
   public final Value.ConfigValue<Integer> flamePendantCooldown = this.defineDuration(
      createKey(ModItems.FLAME_PENDANT, "cooldown"), 0, "The duration in seconds the Flame Pendant goes on cooldown for after setting an entity on fire"
   );
   public final Value.ConfigValue<Integer> flamePendantFireDuration = this.defineDuration(
      createKey(ModItems.FLAME_PENDANT, "fireDuration"), 10, "How long an attacking entity is set on fire for when the Flame Pendant activates"
   );
   public final Value.ConfigValue<Integer> heliumFlamingoFlightDuration = this.defineDuration(
      createKey(ModItems.HELIUM_FLAMINGO, "flightDuration"),
      8,
      "The amount of time in seconds a player can fly with the Helium Flamingo before needing to recharge"
   );
   public final Value.ConfigValue<Integer> heliumFlamingoRechargeDuration = this.defineDuration(
      createKey(ModItems.HELIUM_FLAMINGO, "rechargeDuration"), 15, "The amount of time in seconds it takes for the Helium Flamingo to recharge"
   );
   public final Value.ConfigValue<Integer> heliumFlamingoCooldown = this.defineDuration(
      createKey(ModItems.HELIUM_FLAMINGO, "cooldown"), 3, "The duration in seconds the Helium Flamingo goes on cooldown for when stopping flight"
   );
   public final Value.ConfigValue<Integer> obsidianSkullCooldown = this.defineDuration(
      createKey(ModItems.OBSIDIAN_SKULL, "cooldown"), 60, "The amount of time in seconds the Obsidian Skull goes on cooldown for after taking fire damage"
   );
   public final Value.ConfigValue<Integer> obsidianSkullFireResistanceDuration = this.defineDuration(
      createKey(ModItems.OBSIDIAN_SKULL, "fireResistanceDuration"),
      30,
      "The duration of the fire resistance effect that is applied when taking fire damage while wearing the Obsidian Skull"
   );
   public final Value.ConfigValue<Integer> onionRingHasteDurationPerFoodPoint = this.defineDuration(
      createKey(ModItems.ONION_RING, "hasteDurationPerFoodPoint"), 6, "The duration of haste that is applied per food point eaten while wearing the Onion Ring"
   );
   public final Value.ConfigValue<Integer> panicNecklaceCooldown = this.defineDuration(
      createKey(ModItems.PANIC_NECKLACE, "cooldown"), 0, "The duration in seconds the Panic Necklace goes on cooldown for after taking damage"
   );
   public final Value.ConfigValue<Integer> panicNecklaceSpeedDuration = this.defineDuration(
      createKey(ModItems.PANIC_NECKLACE, "speedDuration"),
      8,
      "The duration in seconds of the speed effect that is applied when taking damage while wearing the Panic Necklace"
   );
   public final Value.ConfigValue<Integer> rootedBootsHungerReplenishingDuration = this.defineDuration(
      createKey(ModItems.ROOTED_BOOTS, "hungerReplenishingDuration"),
      10,
      "The amount of time in seconds it takes to replenish a single point of hunger while wearing the Rooted Boots"
   );
   public final Value.ConfigValue<Integer> shockPendantCooldown = this.defineDuration(
      createKey(ModItems.SHOCK_PENDANT, "cooldown"),
      0,
      "The amount of time in seconds the Shock Pendant goes on cooldown for after striking an attacker with lightning"
   );
   public final Value.ConfigValue<Integer> snorkelWaterBreathingDuration = this.defineDuration(
      createKey(ModItems.SNORKEL, "waterBreathingDuration"), 30, "The duration of the water breathing effect that is applied by the Snorkel"
   );
   public final Value.ConfigValue<Integer> thornPendantCooldown = this.defineDuration(
      createKey(ModItems.THORN_PENDANT, "cooldown"), 0, "The duration in seconds the Thorn Pendant goes on cooldown for after activating"
   );
   public final Value.ConfigValue<Integer> warpDriveCooldown = this.defineDuration(
      createKey(ModItems.WARP_DRIVE, "cooldown"), 0, "The duration Ender Pearls go on cooldown for after being thrown using the Warp Drive"
   );
   public final Value.ConfigValue<Integer> witheredBraceletCooldown = this.defineDuration(
      createKey(ModItems.WITHERED_BRACELET, "cooldown"), 0, "The duration the Withered Bracelet goes on cooldown for after inflicting wither on an entity"
   );
   public final Value.ConfigValue<Integer> witheredBraceletWitherDuration = this.defineDuration(
      createKey(ModItems.WITHERED_BRACELET, "witherDuration"), 8, "The duration of the wither effect applied by the Withered Bracelet"
   );
   public final Value.ConfigValue<Integer> anglersHatLuckOfTheSeaLevelBonus = this.defineEnchantmentLevel(
      createKey(ModItems.ANGLERS_HAT, "luckOfTheSeaLevelBonus"), 1, "The amount of extra levels of luck of the sea that are granted by the Angler's Hat"
   );
   public final Value.ConfigValue<Integer> anglersHatLureLevelBonus = this.defineEnchantmentLevel(
      createKey(ModItems.ANGLERS_HAT, "lureLevelBonus"), 1, "The amount of extra levels of lure that are granted by the Angler's Hat"
   );
   public final Value.ConfigValue<Integer> luckScarfFortuneBonus = this.defineEnchantmentLevel(
      createKey(ModItems.LUCKY_SCARF, "fortuneLevelBonus"), 1, "The amount of extra levels of fortune that are granted by the Lucky Scarf"
   );
   public final Value.ConfigValue<Integer> superstitiousHatLootingLevelBonus = this.defineEnchantmentLevel(
      createKey(ModItems.SUPERSTITIOUS_HAT, "lootingLevelBonus"), 1, "The amount of extra levels of Looting that are granted by the Superstitious Hat"
   );
   public final Value.ConfigValue<Integer> onionRingHasteLevel = this.defineMobEffectLevel(
      createKey(ModItems.ONION_RING, "hasteLevel"), 2, "The level of the haste effect that is applied by the Onion Ring"
   );
   public final Value.ConfigValue<Integer> panicNecklaceSpeedLevel = this.defineMobEffectLevel(
      createKey(ModItems.PANIC_NECKLACE, "speedLevel"), 1, "The level of the speed effect that is applied by the Panic Necklace"
   );
   public final Value.ConfigValue<Integer> universalAttractorMagnetismLevel = this.defineMobEffectLevel(
      createKey(ModItems.UNIVERSAL_ATTRACTOR, "magnetismLevel"), 5, "The level of the magnetism effect that is applied by the Universal Attractor"
   );
   public final Value.ConfigValue<Integer> witheredBraceletWitherLevel = this.defineMobEffectLevel(
      createKey(ModItems.WITHERED_BRACELET, "witherLevel"), 2, "The level of the wither effect that is inflicted by the Withered Bracelet"
   );
   public final Value.ConfigValue<ToolTierUpgrade.Tier> diggingClawsToolTier = this.defineEnum(
      createKey(ModItems.DIGGING_CLAWS, "toolTier"),
      ValueTypes.TOOL_TIER,
      ToolTierUpgrade.Tier.STONE,
      false,
      "The tool tier that the Digging Claws increase the wearer's mining level to"
   );

   protected ItemConfigs() {
      super("items");

      for (RegistryHolder<Item, ?> entry : ModItems.ITEMS.getEntries()) {
         if (entry != ModItems.ETERNAL_STEAK && entry != ModItems.MIMIC_SPAWN_EGG) {
            this.generateAsLoot
               .put(
                  entry.unwrapKey().orElseThrow(),
                  this.defineBool(createKey(entry, "generateAsLoot"), "Whether this item can be found in structures or drop from entities")
               );
         }
      }
   }

   private static String createKey(Holder<? extends Item> holder, String name) {
      return ((ResourceKey)holder.unwrapKey().orElseThrow()).location().getPath() + "." + name;
   }

   public Value<Boolean> generatesAsLoot(Item item) {
      Optional<ResourceKey<Item>> key = BuiltInRegistries.ITEM.getResourceKey(item);
      return !key.isEmpty() && this.generateAsLoot.containsKey(key.get()) ? this.generateAsLoot.get(key.get()) : null;
   }

   @Override
   public void onConfigChanged() {
      if (Artifacts.getCurrentServer() != null) {
         Artifacts.LOGGER.info("Sending updated item configs to connected clients");
         this.readValuesFromConfig();
         this.sendToClients(Artifacts.getCurrentServer());
      }
   }

   private void sendToClients(MinecraftServer server) {
      this.getValues()
         .forEach((key, value) -> NetworkHandler.sendToPlayers(server.getPlayerList().getPlayers(), new UpdateItemConfigPacket((Value.ConfigValue<?>)value)));
   }

   public void sendToClient(ServerPlayer player) {
      Artifacts.LOGGER.info("Sending item configs to client");
      this.getValues().forEach((key, value) -> NetworkHandler.sendToPlayer(player, new UpdateItemConfigPacket((Value.ConfigValue<?>)value)));
   }
}
