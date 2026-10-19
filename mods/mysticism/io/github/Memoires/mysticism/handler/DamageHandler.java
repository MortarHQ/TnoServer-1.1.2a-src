package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.LightningEvent;
import dev.architectury.event.events.common.LightningEvent.Strike;
import io.github.Memoires.mysticism.entity.item.LightningEssenceItemEntity;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillDamageCalculationEvent;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DamageHandler {
   public static void init() {
      SkillEvents.SKILL_DAMAGE_PRE_CALCULATION.register((SkillDamageCalculationEvent)(storage, entity, source, amount) -> {
         if (source.getEntity() instanceof LivingEntity attacker && !TensuraDamageHelper.isTensuraMagic(source) && TensuraDamageHelper.isCold(source)) {
            amount.set((Float)amount.get() * (float)attacker.getAttributeValue(MysticismAttributes.COLD_BOOST));
         }

         return EventResult.pass();
      });
      LightningEvent.STRIKE.register((Strike)(bolt, level, vecPos, entities) -> {
         if (bolt.tickCount == 2) {
            float random = level.random.nextFloat();
            if (bolt instanceof LightningBolt && random < 0.1F) {
               BlockPos pos = bolt.blockPosition();
               ItemStack stack = new ItemStack(MysticismMobDropItems.LIGHTNING_ESSENCE);
               LightningEssenceItemEntity entity = new LightningEssenceItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
               level.addFreshEntity(entity);
            }
         }
      });
   }
}
