package com.tensura.naturesaura.mixin;

import de.ellpeck.naturesaura.events.ClientEvents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ClientEvents.class, remap = false)
public interface ClientEventsAccessor {
   @Accessor("heldCache")
   static ItemStack tensuraNaturesAura$getHeldCache() {
      throw new AssertionError("Mixin accessor was not applied");
   }

   @Accessor("heldEye")
   static ItemStack tensuraNaturesAura$getHeldEye() {
      throw new AssertionError("Mixin accessor was not applied");
   }

   @Accessor("heldOcular")
   static ItemStack tensuraNaturesAura$getHeldOcular() {
      throw new AssertionError("Mixin accessor was not applied");
   }
}
