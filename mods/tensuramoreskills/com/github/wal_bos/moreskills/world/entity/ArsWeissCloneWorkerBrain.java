package com.github.wal_bos.moreskills.world.entity;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class ArsWeissCloneWorkerBrain {
   private ArsWeissCloneWorkerBrain() {
   }

   public static void sortPlannedMaterials(List<ItemStack> stacks) {
      stacks.sort(Comparator.comparingInt(ArsWeissCloneWorkerBrain::materialPriority).thenComparing(ArsWeissCloneWorkerBrain::itemText));
   }

   public static int materialPriority(ItemStack stack) {
      String text = itemText(stack);
      if (isLikelyMobDropText(text)) {
         return 0;
      } else {
         int tensuraPriority = ArsWeissCloneTensuraSupport.materialPriority(stack);
         if (tensuraPriority < 50) {
            return tensuraPriority;
         } else if (isPlantText(text)) {
            return 1;
         } else if (isWoodText(text)) {
            return 2;
         } else if (isOreMaterialText(text)) {
            return 3;
         } else {
            return !text.contains("stone") && !text.contains("cobblestone") && !text.contains("deepslate") ? 5 : 4;
         }
      }
   }

   public static boolean isLikelyMobDrop(ItemStack stack) {
      return isLikelyMobDropText(itemText(stack));
   }

   public static boolean isLikelyMobDropText(String want) {
      return containsAny(
            want,
            "leather",
            "beef",
            "pork",
            "mutton",
            "chicken",
            "feather",
            "wool",
            "string",
            "spider_eye",
            "bone",
            "arrow",
            "rotten_flesh",
            "ender_pearl",
            "blaze_rod",
            "gunpowder",
            "slime",
            "slime_ball",
            "ghast_tear",
            "phantom_membrane",
            "rabbit",
            "ink_sac",
            "prismarine",
            "shulker_shell",
            "magma_cream"
         )
         || ArsWeissCloneTensuraSupport.isLikelyTensuraMobDropText(want);
   }

   public static boolean isMaterialMobTarget(ServerPlayer owner, LivingEntity entity, String want) {
      if (entity.isAlive() && entity != owner && !(entity instanceof ArsWeissCloneEntity) && !entity.isAlliedTo(owner)) {
         if (entity instanceof Player player && player.getUUID().equals(owner.getUUID())) {
            return false;
         } else {
            String mob = entityText(entity);
            if (ArsWeissCloneLootIntel.matchesLootMob(want, mob)) {
               return true;
            } else if (ArsWeissCloneTensuraSupport.matchesTensuraEntityQuery(entity, want)) {
               return true;
            } else if (containsAny(want, "leather", "beef") && containsAny(mob, "cow", "mooshroom", "horse", "llama", "hoglin")) {
               return true;
            } else if (want.contains("pork") && containsAny(mob, "pig", "hoglin")) {
               return true;
            } else if (containsAny(want, "mutton", "wool") && mob.contains("sheep")) {
               return true;
            } else if (containsAny(want, "chicken", "feather") && mob.contains("chicken")) {
               return true;
            } else if (containsAny(want, "string", "spider_eye") && mob.contains("spider")) {
               return true;
            } else if (containsAny(want, "bone", "arrow") && mob.contains("skeleton")) {
               return true;
            } else if (want.contains("rotten_flesh") && containsAny(mob, "zombie", "drowned", "husk")) {
               return true;
            } else if (want.contains("ender_pearl") && mob.contains("enderman")) {
               return true;
            } else if (want.contains("blaze_rod") && mob.contains("blaze")) {
               return true;
            } else if (want.contains("gunpowder") && containsAny(mob, "creeper", "witch", "ghast")) {
               return true;
            } else if (containsAny(want, "slime", "slime_ball") && mob.contains("slime")) {
               return true;
            } else if (want.contains("ghast_tear") && mob.contains("ghast")) {
               return true;
            } else if (want.contains("phantom_membrane") && mob.contains("phantom")) {
               return true;
            } else if (want.contains("rabbit") && mob.contains("rabbit")) {
               return true;
            } else if (want.contains("ink_sac") && containsAny(mob, "squid", "glow_squid")) {
               return true;
            } else if (want.contains("magma_cream") && mob.contains("magma_cube")) {
               return true;
            } else if (want.contains("shulker_shell") && mob.contains("shulker")) {
               return true;
            } else {
               return want.contains("prismarine") && containsAny(mob, "guardian", "elder_guardian")
                  ? true
                  : ArsWeissCloneTensuraSupport.isTensuraMaterialMobTarget(owner, entity, want);
            }
         }
      } else {
         return false;
      }
   }

   public static double mobScore(LivingEntity entity, ItemStack wanted, LivingEntity clone) {
      String want = itemText(wanted);
      String mobText = entityText(entity);
      double score = clone.distanceToSqr(entity);
      if (isMaterialMobTargetNameExact(want, mobText)) {
         score -= 10000.0;
      }

      if (entity instanceof Mob targetMob && targetMob.getTarget() == clone) {
         score -= 500.0;
      }

      if (ArsWeissCloneTensuraSupport.isTensuraText(want) && mobText.contains("tensura")) {
         score -= 6000.0;
      }

      return score;
   }

   private static boolean isMaterialMobTargetNameExact(String want, String mob) {
      if (want.contains("cow") && mob.contains("cow")) {
         return true;
      } else if (want.contains("sheep") && mob.contains("sheep")) {
         return true;
      } else if (want.contains("spider") && mob.contains("spider")) {
         return true;
      } else if (want.contains("skeleton") && mob.contains("skeleton")) {
         return true;
      } else if (want.contains("zombie") && mob.contains("zombie")) {
         return true;
      } else if (want.contains("enderman") && mob.contains("enderman")) {
         return true;
      } else {
         return want.contains("blaze") && mob.contains("blaze") ? true : want.contains("creeper") && mob.contains("creeper");
      }
   }

   public static int blockSourceScore(BlockState state, ItemStack target) {
      if (!state.isAir() && !target.isEmpty()) {
         String block = blockText(state);
         String item = itemText(target);
         String path = itemPath(target);
         int score = ArsWeissCloneTensuraSupport.blockSourceScore(state, target);
         if (block.contains(path)) {
            score = Math.max(score, 100);
         }

         if (isOreMaterialText(item)) {
            score = Math.max(score, oreScore(block, path, item));
         }

         if (isWoodText(item)) {
            score = Math.max(score, woodScore(block));
         }

         if (isPlantText(item)) {
            score = Math.max(score, plantScore(block, item));
         }

         if (containsAny(item, "sand") && block.contains("sand")) {
            score = Math.max(score, 85);
         }

         if (item.contains("gravel") && block.contains("gravel")) {
            score = Math.max(score, 85);
         }

         if (item.contains("clay") && block.contains("clay")) {
            score = Math.max(score, 85);
         }

         if (containsAny(item, "dirt", "mud") && containsAny(block, "dirt", "mud")) {
            score = Math.max(score, 70);
         }

         if (containsAny(item, "cobblestone", "stone") && containsAny(block, "stone", "deepslate") && !block.contains("bedrock")) {
            score = Math.max(score, item.contains("cobblestone") && block.endsWith("stone") ? 82 : 65);
         }

         if (item.contains("obsidian") && block.contains("obsidian")) {
            score = Math.max(score, 100);
         }

         if (containsAny(item, "ice", "snow") && containsAny(block, "ice", "snow")) {
            score = Math.max(score, 85);
         }

         if (item.contains("netherrack") && block.contains("netherrack")) {
            score = Math.max(score, 90);
         }

         if (item.contains("end_stone") && block.contains("end_stone")) {
            score = Math.max(score, 90);
         }

         if (item.contains("glowstone") && block.contains("glowstone")) {
            score = Math.max(score, 90);
         }

         return score;
      } else {
         return 0;
      }
   }

   private static int oreScore(String block, String path, String item) {
      String material = materialName(path);
      if (material.isBlank()) {
         return 0;
      } else if (containsAny(block, "ore", "deepslate") && block.contains(material)) {
         return 95;
      } else {
         return block.contains(material + "_block") && !item.contains("raw_") ? 60 : 0;
      }
   }

   private static int woodScore(String block) {
      if (containsAny(block, "log", "stem")) {
         return 95;
      } else if (block.contains("wood") && !block.contains("planks")) {
         return 90;
      } else if (block.contains("hyphae")) {
         return 90;
      } else {
         return block.contains("mushroom_stem") ? 70 : 0;
      }
   }

   private static int plantScore(String block, String item) {
      if (containsAny(item, "paper", "book", "map") && containsAny(block, "sugar_cane", "bamboo", "bookshelf", "chiseled_bookshelf")) {
         return 95;
      } else if (item.contains("wheat") && containsAny(block, "wheat", "hay_block")) {
         return 95;
      } else if (item.contains("carrot") && block.contains("carrot")) {
         return 95;
      } else if (item.contains("potato") && block.contains("potato")) {
         return 95;
      } else if (item.contains("beetroot") && block.contains("beetroot")) {
         return 95;
      } else if (item.contains("sugar_cane") && block.contains("sugar_cane")) {
         return 95;
      } else if (item.contains("cactus") && block.contains("cactus")) {
         return 95;
      } else if (item.contains("bamboo") && block.contains("bamboo")) {
         return 95;
      } else if (item.contains("kelp") && block.contains("kelp")) {
         return 95;
      } else if (item.contains("chorus") && block.contains("chorus")) {
         return 95;
      } else if (containsAny(item, "seed", "seeds") && containsAny(block, "wheat", "grass", "fern")) {
         return 65;
      } else {
         return item.contains("apple") && containsAny(block, "oak_leaves", "dark_oak_leaves") ? 50 : 0;
      }
   }

   public static boolean matchesLegacyBlockText(String blockText, String itemPath, String want) {
      return blockSourceScoreText(blockText, itemPath, want) > 0;
   }

   private static int blockSourceScoreText(String block, String path, String item) {
      if (block.contains(path)) {
         return 100;
      } else if (isOreMaterialText(item)) {
         return oreScore(block, path, item);
      } else if (isWoodText(item)) {
         return woodScore(block);
      } else {
         return isPlantText(item) ? plantScore(block, item) : 0;
      }
   }

   private static boolean isOreMaterialText(String text) {
      return containsAny(
         text,
         "ore",
         "raw_",
         "gem",
         "coal",
         "diamond",
         "emerald",
         "quartz",
         "redstone",
         "lapis",
         "copper",
         "iron",
         "gold",
         "netherite",
         "ancient_debris",
         "tin",
         "lead",
         "silver",
         "nickel",
         "aluminum",
         "aluminium",
         "uranium",
         "zinc",
         "osmium",
         "magic_ore",
         "magic ore"
      );
   }

   private static boolean isWoodText(String text) {
      return containsAny(
         text, "log", "wood", "stem", "hyphae", "plank", "stick", "crafting_table", "chest", "barrel", "fence", "door", "trapdoor", "boat", "sign"
      );
   }

   private static boolean isPlantText(String text) {
      return containsAny(
         text,
         "paper",
         "book",
         "map",
         "wheat",
         "carrot",
         "potato",
         "beetroot",
         "seed",
         "sugar_cane",
         "cactus",
         "bamboo",
         "kelp",
         "chorus",
         "apple",
         "sapling",
         "flower",
         "mushroom",
         "nether_wart",
         "hipokute",
         "baffledil",
         "night_lily"
      );
   }

   private static String materialName(String path) {
      String material = path;
      material = material.replace("raw_", "");
      material = material.replace("_ingot", "");
      material = material.replace("_gem", "");
      material = material.replace("_dust", "");
      material = material.replace("_nugget", "");
      material = material.replace("_ore", "");
      material = material.replace("deepslate_", "");
      if (material.contains("diamond")) {
         return "diamond";
      } else if (material.contains("emerald")) {
         return "emerald";
      } else if (material.contains("redstone")) {
         return "redstone";
      } else if (material.contains("lapis")) {
         return "lapis";
      } else if (material.contains("quartz")) {
         return "quartz";
      } else if (material.contains("coal")) {
         return "coal";
      } else {
         return !material.contains("ancient_debris") && !material.contains("netherite") ? material : "ancient_debris";
      }
   }

   private static String itemText(ItemStack stack) {
      if (stack.isEmpty()) {
         return "";
      }

      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      String registry = id == null ? "" : id.toString();
      return (registry + " " + stack.getHoverName().getString()).toLowerCase();
   }

   private static String itemPath(ItemStack stack) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      return id == null ? "" : id.getPath().toLowerCase();
   }

   private static String blockText(BlockState state) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
      return id == null ? "" : id.toString().toLowerCase();
   }

   private static String entityText(LivingEntity entity) {
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      String type = id == null ? "" : id.toString();
      return (type + " " + entity.getName().getString()).toLowerCase();
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
