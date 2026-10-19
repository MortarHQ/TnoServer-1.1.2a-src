package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Pair;

public class AnimalEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "animal");
   private int chance;
   private AABB bb;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot <= 0) {
         return false;
      }

      Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 30);
      int aura = (Integer)auraAndSpots.getLeft();
      if (aura < 1500000) {
         return false;
      }

      this.chance = Math.min(50, Mth.ceil(Math.abs(aura) / 500000.0F / ((Integer)auraAndSpots.getRight()).intValue()));
      if (this.chance <= 0) {
         return false;
      }

      int dist = Mth.clamp(Math.abs(aura) / 150000, 5, 35);
      this.bb = new AABB(pos).inflate(dist);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else if (!this.bb.contains(player.getEyePosition())) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return !NaturesAuraAPI.instance().isEffectPowderActive(player.level(), player.blockPosition(), NAME)
            ? IDrainSpotEffect.ActiveType.INHIBITED
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(Items.EGG);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (level.getGameTime() % 200L == 0L) {
         if (this.calcValues(level, pos, spot)) {
            List<Animal> animals = level.getEntitiesOfClass(Animal.class, this.bb);
            if (animals.size() < (Integer)ModConfig.instance.maxAnimalsAroundPowder.get()) {
               for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, this.bb)) {
                  if (item.isAlive() && NaturesAuraAPI.instance().isEffectPowderActive(level, item.blockPosition(), NAME)) {
                     ItemStack stack = item.getItem();
                     if (stack.getItem() instanceof EggItem && item.getAge() >= item.lifespan / 2) {
                        if (stack.getCount() <= 1) {
                           item.kill();
                        } else {
                           stack.shrink(1);
                           item.setItem(stack);
                        }

                        Chicken chicken = new Chicken(EntityType.CHICKEN, level);
                        chicken.setAge(-24000);
                        chicken.setPos(item.getX(), item.getY(), item.getZ());
                        level.addFreshEntity(chicken);
                        BlockPos closestSpot = IAuraChunk.getHighestSpot(level, item.blockPosition(), 35, pos);
                        IAuraChunk.getAuraChunk(level, closestSpot).drainAura(closestSpot, 2000);
                     }
                  }
               }

               if (level.random.nextInt(20) <= this.chance) {
                  if (animals.size() < 2) {
                     return;
                  }

                  Animal first = animals.get(level.random.nextInt(animals.size()));
                  if (first.isBaby() || first.isInLove()) {
                     return;
                  }

                  if (!NaturesAuraAPI.instance().isEffectPowderActive(level, first.blockPosition(), NAME)) {
                     return;
                  }

                  Optional<Animal> secondOptional = animals.stream()
                     .filter(e -> e != first && !e.isInLove() && !e.isBaby())
                     .min(Comparator.comparingDouble(e -> e.distanceToSqr(first)));
                  if (secondOptional.isEmpty()) {
                     return;
                  }

                  Animal second = secondOptional.get();
                  if (second.distanceToSqr(first) > 25.0) {
                     return;
                  }

                  this.setInLove(first);
                  this.setInLove(second);
                  BlockPos closestSpot = IAuraChunk.getHighestSpot(level, first.blockPosition(), 35, pos);
                  IAuraChunk.getAuraChunk(level, closestSpot).drainAura(closestSpot, 3500);
               }
            }
         }
      }
   }

   private void setInLove(Animal animal) {
      animal.setInLove(null);

      for (int j = 0; j < 7; j++) {
         animal.level()
            .addParticle(
               ParticleTypes.HEART,
               animal.getX() + animal.level().random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(),
               animal.getY() + 0.5 + animal.level().random.nextFloat() * animal.getBbHeight(),
               animal.getZ() + animal.level().random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(),
               animal.level().random.nextGaussian() * 0.02,
               animal.level().random.nextGaussian() * 0.02,
               animal.level().random.nextGaussian() * 0.02
            );
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.animalEffect.get();
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
