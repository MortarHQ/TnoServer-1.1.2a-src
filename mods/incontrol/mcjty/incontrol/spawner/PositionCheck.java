package mcjty.incontrol.spawner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.compat.GameStageSupport;
import mcjty.incontrol.compat.LostCitySupport;
import mcjty.incontrol.compat.SereneSeasonsSupport;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.setup.ModSetup;
import mcjty.incontrol.tools.cache.StructureCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.apache.commons.lang3.function.TriFunction;

public class PositionCheck {
   private final TriFunction<Level, BlockPos, Player, Boolean> extraConditions;
   private static final Map<String, PositionCheck.Cmd> CONDITIONS = new HashMap<>();

   private PositionCheck(PositionCheck.Builder builder, boolean defaultIfNone) {
      this.extraConditions = builder.extraConditions == null ? (level, blockPos, player) -> defaultIfNone : builder.extraConditions;
   }

   @Nonnull
   public TriFunction<Level, BlockPos, Player, Boolean> getExtraConditions() {
      return this.extraConditions;
   }

   public static PositionCheck.Builder create(
      BiFunction<TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>> combiner
   ) {
      return new PositionCheck.Builder(combiner);
   }

   public static void parse(JsonObject object, PositionCheck.Builder builder) {
      for (String attr : object.keySet()) {
         PositionCheck.Cmd cmd = CONDITIONS.get(attr);
         if (cmd == null) {
            ErrorHandler.error("Invalid condition '" + attr + "' for spawner rule!");
            return;
         }

         switch (cmd) {
            case MINTIME:
               int mintime = object.getAsJsonPrimitive("mintime").getAsInt();
               builder.extraCondition((level, pos, player) -> (int)(level.getDayTime() % 24000L) >= mintime);
               break;
            case MAXTIME:
               int maxtime = object.getAsJsonPrimitive("maxtime").getAsInt();
               builder.extraCondition((level, pos, player) -> (int)(level.getDayTime() % 24000L) <= maxtime);
               break;
            case MINLIGHT: {
               int minlight = object.getAsJsonPrimitive("minlight").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getBrightness(LightLayer.BLOCK, pos) >= minlight);
               break;
            }
            case MAXLIGHT: {
               int maxlight = object.getAsJsonPrimitive("maxlight").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getBrightness(LightLayer.BLOCK, pos) <= maxlight);
               break;
            }
            case MINLIGHT_FULL: {
               int minlight = object.getAsJsonPrimitive("minlight_full").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getMaxLocalRawBrightness(pos) >= minlight);
               break;
            }
            case MAXLIGHT_FULL: {
               int maxlight = object.getAsJsonPrimitive("maxlight_full").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getMaxLocalRawBrightness(pos) <= maxlight);
               break;
            }
            case MINLIGHT_SKY: {
               int minlight = object.getAsJsonPrimitive("minlight_sky").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getBrightness(LightLayer.SKY, pos) >= minlight);
               break;
            }
            case MAXLIGHT_SKY: {
               int maxlight = object.getAsJsonPrimitive("maxlight_sky").getAsInt();
               builder.extraCondition((level, pos, player) -> level.getBrightness(LightLayer.SKY, pos) <= maxlight);
               break;
            }
            case BIOME:
               JsonElement value = object.get(attr);
               if (!value.isJsonArray()) {
                  ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(value.getAsString()));
                  builder.extraCondition((level, pos, player) -> level.getBiome(pos).is(key));
                  break;
               }

               Set<ResourceKey<Biome>> keys = new HashSet<>();

               for (JsonElement element : value.getAsJsonArray()) {
                  ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(element.getAsString()));
                  keys.add(key);
               }

               Predicate<ResourceKey<Biome>> predicate = keyx -> keys.contains(keyx);
               builder.extraCondition((level, pos, player) -> level.getBiome(pos).is(predicate));
               break;
            case BIOMETAGS:
               JsonElement value = object.get(attr);
               if (!value.isJsonArray()) {
                  TagKey<Biome> tag = TagKey.create(Registries.BIOME, ResourceLocation.parse(value.getAsString()));
                  builder.extraCondition((level, pos, player) -> level.getBiome(pos).is(tag));
                  break;
               }

               Set<TagKey<Biome>> keys = new HashSet<>();

               for (JsonElement element : value.getAsJsonArray()) {
                  ResourceLocation key = ResourceLocation.parse(element.getAsString());
                  keys.add(TagKey.create(Registries.BIOME, key));
               }

               builder.extraCondition((level, pos, player) -> {
                  for (TagKey<Biome> keyx : keys) {
                     Holder<Biome> biome = level.getBiome(pos);
                     if (biome.is(keyx)) {
                        return true;
                     }
                  }

                  return false;
               });
               break;
            case SEESKY:
               boolean seesky = object.getAsJsonPrimitive("seesky").getAsBoolean();
               builder.extraCondition((level, pos, player) -> level.canSeeSkyFromBelowWater(pos) == seesky);
               break;
            case CAVE:
               boolean cave = object.getAsJsonPrimitive("cave").getAsBoolean();
               builder.extraCondition((level, pos, player) -> GenericRuleEvaluator.checkCave(level, pos) == cave);
               break;
            case STRUCTURE:
               JsonElement value = object.get(attr);
               if (!value.isJsonArray()) {
                  String structure = value.getAsString();
                  builder.extraCondition((level, pos, player) -> StructureCache.CACHE.isInStructure(level, structure, pos));
                  break;
               }

               Set<String> keys = new HashSet<>();

               for (JsonElement element : value.getAsJsonArray()) {
                  keys.add(element.getAsString());
               }

               builder.extraCondition((level, pos, player) -> {
                  for (String keyx : keys) {
                     if (StructureCache.CACHE.isInStructure(level, keyx, pos)) {
                        return true;
                     }
                  }

                  return false;
               });
               break;
            case HASSTRUCTURE:
               boolean hasstructure = object.getAsJsonPrimitive("hasstructure").getAsBoolean();
               builder.extraCondition((level, pos, player) -> StructureCache.CACHE.isInAnyStructure(level, pos) == hasstructure);
               break;
            case STRUCTURETAGS:
               JsonElement value = object.get(attr);
               Set<TagKey<Structure>> tagSet = new HashSet<>();
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     ResourceLocation key = ResourceLocation.parse(element.getAsString());
                     TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, key);
                     tagSet.add(tag);
                  }
               } else {
                  ResourceLocation key = ResourceLocation.parse(value.getAsString());
                  TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, key);
                  tagSet.add(tag);
               }

               builder.extraCondition(
                  (level, pos, player) -> {
                     ChunkAccess chunk = level.getChunk(pos);
                     Map<Structure, LongSet> references = chunk.getAllReferences();

                     for (Entry<Structure, LongSet> e : references.entrySet()) {
                        LongSet longs = e.getValue();
                        if (!longs.isEmpty()) {
                           Structure struct = e.getKey();
                           Optional<ResourceKey<Structure>> resourceKey = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getResourceKey(struct);
                           if (resourceKey.isPresent()) {
                              Reference<Structure> holder = (Reference<Structure>)level.registryAccess()
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
               break;
            case INCITY:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               boolean incity = object.getAsJsonPrimitive("incity").getAsBoolean();
               builder.extraCondition((level, pos, player) -> LostCitySupport.isCity(level, pos) == incity);
               break;
            case INBUILDING:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               boolean inbuilding = object.getAsJsonPrimitive("inbuilding").getAsBoolean();
               builder.extraCondition((level, pos, player) -> LostCitySupport.isBuilding(level, pos) == inbuilding);
               break;
            case INMULTIBUILDING:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               boolean inmultibuilding = object.getAsJsonPrimitive("inmultibuilding").getAsBoolean();
               builder.extraCondition((level, pos, player) -> LostCitySupport.isMultiBuilding(level, pos) == inmultibuilding);
               break;
            case BUILDING:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               JsonElement value = object.get(attr);
               Set<String> keys = new HashSet<>();
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     keys.add(element.getAsString());
                  }
               } else {
                  keys.add(value.getAsString());
               }

               builder.extraCondition((level, pos, player) -> {
                  String building = LostCitySupport.getBuildingName(level, pos);
                  return keys.contains(building);
               });
               break;
            case MULTIBUILDING:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               JsonElement value = object.get(attr);
               Set<String> keys = new HashSet<>();
               if (value.isJsonArray()) {
                  for (JsonElement element : value.getAsJsonArray()) {
                     keys.add(element.getAsString());
                  }
               } else {
                  keys.add(value.getAsString());
               }

               builder.extraCondition((level, pos, player) -> {
                  String building = LostCitySupport.getMultiBuildingName(level, pos);
                  return keys.contains(building);
               });
               break;
            case INSTREET:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               boolean instreet = object.getAsJsonPrimitive("instreet").getAsBoolean();
               builder.extraCondition((level, pos, player) -> LostCitySupport.isStreet(level, pos) == instreet);
               break;
            case INSPHERE:
               if (!ModSetup.lostcities) {
                  ErrorHandler.error("Lost Cities condition specified but Lost Cities mod is not loaded!");
                  return;
               }

               boolean inspere = object.getAsJsonPrimitive("inspere").getAsBoolean();
               builder.extraCondition((level, pos, player) -> LostCitySupport.inSphere(level, pos) == inspere);
               break;
            case GAMESTAGE:
               if (!ModSetup.gamestages) {
                  ErrorHandler.error("Game Stages condition specified but Game Stages mod is not loaded!");
                  return;
               }

               JsonElement value = object.get(attr);
               if (!value.isJsonArray()) {
                  String gamestage = value.getAsString();
                  builder.extraCondition((level, pos, player) -> GameStageSupport.hasGameStage(player, gamestage));
                  break;
               }

               Set<String> keys = new HashSet<>();

               for (JsonElement element : value.getAsJsonArray()) {
                  keys.add(element.getAsString());
               }

               builder.extraCondition((level, pos, player) -> {
                  for (String keyx : keys) {
                     if (GameStageSupport.hasGameStage(player, keyx)) {
                        return true;
                     }
                  }

                  return false;
               });
               break;
            case SUMMER:
               if (!ModSetup.sereneSeasons) {
                  ErrorHandler.error("Serene Seasons condition specified but Serene Seasons mod is not loaded!");
                  return;
               }

               boolean summer = object.getAsJsonPrimitive("summer").getAsBoolean();
               builder.extraCondition((level, pos, player) -> SereneSeasonsSupport.isSummer(level) == summer);
               break;
            case WINTER:
               if (!ModSetup.sereneSeasons) {
                  ErrorHandler.error("Serene Seasons condition specified but Serene Seasons mod is not loaded!");
                  return;
               }

               boolean winter = object.getAsJsonPrimitive("winter").getAsBoolean();
               builder.extraCondition((level, pos, player) -> SereneSeasonsSupport.isWinter(level) == winter);
               break;
            case SPRING:
               if (!ModSetup.sereneSeasons) {
                  ErrorHandler.error("Serene Seasons condition specified but Serene Seasons mod is not loaded!");
                  return;
               }

               boolean spring = object.getAsJsonPrimitive("spring").getAsBoolean();
               builder.extraCondition((level, pos, player) -> SereneSeasonsSupport.isSpring(level) == spring);
               break;
            case AUTUMN:
               if (!ModSetup.sereneSeasons) {
                  ErrorHandler.error("Serene Seasons condition specified but Serene Seasons mod is not loaded!");
                  return;
               }

               boolean autumn = object.getAsJsonPrimitive("autumn").getAsBoolean();
               builder.extraCondition((level, pos, player) -> SereneSeasonsSupport.isAutumn(level) == autumn);
         }
      }
   }

   static {
      for (PositionCheck.Cmd cmd : PositionCheck.Cmd.values()) {
         CONDITIONS.put(cmd.name().toLowerCase(), cmd);
      }
   }

   public static class Builder {
      private final BiFunction<TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>> combiner;
      private TriFunction<Level, BlockPos, Player, Boolean> extraConditions = null;

      public Builder(
         BiFunction<TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>> combiner
      ) {
         this.combiner = combiner;
      }

      public PositionCheck.Builder extraCondition(TriFunction<Level, BlockPos, Player, Boolean> extraCondition) {
         if (this.extraConditions == null) {
            this.extraConditions = extraCondition;
         } else {
            TriFunction<Level, BlockPos, Player, Boolean> oldCondition = this.extraConditions;
            this.extraConditions = this.combiner.apply(oldCondition, extraCondition);
         }

         return this;
      }

      public PositionCheck build(boolean defaultIfNone) {
         return new PositionCheck(this, defaultIfNone);
      }
   }

   enum Cmd {
      MINTIME,
      MAXTIME,
      MINLIGHT,
      MAXLIGHT,
      MINLIGHT_FULL,
      MAXLIGHT_FULL,
      MINLIGHT_SKY,
      MAXLIGHT_SKY,
      BIOME,
      BIOMETAGS,
      SEESKY,
      CAVE,
      STRUCTURE,
      HASSTRUCTURE,
      STRUCTURETAGS,
      INCITY,
      INBUILDING,
      INMULTIBUILDING,
      BUILDING,
      MULTIBUILDING,
      INSTREET,
      INSPHERE,
      GAMESTAGE,
      SUMMER,
      WINTER,
      SPRING,
      AUTUMN;
   }
}
