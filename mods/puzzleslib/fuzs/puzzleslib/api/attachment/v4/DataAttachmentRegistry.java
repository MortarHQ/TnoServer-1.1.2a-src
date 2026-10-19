package fuzs.puzzleslib.api.attachment.v4;

import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import fuzs.puzzleslib.api.network.v3.PlayerSet;
import fuzs.puzzleslib.impl.attachment.DataAttachmentRegistryImpl;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

public final class DataAttachmentRegistry {
   private DataAttachmentRegistry() {
   }

   public static <A> DataAttachmentRegistry.EntityBuilder<A> entityBuilder() {
      return DataAttachmentRegistryImpl.INSTANCE.getEntityTypeBuilder();
   }

   public static <A> DataAttachmentRegistry.Builder<BlockEntity, A> blockEntityBuilder() {
      return DataAttachmentRegistryImpl.INSTANCE.getBlockEntityTypeBuilder();
   }

   public static <A> DataAttachmentRegistry.Builder<LevelChunk, A> levelChunkBuilder() {
      return DataAttachmentRegistryImpl.INSTANCE.getLevelChunkBuilder();
   }

   public static <A> DataAttachmentRegistry.Builder<Level, A> levelBuilder() {
      return DataAttachmentRegistryImpl.INSTANCE.getLevelBuilder();
   }

   public interface BlockEntityBuilder<A> extends DataAttachmentRegistry.RegistryBuilder<BlockEntity, A> {
      default DataAttachmentRegistry.BlockEntityBuilder<A> defaultValue(A defaultValue) {
         return (DataAttachmentRegistry.BlockEntityBuilder<A>)DataAttachmentRegistry.RegistryBuilder.super.defaultValue(defaultValue);
      }

      default DataAttachmentRegistry.BlockEntityBuilder<A> defaultValue(Class<? extends BlockEntity> type, A defaultValue) {
         return (DataAttachmentRegistry.BlockEntityBuilder<A>)DataAttachmentRegistry.RegistryBuilder.super.defaultValue(type, defaultValue);
      }

      default DataAttachmentRegistry.BlockEntityBuilder<A> defaultValue(BlockEntityType<?> type, A defaultValue) {
         return this.defaultValue(blockEntity -> blockEntity.getType() == type, defaultValue);
      }

      DataAttachmentRegistry.BlockEntityBuilder<A> defaultValue(Predicate<BlockEntity> var1, A var2);

      DataAttachmentRegistry.BlockEntityBuilder<A> persistent(Codec<A> var1);
   }

   public interface Builder<T, A> {
      DataAttachmentRegistry.Builder<T, A> defaultValue(A var1);

      DataAttachmentRegistry.Builder<T, A> persistent(Codec<A> var1);

      DataAttachmentType<T, A> build(ResourceLocation var1);
   }

   public interface EntityBuilder<A> extends DataAttachmentRegistry.RegistryBuilder<Entity, A> {
      DataAttachmentRegistry.EntityBuilder<A> persistent(Codec<A> var1);

      default DataAttachmentRegistry.EntityBuilder<A> defaultValue(A defaultValue) {
         return (DataAttachmentRegistry.EntityBuilder<A>)DataAttachmentRegistry.RegistryBuilder.super.defaultValue(defaultValue);
      }

      default DataAttachmentRegistry.EntityBuilder<A> defaultValue(Class<? extends Entity> type, A defaultValue) {
         return (DataAttachmentRegistry.EntityBuilder<A>)DataAttachmentRegistry.RegistryBuilder.super.defaultValue(type, defaultValue);
      }

      default DataAttachmentRegistry.EntityBuilder<A> defaultValue(EntityType<?> type, A defaultValue) {
         return this.defaultValue(entity -> entity.getType() == type, defaultValue);
      }

      DataAttachmentRegistry.EntityBuilder<A> defaultValue(Predicate<Entity> var1, A var2);

      default DataAttachmentRegistry.EntityBuilder<A> networkSynchronized(StreamCodec<? super RegistryFriendlyByteBuf, A> streamCodec) {
         return this.networkSynchronized(streamCodec, null);
      }

      DataAttachmentRegistry.EntityBuilder<A> networkSynchronized(
         StreamCodec<? super RegistryFriendlyByteBuf, A> var1, @Nullable Function<Entity, PlayerSet> var2
      );

      DataAttachmentRegistry.EntityBuilder<A> copyOnDeath();
   }

   public interface RegistryBuilder<T, A> extends DataAttachmentRegistry.Builder<T, A> {
      default DataAttachmentRegistry.RegistryBuilder<T, A> defaultValue(A defaultValue) {
         return this.defaultValue(Predicates.alwaysTrue(), defaultValue);
      }

      default DataAttachmentRegistry.RegistryBuilder<T, A> defaultValue(Class<? extends T> type, A defaultValue) {
         return this.defaultValue(type::isInstance, defaultValue);
      }

      DataAttachmentRegistry.RegistryBuilder<T, A> defaultValue(Predicate<T> var1, A var2);
   }
}
