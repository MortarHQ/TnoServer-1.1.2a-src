package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.util.ItemBase;

public abstract class UpgradeItemBase<T extends IUpgradeWrapper> extends ItemBase implements IUpgradeItem<T> {
   private final IUpgradeCountLimitConfig upgradeTypeLimitConfig;

   protected UpgradeItemBase(IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(new Properties());
      this.upgradeTypeLimitConfig = upgradeTypeLimitConfig;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.addAll(TranslationHelper.INSTANCE.getTranslatedLines(stack.getItem().getDescriptionId() + ".tooltip", null, ChatFormatting.DARK_GRAY));
   }

   @Override
   public int getUpgradesPerStorage(String storageType) {
      return this.upgradeTypeLimitConfig.getMaxUpgradesPerStorage(storageType, BuiltInRegistries.ITEM.getKey(this));
   }

   @Override
   public int getUpgradesInGroupPerStorage(String storageType) {
      return this.getUpgradeGroup().isSolo()
         ? Integer.MAX_VALUE
         : this.upgradeTypeLimitConfig.getMaxUpgradesInGroupPerStorage(storageType, this.getUpgradeGroup());
   }

   @Override
   public Component getName() {
      return Component.translatable(this.getDescriptionId());
   }
}
