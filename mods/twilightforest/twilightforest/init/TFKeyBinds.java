package twilightforest.init;

import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.KeyMapping;

public abstract class TFKeyBinds {
   public static final Set<KeyMapping> KEY_MAPPINGS = new HashSet<>();
   public static final KeyMapping RED_THREAD_VISION_KEY = register("red_thread_vision", Type.KEYSYM, -1, TFKeyBindsCategories.TRAVELLERS_GEAR);
   public static final KeyMapping ITEM_DISPLAY_MAP_CYCLE_KEY = register("item_display_map_cycle", Type.KEYSYM, 67, TFKeyBindsCategories.TRAVELLERS_GEAR);
   public static final KeyMapping ZOOM_KEY = register("zoom", Type.KEYSYM, 90, TFKeyBindsCategories.TRAVELLERS_GEAR);
   public static final KeyMapping SWAP_HOTBAR_KEY = register("swap_hotbar", Type.KEYSYM, 86, TFKeyBindsCategories.TRAVELLERS_GEAR);

   private static KeyMapping register(String name, Type type, int key, TFKeyBindsCategories.Category category) {
      KeyMapping keyMapping = new KeyMapping(addPrefix(name), type, key, category.internalName());
      KEY_MAPPINGS.add(keyMapping);
      return keyMapping;
   }

   private static String addPrefix(String s) {
      return "key.twilightforest." + s;
   }
}
