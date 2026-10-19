package io.github.Memoires.mysticism.neoforge;

import io.github.Memoires.mysticism.Mysticism;
import io.github.Memoires.mysticism.bridge.neoforge.MysticismAttributeRegisterImpl;
import io.github.Memoires.mysticism.neoforge.data.MysticismBlockStateProvider;
import io.github.Memoires.mysticism.neoforge.data.MysticismItemModelProvider;
import io.github.Memoires.mysticism.neoforge.data.MysticismLanguageProvider;
import io.github.Memoires.mysticism.neoforge.data.MysticismRecipeProvider;
import io.github.Memoires.mysticism.neoforge.data.MysticismRegistryProvider;
import io.github.Memoires.mysticism.neoforge.data.MysticismSoundProvider;
import io.github.Memoires.mysticism.neoforge.data.loot.MysticismLootProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismBiomeTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismBlockTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismEnchantmentTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismEntityTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismItemTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismMobEffectTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismRaceTagProvider;
import io.github.Memoires.mysticism.neoforge.data.tag.MysticismSkillTagProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod("mysticism")
public final class MysticismNeoForge {
   public MysticismNeoForge(IEventBus bus) {
      Mysticism.init();
      MysticismAttributeRegisterImpl.ATTRIBUTES.register(bus);
      bus.addListener(this::gatherData);
   }

   public void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      ExistingFileHelper helper = event.getExistingFileHelper();
      DatapackBuiltinEntriesProvider registryProvider = new MysticismRegistryProvider(output, lookupProvider);
      CompletableFuture<Provider> lookup = registryProvider.getRegistryProvider();
      generator.addProvider(event.includeClient(), new MysticismSoundProvider(output, helper));
      generator.addProvider(event.includeServer(), new MysticismRaceTagProvider(output, lookup));
      generator.addProvider(event.includeServer(), new MysticismSkillTagProvider(output, lookup));
      generator.addProvider(event.includeServer(), new MysticismBiomeTagProvider(output, lookup, helper));
      generator.addProvider(event.includeServer(), new MysticismMobEffectTagProvider(output, lookupProvider, helper));
      generator.addProvider(event.includeServer(), new MysticismEntityTagProvider(output, lookupProvider, helper));
      MysticismBlockTagProvider blockTags = new MysticismBlockTagProvider(output, lookup, helper);
      generator.addProvider(event.includeServer(), blockTags);
      generator.addProvider(event.includeServer(), new MysticismItemTagProvider(output, lookupProvider, blockTags.contentsGetter(), helper));
      generator.addProvider(event.includeServer(), new MysticismRecipeProvider(output, lookup));
      generator.addProvider(event.includeServer(), registryProvider);
      generator.addProvider(event.includeClient(), new MysticismLanguageProvider(output));
      generator.addProvider(event.includeClient(), new MysticismItemModelProvider(output, helper));
      generator.addProvider(event.includeClient(), new MysticismBlockStateProvider(output, helper));
      generator.addProvider(event.includeServer(), new MysticismLootProvider(output, lookup));
      generator.addProvider(event.includeServer(), new MysticismEnchantmentTagProvider(output, lookup, helper));
   }
}
