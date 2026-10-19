package twilightforest.item.travellers_gear;

import java.util.Optional;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFSounds;
import twilightforest.init.custom.TravellersModifiersManager;

public class TravellersArmorBeltItem extends TravellersArmorItem {
   public static final ItemContainerContents DEFAULT_EMPTY_BELT_CONTAINER = ItemContainerContents.fromItems(NonNullList.withSize(9, ItemStack.EMPTY));

   public TravellersArmorBeltItem(Type equipmentType, Properties properties, int insertableModifierSlots, int durability) {
      super(equipmentType, properties, insertableModifierSlots, durability);
   }

   public TravellersArmorBeltItem(Type equipmentType, Properties properties, int insertableModifierSlots) {
      super(equipmentType, properties, insertableModifierSlots);
   }

   public static Properties beltProperties(Properties properties) {
      return properties.component(TFDataComponents.SWAP_HOTBAR_ABILITY, Unit.INSTANCE)
         .component(DataComponents.CONTAINER, DEFAULT_EMPTY_BELT_CONTAINER)
         .component(TFDataComponents.TRAVELLERS_HAS_BELT, Unit.INSTANCE);
   }

   @NotNull
   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
         ? Optional.ofNullable((ItemContainerContents)stack.get(DataComponents.CONTAINER)).map(TravellersArmorBeltItem.Tooltip::new)
         : Optional.empty();
   }

   public boolean canFitInsideContainerItems() {
      return false;
   }

   public boolean canFitInsideContainerItems(ItemStack stack) {
      return !stack.has(DataComponents.CONTAINER);
   }

   public static void travellersTrySwapHotbar(Player player) {
      ItemStack legArmor = player.getInventory().getArmor(EquipmentSlot.LEGS.getIndex());
      ItemContainerContents containerContents = (ItemContainerContents)legArmor.get(DataComponents.CONTAINER);
      if (hasSwapHotbar(player, legArmor) && containerContents != null) {
         NonNullList<ItemStack> hotbarStacks = NonNullList.withSize(9, ItemStack.EMPTY);
         Inventory inventory = player.getInventory();
         boolean isSwapHotbarActive = isSwapHotbarActive(player, legArmor);
         boolean hasChanged = false;

         for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
            ItemStack inventoryStack = inventory.getItem(slotIndex);
            ItemStack beltStack = containerContents.getSlots() <= slotIndex ? ItemStack.EMPTY : containerContents.getStackInSlot(slotIndex);
            if (inventoryStack.canFitInsideContainerItems()
               && !inventoryStack.is(ItemTagGenerator.TRAVELLERS_BELT_BLACKLISTED)
               && (isSwapHotbarActive || inventoryStack.isEmpty())) {
               hotbarStacks.set(slotIndex, inventoryStack);
               inventory.setItem(slotIndex, beltStack);
               if (!beltStack.equals(inventoryStack)) {
                  hasChanged = true;
               }
            } else {
               hotbarStacks.set(slotIndex, beltStack);
            }
         }

         legArmor.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(hotbarStacks));
         if (hasChanged) {
            player.level().playSound(null, player, (SoundEvent)TFSounds.SWAP_HOTBAR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         }
      }
   }

   public static boolean isSwapHotbarActive(Player player, ItemStack stack) {
      return (
            TravellersModifiersManager.isModifierActive(player, TravellersModifiersManager.SWAP_HOTBAR_MODIFIER)
               || TravellersModifiersManager.isModifierActive(player, TravellersModifiersManager.SWAP_HOTBAR_ABILITY)
         )
         && stack.has(DataComponents.CONTAINER);
   }

   public static boolean hasSwapHotbar(Player player, ItemStack stack) {
      return (
            TravellersModifiersManager.hasTravellersModifier(player.registryAccess(), stack, TravellersModifiersManager.SWAP_HOTBAR_MODIFIER)
               || TravellersModifiersManager.hasTravellersModifier(player.registryAccess(), stack, TravellersModifiersManager.SWAP_HOTBAR_ABILITY)
         )
         && stack.has(DataComponents.CONTAINER);
   }

   public record Tooltip(ItemContainerContents contents) implements TooltipComponent {
   }
}
