package com.github.b4ndithelps.tennogamenolife.client;

import com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate.DragonRoarChargeLock;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
public final class DragonRoarChargeLockClient {
   private DragonRoarChargeLockClient() {
   }

   @SubscribeEvent
   public static void onMovementInput(MovementInputUpdateEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         if (getLockedTarget(player) != null) {
            Input input = event.getInput();
            input.leftImpulse = 0.0F;
            input.forwardImpulse = 0.0F;
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.jumping = false;
            input.shiftKeyDown = false;
            player.setSprinting(false);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         DragonRoarChargeLockClient.LockedTarget locked = getLockedTarget(player);
         if (locked != null) {
            DragonRoarChargeLock.stopHorizontalMovement(player);
            if (locked.lockAim()) {
               DragonRoarChargeLock.applyAim(player, locked.target());
            }
         }
      }
   }

   @SubscribeEvent
   public static void beforeRenderFrame(Pre event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         DragonRoarChargeLockClient.LockedTarget locked = getLockedTarget(player);
         if (locked != null && locked.lockAim()) {
            DragonRoarChargeLock.applyAim(player, locked.target());
         }
      }
   }

   private static DragonRoarChargeLockClient.LockedTarget getLockedTarget(LocalPlayer player) {
      if (!player.isAlive()) {
         return null;
      }

      ManasSkillInstance farCry = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)TenreincarnationSkill.FAR_CRY.get()).orElse(null);
      Vec3 farCryTarget = readTarget(farCry, "FarCryLocked", "FarCryX", "FarCryY", "FarCryZ");
      if (farCryTarget != null) {
         return new DragonRoarChargeLockClient.LockedTarget(farCryTarget, true);
      }

      ManasSkillInstance godlySmite = (ManasSkillInstance)SkillAPI.getSkillsFrom(player)
         .getSkill((ManasSkill)TenreincarnationSkill.GODLY_SMITE.get())
         .orElse(null);
      Vec3 godlySmiteTarget = readTarget(godlySmite, "GodlySmiteLocked", "GodlySmiteX", "GodlySmiteY", "GodlySmiteZ");
      if (godlySmiteTarget != null) {
         return new DragonRoarChargeLockClient.LockedTarget(godlySmiteTarget, false);
      }

      ManasSkillInstance reisen = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)TenreincarnationSkill.REISEN.get()).orElse(null);
      Vec3 reisenTarget = readTarget(reisen, "DragonRoarLocked", "dragonRoarX", "dragonRoarY", "dragonRoarZ");
      return reisenTarget == null ? null : new DragonRoarChargeLockClient.LockedTarget(reisenTarget, true);
   }

   private static Vec3 readTarget(ManasSkillInstance instance, String lockedKey, String xKey, String yKey, String zKey) {
      if (instance == null) {
         return null;
      }

      CompoundTag tag = instance.getTag();
      return tag != null && tag.getBoolean(lockedKey) ? new Vec3(tag.getDouble(xKey), tag.getDouble(yKey), tag.getDouble(zKey)) : null;
   }

   private record LockedTarget(Vec3 target, boolean lockAim) {
   }
}
