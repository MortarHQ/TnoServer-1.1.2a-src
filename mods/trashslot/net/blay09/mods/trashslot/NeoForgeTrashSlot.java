package net.blay09.mods.trashslot;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("trashslot")
public class NeoForgeTrashSlot {
   public NeoForgeTrashSlot(IEventBus eventBus) {
      PlatformBindings.INSTANCE = new PlatformBindings() {
         @Override
         public boolean supportsKeyModifiers() {
            return true;
         }
      };
      NeoForgeLoadContext loadContext = new NeoForgeLoadContext(eventBus);
      Balm.initialize("trashslot", loadContext, TrashSlot::initialize);
   }
}
