package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityWoodStand;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.blocks.tiles.render.RenderWoodStand;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.recipes.TreeRitualRecipe;
import de.ellpeck.naturesaura.reg.IAxeBreakable;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.ITESRProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import org.apache.commons.lang3.mutable.MutableObject;

public class BlockWoodStand extends BlockContainerImpl implements ITESRProvider<BlockEntityWoodStand>, ICustomBlockState, IAxeBreakable {
   private static final VoxelShape SHAPE = Shapes.create(0.1875, 0.0, 0.1875, 0.8125, 0.8125, 0.8125);

   public BlockWoodStand() {
      super("wood_stand", BlockEntityWoodStand.class, Properties.of().strength(1.5F).sound(SoundType.WOOD));
      NeoForge.EVENT_BUS.register(this);
   }

   @Override
   protected boolean hasWaterlogging() {
      return true;
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   @SubscribeEvent
   public void onTreeGrow(BlockGrowFeatureEvent event) {
      LevelAccessor level = event.getLevel();
      BlockPos pos = event.getPos();
      if (!level.isClientSide() && level instanceof Level && Multiblocks.TREE_RITUAL.isComplete((Level)level, pos)) {
         ItemStack saplingStack = new ItemStack(level.getBlockState(pos).getBlock());
         if (!saplingStack.isEmpty()) {
            for (RecipeHolder<TreeRitualRecipe> recipe : ((Level)level).getRecipeManager().getRecipesFor(ModRecipes.TREE_RITUAL_TYPE, null, (Level)level)) {
               if (((TreeRitualRecipe)recipe.value()).saplingType.test(saplingStack)) {
                  List<Ingredient> required = new ArrayList<>(((TreeRitualRecipe)recipe.value()).ingredients);
                  MutableObject<BlockEntityWoodStand> toPick = new MutableObject();
                  boolean fine = Multiblocks.TREE_RITUAL.forEach(pos, 'W', (tilePos, matcher) -> {
                     if (level.getBlockEntity(tilePos) instanceof BlockEntityWoodStand stand) {
                        ItemStack stack = stand.items.getStackInSlot(0);
                        if (!stack.isEmpty()) {
                           for (int i = required.size() - 1; i >= 0; i--) {
                              Ingredient req = required.get(i);
                              if (req.test(stack)) {
                                 required.remove(i);
                                 if (toPick.getValue() == null) {
                                    toPick.setValue(stand);
                                 }

                                 return true;
                              }
                           }

                           return false;
                        }
                     }

                     return true;
                  });
                  if (fine && required.isEmpty()) {
                     ((BlockEntityWoodStand)toPick.getValue()).setRitual(pos, recipe);
                     break;
                  }
               }
            }
         }
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return Helper.putStackOnTile(player, hand, pos, 0, true);
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }

   @Override
   public void registerTESR() {
      BlockEntityRenderers.register(ModBlockEntities.WOOD_STAND, RenderWoodStand::new);
   }
}
