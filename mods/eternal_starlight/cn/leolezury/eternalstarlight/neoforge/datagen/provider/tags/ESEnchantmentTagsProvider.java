package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESEnchantmentTagsProvider extends EnchantmentTagsProvider {
   public ESEnchantmentTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider lookupProvider) {
      this.tag(ESTags.Enchantments.GOLEM_FORGE_LOOT)
         .add(new ResourceKey[]{ESEnchantments.FEARLESS, ESEnchantments.PRECISION, ESEnchantments.HOMING, ESEnchantments.GATHERING});
      this.tag(ESTags.Enchantments.CURSED_GARDEN_LOOT)
         .add(new ResourceKey[]{ESEnchantments.FEARLESS, ESEnchantments.POISONING, ESEnchantments.SOUL_SNATCHER, ESEnchantments.TRACING});
      this.tag(ESTags.Enchantments.PREVENTS_STARFIRE_BIRD_SPAWNS_WHEN_MINING).add(Enchantments.SILK_TOUCH);
      this.tag(ESTags.Enchantments.SEEDS_LAUNCHER_EXCLUSIVE).add(new ResourceKey[]{ESEnchantments.OVERHEAT, ESEnchantments.GLACIAL_SOWING});
      this.tag(EnchantmentTags.TOOLTIP_ORDER)
         .add(
            new ResourceKey[]{
               ESEnchantments.FEARLESS,
               ESEnchantments.POISONING,
               ESEnchantments.SOUL_SNATCHER,
               ESEnchantments.TRACING,
               ESEnchantments.TEARING,
               ESEnchantments.OVERHEAT,
               ESEnchantments.GLACIAL_SOWING,
               ESEnchantments.FERTILE,
               ESEnchantments.PRECISION,
               ESEnchantments.HOMING,
               ESEnchantments.GATHERING,
               ESEnchantments.SWIFT_LASH,
               ESEnchantments.ABYSSAL_TOUCH
            }
         );
      this.tag(EnchantmentTags.TREASURE).add(new ResourceKey[]{ESEnchantments.POISONING, ESEnchantments.FEARLESS, ESEnchantments.SOUL_SNATCHER});
      this.tag(EnchantmentTags.NON_TREASURE)
         .add(
            new ResourceKey[]{
               ESEnchantments.TRACING,
               ESEnchantments.TEARING,
               ESEnchantments.OVERHEAT,
               ESEnchantments.GLACIAL_SOWING,
               ESEnchantments.FERTILE,
               ESEnchantments.PRECISION,
               ESEnchantments.HOMING,
               ESEnchantments.GATHERING,
               ESEnchantments.SWIFT_LASH,
               ESEnchantments.ABYSSAL_TOUCH
            }
         );
   }
}
