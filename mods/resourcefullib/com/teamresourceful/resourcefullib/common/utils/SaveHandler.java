package com.teamresourceful.resourcefullib.common.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SaveHandler extends SavedData {
   @NotNull
   public CompoundTag save(@NotNull CompoundTag tag, Provider provider) {
      this.saveData(tag);
      return tag;
   }

   public abstract void loadData(CompoundTag var1);

   public abstract void saveData(CompoundTag var1);

   public static <T extends SaveHandler> T read(Level level, SaveHandler.HandlerType<T> type, String id) {
      return level instanceof ServerLevel serverLevel ? read(serverLevel.getServer().overworld().getDataStorage(), type, id) : type.clientSide();
   }

   public static <T extends SaveHandler> T read(DimensionDataStorage storage, SaveHandler.HandlerType<T> factory, String id) {
      return (T)storage.computeIfAbsent(factory.factory(), id);
   }

   public static <T extends SaveHandler> void handle(Level level, Function<Level, T> getter, Consumer<T> operation) {
      T handler = (T)getter.apply(level);
      operation.accept(handler);
      handler.setDirty();
   }

   public record HandlerType<T extends SaveHandler>(@Nullable T clientSide, Factory<T> factory) {
      public static <T extends SaveHandler> SaveHandler.HandlerType<T> create(Supplier<T> creator) {
         return create(null, creator);
      }

      public static <T extends SaveHandler> SaveHandler.HandlerType<T> create(T clientSide, Supplier<T> creator) {
         return new SaveHandler.HandlerType<>(clientSide, new Factory(creator, (tag, provider) -> {
            T handler = creator.get();
            handler.loadData(tag);
            return handler;
         }, DataFixTypes.OPTIONS));
      }
   }
}
