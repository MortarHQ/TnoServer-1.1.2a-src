package earth.terrarium.handcrafted.client.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import earth.terrarium.handcrafted.client.utils.neoforge.ClientPlatformUtilsImpl;
import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientPlatformUtils {
   @ExpectPlatform
   @Transformed
   public static <T extends Entity> void registerRenderer(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider) {
      ClientPlatformUtilsImpl.registerRenderer(entity, provider);
   }

   @FunctionalInterface
   public interface BlockRendererRegistry {
      <T extends BlockEntity> void register(RegistryEntry<? extends BlockEntityType<? extends T>> var1, BlockEntityRendererProvider<T> var2);
   }

   @FunctionalInterface
   public interface LayerDefinitionRegistry {
      void register(ModelLayerLocation var1, Supplier<LayerDefinition> var2);
   }
}
