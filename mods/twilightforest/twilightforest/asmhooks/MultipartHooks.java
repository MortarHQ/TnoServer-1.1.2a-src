package twilightforest.asmhooks;

import java.util.Iterator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Autowired;
import twilightforest.util.multiparts.MultipartEntityUtil;

public class MultipartHooks {
   @Autowired
   private static MultipartEntityUtil multipartEntityUtil;

   public static Iterator<Entity> resolveEntitiesForRendering(Iterator<Entity> iter) {
      return multipartEntityUtil.injectTFPartEntities(iter);
   }

   @Nullable
   public static EntityRenderer<?> resolveEntityRenderer(@Nullable EntityRenderer<?> renderer, Entity entity) {
      return multipartEntityUtil.tryLookupTFPartRenderer(renderer, entity);
   }

   public static Entity sendDirtyEntityData(Entity entity) {
      return multipartEntityUtil.sendDirtyMultipartEntityData(entity);
   }
}
