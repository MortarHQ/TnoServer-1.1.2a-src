package net.unusual.block_factorys_bosses.data.gen.tag;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unusual.block_factorys_bosses.init.BossesRiseDamageTypes;
import org.jetbrains.annotations.Nullable;

public class BossesRiseDamageTypeTagGen extends DamageTypeTagsProvider {
   public BossesRiseDamageTypeTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "block_factorys_bosses", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add(BossesRiseDamageTypes.CANNONBALL_HIT);
      this.tag(DamageTypeTags.NO_KNOCKBACK).add(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH);
      this.tag(DamageTypeTags.BYPASSES_ARMOR).add(new ResourceKey[]{BossesRiseDamageTypes.CANNONBALL_HIT, BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH});
      this.tag(DamageTypeTags.BYPASSES_SHIELD).add(BossesRiseDamageTypes.CANNONBALL_HIT);
      this.tag(DamageTypeTags.IS_PROJECTILE).add(BossesRiseDamageTypes.CANNONBALL_HIT);
      this.tag(DamageTypeTags.PANIC_CAUSES).add(BossesRiseDamageTypes.CANNONBALL_HIT);
   }
}
