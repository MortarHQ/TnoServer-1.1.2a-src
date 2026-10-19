package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;
import java.util.Optional;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightningRodBlock.class, priority = 1002)
public abstract class LightningRodBlockOxidizableMixin extends RodBlock implements WeatheringCopper {
   public LightningRodBlockOxidizableMixin(Properties settings) {
      super(settings);
   }

   public Optional<BlockState> getNext(BlockState state) {
      return FriendsAndFoesOxidizable.getNext(state.getBlock()).map(block -> block.withPropertiesOf(state));
   }
}
