package com.foxablazeultimate.registry;

import com.foxablazeultimate.ability.skill.azathoth.AzathothChargeData;
import com.foxablazeultimate.ability.skill.velgrynd.VelgryndChargeData;
import com.foxablazeultimate.item.beelzebub.CapturedEntityData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

public final class FoxAblazeUltimateDataComponents {
   public static final DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "foxablazeultimate");
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<CapturedEntityData>> CAPTURED_ENTITY = DATA_COMPONENTS.registerComponentType(
      "captured_entity", builder -> builder.persistent(CapturedEntityData.CODEC).networkSynchronized(CapturedEntityData.STREAM_CODEC)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<VelgryndChargeData>> VELGRYND_CHARGE = DATA_COMPONENTS.registerComponentType(
      "velgrynd_charge", builder -> builder.persistent(VelgryndChargeData.CODEC).networkSynchronized(VelgryndChargeData.STREAM_CODEC)
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<AzathothChargeData>> AZATHOTH_CHARGE = DATA_COMPONENTS.registerComponentType(
      "azathoth_charge", builder -> builder.persistent(AzathothChargeData.CODEC).networkSynchronized(AzathothChargeData.STREAM_CODEC)
   );

   private FoxAblazeUltimateDataComponents() {
   }

   public static void register(IEventBus modBus) {
      DATA_COMPONENTS.register(modBus);
   }
}
