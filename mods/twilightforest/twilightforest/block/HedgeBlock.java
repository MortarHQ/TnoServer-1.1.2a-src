package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import twilightforest.util.entities.EntityUtil;

public class HedgeBlock extends Block {
   private static final VoxelShape HEDGE_BB = Shapes.create(new AABB(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0));
   private static final int DAMAGE = 3;

   public HedgeBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return HEDGE_BB;
   }

   @Nullable
   public PathType getBlockPathType(BlockState state, BlockGetter getter, BlockPos pos, @Nullable Mob mob) {
      return mob != null && this.shouldDamage(mob) ? PathType.DANGER_OTHER : null;
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (this.shouldDamage(entity)) {
         entity.hurt(level.damageSources().cactus(), 3.0F);
      }
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (this.shouldDamage(entity)) {
         entity.hurt(level.damageSources().cactus(), 3.0F);
      }
   }

   public void attack(BlockState state, Level level, BlockPos pos, Player player) {
      if (!level.isClientSide()) {
         player.hurt(level.damageSources().cactus(), 3.0F);
         level.scheduleTick(pos, this, 10);
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
      super.playerDestroy(level, player, pos, state, te, stack);
      player.hurt(level.damageSources().cactus(), 3.0F);
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      for (Player player : level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(8.0))) {
         if (player.swinging) {
            BlockHitResult ray = EntityUtil.rayTrace(player);
            if (ray.getType() == Type.BLOCK && pos.equals(ray.getBlockPos())) {
               player.hurt(level.damageSources().cactus(), 3.0F);
               level.scheduleTick(pos, this, 10);
            }
         }
      }
   }

   private boolean shouldDamage(Entity entity) {
      return !(entity instanceof Spider)
         && !(entity instanceof ItemEntity)
         && !entity.isIgnoringBlockTriggers()
         && (entity.getVehicle() == null || this.shouldDamage(entity.getVehicle()));
   }
}
