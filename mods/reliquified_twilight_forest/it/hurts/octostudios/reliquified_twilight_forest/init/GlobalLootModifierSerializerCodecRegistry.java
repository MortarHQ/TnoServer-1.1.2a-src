package it.hurts.octostudios.reliquified_twilight_forest.init;

import com.mojang.serialization.MapCodec;
import it.hurts.octostudios.reliquified_twilight_forest.loot.modifier.AddItemsWithChanceModifier;
import it.hurts.octostudios.reliquified_twilight_forest.loot.modifier.ModifyItemsModifier;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class GlobalLootModifierSerializerCodecRegistry {
   public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "reliquified_twilight_forest"
   );
   public static final Supplier<MapCodec<ModifyItemsModifier>> MODIFY_ITEMS = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(
      "modify_items", () -> ModifyItemsModifier.CODEC
   );
   public static final Supplier<MapCodec<AddItemsWithChanceModifier>> ADD_ITEMS = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(
      "add_items", () -> AddItemsWithChanceModifier.CODEC
   );

   public static void register(IEventBus bus) {
      GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
   }
}
