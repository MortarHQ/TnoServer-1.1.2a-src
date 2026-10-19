package artifacts.neoforge.registry;

import artifacts.neoforge.ArtifactsNeoForge;
import artifacts.registry.Register;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegister<R> extends Register<R> {
   private final DeferredRegister<R> register;

   public NeoForgeRegister(ResourceKey<Registry<R>> registry) {
      super(registry);
      this.register = DeferredRegister.create(registry, "artifacts");
   }

   @Override
   public void register() {
      super.register();
      ArtifactsNeoForge.addDeferredRegister(this.register);
   }

   @Override
   protected <T extends R> void bind(RegistryHolder<R, T> holder) {
      holder.bind(this.register.register(holder.unwrapKey().orElseThrow().location().getPath(), holder.getFactory()));
   }
}
