package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.Tags.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFDamageTypes;

public class DamageTypeTagGenerator extends TagsProvider<DamageType> {
   public static final TagKey<DamageType> BREAKS_LICH_SHIELDS = create("breaks_lich_shields");

   public DamageTypeTagGenerator(PackOutput output, CompletableFuture<Provider> future, ExistingFileHelper helper) {
      super(output, Registries.DAMAGE_TYPE, future, "twilightforest", helper);
   }

   protected void addTags(Provider provider) {
      this.tag(
         TFDamageTypes.GHAST_TEAR,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.DAMAGES_HELMET,
         DamageTypes.IS_PHYSICAL,
         DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
         DamageTypeTags.NO_KNOCKBACK
      );
      this.tag(TFDamageTypes.HYDRA_BITE, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.HYDRA_FIRE, DamageTypeTags.IS_FIRE, DamageTypeTags.IGNITES_ARMOR_STANDS, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
      this.tag(TFDamageTypes.HYDRA_MORTAR, DamageTypeTags.IS_FIRE, DamageTypeTags.IGNITES_ARMOR_STANDS, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
      this.tag(
         TFDamageTypes.LICH_BOLT,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.IS_PROJECTILE,
         DamageTypeTags.WITCH_RESISTANT_TO,
         BREAKS_LICH_SHIELDS,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR
      );
      this.tag(
         TFDamageTypes.LICH_BOMB,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.IS_PROJECTILE,
         DamageTypeTags.WITCH_RESISTANT_TO,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR,
         DamageTypeTags.IS_EXPLOSION
      );
      this.tag(TFDamageTypes.CHILLING_BREATH, DamageTypes.IS_MAGIC);
      this.tag(TFDamageTypes.SQUISH, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.THROWN_AXE, DamageTypeTags.IS_PROJECTILE, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.THROWN_PICKAXE, DamageTypeTags.IS_PROJECTILE, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.THORNS, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_PHYSICAL, DamageTypeTags.NO_KNOCKBACK);
      this.tag(TFDamageTypes.OREBERRY, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_PHYSICAL, DamageTypeTags.NO_KNOCKBACK);
      this.tag(TFDamageTypes.KNIGHTMETAL, DamageTypes.IS_ENVIRONMENT);
      this.tag(TFDamageTypes.FIERY, DamageTypeTags.IS_FIRE, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_PHYSICAL, DamageTypeTags.NO_KNOCKBACK);
      this.tag(
         TFDamageTypes.FIRE_JET,
         DamageTypeTags.IS_FIRE,
         DamageTypes.IS_ENVIRONMENT,
         DamageTypes.IS_PHYSICAL,
         DamageTypeTags.IGNITES_ARMOR_STANDS,
         DamageTypeTags.NO_KNOCKBACK
      );
      this.tag(TFDamageTypes.REACTOR, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_MAGIC, DamageTypeTags.NO_KNOCKBACK);
      this.tag(TFDamageTypes.SLIDER, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.THROWN_BLOCK, DamageTypeTags.DAMAGES_HELMET, DamageTypeTags.IS_PROJECTILE, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.AXING, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.SLAM, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.NO_ANGER, DamageTypes.IS_PHYSICAL, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
      this.tag(TFDamageTypes.YEETED, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.IS_FALL, DamageTypes.IS_PHYSICAL, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
      this.tag(TFDamageTypes.ANT, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.HAUNT, DamageTypes.IS_MAGIC);
      this.tag(TFDamageTypes.CLAMPED, DamageTypes.IS_PHYSICAL, DamageTypeTags.NO_KNOCKBACK);
      this.tag(TFDamageTypes.SCORCHED, DamageTypeTags.IS_FIRE, DamageTypeTags.IGNITES_ARMOR_STANDS);
      this.tag(TFDamageTypes.FROZEN, DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_WOLF_ARMOR);
      this.tag(TFDamageTypes.SPIKED, DamageTypes.IS_PHYSICAL);
      this.tag(
         TFDamageTypes.LEAF_BRAIN,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.IS_PROJECTILE,
         DamageTypeTags.WITCH_RESISTANT_TO,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR
      );
      this.tag(
         TFDamageTypes.LOST_WORDS,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.IS_PROJECTILE,
         DamageTypeTags.WITCH_RESISTANT_TO,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR
      );
      this.tag(
         TFDamageTypes.SCHOOLED,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.IS_PROJECTILE,
         DamageTypeTags.WITCH_RESISTANT_TO,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR
      );
      this.tag(TFDamageTypes.SNOWBALL_FIGHT, DamageTypeTags.IS_PROJECTILE, DamageTypes.IS_MAGIC);
      this.tag(TFDamageTypes.TWILIGHT_SCEPTER, DamageTypeTags.IS_PROJECTILE, BREAKS_LICH_SHIELDS, DamageTypes.IS_MAGIC);
      this.tag(TFDamageTypes.LIFEDRAIN, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.IS_PROJECTILE, DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_WOLF_ARMOR);
      this.tag(
         TFDamageTypes.EXPIRED,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.BYPASSES_RESISTANCE,
         DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL,
         DamageTypeTags.BYPASSES_INVULNERABILITY,
         DamageTypes.IS_TECHNICAL,
         DamageTypeTags.BYPASSES_WOLF_ARMOR
      );
      this.tag(TFDamageTypes.FALLING_ICE, DamageTypeTags.BYPASSES_ENCHANTMENTS, DamageTypes.IS_ENVIRONMENT, DamageTypes.IS_PHYSICAL);
      this.tag(TFDamageTypes.MOONWORM, DamageTypes.IS_PHYSICAL, DamageTypeTags.NO_KNOCKBACK);
      this.tag(
         TFDamageTypes.ACID_RAIN,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.WITCH_RESISTANT_TO,
         DamageTypes.IS_ENVIRONMENT,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_WOLF_ARMOR,
         DamageTypeTags.NO_KNOCKBACK
      );
      this.tag(
         TFDamageTypes.OMINOUS_FIRE,
         DamageTypes.IS_MAGIC,
         DamageTypeTags.BYPASSES_ARMOR,
         DamageTypeTags.WITHER_IMMUNE_TO,
         DamageTypeTags.NO_KNOCKBACK,
         DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES
      );
      this.tag(net.minecraft.world.damagesource.DamageTypes.MAGIC, BREAKS_LICH_SHIELDS);
      this.tag(net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC, BREAKS_LICH_SHIELDS);
      this.tag(net.minecraft.world.damagesource.DamageTypes.SONIC_BOOM, BREAKS_LICH_SHIELDS);
   }

   @SafeVarargs
   private void tag(ResourceKey<DamageType> type, TagKey<DamageType>... tags) {
      for (TagKey<DamageType> key : tags) {
         this.tag(key).add(type);
      }
   }

   private static TagKey<DamageType> create(String name) {
      return TagKey.create(Registries.DAMAGE_TYPE, TwilightForestMod.prefix(name));
   }
}
