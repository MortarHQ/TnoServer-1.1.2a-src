package com.github.wal_bos.moreskills.ability.skill.shades;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AnimatedShadeSkill extends Skill {
   private final ResourceLocation[] frames;
   private final long frameDurationMs;
   private long lastFrameTime;
   private int currentFrame;

   protected AnimatedShadeSkill(SkillType type, ResourceLocation[] frames, long frameDurationMs) {
      super(type);
      this.frames = frames;
      this.frameDurationMs = frameDurationMs;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      if (this.frames != null && this.frames.length != 0) {
         long now = System.currentTimeMillis();
         if (now - this.lastFrameTime >= this.frameDurationMs) {
            this.currentFrame++;
            if (this.currentFrame >= this.frames.length) {
               this.currentFrame = 0;
            }

            this.lastFrameTime = now;
         }

         return this.frames[this.currentFrame];
      } else {
         return null;
      }
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }
}
