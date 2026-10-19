package com.moepus.byepregen.PaletteContainer.FastPalette;

import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import net.minecraft.world.level.chunk.PalettedContainer.Strategy;
import org.jetbrains.annotations.NotNull;

abstract class FastPalettedContainer<T> extends PalettedContainer<T> implements FastPalettedContainerAccess<T> {
   protected Data<T> fastData;
   protected T zeroValue;

   protected FastPalettedContainer(IdMap<T> idList, T defaultValue, Strategy strategy) {
      super(idList, defaultValue, strategy);
      this.updateFastData(this.data);
   }

   protected FastPalettedContainer(PalettedContainer<T> source) {
      super(source.registry, source.data.palette().valueFor(0), source.strategy);
      this.copyFrom(source.data);
   }

   public int onResize(int bits, @NotNull T value) {
      Data<T> oldData = this.fastData != null ? this.fastData : this.data;
      Data<T> newData = this.createOrReuseData(oldData, bits);
      newData.copyFrom(oldData.palette(), oldData.storage());
      this.data = newData;
      this.updateFastData(newData);
      return newData.palette().idFor(value);
   }

   public void read(@NotNull FriendlyByteBuf buffer) {
      super.read(buffer);
      this.updateFastData(this.data);
   }

   protected final T getFast(int index) {
      T zeroValue = this.zeroValue;
      if (zeroValue != null) {
         return zeroValue;
      }

      Data<T> data = this.fastData;
      return (T)data.palette().valueFor(data.storage().get(index));
   }

   protected final T getAndSetFast(int index, T value) {
      Data<T> data = this.fastData;
      int newId = data.palette().idFor(value);
      data = this.fastData;
      int oldId = data.storage().getAndSet(index, newId);
      return (T)data.palette().valueFor(oldId);
   }

   protected final void setFast(int index, T value) {
      Data<T> data = this.fastData;
      int newId = data.palette().idFor(value);
      data = this.fastData;
      data.storage().set(index, newId);
   }

   private void copyFrom(Data<T> sourceData) {
      Data<T> newData = this.createOrReuseData(null, sourceData.storage().getBits());
      newData.copyFrom(sourceData.palette(), sourceData.storage());
      this.data = newData;
      this.updateFastData(newData);
   }

   @Override
   public final void byepregen$updateFastData(Data<T> data) {
      this.updateFastData(data);
   }

   private void updateFastData(Data<T> data) {
      this.fastData = data;
      this.zeroValue = (T)(data.storage() instanceof ZeroBitStorage ? data.palette().valueFor(0) : null);
   }

   public final void c2me$setUnsafe(int x, int y, int z, T value) {
      this.setFast(this.strategy.getIndex(x, y, z), value);
   }
}
