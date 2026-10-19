package gardensofthedead.neoforge.platform;

import dev.architectury.registry.registries.RegistrySupplier;
import gardensofthedead.neoforge.block.StrippableLogBlock;
import gardensofthedead.platform.PlatformHelper;
import gardensofthedead.registry.ModBlockProperties;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.common.ItemAbilities;

public class NeoForgePlatformHelper implements PlatformHelper {
   @Override
   public boolean isSword(ItemStack stack) {
      return stack.canPerformAction(ItemAbilities.SWORD_DIG);
   }

   @Override
   public boolean isShears(ItemStack stack) {
      return stack.canPerformAction(ItemAbilities.SHEARS_DIG);
   }

   @Override
   public FlowerPotBlock createFlowerPot(RegistrySupplier<? extends Block> plant, Properties properties) {
      FlowerPotBlock flowerPot = (FlowerPotBlock)Blocks.FLOWER_POT;
      FlowerPotBlock result = new FlowerPotBlock(() -> flowerPot, plant, properties);
      flowerPot.addPlant(plant.getId(), () -> result);
      return result;
   }

   @Override
   public RotatedPillarBlock createStrippableBlock(Supplier<? extends Block> strippedBlock, Properties properties) {
      return new StrippableLogBlock(properties, strippedBlock);
   }

   @Override
   public Properties copyBlockPropertiesWithLoot(Properties properties, ResourceLocation id) {
      return ModBlockProperties.copy(properties).lootFrom(() -> (Block)BuiltInRegistries.BLOCK.get(id));
   }
}
