package com.blamejared.crafttweaker.platform.client;

import com.blamejared.crafttweaker.platform.services.IClientHelper;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class NeoForgeClientHelper implements IClientHelper {
   @Override
   public boolean isKeyDown(KeyMapping keyBinding) {
      Key key = keyBinding.getKey();
      int keyCode = key.getValue();
      if (keyCode != InputConstants.UNKNOWN.getValue()) {
         long windowHandle = Minecraft.getInstance().getWindow().getWindow();

         try {
            if (key.getType() == Type.KEYSYM) {
               return InputConstants.isKeyDown(windowHandle, keyCode);
            }

            if (key.getType() == Type.MOUSE) {
               return GLFW.glfwGetMouseButton(windowHandle, keyCode) == 1;
            }
         } catch (Exception var7) {
         }
      }

      return false;
   }

   @Override
   public boolean isKeyDownExtra(KeyMapping keyBinding) {
      if (keyBinding.isDown()) {
         return true;
      } else {
         return keyBinding.getKeyModifier().isActive(KeyConflictContext.GUI)
            ? this.isKeyDown(keyBinding)
            : KeyModifier.isKeyCodeModifier(keyBinding.getKey()) && this.isKeyDown(keyBinding);
      }
   }
}
