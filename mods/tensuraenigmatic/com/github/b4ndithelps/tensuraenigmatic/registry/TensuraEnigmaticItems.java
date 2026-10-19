package com.github.b4ndithelps.tensuraenigmatic.registry;

import com.github.b4ndithelps.tensuraenigmatic.items.ManaRing;
import com.github.b4ndithelps.tensuraenigmatic.items.PowerOfForceRing;
import com.github.b4ndithelps.tensuraenigmatic.items.RingOfArcaneSource;
import com.github.b4ndithelps.tensuraenigmatic.items.RingOfTheElves;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class TensuraEnigmaticItems {
   public static final Items ITEMS = DeferredRegister.createItems("tensuraenigmatic");
   public static final DeferredItem<RingOfArcaneSource> RING_OF_ARCANE_SOURCE = ITEMS.register("ring_of_arcane_source", RingOfArcaneSource::new);
   public static final DeferredItem<RingOfTheElves> RING_OF_THE_ELVES = ITEMS.register("ring_of_the_elves", RingOfTheElves::new);
   public static final DeferredItem<ManaRing> MANA_RING = ITEMS.register("mana_ring", ManaRing::new);
   public static final DeferredItem<PowerOfForceRing> POWER_OF_FORCE = ITEMS.register("power_of_force", PowerOfForceRing::new);

   private TensuraEnigmaticItems() {
   }
}
