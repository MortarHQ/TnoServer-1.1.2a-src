package com.bobmowzie.mowziesmobs.server.world;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeChecker {
   private Set<BiomeChecker.BiomeCombo> comboList = new HashSet<>();
   private Set<ResourceLocation> whitelist;
   private Set<ResourceLocation> blacklist;

   public BiomeChecker(ConfigHandler.BiomeConfig biomeConfig) {
      for (String biomeComboString : (List)biomeConfig.biomeTags.get()) {
         BiomeChecker.BiomeCombo biomeCombo = new BiomeChecker.BiomeCombo(biomeComboString);
         this.comboList.add(biomeCombo);
      }

      this.whitelist = new HashSet<>();

      for (String biomeString : (List)biomeConfig.biomeWhitelist.get()) {
         this.whitelist.add(ResourceLocation.tryParse(biomeString));
      }

      this.blacklist = new HashSet<>();

      for (String biomeString : (List)biomeConfig.biomeBlacklist.get()) {
         this.blacklist.add(ResourceLocation.tryParse(biomeString));
      }
   }

   public boolean isBiomeInConfig(Holder<Biome> biome) {
      for (ResourceLocation biomeName : this.whitelist) {
         TagKey<Biome> tagKey = TagKey.create(Registries.BIOME, biomeName);
         if (biome.is(tagKey)) {
            return true;
         }

         if (biome.is(biomeName)) {
            return true;
         }
      }

      for (ResourceLocation biomeName : this.blacklist) {
         TagKey<Biome> tagKey = TagKey.create(Registries.BIOME, biomeName);
         if (biome.is(tagKey)) {
            return false;
         }

         if (biome.is(biomeName)) {
            return false;
         }
      }

      for (BiomeChecker.BiomeCombo biomeCombo : this.comboList) {
         if (biomeCombo.acceptsBiome(biome)) {
            return true;
         }
      }

      return false;
   }

   private static class BiomeCombo {
      ResourceLocation[] neededTags;
      boolean[] inverted;

      private BiomeCombo(String biomeComboString) {
         String[] typeStrings = biomeComboString.replace(" ", "").split(",");
         this.neededTags = new ResourceLocation[typeStrings.length];
         this.inverted = new boolean[typeStrings.length];

         for (int i = 0; i < typeStrings.length; i++) {
            if (typeStrings[i].length() != 0) {
               this.inverted[i] = typeStrings[i].charAt(0) == '!';
               String name = typeStrings[i].replace("!", "");
               this.neededTags[i] = ResourceLocation.tryParse(name);
            }
         }
      }

      private boolean acceptsBiome(Holder<Biome> biome) {
         for (int i = 0; i < this.neededTags.length; i++) {
            ResourceLocation neededBiomeName = this.neededTags[i];
            if (neededBiomeName != null) {
               TagKey<Biome> neededBiomeTag = TagKey.create(Registries.BIOME, neededBiomeName);
               boolean failIfMatches = this.inverted[i];
               if (failIfMatches) {
                  if (biome.is(neededBiomeTag) || biome.is(neededBiomeName)) {
                     return false;
                  }
               } else if (!biome.is(neededBiomeTag) && !biome.is(neededBiomeName)) {
                  return false;
               }
            }
         }

         return true;
      }
   }
}
