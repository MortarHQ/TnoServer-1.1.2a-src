package com.github.wal_bos.moreskills.world.entity;

import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceTameEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Objects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class ArsWeissCloneTensuraSupport {
   private ArsWeissCloneTensuraSupport() {
   }

   public static boolean isTensuraText(String text) {
      return text.contains("tensura")
         || containsAny(
            text,
            "magisteel",
            "magicule",
            "magic ore",
            "magic_ore",
            "magic stone",
            "magic_stone",
            "mithril",
            "orichalcum",
            "adamantite",
            "hihiirokane",
            "hipokute",
            "baffledil",
            "night_lily",
            "mana",
            "aura",
            "spirit",
            "battlewill",
            "magic tome",
            "tome",
            "manual",
            "crystal",
            "demon steel",
            "demon_steel",
            "akash",
            "aqua frog",
            "arch daemon",
            "armorsaurus",
            "army wasp",
            "barghest",
            "basilisk",
            "black spider",
            "blade tiger",
            "cattledeer",
            "charybdis",
            "direwolf",
            "dragon peacock",
            "elemental colossus",
            "feathered serpent",
            "giant ant",
            "giant bat",
            "giant bear",
            "goblin",
            "greater daemon",
            "hell caterpillar",
            "hell moth",
            "horned bear",
            "horned rabbit",
            "hound dog",
            "hover lizard",
            "ifrit",
            "knight spider",
            "landfish",
            "leech lizard",
            "lesser daemon",
            "lizardman",
            "megalodon",
            "metal slime",
            "orc",
            "orc lord",
            "orc disaster",
            "pegasus",
            "pegacorn",
            "phantaspore",
            "salamander",
            "sissie",
            "slime",
            "supermassive slime",
            "spear toro",
            "sylphide",
            "undine",
            "unicorn",
            "war gnome",
            "winged cat",
            "evil centipede",
            "tempest serpent"
         );
   }

   public static boolean isRawTensuraMaterialText(String text) {
      return containsAny(
         text,
         "magic_ore",
         "magic ore",
         "magic_stone",
         "magic stone",
         "magic_crystal",
         "magic crystal",
         "quality_magic_crystal",
         "hipokute",
         "baffledil",
         "night_lily",
         "battlewill",
         "tome",
         "manual",
         "monster_leather",
         "sticky_thread",
         "steel_thread"
      );
   }

   public static int materialPriority(ItemStack stack) {
      String text = itemText(stack);
      if (containsAny(text, "magic_crystal", "magic crystal", "spirit", "soul", "core")) {
         return 0;
      } else if (containsAny(text, "hipokute", "baffledil", "night_lily", "flower", "grass", "herb")) {
         return 1;
      } else if (containsAny(text, "magisteel", "mithril", "orichalcum", "adamantite", "hihiirokane", "magic_ore", "magic ore", "magic_stone", "magic stone")) {
         return 3;
      } else {
         return containsAny(text, "tome", "manual", "schematic", "scroll") ? 5 : 50;
      }
   }

   public static int blockSourceScore(BlockState state, ItemStack target) {
      if (!state.isAir() && !target.isEmpty()) {
         String block = blockText(state);
         String item = itemText(target);
         int score = 0;
         if (!isTensuraText(block) && !isTensuraText(item)) {
            return 0;
         }

         if (containsAny(item, "magic_ore", "magic ore", "magic_stone", "magic stone")
            && containsAny(block, "magic_ore", "magic ore", "magic_stone", "magic stone", "ore_magic", "magicore")) {
            score = Math.max(score, 120);
         }

         if (containsAny(item, "low_magisteel", "high_magisteel", "pure_magisteel", "magisteel", "mithril", "orichalcum", "adamantite", "hihiirokane")
            && containsAny(block, "chest", "barrel", "smith", "kiln", "mining_station", "magic_engine", "tool_rack")) {
            score = Math.max(score, 35);
         }

         if (containsAny(item, "hipokute", "baffledil", "night_lily", "flower", "herb")
            && containsAny(block, "hipokute", "baffledil", "night_lily", "flower", "grass", "plant")) {
            score = Math.max(score, 95);
         }

         if (containsAny(item, "tome", "manual", "book", "scroll") && containsAny(block, "chest", "barrel", "library", "wizard", "tower")) {
            score = Math.max(score, 30);
         }

         return score;
      } else {
         return 0;
      }
   }

   public static boolean isLikelyTensuraMobDropText(String want) {
      return containsAny(
         want,
         "magic_crystal",
         "magic crystal",
         "low_quality_magic_crystal",
         "medium_quality_magic_crystal",
         "high_quality_magic_crystal",
         "scale",
         "fang",
         "claw",
         "horn",
         "shell",
         "lizard",
         "spider",
         "thread",
         "spirit_core",
         "core",
         "soul",
         "gel",
         "slime"
      );
   }

   public static boolean isTensuraMaterialMobTarget(ServerPlayer owner, LivingEntity entity, String want) {
      if (!canBeArmyTarget(owner, entity)) {
         return false;
      } else {
         String entityText = entityText(entity);
         if (ArsWeissCloneLootIntel.matchesLootMob(want, entityText)) {
            return true;
         } else if (matchesTensuraEntityQuery(entity, want)) {
            return true;
         } else if (containsAny(want, "monster leather a", "monster_leather_a", "leather a") && containsAny(entityText, "blade tiger", "blade_tiger")) {
            return true;
         } else if (containsAny(want, "monster leather b", "monster_leather_b") && containsAny(entityText, "basilisk", "lizard", "serpent")) {
            return true;
         } else if (containsAny(want, "monster leather c", "monster_leather_c") && containsAny(entityText, "barghest", "bear", "hound", "direwolf")) {
            return true;
         } else if (containsAny(want, "monster leather d", "monster_leather_d") && containsAny(entityText, "cattledeer", "deer", "rabbit")) {
            return true;
         } else if (containsAny(want, "armorsaurus scale", "armorsaurus_scale", "armorsaurus shell", "armorsaurus_shell") && entityText.contains("armorsaurus")
            )
          {
            return true;
         } else if (containsAny(want, "serpent scale", "serpent_scale") && containsAny(entityText, "serpent", "tempest serpent")) {
            return true;
         } else if (containsAny(want, "charybdis scale", "charybdis_scale", "charybdis core") && entityText.contains("charybdis")) {
            return true;
         } else if (containsAny(want, "giant ant carapace", "ant carapace") && entityText.contains("ant")) {
            return true;
         } else if (containsAny(want, "knight spider carapace", "sticky thread", "steel thread", "spider fang") && entityText.contains("spider")) {
            return true;
         } else if (containsAny(want, "daemon essence", "demon essence") && containsAny(entityText, "daemon", "demon")) {
            return true;
         } else if (containsAny(want, "dragon essence") && containsAny(entityText, "dragon", "charybdis", "serpent")) {
            return true;
         } else if (containsAny(want, "elemental essence", "elemental shard")
            && containsAny(entityText, "elemental", "ifrit", "undine", "sylph", "gnome", "akash")) {
            return true;
         } else if (containsAny(want, "magic_crystal", "magic crystal", "crystal")
            && containsAny(entityText, "tensura", "spirit", "monster", "golem", "colossus", "elemental", "ifrit", "undine", "sylph", "gnome", "akash")) {
            return true;
         } else if (containsAny(want, "scale", "shell", "lizard") && containsAny(entityText, "lizard", "dragon", "reptile", "tensura")) {
            return true;
         } else if (containsAny(want, "fang", "claw", "hide") && containsAny(entityText, "hound", "wolf", "beast", "bear", "tensura")) {
            return true;
         } else if (containsAny(want, "thread", "string", "spider") && containsAny(entityText, "spider", "arach", "tensura")) {
            return true;
         } else {
            return containsAny(want, "gel", "slime") && entityText.contains("slime") ? true : isTensuraText(want) && entityText.contains("tensura");
         }
      }
   }

   public static boolean canBeArmyTarget(ServerPlayer owner, LivingEntity entity) {
      if (entity == null || !entity.isAlive() || entity == owner || entity instanceof ArsWeissCloneEntity || entity.isAlliedTo(owner)) {
         return false;
      } else {
         return entity instanceof Player ? false : !Objects.equals(SubordinateHelper.getSubordinateOwnerUUID(entity), owner.getUUID());
      }
   }

   public static boolean matchesArmyQuery(ServerPlayer owner, LivingEntity entity, String query) {
      if (!canBeArmyTarget(owner, entity)) {
         return false;
      }

      String cleaned = query == null ? "" : query.trim().toLowerCase();
      if (!cleaned.isBlank() && !cleaned.equals("all") && !cleaned.equals("everything") && !cleaned.equals("nearby")) {
         String entityText = entityText(entity);

         for (String part : cleaned.replace('_', ' ').replace('-', ' ').split("[ :]+")) {
            if (part.length() >= 2 && entityText.contains(part)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean forceTameForOwner(ServerPlayer owner, LivingEntity target) {
      if (!canBeArmyTarget(owner, target)) {
         return false;
      }

      if (((ForceTameEvent)TensuraEntityEvents.FORCE_TAME_EVENT.invoker()).tame(target, owner, false).isFalse()) {
         return false;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(target);
      existence.setPermanentOwner(owner.getUUID());
      existence.setTemporaryOwner(null);
      existence.setSummoner(null);
      existence.markDirty();
      if (target instanceof ISubordinate subordinate) {
         subordinate.tame(owner);
      }

      if (target instanceof Mob targetMob) {
         SubordinateHelper.removeTarget(targetMob);
         SubordinateHelper.setFollow(targetMob);
         SubordinateHelper.setProtect(targetMob);
      } else {
         SubordinateHelper.removeTarget(target);
      }

      owner.swing(InteractionHand.MAIN_HAND, true);
      TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.RAID_OMEN, 1.5);
      target.level()
         .playSound(null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 1.25F);
      return true;
   }

   public static String structureQuery(String query) {
      String q = query == null ? "" : query.toLowerCase().replace('_', ' ').replace('-', ' ').trim();
      if (!q.isBlank() && !q.equals("tensura")) {
         String lootHint = ArsWeissCloneLootIntel.structureHint(q);
         if (!lootHint.isBlank()) {
            return lootHint;
         } else if (containsAny(q, "dwarf", "dwargon", "smith", "library", "dojo", "market")) {
            return "dwarf village";
         } else if (containsAny(q, "goblin", "goblin village")) {
            return "goblin village";
         } else if (containsAny(q, "lizard", "lizardman", "swamp")) {
            return "lizardman village";
         } else if (containsAny(q, "orc", "orc village")) {
            return "orc village";
         } else if (containsAny(q, "ant", "ant nest", "carapace")) {
            return "ant nest";
         } else if (containsAny(q, "spider", "web", "thread", "fang")) {
            return "spider nest";
         } else if (containsAny(q, "hell", "daemon", "demon", "ruin")) {
            return "hell ruins";
         } else if (containsAny(q, "wizard", "tower", "scroll", "tome", "manual", "magic trainer")) {
            return "wizard tower";
         } else if (containsAny(q, "warp", "pad")) {
            return "warp pad";
         } else if (containsAny(q, "charybdis", "cave", "scale")) {
            return "charybdis cave";
         } else if (containsAny(q, "ancient", "forest", "hipokute", "baffledil")) {
            return "ancient forest";
         } else {
            return containsAny(q, "labyrinth") ? "labyrinth" : q;
         }
      } else {
         return "tensura structure";
      }
   }

   public static int itemScoreBoost(ResourceLocation id, String candidate, String query) {
      int score = 0;
      if (id != null && id.getNamespace().equals("tensura")) {
         score += 35;
      }

      if (isTensuraText(query) && isTensuraText(candidate)) {
         score += 80;
      }

      if (query.contains("magisteel") && candidate.contains("magisteel")) {
         score += 120;
      }

      if (query.contains("mithril") && candidate.contains("mithril")) {
         score += 120;
      }

      if (query.contains("orichalcum") && candidate.contains("orichalcum")) {
         score += 120;
      }

      if (query.contains("adamantite") && candidate.contains("adamantite")) {
         score += 120;
      }

      if (query.contains("hihiirokane") && candidate.contains("hihiirokane")) {
         score += 120;
      }

      if (query.contains("magic stone") && candidate.contains("magic stone")) {
         score += 100;
      }

      if (query.contains("magic ore") && candidate.contains("magic ore")) {
         score += 100;
      }

      return score;
   }

   public static boolean matchesTensuraEntityQuery(LivingEntity entity, String query) {
      String q = query == null ? "" : query.toLowerCase().replace('_', ' ').replace('-', ' ').trim();
      if (q.isBlank()) {
         return false;
      }

      String entityText = entityText(entity);
      if (!entityText.contains("tensura")) {
         return false;
      }

      if (ArsWeissCloneLootIntel.matchesTensuraEntityAlias(q, entityText)) {
         return true;
      }

      for (String part : q.split("[ :]+")) {
         if (part.length() >= 3 && entityText.contains(part)) {
            return true;
         }
      }

      return false;
   }

   public static double tensuraEntityQueryScore(LivingEntity entity, String query) {
      String entityText = entityText(entity);
      String q = query == null ? "" : query.toLowerCase().replace('_', ' ').replace('-', ' ');
      double score = 0.0;
      if (entityText.contains("tensura")) {
         score += 1000.0;
      }

      if (ArsWeissCloneLootIntel.matchesTensuraEntityAlias(q, entityText)) {
         score += 5000.0;
      }

      for (String part : q.split("[ :]+")) {
         if (part.length() >= 3 && entityText.contains(part)) {
            score += 600.0;
         }
      }

      return score;
   }

   private static String itemText(ItemStack stack) {
      if (stack.isEmpty()) {
         return "";
      }

      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      String registry = id == null ? "" : id.toString();
      return (registry + " " + stack.getHoverName().getString()).toLowerCase();
   }

   private static String blockText(BlockState state) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
      return id == null ? "" : id.toString().replace('_', ' ').toLowerCase();
   }

   private static String entityText(LivingEntity entity) {
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      String type = id == null ? "" : id.toString();
      return (type + " " + entity.getName().getString()).replace('_', ' ').toLowerCase();
   }

   private static boolean containsAny(String text, String... parts) {
      for (String part : parts) {
         if (text.contains(part)) {
            return true;
         }
      }

      return false;
   }
}
