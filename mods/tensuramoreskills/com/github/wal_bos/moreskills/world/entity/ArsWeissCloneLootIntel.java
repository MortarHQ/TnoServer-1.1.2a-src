package com.github.wal_bos.moreskills.world.entity;

public final class ArsWeissCloneLootIntel {
   private ArsWeissCloneLootIntel() {
   }

   public static String itemAlias(String query) {
      String q = clean(query);
      if (q.isBlank()) {
         return "";
      } else if (has(q, "reset scroll", "character reset")) {
         return "character reset scroll";
      } else if (has(q, "low quality magic crystal", "low magic crystal")) {
         return "low quality magic crystal";
      } else if (has(q, "medium quality magic crystal", "medium magic crystal")) {
         return "medium quality magic crystal";
      } else if (has(q, "high quality magic crystal", "high magic crystal")) {
         return "high quality magic crystal";
      } else if (has(q, "low magisteel")) {
         return "low magisteel ingot";
      } else if (has(q, "pure magisteel")) {
         return "pure magisteel ingot";
      } else if (has(q, "mithril")) {
         return "mithril ingot";
      } else if (has(q, "orichalcum")) {
         return "orichalcum ingot";
      } else if (has(q, "monster leather a", "leather a", "rank a leather")) {
         return "monster leather a";
      } else if (has(q, "monster leather b", "leather b", "rank b leather")) {
         return "monster leather b";
      } else if (has(q, "monster leather c", "leather c", "rank c leather")) {
         return "monster leather c";
      } else if (has(q, "monster leather d", "leather d", "rank d leather")) {
         return "monster leather d";
      } else if (has(q, "sticky thread")) {
         return "sticky thread";
      } else if (has(q, "steel thread")) {
         return "steel thread";
      } else if (has(q, "spider fang")) {
         return "spider fang";
      } else if (has(q, "giant ant carapace", "ant carapace")) {
         return "giant ant carapace";
      } else if (has(q, "knight spider carapace")) {
         return "knight spider carapace";
      } else if (has(q, "armorsaurus scale")) {
         return "armorsaurus scale";
      } else if (has(q, "armorsaurus shell")) {
         return "armorsaurus shell";
      } else if (has(q, "charybdis scale")) {
         return "charybdis scale";
      } else if (has(q, "daemon essence", "demon essence")) {
         return "daemon essence";
      } else if (has(q, "dragon essence")) {
         return "dragon essence";
      } else if (has(q, "elemental essence")) {
         return "elemental essence";
      } else if (has(q, "battlewill manual", "battle will manual")) {
         return "battlewill manual";
      } else if (has(q, "magic tome", "tome")) {
         return "magic tome";
      } else if (has(q, "unbound tome")) {
         return "unbound tome";
      } else if (has(q, "grimoire")) {
         return "grimoire";
      } else if (has(q, "battlewill manual", "battle will manual")) {
         return "battlewill manual";
      } else if (has(q, "iron gear schematic", "iron gear")) {
         return "iron gear";
      } else if (has(q, "gold gear schematic", "gold gear")) {
         return "gold gear";
      } else if (has(q, "silver gear schematic", "silver gear")) {
         return "silver gear";
      } else if (has(q, "low magisteel gear")) {
         return "low magisteel gear";
      } else if (has(q, "high magisteel gear")) {
         return "high magisteel gear";
      } else if (has(q, "pure magisteel gear")) {
         return "pure magisteel gear";
      } else if (has(q, "basic bows", "bow schematic")) {
         return "basic bows";
      } else if (has(q, "short sword schematic", "short sword")) {
         return "short sword";
      } else if (has(q, "long sword schematic", "long sword")) {
         return "long sword";
      } else if (has(q, "great sword schematic", "great sword")) {
         return "great sword";
      } else if (has(q, "spear schematic", "spear")) {
         return "spear";
      } else if (has(q, "leather gear")) {
         return "leather gear";
      } else if (has(q, "monster leather gear")) {
         return "monster leather gear";
      } else if (has(q, "magic staff")) {
         return "magic staff";
      } else if (has(q, "hunting knife")) {
         return "hunting knife";
      } else if (has(q, "japanese sword")) {
         return "japanese sword";
      } else if (has(q, "charybdis scalemail", "scalemail gear")) {
         return "charybdis scalemail gear";
      } else if (has(q, "anti magic mask")) {
         return "anti magic mask";
      } else if (has(q, "spatial blade")) {
         return "spatial blade";
      } else if (has(q, "bronze coin")) {
         return "bronze coin";
      } else if (has(q, "silver coin")) {
         return "silver coin";
      } else if (has(q, "gold coin")) {
         return "gold coin";
      } else if (has(q, "dwargon banner")) {
         return "dwargon banner pattern";
      } else if (has(q, "thatch")) {
         return "thatch";
      } else if (has(q, "hipokute seed", "hipokute seeds")) {
         return "hipokute seeds";
      } else if (has(q, "hipokute flower")) {
         return "hipokute flower";
      } else if (has(q, "baffledil")) {
         return "baffledil";
      } else if (has(q, "magic stone")) {
         return "magic stone";
      } else {
         return has(q, "magic ore") ? "magic ore" : q;
      }
   }

   public static String structureHint(String query) {
      String q = clean(query);
      if (q.isBlank()) {
         return "";
      } else if (has(
         q,
         "magic tome",
         "unbound tome",
         "grimoire",
         "magic trainer",
         "magic manual",
         "low quality magic crystal",
         "magic stone",
         "amethyst",
         "red candle",
         "white candle",
         "lapis",
         "redstone",
         "glowstone"
      )) {
         return "dwarf magic trainer or wizard tower";
      } else if (has(q, "battlewill manual", "battle will manual", "dojo", "manual")) {
         return "dwarf dojo or lizardman bedroom";
      } else if (has(q, "book", "paper", "library", "ink sac", "bookshelf", "writable book", "scroll", "map")) {
         return "dwarf library or dwarf home";
      } else if (has(
         q,
         "schematic",
         "gear",
         "short sword",
         "long sword",
         "great sword",
         "spear",
         "basic bows",
         "magic staff",
         "hunting knife",
         "japanese sword",
         "anti magic mask",
         "spatial blade"
      )) {
         return "dwarf smithy, goblin tower, lizardman smithy or wizard tower";
      } else if (has(q, "silver", "iron", "gold", "copper", "ingot", "nugget", "diamond", "emerald", "trident")) {
         return "dwarf smithy, dwarf market, lizardman throne or lizardman smithy";
      } else if (has(q, "coin", "bronze coin", "silver coin", "gold coin", "merchant", "dwargon banner")) {
         return "dwarf merchant, dwarf royal tower or lizardman throne";
      } else if (has(q, "leather", "monster leather", "horse armor", "saddle")) {
         return "dwarf guard, dwarf market, goblin village or lizardman bedroom";
      } else if (has(q, "wheat", "carrot", "potato", "beetroot", "melon", "pumpkin", "seed", "apple", "berry", "food", "bread", "cookie")) {
         return "dwarf farm, dwarf home, dwarf market or goblin village";
      } else if (has(q, "log", "wood", "plank", "sapling", "stick", "palm", "thatch")) {
         return "dwarf lumberjack or goblin village";
      } else if (has(q, "fish", "cod", "salmon", "pufferfish", "seagrass", "sea pickle", "fishing rod", "lily pad", "mud")) {
         return "dwarf fisherman or lizardman village";
      } else if (has(q, "sticky thread", "steel thread", "spider fang", "spider", "web", "silk", "carapace")) {
         return "spider nest or goblin tower";
      } else if (has(q, "daemon", "demon", "hell", "netherite", "moth silk")) {
         return "hell ruins";
      } else if (has(q, "goblin", "club", "bow")) {
         return "goblin village or goblin tower";
      } else if (has(q, "orc")) {
         return "orc storage or orc tent";
      } else {
         return has(q, "lizard", "dragon essence", "serpent", "trident") ? "lizardman storage, lizardman throne or lizardman smithy" : "tensura structure";
      }
   }

   public static boolean matchesLootMob(String wanted, String entityText) {
      String w = clean(wanted);
      String e = clean(entityText);
      if (has(w, "monster leather a", "leather a", "blade tiger tail")) {
         return has(e, "blade tiger");
      } else if (has(w, "monster leather b")) {
         return has(e, "basilisk", "hover lizard", "leech lizard", "feathered serpent", "tempest serpent");
      } else if (has(w, "monster leather c")) {
         return has(e, "barghest", "direwolf", "hound dog", "giant bear", "horned bear");
      } else if (has(w, "monster leather d")) {
         return has(e, "cattledeer", "horned rabbit");
      } else if (has(w, "armorsaurus scale", "armorsaurus shell", "raw armorsaurus meat")) {
         return has(e, "armorsaurus");
      } else if (has(w, "charybdis scale", "charybdis meat", "dragon essence")) {
         return has(e, "charybdis", "dragon peacock", "feathered serpent", "tempest serpent");
      } else if (has(w, "giant ant carapace", "giant ant leg", "ant carapace")) {
         return has(e, "giant ant");
      } else if (has(w, "sticky thread", "steel thread", "spider fang", "knight spider carapace", "spider eye")) {
         return has(e, "black spider", "knight spider", "spider");
      } else if (has(w, "daemon essence", "demon essence")) {
         return has(e, "arch daemon", "greater daemon", "lesser daemon", "daemon");
      } else if (has(w, "low quality magic crystal", "medium quality magic crystal", "high quality magic crystal", "magic crystal", "magic stone")) {
         return has(e, "slime", "metal slime", "supermassive slime", "elemental", "akash", "ifrit", "undine", "sylphide", "gnome", "colossus", "tensura");
      } else if (has(w, "elemental essence", "elemental shard", "fire elemental", "water elemental", "earth elemental", "wind elemental")) {
         return has(e, "akash", "ifrit", "undine", "sylphide", "beast gnome", "war gnome", "elemental colossus", "aqua frog");
      } else if (has(w, "slime chunk", "chilled slime", "slime")) {
         return has(e, "slime", "metal slime", "supermassive slime");
      } else if (has(w, "hell moth silk", "gehenna moth silk")) {
         return has(e, "hell moth", "hell caterpillar");
      } else if (has(w, "bat wing", "raw giant bat meat")) {
         return has(e, "giant bat");
      } else if (has(w, "invisible feather")) {
         return has(e, "one eyed owl", "winged cat");
      } else if (has(w, "dragon peacock feather")) {
         return has(e, "dragon peacock");
      } else if (has(w, "centipede stinger")) {
         return has(e, "evil centipede");
      } else {
         return has(w, "raw serpent meat", "serpent scale") ? has(e, "tempest serpent", "feathered serpent") : false;
      }
   }

   public static boolean matchesTensuraEntityAlias(String query, String entityText) {
      String q = clean(query);
      String e = clean(entityText);
      if (q.isBlank()) {
         return false;
      }

      if (has(q, "slime")) {
         return has(e, "tensura slime", "metal slime", "supermassive slime", "tensura:slime");
      }

      if (has(q, "daemon", "demon")) {
         return has(e, "arch daemon", "greater daemon", "lesser daemon");
      }

      if (has(q, "spider")) {
         return has(e, "black spider", "knight spider");
      }

      if (has(q, "bear")) {
         return has(e, "giant bear", "horned bear");
      }

      if (has(q, "wolf")) {
         return has(e, "direwolf", "hound dog");
      }

      if (has(q, "lizard")) {
         return has(e, "hover lizard", "leech lizard", "lizardman");
      }

      if (has(q, "serpent")) {
         return has(e, "feathered serpent", "tempest serpent");
      }

      if (has(q, "orc")) {
         return has(e, "orc", "orc lord", "orc disaster");
      }

      if (has(q, "gnome")) {
         return has(e, "beast gnome", "war gnome");
      }

      if (has(q, "spirit", "elemental")) {
         return has(e, "akash", "ifrit", "undine", "sylphide", "gnome", "elemental colossus");
      }

      String compact = q.replace(" ", "_");
      return e.contains(q) || e.contains(compact);
   }

   private static String clean(String value) {
      return value == null
         ? ""
         : value.toLowerCase()
            .replace('_', ' ')
            .replace('-', ' ')
            .replace("minecraft:", "")
            .replace("tensura:", "tensura ")
            .replaceAll("[^a-z0-9: ]", " ")
            .replaceAll("\\s+", " ")
            .trim();
   }

   private static boolean has(String text, String... needles) {
      for (String needle : needles) {
         if (text.contains(clean(needle))) {
            return true;
         }
      }

      return false;
   }
}
