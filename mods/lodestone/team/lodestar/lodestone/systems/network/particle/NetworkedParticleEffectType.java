package team.lodestar.lodestone.systems.network.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataWrapper;

public abstract class NetworkedParticleEffectType<T extends NetworkedParticleEffectExtraData> {
   public static final Map<String, NetworkedParticleEffectType<?>> EFFECT_TYPES = new LinkedHashMap<>();
   public static final Codec<NetworkedParticleEffectType<?>> CODEC = Codec.STRING
      .comapFlatMap(s -> DataResult.success(EFFECT_TYPES.get(s)), NetworkedParticleEffectType::getId);
   protected final String id;

   public NetworkedParticleEffectType(String id) {
      this.id = id;
      EFFECT_TYPES.put(id, this);
   }

   public String getId() {
      return this.id;
   }

   public Optional<StreamCodec<ByteBuf, ? extends NetworkedParticleEffectPositionData>> getPositionCodec() {
      return Optional.of(NetworkedParticleEffectPositionData.STREAM_CODEC);
   }

   public Optional<StreamCodec<ByteBuf, ? extends NetworkedParticleEffectColorData>> getColorCodec() {
      return Optional.of(NetworkedParticleEffectColorData.STREAM_CODEC);
   }

   public Optional<StreamCodec<ByteBuf, ? extends NetworkedParticleEffectExtraData>> getExtraCodec() {
      return Optional.empty();
   }

   public Optional<? extends NetworkedParticleEffectExtraData> getDefaultExtraData() {
      return Optional.empty();
   }

   protected void castAndAct(
      Level level,
      RandomSource random,
      NetworkedParticleEffectPositionData positionData,
      NetworkedParticleEffectColorData colorData,
      NetworkedParticleEffectExtraData extraData
   ) {
      this.act(level, random, positionData, colorData, (T)extraData);
   }

   public abstract void act(Level var1, RandomSource var2, NetworkedParticleEffectPositionData var3, NetworkedParticleEffectColorData var4, T var5);

   public NetworkedParticleEffectType.ParticleEffectBuilder<T> createEffect(BlockPos position) {
      return this.createEffect().at(position);
   }

   public NetworkedParticleEffectType.ParticleEffectBuilder<T> createEffect(Vec3 position) {
      return this.createEffect().at(position);
   }

   public NetworkedParticleEffectType.ParticleEffectBuilder<T> createEffect(Entity target) {
      return this.createEffect().at(target);
   }

   public NetworkedParticleEffectType.ParticleEffectBuilder<T> createEffect() {
      return new NetworkedParticleEffectType.ParticleEffectBuilder<>(this);
   }

   public static class ParticleEffectBuilder<T extends NetworkedParticleEffectExtraData> {
      protected final NetworkedParticleEffectType<T> type;
      protected NetworkedParticleEffectPositionData position;
      protected NetworkedParticleEffectColorData color;
      protected T extra;

      public ParticleEffectBuilder(NetworkedParticleEffectType<T> type) {
         this.type = type;
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> at(BlockPos position) {
         return this.at(new NetworkedParticleEffectPositionData(position));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> at(Vec3 position) {
         return this.at(new NetworkedParticleEffectPositionData(position));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> at(Entity target) {
         return this.at(new NetworkedParticleEffectPositionData(target));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> at(NetworkedParticleEffectPositionData position) {
         this.position = position;
         return this;
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> color(Color color) {
         return this.color(ColorParticleData.create(color).build());
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> color(ColorParticleDataWrapper color) {
         return this.color(NetworkedParticleEffectColorData.fromColor(color));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> color(List<? extends ColorParticleDataWrapper> colors) {
         return this.color(NetworkedParticleEffectColorData.fromColors(colors));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> color(NetworkedParticleEffectColorData color) {
         this.color = color;
         return this;
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> customData(T extra) {
         this.extra = extra;
         return this;
      }

      protected T getCustomData() {
         if (this.type.getExtraCodec().isEmpty()) {
            return null;
         }

         if (this.extra == null) {
            Optional<? extends NetworkedParticleEffectExtraData> defaultExtra = this.type.getDefaultExtraData();
            if (defaultExtra.isEmpty()) {
               throw new IllegalArgumentException("Effect type that demands custom data did not receive any and has no default provided");
            }

            this.customData((T)defaultExtra.get());
         }

         return this.extra;
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> spawn(ServerLevel level) {
         return this.position == null
            ? this.spawn(x$0 -> PacketDistributor.sendToAllPlayers(x$0, new CustomPacketPayload[0]))
            : this.spawn(p -> PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(this.position.getAsBlockPos()), p, new CustomPacketPayload[0]));
      }

      public NetworkedParticleEffectType.ParticleEffectBuilder<T> spawn(Consumer<NetworkedParticleEffectPayload> sender) {
         sender.accept(new NetworkedParticleEffectPayload(this.type, this.position, this.color, this.getCustomData()));
         return this;
      }
   }
}
