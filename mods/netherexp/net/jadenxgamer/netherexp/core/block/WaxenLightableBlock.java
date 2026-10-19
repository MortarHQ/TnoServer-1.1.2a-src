package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class WaxenLightableBlock extends Block {
   public static final EnumProperty<WaxenLightableBlock.WaxenLit> LIT = EnumProperty.create("lit", WaxenLightableBlock.WaxenLit.class);
   private final Supplier<SimpleParticleType> particle;

   public WaxenLightableBlock(Supplier<SimpleParticleType> particle, Properties properties) {
      super(properties);
      this.particle = particle;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LIT, WaxenLightableBlock.WaxenLit.FALSE));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (state.getValue(LIT) == WaxenLightableBlock.WaxenLit.FALSE) {
         boolean changeState = false;
         boolean waxen = false;
         if (stack.is(Items.FLINT_AND_STEEL)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (!player.getAbilities().instabuild) {
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }

            changeState = true;
         } else if (stack.is(Items.FIRE_CHARGE)) {
            level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            changeState = true;
         } else if (stack.is(JNEItems.TREACHEROUS_WAX.get())) {
            level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
            changeState = true;
            waxen = true;
         }

         if (changeState) {
            level.setBlock(pos, (BlockState)state.setValue(LIT, waxen ? WaxenLightableBlock.WaxenLit.WAXEN : WaxenLightableBlock.WaxenLit.TRUE), 3);
            if (this.particle.get() != null) {
               ParticleHelper.surroundBlockParticle(level, pos, waxen ? JNEParticleTypes.TREACHEROUS_FLAME.get() : this.particle.get());
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      }

      if (state.getValue(LIT) == WaxenLightableBlock.WaxenLit.TRUE && stack.is(JNEItems.TREACHEROUS_WAX.get())) {
         level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.setBlock(pos, (BlockState)state.setValue(LIT, WaxenLightableBlock.WaxenLit.WAXEN), 3);
         if (this.particle.get() != null) {
            ParticleHelper.surroundBlockParticle(level, pos, JNEParticleTypes.TREACHEROUS_FLAME.get());
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   public static int stateForProperty(BlockState state) {
      return switch ((WaxenLightableBlock.WaxenLit)state.getValue(LIT)) {
         case FALSE -> 0;
         case TRUE -> 4;
         case WAXEN -> 3;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT});
   }

   public enum WaxenLit implements StringRepresentable {
      FALSE("false"),
      TRUE("true"),
      WAXEN("waxen");

      private final String name;

      WaxenLit(String name) {
         this.name = name;
      }

      @NotNull
      public String getSerializedName() {
         return this.name;
      }
   }
}
