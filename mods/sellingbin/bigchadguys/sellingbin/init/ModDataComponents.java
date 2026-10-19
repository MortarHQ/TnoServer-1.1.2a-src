package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.block.BinMode;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.resources.ResourceLocation;

public class ModDataComponents extends ModRegistries {
   public static RegistrySupplier<DataComponentType<BinMaterial>> BIN_MATERIAL;
   public static RegistrySupplier<DataComponentType<BinMode>> BIN_MODE;

   public static void register() {
      BIN_MATERIAL = register(SellingBinMod.id("bin_material"), builder -> builder.persistent(BinMaterial.CODEC).networkSynchronized(BinMaterial.PACKET_CODEC));
      BIN_MODE = register(SellingBinMod.id("bin_mode"), builder -> builder.persistent(BinMode.CODEC).networkSynchronized(BinMode.PACKET_CODEC));
   }

   public static <T> RegistrySupplier<DataComponentType<T>> register(ResourceLocation id, UnaryOperator<Builder<T>> item) {
      return register(DATA_COMPONENTS, id, () -> item.apply(DataComponentType.builder()).build());
   }
}
