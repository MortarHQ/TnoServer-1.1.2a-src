package twilightforest.events;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredItem;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.enums.BlockLoggingEnum;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.network.SpawnCharmPacket;
import twilightforest.util.TFItemStackUtils;

@Component
public class CharmEvents {
   public static final String CHARM_INV_TAG = "TFCharmInventory";
   public static final String CASKET_DAMAGE_TAG = "CasketDamage";
   public static final String CONSUMED_CHARM_TAG = "CharmStack";

   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::applyCharmOfLife);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, this::applyKeepingAndCasket);
      NeoForge.EVENT_BUS.addListener(this::returnItemsOnRespawn);
   }

   private void applyCharmOfLife(LivingDeathEvent event) {
      LivingEntity living = event.getEntity();
      if (!event.isCanceled()
         && !living.level().isClientSide()
         && living instanceof Player player
         && !(living instanceof FakePlayer)
         && !player.isCreative()
         && !player.isSpectator()) {
         if (handleCharmOfLife(player)) {
            event.setCanceled(true);
         }
      }
   }

   private void applyKeepingAndCasket(LivingDeathEvent event) {
      LivingEntity living = event.getEntity();
      if (!event.isCanceled()
         && !living.level().isClientSide()
         && living instanceof Player player
         && !(living instanceof FakePlayer)
         && !player.isCreative()
         && !player.isSpectator()) {
         if (!living.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            handleCharmOfKeeping(player);
            stockKeepsakeCasket(player);
         }
      }
   }

   private void returnItemsOnRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         if (!event.isEndConquered()) {
            returnStoredItems(serverPlayer);
         }
      }
   }

   private static boolean handleCharmOfLife(Player player) {
      boolean charm2 = TFItemStackUtils.consumeInventoryItem(player, (ItemLike)TFItems.CHARM_OF_LIFE_2.get(), getPlayerData(player), false)
         || hasCharmCurio((Item)TFItems.CHARM_OF_LIFE_2.get(), player);
      boolean charm1 = !charm2
         && (
            TFItemStackUtils.consumeInventoryItem(player, (ItemLike)TFItems.CHARM_OF_LIFE_1.get(), getPlayerData(player), false)
               || hasCharmCurio((Item)TFItems.CHARM_OF_LIFE_1.get(), player)
         );
      if (!charm2 && !charm1) {
         return false;
      }

      if (charm1) {
         player.setHealth(8.0F);
         player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
      }

      if (charm2) {
         player.setHealth(player.getMaxHealth());
         player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3));
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0));
         player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0));
      }

      if (player instanceof ServerPlayer serverPlayer) {
         PacketDistributor.sendToPlayer(
            serverPlayer,
            new SpawnCharmPacket(
               new ItemStack(charm1 ? (ItemLike)TFItems.CHARM_OF_LIFE_1.get() : (ItemLike)TFItems.CHARM_OF_LIFE_2.get()), TFSounds.CHARM_LIFE.getKey()
            ),
            new CustomPacketPayload[0]
         );
         serverPlayer.awardStat((ResourceLocation)TFStats.LIFE_CHARMS_ACTIVATED.get());
      }

      return true;
   }

   private static void handleCharmOfKeeping(Player player) {
      Inventory keepInventory = new Inventory(player);
      ListTag tagList = new ListTag();
      if (!applyCharm(TFItems.CHARM_OF_KEEPING_3, keepInventory, player, player.getInventory().items)
         && !applyCharm(TFItems.CHARM_OF_KEEPING_2, keepInventory, player, player.getInventory().items.subList(0, 9))) {
         int i = player.getInventory().selected;
         if (Inventory.isHotbarSlot(i)) {
            applyCharm(TFItems.CHARM_OF_KEEPING_1, keepInventory, player, NonNullList.of((ItemStack)player.getInventory().items.get(i), new ItemStack[0]));
         }
      }

      for (int i = 0; i < player.getInventory().items.size(); i++) {
         ItemStack stack = (ItemStack)player.getInventory().items.get(i);
         if (stack.is(ItemTagGenerator.KEPT_ON_DEATH)) {
            keepInventory.items.set(i, stack.copy());
            player.getInventory().items.set(i, ItemStack.EMPTY);
         }
      }

      for (int i = 0; i < player.getInventory().armor.size(); i++) {
         ItemStack armor = (ItemStack)player.getInventory().armor.get(i);
         if (armor.is(ItemTagGenerator.KEPT_ON_DEATH)) {
            keepInventory.armor.set(i, armor.copy());
            player.getInventory().armor.set(i, ItemStack.EMPTY);
         }
      }

      if (((ItemStack)player.getInventory().offhand.getFirst()).is(ItemTagGenerator.KEPT_ON_DEATH)) {
         keepInventory.offhand.set(0, ((ItemStack)player.getInventory().offhand.getFirst()).copy());
         player.getInventory().offhand.set(0, ItemStack.EMPTY);
      }

      if (!keepInventory.isEmpty()) {
         keepInventory.save(tagList);
         getPlayerData(player).put("TFCharmInventory", tagList);
      }
   }

   private static boolean applyCharm(DeferredItem<Item> charm, Inventory keptInventory, Player player, List<ItemStack> inventorySlots) {
      List<ItemStack> mergedCheck = new ArrayList<>(inventorySlots);
      mergedCheck.addAll(player.getInventory().armor);
      mergedCheck.addAll(player.getInventory().offhand);
      if (mergedCheck.stream().filter(stack -> !stack.is(charm)).allMatch(ItemStack::isEmpty)) {
         return false;
      }

      if (!TFItemStackUtils.consumeInventoryItem(player, charm, getPlayerData(player), true) && !hasCharmCurio((Item)charm.value(), player)) {
         return false;
      }

      boolean keptACasket = keepWholeListAndCheckCasket(keptInventory.items, inventorySlots, charm == TFItems.CHARM_OF_KEEPING_3);
      keptACasket = keepWholeListAndCheckCasket(keptInventory.armor, player.getInventory().armor, keptACasket);
      keepWholeListAndCheckCasket(keptInventory.offhand, player.getInventory().offhand, keptACasket);
      return true;
   }

   private static void stockKeepsakeCasket(Player player) {
      if (player.getInventory().hasAnyMatching(stack -> !stack.isEmpty() && !stack.is(TFItems.KEEPSAKE_CASKET))) {
         boolean casketConsumed = TFItemStackUtils.consumeInventoryItem(player, TFBlocks.KEEPSAKE_CASKET, getPlayerData(player), false);
         if (!casketConsumed) {
            return;
         }

         Level level = player.level();
         MutableBlockPos pos = player.blockPosition().mutable();
         if (pos.getY() < level.dimensionType().minY() + 2) {
            pos.setY(level.dimensionType().minY() + 2);
         } else {
            int logicalHeight = player.level().dimensionType().logicalHeight();
            if (pos.getY() > logicalHeight) {
               pos.setY(logicalHeight - 1);
            }
         }

         pos.move(0, -1, 0);

         do {
            pos.move(0, 1, 0);
         } while (!level.getBlockState(pos).canBeReplaced());

         BlockPos immutablePos = pos.immutable();
         FluidState fluidState = level.getFluidState(immutablePos);
         int damage = getPlayerData(player).contains("CasketDamage") ? getPlayerData(player).getInt("CasketDamage") : 0;
         BlockState setState = (BlockState)((BlockState)((BlockState)((Block)TFBlocks.KEEPSAKE_CASKET.get())
                  .defaultBlockState()
                  .setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.getFromFluid(fluidState.getType())))
               .setValue(KeepsakeCasketBlock.BREAKAGE, damage))
            .setValue(KeepsakeCasketBlock.FACING, Direction.from2DDataValue(level.getRandom().nextInt(3)));
         if (player.getRandom().nextFloat() <= 0.15F) {
            if (damage >= 2) {
               setState = ((Block)TFBlocks.SKULL_CHEST.get()).withPropertiesOf(setState);
               TwilightForestMod.LOGGER.debug("{}'s Casket damage value was too high, placing Skull Chest instead", player.getName().getString());
            } else {
               setState = (BlockState)((Block)TFBlocks.KEEPSAKE_CASKET.get()).withPropertiesOf(setState).setValue(KeepsakeCasketBlock.BREAKAGE, ++damage);
               TwilightForestMod.LOGGER.debug("{}'s Casket was randomly damaged, applying new damage", player.getName().getString());
            }
         }

         if (!level.setBlockAndUpdate(immutablePos, setState)) {
            TwilightForestMod.LOGGER.error("Could not place Keepsake Casket at {}", pos);
            return;
         }

         if (!(level.getBlockEntity(immutablePos) instanceof SkullChestBlockEntity casket)) {
            TwilightForestMod.LOGGER.error("Failed to set Keepsake Casket data at {}", pos);
            return;
         }

         if (TFConfig.casketUUIDLocking) {
            casket.owner = new ResolvableProfile(player.getGameProfile());
         } else {
            casket.owner = null;
         }

         String modifiedName = player.getName().getString().substring(0, Math.min(12, player.getName().getString().length()));
         casket.name = net.minecraft.network.chat.Component.literal(
            modifiedName + "'s " + (level.getRandom().nextInt(1000) == 0 ? "Costco Casket" : casket.getDisplayName().getString())
         );
         int casketCapacity = casket.getContainerSize();
         List<ItemStack> list = new ArrayList<>(casketCapacity);
         NonNullList<ItemStack> filler = NonNullList.withSize(4, ItemStack.EMPTY);
         list.addAll(TFItemStackUtils.sortArmorForCasket(player));
         player.getInventory().armor.clear();
         list.addAll(filler);
         list.addAll(player.getInventory().offhand);
         player.getInventory().offhand.clear();
         list.addAll(TFItemStackUtils.sortInvForCasket(player));
         player.getInventory().items.clear();
         casket.setItems(NonNullList.of(ItemStack.EMPTY, (ItemStack[])list.toArray(new ItemStack[casketCapacity])));
         getPlayerData(player).remove("CasketDamage");
      } else {
         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).is(TFItems.KEEPSAKE_CASKET)) {
               Inventory tmp = new Inventory(player);
               tmp.load(getPlayerData(player).getList("TFCharmInventory", 10));
               tmp.add(player.getInventory().getItem(i).copy());
               player.getInventory().setItem(i, ItemStack.EMPTY);
               getPlayerData(player).put("TFCharmInventory", tmp.save(new ListTag()));
            }
         }
      }
   }

   private static void returnStoredItems(Player player) {
      TwilightForestMod.LOGGER.debug("Player {} ({}) respawned and received items held in storage", player.getName().getString(), player.getUUID());
      CompoundTag playerData = getPlayerData(player);
      if (!player.level().isClientSide() && playerData.contains("TFCharmInventory")) {
         ListTag tagList = playerData.getList("TFCharmInventory", 10);
         TFItemStackUtils.loadNoClear(player.registryAccess(), tagList, player.getInventory());
         getPlayerData(player).getList("TFCharmInventory", 10).clear();
         getPlayerData(player).remove("TFCharmInventory");
      }

      if (getPlayerData(player).contains("CharmStack")) {
         ItemStack stack = ItemStack.parseOptional(player.registryAccess(), (CompoundTag)getPlayerData(player).get("CharmStack"));
         if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new SpawnCharmPacket(stack, TFSounds.CHARM_KEEP.getKey()), new CustomPacketPayload[0]);
            serverPlayer.awardStat((ResourceLocation)TFStats.KEEPING_CHARMS_ACTIVATED.get());
         }

         getPlayerData(player).remove("CharmStack");
      }
   }

   public static CompoundTag getPlayerData(Player player) {
      if (!player.getPersistentData().contains("PlayerPersisted")) {
         player.getPersistentData().put("PlayerPersisted", new CompoundTag());
      }

      return player.getPersistentData().getCompound("PlayerPersisted");
   }

   private static boolean keepWholeListAndCheckCasket(NonNullList<ItemStack> transferTo, List<ItemStack> transferFrom, boolean skipCasketCheck) {
      boolean keptCasket = false;

      for (int i = 0; i < transferFrom.size(); i++) {
         ItemStack item = transferFrom.get(i).copy();
         if (!skipCasketCheck && item.is(TFItems.KEEPSAKE_CASKET) && !keptCasket) {
            keptCasket = true;
            if (item.getCount() > 1) {
               item.shrink(1);
               transferTo.set(i, item);
               transferFrom.set(i, item.copyWithCount(1));
            }
         } else {
            transferTo.set(i, item);
            transferFrom.set(i, ItemStack.EMPTY);
         }
      }

      return keptCasket || skipCasketCheck;
   }

   private static boolean hasCharmCurio(Item item, Player player) {
      return ModList.get().isLoaded("curios") ? CuriosCompat.findAndConsumeCurio(item, player) : false;
   }
}
