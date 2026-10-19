package com.blamejared.controlling.mixin;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBindsScreen.class)
public interface AccessKeyBindsScreenNeoForge {
   @Accessor("lastPressedModifier")
   Key getLastPressedModifier();

   @Accessor("lastPressedModifier")
   void setLastPressedModifier(Key var1);

   @Accessor("isLastKeyHeldDown")
   boolean isIsLastKeyHeldDown();

   @Accessor("isLastKeyHeldDown")
   void setIsLastKeyHeldDown(boolean var1);

   @Accessor("isLastModifierHeldDown")
   boolean isIsLastModifierHeldDown();

   @Accessor("isLastModifierHeldDown")
   void setIsLastModifierHeldDown(boolean var1);

   @Accessor("lastPressedKey")
   Key getLastPressedKey();

   @Accessor("lastPressedKey")
   void setLastPressedKey(Key var1);
}
