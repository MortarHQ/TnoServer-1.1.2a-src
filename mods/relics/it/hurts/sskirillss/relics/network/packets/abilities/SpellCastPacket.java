package it.hurts.sskirillss.relics.network.packets.abilities;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.system.casts.abilities.AbilityReference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SpellCastPacket implements CustomPacketPayload {
   private final CastType type;
   private final CastStage stage;
   private final CompoundTag ability;
   public static final Type<SpellCastPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "spell_cast"));
   public static final StreamCodec<ByteBuf, SpellCastPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.idMapper(CastType.BY_ID, CastType::getId),
      SpellCastPacket::getType,
      ByteBufCodecs.idMapper(CastStage.BY_ID, CastStage::getId),
      SpellCastPacket::getStage,
      ByteBufCodecs.COMPOUND_TAG,
      SpellCastPacket::getAbility,
      SpellCastPacket::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         AbilityReference reference = new AbilityReference().deserializeNBT(this.ability);
         ItemStack stack = reference.getSlot().gatherStack(player);
         if (stack.getItem() instanceof IRelicItem relic) {
            if (!relic.canPlayerUseAbility(player, stack, reference.getId())) {
               if (relic.isAbilityTicking(stack, reference.getId())) {
                  relic.setAbilityTicking(stack, reference.getId(), false);
                  relic.castActiveAbility(stack, player, reference.getId(), this.type, CastStage.END);
               }
            } else {
               switch (this.type) {
                  case CYCLICAL:
                  case TOGGLEABLE:
                     switch (this.stage) {
                        case START:
                           relic.setAbilityTicking(stack, reference.getId(), true);
                           break;
                        case END:
                           relic.setAbilityTicking(stack, reference.getId(), false);
                     }
                  default:
                     relic.castActiveAbility(stack, player, reference.getId(), this.type, this.stage);
               }
            }
         }
      });
   }

   public CastType getType() {
      return this.type;
   }

   public CastStage getStage() {
      return this.stage;
   }

   public CompoundTag getAbility() {
      return this.ability;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SpellCastPacket other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$type = this.getType();
         Object other$type = other.getType();
         if (this$type == null ? other$type == null : this$type.equals(other$type)) {
            Object this$stage = this.getStage();
            Object other$stage = other.getStage();
            if (this$stage == null ? other$stage == null : this$stage.equals(other$stage)) {
               Object this$ability = this.getAbility();
               Object other$ability = other.getAbility();
               return this$ability == null ? other$ability == null : this$ability.equals(other$ability);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof SpellCastPacket;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $stage = this.getStage();
      result = result * 59 + ($stage == null ? 43 : $stage.hashCode());
      Object $ability = this.getAbility();
      return result * 59 + ($ability == null ? 43 : $ability.hashCode());
   }

   @Override
   public String toString() {
      return "SpellCastPacket(type=" + this.getType() + ", stage=" + this.getStage() + ", ability=" + this.getAbility() + ")";
   }

   public SpellCastPacket(CastType type, CastStage stage, CompoundTag ability) {
      this.type = type;
      this.stage = stage;
      this.ability = ability;
   }
}
