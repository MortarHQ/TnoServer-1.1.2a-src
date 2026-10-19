package com.blamejared.controlling.api.events;

import com.blamejared.controlling.api.entries.IKeyEntry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.neoforged.bus.api.Event;

public class KeyEntryListenersEvent extends Event implements IKeyEntryListenersEvent {
   private final IKeyEntry entry;
   private final List<GuiEventListener> listeners;

   public KeyEntryListenersEvent(IKeyEntry entry) {
      this.entry = entry;
      this.listeners = new ArrayList<>();
      this.getListeners().add(entry.getBtnChangeKeyBinding());
      this.getListeners().add(entry.getBtnResetKeyBinding());
   }

   @Override
   public List<GuiEventListener> getListeners() {
      return this.listeners;
   }

   @Override
   public IKeyEntry getEntry() {
      return this.entry;
   }
}
