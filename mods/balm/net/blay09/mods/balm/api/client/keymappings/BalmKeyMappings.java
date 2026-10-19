package net.blay09.mods.balm.api.client.keymappings;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.Optional;
import net.minecraft.client.KeyMapping;

public interface BalmKeyMappings {
   default KeyMapping registerKeyMapping(String name, int keyCode, String category) {
      return this.registerKeyMapping(name, Type.KEYSYM, keyCode, category);
   }

   KeyMapping registerKeyMapping(String var1, Type var2, int var3, String var4);

   @Deprecated(forRemoval = true, since = "1.21.5")
   KeyMapping registerKeyMapping(String var1, KeyConflictContext var2, KeyModifier var3, int var4, String var5);

   @Deprecated(forRemoval = true, since = "1.21.5")
   KeyMapping registerKeyMapping(String var1, KeyConflictContext var2, KeyModifiers var3, int var4, String var5);

   @Deprecated(forRemoval = true, since = "1.21.5")
   KeyMapping registerKeyMapping(String var1, KeyConflictContext var2, KeyModifier var3, Type var4, int var5, String var6);

   @Deprecated(forRemoval = true, since = "1.21.5")
   KeyMapping registerKeyMapping(String var1, KeyConflictContext var2, KeyModifiers var3, Type var4, int var5, String var6);

   @Deprecated(forRemoval = true, since = "1.21.5")
   default boolean isActiveAndMatches(KeyMapping keyMapping, int keyCode, int scanCode) {
      return this.isActiveAndMatches(keyMapping, InputConstants.getKey(keyCode, scanCode));
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   default boolean isActiveAndMatches(KeyMapping keyMapping, Type type, int keyCode, int scanCode) {
      return this.isActiveAndMatches(keyMapping, type.getOrCreate(type == Type.SCANCODE ? scanCode : keyCode));
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   boolean isActiveAndMatches(KeyMapping var1, Key var2);

   @Deprecated(forRemoval = true, since = "1.21.5")
   boolean isActiveAndWasPressed(KeyMapping var1);

   @Deprecated(forRemoval = true, since = "1.21.5")
   boolean isKeyDownIgnoreContext(KeyMapping var1);

   @Deprecated(forRemoval = true, since = "1.21.5")
   boolean isActiveAndKeyDown(KeyMapping var1);

   @Deprecated(forRemoval = true, since = "1.21.5")
   Optional<Boolean> conflictsWith(KeyMapping var1, KeyMapping var2);

   @Deprecated(forRemoval = true, since = "1.21.5")
   void ignoreConflicts(KeyMapping var1);

   @Deprecated(forRemoval = true, since = "1.21.5")
   boolean shouldIgnoreConflicts(KeyMapping var1);

   BalmKeyMappings scoped(String var1);
}
