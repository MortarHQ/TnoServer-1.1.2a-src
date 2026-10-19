package cc.sighs.oelib.registry.action;

import java.util.Objects;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public record TradeVillagerAction(VillagerProfession profession, int level, ItemListing[] trades) implements RegistrationAction {
   public TradeVillagerAction {
      Objects.requireNonNull(profession, "profession");
      Objects.requireNonNull(trades, "trades");
      if (level < 1) {
         throw new IllegalArgumentException("Villager trade level must be >= 1");
      }
   }
}
