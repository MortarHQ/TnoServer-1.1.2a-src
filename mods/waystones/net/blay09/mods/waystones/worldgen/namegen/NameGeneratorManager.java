package net.blay09.mods.waystones.worldgen.namegen;

import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.event.GenerateWaystoneNameEvent;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.Nullable;

public class NameGeneratorManager extends SavedData {
   private static final String DATA_NAME = "waystones_NameGenerator";
   private static final String USED_NAMES = "UsedNames";
   private static final NameGeneratorManager clientStorageCopy = new NameGeneratorManager();
   private final Set<String> usedNames = Sets.newHashSet();

   private NameGenerator getNameGenerator(NameGenerationMode nameGenerationMode) {
      TemplateNameGenerator randomGenerator = new TemplateNameGenerator(WaystonesConfig.getActive().worldGen.nameGenerationTemplate)
         .with("MrPork", new MrPorkNameGenerator())
         .with("Biome", new BiomeNameGenerator());
      switch (nameGenerationMode) {
         case MIXED:
            return new MixedNameGenerator(randomGenerator, new CustomNameGenerator(false, this.usedNames));
         case RANDOM_ONLY:
            return randomGenerator;
         case PRESET_ONLY:
            return new CustomNameGenerator(true, this.usedNames);
         case PRESET_FIRST:
         default:
            return new SequencedNameGenerator(new CustomNameGenerator(false, this.usedNames), randomGenerator);
      }
   }

   public synchronized Component getName(LevelAccessor level, Waystone waystone, RandomSource rand, NameGenerationMode nameGenerationMode) {
      NameGenerator nameGenerator = this.getNameGenerator(nameGenerationMode);
      Component originalName = nameGenerator.generateName(level, waystone, rand).orElse(Component.empty());
      Component name = this.resolveDuplicate(originalName);
      GenerateWaystoneNameEvent event = new GenerateWaystoneNameEvent(waystone, name);
      Balm.getEvents().fireEvent(event);
      name = event.getName();
      this.usedNames.add(name.getString());
      this.setDirty();
      return name;
   }

   private Component resolveDuplicate(Component name) {
      Component tryName = name;

      for (int i = 1; this.usedNames.contains(tryName.getString()); i++) {
         tryName = name.copy().append(" " + RomanNumber.toRoman(i));
      }

      return tryName;
   }

   public static NameGeneratorManager load(CompoundTag compound, Provider provider) {
      NameGeneratorManager nameGenerator = new NameGeneratorManager();

      for (Tag tag : compound.getList("UsedNames", 8)) {
         nameGenerator.usedNames.add(tag.getAsString());
      }

      return nameGenerator;
   }

   public CompoundTag save(CompoundTag compound, Provider provider) {
      ListTag tagList = new ListTag();

      for (String entry : this.usedNames) {
         tagList.add(StringTag.valueOf(entry));
      }

      compound.put("UsedNames", tagList);
      return compound;
   }

   public static NameGeneratorManager get(@Nullable MinecraftServer server) {
      if (server != null) {
         ServerLevel overworld = server.getLevel(Level.OVERWORLD);
         return (NameGeneratorManager)Objects.requireNonNull(overworld)
            .getDataStorage()
            .computeIfAbsent(new Factory(NameGeneratorManager::new, NameGeneratorManager::load, DataFixTypes.SAVED_DATA_MAP_DATA), "waystones_NameGenerator");
      } else {
         return clientStorageCopy;
      }
   }
}
