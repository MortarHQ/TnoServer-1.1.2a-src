package bigchadguys.sellingbin.world.data;

import java.io.File;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class WorldDataType<T extends WorldData> {
   private final String path;
   private final Supplier<T> constructor;

   public WorldDataType(String path, Supplier<T> constructor) {
      this.path = path.replace(".", File.separator);
      this.constructor = constructor;
   }

   public T getLocal(ServerLevel world) {
      return (T)world.getDataStorage().computeIfAbsent(new Factory(this.constructor, (nbt, wrapperLookup) -> {
         T data = this.constructor.get();
         if (nbt instanceof CompoundTag compound) {
            data.readNbt(compound);
         }

         return data;
      }, null), this.path);
   }

   public T getGlobal(MinecraftServer server) {
      return this.getLocal(server.overworld());
   }

   public T getGlobal(Level world) {
      return this.getGlobal(world.getServer());
   }
}
