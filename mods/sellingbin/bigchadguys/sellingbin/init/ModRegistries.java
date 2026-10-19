package bigchadguys.sellingbin.init;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModRegistries {
   public static DeferredRegister<Item> ITEMS = DeferredRegister.create("sellingbin", Registries.ITEM);
   public static DeferredRegister<Block> BLOCKS = DeferredRegister.create("sellingbin", Registries.BLOCK);
   public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create("sellingbin", Registries.BLOCK_ENTITY_TYPE);
   public static DeferredRegister<MenuType<?>> SCREEN_HANDLERS = DeferredRegister.create("sellingbin", Registries.MENU);
   public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create("sellingbin", Registries.DATA_COMPONENT_TYPE);

   public static void register() {
      ModItems.register();
      ModBlocks.register();
      ModBlocks.Entities.register();
      ModScreenHandlers.register();
      ModNetwork.register();
      ModDataComponents.register();
      BLOCKS.register();
      ITEMS.register();
      BLOCK_ENTITY_TYPES.register();
      SCREEN_HANDLERS.register();
      DATA_COMPONENTS.register();
      if (Platform.getEnvironment() == Env.CLIENT) {
         ModScreens.register();
      }

      ModWorldData.register();
      ModConfigs.register(true);
   }

   public static <T, V extends T> RegistrySupplier<V> register(DeferredRegister<T> registry, ResourceLocation id, Supplier<V> value) {
      return registry.register(id, value);
   }

   public static <T, V extends T> RegistrySupplier<V> register(DeferredRegister<T> registry, String name, Supplier<V> value) {
      return registry.register(name, value);
   }
}
