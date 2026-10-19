package it.hurts.sskirillss.relics.items.relics.ring;

import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class LeafyRingItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("hide")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.1, 0.35)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(StyleData.builder().tooltip(TooltipData.builder().borderTop(-15315200).borderBottom(-15315200).textured(true).build()).build())
         .leveling(new LevelingData(100, 10, 200))
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         Level var8 = player.getCommandSenderWorld();
         int progress = this.getCurrentProgress(stack);
         boolean hiding = this.isHiding(stack);
         Vec3 pos = player.getBoundingBox().getBottomCenter().add(0.0, player.getBbHeight(), 0.0);
         if (player.isShiftKeyDown()
            && var8.getBlockState(new BlockPos((int)Math.floor(pos.x()), (int)Math.floor(pos.y()), (int)Math.floor(pos.z()))).getBlock() instanceof BushBlock) {
            if (!hiding) {
               this.setHiding(stack, true);
            }

            if (progress < this.getMaxProgress()) {
               this.addCurrentProgress(stack, 1);
            }

            player.addEffect(new MobEffectInstance(EffectRegistry.VANISHING, 5, 0, false, false));
         } else {
            if (hiding) {
               this.setHiding(stack, false);
            }

            if (progress > 0) {
               this.addCurrentProgress(stack, -1);
            }
         }
      }
   }

   public boolean isHiding(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
   }

   public void setHiding(ItemStack stack, boolean hiding) {
      stack.set(DataComponentRegistry.TOGGLED, hiding);
   }

   public int getCurrentProgress(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.PROGRESS, 0);
   }

   public void setCurrentProgress(ItemStack stack, int progress) {
      stack.set(DataComponentRegistry.PROGRESS, Math.clamp(progress, 0, this.getMaxProgress()));
   }

   public void addCurrentProgress(ItemStack stack, int progress) {
      this.setCurrentProgress(stack, this.getCurrentProgress(stack) + progress);
   }

   public int getMaxProgress() {
      return 10;
   }
}
