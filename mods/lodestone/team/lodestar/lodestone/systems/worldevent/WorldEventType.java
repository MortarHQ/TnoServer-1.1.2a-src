package team.lodestar.lodestone.systems.worldevent;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import team.lodestar.lodestone.registry.client.LodestoneWorldEventRenderers;

public class WorldEventType {
   public final ResourceLocation id;
   public final WorldEventType.EventInstanceSupplier<?> supplier;
   public final boolean clientSynced;

   public WorldEventType(ResourceLocation id, WorldEventType.EventInstanceSupplier<?> supplier, boolean clientSynced) {
      this.id = id;
      this.supplier = supplier;
      this.clientSynced = clientSynced;
   }

   public WorldEventType(ResourceLocation id, WorldEventType.EventInstanceSupplier<?> supplier) {
      this(id, supplier, false);
   }

   public boolean isClientSynced() {
      return this.clientSynced;
   }

   public WorldEventInstance createInstance(CompoundTag tag) {
      return this.supplier.getInstance().deserializeNBT(tag);
   }

   public static class Builder<T extends WorldEventInstance> {
      private final ResourceLocation id;
      private final WorldEventType.EventInstanceSupplier<T> supplier;
      private boolean clientSynced;
      private WorldEventRenderer<T> renderer;

      private Builder(WorldEventType.EventInstanceSupplier<T> supplier, ResourceLocation id) {
         this.id = id;
         this.supplier = supplier;
      }

      public static <T extends WorldEventInstance> WorldEventType.Builder<T> of(WorldEventType.EventInstanceSupplier<T> supplier, ResourceLocation id) {
         return new WorldEventType.Builder<>(supplier, id);
      }

      public WorldEventType.Builder<T> clientSynced(@Nullable WorldEventRenderer<T> renderer) {
         this.clientSynced = true;
         this.renderer = renderer;
         return this;
      }

      public WorldEventType.Builder<T> clientSynced() {
         return this.clientSynced(null);
      }

      public WorldEventType build() {
         WorldEventType type = new WorldEventType(this.id, this.supplier, this.clientSynced);
         if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
            LodestoneWorldEventRenderers.registerRenderer(type, this.renderer);
         }

         return type;
      }
   }

   public interface EventInstanceSupplier<T extends WorldEventInstance> {
      T getInstance();
   }
}
