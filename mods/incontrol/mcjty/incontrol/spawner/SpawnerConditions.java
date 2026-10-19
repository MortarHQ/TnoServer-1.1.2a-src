package mcjty.incontrol.spawner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import mcjty.incontrol.ErrorHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.TriFunction;

public class SpawnerConditions {
   public static final BiFunction<TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>> AND_COMBINER = (old, extra) -> old
         == null
      ? extra
      : (level, pos, player) -> (Boolean)old.apply(level, pos, player) && (Boolean)extra.apply(level, pos, player);
   public static final BiFunction<TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>, TriFunction<Level, BlockPos, Player, Boolean>> OR_COMBINER = (old, extra) -> old
         == null
      ? extra
      : (level, pos, player) -> (Boolean)old.apply(level, pos, player) || (Boolean)extra.apply(level, pos, player);
   private final Set<ResourceKey<Level>> dimensions;
   private final int mindist;
   private final int maxdist;
   private final int verticalMindist;
   private final int verticalMaxdist;
   private final int minheight;
   private final int maxheight;
   private final int mindaycount;
   private final int maxdaycount;
   private final boolean inLiquid;
   private final boolean inWater;
   private final boolean inLava;
   private final boolean inAir;
   private final boolean noRestrictions;
   private final int maxthis;
   private final int maxlocal;
   private final int maxtotal;
   private final int maxhostile;
   private final int maxpeaceful;
   private final int maxneutral;
   private final boolean validSpawn;
   private final boolean sturdy;
   private final PositionCheck positiveCheck;
   private final PositionCheck negativeCheck;
   public static final SpawnerConditions DEFAULT = create().build();
   private static final Map<String, SpawnerConditions.Cmd> CONDITIONS = new HashMap<>();

   private SpawnerConditions(SpawnerConditions.Builder builder) {
      this.dimensions = new HashSet<>(builder.dimensions);
      this.mindist = builder.mindist;
      this.maxdist = builder.maxdist;
      this.verticalMindist = builder.verticalMindist;
      this.verticalMaxdist = builder.verticalMaxdist;
      this.minheight = builder.minheight;
      this.maxheight = builder.maxheight;
      this.mindaycount = builder.mindaycount;
      this.maxdaycount = builder.maxdaycount;
      this.inLiquid = builder.inLiquid;
      this.inWater = builder.inWater;
      this.inLava = builder.inLava;
      this.inAir = builder.inAir;
      this.maxthis = builder.maxthis;
      this.maxlocal = builder.maxlocal;
      this.maxtotal = builder.maxtotal;
      this.maxhostile = builder.maxhostile;
      this.maxpeaceful = builder.maxpeaceful;
      this.maxneutral = builder.maxneutral;
      this.noRestrictions = builder.noRestrictions;
      this.validSpawn = builder.validSpawn;
      this.sturdy = builder.sturdy;
      if (builder.positiveCheck == null) {
         this.positiveCheck = PositionCheck.create(AND_COMBINER).build(true);
      } else {
         this.positiveCheck = builder.positiveCheck;
      }

      if (builder.negativeCheck == null) {
         this.negativeCheck = PositionCheck.create(OR_COMBINER).build(false);
      } else {
         this.negativeCheck = builder.negativeCheck;
      }
   }

   public void validate() {
      if (this.dimensions.isEmpty()) {
         throw new IllegalStateException("No dimensions specified!");
      }

      if (this.mindaycount < 0) {
         throw new IllegalStateException("Invalid negative minimum daycount!");
      }

      if (this.maxdaycount < 0) {
         throw new IllegalStateException("Invalid negative maximum daycount!");
      }

      if (this.mindist < 0) {
         throw new IllegalStateException("Invalid negative minimum distance!");
      }

      if (this.maxdist < 0) {
         throw new IllegalStateException("Invalid negative maximum distance!");
      }

      if (this.mindist >= this.maxdist) {
         throw new IllegalStateException("Minimum distance must be smaller then maximum!");
      }

      if (this.minheight >= this.maxheight) {
         throw new IllegalStateException("Minimum height must be smaller then maximum!");
      }
   }

   public Set<ResourceKey<Level>> getDimensions() {
      return this.dimensions;
   }

   public int getMindaycount() {
      return this.mindaycount;
   }

   public int getMaxdaycount() {
      return this.maxdaycount;
   }

   public int getMindist() {
      return this.mindist;
   }

   public int getMaxdist() {
      return this.maxdist;
   }

   public int getVerticalMindist() {
      return this.verticalMindist;
   }

   public int getVerticalMaxdist() {
      return this.verticalMaxdist;
   }

   public int getMinheight() {
      return this.minheight;
   }

   public int getMaxheight() {
      return this.maxheight;
   }

   public boolean isInLiquid() {
      return this.inLiquid;
   }

   public boolean isInWater() {
      return this.inWater;
   }

   public boolean isInLava() {
      return this.inLava;
   }

   public boolean isInAir() {
      return this.inAir;
   }

   public boolean isNoRestrictions() {
      return this.noRestrictions;
   }

   public int getMaxthis() {
      return this.maxthis;
   }

   public int getMaxlocal() {
      return this.maxlocal;
   }

   public int getMaxtotal() {
      return this.maxtotal;
   }

   public int getMaxhostile() {
      return this.maxhostile;
   }

   public int getMaxpeaceful() {
      return this.maxpeaceful;
   }

   public int getMaxneutral() {
      return this.maxneutral;
   }

   public boolean isValidSpawn() {
      return this.validSpawn;
   }

   public boolean isSturdy() {
      return this.sturdy;
   }

   public PositionCheck getPositiveCheck() {
      return this.positiveCheck;
   }

   public PositionCheck getNegativeCheck() {
      return this.negativeCheck;
   }

   public static SpawnerConditions.Builder create() {
      return new SpawnerConditions.Builder();
   }

   public static void parse(JsonObject object, SpawnerConditions.Builder builder) {
      for (String attr : object.keySet()) {
         SpawnerConditions.Cmd cmd = CONDITIONS.get(attr);
         if (cmd == null) {
            ErrorHandler.error("Invalid condition '" + attr + "' for spawner rule!");
            return;
         }

         switch (cmd) {
            case DIMENSION:
               JsonElement valuex = object.get(attr);
               if (valuex.isJsonArray()) {
                  for (JsonElement element : valuex.getAsJsonArray()) {
                     ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(element.getAsString()));
                     builder.dimensions(key);
                  }
                  break;
               }

               ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(valuex.getAsString()));
               builder.dimensions(key);
               break;
            case MINDIST:
               builder.distance(object.getAsJsonPrimitive("mindist").getAsInt(), builder.maxdist);
               break;
            case MAXDIST:
               builder.distance(builder.mindist, object.getAsJsonPrimitive("maxdist").getAsInt());
               break;
            case MINVERTICALDIST:
               builder.verticalDistance(object.getAsJsonPrimitive("minverticaldist").getAsInt(), builder.verticalMaxdist);
               break;
            case MAXVERTICALDIST:
               builder.verticalDistance(builder.verticalMindist, object.getAsJsonPrimitive("maxverticaldist").getAsInt());
               break;
            case MINDAYCOUNT:
               builder.daycount(object.getAsJsonPrimitive("mindaycount").getAsInt(), builder.maxdaycount);
               break;
            case MAXDAYCOUNT:
               builder.daycount(builder.mindaycount, object.getAsJsonPrimitive("maxdaycount").getAsInt());
               break;
            case MINHEIGHT:
               builder.height(object.getAsJsonPrimitive("minheight").getAsInt(), builder.maxheight);
               break;
            case MAXHEIGHT:
               builder.height(builder.minheight, object.getAsJsonPrimitive("maxheight").getAsInt());
               break;
            case INWATER:
               builder.inWater(object.getAsJsonPrimitive("inwater").getAsBoolean());
               break;
            case INLAVA:
               builder.inLava(object.getAsJsonPrimitive("inlava").getAsBoolean());
               break;
            case INLIQUID:
               builder.inLiquid(object.getAsJsonPrimitive("inliquid").getAsBoolean());
               break;
            case INAIR:
               builder.inAir(object.getAsJsonPrimitive("inair").getAsBoolean());
               break;
            case NORESTRICTIONS:
               builder.noRestrictions(object.getAsJsonPrimitive("norestrictions").getAsBoolean());
               break;
            case MAXTHIS:
               builder.maxThis(object.getAsJsonPrimitive("maxthis").getAsInt());
               break;
            case MAXLOCAL:
               builder.maxLocal(object.getAsJsonPrimitive("maxlocal").getAsInt());
               break;
            case MAXTOTAL:
               builder.maxTotal(object.getAsJsonPrimitive("maxtotal").getAsInt());
               break;
            case MAXHOSTILE:
               builder.maxHostile(object.getAsJsonPrimitive("maxhostile").getAsInt());
               break;
            case MAXPEACEFUL:
               builder.maxPeaceful(object.getAsJsonPrimitive("maxpeaceful").getAsInt());
               break;
            case MAXNEUTRAL:
               builder.maxNeutral(object.getAsJsonPrimitive("maxneutral").getAsInt());
               break;
            case VALIDSPAWN:
               builder.validSpawn(object.getAsJsonPrimitive("validspawn").getAsBoolean());
               break;
            case STURDY:
               builder.sturdy(object.getAsJsonPrimitive("sturdy").getAsBoolean());
               break;
            case AND:
               JsonElement value = object.get(attr);
               if (value.isJsonObject()) {
                  PositionCheck.Builder checkBuilder = new PositionCheck.Builder(AND_COMBINER);
                  PositionCheck.parse(value.getAsJsonObject(), checkBuilder);
                  builder.positiveCheck(checkBuilder.build(true));
               } else {
                  ErrorHandler.error("Invalid condition '" + attr + "' for spawner rule!");
               }
               break;
            case NOT:
               JsonElement valuex = object.get(attr);
               if (valuex.isJsonObject()) {
                  PositionCheck.Builder checkBuilder = new PositionCheck.Builder(OR_COMBINER);
                  PositionCheck.parse(valuex.getAsJsonObject(), checkBuilder);
                  builder.negativeCheck(checkBuilder.build(false));
               } else {
                  ErrorHandler.error("Invalid condition '" + attr + "' for spawner rule!");
               }
         }
      }
   }

   static {
      for (SpawnerConditions.Cmd cmd : SpawnerConditions.Cmd.values()) {
         CONDITIONS.put(cmd.name().toLowerCase(), cmd);
      }
   }

   public static class Builder {
      private final Set<ResourceKey<Level>> dimensions = new HashSet<>();
      private int mindist = 24;
      private int maxdist = 120;
      private int verticalMindist = -1;
      private int verticalMaxdist = -1;
      private int mindaycount = 0;
      private int maxdaycount = Integer.MAX_VALUE;
      private int minheight = 1;
      private int maxheight = 256;
      private boolean inLiquid = false;
      private boolean inWater = false;
      private boolean inLava = false;
      private boolean inAir = false;
      private boolean noRestrictions = false;
      private boolean validSpawn = false;
      private boolean sturdy = false;
      private int maxthis = -1;
      private int maxlocal = -1;
      private int maxtotal = -1;
      private int maxhostile = -1;
      private int maxpeaceful = -1;
      private int maxneutral = -1;
      private PositionCheck positiveCheck = null;
      private PositionCheck negativeCheck = null;

      public SpawnerConditions.Builder dimensions(ResourceKey<Level>... dimensions) {
         Collections.addAll(this.dimensions, dimensions);
         return this;
      }

      public SpawnerConditions.Builder noRestrictions(boolean noRestrictions) {
         this.noRestrictions = noRestrictions;
         return this;
      }

      public SpawnerConditions.Builder daycount(int min, int max) {
         this.mindaycount = min;
         this.maxdaycount = max;
         return this;
      }

      public SpawnerConditions.Builder distance(int min, int max) {
         this.mindist = min;
         this.maxdist = max;
         return this;
      }

      public SpawnerConditions.Builder verticalDistance(int min, int max) {
         this.verticalMindist = min;
         this.verticalMaxdist = max;
         return this;
      }

      public SpawnerConditions.Builder height(int min, int max) {
         this.minheight = min;
         this.maxheight = max;
         return this;
      }

      public SpawnerConditions.Builder inLiquid(boolean inLiquid) {
         this.inLiquid = inLiquid;
         return this;
      }

      public SpawnerConditions.Builder inWater(boolean inWater) {
         this.inWater = inWater;
         return this;
      }

      public SpawnerConditions.Builder inLava(boolean inLava) {
         this.inLava = inLava;
         return this;
      }

      public SpawnerConditions.Builder inAir(boolean inAir) {
         this.inAir = inAir;
         return this;
      }

      public SpawnerConditions.Builder maxThis(int maxThis) {
         this.maxthis = maxThis;
         return this;
      }

      public SpawnerConditions.Builder maxLocal(int maxLocal) {
         this.maxlocal = maxLocal;
         return this;
      }

      public SpawnerConditions.Builder maxTotal(int maxTotal) {
         this.maxtotal = maxTotal;
         return this;
      }

      public SpawnerConditions.Builder maxHostile(int maxHostile) {
         this.maxhostile = maxHostile;
         return this;
      }

      public SpawnerConditions.Builder maxPeaceful(int maxPeaceful) {
         this.maxpeaceful = maxPeaceful;
         return this;
      }

      public SpawnerConditions.Builder maxNeutral(int maxNeutral) {
         this.maxneutral = maxNeutral;
         return this;
      }

      public SpawnerConditions.Builder validSpawn(boolean validSpawn) {
         this.validSpawn = validSpawn;
         return this;
      }

      public SpawnerConditions.Builder sturdy(boolean sturdy) {
         this.sturdy = sturdy;
         return this;
      }

      public SpawnerConditions.Builder positiveCheck(PositionCheck positiveCheck) {
         this.positiveCheck = positiveCheck;
         return this;
      }

      public SpawnerConditions.Builder negativeCheck(PositionCheck negativeCheck) {
         this.negativeCheck = negativeCheck;
         return this;
      }

      public SpawnerConditions build() {
         return new SpawnerConditions(this);
      }
   }

   enum Cmd {
      DIMENSION,
      MINDIST,
      MAXDIST,
      MINVERTICALDIST,
      MAXVERTICALDIST,
      MINDAYCOUNT,
      MAXDAYCOUNT,
      MINHEIGHT,
      MAXHEIGHT,
      INWATER,
      INLAVA,
      INLIQUID,
      INAIR,
      NORESTRICTIONS,
      MAXTHIS,
      MAXLOCAL,
      MAXTOTAL,
      MAXHOSTILE,
      MAXPEACEFUL,
      MAXNEUTRAL,
      VALIDSPAWN,
      STURDY,
      AND,
      NOT;
   }
}
