package net.unusual.block_factorys_bosses.item;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.block.BigChainBlock;
import net.unusual.block_factorys_bosses.entity.decoration.AnchorEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AnchorEntityItem extends Item {
   private final Supplier<? extends EntityType<? extends AnchorEntity>> typeSupplier;

   public AnchorEntityItem(Supplier<? extends EntityType<? extends AnchorEntity>> typeSupplier, Properties properties) {
      super(properties);
      this.typeSupplier = typeSupplier;
   }

   public InteractionResult useOn(UseOnContext context) {
      BlockPos pos = context.getClickedPos();
      Direction face = context.getClickedFace();
      Player player = context.getPlayer();
      ItemStack stack = context.getItemInHand();
      Level level = context.getLevel();
      if (!this.mayPlace(player, face, stack, pos, level)) {
         return InteractionResult.FAIL;
      }

      AnchorEntity entity = (AnchorEntity)this.typeSupplier.get().create(level);
      if (entity == null) {
         return InteractionResult.CONSUME;
      }

      Vec3 center = Vec3.atBottomCenterOf(pos);
      Direction viewDirection = player != null ? Direction.fromYRot(player.getYRot()) : Direction.NORTH;
      if (player != null) {
         entity.setYRot(viewDirection.getOpposite().toYRot());
      }

      Vec3 vec3;
      if (face == Direction.UP) {
         vec3 = new Vec3(center.x, context.getClickLocation().y, center.z);
      } else if (level.getBlockState(pos).is((Block)BossesRiseBlocks.BIG_CHAIN.get())) {
         vec3 = new Vec3(center.x, center.y - entity.getBbHeight() - 0.0, center.z);
         if (player != null && viewDirection.getAxis() != (level.getBlockState(pos).getValue(BigChainBlock.ROTATION) == 2 ? Axis.X : Axis.Z)) {
            entity.setYRot(viewDirection.getOpposite().getClockWise().toYRot());
         }
      } else {
         vec3 = new Vec3(center.x, context.getClickLocation().y - entity.getBbHeight(), center.z);
      }

      entity.moveTo(vec3);
      entity.setAttachDiff(pos.getY() - entity.blockPosition().getY());
      CustomData customdata = (CustomData)stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
      if (!customdata.isEmpty()) {
         EntityType.updateCustomEntityTag(level, player, entity, customdata);
      }

      if (entity.survives()) {
         if (!level.isClientSide) {
            entity.playSound(SoundType.WOOD.getPlaceSound(), 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
            level.addFreshEntity(entity);
         }

         stack.shrink(1);
         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.CONSUME;
      }
   }

   protected boolean mayPlace(@Nullable Player player, Direction face, ItemStack stack, BlockPos pos, Level level) {
      if (player != null && !player.mayUseItemAt(pos, face, stack)) {
         return false;
      }

      BlockState state = level.getBlockState(pos);
      return state.is((Block)BossesRiseBlocks.BIG_CHAIN.get()) && level.getBlockState(pos.below()).canBeReplaced()
         ? true
         : face.getAxis().isVertical() && level.getBlockState(pos.relative(face)).canBeReplaced();
   }
}
