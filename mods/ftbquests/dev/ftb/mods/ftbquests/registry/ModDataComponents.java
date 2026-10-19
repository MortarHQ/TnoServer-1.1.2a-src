package dev.ftb.mods.ftbquests.registry;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.ftb.mods.ftbquests.block.entity.BaseBarrierBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ItemContainerContents;

public class ModDataComponents {
   public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create("ftbquests", Registries.DATA_COMPONENT_TYPE);
   public static RegistrySupplier<DataComponentType<ResourceLocation>> CUSTOM_ICON = COMPONENT_TYPES.register(
      "icon", () -> new Builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build()
   );
   public static RegistrySupplier<DataComponentType<ResourceLocation>> ENTITY_FACE_ICON = COMPONENT_TYPES.register(
      "entity_face", () -> new Builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build()
   );
   public static RegistrySupplier<DataComponentType<String>> LOOT_CRATE = COMPONENT_TYPES.register(
      "loot_crate", () -> new Builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build()
   );
   public static RegistrySupplier<DataComponentType<ItemContainerContents>> LOOT_CRATE_ITEMS = COMPONENT_TYPES.register(
      "loot_crate_items", () -> new Builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).build()
   );
   public static RegistrySupplier<DataComponentType<GlobalPos>> SCREEN_POS = COMPONENT_TYPES.register(
      "screen_pos", () -> new Builder().persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC).build()
   );
   public static RegistrySupplier<DataComponentType<String>> MISSING_ITEM_DESC = COMPONENT_TYPES.register(
      "missing_item", () -> new Builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build()
   );
   public static RegistrySupplier<DataComponentType<TaskScreenBlockEntity.TaskScreenSaveData>> TASK_SCREEN_SAVED = COMPONENT_TYPES.register(
      "task_screen_saved",
      () -> new Builder()
         .persistent(TaskScreenBlockEntity.TaskScreenSaveData.CODEC)
         .networkSynchronized(TaskScreenBlockEntity.TaskScreenSaveData.STREAM_CODEC)
         .build()
   );
   public static RegistrySupplier<DataComponentType<BaseBarrierBlockEntity.BarrierSavedData>> BARRIER_SAVED = COMPONENT_TYPES.register(
      "barrier_saved",
      () -> new Builder()
         .persistent(BaseBarrierBlockEntity.BarrierSavedData.CODEC)
         .networkSynchronized(BaseBarrierBlockEntity.BarrierSavedData.STREAM_CODEC)
         .build()
   );

   public static void register() {
      COMPONENT_TYPES.register();
   }
}
