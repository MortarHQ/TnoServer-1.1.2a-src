package it.hurts.sskirillss.relics.init;

import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.CuriosRelicContainer;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.InventoryRelicContainer;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base.RelicContainer;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RelicContainerRegistry {
   public static final DeferredRegister<RelicContainer> RELIC_CONTAINERS = DeferredRegister.create(RegistryRegistry.RELIC_CONTAINER_REGISTRY, "relics");
   public static final Supplier<RelicContainer> CURIOS = RELIC_CONTAINERS.register("curios", CuriosRelicContainer::new);
   public static final Supplier<RelicContainer> INVENTORY = RELIC_CONTAINERS.register("inventory", InventoryRelicContainer::new);

   public static void register(IEventBus bus) {
      RELIC_CONTAINERS.register(bus);
   }
}
