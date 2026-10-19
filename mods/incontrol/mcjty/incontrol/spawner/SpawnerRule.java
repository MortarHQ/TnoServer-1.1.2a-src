package mcjty.incontrol.spawner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import mcjty.incontrol.ErrorHandler;
import mcjty.incontrol.InControl;
import mcjty.incontrol.events.SpawnEventAction;
import mcjty.incontrol.mob.DefaultMob;
import mcjty.incontrol.rules.support.RuleKeys;
import mcjty.incontrol.tools.rules.TestingTools;
import net.minecraft.world.entity.MobCategory;

public class SpawnerRule {
   private final List<DefaultMob> mobs = new ArrayList<>();
   private final List<Float> weights = new ArrayList<>();
   private final List<String> scoreboardTags = new ArrayList<>();
   private final MobCategory mobsFromBiome;
   private final float maxWeight;
   private final float persecond;
   private final int attempts;
   private final int minSpawn;
   private final int maxSpawn;
   private final int groupDistance;
   private final Set<String> phases;
   private final Map<String, Predicate<Integer>> numbers;
   private final SpawnerConditions conditions;
   private static final Map<String, SpawnerRule.Cmd> COMMANDS = new HashMap<>();

   private SpawnerRule(SpawnerRule.Builder builder) {
      this.mobs.addAll(builder.mobs);
      this.weights.addAll(builder.weights);
      this.scoreboardTags.addAll(builder.scoreboardTags);
      this.mobsFromBiome = builder.mobsFromBiome;
      this.phases = builder.phases;
      this.numbers = builder.numbers;
      this.persecond = builder.persecond;
      this.attempts = builder.attempts;
      this.conditions = builder.conditions;
      this.minSpawn = builder.minSpawn;
      this.maxSpawn = builder.maxSpawn;
      this.groupDistance = builder.groupDistance;
      float w = 0.0F;

      for (Float weight : this.weights) {
         w += weight;
      }

      if (w <= 0.0F) {
         w = this.mobs.size();
      }

      this.maxWeight = w;
   }

   public List<DefaultMob> getMobs() {
      return this.mobs;
   }

   public List<String> getScoreboardTags() {
      return this.scoreboardTags;
   }

   public List<Float> getWeights() {
      return this.weights;
   }

   public MobCategory getMobsFromBiome() {
      return this.mobsFromBiome;
   }

   public float getMaxWeight() {
      return this.maxWeight;
   }

   public Set<String> getPhases() {
      return this.phases;
   }

   public Map<String, Predicate<Integer>> getNumbers() {
      return this.numbers;
   }

   public float getPersecond() {
      return this.persecond;
   }

   public int getAttempts() {
      return this.attempts;
   }

   public int getMinSpawn() {
      return this.minSpawn;
   }

   public int getMaxSpawn() {
      return this.maxSpawn;
   }

   public int getGroupDistance() {
      return this.groupDistance;
   }

   public SpawnerConditions getConditions() {
      return this.conditions;
   }

   public static SpawnerRule.Builder create() {
      return new SpawnerRule.Builder();
   }

   public static void parse(JsonObject object, SpawnerRule.Builder builder) {
      for (String attr : object.keySet()) {
         String normalized = Normalizer.normalize(attr, Form.NFD).trim();
         SpawnerRule.Cmd cmd = COMMANDS.get(normalized);
         if (cmd == null) {
            StringBuilder codes = new StringBuilder();

            for (int i = 0; i < attr.length(); i++) {
               codes.append(String.format(" U+%04X", Integer.valueOf(attr.charAt(i))));
            }

            ErrorHandler.error("Invalid command '" + attr + "' for spawner rule! Detected characters:" + codes + " (normalized='" + normalized + "')");
            return;
         }

         switch (cmd) {
            case MOB:
               JsonElement mob = object.get("mob");
               if (mob.isJsonArray()) {
                  for (JsonElement element : mob.getAsJsonArray()) {
                     addMob(builder, element);
                  }
                  break;
               }

               addMob(builder, mob);
               break;
            case WEIGHTS:
               JsonElement weights = object.get("weights");
               if (weights.isJsonArray()) {
                  for (JsonElement element : weights.getAsJsonArray()) {
                     builder.weights(element.getAsFloat());
                  }
                  break;
               }

               builder.weights(weights.getAsFloat());
               break;
            case MOBSFROMBIOME:
               if (!builder.mobs.isEmpty()) {
                  InControl.setup.getLogger().error("'mobsfrombiome' cannot be combined with manual mobs!");
                  throw new RuntimeException("'mobsfrombiome' cannot be combined with manual mobs!");
               }

               String name = object.get("mobsfrombiome").getAsString().toUpperCase();
               MobCategory classification = MobCategory.valueOf(name);
               if (classification == null) {
                  InControl.setup.getLogger().error("Unknown classification " + name + "!");
                  throw new RuntimeException("Unknown classification " + name + "!");
               }

               builder.mobsFromBiome(classification);
               break;
            case PHASE:
               JsonElement phaseElement = object.get(RuleKeys.PHASE.name());
               if (phaseElement.isJsonArray()) {
                  for (JsonElement element : phaseElement.getAsJsonArray()) {
                     builder.phases(element.getAsString());
                  }
                  break;
               }

               builder.phases(phaseElement.getAsString());
               break;
            case NUMBER:
               JsonElement number = object.get(RuleKeys.NUMBER.name());
               if (number.isJsonArray()) {
                  for (JsonElement element : number.getAsJsonArray()) {
                     TestingTools.NumberResult result = TestingTools.parseNumberCheck(element);
                     if (result == null) {
                        return;
                     }

                     builder.number(result.number(), result.test());
                  }
                  break;
               }

               TestingTools.NumberResult result = TestingTools.parseNumberCheck(number);
               if (result == null) {
                  return;
               }

               builder.number(result.number(), result.test());
               break;
            case PERSECOND:
               builder.perSecond(object.getAsJsonPrimitive("persecond").getAsFloat());
               break;
            case ATTEMPTS:
               builder.attempts(object.getAsJsonPrimitive("attempts").getAsInt());
               break;
            case ADDSCOREBOARDTAGS:
               JsonElement tags = object.get("addscoreboardtags");
               if (tags.isJsonArray()) {
                  for (JsonElement element : tags.getAsJsonArray()) {
                     builder.addScoreboardTags(element.getAsString());
                  }
                  break;
               }

               builder.addScoreboardTags(tags.getAsString());
               break;
            case AMOUNT:
               JsonObject amount = object.getAsJsonObject("amount");
               if (amount.has("minimum")) {
                  builder.minSpawn(amount.getAsJsonPrimitive("minimum").getAsInt());
               }

               if (amount.has("maximum")) {
                  builder.maxSpawn(amount.getAsJsonPrimitive("maximum").getAsInt());
               }

               if (amount.has("groupdistance")) {
                  builder.groupDistance(amount.getAsJsonPrimitive("groupdistance").getAsInt());
               }
               break;
            case CONDITIONS:
               JsonObject conditions = object.getAsJsonObject("conditions");
               SpawnerConditions.Builder conditionsBuilder = SpawnerConditions.create();
               SpawnerConditions.parse(conditions, conditionsBuilder);
               SpawnerConditions cnd = conditionsBuilder.build();
               cnd.validate();
               builder.conditions(cnd);
         }
      }
   }

   private static void addMob(SpawnerRule.Builder builder, JsonElement element) {
      DefaultMob defaultMob = SpawnEventAction.parseMob(element);
      if (defaultMob == null) {
         InControl.setup.getLogger().error("Error parsing mob in spawner rule!");
         throw new RuntimeException("Error parsing mob in spawner rule!");
      }

      builder.mobs(defaultMob);
   }

   static {
      for (SpawnerRule.Cmd cmd : SpawnerRule.Cmd.values()) {
         COMMANDS.put(cmd.name().toLowerCase(), cmd);
      }
   }

   public static class Builder {
      private final List<DefaultMob> mobs = new ArrayList<>();
      private final List<Float> weights = new ArrayList<>();
      private final List<String> scoreboardTags = new ArrayList<>();
      private MobCategory mobsFromBiome = null;
      private final Set<String> phases = new HashSet<>();
      private final Map<String, Predicate<Integer>> numbers = new HashMap<>();
      private float persecond = 1.0F;
      private int attempts = 1;
      private int minSpawn = 1;
      private int maxSpawn = 1;
      private int groupDistance = -1;
      private SpawnerConditions conditions = SpawnerConditions.DEFAULT;

      public SpawnerRule.Builder mobs(DefaultMob... mobs) {
         Collections.addAll(this.mobs, mobs);
         return this;
      }

      public SpawnerRule.Builder addScoreboardTags(String... tags) {
         Collections.addAll(this.scoreboardTags, tags);
         return this;
      }

      public SpawnerRule.Builder weights(Float... weights) {
         Collections.addAll(this.weights, weights);
         return this;
      }

      public SpawnerRule.Builder mobsFromBiome(MobCategory mobsFromBiome) {
         this.mobsFromBiome = mobsFromBiome;
         return this;
      }

      public SpawnerRule.Builder phases(String... phases) {
         Collections.addAll(this.phases, phases);
         return this;
      }

      public SpawnerRule.Builder number(String name, Predicate<Integer> predicate) {
         this.numbers.put(name, predicate);
         return this;
      }

      public SpawnerRule.Builder perSecond(float persecond) {
         this.persecond = persecond;
         return this;
      }

      public SpawnerRule.Builder attempts(int attempts) {
         this.attempts = attempts;
         return this;
      }

      public SpawnerRule.Builder minSpawn(int minSpawn) {
         this.minSpawn = minSpawn;
         return this;
      }

      public SpawnerRule.Builder maxSpawn(int maxSpawn) {
         this.maxSpawn = maxSpawn;
         return this;
      }

      public SpawnerRule.Builder groupDistance(int groupDistance) {
         this.groupDistance = groupDistance;
         return this;
      }

      public SpawnerRule.Builder conditions(SpawnerConditions conditions) {
         this.conditions = conditions;
         return this;
      }

      public SpawnerRule build() {
         return new SpawnerRule(this);
      }
   }

   enum Cmd {
      MOB,
      WEIGHTS,
      MOBSFROMBIOME,
      PHASE,
      NUMBER,
      PERSECOND,
      ATTEMPTS,
      ADDSCOREBOARDTAGS,
      AMOUNT,
      CONDITIONS;
   }
}
