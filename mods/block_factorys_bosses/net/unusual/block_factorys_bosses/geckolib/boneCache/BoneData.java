package net.unusual.block_factorys_bosses.geckolib.boneCache;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import org.joml.Matrix4f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BoneData {
   public static final long UNWANTED_AFTER_MS = 2000L;
   private long lastAccessed = -1L;
   private boolean initialized = false;
   private boolean alwaysWanted = false;
   public final Matrix4f worldTransform = new Matrix4f();

   public void markAccessed() {
      this.lastAccessed = System.currentTimeMillis();
   }

   public void setAlwaysWanted() {
      this.alwaysWanted = true;
   }

   public boolean isUnwanted() {
      return !this.alwaysWanted && this.lastAccessed < System.currentTimeMillis() - 2000L;
   }

   public void markUpdated() {
      this.initialized = true;
   }

   public boolean isInitialized() {
      return this.initialized;
   }
}
