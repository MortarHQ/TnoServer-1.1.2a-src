package de.cristelknight.cristellib.config.client;

import de.cristelknight.cristellib.config.serialize.ed.EDConfig;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ClientEDConfig(Map<String, BooleanListEntry> structures) {
   public EDConfig toED() {
      return new EDConfig(this.structures.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().getValue())));
   }
}
