package org.anti_ad.mc.ipnext.mixin;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface IMixinKeyBinding {
   @Accessor("key")
   Key getKeyCode();

   @Accessor("isDown")
   void setPressed(boolean var1);

   @Accessor("clickCount")
   int getTimesPressed();

   @Accessor("clickCount")
   void setTimesPressed(int var1);

   @Accessor("isDown")
   boolean getPressed();
}
