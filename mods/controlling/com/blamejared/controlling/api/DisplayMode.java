package com.blamejared.controlling.api;

import com.blamejared.controlling.api.entries.IKeyEntry;
import com.blamejared.controlling.mixin.AccessKeyMapping;
import java.util.function.Predicate;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;

public enum DisplayMode {
   ALL(keyEntry -> true),
   NONE(keyEntry -> keyEntry.getKey().isUnbound()),
   CONFLICTING(
      keyEntry -> {
         for (KeyMapping key : Minecraft.getInstance().options.keyMappings) {
            if (!key.getName().equals(keyEntry.getKey().getName())
               && !key.isUnbound()
               && ((AccessKeyMapping)key).controlling$getKey().getValue() == ((AccessKeyMapping)keyEntry.getKey()).controlling$getKey().getValue()) {
               return true;
            }
         }

         return false;
      }
   );

   private final Predicate<IKeyEntry> predicate;

   DisplayMode(Predicate<IKeyEntry> predicate) {
      this.predicate = predicate;
   }

   public Predicate<Entry> getPredicate() {
      return entry -> entry instanceof IKeyEntry keyEntry && this.predicate.test(keyEntry);
   }
}
