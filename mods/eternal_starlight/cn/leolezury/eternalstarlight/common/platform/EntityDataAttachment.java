package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.network.SyncAttachmentsPacket;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityDataAttachment<T> {
   ResourceLocation id();

   boolean hasData(Entity var1);

   T getData(Entity var1);

   Optional<T> getExistingData(Entity var1);

   @Nullable
   T setDataUnsynced(Entity var1, T var2);

   @Nullable
   default T setData(Entity entity, T data) {
      T previousValue = this.setDataUnsynced(entity, data);
      if (this.streamCodec() != null && entity.level() instanceof ServerLevel serverLevel && this.shouldSync(previousValue, data)) {
         ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, SyncAttachmentsPacket.create(entity, this, data, serverLevel.registryAccess()));
      }

      return previousValue;
   }

   @Nullable
   T removeDataUnsynced(Entity var1);

   @Nullable
   default T removeData(Entity entity) {
      T previousValue = this.removeDataUnsynced(entity);
      if (this.streamCodec() != null && entity.level() instanceof ServerLevel serverLevel && this.shouldSync(previousValue, null)) {
         ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, SyncAttachmentsPacket.create(entity, this, null, serverLevel.registryAccess()));
      }

      return previousValue;
   }

   @Nullable
   StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec();

   boolean shouldSync(T var1, T var2);
}
