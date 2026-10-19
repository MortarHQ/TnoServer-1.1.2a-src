package com.nododiiiii.ponderer;

import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.nododiiiii.ponderer.mixin.PonderKeybindsAccessor;
import java.util.List;
import net.createmod.ponder.enums.PonderKeybinds;
import net.minecraft.client.KeyMapping;

public final class ModKeyBindings {
   public static final KeyMapping OPEN_FUNCTION_PAGE = new KeyMapping("key.ponderer.open_function_page", Type.KEYSYM, 86, "key.categories.ponderer");
   public static final KeyMapping TRIGGER_PONDER = new KeyMapping("key.ponderer.trigger_ponder", Type.KEYSYM, 67, "key.categories.ponderer");
   public static final List<KeyMapping> ALL_MAPPINGS = List.of(OPEN_FUNCTION_PAGE, TRIGGER_PONDER);
   public static final List<ModKeyBindings.ManagedBinding> MANAGED_BINDINGS = List.of(
      managed("key.ponderer.open_function_page", OPEN_FUNCTION_PAGE, 86),
      managed("ponderer.ui.keybindings.upstream_ponder", upstreamPonderMapping(), 87),
      managed("key.ponderer.trigger_ponder", TRIGGER_PONDER, 67)
   );

   private ModKeyBindings() {
   }

   public static List<KeyMapping> all() {
      return ALL_MAPPINGS;
   }

   public static List<ModKeyBindings.ManagedBinding> managedBindings() {
      return MANAGED_BINDINGS;
   }

   private static KeyMapping upstreamPonderMapping() {
      return ((PonderKeybindsAccessor)PonderKeybinds.PONDER).ponderer$getMapping();
   }

   private static ModKeyBindings.ManagedBinding managed(String translationKey, KeyMapping mapping, int defaultKeyCode) {
      return new ModKeyBindings.ManagedBinding(translationKey, mapping, Type.KEYSYM.getOrCreate(defaultKeyCode));
   }

   public record ManagedBinding(String translationKey, KeyMapping mapping, Key defaultKey) {
      public void resetToDefault() {
         this.mapping.setKey(this.defaultKey);
      }

      public boolean isDefault() {
         return this.mapping.isDefault();
      }
   }
}
