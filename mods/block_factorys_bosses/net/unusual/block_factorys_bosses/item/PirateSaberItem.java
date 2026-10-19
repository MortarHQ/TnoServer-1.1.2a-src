package net.unusual.block_factorys_bosses.item;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.CrossbowPirateEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateRookEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PirateSaberItem extends SwordItem {
   public PirateSaberItem() {
      super(Tiers.IRON, new Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 9, -2.4F)).durability(2000));
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
      if (living instanceof Player player) {
         if (!level.isClientSide) {
            CrossbowPirateEntity crossbowPirate = new CrossbowPirateEntity((EntityType<CrossbowPirateEntity>)BossesRiseEntities.CROSSBOW_PIRATE.get(), level);
            crossbowPirate.setOwnerUUID(player.getUUID());
            crossbowPirate.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
            crossbowPirate.moveTo(
               player.position().add(player.calculateViewVector(0.0F, player.getYRot()).yRot((float) (Math.PI / 2))), player.getYRot(), player.getXRot()
            );
            level.addFreshEntity(crossbowPirate);
            crossbowPirate.triggerAnim("main_controller", "spawn");
            crossbowPirate.skipDropExperience();
            PirateRookEntity pirateRook = new PirateRookEntity((EntityType<PirateRookEntity>)BossesRiseEntities.PIRATE_ROOK.get(), level);
            pirateRook.setOwnerUUID(player.getUUID());
            pirateRook.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)BossesRiseItems.PIRATE_SABER.get()));
            pirateRook.moveTo(
               player.position().add(player.calculateViewVector(0.0F, player.getYRot()).yRot((float) (-Math.PI / 2))), player.getYRot(), player.getXRot()
            );
            level.addFreshEntity(pirateRook);
            pirateRook.triggerAnim("main_controller", "spawn");
            pirateRook.skipDropExperience();
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         player.getCooldowns().addCooldown(stack.getItem(), 80);
         if (!player.hasInfiniteMaterials()) {
            stack.hurtAndBreak(10, player, player.getEquipmentSlotForItem(stack));
         }
      }

      return stack;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 14;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return ItemUtils.startUsingInstantly(level, player, hand);
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }
}
