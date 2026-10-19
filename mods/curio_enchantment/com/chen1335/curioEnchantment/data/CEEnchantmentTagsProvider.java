package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.API.objects.CETags;
import com.chen1335.curioEnchantment.data.enchantments.CEEnchantments;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class CEEnchantmentTagsProvider extends EnchantmentTagsProvider {
   public CEEnchantmentTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "curio_enchantment", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(CETags.EnchantmentTags.DEVOTED_EXCLUSIVE).add(IronsSpellBooksEnchantments.VARIOUS);
      this.tag(CETags.EnchantmentTags.ENCHANTMENT).addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.tag(EnchantmentTags.TOOLTIP_ORDER)
         .addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location())
         .addOptional(IronsSpellBooksEnchantments.KNOWLEDGE.location())
         .addOptional(IronsSpellBooksEnchantments.DEVOTED.location())
         .addOptional(IronsSpellBooksEnchantments.VARIOUS.location())
         .addOptional(IronsSpellBooksEnchantments.QUICK_CASTING.location())
         .addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.tag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS)
         .add(new ResourceKey[]{CEEnchantments.SOLID, CEEnchantments.GROW, CEEnchantments.LIGHT, CEEnchantments.QUICK_CLAW});
      this.tag(EnchantmentTags.TRADEABLE).addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.tag(EnchantmentTags.IN_ENCHANTING_TABLE).addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.tag(EnchantmentTags.NON_TREASURE).addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.tag(EnchantmentTags.ON_RANDOM_LOOT).addTag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS);
      this.addOptionTags(provider);
   }

   protected void addOptionTags(@NotNull Provider provider) {
      this.tag(CETags.EnchantmentTags.ENCHANTMENT).addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location());
      this.tag(EnchantmentTags.TREASURE).addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location());
      this.tag(CETags.EnchantmentTags.COMMON_ENCHANTMENTS)
         .addOptional(IronsSpellBooksEnchantments.QUICK_CASTING.location())
         .addOptional(IronsSpellBooksEnchantments.DEVOTED.location())
         .addOptional(IronsSpellBooksEnchantments.VARIOUS.location())
         .addOptional(IronsSpellBooksEnchantments.KNOWLEDGE.location());
   }
}
