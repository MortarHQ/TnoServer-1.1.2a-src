package nadiendev.constructionwand.data;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class AdvancementGenerator extends AdvancementProvider {
   public AdvancementGenerator(PackOutput output, CompletableFuture<Provider> registries, ExistingFileHelper existingFileHelper) {
      super(output, registries, existingFileHelper, List.of(new AdvancementGenerator.WandAdvancementGenerator()));
   }

   public static class WandAdvancementGenerator implements net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator {
      public void generate(Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
         AdvancementHolder root = Builder.advancement()
            .display(
               rootDisplay(
                  (ItemLike)ModItems.WAND_STONE.get(), advancementPrefix("root.title"), advancementPrefix("root.desc"), mcLoc("textures/block/oak_planks.png")
               )
            )
            .addCriterion(
               "wand",
               TriggerInstance.hasItems(
                  new ItemPredicate[]{
                     net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{(ItemLike)ModItems.WAND_STONE.get()}).build()
                  }
               )
            )
            .save(consumer, rootID("root"));
         onHasItem(consumer, ModItems.WAND_STONE, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.WAND_IRON, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.WAND_DIAMOND, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.WAND_NETHERITE, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.WAND_INFINITY, AdvancementType.GOAL, root);
         onHasItem(consumer, ModItems.CORE_ANGEL, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.CORE_DESTRUCTION, AdvancementType.TASK, root);
         onHasItem(consumer, ModItems.VOID_SACK, AdvancementType.TASK, root);
      }

      private static void onHasItem(Consumer<AdvancementHolder> consumer, DeferredItem<Item> item, AdvancementType type, AdvancementHolder parent) {
         String path = item.getId().getPath();
         Builder.advancement()
            .display(simpleDisplay((ItemLike)item.get(), path, type))
            .parent(parent)
            .addCriterion(path, TriggerInstance.hasItems(new ItemLike[]{(ItemLike)item.get()}))
            .save(consumer, rootID(path));
      }

      private static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
         return new DisplayInfo(
            new ItemStack(icon),
            Component.translatable(titleKey),
            Component.translatable(descKey),
            Optional.of(background),
            AdvancementType.TASK,
            false,
            false,
            false
         );
      }

      private static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType type) {
         return new DisplayInfo(
            new ItemStack(icon),
            Component.translatable(advancementPrefix(name + ".title")),
            Component.translatable(advancementPrefix(name + ".desc")),
            Optional.empty(),
            type,
            true,
            true,
            false
         );
      }

      private static String advancementPrefix(String name) {
         return "advancement.constructionwand." + name;
      }

      private static String rootID(String name) {
         return modLoc(name).toString();
      }

      private static ResourceLocation modLoc(String path) {
         return ConstructionWand.loc(path);
      }

      private static ResourceLocation mcLoc(String path) {
         return ResourceLocation.withDefaultNamespace(path);
      }
   }
}
