package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.enchantment.MysticismEnchantments;
import io.github.manasmods.tensura.data.TensuraTags.Enchantments;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MysticismEnchantmentTagProvider extends EnchantmentTagsProvider {
   public MysticismEnchantmentTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "mysticism", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(Enchantments.INHERITANCE_ENGRAVING).add(new ResourceKey[]{MysticismEnchantments.HOLY_FLAMES, MysticismEnchantments.BOREAL_FROST});
      this.tag(Enchantments.ENGRAVING).add(new ResourceKey[]{MysticismEnchantments.HOLY_FLAMES, MysticismEnchantments.BOREAL_FROST});
      this.tag(Enchantments.ENGRAVING_EXCLUSIVE).add(new ResourceKey[]{MysticismEnchantments.HOLY_FLAMES, MysticismEnchantments.BOREAL_FROST});
   }
}
