package xaero.map.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Deprecated
public abstract class ScreenBase extends xaero.lib.client.gui.ScreenBase {
   protected ScreenBase(Screen parent, Screen escape, Component titleIn) {
      super(parent, escape, titleIn);
   }
}
