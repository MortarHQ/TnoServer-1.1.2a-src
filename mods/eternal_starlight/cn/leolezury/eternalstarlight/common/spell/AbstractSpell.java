package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESSpellUtil;
import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSpell {
   private final AbstractSpell.Properties properties;

   public AbstractSpell.Properties spellProperties() {
      return this.properties;
   }

   public AbstractSpell(AbstractSpell.Properties properties) {
      this.properties = properties;
   }

   public boolean canCast(LivingEntity entity, boolean checkCrystal) {
      boolean crystalCheck = !checkCrystal
         || entity instanceof Player player && (player.hasInfiniteMaterials() || this.hasNeededCrystal(player.getInventory()));
      return crystalCheck && ESSpellUtil.getCooldown(entity, this) <= 0 && this.checkExtraConditions(entity);
   }

   public boolean hasNeededCrystal(Inventory inventory) {
      for (int i = 0; i < inventory.getContainerSize(); i++) {
         ItemStack stack = inventory.getItem(i);
         if (this.spellProperties().types().stream().anyMatch(t -> stack.is(t.getCrystalsTag()))) {
            return true;
         }
      }

      return false;
   }

   public boolean canContinueToCast(LivingEntity entity, int totalTicks) {
      return totalTicks <= this.properties.preparationTicks() + this.properties.spellTicks() && this.checkExtraConditionsToContinue(entity, totalTicks);
   }

   public void start(LivingEntity entity, boolean damageCrystal) {
      this.start(entity, 1, damageCrystal);
   }

   public void start(LivingEntity entity, int strength, boolean damageCrystal) {
      if (damageCrystal && entity instanceof Player player && !player.hasInfiniteMaterials()) {
         this.damageCrystal(player);
      }

      this.onStart(entity);
      if (!entity.level().isClientSide && entity instanceof SpellCaster) {
         ESDataAttachments.SPELL_CAST_DATA
            .setData(
               entity,
               new SpellCastData(
                  true,
                  this,
                  strength,
                  0,
                  ESDataAttachments.SPELL_SOURCE.getData(entity) instanceof SpellCastData.ItemSpellSource source && source.hand() == InteractionHand.OFF_HAND
               )
            );
      }
   }

   public void damageCrystal(Player player) {
      Inventory inventory = player.getInventory();

      for (int i = 0; i < inventory.getContainerSize(); i++) {
         ItemStack stack = inventory.getItem(i);
         if (this.spellProperties().types().stream().anyMatch(t -> stack.is(t.getCrystalsTag()))) {
            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            return;
         }
      }
   }

   public void tick(LivingEntity entity, int ticks) {
      if (ticks <= this.spellProperties().preparationTicks()) {
         this.onPreparationTick(entity, ticks);
      } else if (ticks <= this.spellProperties().preparationTicks() + this.spellProperties().spellTicks()) {
         this.onSpellTick(entity, ticks - this.spellProperties().preparationTicks());
      }

      if (ticks % 15 == 0 && entity instanceof Player player) {
         this.damageCrystal(player);
      }
   }

   public void stop(LivingEntity entity, int ticks) {
      this.onStop(entity, ticks);
      ESSpellUtil.setCooldown(entity, this, this.properties.cooldownTicks());
      if (!entity.level().isClientSide && entity instanceof SpellCaster) {
         ESDataAttachments.SPELL_CAST_DATA.removeData(entity);
      }
   }

   public abstract boolean checkExtraConditions(LivingEntity var1);

   public abstract boolean checkExtraConditionsToContinue(LivingEntity var1, int var2);

   public abstract void onPreparationTick(LivingEntity var1, int var2);

   public abstract void onSpellTick(LivingEntity var1, int var2);

   public abstract void onStart(LivingEntity var1);

   public abstract void onStop(LivingEntity var1, int var2);

   public record Properties(List<ManaType> types, int preparationTicks, int spellTicks, int cooldownTicks) {
      public int totalTicks() {
         return this.preparationTicks() + this.spellTicks();
      }
   }
}
