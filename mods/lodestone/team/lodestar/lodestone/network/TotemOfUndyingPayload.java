package team.lodestar.lodestone.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class TotemOfUndyingPayload extends OneSidedPayloadData {
   private final int entityId;
   private ItemStack stack;

   public TotemOfUndyingPayload(FriendlyByteBuf byteBuf) {
      this.entityId = byteBuf.readInt();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void handle(IPayloadContext context) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level.getEntity(this.entityId) instanceof LivingEntity livingEntity) {
         minecraft.particleEngine.createTrackingEmitter(livingEntity, ParticleTypes.TOTEM_OF_UNDYING, 30);
         minecraft.level
            .playLocalSound(
               livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.TOTEM_USE, livingEntity.getSoundSource(), 1.0F, 1.0F, false
            );
         if (livingEntity == minecraft.player) {
            minecraft.gameRenderer.displayItemActivation(this.stack);
         }
      }
   }

   @Override
   public void serialize(FriendlyByteBuf byteBuf) {
      byteBuf.writeInt(this.entityId);
   }
}
