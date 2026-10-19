package dev.ftb.mods.ftbchunks.client.minimap;

import dev.ftb.mods.ftbchunks.client.gui.MinimapInfoSortScreen;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringMapValue;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import java.util.Map;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class MinimapComponentConfig extends StringMapValue {
   public MinimapComponentConfig(@Nullable SNBTConfig c, String n, Map<String, String> def) {
      super(c, n, def);
   }

   public void createClientConfig(ConfigGroup group) {
      group.add(this.key, new MinimapComponentConfig.MinimapComponentConfigValue(), (Map)this.get(), stringBooleanMap -> {}, (Map)this.defaultValue);
   }

   public static class MinimapComponentConfigValue extends ConfigValue<Map<String, String>> {
      public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
         new MinimapInfoSortScreen().openGui();
      }

      public Component getStringForGUI(@Nullable Map<String, String> v) {
         return (Component)(v == null ? super.getStringForGUI(null) : Component.translatable("ftbchunks.gui.sort_minimap_info"));
      }
   }
}
