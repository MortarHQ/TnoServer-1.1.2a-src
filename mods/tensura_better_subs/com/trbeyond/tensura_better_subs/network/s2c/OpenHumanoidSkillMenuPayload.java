package com.trbeyond.tensura_better_subs.network.s2c;

import com.trbeyond.tensura_better_subs.client.screen.ScreenHelperSubordinates;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public record OpenHumanoidSkillMenuPayload(int entityId) implements CustomPacketPayload {
   public static final Type<OpenHumanoidSkillMenuPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "open_humanoid_skill_menu")
   );
   public static final StreamCodec<FriendlyByteBuf, OpenHumanoidSkillMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenHumanoidSkillMenuPayload::encode, OpenHumanoidSkillMenuPayload::new
   );

   public OpenHumanoidSkillMenuPayload(FriendlyByteBuf buf) {
      this(buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         ScreenHelperSubordinates.openScreen(4, (LivingEntity)context.getPlayer().level().getEntity(this.entityId));
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
