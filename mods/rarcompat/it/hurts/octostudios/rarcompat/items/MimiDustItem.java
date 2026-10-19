package it.hurts.octostudios.rarcompat.items;

import artifacts.entity.MimicEntity;
import artifacts.registry.ModEntityTypes;
import it.hurts.octostudios.rarcompat.handlers.MimicHandler;
import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.misc.ICreativeTabContent;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.Vec3;

public class MimiDustItem extends Item implements ICreativeTabContent {
   public MimiDustItem() {
      super(new Properties().rarity(Rarity.EPIC));
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (level.isClientSide()) {
         return InteractionResult.PASS;
      }

      BlockPos pos = context.getClickedPos();
      if (level.getBlockState(pos).getBlock() == Blocks.CHEST && level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
         int var10 = 0;

         for (int mimic = 0; mimic < chest.getContainerSize(); mimic++) {
            if (MimicHandler.MIMIFICABLE.contains(chest.getItem(mimic).getItem())) {
               var10 += chest.getItem(mimic).getCount();
               chest.setItem(mimic, ItemStack.EMPTY);
            }
         }

         if (var10 == 0) {
            return InteractionResult.FAIL;
         }

         context.getItemInHand().shrink(1);
         level.removeBlock(pos, true);
         MimicEntity mimic = new MimicEntity((EntityType)ModEntityTypes.MIMIC.get(), level);
         mimic.getPersistentData().putInt("relicCount", var10);
         mimic.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
         mimic.setTarget(context.getPlayer());
         level.addFreshEntity(mimic);
         float multiplier = (float)Math.pow(var10, 1.7);
         EntityUtils.applyAttribute(mimic, ItemStack.EMPTY, Attributes.ATTACK_DAMAGE, multiplier, Operation.ADD_VALUE);
         EntityUtils.applyAttribute(mimic, ItemStack.EMPTY, Attributes.MAX_HEALTH, multiplier, Operation.ADD_VALUE);
         mimic.setHealth(mimic.getMaxHealth());
         Vec3 center = pos.getCenter();
         RandomSource random = mimic.getRandom();
         level.playSound(
            null, mimic.blockPosition(), SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, mimic.getSoundSource(), 1.3F, 0.75F + mimic.getRandom().nextFloat()
         );
         ((ServerLevel)level)
            .sendParticles(
               ParticleUtils.constructSimpleSpark(new Color(200 + random.nextInt(50), 50 + random.nextInt(50), 200 + random.nextInt(50)), 0.25F, 60, 0.95F),
               center.x(),
               center.y(),
               center.z(),
               50,
               0.3,
               0.3,
               0.3,
               0.015
            );
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public void gatherCreativeTabContent(CreativeContentConstructor constructor) {
      constructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, new ItemStack[]{this.getDefaultInstance()});
   }

   public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.empty());
      tooltip.add(Component.translatable("tooltip.rarcompat.mimi_dust").withStyle(ChatFormatting.DARK_PURPLE));
   }
}
