package team.creative.creativecore.common.config.converation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.premade.ToggleableConfig;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.simple.GuiCheckBox;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class ConfigTypeToggleable extends ConfigTypeConveration<ToggleableConfig> {
   public ToggleableConfig readElement(
      Provider provider, ToggleableConfig defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key
   ) {
      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      if (element.isJsonObject()) {
         JsonObject object = element.getAsJsonObject();
         configKey.read(provider, true, ignoreRestart, object.get("content"), side);
         return new ToggleableConfig<>(configKey.copy(provider, side), object.get("enabled").getAsBoolean());
      } else {
         configKey.forceValue(defaultValue.value, side);
         return new ToggleableConfig<>(configKey.copy(provider, side), defaultValue.isEnabled());
      }
   }

   public JsonElement writeElement(Provider provider, ToggleableConfig value, boolean saveDefault, boolean ignoreRestart, Side side, ConfigKey key) {
      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      JsonObject object = new JsonObject();
      object.addProperty("enabled", value.isEnabled());
      configKey.forceValue(value.value, side);
      object.add("content", configKey.write(provider, true, ignoreRestart, side));
      return object;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      parent.flow = GuiFlow.STACK_Y;
      parent.add(new GuiCheckBox("enabled", true).setTranslate("gui.config.enabled"));
      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      parent.add(configKey.create(configParent, "content", side));
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void loadValue(ToggleableConfig value, ToggleableConfig defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      parent.get("enabled", GuiCheckBox.class).value = value.isEnabled();
      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      configKey.forceValue(value.value, side);
      configKey.load(configParent, parent.get("content"), side);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected ToggleableConfig saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      configKey.save(parent.get("content"), configParent, side);
      return new ToggleableConfig<>(configKey.copy(configParent.provider(), side), parent.get("enabled", GuiCheckBox.class).value);
   }

   public ToggleableConfig set(ConfigKey key, ToggleableConfig value) {
      return value;
   }

   public boolean areEqual(ToggleableConfig one, ToggleableConfig two, ConfigKey key, Side side) {
      if (one.isEnabled() != two.isEnabled()) {
         return false;
      }

      ConfigKey configKey = ConfigKey.ofGenericType(key, side);
      ConfigTypeConveration converation = configKey.converation();
      return converation != null && !converation.areEqual(one.value, two.value, configKey, side)
         ? false
         : converation != null || key.getRegistry().equals(one.value, two.value, side);
   }
}
