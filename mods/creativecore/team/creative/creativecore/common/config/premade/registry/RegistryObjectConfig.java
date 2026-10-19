package team.creative.creativecore.common.config.premade.registry;

import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.ICreativeConfig;

public class RegistryObjectConfig<T> implements ICreativeConfig {
   public final Registry<T> registry;
   public ResourceLocation location;
   public T value;

   public RegistryObjectConfig(Registry<T> registry, ResourceLocation location) {
      this.registry = registry;
      this.location = location;
      this.configured(null);
   }

   @Override
   public void configured(Side side) {
      this.value = (T)this.registry.get(this.location);
   }

   public Holder<T> getHolder() {
      return this.registry.wrapAsHolder(this.value);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof RegistryObjectConfig re) ? super.equals(obj) : re.registry == this.registry && Objects.equals(re.location, this.location);
   }
}
