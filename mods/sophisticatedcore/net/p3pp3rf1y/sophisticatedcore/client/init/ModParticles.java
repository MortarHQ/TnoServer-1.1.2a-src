package net.p3pp3rf1y.sophisticatedcore.client.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeNoteParticle;

public class ModParticles {
   private ModParticles() {
   }

   public static void registerFactories(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet(net.p3pp3rf1y.sophisticatedcore.init.ModParticles.JUKEBOX_NOTE.get(), JukeboxUpgradeNoteParticle.Factory::new);
   }
}
