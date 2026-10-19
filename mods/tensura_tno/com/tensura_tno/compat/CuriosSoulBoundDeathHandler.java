package com.tensura_tno.compat;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class CuriosSoulBoundDeathHandler {
   private static final String SAVE_KEY = "tensura_tno:curios_soulbound_extra";
   private static final int RESTORE_DELAY = 2;
   private static final int RESTORE_TIMEOUT = 60;
   private static final TagKey<Enchantment> YIGD_SOULBOUND_TAG = TagKey.create(
      Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "soulbound")
   );

   public static void register() {
      if (!ModList.get().isLoaded("curios")) {
         TensuraTNOMod.LOGGER.info("[TensuraTNO] Curios 未加载，跳过额外槽位灵魂绑定死亡修复。");
      } else if (!ModList.get().isLoaded("yigd")) {
         TensuraTNOMod.LOGGER.info("[TensuraTNO] YIGD 未加载，跳过额外槽位灵魂绑定死亡修复。");
      } else {
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, LivingDeathEvent.class, CuriosSoulBoundDeathHandler::onLivingDeath);
         NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, LivingDropsEvent.class, CuriosSoulBoundDeathHandler::onLivingDrops);
         NeoForge.EVENT_BUS.addListener(CuriosSoulBoundDeathHandler::onPlayerClone);
         NeoForge.EVENT_BUS.addListener(CuriosSoulBoundDeathHandler::onPlayerTick);
         TensuraTNOMod.LOGGER.info("[TensuraTNO] 已注册 Curios 额外槽位灵魂绑定死亡修复（YIGD 模式）。");
      }
   }

   private static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (!player.isSpectator()) {
            if (!player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
               try {
                  ListTag saved = extractSoulboundFromExtraSlots(player);
                  if (!saved.isEmpty()) {
                     player.getPersistentData().put("tensura_tno:curios_soulbound_extra", saved);
                     TensuraTNOMod.LOGGER.debug("[TensuraTNO] 死亡提取：从额外槽位取出 {} 个灵魂绑定物品（玩家: {}）", saved.size(), player.getName().getString());
                  }
               } catch (Exception e) {
                  TensuraTNOMod.LOGGER.error("[TensuraTNO] onLivingDeath 出错", e);
               }
            }
         }
      }
   }

   private static void onLivingDrops(LivingDropsEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.getPersistentData().contains("tensura_tno:curios_soulbound_extra", 9)) {
            ListTag saved = player.getPersistentData().getList("tensura_tno:curios_soulbound_extra", 10);
            if (saved.isEmpty()) {
               player.getPersistentData().remove("tensura_tno:curios_soulbound_extra");
            } else {
               try {
                  Class<?> yigdClass = Class.forName("com.b1n_ry.yigd.Yigd");
                  Field field = yigdClass.getField("UNFINISHED_DEATHS");
                  Map<UUID, Object> unfinishedDeaths = (Map<UUID, Object>)field.get(null);
                  Object deathHandler = unfinishedDeaths.get(player.getUUID());
                  if (deathHandler == null) {
                     TensuraTNOMod.LOGGER.debug("[TensuraTNO] 未找到 YIGD DeathHandler，物品保留待 tick 处理器恢复（玩家: {}）", player.getName().getString());
                     return;
                  }

                  Method addItem = deathHandler.getClass().getMethod("addItem", ItemStack.class);
                  int count = 0;

                  for (int i = 0; i < saved.size(); i++) {
                     ItemStack stack = ItemStack.parse(player.registryAccess(), saved.getCompound(i).getCompound("item")).orElse(ItemStack.EMPTY);
                     if (!stack.isEmpty()) {
                        addItem.invoke(deathHandler, stack);
                        count++;
                     }
                  }

                  player.getPersistentData().remove("tensura_tno:curios_soulbound_extra");
                  TensuraTNOMod.LOGGER.debug("[TensuraTNO] 已将 {} 个灵魂绑定物品注入 YIGD 墓碑（玩家: {}）", count, player.getName().getString());
               } catch (Exception e) {
                  TensuraTNOMod.LOGGER.error("[TensuraTNO] onLivingDrops 注入失败，物品将由 tick 处理器兜底（玩家: {}）", player.getName().getString(), e);
               }
            }
         }
      }
   }

   private static void onPlayerClone(Clone event) {
      if (event.isWasDeath()) {
         Player oldPlayer = event.getOriginal();
         Player newPlayer = event.getEntity();
         if (oldPlayer.getPersistentData().contains("tensura_tno:curios_soulbound_extra", 9)) {
            ListTag saved = oldPlayer.getPersistentData().getList("tensura_tno:curios_soulbound_extra", 10);
            oldPlayer.getPersistentData().remove("tensura_tno:curios_soulbound_extra");
            if (!saved.isEmpty()) {
               newPlayer.getPersistentData().put("tensura_tno:curios_soulbound_extra", saved.copy());
               TensuraTNOMod.LOGGER.debug("[TensuraTNO] Clone：转移 {} 个未处理物品到新玩家（玩家: {}）", saved.size(), newPlayer.getName().getString());
            }
         }
      }
   }

   private static void onPlayerTick(Pre event) {
      Player player = event.getEntity();
      if (player instanceof ServerPlayer) {
         if (player.getPersistentData().contains("tensura_tno:curios_soulbound_extra", 9)) {
            ListTag saved = player.getPersistentData().getList("tensura_tno:curios_soulbound_extra", 10);
            if (saved.isEmpty()) {
               player.getPersistentData().remove("tensura_tno:curios_soulbound_extra");
            } else {
               ListTag remaining = new ListTag();
               List<ItemStack> giveToInventory = new ArrayList<>();

               for (int i = 0; i < saved.size(); i++) {
                  CompoundTag entry = saved.getCompound(i).copy();
                  int ticks = entry.getInt("ticks") + 1;
                  entry.putInt("ticks", ticks);
                  if (ticks <= 2) {
                     remaining.add(entry);
                  } else if (!tryRestoreToSlot(entry, player)) {
                     if (ticks <= 62) {
                        remaining.add(entry);
                     } else {
                        ItemStack stack = ItemStack.parse(player.registryAccess(), entry.getCompound("item")).orElse(ItemStack.EMPTY);
                        if (!stack.isEmpty()) {
                           giveToInventory.add(stack);
                        }
                     }
                  }
               }

               if (remaining.isEmpty()) {
                  player.getPersistentData().remove("tensura_tno:curios_soulbound_extra");
               } else {
                  player.getPersistentData().put("tensura_tno:curios_soulbound_extra", remaining);
               }

               for (ItemStack stack : giveToInventory) {
                  if (!player.getInventory().add(stack)) {
                     player.drop(stack, false);
                  }
               }

               if (!giveToInventory.isEmpty()) {
                  TensuraTNOMod.LOGGER.debug("[TensuraTNO] {} 个物品超时无法恢复，已放入背包/掉落（玩家: {}）", giveToInventory.size(), player.getName().getString());
               }
            }
         }
      }
   }

   private static ListTag extractSoulboundFromExtraSlots(ServerPlayer player) throws Exception {
      ListTag result = new ListTag();
      Class<?> api = Class.forName("top.theillusivec4.curios.api.CuriosApi");
      Method getInv = api.getMethod("getCuriosInventory", LivingEntity.class);
      Optional<Object> optHandler = (Optional<Object>)getInv.invoke(null, player);
      if (optHandler.isEmpty()) {
         return result;
      }

      Map<String, Object> curios = (Map<String, Object>)optHandler.get().getClass().getMethod("getCurios").invoke(optHandler.get());

      for (Entry<String, Object> entry : curios.entrySet()) {
         String slotId = entry.getKey();
         Object stacksHandler = entry.getValue();
         int baseCount = getBaseSlotCount(api, slotId, player);
         extractSoulboundFromHandler(player, slotId, stacksHandler, false, baseCount, result);

         try {
            extractSoulboundFromHandler(player, slotId, stacksHandler, true, baseCount, result);
         } catch (NoSuchMethodException var12) {
         }
      }

      return result;
   }

   private static void extractSoulboundFromHandler(ServerPlayer player, String slotId, Object stacksHandler, boolean cosmetic, int baseCount, ListTag out) throws Exception {
      String methodName = cosmetic ? "getCosmeticStacks" : "getStacks";
      IItemHandlerModifiable stacks = (IItemHandlerModifiable)stacksHandler.getClass().getMethod(methodName).invoke(stacksHandler);

      for (int i = 0; i < stacks.getSlots(); i++) {
         if (i >= baseCount) {
            ItemStack stack = stacks.getStackInSlot(i);
            if (!stack.isEmpty() && isSoulbound(stack)) {
               CompoundTag record = new CompoundTag();
               record.putString("s", slotId);
               record.putInt("i", i);
               record.putBoolean("c", cosmetic);
               record.putInt("ticks", 0);
               record.put("item", stack.save(player.registryAccess()));
               out.add(record);
               stacks.setStackInSlot(i, ItemStack.EMPTY);
            }
         }
      }
   }

   private static int getBaseSlotCount(Class<?> api, String slotId, ServerPlayer player) {
      try {
         Method getSlot = api.getMethod("getSlot", String.class, Level.class);
         Optional<Object> opt = (Optional<Object>)getSlot.invoke(null, slotId, player.level());
         if (opt.isPresent()) {
            return (Integer)opt.get().getClass().getMethod("getSize").invoke(opt.get());
         }
      } catch (Exception var5) {
      }

      return 1;
   }

   private static boolean tryRestoreToSlot(CompoundTag record, Player player) {
      if (player instanceof ServerPlayer sp) {
         String slotId = record.getString("s");
         int idx = record.getInt("i");
         boolean cosmetic = record.getBoolean("c");
         ItemStack stack = ItemStack.parse(player.registryAccess(), record.getCompound("item")).orElse(ItemStack.EMPTY);
         if (stack.isEmpty()) {
            return true;
         }

         try {
            Class<?> api = Class.forName("top.theillusivec4.curios.api.CuriosApi");
            Optional<Object> optHandler = (Optional<Object>)api.getMethod("getCuriosInventory", LivingEntity.class).invoke(null, sp);
            if (optHandler.isEmpty()) {
               return false;
            }

            Map<String, Object> curios = (Map<String, Object>)optHandler.get().getClass().getMethod("getCurios").invoke(optHandler.get());
            Object sh = curios.get(slotId);
            if (sh == null) {
               return false;
            }

            IItemHandlerModifiable stacks = (IItemHandlerModifiable)sh.getClass().getMethod(cosmetic ? "getCosmeticStacks" : "getStacks").invoke(sh);
            if (idx >= stacks.getSlots()) {
               return false;
            }

            if (!stacks.getStackInSlot(idx).isEmpty()) {
               return false;
            }

            stacks.setStackInSlot(idx, stack);
            return true;
         } catch (Exception e) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean isSoulbound(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      if (EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
         return true;
      }

      for (Holder<Enchantment> h : EnchantmentHelper.getEnchantmentsForCrafting(stack).keySet()) {
         if (h.is(YIGD_SOULBOUND_TAG)) {
            return true;
         }
      }

      return false;
   }
}
