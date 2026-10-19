package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.lang.reflect.Field;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.fml.util.ObfuscationReflectionHelper;

public class BlockEntityPotionGenerator extends BlockEntityImpl implements ITickableBlockEntity {
   private static final Field POTION_CONTENTS_FIELD = ObfuscationReflectionHelper.findField(AreaEffectCloud.class, "potionContents");

   public BlockEntityPotionGenerator(BlockPos pos, BlockState state) {
      super(ModBlockEntities.POTION_GENERATOR, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 10L == 0L && Multiblocks.POTION_GENERATOR.isComplete(this.level, this.worldPosition)) {
         boolean addedOne = false;

         for (AreaEffectCloud cloud : this.level.getEntitiesOfClass(AreaEffectCloud.class, new AABB(this.worldPosition).inflate(2.0))) {
            if (cloud.isAlive()) {
               if (!addedOne) {
                  PotionContents type;
                  try {
                     type = (PotionContents)POTION_CONTENTS_FIELD.get(cloud);
                  } catch (IllegalAccessException e) {
                     NaturesAura.LOGGER.fatal("Couldn't reflect furnace field", e);
                     continue;
                  }

                  for (MobEffectInstance effect : type.getAllEffects()) {
                     MobEffect potion = (MobEffect)effect.getEffect().value();
                     if (potion.isBeneficial() && !potion.isInstantenous()) {
                        int toAdd = (effect.getAmplifier() * 7 + 1) * (effect.getDuration() / 25) * 100;
                        boolean canGen = this.canGenerateRightNow(toAdd);
                        if (canGen) {
                           this.generateAura(toAdd);
                        } else {
                           this.spawnCannotGenerateParticles();
                        }

                        PacketHandler.sendToAllAround(
                           this.level,
                           this.worldPosition,
                           32,
                           new PacketParticles(
                              this.worldPosition.getX(),
                              this.worldPosition.getY(),
                              this.worldPosition.getZ(),
                              PacketParticles.Type.POTION_GEN,
                              type.getColor(),
                              canGen ? 1 : 0
                           )
                        );
                        addedOne = true;
                        break;
                     }
                  }
               }

               float newRadius = cloud.getRadius() - 0.25F;
               if (newRadius < 0.5F) {
                  cloud.kill();
               } else {
                  cloud.setRadius(newRadius);
               }
            }
         }
      }
   }

   @Override
   public boolean wantsLimitRemover() {
      return true;
   }
}
