package team.creative.creativecore.common.config.key;

import com.google.gson.JsonElement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.IConfigObject;
import team.creative.creativecore.common.config.api.ICreativeConfig;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.field.ConfigField;
import team.creative.creativecore.common.config.gui.GuiConfigSubControl;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;

public class ConfigKeyType extends ConfigKey {
   public final ICreativeRegistry registry;
   public final ConfigTypeConveration converation;
   public final Object defaultValue;

   public ConfigKeyType(
      ConfigField field,
      String name,
      Object defaultValue,
      ConfigSynchronization synchronization,
      boolean requiresRestart,
      boolean hideFromGUI,
      ICreativeRegistry registry
   ) {
      super(field, name, synchronization, requiresRestart, hideFromGUI);
      this.converation = ConfigTypeConveration.get(field.getType());
      this.defaultValue = defaultValue;
      this.registry = registry;
   }

   private void set(Object object, Side side) {
      if (!(this.defaultValue instanceof IConfigObject)) {
         this.field.set(this.converation.set(this, object));
      }
   }

   @Override
   protected boolean checkEqual(Object one, Object two, Side side) {
      return this.converation.areEqual(one, two, this, side);
   }

   @Override
   public boolean isDefault(Side side) {
      return this.defaultValue instanceof IConfigObject c ? c.isDefault(side) : this.checkEqual(this.defaultValue, this.get(), side);
   }

   @Override
   public boolean isDefault(Object value, Side side) {
      return this.defaultValue instanceof IConfigObject c ? c.isDefault(side) : this.checkEqual(this.defaultValue, value, side);
   }

   @Override
   public void restoreDefault(Side side, boolean ignoreRestart) {
      if (this.defaultValue instanceof IConfigObject c) {
         c.restoreDefault(side, ignoreRestart);
      } else {
         this.set(this.defaultValue, side);
      }
   }

   @Override
   public void read(Provider provider, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side) {
      this.set(this.converation.readElement(provider, this.defaultValue, loadDefault, ignoreRestart, element, side, this), side);
   }

   @Override
   public JsonElement write(Provider provider, boolean saveDefault, boolean ignoreRestart, Side side) {
      return this.converation.writeElement(provider, this.get(), saveDefault, ignoreRestart, side, this);
   }

   @Override
   public Object copy(Provider provider, Side side) {
      return this.converation.readElement(provider, this.defaultValue, false, true, this.write(provider, false, true, side), side, this);
   }

   @Override
   public boolean isFolder() {
      return false;
   }

   @Override
   public ICreativeConfigHolder holder() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void triggerConfigured(Side side) {
      if (this.get() instanceof ICreativeConfig c) {
         c.configured(side);
      }
   }

   @Override
   public void forceValue(Object object, Side side) {
      this.field.set(object);
   }

   @Override
   public ConfigTypeConveration converation() {
      return this.converation;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public GuiConfigSubControl create(IGuiConfigParent configParent, String name, Side side) {
      GuiConfigSubControl control = new GuiConfigSubControl(name);
      this.converation.createControls(control, configParent, this, side);
      return control;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void load(IGuiConfigParent configParent, GuiConfigSubControl control, Side side) {
      this.converation.loadValue(this.get(), this.defaultValue, control, configParent, this, side);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void save(GuiConfigSubControl control, IGuiConfigParent configParent, Side side) {
      this.set(this.converation.save(control, configParent, this, side), side);
   }

   @Override
   public ICreativeRegistry getRegistry() {
      return this.registry;
   }
}
