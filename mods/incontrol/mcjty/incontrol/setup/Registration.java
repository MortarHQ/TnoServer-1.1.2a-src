package mcjty.incontrol.setup;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import mcjty.incontrol.events.InControlLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class Registration {
   public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "incontrol"
   );
   public static final Supplier<MapCodec<? extends IGlobalLootModifier>> INCONTROL_GLM = LOOT_MODIFIER_SERIALIZERS.register(
      "incontrol_glm", () -> InControlLootModifier.CODEC
   );

   public static void register(IEventBus bus) {
      LOOT_MODIFIER_SERIALIZERS.register(bus);
   }
}
