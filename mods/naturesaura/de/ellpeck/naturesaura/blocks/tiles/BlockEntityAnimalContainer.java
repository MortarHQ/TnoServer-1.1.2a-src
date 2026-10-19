package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlockEntityAnimalContainer extends BlockEntityImpl implements ITickableBlockEntity {
   public BlockEntityAnimalContainer(BlockPos pos, BlockState state) {
      super(ModBlockEntities.ANIMAL_CONTAINER, pos, state);
   }

   public int getRadius() {
      return this.redstonePower / 2;
   }

   @Override
   public void onRedstonePowerChange(int newPower) {
      super.onRedstonePowerChange(newPower);
      this.sendToClients();
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         int radius = this.getRadius();
         Set<Animal> animalsInRange = new HashSet<>(this.level.getEntitiesOfClass(Animal.class, new AABB(this.worldPosition).inflate(radius - 1)));

         for (Animal animal : this.level.getEntitiesOfClass(Animal.class, new AABB(this.worldPosition).inflate(radius + 1))) {
            if (!animalsInRange.contains(animal)) {
               Vec3 pos = animal.position();
               Vec3 distance = pos.subtract(this.worldPosition.getX(), pos.y, this.worldPosition.getZ());
               distance = distance.normalize().scale(-0.15F);
               animal.setDeltaMovement(distance);
               if (this.level.random.nextBoolean()) {
                  Vec3 eye = animal.getEyePosition(1.0F).add(animal.getLookAngle());
                  PacketHandler.sendToAllAround(
                     this.level, this.worldPosition, 32, new PacketParticles((float)eye.x, (float)eye.y, (float)eye.z, PacketParticles.Type.ANIMAL_CONTAINER)
                  );
               }
            }
         }
      }
   }
}
