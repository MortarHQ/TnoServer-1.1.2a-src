package com.mortal_cultivation.mixin;

import com.mortal_cultivation.registry.skill.CultivationSkills;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResetScrollItem.class)
public class MixinResetScrollItem {
   private static final ResourceLocation SCALE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "size_transformation_scale");

   @Inject(method = "resetRace", at = @At("RETURN"), remap = false)
   private static void onResetRace(ServerPlayer player, CallbackInfo ci) {
      SpiritRootData.clearRoot(player, true);
      resetPlayerScale(player);
   }

   @Inject(method = "resetEverything", at = @At("RETURN"), remap = false)
   private static void onResetEverything(ServerPlayer player, CallbackInfo ci) {
      SpiritRootData.clearRoot(player, true);
      resetPlayerScale(player);
   }

   private static void resetPlayerScale(ServerPlayer player) {
      AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
      if (scaleAttribute != null) {
         scaleAttribute.removeModifier(SCALE_MODIFIER_ID);
      }

      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get());
      if (optional.isPresent()) {
         ManasSkillInstance instance = optional.get();
         CompoundTag tag = instance.getOrCreateTag();
         float raceDefaultSize = getRaceDefaultSize(player);
         tag.putDouble("size", raceDefaultSize);
         instance.markDirty();
      }

      player.refreshDimensions();
   }

   private static float getRaceDefaultSize(ServerPlayer player) {
      AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
      return scaleAttribute != null ? (float)(scaleAttribute.getBaseValue() * 5.0) : 5.0F;
   }
}
