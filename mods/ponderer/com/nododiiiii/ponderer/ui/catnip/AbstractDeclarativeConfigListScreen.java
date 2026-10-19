package com.nododiiiii.ponderer.ui.catnip;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigHelper;
import net.createmod.catnip.config.ui.ConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.ValueSpec;

public abstract class AbstractDeclarativeConfigListScreen extends AbstractDeclarativeFormScreen {
   private final String modId;
   protected final AbstractDeclarativeConfigListScreen.ConfigScope scope;
   protected final ModConfigSpec spec;

   protected AbstractDeclarativeConfigListScreen(
      @Nullable Screen parent, String modId, String scopeKey, String titleKey, AbstractDeclarativeConfigListScreen.ConfigScope scope, ModConfigSpec spec
   ) {
      super(parent, scopeKey, titleKey);
      this.modId = modId;
      this.scope = scope;
      this.spec = spec;
      ConfigScreen.modID = modId;
      ConfigHelper.changes.clear();
   }

   @Override
   protected void init() {
      ConfigScreen.modID = this.modId;
      super.init();
   }

   @Override
   protected boolean hasUnsavedChanges() {
      return !ConfigHelper.changes.isEmpty();
   }

   @Override
   protected int getUnsavedChangeCount() {
      return ConfigHelper.changes.size();
   }

   @Override
   protected boolean saveEdits() {
      UnmodifiableConfig values = this.spec.getValues();
      ConfigHelper.changes.forEach((path, change) -> {
         ConfigValue<Object> configValue = (ConfigValue<Object>)values.get(path);
         Object newValue = ConfigHelper.getValue(path, configValue);
         configValue.set(newValue);
         configValue.save();
      });
      this.spec.save();
      ConfigHelper.changes.clear();
      this.rebuildListPreservingScroll();
      return true;
   }

   @Override
   protected void discardEdits() {
      ConfigHelper.changes.clear();
      this.rebuildListPreservingScroll();
   }

   protected final void addStringConfigEntry(String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<String> value) {
      this.appendEntry(new LocalizedStringConfigEntry(labelKey, hintKey, tooltipKey, value, this.specOf(value)));
   }

   protected final void addBooleanConfigEntry(String labelKey, @Nullable String tooltipKey, ConfigValue<Boolean> value) {
      this.appendEntry(new LocalizedBooleanConfigEntry(labelKey, tooltipKey, value, this.specOf(value)));
   }

   protected final void addIntegerConfigEntry(String labelKey, @Nullable String hintKey, @Nullable String tooltipKey, ConfigValue<Integer> value) {
      this.appendEntry(new LocalizedIntegerConfigEntry(labelKey, hintKey, tooltipKey, value, this.specOf(value)));
   }

   protected final void addChoiceConfigEntry(
      String labelKey, @Nullable String tooltipKey, int buttonWidth, ConfigValue<String> value, List<String> optionLabelKeys, List<String> optionValues
   ) {
      this.appendEntry(new LocalizedChoiceConfigEntry(labelKey, tooltipKey, buttonWidth, value, this.specOf(value), optionLabelKeys, optionValues));
   }

   private <T> ValueSpec specOf(ConfigValue<T> value) {
      return value.getSpec();
   }

   public enum ConfigScope {
      CLIENT,
      SERVER;
   }
}
