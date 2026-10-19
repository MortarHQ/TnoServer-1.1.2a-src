package team.creative.creativecore.common.config.converation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.gui.GuiConfigSubControl;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.premade.NamedList;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.collection.GuiListBoxBase;
import team.creative.creativecore.common.gui.control.simple.GuiButton;

public class ConfigTypeNamedList<T extends NamedList> extends ConfigTypeConveration<T> {
   protected void addToList(T list, String name, Object object) {
      list.put(name, (T)object);
   }

   protected T create(ConfigKey key, Side side) {
      Object result = ConfigTypeConveration.createCollection(key);
      return (T)(result != null ? result : new NamedList());
   }

   public T readElement(Provider provider, NamedList defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key) {
      ConfigKey listKey = ConfigKey.ofGenericType(key, side);
      T list = this.create(key, side);
      if (element.isJsonObject()) {
         JsonObject object = (JsonObject)element;

         for (Entry<String, JsonElement> entry : object.entrySet()) {
            listKey.read(provider, true, ignoreRestart, entry.getValue(), side);
            this.addToList(list, entry.getKey(), listKey.copy(provider, side));
         }

         return list;
      } else {
         for (Entry<String, T> entry : defaultValue.entrySet()) {
            listKey.forceValue(entry.getValue(), side);
            this.addToList(list, entry.getKey(), listKey.copy(provider, side));
         }

         return list;
      }
   }

   public JsonElement writeElement(Provider provider, T value, boolean saveDefault, boolean ignoreRestart, Side side, ConfigKey key) {
      JsonObject object = new JsonObject();
      ConfigKey listKey = ConfigKey.ofGenericType(key, side);

      for (Entry<String, ?> entry : value.entrySet()) {
         listKey.forceValue(entry.getValue(), side);
         object.add(entry.getKey(), listKey.write(provider, true, ignoreRestart, side));
      }

      return object;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      GuiListBoxBase<GuiConfigSubControl> listBox = (GuiListBoxBase<GuiConfigSubControl>)new GuiListBoxBase("data", true, new ArrayList<>())
         .setDim(50, 130)
         .setExpandable();
      listBox.canBeModified = x -> !x.defaultHolder;
      parent.add(listBox);
      ConfigKey listKey = ConfigKey.ofGenericType(key, side);
      parent.add(new GuiButton("add", x -> {
         listKey.forceValue(ConfigTypeConveration.createObject(listKey.field()), side);
         GuiConfigSubControl c = listKey.create(configParent, "", side);
         c.addNameTextfield("");
         listKey.load(configParent, c, side);
         listBox.addItem(c);
      }).setTranslate("gui.add"));
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void restoreDefault(T value, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      this.loadValue(
         this.readElement(
            configParent.provider(), (NamedList)key.get(), true, false, this.writeElement(configParent.provider(), value, true, false, side, key), side, key
         ),
         value,
         parent,
         configParent,
         key,
         side
      );
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void loadValue(T value, T defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      GuiListBoxBase<GuiConfigSubControl> box = parent.get("data");
      if (!box.isEmpty()) {
         box.clearItems();
      }

      ConfigKey listKey = ConfigKey.ofGenericType(key, side);
      List<GuiConfigSubControl> controls = new ArrayList<>(value.size());

      for (Entry<String, ?> entry : value.entrySet()) {
         listKey.forceValue(entry.getValue(), side);
         GuiConfigSubControl c = listKey.create(configParent, "", side);
         if (entry.getKey().equals("default")) {
            c.addNameUnmodifieable(entry.getKey());
         } else {
            c.addNameTextfield(entry.getKey());
         }

         listKey.load(configParent, c, side);
         c.defaultHolder = entry.getKey().equals("default");
         controls.add(c);
      }

      box.addAllItems(controls);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected T saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      ConfigKey listKey = ConfigKey.ofGenericType(key, side);
      GuiListBoxBase<GuiConfigSubControl> box = parent.get("data");
      T value = this.create(key, side);

      for (int i = 0; i < box.size(); i++) {
         listKey.save(box.get(i), configParent, side);
         this.addToList(value, box.get(i).getName(), listKey.copy(configParent.provider(), side));
      }

      return value;
   }

   public T set(ConfigKey key, T value) {
      return value;
   }

   public boolean areEqual(T one, T two, ConfigKey key, Side side) {
      ConfigKey listKey = ConfigKey.ofGenericType(key, side);
      ConfigTypeConveration converation = listKey.converation();
      if (one.size() != two.size()) {
         return false;
      }

      for (Entry<String, ?> entry : one.entrySet()) {
         Object other = two.get(entry.getKey());
         if (converation != null) {
            listKey.forceValue(entry.getValue(), side);
            if (!converation.areEqual(entry.getValue(), other, listKey, side)) {
               return false;
            }
         } else if (converation == null && !key.getRegistry().equals(entry.getValue(), other, side)) {
            return false;
         }
      }

      return true;
   }
}
