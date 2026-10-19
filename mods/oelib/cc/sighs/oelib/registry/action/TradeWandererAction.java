package cc.sighs.oelib.registry.action;

import java.util.Objects;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public record TradeWandererAction(boolean rare, ItemListing[] trades) implements RegistrationAction {
   public TradeWandererAction {
      Objects.requireNonNull(trades, "trades");
   }
}
