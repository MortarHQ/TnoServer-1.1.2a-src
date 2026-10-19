package dev.latvian.mods.kubejs.holder;

import dev.latvian.mods.kubejs.core.RegistryObjectKJS;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.kubejs.util.Cast;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.kubejs.util.RegExpKJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.regexp.NativeRegExp;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Direct;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;
import org.jetbrains.annotations.Nullable;

public interface HolderWrapper {
   TypeInfo HOLDER = TypeInfo.of(Holder.class);
   TypeInfo HOLDER_SET = TypeInfo.of(HolderSet.class);

   static Holder<?> wrap(KubeJSContext cx, Object from, TypeInfo param) {
      if (from instanceof Holder<?> h) {
         return h;
      } else {
         if (from == null) {
            throw Context.reportRuntimeError("Can't interpret 'null' as a Holder", cx);
         }

         Registry<?> registry = cx.lookupRegistry(param, from);
         if (!ID.isKey(from)) {
            Holder<?> h = registry.wrapAsHolder(Cast.to(from));
            if (h instanceof Direct) {
               Class<?> baseClass = cx.lookupRegistryType(param, from).baseClass();
               if (!baseClass.isInstance(from)) {
                  throw Context.reportRuntimeError(
                     "Can't interpret '" + from + "' as Holder: can't cast object to '" + baseClass.getName() + "' of " + registry.key().location(), cx
                  );
               }
            }

            return h;
         } else {
            ResourceLocation id = ID.mc(from);
            Optional<? extends Reference<?>> holder = registry.getHolder(id);
            return (Holder<?>)(holder.isEmpty() ? DeferredHolder.create(registry.key(), id) : (Holder)holder.get());
         }
      }
   }

   static HolderSet<?> wrapSet(KubeJSContext cx, Object from, TypeInfo param) {
      Registry<?> registry = cx.lookupRegistry(param, from);
      HolderSet<?> simpleHolders = wrapSimpleSet(registry, from);
      if (simpleHolders != null) {
         return simpleHolders;
      }

      if (from instanceof Iterable<?> itr) {
         Builder<HolderSet<?>> allDirects = Stream.builder();
         ArrayList<HolderSet<?>> complex = new ArrayList<>();

         for (Object elem : itr) {
            HolderSet<?> wrapped = wrapSet(cx, elem, param);
            if (wrapped instanceof net.minecraft.core.HolderSet.Direct direct) {
               allDirects.accept(direct);
            } else {
               complex.add(wrapped);
            }
         }

         List<? extends Holder<?>> compressedDirects = allDirects.build().flatMap(HolderSet::stream).distinct().toList();
         if (compressedDirects.isEmpty()) {
            return (HolderSet<?>)(switch (complex.size()) {
               case 0 -> HolderSet.empty();
               case 1 -> (HolderSet)complex.getFirst();
               default -> new OrHolderSet(complex);
            });
         } else {
            if (complex.isEmpty()) {
               return HolderSet.direct(compressedDirects);
            }

            complex.add(HolderSet.direct(compressedDirects));
            return new OrHolderSet(complex);
         }
      } else {
         Holder holder = (Holder)cx.jsToJava(from, HOLDER.withParams(new TypeInfo[]{param}));
         return HolderSet.direct(new Holder[]{holder});
      }
   }

   @Nullable
   static <T> HolderSet<T> wrapSimpleSet(Registry<T> registry, Object from) {
      return (HolderSet<T>)(switch (from) {
         case HolderSet set -> set;
         case Holder holder when holder.canSerializeIn(registry.holderOwner()) -> HolderSet.direct(new Holder[]{holder});
         case NativeRegExp regex -> RegExHolderSet.of(registry.asLookup(), RegExpKJS.wrap(regex));
         case Pattern regex -> RegExHolderSet.of(registry.asLookup(), regex);
         case RegistryObjectKJS registered -> wrapSimpleSet(registry, registered.kjs$asHolder());
         case TagKey tag when tag.isFor(registry.key()) -> orEmpty(registry.getTag(tag));
         case ResourceKey<?> key when key.isFor(registry.key()) -> orEmpty(
            key.cast(registry.key()).<Reference>flatMap(registry::getHolder).map(xva$0 -> HolderSet.direct(new Holder[]{xva$0}))
         );
         case ResourceLocation id -> orEmpty(registry.getHolder(id).map(xva$0 -> HolderSet.direct(new Holder[]{xva$0})));
         case CharSequence cs when cs.isEmpty() -> HolderSet.empty();
         case CharSequence cs -> {
            String s = cs.toString();
            switch (s.charAt(0)) {
               case '#':
                  TagKey<T> tagKey = TagKey.create(registry.key(), ResourceLocation.parse(s.substring(1)));
                  yield registry.getOrCreateTag(tagKey);
               case '/':
                  yield wrapSimpleSet(registry, RegExpKJS.wrap(from));
               case '@':
                  yield NamespaceHolderSet.of(registry.asLookup(), s.substring(1));
               default:
                  yield ResourceLocation.read(s).result().map(idx -> wrapSimpleSet(registry, idx)).orElse(null);
            }
         }
         case null, default -> null;
      });
   }

   private static <T> HolderSet<T> orEmpty(Optional<? extends HolderSet<T>> holder) {
      return (HolderSet<T>)holder.orElse(HolderSet.empty());
   }
}
