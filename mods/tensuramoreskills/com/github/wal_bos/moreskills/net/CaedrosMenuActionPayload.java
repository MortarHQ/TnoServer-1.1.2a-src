package com.github.wal_bos.moreskills.net;

import com.github.wal_bos.moreskills.ability.skill.ultimate.CaedrosGodOfConquestSkill;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record CaedrosMenuActionPayload(CaedrosMenuActionPayload.Action action, String value) implements CustomPacketPayload {
   public static final Type<CaedrosMenuActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_menu_action"));
   public static final StreamCodec<FriendlyByteBuf, CaedrosMenuActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      CaedrosMenuActionPayload::write, CaedrosMenuActionPayload::new
   );

   public CaedrosMenuActionPayload(FriendlyByteBuf buf) {
      this((CaedrosMenuActionPayload.Action)buf.readEnum(CaedrosMenuActionPayload.Action.class), buf.readUtf());
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeEnum(this.action);
      buf.writeUtf(this.value == null ? "" : this.value);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(() -> {
            if (context.getPlayer() instanceof ServerPlayer player) {
               switch (this.action) {
                  case SET_ASPECT:
                     CaedrosGodOfConquestSkill.CaedrosAspect aspect = switch (this.value.toLowerCase()) {
                        case "control" -> CaedrosGodOfConquestSkill.CaedrosAspect.CONTROL;
                        case "famine" -> CaedrosGodOfConquestSkill.CaedrosAspect.FAMINE;
                        case "war" -> CaedrosGodOfConquestSkill.CaedrosAspect.WAR;
                        case "death" -> CaedrosGodOfConquestSkill.CaedrosAspect.DEATH;
                        default -> null;
                     };
                     CaedrosGodOfConquestSkill.setActiveAspect(player, aspect);
                     break;
                  case CLEAR_ASPECT:
                     CaedrosGodOfConquestSkill.setActiveAspect(player, null);
                     break;
                  case LOCK_SKILL:
                     CaedrosGodOfConquestSkill.setControlLockedSkill(player, this.value);
                     break;
                  case CLEAR_LOCK:
                     CaedrosGodOfConquestSkill.setControlLockedSkill(player, "OFF");
                     break;
                  case BUY_BACK_GUILT:
                     CaedrosGodOfConquestSkill.rebuyEatenSkillWithGuilt(player, this.value);
                     break;
                  case BUY_BACK_EP:
                     CaedrosGodOfConquestSkill.rebuyEatenSkillWithEP(player, this.value);
               }
            }
         });
      }
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public enum Action {
      SET_ASPECT,
      CLEAR_ASPECT,
      LOCK_SKILL,
      CLEAR_LOCK,
      BUY_BACK_GUILT,
      BUY_BACK_EP;
   }
}
