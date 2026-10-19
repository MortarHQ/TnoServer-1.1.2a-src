package twilightforest.compat.common;

import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import twilightforest.init.TFItems;
import twilightforest.init.custom.TravellersModifiersManager;

public class DefaultModifiedTravellersGearGetter {
   public static List<ItemStack> getDefaultModifiedTravellersGear(Provider registries) {
      return List.of(getDefaultGoggles(registries), getDefaultVest(registries), getDefaultWings(registries), getDefaultBoots(registries));
   }

   public static ItemStack getDemodifiedStack(ItemStack modifiedStack) {
      return new ItemStack(modifiedStack.getItem(), modifiedStack.getCount());
   }

   private static ItemStack getDefaultGoggles(Provider registries) {
      ItemStack goggles = new ItemStack((ItemLike)TFItems.TRAVELLERS_GOGGLES.get());
      TravellersModifiersManager.addModifier(registries, goggles, TravellersModifiersManager.AUTO_REPAIR_MODIFIER);
      TravellersModifiersManager.addModifier(registries, goggles, TravellersModifiersManager.RED_THREAD_VISION_MODIFIER);
      TravellersModifiersManager.addModifier(registries, goggles, TravellersModifiersManager.ALL_NIGHT_GOGGLES_MODIFIER);
      return goggles;
   }

   private static ItemStack getDefaultVest(Provider registries) {
      ItemStack vest = new ItemStack((ItemLike)TFItems.TRAVELLERS_VEST.get());
      TravellersModifiersManager.addModifier(registries, vest, TravellersModifiersManager.AUTO_REPAIR_MODIFIER);
      TravellersModifiersManager.addModifier(registries, vest, TravellersModifiersManager.PERFECT_DODGE_MODIFIER);
      TravellersModifiersManager.addModifier(registries, vest, TravellersModifiersManager.STEALTH_MODIFIER);
      return vest;
   }

   private static ItemStack getDefaultWings(Provider registries) {
      ItemStack wings = new ItemStack((ItemLike)TFItems.TRAVELLERS_WINGS.get());
      TravellersModifiersManager.addModifier(registries, wings, TravellersModifiersManager.AUTO_REPAIR_MODIFIER);
      TravellersModifiersManager.addModifier(registries, wings, TravellersModifiersManager.AGILE_RANGER_MODIFIER);
      TravellersModifiersManager.addModifier(registries, wings, TravellersModifiersManager.SIDESTEP_MODIFIER);
      return wings;
   }

   private static ItemStack getDefaultBoots(Provider registries) {
      ItemStack boots = new ItemStack((ItemLike)TFItems.TRAVELLERS_BOOTS.get());
      TravellersModifiersManager.addModifier(registries, boots, TravellersModifiersManager.AUTO_REPAIR_MODIFIER);
      TravellersModifiersManager.addModifier(registries, boots, TravellersModifiersManager.STRAIGHT_AHEAD_MODIFIER);
      TravellersModifiersManager.addModifier(registries, boots, TravellersModifiersManager.WATER_WALK_MODIFIER);
      return boots;
   }
}
