package net.unusual.block_factorys_bosses.item;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.unusual.block_factorys_bosses.client.renderer.CrateItemRenderer;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CrateEntityItem extends Item {
   private final Supplier<? extends EntityType<? extends CratePileEntity>> typeSupplier;

   public CrateEntityItem(Supplier<? extends EntityType<? extends CratePileEntity>> typeSupplier, Properties properties) {
      super(properties);
      this.typeSupplier = typeSupplier;
   }

   public EntityType<? extends CratePileEntity> getEntityType() {
      return (EntityType<? extends CratePileEntity>)this.typeSupplier.get();
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         private BlockEntityWithoutLevelRenderer renderer;

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               Minecraft mc = Minecraft.getInstance();
               this.renderer = new CrateItemRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
            }

            return this.renderer;
         }
      });
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

      CratePileEntity entity = (CratePileEntity)this.typeSupplier.get().create(level);
      if (entity == null) {
         return InteractionResult.CONSUME;
      }

      Vec3 center = Vec3.atBottomCenterOf(pos);
      Direction viewDirection = player != null ? Direction.fromYRot(player.getYRot()) : Direction.NORTH;
      Vec3 vec3;
      if (face == Direction.UP) {
         vec3 = new Vec3(center.x, context.getClickLocation().y, center.z);
      } else {
         vec3 = new Vec3(center.x, context.getClickLocation().y - entity.getBbHeight(), center.z);
      }

      entity.moveTo(vec3);
      entity.setAttachDiff(pos.getY() - entity.blockPosition().getY());
      if (player != null) {
         entity.setYRot(viewDirection.getOpposite().toYRot());
      }

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
      return player == null || player.mayUseItemAt(pos, face, stack);
   }
}
