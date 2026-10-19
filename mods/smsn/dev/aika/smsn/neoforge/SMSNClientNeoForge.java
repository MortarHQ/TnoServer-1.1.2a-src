package dev.aika.smsn.neoforge;

import dev.aika.smsn.neoforge.compat.ClothConfigCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "smsn", dist = Dist.CLIENT)
public class SMSNClientNeoForge {
   public SMSNClientNeoForge(IEventBus ignoredEventBus, ModContainer container) {
      container.registerExtensionPoint(IConfigScreenFactory.class, ClothConfigCompat::setup);
   }
}
