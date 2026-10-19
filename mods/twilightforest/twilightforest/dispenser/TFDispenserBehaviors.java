package twilightforest.dispenser;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.TrophyBlock;
import twilightforest.entity.projectile.MoonwormShot;
import twilightforest.entity.projectile.TwilightWandBolt;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;

public class TFDispenserBehaviors {
   public static void init() {
      DispenserBlock.registerBehavior((ItemLike)TFItems.MOONWORM_QUEEN.get(), new DamageableStackDispenseBehavior() {
         @Override
         protected Projectile getProjectileEntity(Level level, Position position, ItemStack stack) {
            return new MoonwormShot(level, position.x(), position.y(), position.z());
         }

         @Override
         protected int getDamageAmount() {
            return 2;
         }

         @Override
         protected SoundEvent getFiredSound() {
            return (SoundEvent)TFSounds.MOONWORM_SQUISH.get();
         }
      });
      DispenseItemBehavior idispenseitembehavior = new OptionalDispenseItemBehavior() {
         protected ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(ArmorItem.dispenseArmor(source, stack));
            return stack;
         }
      };
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.NAGA_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.LICH_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.MINOSHROOM_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.HYDRA_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.KNIGHT_PHANTOM_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.UR_GHAST_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.ALPHA_YETI_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.SNOW_QUEEN_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((TrophyBlock)TFBlocks.QUEST_RAM_TROPHY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((AbstractSkullCandleBlock)TFBlocks.CREEPER_SKULL_CANDLE.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((AbstractSkullCandleBlock)TFBlocks.PLAYER_SKULL_CANDLE.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((AbstractSkullCandleBlock)TFBlocks.SKELETON_SKULL_CANDLE.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((AbstractSkullCandleBlock)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((AbstractSkullCandleBlock)TFBlocks.ZOMBIE_SKULL_CANDLE.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((Block)TFBlocks.CICADA.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((Block)TFBlocks.FIREFLY.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((Block)TFBlocks.MOONWORM.get()).asItem(), idispenseitembehavior);
      DispenserBlock.registerBehavior(((Item)TFItems.PEACOCK_FEATHER_FAN.get()).asItem(), new FeatherFanDispenseBehavior());
      DispenserBlock.registerBehavior(((Item)TFItems.CRUMBLE_HORN.get()).asItem(), new CrumbleDispenseBehavior());
      DispenserBlock.registerBehavior(((Item)TFItems.TRANSFORMATION_POWDER.get()).asItem(), new TransformationDispenseBehavior());
      DispenserBlock.registerBehavior((ItemLike)TFItems.TWILIGHT_SCEPTER.get(), new DamageableStackDispenseBehavior() {
         @Override
         protected Projectile getProjectileEntity(Level level, Position position, ItemStack stack) {
            return new TwilightWandBolt(level, position.x(), position.y(), position.z());
         }

         @Override
         protected int getDamageAmount() {
            return 1;
         }

         @Override
         protected SoundEvent getFiredSound() {
            return (SoundEvent)TFSounds.TWILIGHT_SCEPTER_USE.get();
         }

         @Override
         protected float getProjectileInaccuracy() {
            return 6.0F;
         }
      });
      DispenserBlock.registerProjectileBehavior((ItemLike)TFItems.ICE_BOMB.get());
      DispenserBlock.registerBehavior(Items.CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.BLACK_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.GRAY_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.LIGHT_GRAY_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.WHITE_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.RED_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.ORANGE_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.YELLOW_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.GREEN_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.LIME_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.BLUE_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.CYAN_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.LIGHT_BLUE_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.PURPLE_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.MAGENTA_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.PINK_CANDLE, new CandleDispenseBehavior());
      DispenserBlock.registerBehavior(Items.BROWN_CANDLE, new CandleDispenseBehavior());
   }
}
