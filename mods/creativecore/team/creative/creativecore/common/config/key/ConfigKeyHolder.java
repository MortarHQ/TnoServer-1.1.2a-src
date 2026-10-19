package team.creative.creativecore.common.config.key;

import com.google.gson.JsonElement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.field.ConfigField;
import team.creative.creativecore.common.config.gui.GuiConfigSubControl;
import team.creative.creativecore.common.config.gui.GuiConfigSubControlHolder;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.holder.ConfigHolderObject;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;

public class ConfigKeyHolder extends ConfigKey {
   private ICreativeConfigHolder holder;

   public ConfigKeyHolder(
      ICreativeConfigHolder holder, ConfigField field, String name, ConfigSynchronization synchronization, boolean requiresRestart, boolean hideFromGUI
   ) {
      super(field, name, synchronization, requiresRestart, hideFromGUI);
      this.holder = holder;
   }

   @Override
   public void triggerConfigured(Side side) {
      this.holder.configured(side);
   }

   @Override
   public boolean isDefault(Side side) {
      return this.holder.isDefault(side);
   }

   @Override
   public boolean isDefault(Object value, Side side) {
      return this.holder.isDefault(side);
   }

   @Override
   public void restoreDefault(Side side, boolean ignoreRestart) {
      this.holder.restoreDefault(side, ignoreRestart);
   }

   @Override
   public boolean isFolder() {
      return true;
   }

   @Override
   public ICreativeConfigHolder holder() {
      return this.holder;
   }

   @Override
   public Object copy(Provider provider, Side side) {
      Object value = ConfigTypeConveration.createObject(this.field);
      JsonElement element = this.write(provider, false, true, side);
      ICreativeConfigHolder.read(provider, ConfigHolderObject.createUnrelated(this.holder.getRegistry(), side, value, this.get()), true, true, element, side);
      return value;
   }

   @Override
   public void read(Provider provider, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side) {
      ICreativeConfigHolder.read(provider, this.holder, loadDefault, ignoreRestart, element, side);
   }

   @Override
   public JsonElement write(Provider provider, boolean saveDefault, boolean ignoreRestart, Side side) {
      return ICreativeConfigHolder.write(provider, this.holder, saveDefault, ignoreRestart, side);
   }

   @Override
   public void forceValue(Object object, Side side) {
      this.holder = ConfigHolderObject.createUnrelated(this.holder.getRegistry(), side, object);
      this.field.set(object);
   }

   @Override
   public ConfigTypeConveration converation() {
      return null;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public GuiConfigSubControl create(IGuiConfigParent configParent, String name, Side side) {
      return new GuiConfigSubControlHolder(name, null, null, side, configParent::changed);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void load(IGuiConfigParent configParent, GuiConfigSubControl control, Side side) {
      GuiConfigSubControlHolder c = (GuiConfigSubControlHolder)control;
      c.load(this.holder, this.field.get());
      c.createControls();
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void save(GuiConfigSubControl control, IGuiConfigParent configParent, Side side) {
      GuiConfigSubControlHolder c = (GuiConfigSubControlHolder)control;
      c.save();
      this.field.set(c.value);
   }

   @Override
   public ICreativeRegistry getRegistry() {
      return this.holder.getRegistry();
   }
}
