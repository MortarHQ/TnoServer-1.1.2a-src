package net.p3pp3rf1y.sophisticatedcore.client.gui.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class TranslationHelper {
   public static final String TOOLTIP_SUFFIX = ".tooltip";
   private static final String BUTTONS_SUFFIX = "buttons.";
   private static final String MESSAGE_SUFFIX = "message.";
   private static final String CONTROLS_SUFFIX = "controls.";
   public static final TranslationHelper INSTANCE = new TranslationHelper("sophisticatedcore");
   private final String guiPrefix;
   private final String guiUpgradePrefix;
   private final String guiSettingsPrefix;
   private final String guiStatusPrefix;
   private final String buttonsPrefix;
   private final String itemUpgradePrefix;
   private final String blockPrefix;
   private final String upgradeGroupPrefix;
   private final String upgradeButtonsPrefix;
   private final String upgradeControlsPrefix;
   private final String keybindPrefix;

   public TranslationHelper(String modId) {
      this.guiPrefix = "gui." + modId + ".";
      this.keybindPrefix = "key." + modId + ".";
      this.itemUpgradePrefix = "item." + modId + ".";
      this.blockPrefix = "block." + modId + ".";
      this.upgradeGroupPrefix = "upgrade_group." + modId + ".";
      this.guiUpgradePrefix = this.guiPrefix + "upgrades.";
      this.guiSettingsPrefix = this.guiPrefix + "settings.";
      this.guiStatusPrefix = this.guiPrefix + "status.";
      this.buttonsPrefix = this.guiPrefix + "buttons.";
      this.upgradeButtonsPrefix = this.guiUpgradePrefix + "buttons.";
      this.upgradeControlsPrefix = this.guiUpgradePrefix + "controls.";
   }

   public MutableComponent translStatusMessage(String statusMessage, Object... params) {
      return Component.translatable(this.guiStatusPrefix + statusMessage, params);
   }

   public MutableComponent translUpgrade(String upgradeName, Object... params) {
      return Component.translatable(this.translUpgradeKey(upgradeName), params);
   }

   public Component translUpgradeSlotTooltip(String tooltipName) {
      return Component.translatable(this.guiUpgradePrefix + "slots." + tooltipName + ".tooltip");
   }

   public String translUpgradeKey(String upgradeName) {
      return this.guiUpgradePrefix + upgradeName;
   }

   public String translBlockTooltipKey(String blockName) {
      return this.blockPrefix + blockName + ".tooltip";
   }

   public String translSettings(String categoryName) {
      return this.guiSettingsPrefix + categoryName;
   }

   public String translSettingsButton(String buttonName) {
      return this.translSettings("buttons." + buttonName);
   }

   public String translSettingsMessage(String messageName) {
      return this.translSettings("message." + messageName);
   }

   public Component translUpgradeTooltip(String upgradeName) {
      return Component.translatable(this.translUpgradeKey(upgradeName) + ".tooltip");
   }

   public String translSettingsTooltip(String categoryName) {
      return this.translSettings(categoryName) + ".tooltip";
   }

   public Component translColoredButton(String buttonName, ChatFormatting color) {
      return Component.translatable(this.translButton(buttonName)).withStyle(color);
   }

   public String translButton(String buttonName) {
      return this.buttonsPrefix + buttonName;
   }

   public Component translError(String key, Object... params) {
      return Component.translatable(this.guiPrefix + "error." + key, params);
   }

   public String translUpgradeGroup(String groupName) {
      return this.upgradeGroupPrefix + groupName;
   }

   public String translUpgradeButton(String buttonName) {
      return this.upgradeButtonsPrefix + buttonName;
   }

   public String translUpgradeControl(String controlName) {
      return this.upgradeControlsPrefix + controlName;
   }

   public String translItemTooltip(Item item) {
      return this.translItemTooltip(RegistryHelper.getItemKey(item).getPath());
   }

   public String translItemTooltip(String itemName) {
      return this.itemUpgradePrefix + itemName + ".tooltip";
   }

   public List<Component> getTranslatedLines(String translateKey, @Nullable Object parameters, ChatFormatting... textFormattings) {
      List<Component> ret = new ArrayList<>();

      for (Component translatedLine : this.getTranslatedLines(translateKey, parameters)) {
         if (translatedLine instanceof MutableComponent mutableComponent) {
            mutableComponent.withStyle(textFormattings);
            ret.add(translatedLine);
         }
      }

      return ret;
   }

   public List<Component> getTranslatedLines(String translateKey) {
      return this.getTranslatedLines(translateKey, null);
   }

   public List<Component> getTranslatedLines(String translateKey, @Nullable Object parameters) {
      String text = this.translate(translateKey, parameters);
      String[] lines = text.split("\n");
      List<Component> ret = new ArrayList<>();

      for (String line : lines) {
         ret.add(Component.literal(line));
      }

      return ret;
   }

   public String translate(String translateKey, Object... parameters) {
      return I18n.get(translateKey, parameters);
   }

   public String translKeybind(String keybindName) {
      return this.keybindPrefix + keybindName;
   }

   public String translGui(String guiTranslateKey) {
      return this.guiPrefix + guiTranslateKey;
   }

   public String translGuiTooltip(String guiTranslateKey) {
      return this.guiPrefix.substring(0, this.guiPrefix.length() - 1) + ".tooltip." + guiTranslateKey;
   }
}
