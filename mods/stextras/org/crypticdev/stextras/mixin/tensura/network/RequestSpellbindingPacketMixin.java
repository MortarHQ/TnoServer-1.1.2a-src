package org.crypticdev.stextras.mixin.tensura.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import io.github.manasmods.tensura.menu.SpellbindingMenu;
import io.github.manasmods.tensura.network.c2s.RequestSpellbindingPacket;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RequestSpellbindingPacket.class, remap = false)
public abstract class RequestSpellbindingPacketMixin {
   @Redirect(
      method = "handle(Ldev/architectury/networking/NetworkManager$PacketContext;)V",
      at = @At(value = "INVOKE", target = "Ldev/architectury/networking/NetworkManager$PacketContext;queue(Ljava/lang/Runnable;)V"),
      require = 0,
      remap = false
   )
   private void stextras$onSpellbindingQueue(PacketContext context, Runnable originalRunnable) {
      context.queue(
         () -> {
            ResourceLocation skillId = this.stextras$packetSkillId();
            boolean adding = this.stextras$packetAdding();
            Player player = context.getPlayer();
            boolean hadBindingBefore = false;
            boolean wasUnboundTome = false;
            ItemStack beforeStack = ItemStack.EMPTY;
            if (adding && skillId != null && player != null && player.containerMenu instanceof SpellbindingMenu menu) {
               beforeStack = menu.getBlockEntity().getItem(0).copy();
               hadBindingBefore = stextras$hasBoundSkill(beforeStack, skillId);
               wasUnboundTome = beforeStack.is((Item)TensuraMaterialItems.UNBOUND_TOME.get());
               originalRunnable.run();
               if (!hadBindingBefore) {
                  player = context.getPlayer();
                  if (player instanceof ServerPlayer serverPlayer) {
                     if (player.containerMenu instanceof SpellbindingMenu afterMenu) {
                        ItemStack afterStack = afterMenu.getBlockEntity().getItem(0);
                        boolean nowBoundToSpellCastItem = stextras$hasBoundSkill(afterStack, skillId);
                        boolean unboundTomeConverted = wasUnboundTome
                           && !afterStack.isEmpty()
                           && !afterStack.is((Item)TensuraMaterialItems.UNBOUND_TOME.get())
                           && !ItemStack.isSameItemSameComponents(beforeStack, afterStack);
                        if (nowBoundToSpellCastItem || unboundTomeConverted) {
                           STPlayerEvents.fireSpellBinding(serverPlayer, skillId);
                        }
                     }
                  }
               }
            } else {
               originalRunnable.run();
            }
         }
      );
   }

   @Unique
   private static boolean stextras$hasBoundSkill(ItemStack stack, ResourceLocation skillId) {
      if (stack != null && !stack.isEmpty() && skillId != null) {
         ResourceLocation singleSkill = (ResourceLocation)stack.get((DataComponentType)TensuraDataComponents.SKILL.get());
         if (Objects.equals(singleSkill, skillId)) {
            return true;
         }

         List<ResourceLocation> skillList = (List<ResourceLocation>)stack.get((DataComponentType)TensuraDataComponents.SKILL_LIST.get());
         return skillList != null && skillList.contains(skillId);
      } else {
         return false;
      }
   }

   @Unique
   @Nullable
   private ResourceLocation stextras$packetSkillId() {
      Object packet = this;
      ResourceLocation methodValue = stextras$readResourceLocationMethod(packet, "id", "skillId", "abilityId");
      return methodValue != null ? methodValue : stextras$readFirstResourceLocationField(packet, "id", "skillId", "abilityId");
   }

   @Unique
   private boolean stextras$packetAdding() {
      Object packet = this;
      Boolean methodValue = stextras$readBooleanMethod(packet, "adding", "isAdding", "add");
      if (methodValue != null) {
         return methodValue;
      }

      Boolean fieldValue = stextras$readFirstBooleanField(packet, "adding", "isAdding", "add");
      return fieldValue != null && fieldValue;
   }

   @Unique
   @Nullable
   private static ResourceLocation stextras$readResourceLocationMethod(Object target, String... names) {
      Class<?> type = target.getClass();

      for (String name : names) {
         try {
            Method method = type.getDeclaredMethod(name);
            if (method.getParameterCount() == 0) {
               method.setAccessible(true);
               if (method.invoke(target) instanceof ResourceLocation resourceLocation) {
                  return resourceLocation;
               }
            }
         } catch (ReflectiveOperationException | SecurityException var10) {
         }
      }

      return null;
   }

   @Unique
   @Nullable
   private static ResourceLocation stextras$readFirstResourceLocationField(Object target, String... preferredNames) {
      Class<?> type = target.getClass();

      for (String name : preferredNames) {
         try {
            Field field = type.getDeclaredField(name);
            if (!Modifier.isStatic(field.getModifiers())) {
               field.setAccessible(true);
               if (field.get(target) instanceof ResourceLocation resourceLocation) {
                  return resourceLocation;
               }
            }
         } catch (ReflectiveOperationException | SecurityException var11) {
         }
      }

      for (Field field : type.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers())) {
            try {
               field.setAccessible(true);
               if (field.get(target) instanceof ResourceLocation resourceLocation) {
                  return resourceLocation;
               }
            } catch (ReflectiveOperationException | SecurityException var10) {
            }
         }
      }

      return null;
   }

   @Unique
   @Nullable
   private static Boolean stextras$readBooleanMethod(Object target, String... names) {
      Class<?> type = target.getClass();

      for (String name : names) {
         try {
            Method method = type.getDeclaredMethod(name);
            if (method.getReturnType() == boolean.class && method.getParameterCount() == 0) {
               method.setAccessible(true);
               return (Boolean)method.invoke(target);
            }
         } catch (ReflectiveOperationException | SecurityException var8) {
         }
      }

      return null;
   }

   @Unique
   @Nullable
   private static Boolean stextras$readFirstBooleanField(Object target, String... preferredNames) {
      Class<?> type = target.getClass();

      for (String name : preferredNames) {
         try {
            Field field = type.getDeclaredField(name);
            if (field.getType() == boolean.class && !Modifier.isStatic(field.getModifiers())) {
               field.setAccessible(true);
               return field.getBoolean(target);
            }
         } catch (ReflectiveOperationException | SecurityException var8) {
         }
      }

      for (Field field : type.getDeclaredFields()) {
         if (field.getType() == boolean.class && !Modifier.isStatic(field.getModifiers())) {
            try {
               field.setAccessible(true);
               return field.getBoolean(target);
            } catch (ReflectiveOperationException | SecurityException var9) {
            }
         }
      }

      return null;
   }
}
