package com.github.dominickwd04.traddon.ability.skill.intrinsic;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import com.github.dominickwd04.traddon.entity.magic.field.cloud.CrystalDustCloud;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CrystalDustSkill extends Skill {
   private static final TrAddonSkillConfig.CrystalDust CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).CrystalDust;

   public CrystalDustSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/intrinsic/blood_mist.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public boolean canInteractSkill(ManasSkillInstance instance, LivingEntity entity) {
      return TensuraStorages.getExistenceFrom(entity).isSpiritualForm() ? false : super.canInteractSkill(instance, entity);
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, CONFIG.dustRange);
      BlockPos pos = result.getBlockPos();
      instance.addMasteryPoint(entity);
      CrystalDustCloud dust = new CrystalDustCloud(entity.level(), entity);
      dust.setDamage(CONFIG.dustDamage);
      dust.setSize(CONFIG.dustRadius);
      dust.setHeight(1.0F);
      dust.setSkill(entity, instance, this, mode);
      dust.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
      level.addFreshEntity(dust);
      entity.swing(InteractionHand.MAIN_HAND, true);
      instance.setCoolDown(CONFIG.dustCooldown, mode);
      instance.markDirty();
      level.playSound(
         (Player)null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_EARTH.get(), SoundSource.PLAYERS, 1.0F, 1.0F
      );
   }
}
