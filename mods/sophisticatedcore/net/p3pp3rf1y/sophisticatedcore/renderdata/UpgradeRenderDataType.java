package net.p3pp3rf1y.sophisticatedcore.renderdata;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;

public class UpgradeRenderDataType<T extends IUpgradeRenderData> {
   private final String name;
   private final Class<T> clazz;
   private final Function<CompoundTag, T> deserialize;

   public UpgradeRenderDataType(String name, Class<T> clazz, Function<CompoundTag, T> deserialize) {
      this.name = name;
      this.clazz = clazz;
      this.deserialize = deserialize;
   }

   public String getName() {
      return this.name;
   }

   public Optional<T> cast(IUpgradeRenderData upgradeRenderData) {
      return this.clazz.isInstance(upgradeRenderData) ? Optional.of(this.clazz.cast(upgradeRenderData)) : Optional.empty();
   }

   public T deserialize(CompoundTag nbt) {
      return this.deserialize.apply(nbt);
   }
}
