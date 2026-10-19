package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.phys.Vec3;

public final class RonovaSoulHandler {
   public static final String SALVATION_ACTIVE = "RonovaSalvationLordActive";

   private RonovaSoulHandler() {
   }

   public static void toggleSalvationLord(ManasSkillInstance instance, LivingEntity entity) {
      tryRescue(entity, true);
   }

   public static void create(ServerLevel level, LivingEntity owner, LivingEntity dead) {
      if ((Boolean)RonovaConfigAccess.cfg().salvationEnabled.get()) {
         if (!(dead instanceof Player)) {
            CompoundTag tag = new CompoundTag();
            dead.saveWithoutId(tag);
            tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(dead.getType()).toString());
            tag.remove("UUID");
            tag.remove("DeathTime");
            tag.remove("HurtTime");
            tag.remove("HurtByTimestamp");
            tag.remove("SleepingX");
            tag.remove("SleepingY");
            tag.remove("SleepingZ");
            tag.putFloat("Health", Math.max(1.0F, dead.getMaxHealth() * ((Double)RonovaConfigAccess.cfg().salvationReviveHealthRatio.get()).floatValue()));
            RonovaSoulHandler.Data data = data(level);
            data.souls
               .add(
                  new RonovaSoulHandler.Soul(
                     owner.getUUID(),
                     tag,
                     dead.position(),
                     level.getGameTime() + ((Integer)RonovaConfigAccess.cfg().salvationSoulTicks.get()).intValue(),
                     Math.max(1.0, EnergyHelper.getMaxEP(dead))
                  )
               );
            data.setDirty();
            if (owner instanceof Player player) {
               player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.soul_lingers").withStyle(ChatFormatting.DARK_AQUA), true);
            }
         }
      }
   }

   public static void tick(ServerLevel level) {
      RonovaSoulHandler.Data data = data(level);
      long now = level.getGameTime();
      if (data.souls.removeIf(soulx -> soulx.expireTick <= now)) {
         data.setDirty();
      }

      for (RonovaSoulHandler.Soul soul : data.souls) {
         level.sendParticles(ParticleTypes.SOUL, soul.pos.x, soul.pos.y + 1.0, soul.pos.z, 10, 0.35, 0.65, 0.35, 0.01);
         level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, soul.pos.x, soul.pos.y + 0.2, soul.pos.z, 3, 0.2, 0.2, 0.2, 0.005);
      }
   }

   public static boolean tryRescue(LivingEntity owner, boolean messageFail) {
      if (!(owner.level() instanceof ServerLevel level)) {
         return false;
      } else {
         RonovaSoulHandler.Data var21 = data(level);
         Vec3 eye = owner.getEyePosition();
         Vec3 look = owner.getLookAngle().normalize();
         RonovaSoulHandler.Soul best = null;
         double bestScore = -1.0;

         for (RonovaSoulHandler.Soul soul : var21.souls) {
            if (soul.owner.equals(owner.getUUID())) {
               Vec3 soulPos = soul.pos.add(0.0, 1.0, 0.0);
               Vec3 to = soulPos.subtract(eye);
               double distance = to.length();
               if (!(distance > (Double)RonovaConfigAccess.cfg().salvationRescueReach.get())) {
                  double dot = distance <= 0.001 ? 1.0 : look.dot(to.normalize());
                  boolean closeEnough = owner.position().distanceTo(soul.pos) <= 6.0;
                  boolean lookingEnough = dot >= 0.5;
                  if (closeEnough || lookingEnough) {
                     double score = (closeEnough ? 3.0 : 0.0) + dot - distance * 0.01;
                     if (score > bestScore) {
                        bestScore = score;
                        best = soul;
                     }
                  }
               }
            }
         }

         if (best == null) {
            if (messageFail && owner instanceof Player player) {
               player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.no_soul").withStyle(ChatFormatting.GRAY), true);
            }

            return false;
         } else if (!best.entityTag.contains("id")) {
            var21.souls.remove(best);
            var21.setDirty();
            if (messageFail && owner instanceof Player player) {
               player.displayClientMessage(
                  Component.literal("This soul is from the old broken save format. Let a new soul spawn.").withStyle(ChatFormatting.RED), true
               );
            }

            return false;
         } else {
            double ownerMaxEp = EnergyHelper.getMaxEP(owner);
            if (ownerMaxEp < best.epCost) {
               if (messageFail && owner instanceof Player player) {
                  player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.not_enough_ep").withStyle(ChatFormatting.RED), true);
               }

               return false;
            } else {
               CompoundTag entityTag = best.entityTag.copy();
               if (EntityType.loadEntityRecursive(entityTag, level, entity -> entity) instanceof LivingEntity living) {
                  living.setPos(best.pos.x, best.pos.y, best.pos.z);
                  living.setHealth(Math.max(1.0F, living.getMaxHealth() * ((Double)RonovaConfigAccess.cfg().salvationReviveHealthRatio.get()).floatValue()));
                  if (!level.addFreshEntity(living)) {
                     if (messageFail && owner instanceof Player player) {
                        player.displayClientMessage(
                           Component.literal("Soul restoration failed because the entity could not spawn.").withStyle(ChatFormatting.RED), true
                        );
                     }

                     return false;
                  } else {
                     EnergyHelper.setBaseMaxEP(owner, Math.max(1.0, EnergyHelper.getBaseMaxEP(owner) - best.epCost));
                     var21.souls.remove(best);
                     var21.setDirty();
                     level.sendParticles(
                        ParticleTypes.SOUL_FIRE_FLAME, living.getX(), living.getY() + living.getBbHeight() * 0.5, living.getZ(), 40, 0.5, 0.8, 0.5, 0.02
                     );
                     if (owner instanceof Player player) {
                        player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.soul_restored").withStyle(ChatFormatting.AQUA), true);
                     }

                     return true;
                  }
               } else {
                  var21.souls.remove(best);
                  var21.setDirty();
                  if (messageFail && owner instanceof Player player) {
                     player.displayClientMessage(
                        Component.literal("Soul restoration failed because the entity could not be loaded.").withStyle(ChatFormatting.RED), true
                     );
                  }

                  return false;
               }
            }
         }
      }
   }

   private static RonovaSoulHandler.Data data(ServerLevel level) {
      return (RonovaSoulHandler.Data)level.getDataStorage()
         .computeIfAbsent(new Factory(RonovaSoulHandler.Data::new, RonovaSoulHandler.Data::load, null), "tensuramoreskills_ronova_souls");
   }

   public static final class Data extends SavedData {
      private final List<RonovaSoulHandler.Soul> souls = new ArrayList<>();

      public static RonovaSoulHandler.Data load(CompoundTag tag, Provider provider) {
         RonovaSoulHandler.Data data = new RonovaSoulHandler.Data();
         ListTag list = tag.getList("Souls", 10);

         for (int i = 0; i < list.size(); i++) {
            CompoundTag s = list.getCompound(i);
            data.souls
               .add(
                  new RonovaSoulHandler.Soul(
                     s.getUUID("Owner"),
                     s.getCompound("Entity"),
                     new Vec3(s.getDouble("X"), s.getDouble("Y"), s.getDouble("Z")),
                     s.getLong("Expire"),
                     s.getDouble("EpCost")
                  )
               );
         }

         return data;
      }

      public CompoundTag save(CompoundTag tag, Provider provider) {
         ListTag list = new ListTag();

         for (RonovaSoulHandler.Soul soul : this.souls) {
            CompoundTag s = new CompoundTag();
            s.putUUID("Owner", soul.owner);
            s.put("Entity", soul.entityTag);
            s.putDouble("X", soul.pos.x);
            s.putDouble("Y", soul.pos.y);
            s.putDouble("Z", soul.pos.z);
            s.putLong("Expire", soul.expireTick);
            s.putDouble("EpCost", soul.epCost);
            list.add(s);
         }

         tag.put("Souls", list);
         return tag;
      }
   }

   public record Soul(UUID owner, CompoundTag entityTag, Vec3 pos, long expireTick, double epCost) {
   }
}
