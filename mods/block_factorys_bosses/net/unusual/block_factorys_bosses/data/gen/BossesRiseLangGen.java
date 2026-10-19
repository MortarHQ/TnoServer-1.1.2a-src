package net.unusual.block_factorys_bosses.data.gen;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.unusual.block_factorys_bosses.data.util.BossesRiseLangProvider;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.event.StructureDestructionEvents;
import net.unusual.block_factorys_bosses.init.BlockFactorysBossesModTabs;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseDamageTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseKeyMappings;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseLangGen extends BossesRiseLangProvider {
   public BossesRiseLangGen(PackOutput output) {
      super(output);
   }

   @Override
   protected void addCustomTranslations() {
      this.addEntityAndEgg(BossesRiseEntities.YETI, "Skor, The Yeti");
      this.addEntityType(BossesRiseEntities.GLACIAL_SHOVE, "Ice Cube");
      this.addEntityType(BossesRiseEntities.ICE_SPIKE, "Ice Spike");
      this.addEntityType(BossesRiseEntities.ICE_SPIKE_CLUSTER, "Ice Spike Cluster");
      this.addEntityType(BossesRiseEntities.ICE_SPIKE_PR, "Ice Spike Projectile");
      this.addEntityType(BossesRiseEntities.FROZEN_SKELETON, "Frozen Skeleton");
      this.addEntityAndEgg(BossesRiseEntities.INFERNAL_DRAGON, "Ashlord, The Infernal Dragon");
      this.addEntityType(BossesRiseEntities.BLAZING_FIRE_BALL, "Blazing Fireball");
      this.addEntityType(BossesRiseEntities.FIRE_AREA, "Fire Arena");
      this.addEntityAndEgg(BossesRiseEntities.DRAGON_GUARD_SWORD, "Ashlord Guard");
      this.addEntityAndEgg(BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD, "Flaming Ashlord Guard");
      this.addEntityAndEgg(BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL, "Flaming Ashlord Shooter");
      this.addEntityAndEgg(BossesRiseEntities.UNDERWORLD_KNIGHT, "Helvar, the Underworld Knight");
      this.addEntityAndEgg(BossesRiseEntities.SOUL_KNIGHT_WITHER_SKELETON, "Wither Knight");
      this.addEntityAndEgg(BossesRiseEntities.SOUL_SKELETON, "Soul Skeleton");
      this.addEntityType(BossesRiseEntities.SOUL_SHOCKWAVE, "Soul Shockwave");
      this.addEntityType(BossesRiseEntities.SWORD_WAVE, "Sword Wave");
      this.addEntityType(BossesRiseEntities.RIFT, "Helvar Rift");
      this.addEntityType(BossesRiseEntities.RIFT_PROJECTILE, "Helvar Rift Projectile");
      this.addEntityType(BossesRiseEntities.BIG_RIFT_PROJECTILE, "Helvar Rift Projectile Deluxe");
      this.addEntityType(BossesRiseEntities.KNIGHT_MARK, "Helvar's Weakness");
      this.addEntityAndEgg(BossesRiseEntities.SANDWORM, "Sirok, The Sandworm");
      this.addEntityType(BossesRiseEntities.SAND_COLUMN, "Sand Column");
      this.addEntityType(BossesRiseEntities.POISON_AREA, "Poison Area");
      this.addEntityType(BossesRiseEntities.POISON_SPIT_PR, "Poison Spit");
      this.addEntityAndEgg(BossesRiseEntities.KRAKEN, "Nerakyss, The Kraken");
      this.addEntityType(BossesRiseEntities.KRAKEN_TENTACLE, "Kraken Tentacle");
      this.addEntityAndEgg(BossesRiseEntities.PIRATE_CAPTAIN, "Pirate Captain");
      this.addEntityAndEgg(BossesRiseEntities.PIRATE_ROOK, "Pirate Rook");
      this.addEntityAndEgg(BossesRiseEntities.CROSSBOW_PIRATE, "Crossbow Pirate");
      this.addEntityType(BossesRiseEntities.THROWN_CRATE, "Crate");
      this.addEntityAndEgg(BossesRiseEntities.CRATE_PILE, "Crate Pile");
      this.addItem(BossesRiseItems.CRATE, "Crate");
      this.addEntityAndEgg(BossesRiseEntities.ANCHOR, "Anchor");
      this.addItem(BossesRiseItems.ANCHOR_ITEM, "Anchor");
      this.addEntityAndEgg(BossesRiseEntities.PILE_OF_BONES, "Pile Of Bones");
      this.addEntityType(BossesRiseEntities.CAGE, "Cage");
      this.addItem(BossesRiseItems.CAGE_ITEM, "Cage");
      this.addEntityType(BossesRiseEntities.CAGE_SKELLY, "Cage With Skelly");
      this.addItem(BossesRiseItems.CAGE_SKELLY_ITEM, "Cage With Skelly");
      this.addEntityType(BossesRiseEntities.BIG_CAGE, "Big Cage");
      this.addItem(BossesRiseItems.BIG_CAGE_ITEM, "Big Cage");
      this.addEntityType(BossesRiseEntities.BIG_CAGE_SKELLY, "Big Cage With Skelly");
      this.addItem(BossesRiseItems.BIG_CAGE_SKELLY_ITEM, "Big Cage With Skelly");
      this.addEntityType(BossesRiseEntities.KRAKEN_CANNON, "Kraken Cannon");
      this.addItem(BossesRiseItems.KRAKEN_CANNON_ITEM, "Kraken Cannon");
      this.addEntityType(BossesRiseEntities.CANNONBALL, "Cannonball");
      this.addBlock(BossesRiseBlocks.CANNONBALL, "Cannonball");
      this.addBlock(BossesRiseBlocks.PLANK, "Pile of Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS, "Big Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN, "Broken Big Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED, "Cracked Big Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT, "Big Oak Planks (straight)");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION, "Big Oak Planks (variant)");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_WET, "Big Wet Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_WET_BROKEN, "Broken Big Wet Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_WET_CRACKED, "Cracked Big Wet Oak Planks");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_WET_STRAIGHT, "Big Wet Oak Planks (straight)");
      this.addBlock(BossesRiseBlocks.BIG_OAK_PLANKS_WET_VARIATION, "Big Wet Oak Planks (variant)");
      this.addBlock(BossesRiseBlocks.GUARDRAIL, "Guardrail");
      this.addBlock(BossesRiseBlocks.GUARDRAIL_DIAGONAL, "Diagonal Guardrail");
      this.addBlock(BossesRiseBlocks.SHIP_LANTERN, "Ship Lantern");
      this.addBlock(BossesRiseBlocks.COIN_PILE, "Coin Pile");
      this.addBlock(BossesRiseBlocks.ROPE_ROLL, "Rope Roll");
      this.addBlock(BossesRiseBlocks.SHIP_STEERING_WHEEL, "Ship Steering Wheel");
      this.addBlock(BossesRiseBlocks.NET, "Net");
      this.addBlock(BossesRiseBlocks.VASE, "Vase");
      this.addBlock(BossesRiseBlocks.TALL_VASE, "Tall Vase");
      this.addBlock(BossesRiseBlocks.WALL_TORCH, "Wall Torch");
      this.addBlock(BossesRiseBlocks.UNDERWOLD_WALL_TORCH, "Soul Wall Torch");
      this.addBlock(BossesRiseBlocks.CANDLES, "Small Candelabra");
      this.addBlock(BossesRiseBlocks.UNDERWOLD_CANDLES, "Small Soul Candelabra");
      this.addBlockAndDesc(BossesRiseBlocks.TALL_CANDLES, "Tall Candelabra", "§7Twisted");
      this.addBlockAndDesc(BossesRiseBlocks.UNDERWORLD_TALL_CANDLES, "Tall Soul Candelabra", "§7Twisted");
      this.addBlockAndDesc(BossesRiseBlocks.TALL_CANDLES_CROSS, "Tall Cross Candelabra", "§7Cross");
      this.addBlockAndDesc(BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS, "Tall Cross Soul Candelabra", "§7Cross");
      this.addBlockAndDesc(BossesRiseBlocks.BONE_REMAINS, "Bone Remains", "§7Body");
      this.addBlockAndDesc(BossesRiseBlocks.BONE_REMAINS_LEGS, "Bone Remains", "§7Legs");
      this.addBlockAndDesc(BossesRiseBlocks.BONE_REMAINS_RIB_CAGE, "Bone Remains", "§7Rib Cage");
      this.addBlock(BossesRiseBlocks.CORPSE, "Corpse");
      this.addBlock(BossesRiseBlocks.BIG_CHAIN, "Big Chain");
      this.addBlock(BossesRiseBlocks.PRISON_DOOR, "Prison Door");
      this.addBlock(BossesRiseBlocks.RUSTY_PRISON_DOOR, "Rusty Prison Door");
      this.addBlock(BossesRiseBlocks.ICE_BLOCK_PARTICULES, "Ice Block Particles");
      this.addBlock(BossesRiseBlocks.DRAGON_BANNER, "Dragon Banner");
      this.addBlock(BossesRiseBlocks.DRAGON_SKULL, "Ashlord Skull");
      this.addBlock(BossesRiseBlocks.BOSS_SPAWNER, "Boss Spawner");
      this.addBlock(BossesRiseBlocks.KRAKEN_SPAWNER, "Kraken Spawner");
      this.addBlock(BossesRiseBlocks.UNDERWORLD_ARENA_DOOR, "Underworld Arena Door");
      this.addItem(BossesRiseItems.PIRATE_SABER, "Pirate Saber");
      this.addItem(BossesRiseItems.KRAKEN_TOOTH, "Kraken Tooth");
      this.addItemAndDesc(BossesRiseItems.KRAKEN_TRIDENT, "Kraken Trident", "§7On Collision:", "§9Damages and pulls nearby entities");
      this.addItemAndDesc(
         BossesRiseItems.UNDYING_TENTACLE,
         "Undying Tentacle",
         "§7On Use:",
         "§9Hook yourself to block, or an entity to yourself.",
         "§7On Shift-Use:",
         "§9Summon tentacles to fight enemies."
      );
      this.addEntityAndEgg(BossesRiseEntities.GHOST_TENTACLE, "Ghost Tentacle");
      this.addItem(BossesRiseItems.DRAGON_GUARD_SHIELD, "Dragon Guard Shield");
      this.addItem(BossesRiseItems.ENHANCED_SHIELD, "Enhanced Shield");
      this.addItem(BossesRiseItems.DRAGON_BONES_CHESTPLATE, "Dragon Bones Chestplate");
      this.addItem(BossesRiseItems.DRAGON_BONES_LEGGINGS, "Dragon Bones Leggings");
      this.addItem(BossesRiseItems.DRAGON_BONES_BOOTS, "Dragon Bones Boots");
      this.addItemAndDesc(
         BossesRiseItems.ICE_GAUNTLET,
         "Skor Gauntlet",
         "§7On Hit:",
         "§9Freeze Target",
         "§7On Sneak-Right-Click Release:",
         "§9Fire wave of ice-spikes",
         "§7On jump while holding Right-Click:",
         "§9Perform an ice-slam-leap",
         "§8Can be used as a Shield"
      );
      this.addItem(BossesRiseItems.KNIGHT_HELMET, "Knight Helmet");
      this.addItem(BossesRiseItems.KNIGHT_CHESTPLATE, "Knight Chestplate");
      this.addItem(BossesRiseItems.KNIGHT_LEGGINGS, "Knight Leggings");
      this.addItem(BossesRiseItems.KNIGHT_BOOTS, "Knight Boots");
      this.addItem(BossesRiseItems.LARGE_SWORD, "Large Sword");
      this.addItem(BossesRiseItems.WARRIOR_SWORD, "Warrior Sword");
      this.addItem(BossesRiseItems.DAGGER, "Dagger");
      this.addItem(BossesRiseItems.DRAGON_BONE, "Dragon Bone");
      this.addItemAndDesc(BossesRiseItems.KNIGHT_SWORD, "Helvar's Sword", "§7On Right-Click:", "§9Throw a powerful Slash");
      this.addItemAndDesc(BossesRiseItems.SANDWORM_GAUNTLET, "Sirok Gauntlet", "§7On Hold-Right-Click:", "§9Shoot Poison");
      this.addItem(BossesRiseItems.DRAGON_SHANK, "Dragon Shank");
      this.addItem(BossesRiseItems.ANCIENT_TRIAL_KEY, "Ancient Trial Key");
      this.addItem(BossesRiseItems.UNDERWORLD_ARENA_KEY, "Underworld Arena Key");
      this.addDamageType(BossesRiseDamageTypes.CANNONBALL_HIT, "%1$s got flattened with a cannonball", null, "%2$s flattened %1$s with a cannonball");
      this.addDamageType(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH, "%1$s got smashed by a tentacle", null, "%2$s smashed %1$s with a tentacle");
      this.add("block_factorys_bosses", "Bosses' Rise");
      this.addCreativeTab(BlockFactorysBossesModTabs.BLOCKFACTORYSBOSSESTAB, "Bosses' Rise");
      this.addAttribute(BossesRiseAttributes.ROLL_COUNT, "Roll Count");
      this.addKeyMapping(BossesRiseKeyMappings.DODGE_ROLL, "Dodge Roll");
      this.add(StructureDestructionEvents.DESTRUCTION_CANCEL_MESSAGE, "The Dragon Tower is protected by ancient magic!");
      this.add(UnderworldKnightEntity.KNOCKED_DOWN_MESSAGE, "Helvar is knocked down!");
      this.add(CannonEntity.NO_BALLS_MESSAGE, "No ammo!");
   }
}
