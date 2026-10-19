package fuzs.puzzleslib.neoforge.impl.client.core.context;

import fuzs.puzzleslib.api.client.core.v1.context.SkullRenderersContext;
import fuzs.puzzleslib.api.client.init.v1.SkullRenderersFactory;
import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.level.block.SkullBlock.Type;

public record SkullRenderersContextNeoForgeImpl(EntityModelSet entityModelSet, BiConsumer<Type, SkullModelBase> consumer) implements SkullRenderersContext {
   @Override
   public void registerSkullRenderer(SkullRenderersFactory factory) {
      Objects.requireNonNull(factory, "factory is null");
      factory.createSkullRenderers(this.entityModelSet, this.consumer);
   }
}
