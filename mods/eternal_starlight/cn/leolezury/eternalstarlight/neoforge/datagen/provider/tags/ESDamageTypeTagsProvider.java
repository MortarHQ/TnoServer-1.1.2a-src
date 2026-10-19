package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESDamageTypeTagsProvider extends TagsProvider<DamageType> {
   public ESDamageTypeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, Registries.DAMAGE_TYPE, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider lookupProvider) {
      this.tag(ESTags.DamageTypes.BYPASSES_CRESCENT_PENDANT).add(ESDamageTypes.NUMBNESS);
      this.tag(DamageTypeTags.BYPASSES_ARMOR).add(new ResourceKey[]{ESDamageTypes.ETHER, ESDamageTypes.CRYSTAL_INFECTION, ESDamageTypes.SOUL_ABSORB});
      this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(new ResourceKey[]{ESDamageTypes.ETHER, ESDamageTypes.CRYSTAL_INFECTION, ESDamageTypes.SOUL_ABSORB});
      this.tag(DamageTypeTags.BYPASSES_SHIELD)
         .add(new ResourceKey[]{ESDamageTypes.ETHER, ESDamageTypes.CRYSTAL_INFECTION, ESDamageTypes.SONAR, ESDamageTypes.SOUL_ABSORB, ESDamageTypes.NUMBNESS});
      this.tag(DamageTypeTags.NO_IMPACT)
         .add(
            new ResourceKey[]{
               ESDamageTypes.ETHER,
               ESDamageTypes.CRYSTAL_INFECTION,
               ESDamageTypes.SONAR,
               ESDamageTypes.DAGGER_OF_HUNGER,
               ESDamageTypes.SOUL_ABSORB,
               ESDamageTypes.NUMBNESS,
               ESDamageTypes.STARFIRE,
               ESDamageTypes.LASER,
               ESDamageTypes.ELECTRIC_SHOCK,
               ESDamageTypes.POISON,
               ESDamageTypes.ENERGIZED_FLAME
            }
         );
      this.tag(DamageTypeTags.NO_KNOCKBACK)
         .add(
            new ResourceKey[]{
               ESDamageTypes.ETHER,
               ESDamageTypes.CRYSTAL_INFECTION,
               ESDamageTypes.SONAR,
               ESDamageTypes.DAGGER_OF_HUNGER,
               ESDamageTypes.SOUL_ABSORB,
               ESDamageTypes.NUMBNESS,
               ESDamageTypes.STARFIRE,
               ESDamageTypes.LASER,
               ESDamageTypes.ELECTRIC_SHOCK,
               ESDamageTypes.POISON,
               ESDamageTypes.ENERGIZED_FLAME
            }
         );
      this.tag(DamageTypeTags.IS_PROJECTILE).add(new ResourceKey[]{ESDamageTypes.WILT, ESDamageTypes.SHATTERED_BLADE, ESDamageTypes.SEEDS});
      this.tag(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS)
         .add(new ResourceKey[]{ESDamageTypes.METEOR, ESDamageTypes.SHATTERED_BLADE, ESDamageTypes.SEEDS, ESDamageTypes.LASER});
      this.tag(DamageTypeTags.PANIC_CAUSES)
         .add(
            new ResourceKey[]{
               ESDamageTypes.METEOR,
               ESDamageTypes.WILT,
               ESDamageTypes.GROUND_SMASH,
               ESDamageTypes.SHATTERED_BLADE,
               ESDamageTypes.SONAR,
               ESDamageTypes.SOUL_ABSORB,
               ESDamageTypes.SEEDS,
               ESDamageTypes.STARFIRE,
               ESDamageTypes.LASER,
               ESDamageTypes.ENERGIZED_FLAME,
               ESDamageTypes.FREEZE,
               ESDamageTypes.ELECTRIC_SHOCK,
               ESDamageTypes.POISON,
               ESDamageTypes.BITE
            }
         );
   }
}
