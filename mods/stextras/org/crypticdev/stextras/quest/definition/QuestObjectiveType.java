package org.crypticdev.stextras.quest.definition;

import java.util.Arrays;

public enum QuestObjectiveType {
   BREED("entity"),
   KILL("entity"),
   NAME("entity"),
   DAMAGE("entity"),
   SPIRITUAL_DAMAGE("entity"),
   POSSESS("entity"),
   PLUNDER("entity_or_skill"),
   SPIRIT_CONTRACT("spirit"),
   MINE("block"),
   FARM("block"),
   COOK("item"),
   TRADE("item"),
   ENCHANT("item_or_enchantment"),
   MINING_STATION("item"),
   WOODCUT("item"),
   SMITH_MINECRAFT("item"),
   CRAFT("item"),
   SMITH_TENSURA("item"),
   SPELL_BINDING("skill"),
   EP_REACH("none"),
   EQUIP_EVOLVE("item"),
   MASTER("skill"),
   MASTER_TYPE("skill_type"),
   MASTER_TYPE_MAGIC("magic_type"),
   MASTER_SUBTYPE_MAGIC("magic_subtype"),
   CONSUME("item");

   private final String expectedType;

   QuestObjectiveType(String expectedType) {
      this.expectedType = expectedType;
   }

   public String expectedType() {
      return this.expectedType;
   }

   public static QuestObjectiveType fromString(String s) {
      return Arrays.stream(values())
         .filter(v -> v.name().equalsIgnoreCase(s))
         .findFirst()
         .orElseThrow(() -> new IllegalArgumentException("Unknown creator: " + s));
   }
}
