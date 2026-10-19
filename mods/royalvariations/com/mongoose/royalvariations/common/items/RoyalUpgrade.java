package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.RoyalVariations;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;

public class RoyalUpgrade extends SmithingTemplateItem {
   private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
   private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
   private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet");
   private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate");
   private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings");
   private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots");
   private static final ResourceLocation EMPTY_SLOT_FRAGMENTS = RoyalVariations.location("item/empty_slot_fragments");
   private static final Component ROYAL_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", RoyalVariations.location("royal_upgrade")))
      .withStyle(TITLE_FORMAT);
   private static final Component ROYAL_UPGRADE_APPLIES_TO = Component.translatable(
         Util.makeDescriptionId("item", RoyalVariations.location("smithing_template.royal_upgrade.applies_to"))
      )
      .withStyle(DESCRIPTION_FORMAT);
   private static final Component ROYAL_UPGRADE_INGREDIENTS = Component.translatable(
         Util.makeDescriptionId("item", RoyalVariations.location("smithing_template.royal_upgrade.ingredients"))
      )
      .withStyle(DESCRIPTION_FORMAT);
   private static final Component ROYAL_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(
      Util.makeDescriptionId("item", RoyalVariations.location("smithing_template.royal_upgrade.base_slot_description"))
   );
   private static final Component ROYAL_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(
      Util.makeDescriptionId("item", RoyalVariations.location("smithing_template.royal_upgrade.additions_slot_description"))
   );

   public RoyalUpgrade() {
      super(
         ROYAL_UPGRADE_APPLIES_TO,
         ROYAL_UPGRADE_INGREDIENTS,
         ROYAL_UPGRADE,
         ROYAL_UPGRADE_BASE_SLOT_DESCRIPTION,
         ROYAL_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
         createRoyalUpgradeIconList(),
         createRoyalUpgradeMaterialList(),
         new FeatureFlag[0]
      );
   }

   private static List<ResourceLocation> createRoyalUpgradeIconList() {
      return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_BOOTS);
   }

   private static List<ResourceLocation> createRoyalUpgradeMaterialList() {
      return List.of(EMPTY_SLOT_FRAGMENTS);
   }

   public Rarity getRarity(ItemStack p_41461_) {
      return Rarity.UNCOMMON;
   }
}
