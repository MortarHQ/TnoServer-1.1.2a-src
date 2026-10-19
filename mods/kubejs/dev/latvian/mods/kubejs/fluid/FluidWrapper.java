package dev.latvian.mods.kubejs.fluid;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import dev.latvian.mods.kubejs.component.DataComponentWrapper;
import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.kubejs.util.RegExpKJS;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.DataComponentFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public interface FluidWrapper {
   TypeInfo TYPE_INFO = TypeInfo.of(FluidStack.class);
   TypeInfo FLUID_TYPE_INFO = TypeInfo.of(Fluid.class);
   TypeInfo INGREDIENT_TYPE_INFO = TypeInfo.of(FluidIngredient.class);
   TypeInfo SIZED_INGREDIENT_TYPE_INFO = TypeInfo.of(SizedFluidIngredient.class);
   SizedFluidIngredient EMPTY_SIZED = new SizedFluidIngredient(FluidIngredient.empty(), 1000);
   DataResult<FluidStack> EMPTY_STACK_RESULT = DataResult.success(FluidStack.EMPTY);
   DataResult<FluidIngredient> EMPTY_INGREDIENT_RESULT = DataResult.success(FluidIngredient.empty());
   DataResult<SizedFluidIngredient> EMPTY_SIZED_RESULT = DataResult.success(EMPTY_SIZED);

   @HideFromJS
   static DataResult<FluidStack> tryWrap(Context cx, Object from) {
      while (from instanceof Wrapper) {
         Wrapper w = (Wrapper)from;
         from = w.unwrap();
      }

      Object var9 = from;
      byte var3 = 0;

      while (true) {
         DataResult var10000;
         switch (SwitchBootstraps.typeSwitch<"typeSwitch",FluidStack,Fluid,Fluid,FluidIngredient,SizedFluidIngredient>(var9, var3)) {
            case -1:
               var10000 = EMPTY_STACK_RESULT;
               break;
            case 0:
               FluidStack s = (FluidStack)var9;
               var10000 = s.isEmpty() ? EMPTY_STACK_RESULT : DataResult.success(s);
               break;
            case 1: {
               Fluid fluid = (Fluid)var9;
               if (!fluid.kjs$isEmpty()) {
                  var3 = 2;
                  continue;
               }

               var10000 = EMPTY_STACK_RESULT;
               break;
            }
            case 2: {
               Fluid fluid = (Fluid)var9;
               var10000 = DataResult.success(new FluidStack(fluid, 1000));
               break;
            }
            case 3:
               FluidIngredient i = (FluidIngredient)var9;
               throw new KubeRuntimeException("Using FluidIngredient in places where FluidStack is expected is dangerous and unsupported!")
                  .source(SourceLine.of(cx));
            case 4:
               SizedFluidIngredient sized = (SizedFluidIngredient)var9;
               throw new KubeRuntimeException("Using SizedFluidIngredient in places where FluidStack is expected is dangerous and unsupported!")
                  .source(SourceLine.of(cx));
            default:
               var10000 = parseString(cx, RegistryAccessContainer.of(cx).nbt(), from.toString());
         }

         return var10000;
      }
   }

   @HideFromJS
   static FluidStack wrap(Context cx, Object from) {
      return (FluidStack)tryWrap(cx, from)
         .getOrThrow(error -> new KubeRuntimeException("Failed to read FluidStack from %s: %s".formatted(from, error)).source(SourceLine.of(cx)));
   }

   static FluidIngredient ingredientOf(FluidIngredient of) {
      return of;
   }

   @Info("Returns an ingredient that accepts the given set of fluids under the given component filter.")
   static FluidIngredient ingredientOf(HolderSet<Fluid> base, DataComponentMap data) {
      return ingredientOf(base, data, false);
   }

   @Info("Returns an ingredient that accepts the given set of items under the given (optionally strict) component filter.")
   static FluidIngredient ingredientOf(HolderSet<Fluid> base, DataComponentMap data, boolean strict) {
      return DataComponentFluidIngredient.of(strict, data, base);
   }

   @HideFromJS
   static DataResult<FluidIngredient> tryWrapIngredient(Context cx, Object from) {
      while (from instanceof Wrapper) {
         Wrapper w = (Wrapper)from;
         from = w.unwrap();
      }

      RegistryAccessContainer registries = RegistryAccessContainer.of(cx);

      return switch (from) {
         case null -> EMPTY_INGREDIENT_RESULT;
         case FluidStack stack when stack.isEmpty() -> EMPTY_INGREDIENT_RESULT;
         case Fluid fluid when fluid.kjs$isEmpty() -> EMPTY_INGREDIENT_RESULT;
         case FluidIngredient in when in.isEmpty() -> EMPTY_INGREDIENT_RESULT;
         case FluidStack stack -> DataResult.success(FluidIngredient.of(new FluidStack[]{stack}));
         case Fluid fluid -> DataResult.success(FluidIngredient.of(new Fluid[]{fluid}));
         case FluidIngredient in -> DataResult.success(in);
         case SizedFluidIngredient s -> DataResult.success(s.ingredient());
         default -> ingredientOfString(cx, registries.nbt(), from.toString());
      };
   }

   @HideFromJS
   static FluidIngredient wrapIngredient(Context cx, Object from) {
      return (FluidIngredient)tryWrapIngredient(cx, from)
         .getOrThrow(error -> new KubeRuntimeException("Failed to read FluidIngredient from %s: %s".formatted(from, error)).source(SourceLine.of(cx)));
   }

   static SizedFluidIngredient sizedIngredientOf(SizedFluidIngredient of) {
      return of;
   }

   static SizedFluidIngredient sizedIngredientOf(FluidIngredient in, int amount) {
      return new SizedFluidIngredient(in, amount);
   }

   @HideFromJS
   static DataResult<SizedFluidIngredient> tryWrapSizedIngredient(Context cx, Object o) {
      RegistryAccessContainer registries = RegistryAccessContainer.of(cx);

      return switch (o) {
         case null -> EMPTY_SIZED_RESULT;
         case FluidStack stack when stack.isEmpty() -> EMPTY_SIZED_RESULT;
         case Fluid fluid when fluid.kjs$isEmpty() -> EMPTY_SIZED_RESULT;
         case FluidIngredient in when in.isEmpty() -> EMPTY_SIZED_RESULT;
         case FluidStack stack -> DataResult.success(SizedFluidIngredient.of(stack));
         case Fluid fluid -> DataResult.success(SizedFluidIngredient.of(fluid, 1000));
         case FluidIngredient in -> DataResult.success(new SizedFluidIngredient(in, 1000));
         case SizedFluidIngredient s -> DataResult.success(s);
         default -> sizedIngredientOfString(cx, registries.nbt(), o.toString());
      };
   }

   @HideFromJS
   static SizedFluidIngredient wrapSizedIngredient(Context cx, Object from) {
      return (SizedFluidIngredient)tryWrapSizedIngredient(cx, from)
         .getOrThrow(error -> new KubeRuntimeException("Failed to read SizedFluidIngredient from %s: %s".formatted(from, error)).source(SourceLine.of(cx)));
   }

   @Info("Returns a FluidStack of the input")
   static FluidStack of(FluidStack o) {
      return o;
   }

   @Info("Returns a FluidStack of the input, with the specified amount")
   static FluidStack of(FluidStack o, int amount) {
      o.setAmount(amount);
      return o;
   }

   @Info("Returns a FluidStack of the input, with the specified data components")
   static FluidStack of(FluidStack o, DataComponentMap components) {
      o.applyComponents(components);
      return o;
   }

   @Info("Returns a FluidStack of the input, with the specified amount and data components")
   static FluidStack of(FluidStack o, int amount, DataComponentMap components) {
      o.setAmount(amount);
      o.applyComponents(components);
      return o;
   }

   static FluidStack water() {
      return water(1000);
   }

   static FluidStack lava() {
      return lava(1000);
   }

   static FluidStack water(int amount) {
      return new FluidStack(Fluids.WATER, amount);
   }

   static FluidStack lava(int amount) {
      return new FluidStack(Fluids.LAVA, amount);
   }

   static Fluid getType(ResourceLocation id) {
      return (Fluid)BuiltInRegistries.FLUID.get(id);
   }

   static List<String> getTypes() {
      ArrayList<String> types = new ArrayList<>();

      for (Fluid fluid : BuiltInRegistries.FLUID) {
         types.add(fluid.kjs$getId());
      }

      return types;
   }

   static FluidStack getEmpty() {
      return FluidStack.EMPTY;
   }

   static boolean exists(ResourceLocation id) {
      return BuiltInRegistries.FLUID.containsKey(id);
   }

   static ResourceLocation getId(Fluid fluid) {
      return BuiltInRegistries.FLUID.getKey(fluid);
   }

   static <T> DataResult<T> readWithContext(Context cx, DynamicOps<Tag> registryOps, String s, FluidWrapper.ReadFn<T> fn, String name) {
      try {
         StringReader reader = new StringReader(s);
         reader.skipWhitespace();
         return fn.read(registryOps, reader);
      } catch (CommandSyntaxException ex) {
         return DataResult.error(() -> "Error parsing %s from string: %s".formatted(name, ex));
      }
   }

   static DataResult<FluidStack> parseString(Context cx, DynamicOps<Tag> registryOps, String s) {
      return switch (s) {
         case "", "-", "empty", "minecraft:empty" -> DataResult.success(FluidStack.EMPTY);
         default -> readWithContext(cx, registryOps, s, FluidWrapper::read, "FluidStack");
      };
   }

   static DataResult<FluidStack> read(DynamicOps<Tag> registryOps, StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() != '-') {
         DataResult<Integer> amount = readFluidAmount(reader);
         DataResult<Holder<Fluid>> fluid = ID.read(reader).flatMap(FluidWrapper::findFluid);
         DataResult<FluidStack> fluidStack = fluid.apply2(FluidStack::new, amount);
         char next = reader.canRead() ? reader.peek() : '\u0000';
         return next != '[' && next != '{' ? fluidStack : fluidStack.flatMap(stack -> {
            try {
               DataComponentPatch components = DataComponentWrapper.readPatch(registryOps, reader);
               stack.applyComponents(components);
               return DataResult.success(stack);
            } catch (CommandSyntaxException e) {
               return DataResult.error(e::getMessage);
            }
         });
      } else {
         return DataResult.success(FluidStack.EMPTY);
      }
   }

   static DataResult<FluidIngredient> ingredientOfString(Context cx, DynamicOps<Tag> registryOps, String s) {
      return switch (s) {
         case "", "-", "empty", "minecraft:empty" -> EMPTY_INGREDIENT_RESULT;
         default -> readWithContext(cx, registryOps, s, FluidWrapper::readIngredient, "FluidIngredient");
      };
   }

   @HideFromJS
   static DataResult<FluidIngredient> readIngredient(DynamicOps<Tag> registryOps, StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() != '-') {
         return switch (reader.peek()) {
            case '#' -> {
               reader.skip();
               DataResult var11 = ID.read(reader).map(FluidTags::create).map(FluidIngredient::tag);
               yield var11;
            }
            case '/' -> {
               DataResult var10 = RegExpKJS.tryRead(reader).map(RegExFluidIngredient::new);
               yield var10;
            }
            case '@' -> {
               reader.skip();
               String id = reader.readUnquotedString();
               DataResult var9 = DataResult.success(new NamespaceFluidIngredient(id));
               yield var9;
            }
            default -> {
               label42: {
                  DataResult var8;
                  label44: {
                     DataResult<Holder<Fluid>> fluid = ID.read(reader).flatMap(FluidWrapper::findFluid);
                     char next = reader.canRead() ? reader.peek() : '\u0000';
                     if (next == '[' || next == '{') {
                        try {
                           DataComponentPredicate components = DataComponentWrapper.readPredicate(registryOps, reader);
                           if (components != DataComponentPredicate.EMPTY) {
                              var8 = fluid.map(holder -> DataComponentFluidIngredient.of(false, components, new Holder[]{holder}));
                              break label44;
                           }
                        } catch (CommandSyntaxException e) {
                           var8 = DataResult.error(e::getMessage);
                           yield var8;
                           break label42;
                        }
                     }

                     var8 = fluid.map(FluidIngredient::single);
                     yield var8;
                     break label42;
                  }

                  yield var8;
               }
            }
         };
      } else {
         return EMPTY_INGREDIENT_RESULT;
      }
   }

   static DataResult<SizedFluidIngredient> sizedIngredientOfString(Context cx, DynamicOps<Tag> registryOps, String s) {
      return switch (s) {
         case "", "-", "empty", "minecraft:empty" -> EMPTY_SIZED_RESULT;
         default -> readWithContext(cx, registryOps, s, FluidWrapper::readSizedIngredient, "SizedFluidIngredient");
      };
   }

   static DataResult<SizedFluidIngredient> readSizedIngredient(DynamicOps<Tag> registryOps, StringReader reader) throws CommandSyntaxException {
      if (!reader.canRead()) {
         return EMPTY_SIZED_RESULT;
      }

      DataResult<Integer> amount = readFluidAmount(reader);
      DataResult<FluidIngredient> ingredient = readIngredient(registryOps, reader);
      return ingredient.apply2(SizedFluidIngredient::new, amount);
   }

   @HideFromJS
   static DataResult<Holder<Fluid>> findFluid(ResourceLocation id) {
      return BuiltInRegistries.FLUID
         .getHolder(id)
         .<DataResult>map(DataResult::success)
         .orElseGet(() -> DataResult.error(() -> "Fluid with ID " + id + " does not exist!"))
         .map(Function.identity());
   }

   @HideFromJS
   static DataResult<Integer> readFluidAmount(StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && StringReader.isAllowedNumber(reader.peek())) {
         double amountd = reader.readDouble();
         reader.skipWhitespace();
         if (reader.canRead() && (reader.peek() == 'b' || reader.peek() == 'B')) {
            reader.skip();
            reader.skipWhitespace();
            amountd *= 1000.0;
         }

         if (reader.canRead() && reader.peek() == '/') {
            reader.skip();
            reader.skipWhitespace();
            amountd /= reader.readDouble();
         }

         int amount = (int)amountd;
         reader.expect('x');
         reader.skipWhitespace();
         return amount < 1 ? DataResult.error(() -> "Fluid amount smaller than 1 is not allowed!") : DataResult.success(amount);
      } else {
         return DataResult.success(1000);
      }
   }

   interface ReadFn<T> {
      DataResult<T> read(DynamicOps<Tag> registryOps, StringReader reader) throws CommandSyntaxException;
   }
}
