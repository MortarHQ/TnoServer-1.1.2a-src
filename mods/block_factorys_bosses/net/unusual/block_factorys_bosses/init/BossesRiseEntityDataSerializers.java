package net.unusual.block_factorys_bosses.init;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;

public class BossesRiseEntityDataSerializers {
   public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, "block_factorys_bosses");
   public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Direction>>> OPTIONAL_DIRECTION = REGISTRY.register(
      "optional_direction", () -> EntityDataSerializer.forValueType(Direction.STREAM_CODEC.apply(ByteBufCodecs::optional))
   );
   public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<KrakenTentacleEntity.TentacleType>> TENTACLE_TYPE = REGISTRY.register(
      "tentacle_type", () -> EntityDataSerializer.forValueType(KrakenTentacleEntity.TentacleType.STREAM_CODEC)
   );
   public static final StreamCodec<ByteBuf, Vec3> COMPOSITE = StreamCodec.composite(
      ByteBufCodecs.DOUBLE, Vec3::x, ByteBufCodecs.DOUBLE, Vec3::y, ByteBufCodecs.DOUBLE, Vec3::z, Vec3::new
   );
   public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Vec3>> VEC_3 = REGISTRY.register(
      "vec3", () -> EntityDataSerializer.forValueType(COMPOSITE)
   );
}
