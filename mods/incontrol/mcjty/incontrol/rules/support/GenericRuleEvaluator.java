package mcjty.incontrol.rules.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.areas.Area;
import mcjty.incontrol.areas.AreaSystem;
import mcjty.incontrol.compat.ModRuleCompatibilityLayer;
import mcjty.incontrol.data.DataStorage;
import mcjty.incontrol.events.EventsSystem;
import mcjty.incontrol.mob.CNPCMob;
import mcjty.incontrol.setup.ModSetup;
import mcjty.incontrol.spawner.SpawnerSystem;
import mcjty.incontrol.tools.cache.StructureCache;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.incontrol.tools.rules.IModRuleCompatibilityLayer;
import mcjty.incontrol.tools.rules.TestingBlockTools;
import mcjty.incontrol.tools.rules.TestingTools;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;
import org.apache.commons.lang3.StringUtils;

public class GenericRuleEvaluator {
   private static final Random rnd = new Random();
   private final List<BiFunction<Object, IEventQuery, Boolean>> checks = new ArrayList<>();
   private final IModRuleCompatibilityLayer compatibility = new ModRuleCompatibilityLayer();

   public GenericRuleEvaluator(AttributeMap map) {
      this.addChecks(map);
   }

   private void addChecks(AttributeMap map) {
      map.consume(RuleKeys.RANDOM, this::addRandomCheck);
      map.consumeAsList(RuleKeys.DIMENSION, this::addDimensionCheck);
      map.consumeAsList(RuleKeys.DIMENSION_MOD, this::addDimensionModCheck);
      map.consume(RuleKeys.TIME, this::addTimeCheck);
      map.consume(RuleKeys.MINTIME, this::addMinTimeCheck);
      map.consume(RuleKeys.MAXTIME, this::addMaxTimeCheck);
      map.consume(RuleKeys.HEIGHT, this::addHeightCheck);
      map.consume(RuleKeys.MINHEIGHT, this::addMinHeightCheck);
      map.consume(RuleKeys.MAXHEIGHT, this::addMaxHeightCheck);
      map.consumeAsList(RuleKeys.SOURCE, this::addSourceCheck);
      map.consumeAsList(RuleKeys.MOD, this::addModsCheck);
      map.consume(RuleKeys.WEATHER, this::addWeatherCheck);
      map.consumeAsList(RuleKeys.BIOMETAGS, this::addBiomeTagCheck);
      map.consume(RuleKeys.DIFFICULTY, this::addDifficultyCheck);
      map.consume(RuleKeys.MINSPAWNDIST, this::addMinSpawnDistCheck);
      map.consume(RuleKeys.MAXSPAWNDIST, this::addMaxSpawnDistCheck);
      map.consume(RuleKeys.MINDIST, this::addMinDistCheck);
      map.consume(RuleKeys.MAXDIST, this::addMaxDistCheck);
      map.consume(RuleKeys.LIGHT, this::addLightCheck);
      map.consume(RuleKeys.MINLIGHT, this::addMinLightCheck);
      map.consume(RuleKeys.MAXLIGHT, this::addMaxLightCheck);
      map.consume(RuleKeys.MINLIGHT_FULL, this::addMinLightCheckCorrect);
      map.consume(RuleKeys.MAXLIGHT_FULL, this::addMaxLightCheckCorrect);
      map.consume(RuleKeys.MINLIGHT_SKY, this::addMinLightCheckSky);
      map.consume(RuleKeys.MAXLIGHT_SKY, this::addMaxLightCheckSky);
      map.consume(RuleKeys.MINDIFFICULTY, this::addMinAdditionalDifficultyCheck);
      map.consume(RuleKeys.MAXDIFFICULTY, this::addMaxAdditionalDifficultyCheck);
      map.consume(RuleKeys.SEESKY, this::addSeeSkyCheck);
      map.consume(RuleKeys.SLIME, this::addSlimeChunkCheck);
      map.consume(RuleKeys.AREA, this::addAreaCheck);
      map.consumeAsList(RuleKeys.BLOCK, b1 -> this.addBlocksCheck(map, b1));
      map.consumeAsList(RuleKeys.BLOCKTEST, b1 -> this.addBlockTestCheck(b1));
      map.consumeAsList(RuleKeys.BIOME, this::addBiomesCheck);
      map.consumeAsList(RuleKeys.HELMET, this::addHelmetCheck);
      map.consumeAsList(RuleKeys.CHESTPLATE, this::addChestplateCheck);
      map.consumeAsList(RuleKeys.LEGGINGS, this::addLeggingsCheck);
      map.consumeAsList(RuleKeys.BOOTS, this::addBootsCheck);
      map.consumeAsList(RuleKeys.PLAYER_HELDITEM, items -> this.addHeldItemCheck(items, false));
      map.consumeAsList(RuleKeys.HELDITEM, items -> this.addHeldItemCheck(items, false));
      map.consumeAsList(RuleKeys.OFFHANDITEM, items -> this.addOffHandItemCheck(items, false));
      map.consumeAsList(RuleKeys.BOTHHANDSITEM, this::addBothHandsItemCheck);
      map.consumeAsList(RuleKeys.LACKHELMET, this::addHelmetCheckLacking);
      map.consumeAsList(RuleKeys.LACKCHESTPLATE, this::addChestplateCheckLacking);
      map.consumeAsList(RuleKeys.LACKLEGGINGS, this::addLeggingsCheckLacking);
      map.consumeAsList(RuleKeys.LACKBOOTS, this::addBootsCheckLacking);
      map.consumeAsList(RuleKeys.LACKHELDITEM, items -> this.addHeldItemCheck(items, true));
      map.consumeAsList(RuleKeys.LACKOFFHANDITEM, items -> this.addOffHandItemCheck(items, true));
      map.consume(RuleKeys.HASSTRUCTURE, this::addHasStructureCheck);
      map.consumeAsList(RuleKeys.STRUCTURE, this::addStructureCheck);
      map.consumeAsList(RuleKeys.STRUCTURETAGS, this::addStructureTagsCheck);
      map.consumeAsList(RuleKeys.SCOREBOARDTAGS_ALL, this::addAllScoreboardTagsCheck);
      map.consumeAsList(RuleKeys.SCOREBOARDTAGS_ANY, this::addAnyScoreboardTagsCheck);
      map.consume(RuleKeys.STATE, this::addStateCheck);
      map.consume(RuleKeys.PSTATE, this::addPStateCheck);
      map.consume(RuleKeys.SUMMER, this::addSummerCheck);
      map.consume(RuleKeys.WINTER, this::addWinterCheck);
      map.consume(RuleKeys.SPRING, this::addSpringCheck);
      map.consume(RuleKeys.AUTUMN, this::addAutumnCheck);
      map.consume(RuleKeys.GAMESTAGE, this::addGameStageCheck);
      map.consume(RuleKeys.INCITY, this::addInCityCheck);
      map.consume(RuleKeys.INSTREET, this::addInStreetCheck);
      map.consume(RuleKeys.INSPHERE, this::addInSphereCheck);
      map.consume(RuleKeys.INBUILDING, this::addInBuildingCheck);
      map.consume(RuleKeys.INMULTIBUILDING, this::addInMultiBuildingCheck);
      map.consumeAsList(RuleKeys.BUILDING, this::addBuildingCheck);
      map.consumeAsList(RuleKeys.MULTIBUILDING, this::addMultiBuildingCheck);
      map.consumeAsList(RuleKeys.AMULET, v -> this.addBaubleCheck(v, this.compatibility::getAmuletSlots));
      map.consumeAsList(RuleKeys.RING, v -> this.addBaubleCheck(v, this.compatibility::getRingSlots));
      map.consumeAsList(RuleKeys.BELT, v -> this.addBaubleCheck(v, this.compatibility::getBeltSlots));
      map.consumeAsList(RuleKeys.TRINKET, v -> this.addBaubleCheck(v, this.compatibility::getTrinketSlots));
      map.consumeAsList(RuleKeys.HEAD, v -> this.addBaubleCheck(v, this.compatibility::getHeadSlots));
      map.consumeAsList(RuleKeys.BODY, v -> this.addBaubleCheck(v, this.compatibility::getBodySlots));
      map.consumeAsList(RuleKeys.CHARM, v -> this.addBaubleCheck(v, this.compatibility::getCharmSlots));
      map.consume(RuleKeys.WHEN, b -> {});
      map.consumeAsList(RuleKeys.PHASE, b -> {});
      map.consume(RuleKeys.NUMBER, this::addNumberCheck);
      map.consume(RuleKeys.HOSTILE, this::addHostileCheck);
      map.consume(RuleKeys.PASSIVE, this::addPassiveCheck);
      map.consume(RuleKeys.BABY, this::addBabyCheck);
      map.consume(RuleKeys.CANSPAWNHERE, this::addCanSpawnHereCheck);
      map.consume(RuleKeys.NOTCOLLIDING, this::addNotCollidingCheck);
      map.consume(RuleKeys.SPAWNER, this::addSpawnerCheck);
      map.consumeAsList(RuleKeys.SPAWNTYPE, this::addSpawnTypeCheck);
      map.consume(RuleKeys.INCONTROL, this::addInControlCheck);
      map.consume(RuleKeys.EVENTSPAWN, this::addEventSpawnCheck);
      map.consumeAsList(RuleKeys.MOB, this::addMobsCheck);
      map.consumeAsList(RuleKeys.NPC, this::addNPCsCheck);
      map.consume(RuleKeys.PLAYER, this::addPlayerCheck);
      map.consume(RuleKeys.REALPLAYER, this::addRealPlayerCheck);
      map.consume(RuleKeys.FAKEPLAYER, this::addFakePlayerCheck);
      map.consume(RuleKeys.EXPLOSION, this::addExplosionCheck);
      map.consume(RuleKeys.PROJECTILE, this::addProjectileCheck);
      map.consume(RuleKeys.FIRE, this::addFireCheck);
      map.consume(RuleKeys.MAGIC, this::addMagicCheck);
      map.consume(RuleKeys.MINCOUNT, this::addMinCountCheck);
      map.consume(RuleKeys.MAXCOUNT, this::addMaxCountCheck);
      map.consume(RuleKeys.DAYCOUNT, this::addDayCountCheck);
      map.consume(RuleKeys.MINDAYCOUNT, this::addMinDayCountCheck);
      map.consume(RuleKeys.MAXDAYCOUNT, this::addMaxDayCountCheck);
      map.consume(RuleKeys.CAVE, this::addCaveCheck);
   }

   private void addNumberCheck(String json) {
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json);
      TestingTools.NumberResult result = TestingTools.parseNumberCheck(element);
      if (result != null) {
         this.checks.add((event, query) -> {
            DataStorage data = DataStorage.getData(Tools.getServerWorld(query.getWorld(event)));
            return result.test().test(data.getNumber(result.number()));
         });
      }
   }

   private void addCanSpawnHereCheck(boolean c) {
      if (c) {
         this.checks.add((event, query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof Mob) {
               BlockPos pos = entity.blockPosition();
               Level world = entity.getCommandSenderWorld();
               return TestingTools.isChunkInvalid(world, pos) ? false : Mob.checkMobSpawnRules(entity.getType(), world, MobSpawnType.NATURAL, pos, null);
            } else {
               return false;
            }
         });
      } else {
         this.checks.add((event, query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof Mob) {
               BlockPos pos = entity.blockPosition();
               Level world = entity.getCommandSenderWorld();
               return TestingTools.isChunkInvalid(world, pos) ? false : !Mob.checkMobSpawnRules(entity.getType(), world, MobSpawnType.NATURAL, pos, null);
            } else {
               return true;
            }
         });
      }
   }

   private void addNotCollidingCheck(boolean c) {
      if (c) {
         this.checks.add((event, query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof Mob) {
               BlockPos pos = entity.blockPosition();
               Level world = entity.getCommandSenderWorld();
               return TestingTools.isChunkInvalid(world, pos) ? false : ((Mob)entity).checkSpawnObstruction(world);
            } else {
               return false;
            }
         });
      } else {
         this.checks.add((event, query) -> {
            Entity entity = query.getEntity(event);
            if (entity instanceof Mob mob) {
               BlockPos pos = entity.blockPosition();
               Level world = entity.getCommandSenderWorld();
               return TestingTools.isChunkInvalid(world, pos) ? false : !mob.checkSpawnObstruction(world);
            } else {
               return true;
            }
         });
      }
   }

   private void addInControlCheck(boolean c) {
      this.checks.add((event, query) -> c == (SpawnerSystem.busySpawning != null));
   }

   private void addEventSpawnCheck(boolean c) {
      this.checks.add((event, query) -> c == (EventsSystem.busySpawning != null));
   }

   private void addSpawnTypeCheck(List<String> tp) {
      Set<MobSpawnType> types = new HashSet<>();

      for (String s : tp) {
         MobSpawnType type = MobSpawnType.valueOf(s.toUpperCase());
         types.add(type);
      }

      this.checks
         .add(
            (event, query) -> {
               if (event instanceof FinalizeSpawnEvent checkSpawn) {
                  return types.contains(checkSpawn.getSpawnType());
               } else {
                  return event instanceof PositionCheck checkSpawn
                     ? types.contains(checkSpawn.getSpawnType())
                     : query.getEntity(event) instanceof Mob mob && mob.getSpawnType() != null && types.contains(mob.getSpawnType());
               }
            }
         );
   }

   private void addSpawnerCheck(boolean c) {
      if (c) {
         this.checks
            .add(
               (event, query) -> {
                  if (event instanceof FinalizeSpawnEvent checkSpawn) {
                     return checkSpawn.getSpawnType().equals(MobSpawnType.SPAWNER);
                  } else {
                     return event instanceof PositionCheck checkSpawn
                        ? checkSpawn.getSpawnType().equals(MobSpawnType.SPAWNER)
                        : query.getEntity(event) instanceof Mob mob && Objects.equals(mob.getSpawnType(), MobSpawnType.SPAWNER);
                  }
               }
            );
      } else {
         this.checks
            .add(
               (event, query) -> {
                  if (event instanceof FinalizeSpawnEvent checkSpawn) {
                     return !checkSpawn.getSpawnType().equals(MobSpawnType.SPAWNER);
                  } else {
                     return event instanceof PositionCheck checkSpawn
                        ? !checkSpawn.getSpawnType().equals(MobSpawnType.SPAWNER)
                        : !(query.getEntity(event) instanceof Mob mob && Objects.equals(mob.getSpawnType(), MobSpawnType.SPAWNER));
                  }
               }
            );
      }
   }

   private void addBabyCheck(boolean baby) {
      this.checks.add((event, query) -> query.getEntity(event) instanceof LivingEntity living ? living.isBaby() == baby : false);
   }

   private void addHostileCheck(boolean hostile) {
      if (hostile) {
         this.checks.add((event, query) -> query.getEntity(event) instanceof Enemy);
      } else {
         this.checks.add((event, query) -> !(query.getEntity(event) instanceof Enemy));
      }
   }

   private void addPassiveCheck(boolean passive) {
      if (passive) {
         this.checks.add((event, query) -> query.getEntity(event) instanceof Animal && !(query.getEntity(event) instanceof Enemy));
      } else {
         this.checks.add((event, query) -> !(query.getEntity(event) instanceof Animal) || query.getEntity(event) instanceof Enemy);
      }
   }

   private void addNPCsCheck(List<CNPCMob> mobs) {
      if (mobs.size() == 1) {
         CNPCMob npc = mobs.get(0);
         if (npc != null) {
            this.checks.add((event, query) -> npc.matches(query.getEntity(event)));
         }
      } else {
         for (CNPCMob npc : mobs) {
            if (npc != null) {
               try {
                  this.checks.add((event, query) -> npc.matches(query.getEntity(event)));
               } catch (Exception e) {
                  ErrorHandler.error("Unknown NPC with cloneTab = " + npc.getCloneTab() + " and cloneName = '" + npc.getCloneName() + "'!");
               }
            } else {
               ErrorHandler.error("Unknown NPC!");
            }
         }
      }
   }

   private void addMobsCheck(List<String> mobs) {
      if (mobs.size() == 1) {
         String id = mobs.get(0);
         if (id.startsWith("#")) {
            TagKey<EntityType<?>> tagKey = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(id.substring(1)));
            this.checks.add((event, query) -> query.getEntity(event).getType().is(tagKey));
         } else {
            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(ResourceLocation.parse(id))) {
               ErrorHandler.error("Unknown mob '" + id + "'!");
            }

            EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(id));
            if (type != null) {
               this.checks.add((event, query) -> {
                  Entity entity = query.getEntity(event);
                  return entity == null ? false : type.equals(entity.getType());
               });
            }
         }
      } else {
         Set<EntityType> classes = new HashSet<>();
         Set<TagKey<EntityType<?>>> tagKeys = new HashSet<>();

         for (String id : mobs) {
            if (id.startsWith("#")) {
               TagKey<EntityType<?>> tagKey = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(id.substring(1)));
               tagKeys.add(tagKey);
            } else {
               EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(id));
               if (type != null) {
                  classes.add(type);
               } else {
                  ErrorHandler.error("Unknown mob '" + id + "'!");
               }
            }
         }

         if (!classes.isEmpty() && tagKeys.isEmpty()) {
            this.checks.add((event, query) -> classes.contains(query.getEntity(event).getType()));
         } else if (!classes.isEmpty() && !tagKeys.isEmpty()) {
            this.checks.add((event, query) -> {
               EntityType<?> entityType = query.getEntity(event).getType();
               if (classes.contains(entityType)) {
                  return true;
               }

               for (TagKey<EntityType<?>> tagKey : tagKeys) {
                  if (entityType.is(tagKey)) {
                     return true;
                  }
               }

               return false;
            });
         } else if (classes.isEmpty() && !tagKeys.isEmpty()) {
            this.checks.add((event, query) -> {
               EntityType<?> entityType = query.getEntity(event).getType();

               for (TagKey<EntityType<?>> tagKey : tagKeys) {
                  if (entityType.is(tagKey)) {
                     return true;
                  }
               }

               return false;
            });
         }
      }
   }

   private void addModsCheck(List<String> mods) {
      if (mods.size() == 1) {
         String modid = mods.get(0);
         this.checks.add((event, query) -> {
            EntityType<?> type = query.getEntity(event).getType();
            String mod = BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace();
            return modid.equals(mod);
         });
      } else {
         Set<String> modids = new HashSet<>();
         modids.addAll(mods);
         this.checks.add((event, query) -> {
            EntityType<?> type = query.getEntity(event).getType();
            String mod = BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace();
            return modids.contains(mod);
         });
      }
   }

   private void addRandomCheck(float r) {
      this.checks.add((event, query) -> rnd.nextFloat() < r);
   }

   private void addCaveCheck(boolean cave) {
      this.checks.add((event, query) -> {
         LevelAccessor world = query.getWorld(event);
         BlockPos pos = query.getPos(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : checkCave(world, pos) == cave;
      });
   }

   public static boolean checkCave(LevelAccessor world, BlockPos pos) {
      if (world.canSeeSkyFromBelowWater(pos)) {
         return false;
      }

      int cnt = 0;

      for (Direction direction : Direction.values()) {
         BlockPos p = pos;

         for (int i = 0; i < 50; i++) {
            p = p.relative(direction);
            BlockState state = world.getBlockState(p);
            if (state.is(ModSetup.CAVE_BLOCK)) {
               cnt++;
               break;
            }

            if (!state.isAir() && !state.is(Blocks.WATER) && !state.is(Blocks.LAVA)) {
               break;
            }
         }
      }

      return cnt >= 6;
   }

   private void addSeeSkyCheck(boolean seesky) {
      if (seesky) {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            BlockPos pos = query.getPos(event);
            return TestingTools.isChunkInvalid(world, pos) ? false : world.canSeeSkyFromBelowWater(pos);
         });
      } else {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            BlockPos pos = query.getPos(event);
            return TestingTools.isChunkInvalid(world, pos) ? false : !world.canSeeSkyFromBelowWater(pos);
         });
      }
   }

   private void addSlimeChunkCheck(boolean slime) {
      if (slime) {
         this.checks.add((event, query) -> TestingTools.isSlimeChunk(new ChunkPos(query.getPos(event)), query.getWorld(event)));
      } else {
         this.checks.add((event, query) -> !TestingTools.isSlimeChunk(new ChunkPos(query.getPos(event)), query.getWorld(event)));
      }
   }

   private void addDimensionCheck(List<ResourceKey<Level>> dimensions) {
      if (dimensions.size() == 1) {
         ResourceKey<Level> dim = dimensions.get(0);
         this.checks.add((event, query) -> Tools.getDimensionKey(query.getWorld(event)).equals(dim));
      } else {
         Set<ResourceKey<Level>> dims = new HashSet<>(dimensions);
         this.checks.add((event, query) -> dims.contains(Tools.getDimensionKey(query.getWorld(event))));
      }
   }

   private void addDimensionModCheck(List<String> dimensions) {
      if (dimensions.size() == 1) {
         String dimmod = dimensions.get(0);
         this.checks.add((event, query) -> Tools.getDimensionKey(query.getWorld(event)).location().getNamespace().equals(dimmod));
      } else {
         Set<String> dims = new HashSet<>(dimensions);
         this.checks.add((event, query) -> dims.contains(Tools.getDimensionKey(query.getWorld(event)).location().getNamespace()));
      }
   }

   private void addDifficultyCheck(String difficulty) {
      difficulty = difficulty.toLowerCase();
      Difficulty diff = Difficulty.byName(difficulty);
      if (diff != null) {
         Difficulty finalDiff = diff;
         this.checks.add((event, query) -> query.getWorld(event).getDifficulty() == finalDiff);
      } else {
         ErrorHandler.error("Unknown difficulty '" + difficulty + "'! Use one of 'easy', 'normal', 'hard',  or 'peaceful'");
      }
   }

   private void addWeatherCheck(String weather) {
      boolean raining = weather.toLowerCase().startsWith("rain");
      boolean thunder = weather.toLowerCase().startsWith("thunder");
      if (raining) {
         this.checks.add((event, query) -> query.getWorld(event) instanceof Level level ? level.isRaining() : false);
      } else if (thunder) {
         this.checks.add((event, query) -> query.getWorld(event) instanceof Level level ? level.isThundering() : false);
      } else {
         ErrorHandler.error("Unknown weather '" + weather + "'! Use 'rain' or 'thunder'");
      }
   }

   private void addBiomeTagCheck(List<String> list) {
      Set<TagKey<Biome>> tags = list.stream().map(s -> TagKey.create(Registries.BIOME, ResourceLocation.parse(s))).collect(Collectors.toSet());
      if (tags.size() == 1) {
         TagKey<Biome> key = tags.iterator().next();
         this.checks.add((event, query) -> {
            Holder<Biome> biome = query.getWorld(event).getBiome(query.getPos(event));
            return biome.is(key);
         });
      } else {
         this.checks.add((event, query) -> {
            Holder<Biome> biome = query.getWorld(event).getBiome(query.getPos(event));

            for (TagKey<Biome> tag : tags) {
               if (biome.is(tag)) {
                  return true;
               }
            }

            return false;
         });
      }
   }

   private void addAllScoreboardTagsCheck(List<String> list) {
      Set<String> tags = new HashSet<>(list);
      this.checks.add((event, query) -> query.getEntity(event) instanceof LivingEntity living ? living.getTags().containsAll(tags) : false);
   }

   private void addAnyScoreboardTagsCheck(List<String> list) {
      Set<String> tags = new HashSet<>(list);
      this.checks.add((event, query) -> query.getEntity(event) instanceof LivingEntity living ? living.getTags().stream().anyMatch(tags::contains) : false);
   }

   private void addHasStructureCheck(boolean c) {
      this.checks.add((event, query) -> StructureCache.CACHE.isInAnyStructure(query.getWorld(event), query.getPos(event)) == c);
   }

   private void addStructureCheck(List<String> structures) {
      if (structures.size() == 1) {
         String structure = structures.get(0);
         this.checks.add((event, query) -> StructureCache.CACHE.isInStructure(query.getWorld(event), structure, query.getPos(event)));
      } else {
         Set<String> structureNames = new HashSet<>(structures);
         this.checks.add((event, query) -> {
            for (String structure : structureNames) {
               if (StructureCache.CACHE.isInStructure(query.getWorld(event), structure, query.getPos(event))) {
                  return true;
               }
            }

            return false;
         });
      }
   }

   private void addStructureTagsCheck(List<String> tags) {
      Set<TagKey<Structure>> tagSet = tags.stream().map(s -> TagKey.create(Registries.STRUCTURE, ResourceLocation.parse(s))).collect(Collectors.toSet());
      this.checks
         .add(
            (event, query) -> {
               LevelAccessor world = query.getWorld(event);
               BlockPos pos = query.getPos(event);
               if (TestingTools.isChunkInvalid(world, pos)) {
                  return false;
               }

               ChunkAccess chunk = world.getChunk(pos);
               if (chunk == null) {
                  return false;
               }

               Map<Structure, LongSet> references = chunk.getAllReferences();

               for (Entry<Structure, LongSet> e : references.entrySet()) {
                  LongSet longs = e.getValue();
                  if (!longs.isEmpty()) {
                     Structure struct = e.getKey();
                     Optional<ResourceKey<Structure>> resourceKey = world.registryAccess().registryOrThrow(Registries.STRUCTURE).getResourceKey(struct);
                     if (resourceKey.isPresent()) {
                        Reference<Structure> holder = (Reference<Structure>)world.registryAccess()
                           .registryOrThrow(Registries.STRUCTURE)
                           .getHolder(resourceKey.get())
                           .get();

                        for (TagKey<Structure> tagKey : tagSet) {
                           if (holder.is(tagKey)) {
                              return true;
                           }
                        }
                     }
                  }
               }

               return false;
            }
         );
   }

   private void addBiomesCheck(List<String> biomes) {
      if (biomes.size() == 1) {
         String biomename = biomes.get(0);
         this.checks.add((event, query) -> {
            Holder<Biome> biome = query.getWorld(event).getBiome(query.getPos(event));
            return Tools.getBiomeId(biome).equals(biomename);
         });
      } else {
         Set<String> biomenames = new HashSet<>(biomes);
         this.checks.add((event, query) -> {
            Holder<Biome> biome = query.getWorld(event).getBiome(query.getPos(event));
            String biomeId = Tools.getBiomeId(biome);
            return biomenames.contains(biomeId);
         });
      }
   }

   private void addBlockTestCheck(List<String> blocks) {
      List<TestingBlockTools.BlockMatcherWithSettings> matchers = new ArrayList<>();

      for (String json : blocks) {
         TestingBlockTools.BlockMatcherWithSettings matcher = TestingBlockTools.parseBlockWithSettings(json);
         if (matcher != null) {
            matchers.add(matcher);
         }
      }

      if (!matchers.isEmpty()) {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            BlockPos pos = query.getValidBlockPos(event);
            if (TestingTools.isChunkInvalid(world, pos)) {
               return false;
            }

            for (TestingBlockTools.BlockMatcherWithSettings matcherx : matchers) {
               for (int y = -matcherx.distBelow(); y <= matcherx.distAbove(); y++) {
                  if (y != 0) {
                     BlockPos checkPos = pos.offset(0, y, 0);
                     if (checkPos.getY() >= world.getMinBuildHeight() && checkPos.getY() < world.getMaxBuildHeight()) {
                        if (matcherx.matcher().test(world, checkPos)) {
                           return true;
                        }

                        if (matcherx.onlyAir() && !world.getBlockState(checkPos).isAir()) {
                           break;
                        }
                     }
                  }
               }
            }

            return false;
         });
      }
   }

   private void addBlocksCheck(AttributeMap map, List<String> blocks) {
      String bo = map.consumeAndFetch(RuleKeys.BLOCKOFFSET);
      BiFunction<Object, IEventQuery, BlockPos> posFunction;
      if (bo != null) {
         posFunction = TestingBlockTools.parseOffset(bo);
      } else {
         posFunction = (event, query) -> query.getValidBlockPos(event);
      }

      if (blocks.size() == 1) {
         String json = blocks.get(0);
         BiPredicate<LevelAccessor, BlockPos> blockMatcher = TestingBlockTools.parseBlock(json);
         if (blockMatcher != null) {
            this.checks.add((event, query) -> {
               BlockPos pos = posFunction.apply(event, query);
               LevelAccessor world = query.getWorld(event);
               return pos != null && !TestingTools.isChunkInvalid(world, pos) && blockMatcher.test(world, pos);
            });
         }
      } else {
         List<BiPredicate<LevelAccessor, BlockPos>> blockMatchers = new ArrayList<>();

         for (String block : blocks) {
            BiPredicate<LevelAccessor, BlockPos> blockMatcher = TestingBlockTools.parseBlock(block);
            if (blockMatcher == null) {
               return;
            }

            blockMatchers.add(blockMatcher);
         }

         this.checks.add((event, query) -> {
            BlockPos pos = posFunction.apply(event, query);
            if (pos != null) {
               LevelAccessor world = query.getWorld(event);
               if (TestingTools.isChunkInvalid(world, pos)) {
                  return false;
               }

               for (BiPredicate<LevelAccessor, BlockPos> matcher : blockMatchers) {
                  if (matcher.test(world, pos)) {
                     return true;
                  }
               }
            }

            return false;
         });
      }
   }

   private void addTimeCheck(String time) {
      Predicate<Integer> expression = Tools.parseExpression(time);
      if (expression != null) {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            if (world instanceof Level) {
               long t = ((Level)world).getDayTime();
               return expression.test((int)(t % 24000L));
            } else {
               return false;
            }
         });
      }
   }

   private void addMinTimeCheck(int mintime) {
      this.checks.add((event, query) -> {
         LevelAccessor world = query.getWorld(event);
         if (world instanceof Level) {
            long time = ((Level)world).getDayTime();
            return time % 24000L >= mintime;
         } else {
            return false;
         }
      });
   }

   private void addMaxTimeCheck(int maxtime) {
      this.checks.add((event, query) -> {
         LevelAccessor world = query.getWorld(event);
         if (world instanceof Level) {
            long time = ((Level)world).getDayTime();
            return time % 24000L <= maxtime;
         } else {
            return false;
         }
      });
   }

   private void addMinDistCheck(float v) {
      float d = v * v;
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         Player player = query.getPlayer(event);
         if (player == null) {
            return true;
         }

         double sqdist = pos.distSqr(player.blockPosition());
         return sqdist >= d;
      });
   }

   private void addMaxDistCheck(float v) {
      float d = v * v;
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         Player player = query.getPlayer(event);
         if (player == null) {
            return false;
         }

         double sqdist = pos.distSqr(player.blockPosition());
         return sqdist <= d;
      });
   }

   private void addMinSpawnDistCheck(float v) {
      float d = v * v;
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         ServerLevel sw = Tools.getServerWorld(query.getWorld(event));
         double sqdist = pos.distSqr(sw.getSharedSpawnPos());
         return sqdist >= d;
      });
   }

   private void addMaxSpawnDistCheck(float v) {
      float d = v * v;
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         ServerLevel sw = Tools.getServerWorld(query.getWorld(event));
         double sqdist = pos.distSqr(sw.getSharedSpawnPos());
         return sqdist <= d;
      });
   }

   private void addLightCheck(String expression) {
      Predicate<Integer> exp = Tools.parseExpression(expression);
      if (exp != null) {
         this.checks.add((event, query) -> {
            BlockPos pos = query.getPos(event);
            LevelAccessor world = query.getWorld(event);
            return TestingTools.isChunkInvalid(world, pos) ? false : exp.test(world.getBrightness(LightLayer.BLOCK, pos));
         });
      }
   }

   private void addMinLightCheck(int minlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getBrightness(LightLayer.BLOCK, pos) >= minlight;
      });
   }

   private void addMinLightCheckSky(int minlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getBrightness(LightLayer.SKY, pos) >= minlight;
      });
   }

   private void addMinLightCheckCorrect(int minlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getMaxLocalRawBrightness(pos) >= minlight;
      });
   }

   private void addMaxLightCheck(int maxlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getBrightness(LightLayer.BLOCK, pos) <= maxlight;
      });
   }

   private void addMaxLightCheckSky(int maxlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getBrightness(LightLayer.SKY, pos) <= maxlight;
      });
   }

   private void addMaxLightCheckCorrect(int maxlight) {
      this.checks.add((event, query) -> {
         BlockPos pos = query.getPos(event);
         LevelAccessor world = query.getWorld(event);
         return TestingTools.isChunkInvalid(world, pos) ? false : world.getMaxLocalRawBrightness(pos) <= maxlight;
      });
   }

   private void addMinAdditionalDifficultyCheck(Float mindifficulty) {
      this.checks.add((event, query) -> query.getWorld(event).getCurrentDifficultyAt(query.getPos(event)).getEffectiveDifficulty() >= mindifficulty);
   }

   private void addMaxAdditionalDifficultyCheck(Float maxdifficulty) {
      this.checks.add((event, query) -> query.getWorld(event).getCurrentDifficultyAt(query.getPos(event)).getEffectiveDifficulty() <= maxdifficulty);
   }

   private void addHeightCheck(String input) {
      Predicate<Integer> expression = Tools.parseExpression(input);
      if (expression != null) {
         this.checks.add((event, query) -> expression.test(query.getY(event)));
      }
   }

   private void addMaxHeightCheck(int maxheight) {
      this.checks.add((event, query) -> query.getY(event) <= maxheight);
   }

   private void addMinHeightCheck(int minheight) {
      this.checks.add((event, query) -> query.getY(event) >= minheight);
   }

   private void addHelmetCheck(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.HEAD, false);
   }

   private void addChestplateCheck(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.CHEST, false);
   }

   private void addLeggingsCheck(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.LEGS, false);
   }

   private void addBootsCheck(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.FEET, false);
   }

   private void addHelmetCheckLacking(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.HEAD, true);
   }

   private void addChestplateCheckLacking(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.CHEST, true);
   }

   private void addLeggingsCheckLacking(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.LEGS, true);
   }

   private void addBootsCheckLacking(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.addArmorCheck(items, EquipmentSlot.FEET, true);
   }

   private void addArmorCheck(List<Predicate<ItemStack>> items, EquipmentSlot slot, boolean lacking) {
      this.checks.add((event, query) -> {
         Player player = query.getPlayer(event);
         if (player != null) {
            ItemStack armorItem = player.getItemBySlot(slot);
            if (!armorItem.isEmpty()) {
               for (Predicate<ItemStack> item : items) {
                  if (item.test(armorItem)) {
                     return !lacking;
                  }
               }
            }
         }

         return lacking;
      });
   }

   private void addHeldItemCheck(List<String> itemList, boolean lacking) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.checks.add((event, query) -> {
         Player player = query.getPlayer(event);
         if (player != null) {
            ItemStack mainhand = player.getMainHandItem();
            if (!mainhand.isEmpty()) {
               for (Predicate<ItemStack> item : items) {
                  if (item.test(mainhand)) {
                     return !lacking;
                  }
               }
            }
         }

         return lacking;
      });
   }

   private void addOffHandItemCheck(List<String> itemList, boolean lacking) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.checks.add((event, query) -> {
         Player player = query.getPlayer(event);
         if (player != null) {
            ItemStack offhand = player.getOffhandItem();
            if (!offhand.isEmpty()) {
               for (Predicate<ItemStack> item : items) {
                  if (item.test(offhand)) {
                     return !lacking;
                  }
               }
            }
         }

         return lacking;
      });
   }

   private void addBothHandsItemCheck(List<String> itemList) {
      List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
      this.checks.add((event, query) -> {
         Player player = query.getPlayer(event);
         if (player != null) {
            ItemStack offhand = player.getOffhandItem();
            if (!offhand.isEmpty()) {
               for (Predicate<ItemStack> item : items) {
                  if (item.test(offhand)) {
                     return true;
                  }
               }
            }

            ItemStack mainhand = player.getMainHandItem();
            if (!mainhand.isEmpty()) {
               for (Predicate<ItemStack> item : items) {
                  if (item.test(mainhand)) {
                     return true;
                  }
               }
            }
         }

         return false;
      });
   }

   private void addStateCheck(String s) {
      if (!this.compatibility.hasEnigmaScript()) {
         TestingTools.warn("EnigmaScript is missing: this test cannot work!");
      } else {
         try {
            String[] split = StringUtils.split(s, '=');
            String state = split[0];
            String value = split[1];
            this.checks.add((event, query) -> value.equals(this.compatibility.getState(query.getWorld(event), state)));
         } catch (Exception e) {
            ErrorHandler.error("Bad state=value specifier '" + s + "'!");
         }
      }
   }

   private void addPStateCheck(String s) {
      if (!this.compatibility.hasEnigmaScript()) {
         TestingTools.warn("EnigmaScript is missing: this test cannot work!");
      } else {
         try {
            String[] split = StringUtils.split(s, '=');
            String state = split[0];
            String value = split[1];
            this.checks.add((event, query) -> value.equals(this.compatibility.getPlayerState(query.getPlayer(event), state)));
         } catch (Exception e) {
            ErrorHandler.error("Bad state=value specifier '" + s + "'!");
         }
      }
   }

   private void addSummerCheck(Boolean s) {
      if (!this.compatibility.hasSereneSeasons()) {
         TestingTools.warn("Serene Seasons is missing: this test cannot work!");
      } else {
         this.checks.add((event, query) -> s == this.compatibility.isSummer(Tools.getServerWorld(query.getWorld(event))));
      }
   }

   private void addWinterCheck(Boolean s) {
      if (!this.compatibility.hasSereneSeasons()) {
         TestingTools.warn("Serene Seasons is missing: this test cannot work!");
      } else {
         this.checks.add((event, query) -> s == this.compatibility.isWinter(Tools.getServerWorld(query.getWorld(event))));
      }
   }

   private void addSpringCheck(Boolean s) {
      if (!this.compatibility.hasSereneSeasons()) {
         TestingTools.warn("Serene Seasons is missing: this test cannot work!");
      } else {
         this.checks.add((event, query) -> s == this.compatibility.isSpring(Tools.getServerWorld(query.getWorld(event))));
      }
   }

   private void addAutumnCheck(Boolean s) {
      if (!this.compatibility.hasSereneSeasons()) {
         TestingTools.warn("Serene Seasons is missing: this test cannot work!");
      } else {
         this.checks.add((event, query) -> s == this.compatibility.isAutumn(Tools.getServerWorld(query.getWorld(event))));
      }
   }

   private void addGameStageCheck(String stage) {
      if (!this.compatibility.hasGameStages()) {
         TestingTools.warn("Game Stages is missing: the 'gamestage' test cannot work!");
      } else {
         this.checks.add((event, query) -> this.compatibility.hasGameStage(query.getPlayer(event), stage));
      }
   }

   private void addInCityCheck(boolean incity) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'incity' test cannot work!");
      } else {
         if (incity) {
            this.checks.add((event, query) -> this.compatibility.isCity(query, event));
         } else {
            this.checks.add((event, query) -> !this.compatibility.isCity(query, event));
         }
      }
   }

   private void addInStreetCheck(boolean instreet) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'instreet' test cannot work!");
      } else {
         if (instreet) {
            this.checks.add((event, query) -> this.compatibility.isStreet(query, event));
         } else {
            this.checks.add((event, query) -> !this.compatibility.isStreet(query, event));
         }
      }
   }

   private void addInSphereCheck(boolean insphere) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'insphere' test cannot work!");
      } else {
         if (insphere) {
            this.checks.add((event, query) -> this.compatibility.inSphere(query, event));
         } else {
            this.checks.add((event, query) -> !this.compatibility.inSphere(query, event));
         }
      }
   }

   private void addInBuildingCheck(boolean inbuilding) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'inbuilding' test cannot work!");
      } else {
         if (inbuilding) {
            this.checks.add((event, query) -> this.compatibility.isBuilding(query, event));
         } else {
            this.checks.add((event, query) -> !this.compatibility.isBuilding(query, event));
         }
      }
   }

   private void addInMultiBuildingCheck(boolean inbuilding) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'inbuilding' test cannot work!");
      } else {
         if (inbuilding) {
            this.checks.add((event, query) -> this.compatibility.isMultiBuilding(query, event));
         } else {
            this.checks.add((event, query) -> !this.compatibility.isMultiBuilding(query, event));
         }
      }
   }

   private void addBuildingCheck(List<String> buildings) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'building' test cannot work!");
      } else {
         Set<String> buildingSet = new HashSet<>(buildings);
         this.checks.add((event, query) -> {
            String building = this.compatibility.getBuilding(query, event);
            return building != null && buildingSet.contains(building);
         });
      }
   }

   private void addMultiBuildingCheck(List<String> buildings) {
      if (!this.compatibility.hasLostCities()) {
         TestingTools.warn("The Lost Cities is missing: the 'building' test cannot work!");
      } else {
         Set<String> buildingSet = new HashSet<>(buildings);
         this.checks.add((event, query) -> {
            String building = this.compatibility.getMultiBuilding(query, event);
            return building != null && buildingSet.contains(building);
         });
      }
   }

   private void addBaubleCheck(List<String> itemList, Supplier<int[]> slotSupplier) {
      if (!this.compatibility.hasBaubles()) {
         TestingTools.warn("Baubles is missing: this test cannot work!");
      } else {
         List<Predicate<ItemStack>> items = TestingTools.getItems(itemList);
         this.checks.add((event, query) -> {
            Player player = query.getPlayer(event);
            if (player != null) {
               for (int slot : slotSupplier.get()) {
                  ItemStack stack = this.compatibility.getBaubleStack(player, slot);
                  if (!stack.isEmpty()) {
                     for (Predicate<ItemStack> item : items) {
                        if (item.test(stack)) {
                           return true;
                        }
                     }
                  }
               }
            }

            return false;
         });
      }
   }

   private void addMinCountCheck(String json) {
      CountInfo info = CountInfo.parseCountInfo(json);
      if (info != null) {
         BiFunction<LevelAccessor, Entity, Integer> counter = info.getCounter();
         Function<LevelAccessor, Integer> amountAdjuster = CountInfo.getAmountAdjuster(info, info.amount);
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            Entity entity = query.getEntity(event);
            int count = counter.apply(world, entity);
            int amount = amountAdjuster.apply(world);
            return count >= amount;
         });
      }
   }

   private void addMaxCountCheck(String json) {
      CountInfo info = CountInfo.parseCountInfo(json);
      BiFunction<LevelAccessor, Entity, Integer> counter = info.getCounter();
      Function<LevelAccessor, Integer> amountAdjuster = CountInfo.getAmountAdjuster(info, info.amount);
      this.checks.add((event, query) -> {
         LevelAccessor world = query.getWorld(event);
         Entity entity = query.getEntity(event);
         int count = counter.apply(world, entity);
         int amount = amountAdjuster.apply(world);
         return count < amount;
      });
   }

   private void addDayCountCheck(Object count) {
      if (count != null) {
         if (count instanceof Integer c) {
            this.checks.add((event, query) -> {
               LevelAccessor world = query.getWorld(event);
               DataStorage data = DataStorage.getData(Tools.getServerWorld(world));
               int amount = data.getDaycounter();
               return amount % c == 0;
            });
         } else if (count instanceof String input) {
            Predicate<Integer> expression = Tools.parseExpression(input);
            if (expression == null) {
               return;
            }

            this.checks.add((event, query) -> {
               LevelAccessor world = query.getWorld(event);
               DataStorage data = DataStorage.getData(Tools.getServerWorld(world));
               int amount = data.getDaycounter();
               return expression.test(amount);
            });
         }
      }
   }

   private void addMinDayCountCheck(Integer count) {
      if (count != null) {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            DataStorage data = DataStorage.getData(Tools.getServerWorld(world));
            int amount = data.getDaycounter();
            return amount >= count;
         });
      }
   }

   private void addMaxDayCountCheck(Integer count) {
      if (count != null) {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            DataStorage data = DataStorage.getData(Tools.getServerWorld(world));
            int amount = data.getDaycounter();
            return amount < count;
         });
      }
   }

   private void addPlayerCheck(boolean asPlayer) {
      if (asPlayer) {
         this.checks.add((event, query) -> query.getAttacker(event) instanceof Player);
      } else {
         this.checks.add((event, query) -> query.getAttacker(event) instanceof Player);
      }
   }

   private void addRealPlayerCheck(boolean asPlayer) {
      if (asPlayer) {
         this.checks.add((event, query) -> query.getAttacker(event) == null ? false : TestingTools.isRealPlayer(query.getAttacker(event)));
      } else {
         this.checks.add((event, query) -> query.getAttacker(event) == null ? true : !TestingTools.isRealPlayer(query.getAttacker(event)));
      }
   }

   private void addFakePlayerCheck(boolean asPlayer) {
      if (asPlayer) {
         this.checks.add((event, query) -> query.getAttacker(event) == null ? false : TestingTools.isFakePlayer(query.getAttacker(event)));
      } else {
         this.checks.add((event, query) -> query.getAttacker(event) == null ? true : !TestingTools.isFakePlayer(query.getAttacker(event)));
      }
   }

   private void addExplosionCheck(boolean explosion) {
      if (explosion) {
         this.checks.add((event, query) -> query.getSource(event) == null ? false : query.getSource(event).is(DamageTypes.EXPLOSION));
      } else {
         this.checks.add((event, query) -> query.getSource(event) == null ? true : !query.getSource(event).is(DamageTypes.EXPLOSION));
      }
   }

   private void addProjectileCheck(boolean projectile) {
      if (projectile) {
         this.checks.add((event, query) -> query.getSource(event) == null ? false : query.getSource(event).is(DamageTypes.EXPLOSION));
      } else {
         this.checks.add((event, query) -> query.getSource(event) == null ? true : !query.getSource(event).is(DamageTypes.EXPLOSION));
      }
   }

   private void addFireCheck(boolean fire) {
      if (fire) {
         this.checks.add((event, query) -> query.getSource(event) == null ? false : query.getSource(event).is(DamageTypes.IN_FIRE));
      } else {
         this.checks.add((event, query) -> query.getSource(event) == null ? true : !query.getSource(event).is(DamageTypes.IN_FIRE));
      }
   }

   private void addMagicCheck(boolean magic) {
      if (magic) {
         this.checks.add((event, query) -> query.getSource(event) == null ? false : query.getSource(event).is(DamageTypes.MAGIC));
      } else {
         this.checks.add((event, query) -> query.getSource(event) == null ? true : !query.getSource(event).is(DamageTypes.MAGIC));
      }
   }

   private void addSourceCheck(List<String> sources) {
      Set<String> sourceSet = new HashSet<>(sources);
      this.checks.add((event, query) -> query.getSource(event) == null ? false : sourceSet.contains(query.getSource(event).getMsgId()));
   }

   private void addAreaCheck(String areaName) {
      Area area = AreaSystem.getArea(areaName);
      if (area == null) {
         ErrorHandler.error("Cannot find area '" + areaName + "'!");
      } else {
         this.checks.add((event, query) -> {
            LevelAccessor world = query.getWorld(event);
            ResourceKey<Level> key = Tools.getDimensionKey(world);
            if (area.dimension() == key) {
               BlockPos pos = query.getPos(event);
               return area.isInArea(pos.getX(), pos.getY(), pos.getZ());
            } else {
               return false;
            }
         });
      }
   }

   public boolean match(Object event, IEventQuery query) {
      for (BiFunction<Object, IEventQuery, Boolean> rule : this.checks) {
         if (!rule.apply(event, query)) {
            return false;
         }
      }

      return true;
   }
}
