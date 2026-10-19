package net.p3pp3rf1y.sophisticatedcore.settings.main;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.settings.MainSetting;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsManager;

public class MainSettingsContainer extends SettingsContainerBase<MainSettingsCategory<?>> {
   private static final String CONTEXT_TAG = "context";
   private Context context = Context.PLAYER;

   public MainSettingsContainer(SettingsContainerMenu<?> settingsContainer, String categoryName, MainSettingsCategory<?> category) {
      super(settingsContainer, categoryName, category);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("context")) {
         this.context = Context.fromId(data.getInt("context"));
      } else {
         for (String tagName : data.getAllKeys()) {
            SettingsManager.getSetting(tagName).ifPresent(setting -> this.setSettingValue(this.getPlayer(), (MainSetting<?>)setting, data));
         }
      }
   }

   public void toggleContext() {
      this.context = this.context == Context.PLAYER ? Context.STORAGE : Context.PLAYER;
      this.sendIntToServer("context", this.context.getId());
   }

   public Context getContext() {
      return this.context;
   }

   protected Player getPlayer() {
      return this.getSettingsContainer().getPlayer();
   }

   public void toggleShiftClickIntoOpenTab() {
      this.toggleBooleanSetting(this.getPlayer(), SettingsManager.SHIFT_CLICK_INTO_OPEN_TAB_FIRST);
   }

   public boolean shouldShiftClickIntoOpenTab() {
      return this.getSettingValue(SettingsManager.SHIFT_CLICK_INTO_OPEN_TAB_FIRST);
   }

   public void toggleKeepTabOpen() {
      this.toggleBooleanSetting(this.getPlayer(), SettingsManager.KEEP_TAB_OPEN);
   }

   public void toggleKeepSearchPhrase() {
      this.toggleBooleanSetting(this.getPlayer(), SettingsManager.KEEP_SEARCH_PHRASE);
      this.clearSearchPhraseIfOff(this.getPlayer());
   }

   private void clearSearchPhraseIfOff(Player player) {
      if (!this.shouldKeepSearchPhrase() && !this.getSettingValue(SettingsManager.SEARCH_PHRASE).isEmpty()) {
         if (this.context == Context.PLAYER) {
            SettingsManager.setPlayerSetting(player, this.getCategory().getPlayerSettingsTagName(), SettingsManager.SEARCH_PHRASE, "");
         } else {
            SettingsManager.setSetting(player, this.getCategory().getPlayerSettingsTagName(), this.getCategory(), SettingsManager.SEARCH_PHRASE, "");
         }

         this.sendSettingValueToServer(SettingsManager.SEARCH_PHRASE, "");
      }
   }

   public boolean shouldKeepTabOpen() {
      return this.getSettingValue(SettingsManager.KEEP_TAB_OPEN);
   }

   public boolean shouldKeepSearchPhrase() {
      return this.getSettingValue(SettingsManager.KEEP_SEARCH_PHRASE);
   }

   protected <S> S getSettingValue(MainSetting<S> setting) {
      return this.context == Context.PLAYER
         ? SettingsManager.getPlayerSettingOrDefault(this.getPlayer(), this.getCategory().getPlayerSettingsTagName(), setting)
         : SettingsManager.getSettingValue(this.getPlayer(), this.getCategory().getPlayerSettingsTagName(), this.getCategory(), setting);
   }

   private <S> void setSettingValue(Player player, MainSetting<S> setting, CompoundTag data) {
      setting.getValue(data).ifPresent(value -> {
         if (this.context == Context.PLAYER) {
            SettingsManager.setPlayerSetting(player, this.getCategory().getPlayerSettingsTagName(), setting, (S)value);
         } else {
            SettingsManager.setSetting(player, this.getCategory().getPlayerSettingsTagName(), this.getCategory(), setting, (S)value);
         }
      });
   }

   protected void toggleBooleanSetting(Player player, MainSetting<Boolean> setting) {
      if (this.context == Context.PLAYER) {
         boolean value = !SettingsManager.getPlayerSettingOrDefault(player, this.getCategory().getPlayerSettingsTagName(), setting);
         SettingsManager.setPlayerSetting(player, this.getCategory().getPlayerSettingsTagName(), setting, value);
         this.sendSettingValueToServer(setting, value);
      } else {
         boolean value = !SettingsManager.getSettingValue(player, this.getCategory().getPlayerSettingsTagName(), this.getCategory(), setting);
         this.sendSettingValueToServer(setting, value);
      }
   }

   private <T> void sendSettingValueToServer(MainSetting<T> setting, T value) {
      CompoundTag data = new CompoundTag();
      setting.setValue(data, value);
      this.sendDataToServer(() -> data);
   }
}
