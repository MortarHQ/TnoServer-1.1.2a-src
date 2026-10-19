package com.blamejared.controlling.api.events;

import com.blamejared.controlling.api.entries.IKeyEntry;
import net.neoforged.bus.api.Event;

public class KeyEntryMouseClickedEvent extends Event implements IKeyEntryMouseClickedEvent {
   private final IKeyEntry entry;
   private final double mouseX;
   private final double mouseY;
   private final int buttonId;
   private boolean handled;

   public KeyEntryMouseClickedEvent(IKeyEntry entry, double mouseX, double mouseY, int buttonId) {
      this.entry = entry;
      this.mouseX = mouseX;
      this.mouseY = mouseY;
      this.buttonId = buttonId;
   }

   @Override
   public IKeyEntry getEntry() {
      return this.entry;
   }

   @Override
   public double getMouseX() {
      return this.mouseX;
   }

   @Override
   public double getMouseY() {
      return this.mouseY;
   }

   @Override
   public int getButtonId() {
      return this.buttonId;
   }

   @Override
   public boolean isHandled() {
      return this.handled;
   }

   @Override
   public void setHandled(boolean handled) {
      this.handled = handled;
   }
}
