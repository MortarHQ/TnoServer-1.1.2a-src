package com.moepus.byepregen.yalight;

import com.moepus.byepregen.UnsafeIntArrayAccess;
import java.util.Arrays;
import net.minecraft.world.level.chunk.DataLayer;
import org.spongepowered.asm.mixin.Unique;

public final class YANibbleArray {
   public static final int SIZE = 2048;
   public static final int SAVE_EMPTY = 0;
   public static final int SAVE_FULL = 1;
   public static final int SAVE_DATA = 2;
   @Unique
   public static final byte[] FULL_LIGHT_DATA = createFullLightData();
   static final int STATE_NULL = 0;
   static final int STATE_ZERO = 1;
   static final int STATE_FULL = 2;
   static final int STATE_INIT = 3;
   private int updatingState;
   private volatile int visibleState;
   private byte[] updating;
   private volatile byte[] visible;
   private volatile YANibbleArray.VisibleDataLayer visibleDataLayer;
   private boolean dirty;

   public YANibbleArray() {
      this(1, null);
   }

   private YANibbleArray(int state, byte[] data) {
      this.updatingState = state;
      this.visibleState = state;
      this.updating = data;
      this.visible = data;
   }

   public static YANibbleArray nullArray() {
      return new YANibbleArray(0, null);
   }

   public static YANibbleArray fullArray() {
      return new YANibbleArray(2, null);
   }

   public static YANibbleArray fromVanilla(DataLayer layer) {
      if (layer == null) {
         return nullArray();
      }

      if (layer.isEmpty()) {
         return new YANibbleArray();
      }

      if (layer.isDefinitelyFilledWith(15)) {
         return fullArray();
      }

      byte[] data = layer.getData();
      return new YANibbleArray(3, (byte[])data.clone());
   }

   public static YANibbleArray fromOwnedBytes(byte[] data) {
      if (data == null) {
         return nullArray();
      }

      if (data.length != 2048) {
         throw new IllegalArgumentException("Expected 2048 light bytes, got " + data.length);
      }

      boolean allZero = true;
      boolean allFull = true;

      for (int i = 0; i < 2048 && (allZero || allFull); i++) {
         int value = data[i] & 255;
         allZero &= value == 0;
         allFull &= value == 255;
      }

      if (allZero) {
         return new YANibbleArray();
      } else {
         return allFull ? fullArray() : new YANibbleArray(3, data);
      }
   }

   public boolean isNullVisible() {
      return this.visibleState == 0;
   }

   public boolean hasVisibleLayer() {
      return this.visibleState != 0;
   }

   public boolean isNullUpdating() {
      return this.updatingState == 0;
   }

   public boolean isFullUpdating() {
      return this.updatingState == 2;
   }

   public boolean isDirty() {
      return this.dirty || this.updatingState != this.visibleState;
   }

   public int getVisible(int x, int y, int z) {
      int state = this.visibleState;
      if (state == 0 || state == 1) {
         return 0;
      }

      if (state == 2) {
         return 15;
      }

      byte[] data = this.visible;
      if (data == null) {
         return 0;
      }

      int index = index(x, y, z);
      int packed = UnsafeIntArrayAccess.get(data, index >>> 1) & 255;
      return packed >>> ((index & 1) << 2) & 15;
   }

   public int getUpdating(int x, int y, int z) {
      return this.getUpdating(index(x, y, z));
   }

   public int getUpdating(int index) {
      int state = this.updatingState;
      if (state == 0 || state == 1) {
         return 0;
      }

      if (state == 2) {
         return 15;
      }

      byte[] data = this.updating;
      if (data == null) {
         return 0;
      }

      int packed = UnsafeIntArrayAccess.get(data, index >>> 1) & 255;
      return packed >>> ((index & 1) << 2) & 15;
   }

   public void setUpdating(int x, int y, int z, int value) {
      this.setUpdating(index(x, y, z), value);
   }

   public void setUpdating(int index, int value) {
      this.setUpdatingAndGetDirtyTransition(index, value);
   }

   boolean setUpdatingAndGetDirtyTransition(int index, int value) {
      boolean dirtyTransition = !this.dirty;
      byte[] data = this.ensureUpdating();
      int byteIndex = index >>> 1;
      int shift = (index & 1) << 2;
      int packed = UnsafeIntArrayAccess.get(data, byteIndex) & 255;
      UnsafeIntArrayAccess.set(data, byteIndex, (byte)(packed & ~(15 << shift) | (value & 15) << shift));
      return dirtyTransition;
   }

   public void fillColumnRun(int localX, int localZ, int fromLocalY, int toLocalY, int value) {
      byte[] data = this.ensureUpdating();
      int shift = (localX & 1) << 2;
      int mask = ~(15 << shift);
      int packedValue = (value & 15) << shift;
      int byteIndex = index(localX, fromLocalY, localZ) >>> 1;

      for (int y = fromLocalY; y <= toLocalY; y++) {
         int packed = UnsafeIntArrayAccess.get(data, byteIndex) & 255;
         UnsafeIntArrayAccess.set(data, byteIndex, (byte)(packed & mask | packedValue));
         byteIndex += 128;
      }
   }

   public void publish() {
      if (this.isDirty()) {
         int state = this.updatingState;
         if (state != 0 && state != 1 && state != 2) {
            byte[] updatingData = this.updating;
            this.visible = updatingData;
            this.updating = updatingData;
            this.visibleState = state;
         } else {
            int oldState = this.visibleState;
            this.visibleState = state;
            if (oldState != 3) {
               this.visible = null;
            }
         }

         this.visibleDataLayer = null;
         this.dirty = false;
      }
   }

   public DataLayer toVanilla() {
      int state = this.visibleState;
      if (state == 0) {
         return null;
      }

      byte[] data = this.visible;
      YANibbleArray.VisibleDataLayer layer = this.visibleDataLayer;
      if (layer != null && layer.matches(state, data)) {
         return layer;
      }

      layer = createVisibleDataLayer(state, data);
      if (state == this.visibleState && data == this.visible) {
         this.visibleDataLayer = layer;
      }

      return layer;
   }

   public int visibleSaveKind() {
      int state = this.visibleState;
      if (state == 2) {
         return 1;
      } else {
         return state == 3 && this.visible != null ? 2 : 0;
      }
   }

   public byte[] visibleDataForSave() {
      return this.visible;
   }

   public void retirePublished() {
      this.updating = this.visible;
      this.updatingState = this.visibleState;
      this.dirty = false;
   }

   public void discardUnpublished() {
      this.updating = null;
      this.visible = null;
      this.updatingState = 0;
      this.visibleState = 0;
      this.visibleDataLayer = null;
      this.dirty = false;
   }

   private byte[] ensureUpdating() {
      if (!this.dirty) {
         byte[] next = new byte[2048];
         if (this.updatingState == 3 && this.updating != null) {
            System.arraycopy(this.updating, 0, next, 0, 2048);
         } else if (this.updatingState == 2) {
            Arrays.fill(next, (byte)-1);
         }

         this.updating = next;
         this.updatingState = 3;
         this.dirty = true;
      }

      return this.updating;
   }

   static int index(int x, int y, int z) {
      return (y & 15) << 8 | (z & 15) << 4 | x & 15;
   }

   @Unique
   private static byte[] createFullLightData() {
      byte[] data = new byte[2048];
      Arrays.fill(data, (byte)-1);
      return data;
   }

   private static YANibbleArray.VisibleDataLayer createVisibleDataLayer(int state, byte[] data) {
      return switch (state) {
         case 1 -> new YANibbleArray.VisibleDataLayer(state, 0);
         case 2 -> new YANibbleArray.VisibleDataLayer(state, 15);
         case 3 -> data == null ? new YANibbleArray.VisibleDataLayer(state, 0) : new YANibbleArray.VisibleDataLayer(state, data);
         default -> throw new IllegalStateException("Unexpected visible light state: " + state);
      };
   }

   private static final class VisibleDataLayer extends DataLayer {
      private static final String READ_ONLY_MESSAGE = "Published YA light data is read-only";
      private final int visibleState;
      private final byte[] visibleData;
      private final int visibleDefault;

      private VisibleDataLayer(int state, int defaultValue) {
         super(defaultValue);
         this.visibleState = state;
         this.visibleData = null;
         this.visibleDefault = defaultValue;
      }

      private VisibleDataLayer(int state, byte[] data) {
         super(data);
         this.visibleState = state;
         this.visibleData = data;
         this.visibleDefault = 0;
      }

      private boolean matches(int state, byte[] data) {
         return this.visibleState == state && (state != 3 || this.visibleData == data);
      }

      public void set(int x, int y, int z, int value) {
         throw new UnsupportedOperationException("Published YA light data is read-only");
      }

      public void fill(int defaultValue) {
         throw new UnsupportedOperationException("Published YA light data is read-only");
      }

      public byte[] getData() {
         if (this.visibleData != null) {
            return (byte[])this.visibleData.clone();
         }

         byte[] data = new byte[2048];
         if (this.visibleDefault != 0) {
            Arrays.fill(data, (byte)(this.visibleDefault | this.visibleDefault << 4));
         }

         return data;
      }

      public DataLayer copy() {
         return this.visibleData == null ? new DataLayer(this.visibleDefault) : new DataLayer((byte[])this.visibleData.clone());
      }
   }
}
