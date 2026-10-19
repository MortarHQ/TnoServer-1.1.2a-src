package com.iafenvoy.uranus.util.function.function;

@FunctionalInterface
public interface Function3<T1, T2, T3, R> {
   R apply(T1 var1, T2 var2, T3 var3);
}
