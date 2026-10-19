package twilightforest.util.multiparts;

import java.util.Iterator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Component;
import twilightforest.client.BakedMultiPartRenderers;
import twilightforest.entity.TFPart;
import twilightforest.network.UpdateTFMultipartPacket;

@Component
public class MultipartEntityUtil {
   public Iterator<Entity> injectTFPartEntities(Iterator<Entity> iter) {
      return new MultipartEntityIteratorWrapper(iter);
   }

   @Nullable
   public EntityRenderer<?> tryLookupTFPartRenderer(@Nullable EntityRenderer<?> renderer, Entity entity) {
      return entity instanceof TFPart<?> part ? BakedMultiPartRenderers.lookup(part.renderer()) : renderer;
   }

   public Entity sendDirtyMultipartEntityData(Entity entity) {
      if (entity.isMultipartEntity()) {
         PacketDistributor.sendToPlayersTrackingEntity(entity, new UpdateTFMultipartPacket(entity), new CustomPacketPayload[0]);
      }

      return entity;
   }
}
