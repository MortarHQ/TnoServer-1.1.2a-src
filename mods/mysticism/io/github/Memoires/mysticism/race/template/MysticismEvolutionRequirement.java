package io.github.Memoires.mysticism.race.template;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class MysticismEvolutionRequirement {
   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      return 0.0F;
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.empty();
   }

   public static class AlignmentRequirement extends EvolutionRequirement {
      private final MysticismEvolutionRequirement.AlignmentRequirement.Expected expected;

      public AlignmentRequirement(MysticismEvolutionRequirement.AlignmentRequirement.Expected expected) {
         this.expected = expected;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         Alignment current = TensuraStorages.getExistenceFrom(entity).getAlignment();
         return current == map(this.expected) ? 1.0F : 0.0F;
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.alignment_requirement", new Object[]{map(this.expected).getName()});
      }

      public MysticismEvolutionRequirement.AlignmentRequirement.Expected getExpected() {
         return this.expected;
      }

      private static Alignment map(MysticismEvolutionRequirement.AlignmentRequirement.Expected e) {
         return switch (e) {
            case CHAOS -> Alignment.CHAOS;
            case HOLY -> Alignment.HOLY;
            case MAJIN -> Alignment.MAJIN;
            case DEFAULT -> Alignment.DEFAULT;
         };
      }

      public enum Expected {
         CHAOS,
         HOLY,
         MAJIN,
         DEFAULT;
      }
   }

   public static class BiomeRequirementAnyOf extends EvolutionRequirement {
      private final List<ResourceLocation> biomeIds;
      @Nullable
      private final TagKey<Biome> biomeTag;

      public BiomeRequirementAnyOf(Collection<ResourceLocation> biomeIds) {
         this.biomeIds = List.copyOf(biomeIds);
         this.biomeTag = null;
      }

      public BiomeRequirementAnyOf(TagKey<Biome> biomeTag) {
         this.biomeIds = List.of();
         this.biomeTag = biomeTag;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         Holder<Biome> here = entity.level().getBiome(entity.blockPosition());
         if (this.biomeTag != null) {
            return here.is(this.biomeTag) ? 1.0F : 0.0F;
         }

         for (ResourceLocation id : this.biomeIds) {
            ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, id);
            if (here.is(key)) {
               return 1.0F;
            }
         }

         return 0.0F;
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         if (this.biomeTag != null) {
            Optional<RegistryLookup<Biome>> lookupOpt = entity.level().registryAccess().lookup(Registries.BIOME);
            if (lookupOpt.isPresent()) {
               RegistryLookup<Biome> lookup = lookupOpt.get();
               Optional<Named<Biome>> setOpt = lookup.get(this.biomeTag);
               if (setOpt.isPresent()) {
                  List<MutableComponent> names = setOpt.get().stream().map(holder -> {
                     ResourceLocation id = ((ResourceKey)holder.unwrapKey().orElseThrow()).location();
                     return Component.translatable(Util.makeDescriptionId("biome", id));
                  }).toList();
                  Component list = ComponentUtils.formatList(names, Component.literal(", "));
                  return Component.translatable("mysticism.evolution_menu.biome_requirement_any_of", new Object[]{list});
               }
            }

            return Component.translatable("mysticism.evolution_menu.biome_requirement_any_of", new Object[]{Component.literal("#" + this.biomeTag.location())});
         } else {
            List<Component> names = this.biomeIds.stream().map(MysticismEvolutionRequirement.BiomeRequirementAnyOf::biomeName).toList();
            Component list = ComponentUtils.formatList(names, Component.literal(", "));
            return Component.translatable("mysticism.evolution_menu.biome_requirement_any_of", new Object[]{list});
         }
      }

      private static Component biomeName(ResourceLocation id) {
         return Component.translatable(Util.makeDescriptionId("biome", id));
      }

      public List<ResourceLocation> getBiomeIds() {
         return this.biomeIds;
      }

      @Nullable
      public TagKey<Biome> getBiomeTag() {
         return this.biomeTag;
      }
   }

   public static class BiomeRequirementAnyTags extends EvolutionRequirement {
      private final List<TagKey<Biome>> anyOfTags;

      public BiomeRequirementAnyTags(Collection<TagKey<Biome>> anyOfTags) {
         this.anyOfTags = List.copyOf(anyOfTags);
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         Holder<Biome> here = entity.level().getBiome(entity.blockPosition());

         for (TagKey<Biome> tag : this.anyOfTags) {
            if (here.is(tag)) {
               return 1.0F;
            }
         }

         return 0.0F;
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         Registry<Biome> biomeReg = entity.level().registryAccess().registryOrThrow(Registries.BIOME);
         Set<ResourceLocation> ids = new LinkedHashSet<>();

         for (TagKey<Biome> tag : this.anyOfTags) {
            biomeReg.getTag(tag).ifPresent(holderSet -> {
               for (Holder<Biome> h : holderSet) {
                  ResourceLocation id = biomeReg.getKey((Biome)h.value());
                  if (id != null) {
                     ids.add(id);
                  }
               }
            });
         }

         List<Component> names = new ArrayList<>();

         for (ResourceLocation id : ids) {
            names.add(Component.translatable("biome." + id.getNamespace() + "." + id.getPath()));
         }

         Component listComp;
         if (names.isEmpty()) {
            listComp = Component.literal("—");
         } else if (names.size() == 1) {
            listComp = names.get(0);
         } else {
            MutableComponent joined = Component.empty();

            for (int i = 0; i < names.size(); i++) {
               if (i > 0) {
                  joined.append(Component.literal(", "));
               }

               joined.append(names.get(i));
            }

            listComp = joined;
         }

         return Component.translatable("mysticism.evolution_menu.biome_requirement_any_of", new Object[]{listComp});
      }

      public List<TagKey<Biome>> getAnyOfTags() {
         return this.anyOfTags;
      }
   }

   public static class CompositeRequirement extends EvolutionRequirement {
      private final List<EvolutionRequirement> children;

      public CompositeRequirement(List<EvolutionRequirement> children) {
         this.children = List.copyOf(children);
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         float best = 0.0F;

         for (EvolutionRequirement r : this.children) {
            best = Math.max(best, r.getProgress(instance, entity));
            if (best >= 1.0F) {
               break;
            }
         }

         return best;
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         List<Component> parts = new ArrayList<>();

         for (EvolutionRequirement r : this.children) {
            parts.add(r.getRequirementComponent(instance, entity));
         }

         return Component.translatable("mysticism.evolution_menu.any_of", new Object[]{ComponentUtils.formatList(parts, Component.literal(" OR "))});
      }

      public List<EvolutionRequirement> getChildren() {
         return this.children;
      }
   }

   public static class HeightRequirement extends EvolutionRequirement {
      private final MysticismEvolutionRequirement.HeightRequirement.Mode mode;
      private final int yLevel;

      public HeightRequirement(MysticismEvolutionRequirement.HeightRequirement.Mode mode, int yLevel) {
         this.mode = mode;
         this.yLevel = yLevel;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         int y = entity.blockPosition().getY();

         return switch (this.mode) {
            case EQUAL -> y == this.yLevel ? 1.0F : 0.0F;
            case HIGHER -> y > this.yLevel ? 1.0F : 0.0F;
            case LOWER -> y < this.yLevel ? 1.0F : 0.0F;
         };
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         String modeWord = switch (this.mode) {
            case EQUAL -> "at";
            case HIGHER -> "above";
            case LOWER -> "below";
         };
         return Component.translatable(
            "mysticism.evolution_menu.height_requirement", new Object[]{Component.literal(modeWord), Component.literal(Integer.toString(this.yLevel))}
         );
      }

      public MysticismEvolutionRequirement.HeightRequirement.Mode getMode() {
         return this.mode;
      }

      public int getYLevel() {
         return this.yLevel;
      }

      public enum Mode {
         EQUAL,
         HIGHER,
         LOWER;
      }
   }

   public static class LightRequirement extends EvolutionRequirement {
      private final MysticismEvolutionRequirement.LightRequirement.Comparator comparator;
      private final int targetLevel;

      public LightRequirement(MysticismEvolutionRequirement.LightRequirement.Comparator comparator, int targetLevel) {
         this.comparator = Objects.requireNonNull(comparator);
         this.targetLevel = Mth.clamp(targetLevel, 0, 15);
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         int current = entity.level().getMaxLocalRawBrightness(entity.blockPosition());
         return this.test(current) ? 1.0F : 0.0F;
      }

      private boolean test(int current) {
         return switch (this.comparator) {
            case GREATER -> current > this.targetLevel;
            case GREATER_OR_EQUAL -> current >= this.targetLevel;
            case EQUAL -> current == this.targetLevel;
            case LESS -> current < this.targetLevel;
            case LESS_OR_EQUAL -> current <= this.targetLevel;
         };
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.light_requirement", new Object[]{this.comparator.symbol(), this.targetLevel});
      }

      public enum Comparator {
         GREATER(">"),
         GREATER_OR_EQUAL("≥"),
         EQUAL("="),
         LESS("<"),
         LESS_OR_EQUAL("≤");

         private final String symbol;

         Comparator(String symbol) {
            this.symbol = symbol;
         }

         public String symbol() {
            return this.symbol;
         }
      }
   }

   public static class PossessGolemRequirement extends EvolutionRequirement {
      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(entity).getRace();
         if (optional.isPresent()) {
            optional.get().markDirty();
            CompoundTag tag = optional.get().getTag();
            if (tag != null && tag.getBoolean("BoneGolem")) {
               return 100.0F;
            }
         }

         return 0.0F;
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.possess_golem");
      }
   }

   public static class SlayRequirement extends EvolutionRequirement {
      private final int requirement;
      private final EntityType<?> requirementEntity;

      public SlayRequirement(EntityType<?> entity, int requirement) {
         this.requirementEntity = entity;
         this.requirement = requirement;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         int essence = 0;
         if (entity instanceof Player player) {
            if (player.isLocalPlayer()) {
               essence = ((LocalPlayer)entity).getStats().getValue(Stats.ENTITY_KILLED.get(this.getRequirementEntity()));
            } else {
               essence = ((ServerPlayer)entity).getStats().getValue(Stats.ENTITY_KILLED.get(this.getRequirementEntity()));
            }
         }

         return (float)essence / this.getRequirement();
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.translatable(
            "mysticism.evolution_menu.slay_requirement", new Object[]{this.getRequirement(), this.getRequirementEntity().getDescription()}
         );
      }

      public int getRequirement() {
         return this.requirement;
      }

      public EntityType<?> getRequirementEntity() {
         return this.requirementEntity;
      }
   }
}
