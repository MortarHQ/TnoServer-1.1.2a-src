package dev.ftb.mods.ftbquests.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.item.CustomIconItem;
import dev.ftb.mods.ftbquests.item.LootCrateItem;
import dev.ftb.mods.ftbquests.item.MissingItem;
import dev.ftb.mods.ftbquests.item.QuestBarrierBlockItem;
import dev.ftb.mods.ftbquests.item.QuestBookItem;
import dev.ftb.mods.ftbquests.item.ScreenBlockItem;
import dev.ftb.mods.ftbquests.item.StageBarrierBlockItem;
import dev.ftb.mods.ftbquests.item.TaskScreenConfiguratorItem;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("ftbquests", Registries.ITEM);
   public static final RegistrySupplier<Item> BOOK = ITEMS.register("book", QuestBookItem::new);
   public static final RegistrySupplier<Item> LOOTCRATE = ITEMS.register("lootcrate", LootCrateItem::new);
   public static final RegistrySupplier<Item> TASK_SCREEN_CONFIGURATOR = ITEMS.register("task_screen_configurator", TaskScreenConfiguratorItem::new);
   public static final RegistrySupplier<Item> MISSING_ITEM = ITEMS.register("missing_item", MissingItem::new);
   public static final RegistrySupplier<Item> CUSTOM_ICON = ITEMS.register("custom_icon", CustomIconItem::new);
   public static final RegistrySupplier<Item> BARRIER = blockItemFor("barrier", QuestBarrierBlockItem::new);
   public static final RegistrySupplier<Item> STAGE_BARRIER = blockItemFor("stage_barrier", StageBarrierBlockItem::new);
   public static final RegistrySupplier<Item> DETECTOR = blockItem("detector", ModBlocks.DETECTOR);
   public static final RegistrySupplier<Item> LOOT_CRATE_OPENER = blockItem("loot_crate_opener", ModBlocks.LOOT_CRATE_OPENER);
   public static final RegistrySupplier<Item> TASK_SCREEN_1 = blockItemFor(
      "screen_1", () -> new ScreenBlockItem((Block)ModBlocks.TASK_SCREEN_1.get(), ScreenBlockItem.ScreenSize.ONE_X_ONE)
   );
   public static final RegistrySupplier<Item> TASK_SCREEN_3 = blockItemFor(
      "screen_3", () -> new ScreenBlockItem((Block)ModBlocks.TASK_SCREEN_3.get(), ScreenBlockItem.ScreenSize.THREE_X_THREE)
   );
   public static final RegistrySupplier<Item> TASK_SCREEN_5 = blockItemFor(
      "screen_5", () -> new ScreenBlockItem((Block)ModBlocks.TASK_SCREEN_5.get(), ScreenBlockItem.ScreenSize.FIVE_X_FIVE)
   );
   public static final RegistrySupplier<Item> TASK_SCREEN_7 = blockItemFor(
      "screen_7", () -> new ScreenBlockItem((Block)ModBlocks.TASK_SCREEN_7.get(), ScreenBlockItem.ScreenSize.SEVEN_X_SEVEN)
   );
   public static final List<RegistrySupplier<Item>> BASE_ITEMS = List.of(
      BOOK, BARRIER, STAGE_BARRIER, DETECTOR, LOOT_CRATE_OPENER, TASK_SCREEN_1, TASK_SCREEN_3, TASK_SCREEN_5, TASK_SCREEN_7, TASK_SCREEN_CONFIGURATOR
   );

   private static RegistrySupplier<Item> blockItem(String id, Supplier<Block> b) {
      return ITEMS.register(id, () -> new BlockItem(b.get(), defaultProps()));
   }

   private static RegistrySupplier<Item> blockItemFor(String id, Supplier<BlockItem> bi) {
      return ITEMS.register(id, bi);
   }

   public static void register() {
      ITEMS.register();
      CreativeTabRegistry.appendStack(FTBLibrary.getCreativeModeTab(), BASE_ITEMS.stream().map(item -> () -> new ItemStack((ItemLike)item.get())));
      CreativeTabRegistry.modify(FTBLibrary.getCreativeModeTab(), (flags, output, canUseGameMasterBlocks) -> {
         List<ItemStack> stacks = FTBQuests.PROXY.getKnownLootCrates().stream().map(LootCrate::createStack).filter(stack -> !stack.isEmpty()).toList();
         output.acceptAllAfter((ItemLike)BASE_ITEMS.getLast().get(), stacks);
      });
   }

   public static Properties defaultProps() {
      return new Properties();
   }
}
