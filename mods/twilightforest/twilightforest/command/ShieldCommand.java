package twilightforest.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import twilightforest.beanification.Component;
import twilightforest.components.entity.FortificationShieldAttachment;
import twilightforest.init.TFDataAttachments;

@Component
public class ShieldCommand {
   public LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("shield").requires(cs -> cs.hasPermission(2)))
         .then(
            ((RequiredArgumentBuilder)Commands.argument("target", EntityArgument.entity())
                  .then(
                     Commands.literal("set")
                        .then(
                           ((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer())
                                 .executes(ctx -> this.set(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount"), true)))
                              .then(
                                 Commands.argument("temp", BoolArgumentType.bool())
                                    .executes(
                                       ctx -> this.set(
                                          EntityArgument.getEntity(ctx, "target"),
                                          IntegerArgumentType.getInteger(ctx, "amount"),
                                          BoolArgumentType.getBool(ctx, "temp")
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  Commands.literal("add")
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer())
                              .executes(ctx -> this.add(EntityArgument.getEntity(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount"), true)))
                           .then(
                              Commands.argument("temp", BoolArgumentType.bool())
                                 .executes(
                                    ctx -> this.add(
                                       EntityArgument.getEntity(ctx, "target"),
                                       IntegerArgumentType.getInteger(ctx, "amount"),
                                       BoolArgumentType.getBool(ctx, "temp")
                                    )
                                 )
                           )
                     )
               )
         );
   }

   private int add(Entity e, int num, boolean temporary) {
      if (e instanceof LivingEntity living) {
         ((FortificationShieldAttachment)living.getData(TFDataAttachments.FORTIFICATION_SHIELDS)).addShields(living, num, temporary);
      }

      return 1;
   }

   private int set(Entity e, int num, boolean temporary) {
      if (e instanceof LivingEntity living) {
         ((FortificationShieldAttachment)living.getData(TFDataAttachments.FORTIFICATION_SHIELDS)).setShields(living, num, temporary);
      }

      return 1;
   }
}
