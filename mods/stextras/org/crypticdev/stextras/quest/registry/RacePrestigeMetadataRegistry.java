package org.crypticdev.stextras.quest.registry;

import io.github.manasmods.manascore.race.impl.RaceRegistry;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.quest.race.RacePrestigeMetadata;
import org.crypticdev.stextras.quest.race.RacePrestigeText;

public final class RacePrestigeMetadataRegistry {
   public static final RacePrestigeMetadataRegistry INSTANCE = new RacePrestigeMetadataRegistry();
   private final Map<ResourceLocation, RacePrestigeMetadata> byRace = new HashMap<>();

   private RacePrestigeMetadataRegistry() {
   }

   public void clear() {
      this.byRace.clear();
   }

   public void put(ResourceLocation race, RacePrestigeText start, RacePrestigeText complete) {
      this.registerRaceMeta(race, null, start, complete, 0);
   }

   public void registerRaceMeta(ResourceLocation race, RacePrestigeText lore, RacePrestigeText start, RacePrestigeText complete, int soulGradeReward) {
      if (race != null) {
         if (RaceRegistry.RACES.contains(race)) {
            RacePrestigeMetadata current = this.byRace.get(race);
            RacePrestigeText mergedLore = lore != null ? lore : (current != null ? current.lore() : null);
            RacePrestigeText mergedStart = start != null ? start : (current != null ? current.start() : null);
            RacePrestigeText mergedComplete = complete != null ? complete : (current != null ? current.complete() : null);
            int mergedSoulGrade = soulGradeReward > 0 ? soulGradeReward : (current != null ? current.soulGradeReward() : 0);
            this.byRace.put(race, new RacePrestigeMetadata(mergedLore, mergedStart, mergedComplete, mergedSoulGrade));
         }
      }
   }

   public Set<ResourceLocation> getAllRaceIds() {
      return new LinkedHashSet<>(this.byRace.keySet());
   }

   public Optional<RacePrestigeText> lore(ResourceLocation race) {
      return Optional.ofNullable(this.byRace.get(race)).map(RacePrestigeMetadata::lore);
   }

   public Optional<RacePrestigeText> start(ResourceLocation race) {
      return Optional.ofNullable(this.byRace.get(race)).map(RacePrestigeMetadata::start);
   }

   public Optional<RacePrestigeText> complete(ResourceLocation race) {
      return Optional.ofNullable(this.byRace.get(race)).map(RacePrestigeMetadata::complete);
   }

   public int soulGradeReward(ResourceLocation race) {
      RacePrestigeMetadata metadata = this.byRace.get(race);
      return metadata == null ? 0 : metadata.soulGradeReward();
   }
}
