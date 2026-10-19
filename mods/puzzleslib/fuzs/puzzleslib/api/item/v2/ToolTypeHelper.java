package fuzs.puzzleslib.api.item.v2;

import fuzs.puzzleslib.impl.core.CommonFactories;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

public interface ToolTypeHelper {
   ToolTypeHelper INSTANCE = CommonFactories.INSTANCE.getToolTypeHelper();

   default boolean isSword(ItemStack itemStack) {
      return itemStack.getItem() instanceof SwordItem || itemStack.is(ItemTags.SWORDS);
   }

   default boolean isAxe(ItemStack itemStack) {
      return itemStack.getItem() instanceof AxeItem || itemStack.is(ItemTags.AXES);
   }

   default boolean isHoe(ItemStack itemStack) {
      return itemStack.getItem() instanceof HoeItem || itemStack.is(ItemTags.HOES);
   }

   default boolean isPickaxe(ItemStack itemStack) {
      return itemStack.getItem() instanceof PickaxeItem || itemStack.is(ItemTags.PICKAXES);
   }

   default boolean isShovel(ItemStack itemStack) {
      return itemStack.getItem() instanceof AxeItem || itemStack.is(ItemTags.SHOVELS);
   }

   default boolean isShears(ItemStack itemStack) {
      return itemStack.getItem() instanceof ShearsItem;
   }

   default boolean isShield(ItemStack itemStack) {
      return itemStack.getItem() instanceof ShieldItem;
   }

   default boolean isBow(ItemStack itemStack) {
      return itemStack.getItem() instanceof BowItem;
   }

   default boolean isCrossbow(ItemStack itemStack) {
      return itemStack.getItem() instanceof CrossbowItem;
   }

   default boolean isFishingRod(ItemStack itemStack) {
      return itemStack.getItem() instanceof FishingRodItem;
   }

   default boolean isTridentLike(ItemStack itemStack) {
      return itemStack.getItem() instanceof TridentItem;
   }

   default boolean isBrush(ItemStack itemStack) {
      return itemStack.getItem() instanceof BrushItem;
   }

   default boolean isMace(ItemStack itemStack) {
      return itemStack.getItem() instanceof MaceItem;
   }

   default boolean isMeleeWeapon(ItemStack itemStack) {
      return this.isSword(itemStack) || this.isAxe(itemStack) || this.isTridentLike(itemStack) || this.isMace(itemStack);
   }

   default boolean isRangedWeapon(ItemStack itemStack) {
      return this.isBow(itemStack) || this.isCrossbow(itemStack) || this.isTridentLike(itemStack);
   }

   default boolean isWeapon(ItemStack itemStack) {
      return this.isMeleeWeapon(itemStack) || this.isRangedWeapon(itemStack);
   }

   default boolean isMiningTool(ItemStack itemStack) {
      return this.isAxe(itemStack) || this.isHoe(itemStack) || this.isPickaxe(itemStack) || this.isShovel(itemStack);
   }

   default boolean isTool(ItemStack itemStack) {
      return this.isMiningTool(itemStack)
         || this.isWeapon(itemStack)
         || this.isShears(itemStack)
         || this.isShield(itemStack)
         || this.isFishingRod(itemStack)
         || this.isBrush(itemStack);
   }

   default boolean isHeadArmor(ItemStack itemStack) {
      return itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.HEAD || itemStack.is(ItemTags.HEAD_ARMOR);
   }

   default boolean isChestArmor(ItemStack itemStack) {
      return itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.CHEST || itemStack.is(ItemTags.CHEST_ARMOR);
   }

   default boolean isLegArmor(ItemStack itemStack) {
      return itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.LEGS || itemStack.is(ItemTags.LEG_ARMOR);
   }

   default boolean isFootArmor(ItemStack itemStack) {
      return itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.FEET || itemStack.is(ItemTags.FOOT_ARMOR);
   }

   default boolean isBodyArmor(ItemStack itemStack) {
      return itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.BODY;
   }

   default boolean isArmor(ItemStack itemStack) {
      return this.isHeadArmor(itemStack)
         || this.isChestArmor(itemStack)
         || this.isLegArmor(itemStack)
         || this.isFootArmor(itemStack)
         || this.isBodyArmor(itemStack);
   }
}
