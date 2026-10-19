package de.ellpeck.naturesaura.blocks.tiles;

import com.google.common.primitives.Ints;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.component.FireworkExplosion.Shape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BlockEntityFireworkGenerator extends BlockEntityImpl implements ITickableBlockEntity {
   private FireworkRocketEntity trackedEntity;
   private ItemStack trackedItem;
   private int toRelease;
   private int releaseTimer;

   public BlockEntityFireworkGenerator(BlockPos pos, BlockState state) {
      super(ModBlockEntities.FIREWORK_GENERATOR, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.level.getGameTime() % 10L == 0L) {
            for (ItemEntity item : this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition).inflate(4.0), Entity::isAlive)) {
               if (!item.hasPickUpDelay()) {
                  ItemStack stack = item.getItem();
                  if (!stack.isEmpty() && stack.getItem() == Items.FIREWORK_ROCKET) {
                     if (this.trackedEntity == null && this.releaseTimer <= 0) {
                        FireworkRocketEntity entity = new FireworkRocketEntity(this.level, item.getX(), item.getY(), item.getZ(), stack);
                        this.trackedEntity = entity;
                        this.trackedItem = stack.copy();
                        this.level.addFreshEntity(entity);
                     }

                     stack.shrink(1);
                     if (stack.isEmpty()) {
                        item.kill();
                     } else {
                        item.setItem(stack);
                     }
                  }
               }
            }
         }

         if (this.trackedEntity != null && !this.trackedEntity.isAlive()) {
            if (this.trackedItem.has(DataComponents.FIREWORKS)) {
               float generateFactor = 0.0F;
               Set<Integer> usedColors = new HashSet<>();
               Fireworks fireworks = (Fireworks)this.trackedItem.get(DataComponents.FIREWORKS);
               int flightTime = fireworks.flightDuration();
               List<FireworkExplosion> explosions = fireworks.explosions();
               if (!explosions.isEmpty()) {
                  generateFactor += flightTime;

                  for (FireworkExplosion explosion : explosions) {
                     generateFactor += 1.5F;
                     boolean flicker = explosion.hasTwinkle();
                     if (flicker) {
                        generateFactor++;
                     }

                     boolean trail = explosion.hasTrail();
                     if (trail) {
                        generateFactor += 8.0F;
                     }

                     Shape type = explosion.shape();
                     generateFactor += new float[]{0.0F, 1.0F, 0.5F, 20.0F, 0.5F}[type.getId()];
                     Set<Integer> colors = new HashSet<>();
                     IntListIterator var12 = explosion.colors().iterator();

                     while (var12.hasNext()) {
                        Integer color = (Integer)var12.next();
                        usedColors.add(color);
                        colors.add(color);
                     }

                     generateFactor += 0.75F * colors.size();
                  }
               }

               if (generateFactor > 0.0F) {
                  int toAdd = Mth.ceil(generateFactor * 10000.0F);
                  if (this.canGenerateRightNow(toAdd)) {
                     this.toRelease = toAdd;
                     this.releaseTimer = 15 * flightTime + 40;
                  } else {
                     this.spawnCannotGenerateParticles();
                  }

                  List<Integer> data = new ArrayList<>();
                  data.add(this.worldPosition.getX());
                  data.add(this.worldPosition.getY());
                  data.add(this.worldPosition.getZ());
                  data.addAll(usedColors);
                  PacketHandler.sendToAllLoaded(
                     this.level,
                     this.worldPosition,
                     new PacketParticles(
                        (float)this.trackedEntity.getX(),
                        (float)this.trackedEntity.getY(),
                        (float)this.trackedEntity.getZ(),
                        PacketParticles.Type.FIREWORK_GEN,
                        Ints.toArray(data)
                     )
                  );
               }
            }

            this.trackedEntity = null;
            this.trackedItem = null;
         }

         if (this.releaseTimer > 0) {
            this.releaseTimer--;
            if (this.releaseTimer <= 0) {
               this.generateAura(this.toRelease);
               this.toRelease = 0;
               PacketHandler.sendToAllLoaded(
                  this.level,
                  this.worldPosition,
                  new PacketParticles(
                     this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.FLOWER_GEN_AURA_CREATION
                  )
               );
            }
         }
      }
   }

   @Override
   public boolean wantsLimitRemover() {
      return true;
   }
}
