package net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.FilterSlotItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.EntityMatch;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class AlchemyUpgradeContainer extends UpgradeContainerBase<AlchemyUpgradeWrapper, AlchemyUpgradeContainer> {
   public static final ResourceLocation EMPTY_POTION_SLOT_BACKGROUND = SophisticatedCore.getRL("item/empty_potion_slot");
   public static final String DATA_CONDITION = "condition";
   public static final String DATA_MATCH_ALL = "matchAll";
   private static final String DATA_MATCH_DURATION = "matchDuration";
   public static final String DATA_MATCH_AMPLIFIER = "matchAmplifier";
   private static final String DATA_ENTITY_MATCH = "entityMatch";

   public AlchemyUpgradeContainer(
      Player player, int upgradeContainerId, AlchemyUpgradeWrapper upgradeWrapper, UpgradeContainerType<AlchemyUpgradeWrapper, AlchemyUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      InventoryHelper.iterate(
         upgradeWrapper.getFilterHandler(), (slot, stack) -> this.slots.add((new FilterSlotItemHandler(upgradeWrapper::getFilterHandler, slot, 0, 0) {
            @Override
            public boolean mayPickup(Player player) {
               return false;
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
               return stack.isEmpty() || this.getItemHandler().isItemValid(slot, stack);
            }
         }).setBackground(InventoryMenu.BLOCK_ATLAS, EMPTY_POTION_SLOT_BACKGROUND))
      );
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("condition")) {
         NBTHelper.getEnumConstant(data, "condition", AlchemyCondition::fromName)
            .ifPresent(condition -> this.setConditionValue(data.getInt("slot"), condition, data.getFloat("value")));
      } else if (data.contains("matchAll")) {
         this.setMatchAll(data.getBoolean("matchAll"));
      } else if (data.contains("matchDuration")) {
         this.setMatchDuration(data.getBoolean("matchDuration"));
      } else if (data.contains("matchAmplifier")) {
         this.setMatchAmplifier(data.getBoolean("matchAmplifier"));
      } else if (data.contains("entityMatch")) {
         NBTHelper.getEnumConstant(data, "entityMatch", EntityMatch::fromName).ifPresent(this::setEntityMatch);
      }
   }

   private void setConditionValue(int slot, AlchemyCondition enumConstant, float value) {
      this.upgradeWrapper.setConditionValue(slot, enumConstant, value);
   }

   public AlchemyCondition getCondition(int slot) {
      return this.getUpgradeWrapper().getCondition(slot);
   }

   public boolean hasNoFilter(int slot) {
      return this.slots.get(slot).getItem().isEmpty();
   }

   public float getValue(int slot) {
      return this.getUpgradeWrapper().getValue(slot);
   }

   public void toggleCondition(int slot) {
      if (!this.upgradeWrapper.getFilterHandler().getStackInSlot(slot).isEmpty()) {
         AlchemyCondition condition = this.upgradeWrapper.getCondition(slot).next();
         float value = condition.defaultValue();
         this.setConditionValue(slot, condition, value);
         this.sendConditionValue(slot, condition, value);
      }
   }

   private void sendConditionValue(int slot, AlchemyCondition condition, float value) {
      this.sendDataToServer(() -> {
         CompoundTag tag = new CompoundTag();
         tag.putInt("slot", slot);
         NBTHelper.putEnumConstant(tag, "condition", condition);
         tag.putFloat("value", value);
         return tag;
      });
   }

   public void setValue(int slot, float value) {
      AlchemyCondition condition = this.upgradeWrapper.getCondition(slot);
      this.setConditionValue(slot, condition, value);
      this.sendConditionValue(slot, condition, value);
   }

   public void toggleMatchAll() {
      this.setMatchAll(!this.upgradeWrapper.shouldMatchAllEffects());
      this.sendBooleanToServer("matchAll", this.upgradeWrapper.shouldMatchAllEffects());
   }

   private void setMatchAll(boolean matchAllEffects) {
      this.upgradeWrapper.setMatchAllEffects(matchAllEffects);
   }

   public Boolean shouldMatchAll() {
      return this.upgradeWrapper.shouldMatchAllEffects();
   }

   public void toggleMatchDuration() {
      this.setMatchDuration(!this.upgradeWrapper.shouldMatchEffectDuration());
      this.sendBooleanToServer("matchDuration", this.upgradeWrapper.shouldMatchEffectDuration());
   }

   private void setMatchDuration(boolean matchDuration) {
      this.upgradeWrapper.setMatchEffectDuration(matchDuration);
   }

   public boolean shouldMatchDuration() {
      return this.upgradeWrapper.shouldMatchEffectDuration();
   }

   public void toggleMatchAmplifier() {
      this.setMatchAmplifier(!this.upgradeWrapper.shouldMatchEffectAmplifier());
      this.sendBooleanToServer("matchAmplifier", this.upgradeWrapper.shouldMatchEffectAmplifier());
   }

   private void setMatchAmplifier(boolean matchAmplifier) {
      this.upgradeWrapper.setMatchEffectAmplifier(matchAmplifier);
   }

   public boolean shouldMatchAmplifier() {
      return this.upgradeWrapper.shouldMatchEffectAmplifier();
   }

   public void toggleEntityMatch() {
      this.setEntityMatch(this.getEntityMatch().next());
      this.sendDataToServer(() -> {
         CompoundTag tag = new CompoundTag();
         NBTHelper.putEnumConstant(tag, "entityMatch", this.getEntityMatch());
         return tag;
      });
   }

   private void setEntityMatch(EntityMatch entityMatch) {
      this.upgradeWrapper.setEntityMatch(entityMatch);
   }

   public EntityMatch getEntityMatch() {
      return this.upgradeWrapper.getEntityMatch();
   }

   public boolean hasEntityMatchOption() {
      return this.player.containerMenu instanceof StorageContainerMenuBase<?> storageMenu
         ? storageMenu.getEntity().map(entity -> !(entity instanceof Player)).orElse(true)
         : false;
   }
}
