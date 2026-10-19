package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.net.msg.AvalonDivineExcaliburVfxPayload;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.enchantment.EngravingHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;

public final class AvalonDivineExcaliburHandler implements AvalonModeHandler {
   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.divine_excalibur";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         ItemStack held = entity.getMainHandItem();
         if (!held.is((Item)TenaddexItems.TRUE_EXCALIBUR.get())) {
            entity.sendSystemMessage(Component.literal("You must hold True Excalibur.").withStyle(ChatFormatting.RED));
         } else {
            ItemStack evolved = new ItemStack(((Item)TenaddexItems.DIVINE_EXCALIBUR.get()).builtInRegistryHolder(), held.getCount(), held.getComponentsPatch());
            Registry<Enchantment> enchantments = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            evolved.enchant(enchantments.getHolderOrThrow(TensuraEnchantments.HOLY_WEAPON), 2);
            evolved.enchant(enchantments.getHolderOrThrow(TensuraEnchantments.HOLY_COAT), 3);
            evolved.enchant(enchantments.getHolderOrThrow(TensuraEnchantments.TRANSCENDENCE), 3);
            EngravingHelper.grantRandomEngraving(entity, evolved, EnergyHelper.getMaxEP(entity));
            entity.setItemInHand(InteractionHand.MAIN_HAND, evolved);
            Vec3 start = RoyalSkillSupport.eye(entity);
            AvalonDivineExcaliburVfxPayload.send(level, entity, null, start, start.add(0.0, 18.0, 0.0), 16.0F, 180, 0);
            level.playSound(null, entity.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 5.0F, 1.8F);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.sendSystemMessage(
               Component.literal("True Excalibur has ascended into Divine Excalibur.")
                  .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
            );
            instance.setCoolDown(160, mode);
            instance.addMasteryPoint(entity);
         }
      }
   }
}
