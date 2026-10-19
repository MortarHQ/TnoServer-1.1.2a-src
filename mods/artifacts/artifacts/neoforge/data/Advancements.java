package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.neoforge.data.tags.ItemTags;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class Advancements extends AdvancementProvider {
   public static final Map<String, String> TRANSLATIONS = new HashMap<>();

   public Advancements(PackOutput output, CompletableFuture<Provider> registries, ExistingFileHelper existingFileHelper) {
      super(output, registries, existingFileHelper, List.of(Advancements::generate));
   }

   private static void generate(Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
      ResourceLocation amateurArcheologist = Artifacts.id("amateur_archaeologist");
      AdvancementHolder parent = advancement(amateurArcheologist, (ItemLike)ModItems.FLAME_PENDANT.value(), "Amateur Archaeologist", "Find an Artifact")
         .parent(ResourceLocation.parse("adventure/root"))
         .addCriterion("find_artifact", TriggerInstance.hasItems(new ItemPredicate[]{Builder.item().of(ItemTags.ARTIFACTS).build()}))
         .save(saver, amateurArcheologist, existingFileHelper);
      ResourceLocation chestSlayer = Artifacts.id("chest_slayer");
      advancement(chestSlayer, (ItemLike)ModItems.MIMIC_SPAWN_EGG.value(), "Chest Slayer", "Kill a Mimic")
         .parent(parent)
         .addCriterion(
            "kill_mimic",
            net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance.playerKilledEntity(
               net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(ModEntityTypes.MIMIC.value())
            )
         )
         .save(saver, chestSlayer, existingFileHelper);
      ResourceLocation adventurousEater = Artifacts.id("adventurous_eater");
      advancement(adventurousEater, (ItemLike)ModItems.ONION_RING.value(), "Adventurous Eater", "Eat an Artifact", true)
         .parent(parent)
         .addCriterion(
            "eat_artifact",
            net.minecraft.advancements.critereon.ConsumeItemTrigger.TriggerInstance.usedItem(
               Builder.item().of(new ItemLike[]{(ItemLike)ModItems.ONION_RING.value()})
            )
         )
         .save(saver, adventurousEater, existingFileHelper);
   }

   private static net.minecraft.advancements.Advancement.Builder advancement(ResourceLocation id, ItemLike icon, String title, String description) {
      return advancement(id, icon, title, description, false);
   }

   private static net.minecraft.advancements.Advancement.Builder advancement(
      ResourceLocation id, ItemLike icon, String title, String description, boolean hidden
   ) {
      TRANSLATIONS.put("%s.advancements.%s.title".formatted(id.getNamespace(), id.getPath()), title);
      TRANSLATIONS.put("%s.advancements.%s.description".formatted(id.getNamespace(), id.getPath()), description);
      return net.minecraft.advancements.Advancement.Builder.advancement().display(display(id.getPath(), icon, hidden));
   }

   private static DisplayInfo display(String title, ItemLike icon, boolean hidden) {
      return new DisplayInfo(
         new ItemStack(icon),
         Component.translatable("%s.advancements.%s.title".formatted("artifacts", title)),
         Component.translatable("%s.advancements.%s.description".formatted("artifacts", title)),
         Optional.empty(),
         AdvancementType.TASK,
         true,
         true,
         hidden
      );
   }
}
