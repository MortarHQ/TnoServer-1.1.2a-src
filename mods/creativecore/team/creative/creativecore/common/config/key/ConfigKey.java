package team.creative.creativecore.common.config.key;

import com.google.gson.JsonElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.converation.ConfigTypeConveration;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.field.ConfigField;
import team.creative.creativecore.common.config.field.ConfigFieldTyped;
import team.creative.creativecore.common.config.field.ConfigFieldWrapper;
import team.creative.creativecore.common.config.gui.GuiConfigSubControl;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.holder.ConfigHolderObject;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;

public abstract class ConfigKey {
   public final String name;
   public final ConfigSynchronization synchronization;
   public final boolean requiresRestart;
   public final boolean hideFromGUI;
   public boolean forceSynchronization;
   protected final ConfigField field;

   public static ConfigKey of(
      ICreativeConfigHolder parentHolder,
      Field field,
      String name,
      Object defaultValue,
      ConfigSynchronization sync,
      boolean requiresRestart,
      boolean hideFromGUI,
      Object parent
   ) {
      return of(parentHolder, new ConfigFieldWrapper(parent, field), name, defaultValue, sync, requiresRestart, hideFromGUI);
   }

   public static ConfigKey of(
      ICreativeConfigHolder parent,
      ConfigField field,
      String name,
      Object defaultValue,
      ConfigSynchronization sync,
      boolean requiresRestart,
      boolean hideFromGUI
   ) {
      return ConfigTypeConveration.has(field.getType())
         ? new ConfigKeyType(field, name, defaultValue, sync, requiresRestart, hideFromGUI, parent.getRegistry())
         : new ConfigKeyHolder(new ConfigHolderObject(parent, sync, name, defaultValue), field, name, sync, requiresRestart, hideFromGUI);
   }

   public static ConfigKey ofGenericType(ConfigKey key, Side side) {
      Type type = ConfigTypeConveration.getGenericType(key);
      Class clazz = type instanceof ParameterizedType p ? (Class)p.getRawType() : (Class)type;
      ConfigField field = new ConfigFieldTyped(null, type, clazz);
      return ofType(key, field, side);
   }

   public static ConfigKey ofArrayType(ConfigKey key, Side side) {
      Type type = ConfigTypeConveration.getGenericType(key);
      Class clazz = key.field().getType().getComponentType();
      ConfigField field = new ConfigFieldTyped(null, type, clazz);
      return ofType(key, field, side);
   }

   public static ConfigKey ofType(ConfigKey key, ConfigField field, Side side) {
      field.set(ConfigTypeConveration.createObject(field));
      return ConfigTypeConveration.has(field.getType())
         ? new ConfigKeyType(field, "", ConfigTypeConveration.createObject(field), ConfigSynchronization.UNIVERSAL, false, false, key.getRegistry())
         : new ConfigKeyHolder(
            ConfigHolderObject.createUnrelated(key.getRegistry(), side, field.get()), field, "", ConfigSynchronization.UNIVERSAL, false, false
         );
   }

   public ConfigKey(ConfigField field, String name, ConfigSynchronization synchronization, boolean requiresRestart, boolean hideFromGUI) {
      this.synchronization = synchronization;
      this.requiresRestart = requiresRestart;
      this.hideFromGUI = hideFromGUI;
      this.name = name;
      this.field = field;
   }

   public Object get() {
      return this.field.get();
   }

   @Override
   public int hashCode() {
      return this.name.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof ConfigKey c ? c.name.equals(this.name) : false;
   }

   @Override
   public String toString() {
      return this.name;
   }

   protected boolean checkEqual(Object one, Object two, Side side) {
      return one.equals(two);
   }

   public abstract boolean isFolder();

   public abstract ICreativeConfigHolder holder();

   public abstract ConfigTypeConveration converation();

   public boolean is(Side side) {
      return this.isFolder() ? this.synchronization.useFolder(this.forceSynchronization, side) : this.synchronization.useValue(this.forceSynchronization, side);
   }

   public boolean isWithoutForce(Side side) {
      return this.isFolder() ? this.synchronization.useFolder(false, side) : this.synchronization.useValue(false, side);
   }

   public abstract void triggerConfigured(Side var1);

   public abstract boolean isDefault(Side var1);

   public abstract boolean isDefault(Object var1, Side var2);

   public abstract void restoreDefault(Side var1, boolean var2);

   public abstract void forceValue(Object var1, Side var2);

   public abstract void read(Provider var1, boolean var2, boolean var3, JsonElement var4, Side var5);

   public abstract JsonElement write(Provider var1, boolean var2, boolean var3, Side var4);

   public abstract Object copy(Provider var1, Side var2);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public abstract GuiConfigSubControl create(IGuiConfigParent var1, String var2, Side var3);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public abstract void load(IGuiConfigParent var1, GuiConfigSubControl var2, Side var3);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public abstract void save(GuiConfigSubControl var1, IGuiConfigParent var2, Side var3);

   public ConfigField field() {
      return this.field;
   }

   public abstract ICreativeRegistry getRegistry();
}
