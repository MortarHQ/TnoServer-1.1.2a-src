package twilightforest.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.entity.monster.DeathTome;
import twilightforest.entity.passive.Bighorn;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.Squirrel;
import twilightforest.entity.passive.TinyBird;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFEntities;
import twilightforest.network.CreateMovingCicadaSoundPacket;

@Component
public class MiscEvents {
   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::addPrey);
      NeoForge.EVENT_BUS.addListener(this::updateCicadaSoundsOnHead);
      NeoForge.EVENT_BUS.addListener(this::addTomesToLecterns);
      NeoForge.EVENT_BUS.addListener(this::washOffCloth);
   }

   private void addPrey(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof Mob mob) {
         EntityType<?> type = mob.getType();
         if (type == EntityType.CAT) {
            mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal((TamableAnimal)mob, DwarfRabbit.class, true, null));
            mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal((TamableAnimal)mob, Squirrel.class, true, null));
            mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal((TamableAnimal)mob, TinyBird.class, true, null));
         } else if (type == EntityType.OCELOT) {
            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, DwarfRabbit.class, true));
            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, Squirrel.class, true));
            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal(mob, TinyBird.class, true));
         } else if (type == EntityType.FOX) {
            mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, DwarfRabbit.class, true));
            mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal(mob, Squirrel.class, true));
         } else if (type == EntityType.WOLF) {
            mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal((TamableAnimal)mob, DwarfRabbit.class, true, null));
            mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal((TamableAnimal)mob, Squirrel.class, true, null));
            mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal((TamableAnimal)mob, Bighorn.class, true, null));
         }
      }
   }

   private void updateCicadaSoundsOnHead(LivingEquipmentChangeEvent event) {
      LivingEntity living = event.getEntity();
      if (!ModList.get().isLoaded("curios") || !CuriosCompat.isCurioEquipped(living, stack -> stack.is(TFBlocks.CICADA.asItem()))) {
         if (!living.level().isClientSide() && event.getSlot() == EquipmentSlot.HEAD && event.getTo().is(TFBlocks.CICADA.asItem())) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new CreateMovingCicadaSoundPacket(living.getId()), new CustomPacketPayload[0]);
         }
      }
   }

   private void addTomesToLecterns(RightClickBlock event) {
      Player player = event.getEntity();
      ItemStack stack = player.getItemInHand(event.getHand());
      if (stack.getItem() instanceof SpawnEggItem spawnEggItem && spawnEggItem.getType(stack) == TFEntities.DEATH_TOME.get()) {
         BlockPos pos = event.getPos();
         Level level = event.getLevel();
         BlockState state = level.getBlockState(pos);
         if (state.getBlock() instanceof LecternBlock && !(Boolean)state.getValue(BlockStateProperties.HAS_BOOK)) {
            event.setCanceled(true);
            level.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (level instanceof ServerLevel serverLevel) {
               DeathTome tome = (DeathTome)((EntityType)TFEntities.DEATH_TOME.get())
                  .spawn(serverLevel, stack, player, pos.below(), MobSpawnType.SPAWN_EGG, true, false);
               if (tome != null) {
                  stack.consume(1, player);
                  serverLevel.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
                  tome.setOnLectern(true);
               }
            }
         }
      }
   }

   private void washOffCloth(RightClickBlock event) {
      if (!event.isCanceled()) {
         BlockState state = event.getLevel().getBlockState(event.getPos());
         if (state.is(Blocks.WATER_CAULDRON) && (Integer)state.getValue(LayeredCauldronBlock.LEVEL) > 0) {
            if (event.getItemStack().has(TFDataComponents.EMPERORS_CLOTH)) {
               LayeredCauldronBlock.lowerFillLevel(state, event.getLevel(), event.getPos());
               event.getItemStack().remove(TFDataComponents.EMPERORS_CLOTH);
               event.getEntity().awardStat(Stats.CLEAN_ARMOR);
               event.setCancellationResult(InteractionResult.SUCCESS);
               event.setCanceled(true);
            }
         }
      }
   }
}
