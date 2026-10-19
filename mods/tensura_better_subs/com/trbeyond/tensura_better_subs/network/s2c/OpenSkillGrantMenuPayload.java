package com.trbeyond.tensura_better_subs.network.s2c;

import com.trbeyond.tensura_better_subs.ability.skill.IFoodChain;
import com.trbeyond.tensura_better_subs.client.screen.ScreenHelperSubordinates;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public record OpenSkillGrantMenuPayload(int entityID, ResourceLocation skillGrantingSkill) implements CustomPacketPayload {
   public static final Type<OpenSkillGrantMenuPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "open_skill_granting_menu"));
   public static final StreamCodec<FriendlyByteBuf, OpenSkillGrantMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenSkillGrantMenuPayload::encode, OpenSkillGrantMenuPayload::new
   );

   public OpenSkillGrantMenuPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readResourceLocation());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
      buf.writeResourceLocation(this.skillGrantingSkill);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         Entity entity = context.getPlayer().level().getEntity(this.entityID);
         if (!(SkillRegistry.SKILLS.get(this.skillGrantingSkill) instanceof IFoodChain)) {
            return;
         }

         if (entity instanceof LivingEntity subordinate && SubordinateHelper.isSubordinate(context.getPlayer(), subordinate)) {
            ScreenHelperSubordinates.openSkillGrantScreen(context.getPlayer(), subordinate, this.skillGrantingSkill);
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
