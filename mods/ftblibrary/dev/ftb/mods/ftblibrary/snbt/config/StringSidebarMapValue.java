package dev.ftb.mods.ftblibrary.snbt.config;

import dev.ftb.mods.ftblibrary.sidebar.SidebarGuiButton;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class StringSidebarMapValue extends BaseValue<Map<String, StringSidebarMapValue.SideButtonInfo>> {
   public StringSidebarMapValue(@Nullable SNBTConfig c, String n, Map<String, StringSidebarMapValue.SideButtonInfo> def) {
      super(c, n, def);
      super.set(new HashMap<>(def));
   }

   @Override
   public void write(SNBTCompoundTag tag) {
      Map<String, StringSidebarMapValue.SideButtonInfo> map = this.get();
      SNBTCompoundTag mapTag = new SNBTCompoundTag();

      for (Entry<String, StringSidebarMapValue.SideButtonInfo> entry : map.entrySet()) {
         SNBTCompoundTag buttonTag = new SNBTCompoundTag();
         buttonTag.putBoolean("enabled", entry.getValue().enabled());
         buttonTag.putInt("x", entry.getValue().xPos());
         buttonTag.putInt("y", entry.getValue().yPos());
         mapTag.put(entry.getKey(), buttonTag);
      }

      tag.put(this.key, mapTag);
   }

   @Override
   public void read(SNBTCompoundTag tag) {
      Map<String, StringSidebarMapValue.SideButtonInfo> map = new HashMap<>();
      SNBTCompoundTag compound = tag.getCompound(this.key);

      for (String key : compound.getAllKeys()) {
         SNBTCompoundTag buttonTag = compound.getCompound(key);
         map.put(key, new StringSidebarMapValue.SideButtonInfo(buttonTag.getBoolean("enabled"), buttonTag.getInt("x"), buttonTag.getInt("y")));
      }

      this.set(map);
   }

   public record SideButtonInfo(boolean enabled, int xPos, int yPos) {
      public static StringSidebarMapValue.SideButtonInfo DISABLED = new StringSidebarMapValue.SideButtonInfo(false, -1, -1);

      public static StringSidebarMapValue.SideButtonInfo at(int x, int y) {
         return new StringSidebarMapValue.SideButtonInfo(true, x, y);
      }

      public static StringSidebarMapValue.SideButtonInfo ofButton(SidebarGuiButton button) {
         return new StringSidebarMapValue.SideButtonInfo(button.isEnabled(), button.getGridLocation().x(), button.getGridLocation().y());
      }
   }
}
