package xaero.common.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class MySuperTinyButton extends Button {
   public MySuperTinyButton(int par1, int par2, int par3, int par4, Component par6Str, OnPress onPress) {
      super(par1, par2, par3, par4, par6Str, onPress, DEFAULT_NARRATION);
   }

   public MySuperTinyButton(int par1, int par2, Component par5Str, OnPress onPress) {
      super(par1, par2, 50, 20, par5Str, onPress, DEFAULT_NARRATION);
   }
}
