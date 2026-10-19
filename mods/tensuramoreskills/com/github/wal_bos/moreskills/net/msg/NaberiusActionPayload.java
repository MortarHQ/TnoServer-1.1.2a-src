package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.ability.skill.shades.NaberiusSkill;
import com.github.wal_bos.moreskills.menu.NaberiusMenu;
import com.github.wal_bos.moreskills.menu.NaberiusMenuProvider;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;

public record NaberiusActionPayload(NaberiusActionPayload.Action action, int value) implements CustomPacketPayload {
   public static final Type<NaberiusActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_action"));
   public static final StreamCodec<FriendlyByteBuf, NaberiusActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      NaberiusActionPayload::encode, NaberiusActionPayload::new
   );

   public NaberiusActionPayload(FriendlyByteBuf buf) {
      this((NaberiusActionPayload.Action)buf.readEnum(NaberiusActionPayload.Action.class), buf.readVarInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeEnum(this.action);
      buf.writeVarInt(this.value);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(() -> {
            ServerPlayer player = (ServerPlayer)context.getPlayer();
            if (player != null) {
               Skills skills = SkillAPI.getSkillsFrom(player);
               if (skills != null) {
                  int page = player.containerMenu instanceof NaberiusMenu menu ? menu.getPageMode() : 1;

                  for (ManasSkillInstance instance : skills.getLearnedSkills()) {
                     if (instance.getSkill() instanceof NaberiusSkill skill) {
                        switch (this.action) {
                           case TOGGLE_SELECTED:
                              NaberiusSkill.toggleSelected(player, this.value);
                              break;
                           case REDUCE_COUNT:
                              NaberiusSkill.reduceStoredCount(player, this.value);
                              break;
                           case DELETE_SELECTED:
                              NaberiusSkill.deleteSelected(player, this.value);
                              break;
                           case MANIFEST_SELECTED:
                              skill.manifestSelected(player, instance, false);
                              break;
                           case MANIFEST_ALL_SELECTED:
                              skill.manifestSelected(player, instance, true);
                              break;
                           case CLEAR_ACTIVE_SUMMONS:
                              skill.clearActiveSummons(player);
                              break;
                           case CRAFT_CHIMERA:
                              craftChimera(player, this.value);
                        }

                        NaberiusMenuProvider.open(player, page);
                        break;
                     }
                  }
               }
            }
         });
      }
   }

   private static void craftChimera(ServerPlayer player, int recipe) {
      CompoundTag root = NaberiusSkill.root(player);
      ListTag archive = root.getList("archive", 10);
      if (!archive.isEmpty()) {
         double hp = totalHp(archive);
         double ep = totalEp(archive);
         int sacrificed = totalCount(archive);

         LivingEntity entity = switch (recipe) {
            case 0 -> hp >= 40000.0 && ep >= 300000.0 && sacrificed >= 2 ? (LivingEntity)EntityType.WOLF.create(player.level()) : null;
            case 1 -> hp >= 75000.0 && ep >= 700000.0 && sacrificed >= 3 ? (LivingEntity)EntityType.ALLAY.create(player.level()) : null;
            case 2 -> hp >= 150000.0 && ep >= 1500000.0 && sacrificed >= 5 ? (LivingEntity)EntityType.WARDEN.create(player.level()) : null;
            default -> null;
         };
         if (entity == null) {
            player.displayClientMessage(Component.literal("Not enough stored life for that chimera.").withStyle(ChatFormatting.RED), true);
         } else {
            try {
               entity.moveTo(
                  player.getX() + player.getLookAngle().x * 4.0, player.getY(), player.getZ() + player.getLookAngle().z * 4.0, player.getYRot(), 0.0F
               );
               if (entity instanceof TamableAnimal tamable) {
                  tamable.tame(player);
                  tamable.setOwnerUUID(player.getUUID());
               }

               AttributeInstance maxHp = entity.getAttribute(Attributes.MAX_HEALTH);
               if (maxHp != null) {
                  double value = switch (recipe) {
                     case 0 -> Math.min(60000.0, hp * 0.7);
                     case 1 -> Math.min(95000.0, hp * 0.85);
                     case 2 -> Math.min(180000.0, hp);
                     default -> maxHp.getBaseValue();
                  };
                  maxHp.setBaseValue(value);
                  entity.setHealth((float)value);
               }

               AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
               if (attack != null) {
                  double value = switch (recipe) {
                     case 0 -> Math.min(4000.0, 200.0 + ep * 0.002);
                     case 1 -> Math.min(7500.0, 320.0 + ep * 0.003);
                     case 2 -> Math.min(15000.0, 600.0 + ep * 0.005);
                     default -> attack.getBaseValue();
                  };
                  attack.setBaseValue(value);
               }

               AttributeInstance scale = entity.getAttribute(Attributes.SCALE);
               if (scale != null) {
                  scale.setBaseValue(switch (recipe) {
                     case 0 -> 1.7;
                     case 1 -> 2.0;
                     case 2 -> 2.6;
                     default -> 1.0;
                  });
               }

               AttributeInstance pierce = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
               if (pierce != null) {
                  pierce.addOrReplacePermanentModifier(new AttributeModifier(NaberiusSkill.MASTERY_PIERCE, 1.0, Operation.ADD_VALUE));
               }

               TensuraStorages.getExistenceFrom(entity).setSummoner(player.getUUID());
               TensuraStorages.getExistenceFrom(entity).setSummonedSecond(3600);
               TensuraStorages.getExistenceFrom(entity).markDirty();
               entity.getPersistentData().putUUID("owner_uuid", player.getUUID());
               entity.getPersistentData().putBoolean("naberius_active", true);

               entity.setCustomName(switch (recipe) {
                  case 0 -> Component.literal("Grave Beast");
                  case 1 -> Component.literal("Funeral Seraph");
                  case 2 -> Component.literal("Genesis Hydra");
                  default -> Component.literal("Chimera");
               });
               entity.setCustomNameVisible(true);
               player.level().addFreshEntity(entity);
               root.put("archive", new ListTag());
            } catch (Throwable ignored) {
               try {
                  entity.discard();
               } catch (Throwable var14) {
               }
            }
         }
      }
   }

   private static double totalHp(ListTag archive) {
      double total = 0.0;

      for (int i = 0; i < archive.size(); i++) {
         total += archive.getCompound(i).getDouble("hp");
      }

      return total;
   }

   private static double totalEp(ListTag archive) {
      double total = 0.0;

      for (int i = 0; i < archive.size(); i++) {
         total += archive.getCompound(i).getDouble("ep");
      }

      return total;
   }

   private static int totalCount(ListTag archive) {
      int total = 0;

      for (int i = 0; i < archive.size(); i++) {
         total += Math.max(1, archive.getCompound(i).getInt("count"));
      }

      return total;
   }

   @NotNull
   public Type<NaberiusActionPayload> type() {
      return TYPE;
   }

   public enum Action {
      TOGGLE_SELECTED,
      REDUCE_COUNT,
      DELETE_SELECTED,
      MANIFEST_SELECTED,
      MANIFEST_ALL_SELECTED,
      CLEAR_ACTIVE_SUMMONS,
      CRAFT_CHIMERA;
   }
}
