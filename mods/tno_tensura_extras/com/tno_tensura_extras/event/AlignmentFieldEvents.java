package com.tno_tensura_extras.event;

import com.tno_tensura_extras.magic.AlignmentFieldMagic;
import com.tno_tensura_extras.registry.ExtrasSkills;
import com.tno_tensura_extras.util.AlignmentFieldManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class AlignmentFieldEvents {
   private static final int UNLOCK_CHECK_INTERVAL = 100;

   private AlignmentFieldEvents() {
   }

   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.tickCount % 100 == 0) {
            ManasSkill alignmentField = (ManasSkill)ExtrasSkills.ALIGNMENT_FIELD.get();
            if (!SkillAPI.getSkillsFrom(serverPlayer).getSkill(alignmentField).isPresent()) {
               if (SkillUtils.isSkillMastered(serverPlayer, (ManasSkill)AspectualMagics.ANTI_MAGIC_AREA.get())) {
                  TensuraSkillInstance instance = new TensuraSkillInstance(alignmentField);
                  instance.setMastery(((AlignmentFieldMagic)ExtrasSkills.ALIGNMENT_FIELD.get()).getAcquirementMastery(serverPlayer));
                  SkillHelper.learnSkill(serverPlayer, instance);
               }
            }
         }
      }
   }

   public static void onEntityTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Post event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide() && !entity.isRemoved() && !entity.isSpectator()) {
         if (!(entity instanceof Player) && !(entity instanceof Projectile)) {
            Vec3 current = entity.getBoundingBox().getCenter();
            if (AlignmentFieldManager.isInsideField(entity.level(), current)) {
               Vec3 previousPosition = new Vec3(entity.xo, entity.yo, entity.zo);
               Vec3 previousCentre = current.subtract(entity.position().subtract(previousPosition));
               if (!AlignmentFieldManager.isInsideField(entity.level(), previousCentre)) {
                  entity.teleportTo(previousPosition.x, previousPosition.y, previousPosition.z);
                  entity.setDeltaMovement(Vec3.ZERO);
                  entity.hurtMarked = true;
               }
            }
         }
      }
   }

   public static void onServerStopped(ServerStoppedEvent event) {
      AlignmentFieldManager.clear();
   }
}
