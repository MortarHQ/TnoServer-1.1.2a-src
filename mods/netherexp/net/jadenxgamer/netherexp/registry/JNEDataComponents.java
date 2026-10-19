package net.jadenxgamer.netherexp.registry;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.core.item.components.LocatorCompass;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

public class JNEDataComponents {
   public static final DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "netherexp");
   public static final Supplier<DataComponentType<Integer>> PUMPS = COMPONENTS.registerComponentType(
      "pumps", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
   );
   public static final Supplier<DataComponentType<AntidoteContents>> ANTIDOTE_CONTENTS = COMPONENTS.registerComponentType(
      "antidote_contents", builder -> builder.persistent(AntidoteContents.CODEC).networkSynchronized(ByteBufCodecs.fromCodec(AntidoteContents.CODEC))
   );
   public static final Supplier<DataComponentType<LocatorCompass>> LOCATOR_COMPASS = COMPONENTS.registerComponentType(
      "locator_compass", builder -> builder.persistent(LocatorCompass.CODEC).networkSynchronized(ByteBufCodecs.fromCodec(LocatorCompass.CODEC))
   );

   public static void init(IEventBus eventBus) {
      COMPONENTS.register(eventBus);
   }
}
