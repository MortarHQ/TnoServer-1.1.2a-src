package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityProjectileGenerator;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.blocks.tiles.render.RenderProjectileGenerator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import de.ellpeck.naturesaura.reg.ITESRProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileItem.DispenseConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public class BlockProjectileGenerator extends BlockContainerImpl implements ITESRProvider<BlockEntityProjectileGenerator>, ICustomBlockState, IPickaxeBreakable {
   public BlockProjectileGenerator() {
      super("projectile_generator", BlockEntityProjectileGenerator.class, Properties.of().strength(2.5F).sound(SoundType.STONE));
      NeoForge.EVENT_BUS.register(this);
      DispenserBlock.registerBehavior(
         Items.ENDER_PEARL,
         new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack item) {
               Level level = blockSource.level();
               Direction direction = (Direction)blockSource.state().getValue(DispenserBlock.FACING);
               Position position = DispenseConfig.DEFAULT.positionFunction().getDispensePosition(blockSource, direction);
               ThrownEnderpearl projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
               projectile.setPos(position.x(), position.y(), position.z());
               projectile.shoot(
                  direction.getStepX(), direction.getStepY(), direction.getStepZ(), DispenseConfig.DEFAULT.power(), DispenseConfig.DEFAULT.uncertainty()
               );
               level.addFreshEntity(projectile);
               item.shrink(1);
               return item;
            }
         }
      );
      DispenserBlock.registerBehavior(
         Items.TRIDENT,
         new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack item) {
               Level level = blockSource.level();
               Direction direction = (Direction)blockSource.state().getValue(DispenserBlock.FACING);
               Position position = DispenseConfig.DEFAULT.positionFunction().getDispensePosition(blockSource, direction);
               ThrownTrident projectile = new ThrownTrident(EntityType.TRIDENT, level);
               ObfuscationReflectionHelper.setPrivateValue(AbstractArrow.class, projectile, item.copy(), "pickupItemStack");
               projectile.pickup = Pickup.ALLOWED;
               projectile.setPos(position.x(), position.y(), position.z());
               projectile.shoot(
                  direction.getStepX(), direction.getStepY(), direction.getStepZ(), DispenseConfig.DEFAULT.power(), DispenseConfig.DEFAULT.uncertainty()
               );
               level.addFreshEntity(projectile);
               item.shrink(1);
               return item;
            }
         }
      );
   }

   @SubscribeEvent
   public void onProjectileImpact(ProjectileImpactEvent event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide) {
         if (event.getRayTraceResult() instanceof BlockHitResult blockRay) {
            BlockPos pos = blockRay.getBlockPos();
            if (pos != null) {
               if (entity.level().getBlockEntity(pos) instanceof BlockEntityProjectileGenerator generator) {
                  if (generator.nextSide == blockRay.getDirection()) {
                     Integer amount = NaturesAuraAPI.PROJECTILE_GENERATIONS.get(entity.getType());
                     if (amount != null && amount > 0) {
                        if (!generator.canGenerateRightNow(amount)) {
                           generator.spawnCannotGenerateParticles();
                        } else {
                           generator.generateAura(amount);
                           PacketHandler.sendToAllAround(
                              entity.level(),
                              pos,
                              32,
                              new PacketParticles(
                                 (float)entity.getX(),
                                 (float)entity.getY(),
                                 (float)entity.getZ(),
                                 PacketParticles.Type.PROJECTILE_GEN,
                                 pos.getX(),
                                 pos.getY(),
                                 pos.getZ()
                              )
                           );
                           entity.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.BLOCKS, 0.8F, 1.0F);
                           generator.nextSide = generator.nextSide.getClockWise();
                           generator.sendToClients();
                           entity.kill();
                           event.setCanceled(true);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_top"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }

   @Override
   public void registerTESR() {
      BlockEntityRenderers.register(ModBlockEntities.PROJECTILE_GENERATOR, RenderProjectileGenerator::new);
   }
}
