package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tensura_tno.compat.RandomSkillBookMessageGuard;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Pseudo
@Mixin(targets = "com.sjyueluo.tensura_skill_books.item.SkillStudyBookItem", remap = false)
public abstract class SkillStudyBookRandomMessageMixin {
   @WrapOperation(
      method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
      at = @At(
         value = "INVOKE",
         target = "Lcom/sjyueluo/tensura_skill_books/item/SkillStudyBookItem;handleServerRandomUse(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lcom/sjyueluo/tensura_skill_books/catalog/SkillCategory;)V"
      ),
      remap = false,
      require = 1
   )
   private void tensuraTno$collectRandomBookFailures(
      @Coerce Object book, ServerLevel level, Player player, ItemStack stack, @Coerce Object category, Operation<Void> original
   ) {
      RandomSkillBookMessageGuard.duringRandomUse(player, () -> original.call(new Object[]{book, level, player, stack, category}));
   }

   @WrapOperation(
      method = "handleServerRandomUse",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"),
      remap = false,
      require = 1
   )
   private static void tensuraTno$reportFinalRandomBookResult(Player player, Component message, boolean actionBar, Operation<Void> original) {
      String key = message.getContents() instanceof TranslatableContents contents ? contents.getKey() : null;
      RandomSkillBookMessageGuard.displayRandomResult(player, key, () -> original.call(new Object[]{player, message, actionBar}));
   }
}
