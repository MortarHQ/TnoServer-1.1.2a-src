package team.lodestar.lodestone.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

public class LodestoneDamageTypeDatagen extends DamageTypeTagsProvider {
   public LodestoneDamageTypeDatagen(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
      super(pOutput, pProvider, "lodestone", existingFileHelper);
   }

   protected void addTags(Provider pProvider) {
      this.tag(LodestoneDamageTypeTags.IS_MAGIC).add(new ResourceKey[]{DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC});
      this.tag(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC).add(DamageTypes.PLAYER_ATTACK);
      this.tag(LodestoneDamageTypeTags.IGNORES_MAGIC_ATTACK_COOLDOWN_SCALAR);
   }
}
