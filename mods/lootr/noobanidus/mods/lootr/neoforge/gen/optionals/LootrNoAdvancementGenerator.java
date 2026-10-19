package noobanidus.mods.lootr.neoforge.gen.optionals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.DetectedVersion;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import noobanidus.mods.lootr.common.api.LootrAPI;

@EventBusSubscriber(modid = "lootr")
public class LootrNoAdvancementGenerator {
   private static final List<DataGenerator> generators = new ArrayList<>();

   public static DataGenerator makeGenerator(Path path, Component description) {
      DataGenerator generator = new DataGenerator(path, DetectedVersion.tryDetectVersion(), true);
      generator.addProvider(
         true,
         new PackMetadataGenerator(generator.getPackOutput())
            .add(
               PackMetadataSection.TYPE, new PackMetadataSection(description, DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA), Optional.empty())
            )
      );
      generators.add(generator);
      return generator;
   }

   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      if (event.getMods().contains("lootr")) {
         PackOutput output = event.getGenerator().getPackOutput();
         CompletableFuture<Provider> provider = event.getLookupProvider();
         ExistingFileHelper helper = event.getExistingFileHelper();
         Path root = output.getOutputFolder().getParent().getParent().getParent().getParent();
         Path datapacks = root.resolve("datapacks");
         DataGenerator generator = makeGenerator(datapacks.resolve("lootr_no_advancements"), Component.literal("Disable Lootr Advancements"));
         generator.addProvider(
            event.includeServer(),
            new AdvancementProvider(generator.getPackOutput(), provider, helper, List.of(new LootrNoAdvancementGenerator.LootrAdvancementGenerator()))
         );
         generator = makeGenerator(datapacks.resolve("lootr_no_suspicious_blocks"), Component.literal("Disable Lootr Suspicious Sand and Gravel"));
         generator.addProvider(event.includeServer(), new LootrNoSuspiciousGenerator.LootrBlockTagProvider(generator.getPackOutput(), provider, helper));

         try {
            for (DataGenerator toRun : generators) {
               toRun.run();
               Files.copy(root.resolve("logo.png"), toRun.getPackOutput().getOutputFolder().resolve("pack.png"));
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public static class LootrAdvancementGenerator implements AdvancementGenerator {
      public void generate(Provider arg, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
         Criterion<TriggerInstance> impossible = CriteriaTriggers.IMPOSSIBLE.createCriterion(new TriggerInstance());
         AdvancementHolder lootrRoot = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("root"), existingFileHelper);
         AdvancementHolder one_barrel = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("1barrel"), existingFileHelper);
         AdvancementHolder one_cart = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("1cart"), existingFileHelper);
         AdvancementHolder one_chest = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("1chest"), existingFileHelper);
         AdvancementHolder one_shulker = Builder.advancement()
            .addCriterion("impossible", impossible)
            .save(consumer, LootrAPI.rl("1shulker"), existingFileHelper);
         AdvancementHolder brush = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("all_gravel"), existingFileHelper);
         AdvancementHolder pot = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("pot_opened"), existingFileHelper);
         AdvancementHolder archaeologist = Builder.advancement()
            .addCriterion("impossible", impossible)
            .save(consumer, LootrAPI.rl("archaeologist"), existingFileHelper);
         AdvancementHolder item_frame = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("1frame"), existingFileHelper);
         AdvancementHolder ten_loot = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("10loot"), existingFileHelper);
         AdvancementHolder twentyfive_loot = Builder.advancement()
            .addCriterion("impossible", impossible)
            .save(consumer, LootrAPI.rl("25loot"), existingFileHelper);
         AdvancementHolder fifty_loot = Builder.advancement().addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("50loot"), existingFileHelper);
         Builder.advancement().parent(fifty_loot).addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("100loot"), existingFileHelper);
         Builder.advancement().parent(one_chest).addCriterion("impossible", impossible).save(consumer, LootrAPI.rl("social"), existingFileHelper);
      }
   }
}
