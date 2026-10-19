package net.mebahelsapi.block.entity.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.mebahelsapi.screenhandler.ModChestScreenHandler;
import net.mebahelsapi.util.ChestOpenSync;
import net.minecraft.class_1262;
import net.minecraft.class_1277;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_173;
import net.minecraft.class_1799;
import net.minecraft.class_181;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_2388;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2519;
import net.minecraft.class_2561;
import net.minecraft.class_2591;
import net.minecraft.class_2595;
import net.minecraft.class_2596;
import net.minecraft.class_2602;
import net.minecraft.class_2622;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_52;
import net.minecraft.class_5321;
import net.minecraft.class_7924;
import net.minecraft.class_8567;
import net.minecraft.class_7225.class_7874;
import net.minecraft.class_8567.class_8568;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.animation.AnimationController.State;

public abstract class BaseChestBlockEntity extends class_2595 implements GeoBlockEntity, ExtendedScreenHandlerFactory {
   protected final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
   private boolean spawnAnimationPlaying = false;
   public boolean isOpened = false;
   public boolean hasBeenOpened = false;
   public boolean shouldDoSpawnAnimation = false;
   public int closeCooldown = 0;
   public int age = 0;
   protected int viewerCount = 0;
   protected int viewers = 0;
   public final Map<UUID, class_2371<class_1799>> personalLoots = new HashMap<>();
   protected final Set<UUID> generatedLootPlayers = new HashSet<>();
   @Nullable
   protected class_5321<class_52> baseLootTableKey;
   private final int chestSize;

   protected BaseChestBlockEntity(class_2591<?> type, class_2338 pos, class_2680 state, int chestSize) {
      super(type, pos, state);
      this.chestSize = chestSize;
      this.method_11281(class_2371.method_10213(chestSize, class_1799.field_8037));
   }

   protected abstract class_2561 getChestTitle();

   protected abstract String getLogPrefix();

   protected boolean isMultiplayerEnabled() {
      return true;
   }

   public boolean isSharedChest() {
      return !this.isMultiplayerEnabled() ? true : this.baseLootTableKey == null && this.method_54869() == null;
   }

   public boolean hasLootTable() {
      return this.baseLootTableKey != null || this.method_54869() != null;
   }

   public void handlePlayerLoot(class_1657 player) {
      if (this.field_11863 != null && !this.field_11863.field_9236) {
         if (this.isMultiplayerEnabled()) {
            UUID id = player.method_5667();
            if (this.baseLootTableKey != null || this.method_54869() != null) {
               if (!this.generatedLootPlayers.contains(id)) {
                  this.generateLootFor(player);
                  this.generatedLootPlayers.add(id);
                  this.method_5431();
               }
            }
         }
      }
   }

   @Nullable
   public class_2960 getBaseLootTableId() {
      if (this.baseLootTableKey != null) {
         return this.baseLootTableKey.method_29177();
      }

      class_5321<class_52> key = this.method_54869();
      return key != null ? key.method_29177() : null;
   }

   private void generateLootFor(class_1657 player) {
      if (this.field_11863 instanceof class_3218 serverWorld) {
         if (this.baseLootTableKey == null) {
            class_5321<class_52> vanillaKey = this.method_54869();
            System.out.println(this.getLogPrefix() + " [DEBUG] getLootTable() = " + vanillaKey);
            if (vanillaKey == null) {
               System.out.println(this.getLogPrefix() + " No lootTable found — shared chest only.");
               return;
            }

            this.baseLootTableKey = vanillaKey;
            this.method_11285(null);
            System.out.println(this.getLogPrefix() + " Stored structure loot_table: " + this.baseLootTableKey.method_29177());
            this.method_5431();
         }

         MinecraftServer server = serverWorld.method_8503();
         class_52 lootTable = server.method_58576().method_58295(this.baseLootTableKey);
         System.out
            .println(
               this.getLogPrefix() + " [DEBUG] Using lootTable " + this.baseLootTableKey.method_29177() + " for player " + player.method_5477().getString()
            );
         long seed = this.field_11863.method_8510()
            ^ player.method_5667().getMostSignificantBits()
            ^ this.field_11867.method_10063()
            ^ this.field_11863.field_9229.method_43055();
         class_8567 lootContext = new class_8568(serverWorld)
            .method_51874(class_181.field_24424, class_243.method_24953(this.field_11867))
            .method_51874(class_181.field_1226, player)
            .method_51875(class_173.field_1179);
         class_1277 tempInventory = new class_1277(this.method_5439());
         lootTable.method_329(tempInventory, lootContext, seed);
         class_2371<class_1799> loot = class_2371.method_10213(this.method_5439(), class_1799.field_8037);
         int nonEmpty = 0;

         for (int i = 0; i < this.method_5439(); i++) {
            class_1799 stack = tempInventory.method_5438(i);
            if (!stack.method_7960()) {
               loot.set(i, stack.method_7972());
               nonEmpty++;
            }
         }

         System.out.println(this.getLogPrefix() + " [DEBUG] Generated " + nonEmpty + " non-empty stacks for player " + player.method_5477().getString());
         this.personalLoots.put(player.method_5667(), loot);
      }
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
      controllerRegistrar.add(new AnimationController(this, "openController", 0, this::openPredicate));
      controllerRegistrar.add(new AnimationController(this, "spawnController", 0, this::spawnPredicate));
   }

   private <T extends GeoAnimatable> PlayState openPredicate(AnimationState<T> event) {
      AnimationController<?> controller = event.getController();
      if (this.shouldDoSpawnAnimation) {
         return PlayState.STOP;
      }

      if (this.isOpened) {
         controller.setAnimation(RawAnimation.begin().then("open", LoopType.PLAY_ONCE).then("isOpened", LoopType.LOOP));
      } else if (this.hasBeenOpened) {
         controller.setAnimation(RawAnimation.begin().then("close", LoopType.PLAY_ONCE).then("isClosed", LoopType.LOOP));
      } else {
         controller.setAnimation(RawAnimation.begin().then("isClosed", LoopType.LOOP));
      }

      return PlayState.CONTINUE;
   }

   private <T extends GeoAnimatable> PlayState spawnPredicate(AnimationState<T> event) {
      AnimationController<?> controller = event.getController();
      if (this.shouldDoSpawnAnimation && !this.spawnAnimationPlaying) {
         this.spawnAnimationPlaying = true;
         controller.forceAnimationReset();
         controller.setAnimation(RawAnimation.begin().then("spawn", LoopType.PLAY_ONCE));
      }

      if (this.spawnAnimationPlaying) {
         if (controller.getAnimationState() != State.STOPPED) {
            this.spawnChestParticles();
         }

         if (controller.hasAnimationFinished()) {
            this.spawnAnimationPlaying = false;
            this.shouldDoSpawnAnimation = false;
         }

         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void serverTick() {
      if (this.viewerCount <= 0 && this.isOpened) {
         this.closeCooldown--;
         if (this.closeCooldown <= 0) {
            this.isOpened = false;
            this.hasBeenOpened = false;
            this.method_5431();
         }
      }

      if (this.shouldDoSpawnAnimation) {
         this.shouldDoSpawnAnimation = false;
         this.method_5431();
      }
   }

   @Environment(EnvType.CLIENT)
   public void clientTick() {
      this.age++;
      if (this.age < 10) {
         this.hasBeenOpened = false;
      }

      if (this.closeCooldown > 0) {
         this.closeCooldown--;
      }
   }

   public void sync() {
      if (this.field_11863 instanceof class_3218 serverWorld) {
         serverWorld.method_14178().method_14128(this.field_11867);
      }
   }

   protected class_2561 method_17823() {
      return this.getChestTitle();
   }

   public int method_5439() {
      return this.chestSize;
   }

   @Environment(EnvType.CLIENT)
   public int countViewers() {
      return this.viewerCount;
   }

   public class_2371<class_1799> getInternalInventory() {
      return this.method_11282();
   }

   public void method_5435(class_1657 player) {
      if (this.field_11863 != null && !this.field_11863.field_9236) {
         if (!player.method_7325()) {
            int oldViewers = this.viewers++;
            if (oldViewers == 0) {
               this.isOpened = true;
               this.hasBeenOpened = true;
               this.method_5431();
               ChestOpenSync.sendToAll((class_3218)this.field_11863, this.field_11867, true);
            }
         }
      }
   }

   public void method_5432(class_1657 player) {
      if (this.field_11863 != null && !this.field_11863.field_9236) {
         if (!player.method_7325()) {
            int oldViewers = this.viewers--;
            if (oldViewers == 1) {
               this.isOpened = false;
               this.method_5431();
               ChestOpenSync.sendToAll((class_3218)this.field_11863, this.field_11867, false);
            }

            if (this.viewers < 0) {
               this.viewers = 0;
            }
         }
      }
   }

   protected void method_11014(class_2487 nbt, class_7874 registryLookup) {
      super.method_11014(nbt, registryLookup);
      this.isOpened = nbt.method_10577("isOpened");
      this.hasBeenOpened = nbt.method_10577("hasBeenOpened");
      this.shouldDoSpawnAnimation = nbt.method_10577("shouldDoSpawnAnimation");
      this.closeCooldown = nbt.method_10550("closeCooldown");
      this.viewerCount = nbt.method_10550("viewers");
      this.generatedLootPlayers.clear();
      this.personalLoots.clear();
      if (nbt.method_10573("BaseLootTable", 8)) {
         class_2960 id = class_2960.method_60654(nbt.method_10558("BaseLootTable"));
         this.baseLootTableKey = class_5321.method_29179(class_7924.field_50079, id);
      }

      if (this.isMultiplayerEnabled()) {
         class_2499 list = nbt.method_10554("GeneratedLootPlayers", 8);

         for (int i = 0; i < list.size(); i++) {
            this.generatedLootPlayers.add(UUID.fromString(list.method_10608(i)));
         }

         if (nbt.method_10573("PersonalLoots", 9)) {
            class_2499 lootsList = nbt.method_10554("PersonalLoots", 10);

            for (int i = 0; i < lootsList.size(); i++) {
               class_2487 playerLootTag = lootsList.method_10602(i);
               UUID playerId = UUID.fromString(playerLootTag.method_10558("Player"));
               class_2371<class_1799> items = class_2371.method_10213(this.method_5439(), class_1799.field_8037);
               if (playerLootTag.method_10573("Items", 10)) {
                  class_1262.method_5429(playerLootTag.method_10562("Items"), items, registryLookup);
               }

               this.personalLoots.put(playerId, items);
            }
         }
      }
   }

   protected void method_11007(class_2487 nbt, class_7874 registryLookup) {
      super.method_11007(nbt, registryLookup);
      nbt.method_10556("isOpened", this.isOpened);
      nbt.method_10556("hasBeenOpened", this.hasBeenOpened);
      nbt.method_10556("shouldDoSpawnAnimation", this.shouldDoSpawnAnimation);
      nbt.method_10569("closeCooldown", this.closeCooldown);
      nbt.method_10569("viewers", this.viewerCount);
      class_2499 list = new class_2499();

      for (UUID id : this.generatedLootPlayers) {
         list.add(class_2519.method_23256(id.toString()));
      }

      nbt.method_10566("GeneratedLootPlayers", list);
      if (this.baseLootTableKey != null) {
         nbt.method_10582("BaseLootTable", this.baseLootTableKey.method_29177().toString());
      }

      if (this.isMultiplayerEnabled()) {
         class_2499 lootsList = new class_2499();

         for (Entry<UUID, class_2371<class_1799>> entry : this.personalLoots.entrySet()) {
            class_2487 playerLootTag = new class_2487();
            playerLootTag.method_10582("Player", entry.getKey().toString());
            class_2487 itemsTag = new class_2487();
            class_1262.method_5426(itemsTag, entry.getValue(), registryLookup);
            playerLootTag.method_10566("Items", itemsTag);
            lootsList.add(playerLootTag);
         }

         nbt.method_10566("PersonalLoots", lootsList);
      }
   }

   public class_2487 method_16887(class_7874 registryLookup) {
      return this.method_38244(registryLookup);
   }

   public class_2596<class_2602> method_38235() {
      return class_2622.method_38585(this);
   }

   public void method_5431() {
      super.method_5431();
   }

   public void setShouldDoSpawnAnimation(boolean value) {
      this.shouldDoSpawnAnimation = value;
      this.method_5431();
      this.sync();
   }

   @Environment(EnvType.CLIENT)
   public void playChestSound(class_3414 soundEvent) {
      if (this.field_11863 != null) {
         double x = this.field_11867.method_10263() + 0.5;
         double y = this.field_11867.method_10264() + 0.5;
         double z = this.field_11867.method_10260() + 0.5;
         this.field_11863.method_8486(x, y, z, soundEvent, class_3419.field_15245, 1.0F, this.field_11863.field_9229.method_43057() * 0.1F + 0.9F, false);
      }
   }

   @Environment(EnvType.CLIENT)
   public void playOpenSound() {
      this.playChestSound(class_3417.field_14982);
   }

   @Environment(EnvType.CLIENT)
   public void playCloseSound() {
      this.playChestSound(class_3417.field_14823);
   }

   public void spawnChestParticles() {
      if (this.field_11863 != null) {
         double posX = this.field_11867.method_10263() + 0.5;
         double posY = this.field_11867.method_10264();
         double posZ = this.field_11867.method_10260() + 0.5;
         class_2338 blockPos = this.field_11867.method_10074();
         class_2680 blockState = this.field_11863.method_8320(blockPos);
         if (!blockState.method_26215()) {
            for (int i = 0; i < 6; i++) {
               double offsetX = (this.field_11863.field_9229.method_43058() - 0.5) * 0.3;
               double offsetZ = (this.field_11863.field_9229.method_43058() - 0.5) * 0.3;
               double velocityY = 0.15;
               this.field_11863.method_8406(new class_2388(class_2398.field_11217, blockState), posX + offsetX, posY, posZ + offsetZ, 0.0, velocityY, 0.0);
            }
         }
      }
   }

   public class_2561 method_5476() {
      return this.getChestTitle();
   }

   public class_1703 createMenu(int syncId, class_1661 playerInventory, class_1657 player) {
      if (!this.isMultiplayerEnabled()) {
         return new ModChestScreenHandler(syncId, playerInventory, this);
      }

      if (this.isSharedChest()) {
         System.out.println(this.getLogPrefix() + " Opening shared chest for " + player.method_5477().getString());
         return new ModChestScreenHandler(syncId, playerInventory, this);
      }

      class_2371<class_1799> loot = this.personalLoots.get(player.method_5667());
      if (loot == null) {
         loot = class_2371.method_10213(this.method_5439(), class_1799.field_8037);
         this.personalLoots.put(player.method_5667(), loot);
      }

      return new ModChestScreenHandler(syncId, playerInventory, loot, this.field_11867);
   }

   public class_2338 getScreenOpeningData(class_3222 player) {
      return this.method_11016();
   }
}
