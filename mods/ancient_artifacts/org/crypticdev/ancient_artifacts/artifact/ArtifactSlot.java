package org.crypticdev.ancient_artifacts.artifact;

import java.util.List;

public enum ArtifactSlot {
   HEAD("head", "head/head"),
   NECKLACE("necklace", "chest/necklace"),
   BELT("belt", "legs/belt"),
   HANDS("hands", "hand/glove", "offhand/glove"),
   EMLEM("body", "chest/emblem"),
   ARM("bracelet", "hand/bracelet"),
   FEET("feet", "feet/shoes");

   private final String curiosSlot;
   private final List<String> trinketsSlots;

   ArtifactSlot(String curiosSlot, String... trinketsSlots) {
      this.curiosSlot = curiosSlot;
      this.trinketsSlots = List.of(trinketsSlots);
   }

   public String curiosSlot() {
      return this.curiosSlot;
   }

   public List<String> trinketsSlots() {
      return this.trinketsSlots;
   }

   public String itemTagPath() {
      return "slot/" + this.curiosSlot;
   }

   public List<String[]> getTrinketSlotPairs() {
      return this.trinketsSlots.stream().map(s -> s.split("/")).toList();
   }
}
