package com.blamejared.controlling.api.events;

import com.blamejared.controlling.api.entries.IKeyEntry;
import java.util.List;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface IKeyEntryListenersEvent {
   List<GuiEventListener> getListeners();

   IKeyEntry getEntry();
}
