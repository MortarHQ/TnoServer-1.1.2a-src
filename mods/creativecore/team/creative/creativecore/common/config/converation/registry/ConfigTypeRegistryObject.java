package team.creative.creativecore.common.config.converation.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.premade.registry.RegistryObjectConfig;
import team.creative.creativecore.common.gui.GuiParent;

public class ConfigTypeRegistryObject extends ConfigTypeConveration<RegistryObjectConfig> {
   public RegistryObjectConfig readElement(
      Provider provider, RegistryObjectConfig defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key
   ) {
      return element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()
         ? new RegistryObjectConfig(defaultValue.registry, ResourceLocation.parse(element.getAsString()))
         : defaultValue;
   }

   public JsonElement writeElement(Provider provider, RegistryObjectConfig value, boolean saveDefault, boolean ignoreRestart, Side side, ConfigKey key) {
      return new JsonPrimitive(value.location.toString());
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      RegistryObjectConfig value = (RegistryObjectConfig)key.get();
      GuiRegistryObjectHandler.REGISTRY.get(value.registry).createControls(parent, value.registry);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void loadValue(
      RegistryObjectConfig value, RegistryObjectConfig defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side
   ) {
      GuiRegistryObjectHandler.REGISTRY.get(value.registry).loadValue(parent, value.registry, value.location);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected RegistryObjectConfig saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      RegistryObjectConfig value = (RegistryObjectConfig)key.get();
      return new RegistryObjectConfig(value.registry, GuiRegistryObjectHandler.REGISTRY.get(value.registry).saveValue(parent, value.registry));
   }

   public RegistryObjectConfig set(ConfigKey key, RegistryObjectConfig value) {
      return value;
   }
}
