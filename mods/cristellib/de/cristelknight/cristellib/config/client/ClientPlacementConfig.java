package de.cristelknight.cristellib.config.client;

import de.cristelknight.cristellib.config.serialize.placement.PlacementConfig;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ClientPlacementConfig(DoubleListEntry frequency, IntegerListEntry salt, IntegerListEntry separation, IntegerListEntry spacing) {
   public PlacementConfig toPlacement() {
      return new PlacementConfig(this.frequency.getValue(), this.salt.getValue(), this.separation.getValue(), this.spacing.getValue());
   }
}
