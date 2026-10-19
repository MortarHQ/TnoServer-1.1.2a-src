package it.hurts.sskirillss.relics.init;

import com.mojang.serialization.Codec;
import it.hurts.sskirillss.relics.components.DataComponent;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.data.WorldPosition;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = Bus.MOD)
public class DataComponentRegistry {
   public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "relics");
   @Deprecated(since = "1.21", forRemoval = true)
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<DataComponent>> DATA = DATA_COMPONENTS.register(
      "data", () -> DataComponentType.builder().persistent(DataComponent.CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE = DATA_COMPONENTS.register(
      "charge", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TOGGLED = DATA_COMPONENTS.register(
      "toggled", () -> DataComponentType.builder().persistent(Codec.BOOL).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TIME = DATA_COMPONENTS.register(
      "time", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COOLDOWN = DATA_COMPONENTS.register(
      "cooldown", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COUNT = DATA_COMPONENTS.register(
      "count", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PROGRESS = DATA_COMPONENTS.register(
      "progress", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> TARGET = DATA_COMPONENTS.register(
      "target", () -> DataComponentType.builder().persistent(Codec.STRING).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> SPEED = DATA_COMPONENTS.register(
      "speed", () -> DataComponentType.builder().persistent(Codec.DOUBLE).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> RADIUS = DATA_COMPONENTS.register(
      "radius", () -> DataComponentType.builder().persistent(Codec.DOUBLE).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> HEIGHT = DATA_COMPONENTS.register(
      "height", () -> DataComponentType.builder().persistent(Codec.DOUBLE).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<WorldPosition>> WORLD_POSITION = DATA_COMPONENTS.register(
      "world_position", () -> DataComponentType.builder().persistent(WorldPosition.CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> PORTAL = DATA_COMPONENTS.register(
      "portal", () -> DataComponentType.builder().persistent(Codec.STRING).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockState>> BLOCK_STATE = DATA_COMPONENTS.register(
      "block_state", () -> DataComponentType.builder().persistent(BlockState.CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MODE = DATA_COMPONENTS.register(
      "mode", () -> DataComponentType.builder().persistent(Codec.INT).build()
   );

   public static void register(IEventBus bus) {
      DATA_COMPONENTS.register(bus);
   }

   @SubscribeEvent
   public static void modifyComponents(ModifyDefaultComponentsEvent event) {
      event.modifyMatching(item -> item instanceof IRelicItem, builder -> builder.set((DataComponentType)DATA.get(), DataComponent.EMPTY));
   }
}
