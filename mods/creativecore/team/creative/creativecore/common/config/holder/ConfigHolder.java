package team.creative.creativecore.common.config.holder;

import com.google.common.collect.ObjectArrays;
import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.core.HolderLookup.Provider;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;
import team.creative.creativecore.common.util.type.list.PairList;

public abstract class ConfigHolder<T extends ConfigKey> implements ICreativeConfigHolder {
   public final ConfigSynchronization synchronization;
   public final ICreativeConfigHolder parent;
   public final String[] path;
   protected PairList<String, T> fields = new PairList<>();

   public ConfigHolder(ICreativeConfigHolder parent, String key, ConfigSynchronization synchronization) {
      this.parent = parent;
      this.path = (String[])ObjectArrays.concat(parent.path(), key);
      this.synchronization = synchronization;
   }

   ConfigHolder() {
      this.parent = null;
      this.path = new String[0];
      this.synchronization = ConfigSynchronization.UNIVERSAL;
   }

   @Override
   public ConfigSynchronization synchronization() {
      return this.synchronization;
   }

   @Override
   public ICreativeConfigHolder parent() {
      return this.parent;
   }

   @Override
   public String[] path() {
      return this.path;
   }

   @Override
   public ConfigKey getField(String key) {
      return this.fields.getValue(key);
   }

   @Override
   public Collection<? extends ConfigKey> fields() {
      return this.fields.values();
   }

   @Override
   public Collection<String> names() {
      return this.fields.keys();
   }

   @Override
   public Object get(String key) {
      T field = this.fields.getValue(key);
      return field != null ? field.get() : null;
   }

   @Override
   public boolean isEmpty(Side side) {
      for (int i = 0; i < this.fields.size(); i++) {
         T field = (T)this.fields.get(i).value;
         if (field.isFolder()) {
            if (!field.holder().isEmpty(side)) {
               return false;
            }
         } else if (field.is(side)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isEmptyWithoutForce(Side side) {
      for (int i = 0; i < this.fields.size(); i++) {
         T field = (T)this.fields.get(i).value;
         if (field.isFolder()) {
            if (!field.holder().isEmptyWithoutForce(side)) {
               return false;
            }
         } else if (field.isWithoutForce(side)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isDefault(Side side) {
      for (int i = 0; i < this.fields.size(); i++) {
         if (((ConfigKey)this.fields.get(i).value).is(side) && !((ConfigKey)this.fields.get(i).value).isDefault(side)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void restoreDefault(Side side, boolean ignoreRestart) {
      for (int i = 0; i < this.fields.size(); i++) {
         T key = (T)this.fields.get(i).value;
         if (key.is(side) && (!ignoreRestart || !key.requiresRestart) && (!key.isFolder() || !key.holder().isEmpty(side))) {
            key.restoreDefault(side, ignoreRestart);
         }
      }
   }

   @Override
   public void load(Provider provider, boolean loadDefault, boolean ignoreRestart, JsonObject json, Side side) {
      for (int i = 0; i < this.fields.size(); i++) {
         T field = (T)this.fields.get(i).value;
         if (field.is(side) && (!ignoreRestart || !field.requiresRestart)) {
            if (json.has(field.name)) {
               field.read(provider, loadDefault, ignoreRestart, json.get(field.name), side);
            } else if (loadDefault && (!field.isFolder() || !field.holder().isEmpty(side))) {
               field.restoreDefault(side, ignoreRestart);
            }

            field.triggerConfigured(side);
         }
      }
   }

   @Override
   public JsonObject save(Provider provider, boolean saveDefault, boolean ignoreRestart, Side side) {
      JsonObject object = new JsonObject();

      for (int i = 0; i < this.fields.size(); i++) {
         T field = (T)this.fields.get(i).value;
         if (field.is(side) && (!ignoreRestart || !field.requiresRestart) && (saveDefault || !field.isDefault(side))) {
            object.add(field.name, field.write(provider, saveDefault, ignoreRestart, side));
         }
      }

      return object;
   }

   @Override
   public ICreativeRegistry getRegistry() {
      return this.parent.getRegistry();
   }
}
