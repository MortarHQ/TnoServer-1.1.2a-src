package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.net.msg.AvalonLawSanctuaryVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

final class AvalonLawOfSanctuaryHandler implements AvalonLawHandler {
   private static final ResourceLocation ABSOLUTE_GUARD = RoyalSkillSupport.id("avalon_law_absolute_guard");
   private static final ResourceLocation PHYSICAL_GUARD = RoyalSkillSupport.id("avalon_law_physical_guard");
   private static final ResourceLocation MAGIC_GUARD = RoyalSkillSupport.id("avalon_law_magic_guard");

   @Override
   public String name() {
      return "Law of Sanctuary";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawSanctuaryVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void onActivate(ManasSkillInstance instance, LivingEntity owner) {
      apply(owner);
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 20 == 0) {
         apply(owner);
      }
   }

   private static void apply(LivingEntity owner) {
      AttributeHelper.addPermanentAttribute(
         owner, TensuraAttributes.MULTILAYER_BARRIER, ABSOLUTE_GUARD, Math.max(1000000.0, owner.getMaxHealth() * 10000.0), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(owner, TensuraAttributes.PHYSICAL_BARRIER, PHYSICAL_GUARD, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(owner, TensuraAttributes.MAGIC_BARRIER, MAGIC_GUARD, 1.0, Operation.ADD_VALUE);
   }

   @Override
   public void onDeactivate(ManasSkillInstance instance, LivingEntity owner) {
      AttributeHelper.removeAttribute(owner, TensuraAttributes.MULTILAYER_BARRIER, ABSOLUTE_GUARD);
      AttributeHelper.removeAttribute(owner, TensuraAttributes.PHYSICAL_BARRIER, PHYSICAL_GUARD);
      AttributeHelper.removeAttribute(owner, TensuraAttributes.MAGIC_BARRIER, MAGIC_GUARD);
   }
}
