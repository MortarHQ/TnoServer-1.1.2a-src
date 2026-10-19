package twilightforest.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.FakePlayer;
import twilightforest.TFRegistries;
import twilightforest.beanification.Component;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.modifiers.InsertableTravellersModifier;
import twilightforest.item.travellers_gear.modifiers.TravellersModifiable;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;

@Component
public class TravellersGearCommand {
   private static final DynamicCommandExceptionType ERROR_INVALID_MODIFIER = new DynamicCommandExceptionType(
      p_304101_ -> net.minecraft.network.chat.Component.translatableEscape("commands.tffeature.invalid_modifier", new Object[]{p_304101_})
   );
   private final SimpleCommandExceptionType ERROR_NOT_RUN_BY_PLAYER = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.not_player")
   );
   private final SimpleCommandExceptionType ERROR_NOT_HOLDING_GEAR = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.not_travellers_gear")
   );
   private final SimpleCommandExceptionType ERROR_TOO_MANY_MODIFIERS = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.too_many_modifiers")
   );
   private final Function<net.minecraft.network.chat.Component, SimpleCommandExceptionType> ERROR_NO_MODIFIER = component -> new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.no_modifier", new Object[]{component})
   );
   private final Function<net.minecraft.network.chat.Component, SimpleCommandExceptionType> ERROR_HAS_MODIFIER = component -> new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.has_modifier", new Object[]{component})
   );
   private final Function<net.minecraft.network.chat.Component, SimpleCommandExceptionType> ERROR_WRONG_SLOT = component -> new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.wrong_modifier_slot", new Object[]{component})
   );
   private final SimpleCommandExceptionType ERROR_ABILITY = new SimpleCommandExceptionType(
      net.minecraft.network.chat.Component.translatable("commands.tffeature.ability_modifier")
   );

   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("travellers_gear")
               .requires(cs -> cs.hasPermission(2)))
            .then(
               Commands.literal("add_modifier")
                  .then(
                     Commands.argument("modifier", ResourceKeyArgument.key(TFRegistries.Keys.TRAVELLERS_MODIFIERS))
                        .executes(
                           context -> this.addModifier(
                              (CommandSourceStack)context.getSource(),
                              ResourceKeyArgument.resolveKey(context, "modifier", TFRegistries.Keys.TRAVELLERS_MODIFIERS, ERROR_INVALID_MODIFIER)
                           )
                        )
                  )
            ))
         .then(
            Commands.literal("remove_modifier")
               .then(
                  Commands.argument("modifier", ResourceKeyArgument.key(TFRegistries.Keys.TRAVELLERS_MODIFIERS))
                     .executes(
                        context -> this.removeModifier(
                           (CommandSourceStack)context.getSource(),
                           ResourceKeyArgument.resolveKey(context, "modifier", TFRegistries.Keys.TRAVELLERS_MODIFIERS, ERROR_INVALID_MODIFIER)
                        )
                     )
               )
         );
   }

   private int addModifier(CommandSourceStack source, Reference<TravellersModifier> modifier) throws CommandSyntaxException {
      TravellersGearCommand.Context ctx = this.validate(source, modifier);
      if (TravellersModifiersManager.countInsertableModifiers(source.registryAccess(), ctx.stack()) >= ctx.item().getModifierSlots()) {
         throw this.ERROR_TOO_MANY_MODIFIERS.create();
      }

      if (TravellersModifiersManager.hasTravellersModifier(source.registryAccess(), ctx.stack(), modifier.key())) {
         throw this.ERROR_HAS_MODIFIER.apply(ctx.modKey()).create();
      }

      if (!((TravellersModifier)modifier.value()).group().test(ctx.player().getEquipmentSlotForItem(ctx.stack()))) {
         throw this.ERROR_WRONG_SLOT.apply(ctx.modKey()).create();
      }

      TravellersModifiersManager.addModifier(source.registryAccess(), ctx.stack(), modifier.key());
      source.sendSuccess(
         () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.added_modifier", new Object[]{ctx.modKey(), ctx.stack().getHoverName()}),
         true
      );
      return 1;
   }

   private int removeModifier(CommandSourceStack source, Reference<TravellersModifier> modifier) throws CommandSyntaxException {
      TravellersGearCommand.Context ctx = this.validate(source, modifier);
      if (!TravellersModifiersManager.hasTravellersModifier(source.registryAccess(), ctx.stack(), modifier.key())) {
         throw this.ERROR_NO_MODIFIER.apply(ctx.modKey()).create();
      }

      ((InsertableTravellersModifier)modifier.value()).removeModifier(ctx.stack());
      source.sendSuccess(
         () -> net.minecraft.network.chat.Component.translatable("commands.tffeature.removed_modifier", new Object[]{ctx.modKey(), ctx.stack().getHoverName()}),
         true
      );
      return 1;
   }

   private TravellersGearCommand.Context validate(CommandSourceStack source, Reference<TravellersModifier> modifier) throws CommandSyntaxException {
      if (!(source.getEntity() instanceof Player player && !(player instanceof FakePlayer))) {
         throw this.ERROR_NOT_RUN_BY_PLAYER.create();
      } else if (!(player.getMainHandItem().getItem() instanceof TravellersModifiable armor)) {
         throw this.ERROR_NOT_HOLDING_GEAR.create();
      } else {
         if (((TravellersModifier)modifier.value()).isAbility()) {
            throw this.ERROR_ABILITY.create();
         }

         net.minecraft.network.chat.Component modKey = TravellersModifiersManager.getModifierTooltipComponent(modifier);
         return new TravellersGearCommand.Context(player, player.getMainHandItem(), armor, modKey);
      }
   }

   private record Context(Player player, ItemStack stack, TravellersModifiable item, net.minecraft.network.chat.Component modKey) {
   }
}
