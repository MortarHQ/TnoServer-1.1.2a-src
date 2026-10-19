package nadiendev.constructionwand.component;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import nadiendev.constructionwand.basics.option.WandOptions;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
   public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "constructionwand");
   public static final Supplier<DataComponentType<WandOptions.LOCK>> LOCK = DATA_COMPONENT_TYPES.register(
      "lock", () -> DataComponentType.builder().persistent(WandOptions.LOCK.CODEC).networkSynchronized(WandOptions.LOCK.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<WandOptions.DIRECTION>> DIRECTION = DATA_COMPONENT_TYPES.register(
      "direction", () -> DataComponentType.builder().persistent(WandOptions.DIRECTION.CODEC).networkSynchronized(WandOptions.DIRECTION.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> REPLACE = DATA_COMPONENT_TYPES.register(
      "replace", () -> DataComponentType.builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<WandOptions.MATCH>> MATCH = DATA_COMPONENT_TYPES.register(
      "match", () -> DataComponentType.builder().persistent(WandOptions.MATCH.CODEC).networkSynchronized(WandOptions.MATCH.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> RANDOM = DATA_COMPONENT_TYPES.register(
      "random", () -> DataComponentType.builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<CompoundTag>> CORES = DATA_COMPONENT_TYPES.register(
      "cores", () -> DataComponentType.builder().persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build()
   );
   public static final Supplier<DataComponentType<CompoundTag>> VOID_SACK_DATA = DATA_COMPONENT_TYPES.register(
      "void_sack_data", () -> DataComponentType.builder().persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build()
   );
}
