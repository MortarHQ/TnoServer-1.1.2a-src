package com.mongoose.royalvariations.compat.curios;

import com.google.common.collect.ImmutableMap;
import com.mongoose.royalvariations.common.items.GloveItem;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.compat.ICompatable;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

public class CuriosIntegration implements ICompatable {
   private static final Map<Item, String> TYPES = ImmutableMap.builder().put((Item)RVItems.SPECTRAL_GAUNTLET.get(), "hands").build();

   @Override
   public void setup() {
      NeoForge.EVENT_BUS.addListener(this::sendImc);
      NeoForge.EVENT_BUS.addListener(this::registerCapabilities);
   }

   private void sendImc(InterModEnqueueEvent event) {
      TYPES.values().stream().distinct().forEach(t -> InterModComms.sendTo("curios", "register_type", () -> new Builder(t).build()));
   }

   private void registerCapabilities(RegisterCapabilitiesEvent event) {
      TYPES.keySet().forEach(entry -> {
         if (entry instanceof GloveItem item) {
            CuriosApi.registerCurio(item, new GloveItem());
         }
      });
   }
}
