package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.data.enchantments.CEEnchantments;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = "curio_enchantment")
public class Main {
   public static Map<ResourceKey<?>, List<ICondition>> conditions = new HashMap<>();

   @SubscribeEvent
   public static void GatherDataEvent(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = (DatapackBuiltinEntriesProvider)generator.addProvider(
         event.includeServer(),
         new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), new RegistrySetBuilder().add(Registries.ENCHANTMENT, context -> {
            new CEEnchantments().bootstrap(context, conditions);
            new IronsSpellBooksEnchantments().bootstrap(context, conditions);
         }), conditions, Set.of("curio_enchantment"))
      );
      BlockTagsProvider blockTagsProvider = (BlockTagsProvider)generator.addProvider(
         event.includeServer(),
         new BlockTagsProvider(packOutput, datapackBuiltinEntriesProvider.getRegistryProvider(), "curio_enchantment", event.getExistingFileHelper()) {
            protected void addTags(@NotNull Provider provider) {
            }
         }
      );
      generator.addProvider(
         event.includeServer(),
         new CEItemTagsProvider(
            packOutput, datapackBuiltinEntriesProvider.getRegistryProvider(), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()
         )
      );
      generator.addProvider(
         event.includeServer(), new CEEnchantmentTagsProvider(packOutput, datapackBuiltinEntriesProvider.getRegistryProvider(), event.getExistingFileHelper())
      );
      generator.addProvider(event.includeServer(), new LootTables(packOutput, Set.of(), datapackBuiltinEntriesProvider.getRegistryProvider()));
      generator.addProvider(event.includeServer(), new LootModifiers(packOutput, event.getLookupProvider(), "curio_enchantment"));
   }
}
