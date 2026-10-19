package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiRun {
   public static final AnimationDefinition RUN;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 0.84
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 20
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: fconst_0
      // 001d: ldc -2.062
      // 001f: fconst_0
      // 0020: ldc -33.269
      // 0022: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0025: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0028: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002b: aastore
      // 002c: dup
      // 002d: bipush 1
      // 002e: new net/minecraft/client/animation/Keyframe
      // 0031: dup
      // 0032: ldc 0.07
      // 0034: ldc -2.425
      // 0036: fconst_0
      // 0037: ldc -22.403
      // 0039: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 003f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0042: aastore
      // 0043: dup
      // 0044: bipush 2
      // 0045: new net/minecraft/client/animation/Keyframe
      // 0048: dup
      // 0049: ldc 0.1
      // 004b: ldc -2.425
      // 004d: fconst_0
      // 004e: ldc -16.522
      // 0050: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0053: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0056: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0059: aastore
      // 005a: dup
      // 005b: bipush 3
      // 005c: new net/minecraft/client/animation/Keyframe
      // 005f: dup
      // 0060: ldc 0.14
      // 0062: ldc -2.425
      // 0064: fconst_0
      // 0065: ldc -10.417
      // 0067: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0070: aastore
      // 0071: dup
      // 0072: bipush 4
      // 0073: new net/minecraft/client/animation/Keyframe
      // 0076: dup
      // 0077: ldc 0.18
      // 0079: ldc -2.425
      // 007b: fconst_0
      // 007c: ldc -4.175
      // 007e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0081: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0084: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0087: aastore
      // 0088: dup
      // 0089: bipush 5
      // 008a: new net/minecraft/client/animation/Keyframe
      // 008d: dup
      // 008e: ldc 0.21
      // 0090: ldc -2.425
      // 0092: fconst_0
      // 0093: ldc 2.125
      // 0095: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0098: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009e: aastore
      // 009f: dup
      // 00a0: bipush 6
      // 00a2: new net/minecraft/client/animation/Keyframe
      // 00a5: dup
      // 00a6: ldc 0.24
      // 00a8: ldc -2.425
      // 00aa: fconst_0
      // 00ab: ldc 8.391
      // 00ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b6: aastore
      // 00b7: dup
      // 00b8: bipush 7
      // 00ba: new net/minecraft/client/animation/Keyframe
      // 00bd: dup
      // 00be: ldc 0.28
      // 00c0: ldc -2.425
      // 00c2: fconst_0
      // 00c3: ldc 14.501
      // 00c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00ce: aastore
      // 00cf: dup
      // 00d0: bipush 8
      // 00d2: new net/minecraft/client/animation/Keyframe
      // 00d5: dup
      // 00d6: ldc 0.32
      // 00d8: ldc -2.344
      // 00da: ldc -0.194
      // 00dc: ldc 18.962
      // 00de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e7: aastore
      // 00e8: dup
      // 00e9: bipush 9
      // 00eb: new net/minecraft/client/animation/Keyframe
      // 00ee: dup
      // 00ef: ldc 0.35
      // 00f1: ldc -2.126
      // 00f3: ldc -0.713
      // 00f5: ldc 21.301
      // 00f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0100: aastore
      // 0101: dup
      // 0102: bipush 10
      // 0104: new net/minecraft/client/animation/Keyframe
      // 0107: dup
      // 0108: ldc 0.42
      // 010a: ldc -1.414
      // 010c: ldc -2.357
      // 010e: ldc 20.649
      // 0110: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0113: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0116: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0119: aastore
      // 011a: dup
      // 011b: bipush 11
      // 011d: new net/minecraft/client/animation/Keyframe
      // 0120: dup
      // 0121: ldc 0.45
      // 0123: ldc -0.99
      // 0125: ldc -3.295
      // 0127: ldc 18.157
      // 0129: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 012c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 012f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0132: aastore
      // 0133: dup
      // 0134: bipush 12
      // 0136: new net/minecraft/client/animation/Keyframe
      // 0139: dup
      // 013a: ldc 0.49
      // 013c: ldc -0.565
      // 013e: ldc -4.187
      // 0140: ldc 14.544
      // 0142: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0145: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0148: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 014b: aastore
      // 014c: dup
      // 014d: bipush 13
      // 014f: new net/minecraft/client/animation/Keyframe
      // 0152: dup
      // 0153: ldc 0.52
      // 0155: ldc -0.175
      // 0157: ldc -4.939
      // 0159: ldc 10.046
      // 015b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 015e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0161: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0164: aastore
      // 0165: dup
      // 0166: bipush 14
      // 0168: new net/minecraft/client/animation/Keyframe
      // 016b: dup
      // 016c: ldc 0.56
      // 016e: ldc 0.147
      // 0170: ldc -5.458
      // 0172: ldc 4.895
      // 0174: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0177: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 017a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 017d: aastore
      // 017e: dup
      // 017f: bipush 15
      // 0181: new net/minecraft/client/animation/Keyframe
      // 0184: dup
      // 0185: ldc 0.6
      // 0187: ldc 0.365
      // 0189: ldc -5.652
      // 018b: ldc -0.678
      // 018d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0190: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0193: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0196: aastore
      // 0197: dup
      // 0198: bipush 16
      // 019a: new net/minecraft/client/animation/Keyframe
      // 019d: dup
      // 019e: ldc 0.63
      // 01a0: ldc 0.445
      // 01a2: ldc -5.585
      // 01a4: ldc -6.446
      // 01a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01af: aastore
      // 01b0: dup
      // 01b1: bipush 17
      // 01b3: new net/minecraft/client/animation/Keyframe
      // 01b6: dup
      // 01b7: ldc 0.7
      // 01b9: ldc -0.106
      // 01bb: ldc -5.027
      // 01bd: ldc -17.665
      // 01bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01c8: aastore
      // 01c9: dup
      // 01ca: bipush 18
      // 01cc: new net/minecraft/client/animation/Keyframe
      // 01cf: dup
      // 01d0: ldc 0.81
      // 01d2: ldc -1.523
      // 01d4: ldc -2.88
      // 01d6: ldc -30.305
      // 01d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01e1: aastore
      // 01e2: dup
      // 01e3: bipush 19
      // 01e5: new net/minecraft/client/animation/Keyframe
      // 01e8: dup
      // 01e9: ldc 0.84
      // 01eb: ldc -1.681
      // 01ed: ldc -1.654
      // 01ef: ldc -32.489
      // 01f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01fa: aastore
      // 01fb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 01fe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0201: ldc "CTR_hips"
      // 0203: new net/minecraft/client/animation/AnimationChannel
      // 0206: dup
      // 0207: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 020a: bipush 19
      // 020c: anewarray 28
      // 020f: dup
      // 0210: bipush 0
      // 0211: new net/minecraft/client/animation/Keyframe
      // 0214: dup
      // 0215: fconst_0
      // 0216: ldc 35.886
      // 0218: ldc -16.233
      // 021a: ldc -14.137
      // 021c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 021f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0222: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0225: aastore
      // 0226: dup
      // 0227: bipush 1
      // 0228: new net/minecraft/client/animation/Keyframe
      // 022b: dup
      // 022c: ldc 0.07
      // 022e: ldc 33.547
      // 0230: ldc -16.331
      // 0232: ldc -7.28
      // 0234: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0237: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 023a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 023d: aastore
      // 023e: dup
      // 023f: bipush 2
      // 0240: new net/minecraft/client/animation/Keyframe
      // 0243: dup
      // 0244: ldc 0.1
      // 0246: ldc 31.344
      // 0248: ldc -16.334
      // 024a: ldc -2.564
      // 024c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 024f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0252: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0255: aastore
      // 0256: dup
      // 0257: bipush 3
      // 0258: new net/minecraft/client/animation/Keyframe
      // 025b: dup
      // 025c: ldc 0.14
      // 025e: ldc 29.101
      // 0260: ldc -16.318
      // 0262: ldc -0.421
      // 0264: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0267: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 026a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 026d: aastore
      // 026e: dup
      // 026f: bipush 4
      // 0270: new net/minecraft/client/animation/Keyframe
      // 0273: dup
      // 0274: ldc 0.18
      // 0276: ldc 26.889
      // 0278: ldc -16.318
      // 027a: ldc -0.421
      // 027c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 027f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0282: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0285: aastore
      // 0286: dup
      // 0287: bipush 5
      // 0288: new net/minecraft/client/animation/Keyframe
      // 028b: dup
      // 028c: ldc 0.24
      // 028e: ldc 22.203
      // 0290: ldc -16.318
      // 0292: ldc -0.421
      // 0294: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0297: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 029a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029d: aastore
      // 029e: dup
      // 029f: bipush 6
      // 02a1: new net/minecraft/client/animation/Keyframe
      // 02a4: dup
      // 02a5: ldc 0.28
      // 02a7: ldc 19.991
      // 02a9: ldc -16.318
      // 02ab: ldc -0.421
      // 02ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b6: aastore
      // 02b7: dup
      // 02b8: bipush 7
      // 02ba: new net/minecraft/client/animation/Keyframe
      // 02bd: dup
      // 02be: ldc 0.32
      // 02c0: ldc 18.042
      // 02c2: ldc -16.318
      // 02c4: ldc -0.421
      // 02c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02cf: aastore
      // 02d0: dup
      // 02d1: bipush 8
      // 02d3: new net/minecraft/client/animation/Keyframe
      // 02d6: dup
      // 02d7: ldc 0.35
      // 02d9: ldc 16.487
      // 02db: ldc -16.318
      // 02dd: ldc -0.421
      // 02df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e8: aastore
      // 02e9: dup
      // 02ea: bipush 9
      // 02ec: new net/minecraft/client/animation/Keyframe
      // 02ef: dup
      // 02f0: ldc 0.42
      // 02f2: ldc 15.086
      // 02f4: ldc -16.318
      // 02f6: ldc -0.421
      // 02f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0301: aastore
      // 0302: dup
      // 0303: bipush 10
      // 0305: new net/minecraft/client/animation/Keyframe
      // 0308: dup
      // 0309: ldc 0.45
      // 030b: ldc 15.454
      // 030d: ldc -16.318
      // 030f: ldc -0.421
      // 0311: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0314: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0317: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 031a: aastore
      // 031b: dup
      // 031c: bipush 11
      // 031e: new net/minecraft/client/animation/Keyframe
      // 0321: dup
      // 0322: ldc 0.49
      // 0324: ldc 16.472
      // 0326: ldc -16.318
      // 0328: ldc -0.421
      // 032a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 032d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0333: aastore
      // 0334: dup
      // 0335: bipush 12
      // 0337: new net/minecraft/client/animation/Keyframe
      // 033a: dup
      // 033b: ldc 0.52
      // 033d: ldc 18.009
      // 033f: ldc -16.318
      // 0341: ldc -0.421
      // 0343: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0346: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0349: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 034c: aastore
      // 034d: dup
      // 034e: bipush 13
      // 0350: new net/minecraft/client/animation/Keyframe
      // 0353: dup
      // 0354: ldc 0.56
      // 0356: ldc 19.936
      // 0358: ldc -16.318
      // 035a: ldc -0.421
      // 035c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 035f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0362: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0365: aastore
      // 0366: dup
      // 0367: bipush 14
      // 0369: new net/minecraft/client/animation/Keyframe
      // 036c: dup
      // 036d: ldc 0.6
      // 036f: ldc 22.138
      // 0371: ldc -16.319
      // 0373: ldc -0.533
      // 0375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 037b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 037e: aastore
      // 037f: dup
      // 0380: bipush 15
      // 0382: new net/minecraft/client/animation/Keyframe
      // 0385: dup
      // 0386: ldc 0.63
      // 0388: ldc 24.507
      // 038a: ldc -16.322
      // 038c: ldc -0.91
      // 038e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0391: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0394: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0397: aastore
      // 0398: dup
      // 0399: bipush 16
      // 039b: new net/minecraft/client/animation/Keyframe
      // 039e: dup
      // 039f: ldc 0.7
      // 03a1: ldc 29.279
      // 03a3: ldc -16.335
      // 03a5: ldc -2.865
      // 03a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03b0: aastore
      // 03b1: dup
      // 03b2: bipush 17
      // 03b4: new net/minecraft/client/animation/Keyframe
      // 03b7: dup
      // 03b8: ldc 0.81
      // 03ba: ldc 34.905
      // 03bc: ldc -16.289
      // 03be: ldc -11.201
      // 03c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c9: aastore
      // 03ca: dup
      // 03cb: bipush 18
      // 03cd: new net/minecraft/client/animation/Keyframe
      // 03d0: dup
      // 03d1: ldc 0.84
      // 03d3: ldc 35.589
      // 03d5: ldc -16.247
      // 03d7: ldc -13.513
      // 03d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e2: aastore
      // 03e3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 03e6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 03e9: ldc "CTR_hips"
      // 03eb: new net/minecraft/client/animation/AnimationChannel
      // 03ee: dup
      // 03ef: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 03f2: bipush 19
      // 03f4: anewarray 28
      // 03f7: dup
      // 03f8: bipush 0
      // 03f9: new net/minecraft/client/animation/Keyframe
      // 03fc: dup
      // 03fd: fconst_0
      // 03fe: fconst_0
      // 03ff: ldc 2.999
      // 0401: fconst_0
      // 0402: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0405: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0408: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 040b: aastore
      // 040c: dup
      // 040d: bipush 1
      // 040e: new net/minecraft/client/animation/Keyframe
      // 0411: dup
      // 0412: ldc 0.07
      // 0414: ldc -0.069
      // 0416: ldc 3.559
      // 0418: ldc -2.486
      // 041a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 041d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0420: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0423: aastore
      // 0424: dup
      // 0425: bipush 2
      // 0426: new net/minecraft/client/animation/Keyframe
      // 0429: dup
      // 042a: ldc 0.1
      // 042c: ldc -0.224
      // 042e: ldc 4.468
      // 0430: ldc -3.721
      // 0432: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0435: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0438: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 043b: aastore
      // 043c: dup
      // 043d: bipush 3
      // 043e: new net/minecraft/client/animation/Keyframe
      // 0441: dup
      // 0442: ldc 0.14
      // 0444: ldc -0.384
      // 0446: ldc 5.761
      // 0448: ldc -4.683
      // 044a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 044d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0450: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0453: aastore
      // 0454: dup
      // 0455: bipush 4
      // 0456: new net/minecraft/client/animation/Keyframe
      // 0459: dup
      // 045a: ldc 0.18
      // 045c: ldc -0.481
      // 045e: ldc 7.013
      // 0460: ldc -5.25
      // 0462: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0465: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0468: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 046b: aastore
      // 046c: dup
      // 046d: bipush 5
      // 046e: new net/minecraft/client/animation/Keyframe
      // 0471: dup
      // 0472: ldc 0.21
      // 0474: ldc -0.545
      // 0476: ldc 7.998
      // 0478: ldc -5.549
      // 047a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 047d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0480: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0483: aastore
      // 0484: dup
      // 0485: bipush 6
      // 0487: new net/minecraft/client/animation/Keyframe
      // 048a: dup
      // 048b: ldc 0.24
      // 048d: ldc -0.59
      // 048f: ldc 8.736
      // 0491: ldc -5.708
      // 0493: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 049c: aastore
      // 049d: dup
      // 049e: bipush 7
      // 04a0: new net/minecraft/client/animation/Keyframe
      // 04a3: dup
      // 04a4: ldc 0.28
      // 04a6: ldc -0.624
      // 04a8: ldc 9.288
      // 04aa: ldc -5.797
      // 04ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04b5: aastore
      // 04b6: dup
      // 04b7: bipush 8
      // 04b9: new net/minecraft/client/animation/Keyframe
      // 04bc: dup
      // 04bd: ldc 0.35
      // 04bf: ldc -0.667
      // 04c1: ldc 10.008
      // 04c3: ldc -5.875
      // 04c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04ce: aastore
      // 04cf: dup
      // 04d0: bipush 9
      // 04d2: new net/minecraft/client/animation/Keyframe
      // 04d5: dup
      // 04d6: ldc 0.42
      // 04d8: ldc -0.69
      // 04da: ldc 10.391
      // 04dc: ldc -5.898
      // 04de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04e7: aastore
      // 04e8: dup
      // 04e9: bipush 10
      // 04eb: new net/minecraft/client/animation/Keyframe
      // 04ee: dup
      // 04ef: ldc 0.45
      // 04f1: ldc -0.697
      // 04f3: ldc 10.495
      // 04f5: ldc -5.901
      // 04f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0500: aastore
      // 0501: dup
      // 0502: bipush 11
      // 0504: new net/minecraft/client/animation/Keyframe
      // 0507: dup
      // 0508: ldc 0.49
      // 050a: ldc -0.7
      // 050c: ldc 10.552
      // 050e: ldc -5.902
      // 0510: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0513: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0516: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0519: aastore
      // 051a: dup
      // 051b: bipush 12
      // 051d: new net/minecraft/client/animation/Keyframe
      // 0520: dup
      // 0521: ldc 0.52
      // 0523: ldc -0.701
      // 0525: ldc 10.569
      // 0527: ldc -5.902
      // 0529: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 052c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 052f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0532: aastore
      // 0533: dup
      // 0534: bipush 13
      // 0536: new net/minecraft/client/animation/Keyframe
      // 0539: dup
      // 053a: ldc 0.56
      // 053c: ldc -0.699
      // 053e: ldc 10.522
      // 0540: ldc -5.866
      // 0542: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0545: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0548: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 054b: aastore
      // 054c: dup
      // 054d: bipush 14
      // 054f: new net/minecraft/client/animation/Keyframe
      // 0552: dup
      // 0553: ldc 0.6
      // 0555: ldc -0.69
      // 0557: ldc 10.364
      // 0559: ldc -5.743
      // 055b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 055e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0561: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0564: aastore
      // 0565: dup
      // 0566: bipush 15
      // 0568: new net/minecraft/client/animation/Keyframe
      // 056b: dup
      // 056c: ldc 0.63
      // 056e: ldc -0.674
      // 0570: ldc 10.059
      // 0572: ldc -5.508
      // 0574: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0577: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 057a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 057d: aastore
      // 057e: dup
      // 057f: bipush 16
      // 0581: new net/minecraft/client/animation/Keyframe
      // 0584: dup
      // 0585: ldc 0.7
      // 0587: ldc -0.601
      // 0589: ldc 8.742
      // 058b: ldc -4.51
      // 058d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0590: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0593: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0596: aastore
      // 0597: dup
      // 0598: bipush 17
      // 059a: new net/minecraft/client/animation/Keyframe
      // 059d: dup
      // 059e: ldc 0.81
      // 05a0: ldc -0.117
      // 05a2: ldc 4.033
      // 05a4: ldc -0.841
      // 05a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05af: aastore
      // 05b0: dup
      // 05b1: bipush 18
      // 05b3: new net/minecraft/client/animation/Keyframe
      // 05b6: dup
      // 05b7: ldc 0.84
      // 05b9: ldc -0.019
      // 05bb: ldc 3.2
      // 05bd: ldc -0.201
      // 05bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05c8: aastore
      // 05c9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 05cc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 05cf: ldc "CTR_chest"
      // 05d1: new net/minecraft/client/animation/AnimationChannel
      // 05d4: dup
      // 05d5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 05d8: bipush 20
      // 05da: anewarray 28
      // 05dd: dup
      // 05de: bipush 0
      // 05df: new net/minecraft/client/animation/Keyframe
      // 05e2: dup
      // 05e3: fconst_0
      // 05e4: ldc -17.489
      // 05e6: ldc -9.489
      // 05e8: ldc -18.683
      // 05ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f3: aastore
      // 05f4: dup
      // 05f5: bipush 1
      // 05f6: new net/minecraft/client/animation/Keyframe
      // 05f9: dup
      // 05fa: ldc 0.07
      // 05fc: ldc -8.237
      // 05fe: ldc -10.772
      // 0600: ldc -15.935
      // 0602: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0605: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0608: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 060b: aastore
      // 060c: dup
      // 060d: bipush 2
      // 060e: new net/minecraft/client/animation/Keyframe
      // 0611: dup
      // 0612: ldc 0.1
      // 0614: ldc 0.546
      // 0616: ldc -12.007
      // 0618: ldc -14.046
      // 061a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 061d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0620: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0623: aastore
      // 0624: dup
      // 0625: bipush 3
      // 0626: new net/minecraft/client/animation/Keyframe
      // 0629: dup
      // 062a: ldc 0.14
      // 062c: ldc 9.992
      // 062e: ldc -13.301
      // 0630: ldc -13.193
      // 0632: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0635: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0638: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 063b: aastore
      // 063c: dup
      // 063d: bipush 4
      // 063e: new net/minecraft/client/animation/Keyframe
      // 0641: dup
      // 0642: ldc 0.18
      // 0644: ldc 18.408
      // 0646: ldc -14.432
      // 0648: ldc -13.122
      // 064a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 064d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0650: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0653: aastore
      // 0654: dup
      // 0655: bipush 5
      // 0656: new net/minecraft/client/animation/Keyframe
      // 0659: dup
      // 065a: ldc 0.21
      // 065c: ldc 23.559
      // 065e: ldc -15.36
      // 0660: ldc -12.907
      // 0662: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0665: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0668: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 066b: aastore
      // 066c: dup
      // 066d: bipush 6
      // 066f: new net/minecraft/client/animation/Keyframe
      // 0672: dup
      // 0673: ldc 0.24
      // 0675: ldc 25.838
      // 0677: ldc -16.105
      // 0679: ldc -12.579
      // 067b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 067e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0681: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0684: aastore
      // 0685: dup
      // 0686: bipush 7
      // 0688: new net/minecraft/client/animation/Keyframe
      // 068b: dup
      // 068c: ldc 0.28
      // 068e: ldc 27.446
      // 0690: ldc -16.688
      // 0692: ldc -12.161
      // 0694: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0697: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 069a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 069d: aastore
      // 069e: dup
      // 069f: bipush 8
      // 06a1: new net/minecraft/client/animation/Keyframe
      // 06a4: dup
      // 06a5: ldc 0.32
      // 06a7: ldc 28.335
      // 06a9: ldc -17.129
      // 06ab: ldc -11.668
      // 06ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06b6: aastore
      // 06b7: dup
      // 06b8: bipush 9
      // 06ba: new net/minecraft/client/animation/Keyframe
      // 06bd: dup
      // 06be: ldc 0.35
      // 06c0: ldc 28.453
      // 06c2: ldc -17.446
      // 06c4: ldc -11.115
      // 06c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06cf: aastore
      // 06d0: dup
      // 06d1: bipush 10
      // 06d3: new net/minecraft/client/animation/Keyframe
      // 06d6: dup
      // 06d7: ldc 0.42
      // 06d9: ldc 26.191
      // 06db: ldc -17.792
      // 06dd: ldc -9.879
      // 06df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06e8: aastore
      // 06e9: dup
      // 06ea: bipush 11
      // 06ec: new net/minecraft/client/animation/Keyframe
      // 06ef: dup
      // 06f0: ldc 0.45
      // 06f2: ldc 23.744
      // 06f4: ldc -17.861
      // 06f6: ldc -9.222
      // 06f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0701: aastore
      // 0702: dup
      // 0703: bipush 12
      // 0705: new net/minecraft/client/animation/Keyframe
      // 0708: dup
      // 0709: ldc 0.49
      // 070b: ldc 20.415
      // 070d: ldc -17.887
      // 070f: ldc -8.565
      // 0711: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0714: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0717: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 071a: aastore
      // 071b: dup
      // 071c: bipush 13
      // 071e: new net/minecraft/client/animation/Keyframe
      // 0721: dup
      // 0722: ldc 0.52
      // 0724: ldc 16.26
      // 0726: ldc_w -17.891
      // 0729: ldc_w -7.948
      // 072c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 072f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0732: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0735: aastore
      // 0736: dup
      // 0737: bipush 14
      // 0739: new net/minecraft/client/animation/Keyframe
      // 073c: dup
      // 073d: ldc 0.56
      // 073f: ldc_w 11.429
      // 0742: ldc_w -17.652
      // 0745: ldc_w -7.549
      // 0748: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 074b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 074e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0751: aastore
      // 0752: dup
      // 0753: bipush 15
      // 0755: new net/minecraft/client/animation/Keyframe
      // 0758: dup
      // 0759: ldc 0.6
      // 075b: ldc_w 6.177
      // 075e: ldc_w -17.002
      // 0761: ldc_w -7.641
      // 0764: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0767: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 076a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 076d: aastore
      // 076e: dup
      // 076f: bipush 16
      // 0771: new net/minecraft/client/animation/Keyframe
      // 0774: dup
      // 0775: ldc 0.63
      // 0777: ldc_w 0.726
      // 077a: ldc_w -16.047
      // 077d: ldc_w -7.953
      // 0780: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0783: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0786: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0789: aastore
      // 078a: dup
      // 078b: bipush 17
      // 078d: new net/minecraft/client/animation/Keyframe
      // 0790: dup
      // 0791: ldc 0.7
      // 0793: ldc_w -9.363
      // 0796: ldc_w -13.633
      // 0799: ldc_w -9.548
      // 079c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 079f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a5: aastore
      // 07a6: dup
      // 07a7: bipush 18
      // 07a9: new net/minecraft/client/animation/Keyframe
      // 07ac: dup
      // 07ad: ldc 0.81
      // 07af: ldc_w -17.963
      // 07b2: ldc_w -10.287
      // 07b5: ldc_w -16.308
      // 07b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c1: aastore
      // 07c2: dup
      // 07c3: bipush 19
      // 07c5: new net/minecraft/client/animation/Keyframe
      // 07c8: dup
      // 07c9: ldc 0.84
      // 07cb: ldc_w -18.556
      // 07ce: ldc_w -9.672
      // 07d1: ldc_w -18.179
      // 07d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07dd: aastore
      // 07de: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07e1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07e4: ldc_w "CTR_head"
      // 07e7: new net/minecraft/client/animation/AnimationChannel
      // 07ea: dup
      // 07eb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 07ee: bipush 20
      // 07f0: anewarray 28
      // 07f3: dup
      // 07f4: bipush 0
      // 07f5: new net/minecraft/client/animation/Keyframe
      // 07f8: dup
      // 07f9: fconst_0
      // 07fa: ldc_w -18.157
      // 07fd: ldc_w -0.397
      // 0800: ldc_w -7.151
      // 0803: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0806: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0809: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 080c: aastore
      // 080d: dup
      // 080e: bipush 1
      // 080f: new net/minecraft/client/animation/Keyframe
      // 0812: dup
      // 0813: ldc 0.07
      // 0815: ldc_w -28.273
      // 0818: ldc_w -0.836
      // 081b: ldc_w -2.997
      // 081e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0821: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0824: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0827: aastore
      // 0828: dup
      // 0829: bipush 2
      // 082a: new net/minecraft/client/animation/Keyframe
      // 082d: dup
      // 082e: ldc 0.1
      // 0830: ldc_w -35.247
      // 0833: ldc_w 0.204
      // 0836: ldc_w -0.1
      // 0839: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 083c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 083f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0842: aastore
      // 0843: dup
      // 0844: bipush 3
      // 0845: new net/minecraft/client/animation/Keyframe
      // 0848: dup
      // 0849: ldc 0.14
      // 084b: ldc_w -42.099
      // 084e: ldc_w 2.65
      // 0851: ldc_w 0.909
      // 0854: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0857: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 085a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 085d: aastore
      // 085e: dup
      // 085f: bipush 4
      // 0860: new net/minecraft/client/animation/Keyframe
      // 0863: dup
      // 0864: ldc 0.18
      // 0866: ldc_w -46.926
      // 0869: ldc_w 5.254
      // 086c: ldc_w -0.029
      // 086f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0872: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0875: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0878: aastore
      // 0879: dup
      // 087a: bipush 5
      // 087b: new net/minecraft/client/animation/Keyframe
      // 087e: dup
      // 087f: ldc 0.21
      // 0881: ldc_w -47.175
      // 0884: ldc_w 6.854
      // 0887: ldc_w -1.258
      // 088a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 088d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0890: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0893: aastore
      // 0894: dup
      // 0895: bipush 6
      // 0897: new net/minecraft/client/animation/Keyframe
      // 089a: dup
      // 089b: ldc 0.24
      // 089d: ldc_w -43.075
      // 08a0: ldc_w 7.499
      // 08a3: ldc_w -2.379
      // 08a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08af: aastore
      // 08b0: dup
      // 08b1: bipush 7
      // 08b3: new net/minecraft/client/animation/Keyframe
      // 08b6: dup
      // 08b7: ldc 0.28
      // 08b9: ldc_w -38.121
      // 08bc: ldc_w 7.538
      // 08bf: ldc_w -3.425
      // 08c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08cb: aastore
      // 08cc: dup
      // 08cd: bipush 8
      // 08cf: new net/minecraft/client/animation/Keyframe
      // 08d2: dup
      // 08d3: ldc 0.32
      // 08d5: ldc_w -35.102
      // 08d8: ldc_w 7.265
      // 08db: ldc_w -4.363
      // 08de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08e7: aastore
      // 08e8: dup
      // 08e9: bipush 9
      // 08eb: new net/minecraft/client/animation/Keyframe
      // 08ee: dup
      // 08ef: ldc 0.35
      // 08f1: ldc_w -33.287
      // 08f4: ldc_w 6.768
      // 08f7: ldc_w -5.178
      // 08fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0900: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0903: aastore
      // 0904: dup
      // 0905: bipush 10
      // 0907: new net/minecraft/client/animation/Keyframe
      // 090a: dup
      // 090b: ldc 0.42
      // 090d: ldc_w -29.273
      // 0910: ldc_w 5.431
      // 0913: ldc_w -6.448
      // 0916: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0919: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 091c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 091f: aastore
      // 0920: dup
      // 0921: bipush 11
      // 0923: new net/minecraft/client/animation/Keyframe
      // 0926: dup
      // 0927: ldc 0.45
      // 0929: ldc_w -27.225
      // 092c: ldc_w 4.733
      // 092f: ldc_w -6.915
      // 0932: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 093b: aastore
      // 093c: dup
      // 093d: bipush 12
      // 093f: new net/minecraft/client/animation/Keyframe
      // 0942: dup
      // 0943: ldc 0.49
      // 0945: ldc_w -25.091
      // 0948: ldc_w 4.092
      // 094b: ldc_w -7.298
      // 094e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0957: aastore
      // 0958: dup
      // 0959: bipush 13
      // 095b: new net/minecraft/client/animation/Keyframe
      // 095e: dup
      // 095f: ldc 0.52
      // 0961: ldc_w -22.798
      // 0964: ldc_w 3.561
      // 0967: ldc_w -7.598
      // 096a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 096d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0973: aastore
      // 0974: dup
      // 0975: bipush 14
      // 0977: new net/minecraft/client/animation/Keyframe
      // 097a: dup
      // 097b: ldc 0.56
      // 097d: ldc_w -20.336
      // 0980: ldc_w 3.004
      // 0983: ldc_w -7.594
      // 0986: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0989: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 098c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 098f: aastore
      // 0990: dup
      // 0991: bipush 15
      // 0993: new net/minecraft/client/animation/Keyframe
      // 0996: dup
      // 0997: ldc 0.6
      // 0999: ldc_w -17.802
      // 099c: ldc_w 2.303
      // 099f: ldc_w -7.139
      // 09a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ab: aastore
      // 09ac: dup
      // 09ad: bipush 16
      // 09af: new net/minecraft/client/animation/Keyframe
      // 09b2: dup
      // 09b3: ldc 0.63
      // 09b5: ldc_w -15.384
      // 09b8: ldc_w 1.419
      // 09bb: ldc_w -6.743
      // 09be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09c7: aastore
      // 09c8: dup
      // 09c9: bipush 17
      // 09cb: new net/minecraft/client/animation/Keyframe
      // 09ce: dup
      // 09cf: ldc 0.7
      // 09d1: ldc_w -11.867
      // 09d4: ldc_w -0.352
      // 09d7: ldc_w -6.434
      // 09da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09e3: aastore
      // 09e4: dup
      // 09e5: bipush 18
      // 09e7: new net/minecraft/client/animation/Keyframe
      // 09ea: dup
      // 09eb: ldc 0.81
      // 09ed: ldc_w -12.847
      // 09f0: ldc -0.601
      // 09f2: ldc_w -6.993
      // 09f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09fe: aastore
      // 09ff: dup
      // 0a00: bipush 19
      // 0a02: new net/minecraft/client/animation/Keyframe
      // 0a05: dup
      // 0a06: ldc 0.84
      // 0a08: ldc_w -14.762
      // 0a0b: ldc_w -0.361
      // 0a0e: ldc_w -7.17
      // 0a11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a1a: aastore
      // 0a1b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a1e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a21: ldc_w "CTR_shoulde1r_left"
      // 0a24: new net/minecraft/client/animation/AnimationChannel
      // 0a27: dup
      // 0a28: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a2b: bipush 20
      // 0a2d: anewarray 28
      // 0a30: dup
      // 0a31: bipush 0
      // 0a32: new net/minecraft/client/animation/Keyframe
      // 0a35: dup
      // 0a36: fconst_0
      // 0a37: ldc_w 0.667
      // 0a3a: ldc_w -4.824
      // 0a3d: ldc_w -8.38
      // 0a40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a49: aastore
      // 0a4a: dup
      // 0a4b: bipush 1
      // 0a4c: new net/minecraft/client/animation/Keyframe
      // 0a4f: dup
      // 0a50: ldc 0.07
      // 0a52: ldc_w 0.278
      // 0a55: ldc_w -2.029
      // 0a58: ldc_w -7.5
      // 0a5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a64: aastore
      // 0a65: dup
      // 0a66: bipush 2
      // 0a67: new net/minecraft/client/animation/Keyframe
      // 0a6a: dup
      // 0a6b: ldc 0.1
      // 0a6d: ldc_w 0.027
      // 0a70: ldc_w -0.785
      // 0a73: ldc_w -7.06
      // 0a76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a7f: aastore
      // 0a80: dup
      // 0a81: bipush 3
      // 0a82: new net/minecraft/client/animation/Keyframe
      // 0a85: dup
      // 0a86: ldc 0.14
      // 0a88: ldc_w -0.205
      // 0a8b: ldc_w 0.237
      // 0a8e: ldc_w -6.686
      // 0a91: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a9a: aastore
      // 0a9b: dup
      // 0a9c: bipush 4
      // 0a9d: new net/minecraft/client/animation/Keyframe
      // 0aa0: dup
      // 0aa1: ldc 0.18
      // 0aa3: ldc_w -0.376
      // 0aa6: ldc_w 0.942
      // 0aa9: ldc_w -6.424
      // 0aac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0aaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ab2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ab5: aastore
      // 0ab6: dup
      // 0ab7: bipush 5
      // 0ab8: new net/minecraft/client/animation/Keyframe
      // 0abb: dup
      // 0abc: ldc 0.21
      // 0abe: ldc_w -0.442
      // 0ac1: ldc_w 1.212
      // 0ac4: ldc_w -6.326
      // 0ac7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0aca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0acd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ad0: aastore
      // 0ad1: dup
      // 0ad2: bipush 6
      // 0ad4: new net/minecraft/client/animation/Keyframe
      // 0ad7: dup
      // 0ad8: ldc 0.24
      // 0ada: ldc_w -0.354
      // 0add: ldc_w 0.732
      // 0ae0: ldc_w -6.489
      // 0ae3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ae6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ae9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aec: aastore
      // 0aed: dup
      // 0aee: bipush 7
      // 0af0: new net/minecraft/client/animation/Keyframe
      // 0af3: dup
      // 0af4: ldc 0.28
      // 0af6: ldc_w -0.114
      // 0af9: ldc_w -0.571
      // 0afc: ldc_w -6.933
      // 0aff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b08: aastore
      // 0b09: dup
      // 0b0a: bipush 8
      // 0b0c: new net/minecraft/client/animation/Keyframe
      // 0b0f: dup
      // 0b10: ldc 0.32
      // 0b12: ldc_w 0.239
      // 0b15: ldc_w -2.492
      // 0b18: ldc_w -7.587
      // 0b1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b24: aastore
      // 0b25: dup
      // 0b26: bipush 9
      // 0b28: new net/minecraft/client/animation/Keyframe
      // 0b2b: dup
      // 0b2c: ldc 0.35
      // 0b2e: ldc_w 0.667
      // 0b31: ldc_w -4.824
      // 0b34: ldc_w -8.38
      // 0b37: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b40: aastore
      // 0b41: dup
      // 0b42: bipush 10
      // 0b44: new net/minecraft/client/animation/Keyframe
      // 0b47: dup
      // 0b48: ldc 0.42
      // 0b4a: ldc_w 1.6
      // 0b4d: ldc_w -9.899
      // 0b50: ldc_w -10.108
      // 0b53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b5c: aastore
      // 0b5d: dup
      // 0b5e: bipush 11
      // 0b60: new net/minecraft/client/animation/Keyframe
      // 0b63: dup
      // 0b64: ldc 0.45
      // 0b66: ldc_w 2.029
      // 0b69: ldc_w -12.231
      // 0b6c: ldc_w -10.902
      // 0b6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b78: aastore
      // 0b79: dup
      // 0b7a: bipush 12
      // 0b7c: new net/minecraft/client/animation/Keyframe
      // 0b7f: dup
      // 0b80: ldc 0.49
      // 0b82: ldc_w 2.382
      // 0b85: ldc_w -14.152
      // 0b88: ldc_w -11.555
      // 0b8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b94: aastore
      // 0b95: dup
      // 0b96: bipush 13
      // 0b98: new net/minecraft/client/animation/Keyframe
      // 0b9b: dup
      // 0b9c: ldc 0.52
      // 0b9e: ldc_w 2.621
      // 0ba1: ldc_w -15.455
      // 0ba4: ldc_w -11.999
      // 0ba7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0baa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bb0: aastore
      // 0bb1: dup
      // 0bb2: bipush 14
      // 0bb4: new net/minecraft/client/animation/Keyframe
      // 0bb7: dup
      // 0bb8: ldc 0.56
      // 0bba: ldc_w 2.709
      // 0bbd: ldc -15.935
      // 0bbf: ldc_w -12.162
      // 0bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bcb: aastore
      // 0bcc: dup
      // 0bcd: bipush 15
      // 0bcf: new net/minecraft/client/animation/Keyframe
      // 0bd2: dup
      // 0bd3: ldc 0.6
      // 0bd5: ldc_w 2.635
      // 0bd8: ldc_w -15.53
      // 0bdb: ldc_w -12.024
      // 0bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0be7: aastore
      // 0be8: dup
      // 0be9: bipush 16
      // 0beb: new net/minecraft/client/animation/Keyframe
      // 0bee: dup
      // 0bef: ldc 0.63
      // 0bf1: ldc_w 2.439
      // 0bf4: ldc_w -14.461
      // 0bf7: ldc_w -11.661
      // 0bfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c03: aastore
      // 0c04: dup
      // 0c05: bipush 17
      // 0c07: new net/minecraft/client/animation/Keyframe
      // 0c0a: dup
      // 0c0b: ldc 0.7
      // 0c0d: ldc_w 1.843
      // 0c10: ldc_w -11.22
      // 0c13: ldc_w -10.558
      // 0c16: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c1f: aastore
      // 0c20: dup
      // 0c21: bipush 18
      // 0c23: new net/minecraft/client/animation/Keyframe
      // 0c26: dup
      // 0c27: ldc 0.81
      // 0c29: ldc_w 1.051
      // 0c2c: ldc_w -6.911
      // 0c2f: ldc_w -9.091
      // 0c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c3b: aastore
      // 0c3c: dup
      // 0c3d: bipush 19
      // 0c3f: new net/minecraft/client/animation/Keyframe
      // 0c42: dup
      // 0c43: ldc 0.84
      // 0c45: ldc_w 0.976
      // 0c48: ldc_w -6.505
      // 0c4b: ldc_w -8.953
      // 0c4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c57: aastore
      // 0c58: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c5b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c5e: ldc_w "CTR_upperarm_left"
      // 0c61: new net/minecraft/client/animation/AnimationChannel
      // 0c64: dup
      // 0c65: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c68: bipush 20
      // 0c6a: anewarray 28
      // 0c6d: dup
      // 0c6e: bipush 0
      // 0c6f: new net/minecraft/client/animation/Keyframe
      // 0c72: dup
      // 0c73: fconst_0
      // 0c74: ldc_w -8.928
      // 0c77: ldc_w 9.746
      // 0c7a: ldc_w -10.3
      // 0c7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c86: aastore
      // 0c87: dup
      // 0c88: bipush 1
      // 0c89: new net/minecraft/client/animation/Keyframe
      // 0c8c: dup
      // 0c8d: ldc 0.07
      // 0c8f: ldc_w -14.102
      // 0c92: ldc_w 15.104
      // 0c95: ldc_w -19.608
      // 0c98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ca1: aastore
      // 0ca2: dup
      // 0ca3: bipush 2
      // 0ca4: new net/minecraft/client/animation/Keyframe
      // 0ca7: dup
      // 0ca8: ldc 0.1
      // 0caa: ldc_w -19.771
      // 0cad: ldc_w 18.595
      // 0cb0: ldc_w -29.414
      // 0cb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cbc: aastore
      // 0cbd: dup
      // 0cbe: bipush 3
      // 0cbf: new net/minecraft/client/animation/Keyframe
      // 0cc2: dup
      // 0cc3: ldc 0.14
      // 0cc5: ldc_w -25.8
      // 0cc8: ldc_w 18.97
      // 0ccb: ldc_w -42.263
      // 0cce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cd7: aastore
      // 0cd8: dup
      // 0cd9: bipush 4
      // 0cda: new net/minecraft/client/animation/Keyframe
      // 0cdd: dup
      // 0cde: ldc 0.18
      // 0ce0: ldc_w -30.079
      // 0ce3: ldc_w 15.486
      // 0ce6: ldc_w -55.032
      // 0ce9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cf2: aastore
      // 0cf3: dup
      // 0cf4: bipush 5
      // 0cf5: new net/minecraft/client/animation/Keyframe
      // 0cf8: dup
      // 0cf9: ldc 0.21
      // 0cfb: ldc_w -31.002
      // 0cfe: ldc_w 9.942
      // 0d01: ldc_w -63.769
      // 0d04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d0d: aastore
      // 0d0e: dup
      // 0d0f: bipush 6
      // 0d11: new net/minecraft/client/animation/Keyframe
      // 0d14: dup
      // 0d15: ldc 0.24
      // 0d17: ldc_w -24.36
      // 0d1a: ldc_w 14.478
      // 0d1d: ldc_w -55.97
      // 0d20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d29: aastore
      // 0d2a: dup
      // 0d2b: bipush 7
      // 0d2d: new net/minecraft/client/animation/Keyframe
      // 0d30: dup
      // 0d31: ldc 0.28
      // 0d33: ldc_w -17.006
      // 0d36: ldc_w 15.988
      // 0d39: ldc_w -47.182
      // 0d3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d45: aastore
      // 0d46: dup
      // 0d47: bipush 8
      // 0d49: new net/minecraft/client/animation/Keyframe
      // 0d4c: dup
      // 0d4d: ldc 0.32
      // 0d4f: ldc_w -10.438
      // 0d52: ldc_w 14.679
      // 0d55: ldc_w -39.309
      // 0d58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d61: aastore
      // 0d62: dup
      // 0d63: bipush 9
      // 0d65: new net/minecraft/client/animation/Keyframe
      // 0d68: dup
      // 0d69: ldc 0.35
      // 0d6b: ldc_w -5.298
      // 0d6e: ldc_w 11.495
      // 0d71: ldc_w -33.976
      // 0d74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d7d: aastore
      // 0d7e: dup
      // 0d7f: bipush 10
      // 0d81: new net/minecraft/client/animation/Keyframe
      // 0d84: dup
      // 0d85: ldc 0.42
      // 0d87: ldc_w 2.218
      // 0d8a: ldc_w 3.642
      // 0d8d: ldc_w -29.579
      // 0d90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d99: aastore
      // 0d9a: dup
      // 0d9b: bipush 11
      // 0d9d: new net/minecraft/client/animation/Keyframe
      // 0da0: dup
      // 0da1: ldc 0.45
      // 0da3: ldc_w 5.432
      // 0da6: ldc_w -0.044
      // 0da9: ldc_w -28.98
      // 0dac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0daf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0db2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0db5: aastore
      // 0db6: dup
      // 0db7: bipush 12
      // 0db9: new net/minecraft/client/animation/Keyframe
      // 0dbc: dup
      // 0dbd: ldc 0.49
      // 0dbf: ldc_w 8.725
      // 0dc2: ldc_w -3.49
      // 0dc5: ldc_w -28.834
      // 0dc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dd1: aastore
      // 0dd2: dup
      // 0dd3: bipush 13
      // 0dd5: new net/minecraft/client/animation/Keyframe
      // 0dd8: dup
      // 0dd9: ldc 0.52
      // 0ddb: ldc_w 9.746
      // 0dde: ldc_w -4.742
      // 0de1: ldc_w -26.169
      // 0de4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0de7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ded: aastore
      // 0dee: dup
      // 0def: bipush 14
      // 0df1: new net/minecraft/client/animation/Keyframe
      // 0df4: dup
      // 0df5: ldc 0.56
      // 0df7: ldc_w 9.3
      // 0dfa: ldc_w -4.708
      // 0dfd: ldc_w -22.55
      // 0e00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e09: aastore
      // 0e0a: dup
      // 0e0b: bipush 15
      // 0e0d: new net/minecraft/client/animation/Keyframe
      // 0e10: dup
      // 0e11: ldc 0.6
      // 0e13: ldc_w 7.488
      // 0e16: ldc_w -3.349
      // 0e19: ldc_w -17.334
      // 0e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e25: aastore
      // 0e26: dup
      // 0e27: bipush 16
      // 0e29: new net/minecraft/client/animation/Keyframe
      // 0e2c: dup
      // 0e2d: ldc 0.63
      // 0e2f: ldc_w 5.145
      // 0e32: ldc_w -1.454
      // 0e35: ldc_w -11.778
      // 0e38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e41: aastore
      // 0e42: dup
      // 0e43: bipush 17
      // 0e45: new net/minecraft/client/animation/Keyframe
      // 0e48: dup
      // 0e49: ldc 0.7
      // 0e4b: ldc_w 0.214
      // 0e4e: ldc_w 3.082
      // 0e51: ldc_w -2.501
      // 0e54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e5d: aastore
      // 0e5e: dup
      // 0e5f: bipush 18
      // 0e61: new net/minecraft/client/animation/Keyframe
      // 0e64: dup
      // 0e65: ldc 0.81
      // 0e67: ldc_w -6.227
      // 0e6a: ldc_w 9.278
      // 0e6d: ldc_w -1.542
      // 0e70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e79: aastore
      // 0e7a: dup
      // 0e7b: bipush 19
      // 0e7d: new net/minecraft/client/animation/Keyframe
      // 0e80: dup
      // 0e81: ldc 0.84
      // 0e83: ldc_w -7.419
      // 0e86: ldc_w 9.076
      // 0e89: ldc_w -6.376
      // 0e8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e95: aastore
      // 0e96: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0e99: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0e9c: ldc_w "CTR_lowerarm_left"
      // 0e9f: new net/minecraft/client/animation/AnimationChannel
      // 0ea2: dup
      // 0ea3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ea6: bipush 20
      // 0ea8: anewarray 28
      // 0eab: dup
      // 0eac: bipush 0
      // 0ead: new net/minecraft/client/animation/Keyframe
      // 0eb0: dup
      // 0eb1: fconst_0
      // 0eb2: ldc_w -49.355
      // 0eb5: ldc_w -80.539
      // 0eb8: ldc_w 60.121
      // 0ebb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ebe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ec1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ec4: aastore
      // 0ec5: dup
      // 0ec6: bipush 1
      // 0ec7: new net/minecraft/client/animation/Keyframe
      // 0eca: dup
      // 0ecb: ldc 0.07
      // 0ecd: ldc_w -38.115
      // 0ed0: ldc_w -87.742
      // 0ed3: ldc_w 50.155
      // 0ed6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ed9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0edc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0edf: aastore
      // 0ee0: dup
      // 0ee1: bipush 2
      // 0ee2: new net/minecraft/client/animation/Keyframe
      // 0ee5: dup
      // 0ee6: ldc 0.1
      // 0ee8: ldc_w -33.964
      // 0eeb: ldc_w -91.795
      // 0eee: ldc_w 46.69
      // 0ef1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ef4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0efa: aastore
      // 0efb: dup
      // 0efc: bipush 3
      // 0efd: new net/minecraft/client/animation/Keyframe
      // 0f00: dup
      // 0f01: ldc 0.14
      // 0f03: ldc_w -33.607
      // 0f06: ldc_w -92.193
      // 0f09: ldc_w 46.4
      // 0f0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f15: aastore
      // 0f16: dup
      // 0f17: bipush 4
      // 0f18: new net/minecraft/client/animation/Keyframe
      // 0f1b: dup
      // 0f1c: ldc 0.18
      // 0f1e: ldc_w -36.452
      // 0f21: ldc_w -89.246
      // 0f24: ldc_w 48.747
      // 0f27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f30: aastore
      // 0f31: dup
      // 0f32: bipush 5
      // 0f33: new net/minecraft/client/animation/Keyframe
      // 0f36: dup
      // 0f37: ldc 0.21
      // 0f39: ldc_w -43.05
      // 0f3c: ldc_w -84.037
      // 0f3f: ldc_w 54.449
      // 0f42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f4b: aastore
      // 0f4c: dup
      // 0f4d: bipush 6
      // 0f4f: new net/minecraft/client/animation/Keyframe
      // 0f52: dup
      // 0f53: ldc 0.24
      // 0f55: ldc_w -40.699
      // 0f58: ldc_w -85.674
      // 0f5b: ldc_w 52.384
      // 0f5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f67: aastore
      // 0f68: dup
      // 0f69: bipush 7
      // 0f6b: new net/minecraft/client/animation/Keyframe
      // 0f6e: dup
      // 0f6f: ldc 0.28
      // 0f71: ldc_w -42.033
      // 0f74: ldc_w -84.719
      // 0f77: ldc_w 53.552
      // 0f7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f83: aastore
      // 0f84: dup
      // 0f85: bipush 8
      // 0f87: new net/minecraft/client/animation/Keyframe
      // 0f8a: dup
      // 0f8b: ldc 0.32
      // 0f8d: ldc_w -47.84
      // 0f90: ldc_w -81.278
      // 0f93: ldc_w 58.743
      // 0f96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f9f: aastore
      // 0fa0: dup
      // 0fa1: bipush 9
      // 0fa3: new net/minecraft/client/animation/Keyframe
      // 0fa6: dup
      // 0fa7: ldc 0.35
      // 0fa9: ldc_w -61.147
      // 0fac: ldc_w -76.328
      // 0faf: ldc_w 71.057
      // 0fb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fbb: aastore
      // 0fbc: dup
      // 0fbd: bipush 10
      // 0fbf: new net/minecraft/client/animation/Keyframe
      // 0fc2: dup
      // 0fc3: ldc 0.42
      // 0fc5: ldc_w -124.336
      // 0fc8: ldc_w -73.326
      // 0fcb: ldc_w 131.577
      // 0fce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd7: aastore
      // 0fd8: dup
      // 0fd9: bipush 11
      // 0fdb: new net/minecraft/client/animation/Keyframe
      // 0fde: dup
      // 0fdf: ldc 0.45
      // 0fe1: ldc_w -150.878
      // 0fe4: ldc_w -79.931
      // 0fe7: ldc_w 156.559
      // 0fea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ff0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ff3: aastore
      // 0ff4: dup
      // 0ff5: bipush 12
      // 0ff7: new net/minecraft/client/animation/Keyframe
      // 0ffa: dup
      // 0ffb: ldc 0.49
      // 0ffd: ldc_w -165.917
      // 1000: ldc_w -90.021
      // 1003: ldc_w 169.823
      // 1006: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1009: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 100c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100f: aastore
      // 1010: dup
      // 1011: bipush 13
      // 1013: new net/minecraft/client/animation/Keyframe
      // 1016: dup
      // 1017: ldc 0.52
      // 1019: ldc_w -168.922
      // 101c: ldc_w -93.306
      // 101f: ldc_w 172.274
      // 1022: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1025: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1028: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 102b: aastore
      // 102c: dup
      // 102d: bipush 14
      // 102f: new net/minecraft/client/animation/Keyframe
      // 1032: dup
      // 1033: ldc 0.56
      // 1035: ldc_w -168.16
      // 1038: ldc_w -92.415
      // 103b: ldc_w 171.662
      // 103e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1041: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1044: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1047: aastore
      // 1048: dup
      // 1049: bipush 15
      // 104b: new net/minecraft/client/animation/Keyframe
      // 104e: dup
      // 104f: ldc 0.6
      // 1051: ldc_w -163.659
      // 1054: ldc_w -87.917
      // 1057: ldc_w 167.922
      // 105a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 105d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1060: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1063: aastore
      // 1064: dup
      // 1065: bipush 16
      // 1067: new net/minecraft/client/animation/Keyframe
      // 106a: dup
      // 106b: ldc 0.63
      // 106d: ldc_w -155.402
      // 1070: ldc_w -82.162
      // 1073: ldc_w 160.669
      // 1076: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1079: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 107c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107f: aastore
      // 1080: dup
      // 1081: bipush 17
      // 1083: new net/minecraft/client/animation/Keyframe
      // 1086: dup
      // 1087: ldc 0.7
      // 1089: ldc_w -119.868
      // 108c: ldc_w -72.844
      // 108f: ldc_w 127.299
      // 1092: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1095: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1098: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 109b: aastore
      // 109c: dup
      // 109d: bipush 18
      // 109f: new net/minecraft/client/animation/Keyframe
      // 10a2: dup
      // 10a3: ldc 0.81
      // 10a5: ldc_w -55.844
      // 10a8: ldc_w -77.932
      // 10ab: ldc_w 66.099
      // 10ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b7: aastore
      // 10b8: dup
      // 10b9: bipush 19
      // 10bb: new net/minecraft/client/animation/Keyframe
      // 10be: dup
      // 10bf: ldc 0.84
      // 10c1: ldc_w -53.492
      // 10c4: ldc_w -78.784
      // 10c7: ldc_w 63.919
      // 10ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10d3: aastore
      // 10d4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 10d7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 10da: ldc_w "CTR_upperarm_left.001"
      // 10dd: new net/minecraft/client/animation/AnimationChannel
      // 10e0: dup
      // 10e1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 10e4: bipush 20
      // 10e6: anewarray 28
      // 10e9: dup
      // 10ea: bipush 0
      // 10eb: new net/minecraft/client/animation/Keyframe
      // 10ee: dup
      // 10ef: fconst_0
      // 10f0: ldc_w 4.403
      // 10f3: ldc_w -30.45
      // 10f6: ldc_w -3.398
      // 10f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1102: aastore
      // 1103: dup
      // 1104: bipush 1
      // 1105: new net/minecraft/client/animation/Keyframe
      // 1108: dup
      // 1109: ldc 0.07
      // 110b: ldc_w 3.194
      // 110e: ldc_w -26.84
      // 1111: ldc_w -2.481
      // 1114: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1117: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 111a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 111d: aastore
      // 111e: dup
      // 111f: bipush 2
      // 1120: new net/minecraft/client/animation/Keyframe
      // 1123: dup
      // 1124: ldc 0.1
      // 1126: ldc_w 2.225
      // 1129: ldc_w -23.758
      // 112c: ldc_w -1.811
      // 112f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1132: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1135: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1138: aastore
      // 1139: dup
      // 113a: bipush 3
      // 113b: new net/minecraft/client/animation/Keyframe
      // 113e: dup
      // 113f: ldc 0.14
      // 1141: ldc_w 0.812
      // 1144: ldc_w -18.165
      // 1147: ldc_w -1.086
      // 114a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 114d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1150: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1153: aastore
      // 1154: dup
      // 1155: bipush 4
      // 1156: new net/minecraft/client/animation/Keyframe
      // 1159: dup
      // 115a: ldc 0.18
      // 115c: ldc_w -1.28
      // 115f: ldc_w -10.315
      // 1162: ldc_w -0.893
      // 1165: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1168: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 116b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 116e: aastore
      // 116f: dup
      // 1170: bipush 5
      // 1171: new net/minecraft/client/animation/Keyframe
      // 1174: dup
      // 1175: ldc 0.21
      // 1177: ldc_w -6.853
      // 117a: ldc_w -2.237
      // 117d: ldc_w -4.682
      // 1180: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1183: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1186: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1189: aastore
      // 118a: dup
      // 118b: bipush 6
      // 118d: new net/minecraft/client/animation/Keyframe
      // 1190: dup
      // 1191: ldc 0.24
      // 1193: ldc_w -0.708
      // 1196: ldc_w -11.274
      // 1199: ldc_w -1.037
      // 119c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 119f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11a5: aastore
      // 11a6: dup
      // 11a7: bipush 7
      // 11a9: new net/minecraft/client/animation/Keyframe
      // 11ac: dup
      // 11ad: ldc 0.28
      // 11af: ldc_w 1.608
      // 11b2: ldc_w -19.828
      // 11b5: ldc_w -1.574
      // 11b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11c1: aastore
      // 11c2: dup
      // 11c3: bipush 8
      // 11c5: new net/minecraft/client/animation/Keyframe
      // 11c8: dup
      // 11c9: ldc 0.32
      // 11cb: ldc_w 3.296
      // 11ce: ldc_w -26.934
      // 11d1: ldc -2.564
      // 11d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11dc: aastore
      // 11dd: dup
      // 11de: bipush 9
      // 11e0: new net/minecraft/client/animation/Keyframe
      // 11e3: dup
      // 11e4: ldc 0.35
      // 11e6: ldc_w 4.342
      // 11e9: ldc_w -31.466
      // 11ec: ldc_w -3.316
      // 11ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11f8: aastore
      // 11f9: dup
      // 11fa: bipush 10
      // 11fc: new net/minecraft/client/animation/Keyframe
      // 11ff: dup
      // 1200: ldc 0.42
      // 1202: ldc_w 4.79
      // 1205: ldc_w -34.285
      // 1208: ldc_w -3.639
      // 120b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 120e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1211: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1214: aastore
      // 1215: dup
      // 1216: bipush 11
      // 1218: new net/minecraft/client/animation/Keyframe
      // 121b: dup
      // 121c: ldc 0.45
      // 121e: ldc_w 4.479
      // 1221: ldc_w -33.918
      // 1224: ldc_w -3.372
      // 1227: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 122a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 122d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1230: aastore
      // 1231: dup
      // 1232: bipush 12
      // 1234: new net/minecraft/client/animation/Keyframe
      // 1237: dup
      // 1238: ldc 0.49
      // 123a: ldc_w 3.898
      // 123d: ldc_w -32.684
      // 1240: ldc_w -2.891
      // 1243: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1246: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1249: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 124c: aastore
      // 124d: dup
      // 124e: bipush 13
      // 1250: new net/minecraft/client/animation/Keyframe
      // 1253: dup
      // 1254: ldc 0.52
      // 1256: ldc_w 3.935
      // 1259: ldc_w -34.274
      // 125c: ldc_w -2.882
      // 125f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1262: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1265: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1268: aastore
      // 1269: dup
      // 126a: bipush 14
      // 126c: new net/minecraft/client/animation/Keyframe
      // 126f: dup
      // 1270: ldc 0.56
      // 1272: ldc_w 4.209
      // 1275: ldc_w -36.144
      // 1278: ldc_w -3.081
      // 127b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 127e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1281: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1284: aastore
      // 1285: dup
      // 1286: bipush 15
      // 1288: new net/minecraft/client/animation/Keyframe
      // 128b: dup
      // 128c: ldc 0.6
      // 128e: ldc_w 4.824
      // 1291: ldc_w -38.996
      // 1294: ldc_w -3.573
      // 1297: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 129a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 129d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a0: aastore
      // 12a1: dup
      // 12a2: bipush 16
      // 12a4: new net/minecraft/client/animation/Keyframe
      // 12a7: dup
      // 12a8: ldc 0.63
      // 12aa: ldc_w 5.569
      // 12ad: ldc_w -41.829
      // 12b0: ldc_w -4.199
      // 12b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12bc: aastore
      // 12bd: dup
      // 12be: bipush 17
      // 12c0: new net/minecraft/client/animation/Keyframe
      // 12c3: dup
      // 12c4: ldc 0.7
      // 12c6: ldc_w 6.914
      // 12c9: ldc_w -45.51
      // 12cc: ldc_w -5.379
      // 12cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12d8: aastore
      // 12d9: dup
      // 12da: bipush 18
      // 12dc: new net/minecraft/client/animation/Keyframe
      // 12df: dup
      // 12e0: ldc 0.81
      // 12e2: ldc_w 6.488
      // 12e5: ldc_w -40.054
      // 12e8: ldc_w -5.055
      // 12eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12f4: aastore
      // 12f5: dup
      // 12f6: bipush 19
      // 12f8: new net/minecraft/client/animation/Keyframe
      // 12fb: dup
      // 12fc: ldc 0.84
      // 12fe: ldc_w 5.216
      // 1301: ldc_w -33.902
      // 1304: ldc_w -4.025
      // 1307: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 130a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 130d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1310: aastore
      // 1311: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1314: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1317: ldc_w "CTR_upperarm_left.001"
      // 131a: new net/minecraft/client/animation/AnimationChannel
      // 131d: dup
      // 131e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1321: bipush 16
      // 1323: anewarray 28
      // 1326: dup
      // 1327: bipush 0
      // 1328: new net/minecraft/client/animation/Keyframe
      // 132b: dup
      // 132c: fconst_0
      // 132d: dconst_1
      // 132e: dconst_1
      // 132f: ldc2_w 1.7960000038146973
      // 1332: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1335: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1338: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 133b: aastore
      // 133c: dup
      // 133d: bipush 1
      // 133e: new net/minecraft/client/animation/Keyframe
      // 1341: dup
      // 1342: ldc 0.07
      // 1344: dconst_1
      // 1345: dconst_1
      // 1346: ldc2_w 1.9759999513626099
      // 1349: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 134c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 134f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1352: aastore
      // 1353: dup
      // 1354: bipush 2
      // 1355: new net/minecraft/client/animation/Keyframe
      // 1358: dup
      // 1359: ldc 0.1
      // 135b: dconst_1
      // 135c: dconst_1
      // 135d: ldc2_w 2.0869998931884766
      // 1360: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1363: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1366: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1369: aastore
      // 136a: dup
      // 136b: bipush 3
      // 136c: new net/minecraft/client/animation/Keyframe
      // 136f: dup
      // 1370: ldc 0.18
      // 1372: dconst_1
      // 1373: dconst_1
      // 1374: ldc2_w 2.3329999446868896
      // 1377: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 137a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1380: aastore
      // 1381: dup
      // 1382: bipush 4
      // 1383: new net/minecraft/client/animation/Keyframe
      // 1386: dup
      // 1387: ldc 0.21
      // 1389: dconst_1
      // 138a: dconst_1
      // 138b: ldc2_w 2.440999984741211
      // 138e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1391: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1394: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1397: aastore
      // 1398: dup
      // 1399: bipush 5
      // 139a: new net/minecraft/client/animation/Keyframe
      // 139d: dup
      // 139e: ldc 0.24
      // 13a0: dconst_1
      // 13a1: dconst_1
      // 13a2: ldc2_w 2.308000087738037
      // 13a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 13a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ae: aastore
      // 13af: dup
      // 13b0: bipush 6
      // 13b2: new net/minecraft/client/animation/Keyframe
      // 13b5: dup
      // 13b6: ldc 0.28
      // 13b8: dconst_1
      // 13b9: dconst_1
      // 13ba: ldc2_w 2.177000045776367
      // 13bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 13c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13c6: aastore
      // 13c7: dup
      // 13c8: bipush 7
      // 13ca: new net/minecraft/client/animation/Keyframe
      // 13cd: dup
      // 13ce: ldc 0.35
      // 13d0: dconst_1
      // 13d1: dconst_1
      // 13d2: ldc2_w 1.9789999723434448
      // 13d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 13d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13de: aastore
      // 13df: dup
      // 13e0: bipush 8
      // 13e2: new net/minecraft/client/animation/Keyframe
      // 13e5: dup
      // 13e6: ldc 0.42
      // 13e8: dconst_1
      // 13e9: dconst_1
      // 13ea: ldc2_w 1.8739999532699585
      // 13ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 13f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13f6: aastore
      // 13f7: dup
      // 13f8: bipush 9
      // 13fa: new net/minecraft/client/animation/Keyframe
      // 13fd: dup
      // 13fe: ldc 0.45
      // 1400: dconst_1
      // 1401: dconst_1
      // 1402: ldc2_w 1.843999981880188
      // 1405: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1408: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 140b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 140e: aastore
      // 140f: dup
      // 1410: bipush 10
      // 1412: new net/minecraft/client/animation/Keyframe
      // 1415: dup
      // 1416: ldc 0.49
      // 1418: dconst_1
      // 1419: dconst_1
      // 141a: ldc2_w 1.8259999752044678
      // 141d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1420: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1423: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1426: aastore
      // 1427: dup
      // 1428: bipush 11
      // 142a: new net/minecraft/client/animation/Keyframe
      // 142d: dup
      // 142e: ldc 0.52
      // 1430: dconst_1
      // 1431: dconst_1
      // 1432: ldc2_w 1.7910000085830688
      // 1435: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1438: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 143b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 143e: aastore
      // 143f: dup
      // 1440: bipush 12
      // 1442: new net/minecraft/client/animation/Keyframe
      // 1445: dup
      // 1446: ldc 0.63
      // 1448: dconst_1
      // 1449: dconst_1
      // 144a: ldc2_w 1.6950000524520874
      // 144d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1450: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1453: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1456: aastore
      // 1457: dup
      // 1458: bipush 13
      // 145a: new net/minecraft/client/animation/Keyframe
      // 145d: dup
      // 145e: ldc 0.7
      // 1460: dconst_1
      // 1461: dconst_1
      // 1462: ldc2_w 1.659999966621399
      // 1465: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1468: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 146b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 146e: aastore
      // 146f: dup
      // 1470: bipush 14
      // 1472: new net/minecraft/client/animation/Keyframe
      // 1475: dup
      // 1476: ldc 0.81
      // 1478: dconst_1
      // 1479: dconst_1
      // 147a: ldc2_w 1.6779999732971191
      // 147d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1480: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1483: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1486: aastore
      // 1487: dup
      // 1488: bipush 15
      // 148a: new net/minecraft/client/animation/Keyframe
      // 148d: dup
      // 148e: ldc 0.84
      // 1490: dconst_1
      // 1491: dconst_1
      // 1492: ldc2_w 1.725000023841858
      // 1495: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1498: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 149b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 149e: aastore
      // 149f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 14a2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 14a5: ldc_w "CTR_shoulder_right"
      // 14a8: new net/minecraft/client/animation/AnimationChannel
      // 14ab: dup
      // 14ac: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 14af: bipush 20
      // 14b1: anewarray 28
      // 14b4: dup
      // 14b5: bipush 0
      // 14b6: new net/minecraft/client/animation/Keyframe
      // 14b9: dup
      // 14ba: fconst_0
      // 14bb: ldc_w 0.177
      // 14be: ldc_w -4.253
      // 14c1: ldc_w 0.006
      // 14c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14cd: aastore
      // 14ce: dup
      // 14cf: bipush 1
      // 14d0: new net/minecraft/client/animation/Keyframe
      // 14d3: dup
      // 14d4: ldc 0.07
      // 14d6: ldc_w 0.097
      // 14d9: ldc_w -2.197
      // 14dc: ldc_w -0.083
      // 14df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14e8: aastore
      // 14e9: dup
      // 14ea: bipush 2
      // 14eb: new net/minecraft/client/animation/Keyframe
      // 14ee: dup
      // 14ef: ldc 0.1
      // 14f1: ldc_w 0.042
      // 14f4: ldc_w -1.423
      // 14f7: ldc_w -0.145
      // 14fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1503: aastore
      // 1504: dup
      // 1505: bipush 3
      // 1506: new net/minecraft/client/animation/Keyframe
      // 1509: dup
      // 150a: ldc 0.14
      // 150c: ldc_w 0.016
      // 150f: ldc_w -1.1
      // 1512: ldc_w -0.172
      // 1515: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1518: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 151b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 151e: aastore
      // 151f: dup
      // 1520: bipush 4
      // 1521: new net/minecraft/client/animation/Keyframe
      // 1524: dup
      // 1525: ldc 0.18
      // 1527: ldc_w 0.036
      // 152a: ldc_w -1.481
      // 152d: ldc_w -0.151
      // 1530: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1539: aastore
      // 153a: dup
      // 153b: bipush 5
      // 153c: new net/minecraft/client/animation/Keyframe
      // 153f: dup
      // 1540: ldc 0.21
      // 1542: ldc_w 0.089
      // 1545: ldc_w -2.514
      // 1548: ldc_w -0.092
      // 154b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 154e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1551: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1554: aastore
      // 1555: dup
      // 1556: bipush 6
      // 1558: new net/minecraft/client/animation/Keyframe
      // 155b: dup
      // 155c: ldc 0.24
      // 155e: ldc_w 0.166
      // 1561: ldc_w -4.036
      // 1564: ldc_w -0.006
      // 1567: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 156a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 156d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1570: aastore
      // 1571: dup
      // 1572: bipush 7
      // 1574: new net/minecraft/client/animation/Keyframe
      // 1577: dup
      // 1578: ldc 0.28
      // 157a: ldc_w 0.261
      // 157d: ldc_w -5.885
      // 1580: ldc_w 0.098
      // 1583: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1586: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1589: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 158c: aastore
      // 158d: dup
      // 158e: bipush 8
      // 1590: new net/minecraft/client/animation/Keyframe
      // 1593: dup
      // 1594: ldc 0.32
      // 1596: ldc_w 0.364
      // 1599: ldc_w -7.897
      // 159c: ldc_w 0.212
      // 159f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 15a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15a8: aastore
      // 15a9: dup
      // 15aa: bipush 9
      // 15ac: new net/minecraft/client/animation/Keyframe
      // 15af: dup
      // 15b0: ldc 0.35
      // 15b2: ldc_w 0.467
      // 15b5: ldc_w -9.908
      // 15b8: ldc_w 0.325
      // 15bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 15be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15c4: aastore
      // 15c5: dup
      // 15c6: bipush 10
      // 15c8: new net/minecraft/client/animation/Keyframe
      // 15cb: dup
      // 15cc: ldc 0.42
      // 15ce: ldc_w 0.639
      // 15d1: ldc_w -13.279
      // 15d4: ldc_w 0.516
      // 15d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 15da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e0: aastore
      // 15e1: dup
      // 15e2: bipush 11
      // 15e4: new net/minecraft/client/animation/Keyframe
      // 15e7: dup
      // 15e8: ldc 0.45
      // 15ea: ldc_w 0.692
      // 15ed: ldc_w -14.312
      // 15f0: ldc_w 0.574
      // 15f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 15f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15fc: aastore
      // 15fd: dup
      // 15fe: bipush 12
      // 1600: new net/minecraft/client/animation/Keyframe
      // 1603: dup
      // 1604: ldc 0.49
      // 1606: ldc_w 0.711
      // 1609: ldc_w -14.693
      // 160c: ldc_w 0.596
      // 160f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1612: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1615: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1618: aastore
      // 1619: dup
      // 161a: bipush 13
      // 161c: new net/minecraft/client/animation/Keyframe
      // 161f: dup
      // 1620: ldc 0.52
      // 1622: ldc_w 0.698
      // 1625: ldc_w -14.433
      // 1628: ldc_w 0.581
      // 162b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 162e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1631: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1634: aastore
      // 1635: dup
      // 1636: bipush 14
      // 1638: new net/minecraft/client/animation/Keyframe
      // 163b: dup
      // 163c: ldc 0.56
      // 163e: ldc_w 0.662
      // 1641: ldc_w -13.727
      // 1644: ldc_w 0.541
      // 1647: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 164a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 164d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1650: aastore
      // 1651: dup
      // 1652: bipush 15
      // 1654: new net/minecraft/client/animation/Keyframe
      // 1657: dup
      // 1658: ldc 0.6
      // 165a: ldc_w 0.608
      // 165d: ldc_w -12.686
      // 1660: ldc_w 0.482
      // 1663: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1666: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1669: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 166c: aastore
      // 166d: dup
      // 166e: bipush 16
      // 1670: new net/minecraft/client/animation/Keyframe
      // 1673: dup
      // 1674: ldc 0.63
      // 1676: ldc_w 0.544
      // 1679: ldc_w -11.422
      // 167c: ldc_w 0.411
      // 167f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1682: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1685: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1688: aastore
      // 1689: dup
      // 168a: bipush 17
      // 168c: new net/minecraft/client/animation/Keyframe
      // 168f: dup
      // 1690: ldc 0.7
      // 1692: ldc_w 0.403
      // 1695: ldc_w -8.672
      // 1698: ldc_w 0.255
      // 169b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 169e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16a4: aastore
      // 16a5: dup
      // 16a6: bipush 18
      // 16a8: new net/minecraft/client/animation/Keyframe
      // 16ab: dup
      // 16ac: ldc 0.81
      // 16ae: ldc_w 0.249
      // 16b1: ldc_w -5.661
      // 16b4: ldc_w 0.085
      // 16b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 16ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16c0: aastore
      // 16c1: dup
      // 16c2: bipush 19
      // 16c4: new net/minecraft/client/animation/Keyframe
      // 16c7: dup
      // 16c8: ldc 0.84
      // 16ca: ldc_w 0.236
      // 16cd: ldc_w -5.401
      // 16d0: ldc_w 0.071
      // 16d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 16d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16dc: aastore
      // 16dd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 16e0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 16e3: ldc_w "CTR_upperarm_right"
      // 16e6: new net/minecraft/client/animation/AnimationChannel
      // 16e9: dup
      // 16ea: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 16ed: bipush 20
      // 16ef: anewarray 28
      // 16f2: dup
      // 16f3: bipush 0
      // 16f4: new net/minecraft/client/animation/Keyframe
      // 16f7: dup
      // 16f8: fconst_0
      // 16f9: ldc_w -18.252
      // 16fc: ldc_w -3.029
      // 16ff: ldc_w 18.982
      // 1702: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1705: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1708: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 170b: aastore
      // 170c: dup
      // 170d: bipush 1
      // 170e: new net/minecraft/client/animation/Keyframe
      // 1711: dup
      // 1712: ldc 0.07
      // 1714: ldc_w -16.277
      // 1717: ldc_w 0.26
      // 171a: ldc_w 32.717
      // 171d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1720: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1723: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1726: aastore
      // 1727: dup
      // 1728: bipush 2
      // 1729: new net/minecraft/client/animation/Keyframe
      // 172c: dup
      // 172d: ldc 0.1
      // 172f: ldc_w -14.664
      // 1732: ldc_w 2.184
      // 1735: ldc_w 43.084
      // 1738: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 173b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 173e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1741: aastore
      // 1742: dup
      // 1743: bipush 3
      // 1744: new net/minecraft/client/animation/Keyframe
      // 1747: dup
      // 1748: ldc 0.14
      // 174a: ldc_w -14.191
      // 174d: ldc_w 3.965
      // 1750: ldc_w 55.191
      // 1753: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1756: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1759: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 175c: aastore
      // 175d: dup
      // 175e: bipush 4
      // 175f: new net/minecraft/client/animation/Keyframe
      // 1762: dup
      // 1763: ldc 0.18
      // 1765: ldc_w -15.568
      // 1768: ldc_w 2.237
      // 176b: ldc_w 52.712
      // 176e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1777: aastore
      // 1778: dup
      // 1779: bipush 5
      // 177a: new net/minecraft/client/animation/Keyframe
      // 177d: dup
      // 177e: ldc 0.21
      // 1780: ldc_w -16.152
      // 1783: ldc_w 0.785
      // 1786: ldc_w 47.418
      // 1789: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 178c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 178f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1792: aastore
      // 1793: dup
      // 1794: bipush 6
      // 1796: new net/minecraft/client/animation/Keyframe
      // 1799: dup
      // 179a: ldc 0.24
      // 179c: ldc_w -16.114
      // 179f: ldc_w -0.239
      // 17a2: ldc_w 40.065
      // 17a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17ae: aastore
      // 17af: dup
      // 17b0: bipush 7
      // 17b2: new net/minecraft/client/animation/Keyframe
      // 17b5: dup
      // 17b6: ldc 0.28
      // 17b8: ldc_w -15.915
      // 17bb: ldc_w -0.887
      // 17be: ldc_w 33.85
      // 17c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17ca: aastore
      // 17cb: dup
      // 17cc: bipush 8
      // 17ce: new net/minecraft/client/animation/Keyframe
      // 17d1: dup
      // 17d2: ldc 0.32
      // 17d4: ldc_w -15.416
      // 17d7: ldc_w -1.074
      // 17da: ldc_w 29.12
      // 17dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e6: aastore
      // 17e7: dup
      // 17e8: bipush 9
      // 17ea: new net/minecraft/client/animation/Keyframe
      // 17ed: dup
      // 17ee: ldc 0.35
      // 17f0: ldc_w -14.581
      // 17f3: ldc_w -0.783
      // 17f6: ldc_w 25.825
      // 17f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1802: aastore
      // 1803: dup
      // 1804: bipush 10
      // 1806: new net/minecraft/client/animation/Keyframe
      // 1809: dup
      // 180a: ldc 0.42
      // 180c: ldc_w -11.815
      // 180f: ldc_w 1.159
      // 1812: ldc_w 22.752
      // 1815: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1818: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 181b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 181e: aastore
      // 181f: dup
      // 1820: bipush 11
      // 1822: new net/minecraft/client/animation/Keyframe
      // 1825: dup
      // 1826: ldc 0.45
      // 1828: ldc_w -11.33
      // 182b: ldc_w 1.16
      // 182e: ldc_w 19.981
      // 1831: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183a: aastore
      // 183b: dup
      // 183c: bipush 12
      // 183e: new net/minecraft/client/animation/Keyframe
      // 1841: dup
      // 1842: ldc 0.49
      // 1844: ldc_w -11.397
      // 1847: ldc_w 0.693
      // 184a: ldc_w 17.034
      // 184d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1850: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1853: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1856: aastore
      // 1857: dup
      // 1858: bipush 13
      // 185a: new net/minecraft/client/animation/Keyframe
      // 185d: dup
      // 185e: ldc 0.52
      // 1860: ldc_w -11.853
      // 1863: ldc_w -0.098
      // 1866: ldc_w 13.465
      // 1869: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 186c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 186f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1872: aastore
      // 1873: dup
      // 1874: bipush 14
      // 1876: new net/minecraft/client/animation/Keyframe
      // 1879: dup
      // 187a: ldc 0.56
      // 187c: ldc_w -12.297
      // 187f: ldc_w -0.806
      // 1882: ldc_w 9.987
      // 1885: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 188b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 188e: aastore
      // 188f: dup
      // 1890: bipush 15
      // 1892: new net/minecraft/client/animation/Keyframe
      // 1895: dup
      // 1896: ldc 0.6
      // 1898: ldc_w -12.538
      // 189b: ldc_w -1.162
      // 189e: ldc_w 7.077
      // 18a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18aa: aastore
      // 18ab: dup
      // 18ac: bipush 16
      // 18ae: new net/minecraft/client/animation/Keyframe
      // 18b1: dup
      // 18b2: ldc 0.63
      // 18b4: ldc_w -12.732
      // 18b7: ldc_w -1.676
      // 18ba: ldc_w 4.513
      // 18bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c6: aastore
      // 18c7: dup
      // 18c8: bipush 17
      // 18ca: new net/minecraft/client/animation/Keyframe
      // 18cd: dup
      // 18ce: ldc 0.7
      // 18d0: ldc_w -13.349
      // 18d3: ldc_w -3.208
      // 18d6: ldc_w 1.253
      // 18d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18e2: aastore
      // 18e3: dup
      // 18e4: bipush 18
      // 18e6: new net/minecraft/client/animation/Keyframe
      // 18e9: dup
      // 18ea: ldc 0.81
      // 18ec: ldc_w -16.871
      // 18ef: ldc_w -4.877
      // 18f2: ldc_w 7.122
      // 18f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18fe: aastore
      // 18ff: dup
      // 1900: bipush 19
      // 1902: new net/minecraft/client/animation/Keyframe
      // 1905: dup
      // 1906: ldc 0.84
      // 1908: ldc_w -17.58
      // 190b: ldc_w -3.78
      // 190e: ldc_w 13.224
      // 1911: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1914: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1917: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 191a: aastore
      // 191b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 191e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1921: ldc_w "CTR_lowerarm_right"
      // 1924: new net/minecraft/client/animation/AnimationChannel
      // 1927: dup
      // 1928: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 192b: bipush 19
      // 192d: anewarray 28
      // 1930: dup
      // 1931: bipush 0
      // 1932: new net/minecraft/client/animation/Keyframe
      // 1935: dup
      // 1936: fconst_0
      // 1937: ldc_w -12.618
      // 193a: ldc_w 53.225
      // 193d: ldc_w -12.842
      // 1940: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1943: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1946: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1949: aastore
      // 194a: dup
      // 194b: bipush 1
      // 194c: new net/minecraft/client/animation/Keyframe
      // 194f: dup
      // 1950: ldc 0.07
      // 1952: ldc_w 6.585
      // 1955: ldc_w 47.386
      // 1958: ldc_w 6.383
      // 195b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 195e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1961: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1964: aastore
      // 1965: dup
      // 1966: bipush 2
      // 1967: new net/minecraft/client/animation/Keyframe
      // 196a: dup
      // 196b: ldc 0.1
      // 196d: ldc_w 4.591
      // 1970: ldc_w 45.902
      // 1973: ldc_w 4.395
      // 1976: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1979: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 197c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 197f: aastore
      // 1980: dup
      // 1981: bipush 3
      // 1982: new net/minecraft/client/animation/Keyframe
      // 1985: dup
      // 1986: ldc 0.14
      // 1988: ldc_w 4.672
      // 198b: ldc_w 45.986
      // 198e: ldc_w 4.476
      // 1991: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1994: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1997: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 199a: aastore
      // 199b: dup
      // 199c: bipush 4
      // 199d: new net/minecraft/client/animation/Keyframe
      // 19a0: dup
      // 19a1: ldc 0.18
      // 19a3: ldc_w -5.278
      // 19a6: ldc_w 56.346
      // 19a9: ldc_w -5.515
      // 19ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19b5: aastore
      // 19b6: dup
      // 19b7: bipush 5
      // 19b8: new net/minecraft/client/animation/Keyframe
      // 19bb: dup
      // 19bc: ldc 0.21
      // 19be: ldc_w -3.001
      // 19c1: ldc_w 60.796
      // 19c4: ldc_w -3.256
      // 19c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19d0: aastore
      // 19d1: dup
      // 19d2: bipush 6
      // 19d4: new net/minecraft/client/animation/Keyframe
      // 19d7: dup
      // 19d8: ldc 0.24
      // 19da: ldc_w -3.188
      // 19dd: ldc_w 60.166
      // 19e0: ldc_w -3.44
      // 19e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19ec: aastore
      // 19ed: dup
      // 19ee: bipush 7
      // 19f0: new net/minecraft/client/animation/Keyframe
      // 19f3: dup
      // 19f4: ldc 0.28
      // 19f6: ldc_w -4.902
      // 19f9: ldc_w 56.778
      // 19fc: ldc_w -5.14
      // 19ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a08: aastore
      // 1a09: dup
      // 1a0a: bipush 8
      // 1a0c: new net/minecraft/client/animation/Keyframe
      // 1a0f: dup
      // 1a10: ldc 0.32
      // 1a12: ldc_w 67.586
      // 1a15: ldc_w 50.618
      // 1a18: ldc_w 67.371
      // 1a1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a24: aastore
      // 1a25: dup
      // 1a26: bipush 9
      // 1a28: new net/minecraft/client/animation/Keyframe
      // 1a2b: dup
      // 1a2c: ldc 0.35
      // 1a2e: ldc_w 2.806
      // 1a31: ldc_w 43.012
      // 1a34: ldc_w 2.622
      // 1a37: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a40: aastore
      // 1a41: dup
      // 1a42: bipush 10
      // 1a44: new net/minecraft/client/animation/Keyframe
      // 1a47: dup
      // 1a48: ldc 0.42
      // 1a4a: ldc_w 0.434
      // 1a4d: ldc_w 21.633
      // 1a50: ldc_w 0.336
      // 1a53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a5c: aastore
      // 1a5d: dup
      // 1a5e: bipush 11
      // 1a60: new net/minecraft/client/animation/Keyframe
      // 1a63: dup
      // 1a64: ldc 0.45
      // 1a66: ldc_w 0.321
      // 1a69: ldc_w 17.747
      // 1a6c: ldc 0.24
      // 1a6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a77: aastore
      // 1a78: dup
      // 1a79: bipush 12
      // 1a7b: new net/minecraft/client/animation/Keyframe
      // 1a7e: dup
      // 1a7f: ldc 0.49
      // 1a81: ldc_w 0.324
      // 1a84: ldc_w 17.848
      // 1a87: ldc_w 0.242
      // 1a8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a93: aastore
      // 1a94: dup
      // 1a95: bipush 13
      // 1a97: new net/minecraft/client/animation/Keyframe
      // 1a9a: dup
      // 1a9b: ldc 0.56
      // 1a9d: ldc_w 0.547
      // 1aa0: ldc_w 24.776
      // 1aa3: ldc_w 0.436
      // 1aa6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aa9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aaf: aastore
      // 1ab0: dup
      // 1ab1: bipush 14
      // 1ab3: new net/minecraft/client/animation/Keyframe
      // 1ab6: dup
      // 1ab7: ldc 0.6
      // 1ab9: ldc_w 0.726
      // 1abc: ldc_w 28.63
      // 1abf: ldc_w 0.599
      // 1ac2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ac5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ac8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1acb: aastore
      // 1acc: dup
      // 1acd: bipush 15
      // 1acf: new net/minecraft/client/animation/Keyframe
      // 1ad2: dup
      // 1ad3: ldc 0.63
      // 1ad5: ldc_w 0.989
      // 1ad8: ldc_w 32.683
      // 1adb: ldc_w 0.846
      // 1ade: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ae1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ae4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ae7: aastore
      // 1ae8: dup
      // 1ae9: bipush 16
      // 1aeb: new net/minecraft/client/animation/Keyframe
      // 1aee: dup
      // 1aef: ldc 0.7
      // 1af1: ldc_w 2.294
      // 1af4: ldc_w 41.48
      // 1af7: ldc_w 2.116
      // 1afa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1afd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b03: aastore
      // 1b04: dup
      // 1b05: bipush 17
      // 1b07: new net/minecraft/client/animation/Keyframe
      // 1b0a: dup
      // 1b0b: ldc 0.81
      // 1b0d: ldc_w -6.947
      // 1b10: ldc_w 55.026
      // 1b13: ldc_w -7.178
      // 1b16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b1f: aastore
      // 1b20: dup
      // 1b21: bipush 18
      // 1b23: new net/minecraft/client/animation/Keyframe
      // 1b26: dup
      // 1b27: ldc 0.84
      // 1b29: ldc_w -10.197
      // 1b2c: ldc_w 53.74
      // 1b2f: ldc_w -10.423
      // 1b32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b3b: aastore
      // 1b3c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1b3f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1b42: ldc_w "CTR_upperarm_right.001"
      // 1b45: new net/minecraft/client/animation/AnimationChannel
      // 1b48: dup
      // 1b49: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1b4c: bipush 20
      // 1b4e: anewarray 28
      // 1b51: dup
      // 1b52: bipush 0
      // 1b53: new net/minecraft/client/animation/Keyframe
      // 1b56: dup
      // 1b57: fconst_0
      // 1b58: ldc_w -0.232
      // 1b5b: ldc_w 9.78
      // 1b5e: ldc_w 1.505
      // 1b61: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b6a: aastore
      // 1b6b: dup
      // 1b6c: bipush 1
      // 1b6d: new net/minecraft/client/animation/Keyframe
      // 1b70: dup
      // 1b71: ldc 0.07
      // 1b73: ldc_w -0.531
      // 1b76: ldc_w 2.215
      // 1b79: ldc_w 0.671
      // 1b7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b85: aastore
      // 1b86: dup
      // 1b87: bipush 2
      // 1b88: new net/minecraft/client/animation/Keyframe
      // 1b8b: dup
      // 1b8c: ldc 0.1
      // 1b8e: ldc_w -5.446
      // 1b91: ldc_w -2.19
      // 1b94: ldc_w 4.974
      // 1b97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ba0: aastore
      // 1ba1: dup
      // 1ba2: bipush 3
      // 1ba3: new net/minecraft/client/animation/Keyframe
      // 1ba6: dup
      // 1ba7: ldc 0.14
      // 1ba9: ldc_w -1.928
      // 1bac: ldc_w -8.39
      // 1baf: ldc_w 0.625
      // 1bb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bbb: aastore
      // 1bbc: dup
      // 1bbd: bipush 4
      // 1bbe: new net/minecraft/client/animation/Keyframe
      // 1bc1: dup
      // 1bc2: ldc 0.18
      // 1bc4: ldc_w 1.334
      // 1bc7: ldc_w -1.337
      // 1bca: ldc_w -2.159
      // 1bcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bd6: aastore
      // 1bd7: dup
      // 1bd8: bipush 5
      // 1bd9: new net/minecraft/client/animation/Keyframe
      // 1bdc: dup
      // 1bdd: ldc 0.21
      // 1bdf: ldc_w -0.759
      // 1be2: ldc_w 6.745
      // 1be5: ldc_w 0.998
      // 1be8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1beb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bf1: aastore
      // 1bf2: dup
      // 1bf3: bipush 6
      // 1bf5: new net/minecraft/client/animation/Keyframe
      // 1bf8: dup
      // 1bf9: ldc 0.24
      // 1bfb: ldc_w -0.054
      // 1bfe: ldc_w 15.566
      // 1c01: ldc_w 1.817
      // 1c04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c0d: aastore
      // 1c0e: dup
      // 1c0f: bipush 7
      // 1c11: new net/minecraft/client/animation/Keyframe
      // 1c14: dup
      // 1c15: ldc 0.28
      // 1c17: ldc_w 0.668
      // 1c1a: ldc_w 23.315
      // 1c1d: ldc_w 2.567
      // 1c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c29: aastore
      // 1c2a: dup
      // 1c2b: bipush 8
      // 1c2d: new net/minecraft/client/animation/Keyframe
      // 1c30: dup
      // 1c31: ldc 0.32
      // 1c33: ldc_w 1.232
      // 1c36: ldc_w 29.343
      // 1c39: ldc_w 3.145
      // 1c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c45: aastore
      // 1c46: dup
      // 1c47: bipush 9
      // 1c49: new net/minecraft/client/animation/Keyframe
      // 1c4c: dup
      // 1c4d: ldc 0.35
      // 1c4f: ldc_w 1.546
      // 1c52: ldc_w 33.447
      // 1c55: ldc_w 3.473
      // 1c58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c61: aastore
      // 1c62: dup
      // 1c63: bipush 10
      // 1c65: new net/minecraft/client/animation/Keyframe
      // 1c68: dup
      // 1c69: ldc 0.42
      // 1c6b: ldc_w 1.334
      // 1c6e: ldc_w 36.087
      // 1c71: ldc_w 3.305
      // 1c74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c7d: aastore
      // 1c7e: dup
      // 1c7f: bipush 11
      // 1c81: new net/minecraft/client/animation/Keyframe
      // 1c84: dup
      // 1c85: ldc 0.45
      // 1c87: ldc_w 1.377
      // 1c8a: ldc_w 38.517
      // 1c8d: ldc_w 3.364
      // 1c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c99: aastore
      // 1c9a: dup
      // 1c9b: bipush 12
      // 1c9d: new net/minecraft/client/animation/Keyframe
      // 1ca0: dup
      // 1ca1: ldc 0.49
      // 1ca3: ldc_w 1.516
      // 1ca6: ldc_w 40.565
      // 1ca9: ldc_w 3.507
      // 1cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cb5: aastore
      // 1cb6: dup
      // 1cb7: bipush 13
      // 1cb9: new net/minecraft/client/animation/Keyframe
      // 1cbc: dup
      // 1cbd: ldc 0.52
      // 1cbf: ldc_w 1.771
      // 1cc2: ldc_w 42.69
      // 1cc5: ldc_w 3.758
      // 1cc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ccb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cd1: aastore
      // 1cd2: dup
      // 1cd3: bipush 14
      // 1cd5: new net/minecraft/client/animation/Keyframe
      // 1cd8: dup
      // 1cd9: ldc 0.56
      // 1cdb: ldc_w 2.028
      // 1cde: ldc_w 44.191
      // 1ce1: ldc_w 4.008
      // 1ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ced: aastore
      // 1cee: dup
      // 1cef: bipush 15
      // 1cf1: new net/minecraft/client/animation/Keyframe
      // 1cf4: dup
      // 1cf5: ldc 0.6
      // 1cf7: ldc_w 2.204
      // 1cfa: ldc_w 44.64
      // 1cfd: ldc_w 4.175
      // 1d00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d09: aastore
      // 1d0a: dup
      // 1d0b: bipush 16
      // 1d0d: new net/minecraft/client/animation/Keyframe
      // 1d10: dup
      // 1d11: ldc 0.63
      // 1d13: ldc_w 2.317
      // 1d16: ldc_w 44.331
      // 1d19: ldc_w 4.277
      // 1d1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d25: aastore
      // 1d26: dup
      // 1d27: bipush 17
      // 1d29: new net/minecraft/client/animation/Keyframe
      // 1d2c: dup
      // 1d2d: ldc 0.7
      // 1d2f: ldc_w 2.276
      // 1d32: ldc_w 41.061
      // 1d35: ldc_w 4.213
      // 1d38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d41: aastore
      // 1d42: dup
      // 1d43: bipush 18
      // 1d45: new net/minecraft/client/animation/Keyframe
      // 1d48: dup
      // 1d49: ldc 0.81
      // 1d4b: ldc_w 0.926
      // 1d4e: ldc_w 24.266
      // 1d51: ldc_w 2.806
      // 1d54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d5d: aastore
      // 1d5e: dup
      // 1d5f: bipush 19
      // 1d61: new net/minecraft/client/animation/Keyframe
      // 1d64: dup
      // 1d65: ldc 0.84
      // 1d67: ldc_w 0.238
      // 1d6a: ldc_w 15.944
      // 1d6d: ldc_w 2.064
      // 1d70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d79: aastore
      // 1d7a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d7d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d80: ldc_w "CTR_upperarm_right.001"
      // 1d83: new net/minecraft/client/animation/AnimationChannel
      // 1d86: dup
      // 1d87: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d8a: bipush 15
      // 1d8c: anewarray 28
      // 1d8f: dup
      // 1d90: bipush 0
      // 1d91: new net/minecraft/client/animation/Keyframe
      // 1d94: dup
      // 1d95: fconst_0
      // 1d96: dconst_1
      // 1d97: dconst_1
      // 1d98: ldc2_w 1.8799999952316284
      // 1d9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1d9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1da1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1da4: aastore
      // 1da5: dup
      // 1da6: bipush 1
      // 1da7: new net/minecraft/client/animation/Keyframe
      // 1daa: dup
      // 1dab: ldc 0.07
      // 1dad: dconst_1
      // 1dae: dconst_1
      // 1daf: ldc2_w 2.1019999980926514
      // 1db2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1db5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1db8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dbb: aastore
      // 1dbc: dup
      // 1dbd: bipush 2
      // 1dbe: new net/minecraft/client/animation/Keyframe
      // 1dc1: dup
      // 1dc2: ldc 0.1
      // 1dc4: dconst_1
      // 1dc5: dconst_1
      // 1dc6: ldc2_w 2.2100000381469727
      // 1dc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1dcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dd2: aastore
      // 1dd3: dup
      // 1dd4: bipush 3
      // 1dd5: new net/minecraft/client/animation/Keyframe
      // 1dd8: dup
      // 1dd9: ldc 0.14
      // 1ddb: dconst_1
      // 1ddc: dconst_1
      // 1ddd: ldc2_w 2.312000036239624
      // 1de0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1de3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1de6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1de9: aastore
      // 1dea: dup
      // 1deb: bipush 4
      // 1dec: new net/minecraft/client/animation/Keyframe
      // 1def: dup
      // 1df0: ldc 0.18
      // 1df2: dconst_1
      // 1df3: dconst_1
      // 1df4: ldc2_w 2.1989998817443848
      // 1df7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1dfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e00: aastore
      // 1e01: dup
      // 1e02: bipush 5
      // 1e03: new net/minecraft/client/animation/Keyframe
      // 1e06: dup
      // 1e07: ldc 0.24
      // 1e09: dconst_1
      // 1e0a: dconst_1
      // 1e0b: ldc2_w 1.9529999494552612
      // 1e0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e17: aastore
      // 1e18: dup
      // 1e19: bipush 6
      // 1e1b: new net/minecraft/client/animation/Keyframe
      // 1e1e: dup
      // 1e1f: ldc 0.28
      // 1e21: dconst_1
      // 1e22: dconst_1
      // 1e23: ldc2_w 1.8589999675750732
      // 1e26: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e2f: aastore
      // 1e30: dup
      // 1e31: bipush 7
      // 1e33: new net/minecraft/client/animation/Keyframe
      // 1e36: dup
      // 1e37: ldc 0.32
      // 1e39: dconst_1
      // 1e3a: dconst_1
      // 1e3b: ldc2_w 1.7949999570846558
      // 1e3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e47: aastore
      // 1e48: dup
      // 1e49: bipush 8
      // 1e4b: new net/minecraft/client/animation/Keyframe
      // 1e4e: dup
      // 1e4f: ldc 0.35
      // 1e51: dconst_1
      // 1e52: dconst_1
      // 1e53: ldc2_w 1.75600004196167
      // 1e56: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e5f: aastore
      // 1e60: dup
      // 1e61: bipush 9
      // 1e63: new net/minecraft/client/animation/Keyframe
      // 1e66: dup
      // 1e67: ldc 0.42
      // 1e69: dconst_1
      // 1e6a: dconst_1
      // 1e6b: ldc2_w 1.7339999675750732
      // 1e6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e77: aastore
      // 1e78: dup
      // 1e79: bipush 10
      // 1e7b: new net/minecraft/client/animation/Keyframe
      // 1e7e: dup
      // 1e7f: ldc 0.56
      // 1e81: dconst_1
      // 1e82: dconst_1
      // 1e83: ldc2_w 1.6579999923706055
      // 1e86: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1e89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e8f: aastore
      // 1e90: dup
      // 1e91: bipush 11
      // 1e93: new net/minecraft/client/animation/Keyframe
      // 1e96: dup
      // 1e97: ldc 0.63
      // 1e99: dconst_1
      // 1e9a: dconst_1
      // 1e9b: ldc2_w 1.6460000276565552
      // 1e9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1ea1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ea4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ea7: aastore
      // 1ea8: dup
      // 1ea9: bipush 12
      // 1eab: new net/minecraft/client/animation/Keyframe
      // 1eae: dup
      // 1eaf: ldc 0.7
      // 1eb1: dconst_1
      // 1eb2: dconst_1
      // 1eb3: ldc2_w 1.6540000438690186
      // 1eb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1eb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ebc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ebf: aastore
      // 1ec0: dup
      // 1ec1: bipush 13
      // 1ec3: new net/minecraft/client/animation/Keyframe
      // 1ec6: dup
      // 1ec7: ldc 0.81
      // 1ec9: dconst_1
      // 1eca: dconst_1
      // 1ecb: ldc2_w 1.715000033378601
      // 1ece: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1ed1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ed4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ed7: aastore
      // 1ed8: dup
      // 1ed9: bipush 14
      // 1edb: new net/minecraft/client/animation/Keyframe
      // 1ede: dup
      // 1edf: ldc 0.84
      // 1ee1: dconst_1
      // 1ee2: dconst_1
      // 1ee3: ldc2_w 1.7890000343322754
      // 1ee6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1ee9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eef: aastore
      // 1ef0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ef3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ef6: ldc_w "CTR_leg_left"
      // 1ef9: new net/minecraft/client/animation/AnimationChannel
      // 1efc: dup
      // 1efd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f00: bipush 20
      // 1f02: anewarray 28
      // 1f05: dup
      // 1f06: bipush 0
      // 1f07: new net/minecraft/client/animation/Keyframe
      // 1f0a: dup
      // 1f0b: fconst_0
      // 1f0c: ldc_w -167.61
      // 1f0f: ldc_w 72.433
      // 1f12: ldc_w 186.033
      // 1f15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f1e: aastore
      // 1f1f: dup
      // 1f20: bipush 1
      // 1f21: new net/minecraft/client/animation/Keyframe
      // 1f24: dup
      // 1f25: ldc 0.07
      // 1f27: ldc_w -168.897
      // 1f2a: ldc_w 49.774
      // 1f2d: ldc_w 176.574
      // 1f30: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f39: aastore
      // 1f3a: dup
      // 1f3b: bipush 2
      // 1f3c: new net/minecraft/client/animation/Keyframe
      // 1f3f: dup
      // 1f40: ldc 0.1
      // 1f42: ldc_w -170.851
      // 1f45: ldc_w 48.848
      // 1f48: ldc_w 174.528
      // 1f4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f54: aastore
      // 1f55: dup
      // 1f56: bipush 3
      // 1f57: new net/minecraft/client/animation/Keyframe
      // 1f5a: dup
      // 1f5b: ldc 0.14
      // 1f5d: ldc_w -171.701
      // 1f60: ldc_w 47.971
      // 1f63: ldc_w 173.298
      // 1f66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f6f: aastore
      // 1f70: dup
      // 1f71: bipush 4
      // 1f72: new net/minecraft/client/animation/Keyframe
      // 1f75: dup
      // 1f76: ldc 0.18
      // 1f78: ldc_w -171.388
      // 1f7b: ldc_w 47.092
      // 1f7e: ldc_w 172.839
      // 1f81: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f8a: aastore
      // 1f8b: dup
      // 1f8c: bipush 5
      // 1f8d: new net/minecraft/client/animation/Keyframe
      // 1f90: dup
      // 1f91: ldc 0.21
      // 1f93: ldc_w -170.524
      // 1f96: ldc_w 45.251
      // 1f99: ldc_w 171.827
      // 1f9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fa2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fa5: aastore
      // 1fa6: dup
      // 1fa7: bipush 6
      // 1fa9: new net/minecraft/client/animation/Keyframe
      // 1fac: dup
      // 1fad: ldc 0.24
      // 1faf: ldc_w -168.701
      // 1fb2: ldc_w 42.325
      // 1fb5: ldc_w 169.792
      // 1fb8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fbb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fbe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fc1: aastore
      // 1fc2: dup
      // 1fc3: bipush 7
      // 1fc5: new net/minecraft/client/animation/Keyframe
      // 1fc8: dup
      // 1fc9: ldc 0.28
      // 1fcb: ldc_w -164.732
      // 1fce: ldc_w 38.325
      // 1fd1: ldc_w 165.498
      // 1fd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fdd: aastore
      // 1fde: dup
      // 1fdf: bipush 8
      // 1fe1: new net/minecraft/client/animation/Keyframe
      // 1fe4: dup
      // 1fe5: ldc 0.32
      // 1fe7: ldc_w -133.156
      // 1fea: ldc_w 32.807
      // 1fed: ldc_w 133.247
      // 1ff0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ff3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ff6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ff9: aastore
      // 1ffa: dup
      // 1ffb: bipush 9
      // 1ffd: new net/minecraft/client/animation/Keyframe
      // 2000: dup
      // 2001: ldc 0.35
      // 2003: ldc_w -97.826
      // 2006: ldc_w 31.935
      // 2009: ldc_w 97.721
      // 200c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 200f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2012: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2015: aastore
      // 2016: dup
      // 2017: bipush 10
      // 2019: new net/minecraft/client/animation/Keyframe
      // 201c: dup
      // 201d: ldc 0.42
      // 201f: ldc_w -92.014
      // 2022: ldc_w 32.449
      // 2025: ldc_w 91.407
      // 2028: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 202b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 202e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2031: aastore
      // 2032: dup
      // 2033: bipush 11
      // 2035: new net/minecraft/client/animation/Keyframe
      // 2038: dup
      // 2039: ldc 0.45
      // 203b: ldc_w -110.957
      // 203e: ldc_w 32.749
      // 2041: ldc_w 109.981
      // 2044: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2047: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 204a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 204d: aastore
      // 204e: dup
      // 204f: bipush 12
      // 2051: new net/minecraft/client/animation/Keyframe
      // 2054: dup
      // 2055: ldc 0.49
      // 2057: ldc_w -133.672
      // 205a: ldc_w 34.276
      // 205d: ldc_w 132.284
      // 2060: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2063: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2066: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2069: aastore
      // 206a: dup
      // 206b: bipush 13
      // 206d: new net/minecraft/client/animation/Keyframe
      // 2070: dup
      // 2071: ldc 0.52
      // 2073: ldc_w -150.577
      // 2076: ldc_w 37.245
      // 2079: ldc_w 148.769
      // 207c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 207f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2082: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2085: aastore
      // 2086: dup
      // 2087: bipush 14
      // 2089: new net/minecraft/client/animation/Keyframe
      // 208c: dup
      // 208d: ldc 0.56
      // 208f: ldc_w -160.994
      // 2092: ldc_w 41.189
      // 2095: ldc_w 158.784
      // 2098: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 209b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 209e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20a1: aastore
      // 20a2: dup
      // 20a3: bipush 15
      // 20a5: new net/minecraft/client/animation/Keyframe
      // 20a8: dup
      // 20a9: ldc 0.6
      // 20ab: ldc_w -167.328
      // 20ae: ldc_w 45.678
      // 20b1: ldc_w 164.855
      // 20b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20bd: aastore
      // 20be: dup
      // 20bf: bipush 16
      // 20c1: new net/minecraft/client/animation/Keyframe
      // 20c4: dup
      // 20c5: ldc 0.63
      // 20c7: ldc_w -171.256
      // 20ca: ldc_w 50.513
      // 20cd: ldc_w 168.853
      // 20d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20d9: aastore
      // 20da: dup
      // 20db: bipush 17
      // 20dd: new net/minecraft/client/animation/Keyframe
      // 20e0: dup
      // 20e1: ldc 0.7
      // 20e3: ldc_w -174.515
      // 20e6: ldc_w 60.713
      // 20e9: ldc_w 174.41
      // 20ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20f5: aastore
      // 20f6: dup
      // 20f7: bipush 18
      // 20f9: new net/minecraft/client/animation/Keyframe
      // 20fc: dup
      // 20fd: ldc 0.81
      // 20ff: ldc_w -170.2
      // 2102: ldc_w 73.883
      // 2105: ldc_w 183.488
      // 2108: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 210b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 210e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2111: aastore
      // 2112: dup
      // 2113: bipush 19
      // 2115: new net/minecraft/client/animation/Keyframe
      // 2118: dup
      // 2119: ldc 0.84
      // 211b: ldc_w -168.455
      // 211e: ldc_w 74.56
      // 2121: ldc_w 185.481
      // 2124: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2127: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 212a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 212d: aastore
      // 212e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2131: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2134: ldc_w "CTR_leg_left"
      // 2137: new net/minecraft/client/animation/AnimationChannel
      // 213a: dup
      // 213b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 213e: bipush 19
      // 2140: anewarray 28
      // 2143: dup
      // 2144: bipush 0
      // 2145: new net/minecraft/client/animation/Keyframe
      // 2148: dup
      // 2149: fconst_0
      // 214a: ldc_w -5.849
      // 214d: ldc_w 26.24
      // 2150: ldc_w -13.051
      // 2153: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 215c: aastore
      // 215d: dup
      // 215e: bipush 1
      // 215f: new net/minecraft/client/animation/Keyframe
      // 2162: dup
      // 2163: ldc 0.07
      // 2165: ldc_w -6.351
      // 2168: ldc_w 25.569
      // 216b: ldc_w -15.565
      // 216e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2171: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2174: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2177: aastore
      // 2178: dup
      // 2179: bipush 2
      // 217a: new net/minecraft/client/animation/Keyframe
      // 217d: dup
      // 217e: ldc 0.1
      // 2180: ldc_w -5.168
      // 2183: ldc_w 17.207
      // 2186: ldc_w -13.601
      // 2189: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 218c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 218f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2192: aastore
      // 2193: dup
      // 2194: bipush 3
      // 2195: new net/minecraft/client/animation/Keyframe
      // 2198: dup
      // 2199: ldc 0.18
      // 219b: ldc_w -2.801
      // 219e: ldc_w 0.483
      // 21a1: ldc_w -9.674
      // 21a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ad: aastore
      // 21ae: dup
      // 21af: bipush 4
      // 21b0: new net/minecraft/client/animation/Keyframe
      // 21b3: dup
      // 21b4: ldc 0.21
      // 21b6: ldc_w -1.618
      // 21b9: ldc_w -7.879
      // 21bc: ldc_w -7.71
      // 21bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21c8: aastore
      // 21c9: dup
      // 21ca: bipush 5
      // 21cb: new net/minecraft/client/animation/Keyframe
      // 21ce: dup
      // 21cf: ldc 0.24
      // 21d1: ldc_w -0.767
      // 21d4: ldc_w -14.183
      // 21d7: ldc_w -5.132
      // 21da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21e3: aastore
      // 21e4: dup
      // 21e5: bipush 6
      // 21e7: new net/minecraft/client/animation/Keyframe
      // 21ea: dup
      // 21eb: ldc 0.28
      // 21ed: ldc_w -0.111
      // 21f0: ldc_w -19.476
      // 21f3: ldc_w -2.649
      // 21f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21ff: aastore
      // 2200: dup
      // 2201: bipush 7
      // 2203: new net/minecraft/client/animation/Keyframe
      // 2206: dup
      // 2207: ldc 0.32
      // 2209: ldc_w -0.107
      // 220c: ldc_w -21.519
      // 220f: ldc_w -2.549
      // 2212: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2215: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2218: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 221b: aastore
      // 221c: dup
      // 221d: bipush 8
      // 221f: new net/minecraft/client/animation/Keyframe
      // 2222: dup
      // 2223: ldc 0.35
      // 2225: ldc_w -0.786
      // 2228: ldc_w -18.057
      // 222b: ldc_w -2.479
      // 222e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2231: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2234: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2237: aastore
      // 2238: dup
      // 2239: bipush 9
      // 223b: new net/minecraft/client/animation/Keyframe
      // 223e: dup
      // 223f: ldc 0.42
      // 2241: ldc_w -1.925
      // 2244: ldc_w -10.94
      // 2247: ldc_w -3.529
      // 224a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 224d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2250: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2253: aastore
      // 2254: dup
      // 2255: bipush 10
      // 2257: new net/minecraft/client/animation/Keyframe
      // 225a: dup
      // 225b: ldc 0.45
      // 225d: ldc_w -2.428
      // 2260: ldc_w -7.415
      // 2263: ldc_w -4.34
      // 2266: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2269: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 226c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 226f: aastore
      // 2270: dup
      // 2271: bipush 11
      // 2273: new net/minecraft/client/animation/Keyframe
      // 2276: dup
      // 2277: ldc 0.49
      // 2279: ldc_w -2.897
      // 227c: ldc_w -3.952
      // 227f: ldc_w -5.251
      // 2282: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2285: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2288: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 228b: aastore
      // 228c: dup
      // 228d: bipush 12
      // 228f: new net/minecraft/client/animation/Keyframe
      // 2292: dup
      // 2293: ldc 0.52
      // 2295: ldc_w -3.336
      // 2298: ldc_w -0.571
      // 229b: ldc_w -6.227
      // 229e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22a7: aastore
      // 22a8: dup
      // 22a9: bipush 13
      // 22ab: new net/minecraft/client/animation/Keyframe
      // 22ae: dup
      // 22af: ldc 0.56
      // 22b1: ldc_w -3.748
      // 22b4: ldc_w 2.709
      // 22b7: ldc_w -7.24
      // 22ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22c3: aastore
      // 22c4: dup
      // 22c5: bipush 14
      // 22c7: new net/minecraft/client/animation/Keyframe
      // 22ca: dup
      // 22cb: ldc 0.6
      // 22cd: ldc_w -4.135
      // 22d0: ldc_w 5.872
      // 22d3: ldc_w -8.268
      // 22d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22df: aastore
      // 22e0: dup
      // 22e1: bipush 15
      // 22e3: new net/minecraft/client/animation/Keyframe
      // 22e6: dup
      // 22e7: ldc 0.63
      // 22e9: ldc_w -4.496
      // 22ec: ldc_w 8.901
      // 22ef: ldc_w -9.296
      // 22f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22fb: aastore
      // 22fc: dup
      // 22fd: bipush 16
      // 22ff: new net/minecraft/client/animation/Keyframe
      // 2302: dup
      // 2303: ldc 0.7
      // 2305: ldc_w -5.141
      // 2308: ldc_w 14.482
      // 230b: ldc_w -11.283
      // 230e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2311: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2314: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2317: aastore
      // 2318: dup
      // 2319: bipush 17
      // 231b: new net/minecraft/client/animation/Keyframe
      // 231e: dup
      // 231f: ldc 0.81
      // 2321: ldc_w -5.895
      // 2324: ldc_w 21.287
      // 2327: ldc_w -13.856
      // 232a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 232d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2333: aastore
      // 2334: dup
      // 2335: bipush 18
      // 2337: new net/minecraft/client/animation/Keyframe
      // 233a: dup
      // 233b: ldc 0.84
      // 233d: ldc_w -5.95
      // 2340: ldc_w 23.754
      // 2343: ldc_w -13.665
      // 2346: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2349: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 234c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 234f: aastore
      // 2350: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2353: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2356: ldc_w "CTR_leg_right"
      // 2359: new net/minecraft/client/animation/AnimationChannel
      // 235c: dup
      // 235d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2360: bipush 20
      // 2362: anewarray 28
      // 2365: dup
      // 2366: bipush 0
      // 2367: new net/minecraft/client/animation/Keyframe
      // 236a: dup
      // 236b: fconst_0
      // 236c: ldc_w 175.231
      // 236f: ldc_w 64.512
      // 2372: ldc_w 171.72
      // 2375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 237b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 237e: aastore
      // 237f: dup
      // 2380: bipush 1
      // 2381: new net/minecraft/client/animation/Keyframe
      // 2384: dup
      // 2385: ldc 0.07
      // 2387: ldc_w 176.311
      // 238a: ldc_w 55.185
      // 238d: ldc_w 164.167
      // 2390: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2393: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2396: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2399: aastore
      // 239a: dup
      // 239b: bipush 2
      // 239c: new net/minecraft/client/animation/Keyframe
      // 239f: dup
      // 23a0: ldc 0.1
      // 23a2: ldc_w 176.649
      // 23a5: ldc_w 50.459
      // 23a8: ldc_w 159.979
      // 23ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b4: aastore
      // 23b5: dup
      // 23b6: bipush 3
      // 23b7: new net/minecraft/client/animation/Keyframe
      // 23ba: dup
      // 23bb: ldc 0.14
      // 23bd: ldc_w 175.249
      // 23c0: ldc_w 45.98
      // 23c3: ldc_w 157.362
      // 23c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23cf: aastore
      // 23d0: dup
      // 23d1: bipush 4
      // 23d2: new net/minecraft/client/animation/Keyframe
      // 23d5: dup
      // 23d6: ldc 0.18
      // 23d8: ldc_w 172.483
      // 23db: ldc_w 41.733
      // 23de: ldc_w 155.571
      // 23e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ea: aastore
      // 23eb: dup
      // 23ec: bipush 5
      // 23ed: new net/minecraft/client/animation/Keyframe
      // 23f0: dup
      // 23f1: ldc 0.21
      // 23f3: ldc_w 168.773
      // 23f6: ldc_w 37.443
      // 23f9: ldc_w 152.701
      // 23fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2402: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2405: aastore
      // 2406: dup
      // 2407: bipush 6
      // 2409: new net/minecraft/client/animation/Keyframe
      // 240c: dup
      // 240d: ldc 0.24
      // 240f: ldc_w 156.792
      // 2412: ldc_w 30.263
      // 2415: ldc_w 141.36
      // 2418: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 241b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 241e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2421: aastore
      // 2422: dup
      // 2423: bipush 7
      // 2425: new net/minecraft/client/animation/Keyframe
      // 2428: dup
      // 2429: ldc 0.28
      // 242b: ldc_w 135.363
      // 242e: ldc_w 26.053
      // 2431: ldc_w 120.52
      // 2434: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2437: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 243a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 243d: aastore
      // 243e: dup
      // 243f: bipush 8
      // 2441: new net/minecraft/client/animation/Keyframe
      // 2444: dup
      // 2445: ldc 0.32
      // 2447: ldc_w 105.392
      // 244a: ldc_w 24.436
      // 244d: ldc_w 91.112
      // 2450: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2453: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2456: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2459: aastore
      // 245a: dup
      // 245b: bipush 9
      // 245d: new net/minecraft/client/animation/Keyframe
      // 2460: dup
      // 2461: ldc 0.35
      // 2463: ldc_w 84.375
      // 2466: ldc_w 24.354
      // 2469: ldc_w 70.649
      // 246c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 246f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2472: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2475: aastore
      // 2476: dup
      // 2477: bipush 10
      // 2479: new net/minecraft/client/animation/Keyframe
      // 247c: dup
      // 247d: ldc 0.42
      // 247f: ldc_w 98.84
      // 2482: ldc_w 24.105
      // 2485: ldc_w 86.205
      // 2488: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 248b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 248e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2491: aastore
      // 2492: dup
      // 2493: bipush 11
      // 2495: new net/minecraft/client/animation/Keyframe
      // 2498: dup
      // 2499: ldc 0.45
      // 249b: ldc_w 133.795
      // 249e: ldc_w 25.469
      // 24a1: ldc_w 121.66
      // 24a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24ad: aastore
      // 24ae: dup
      // 24af: bipush 12
      // 24b1: new net/minecraft/client/animation/Keyframe
      // 24b4: dup
      // 24b5: ldc 0.49
      // 24b7: ldc_w 157.985
      // 24ba: ldc_w 30.097
      // 24bd: ldc_w 146.263
      // 24c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24c9: aastore
      // 24ca: dup
      // 24cb: bipush 13
      // 24cd: new net/minecraft/client/animation/Keyframe
      // 24d0: dup
      // 24d1: ldc 0.52
      // 24d3: ldc_w 169.016
      // 24d6: ldc_w 37.518
      // 24d9: ldc_w 157.534
      // 24dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24e5: aastore
      // 24e6: dup
      // 24e7: bipush 14
      // 24e9: new net/minecraft/client/animation/Keyframe
      // 24ec: dup
      // 24ed: ldc 0.56
      // 24ef: ldc_w 174.364
      // 24f2: ldc_w 46.885
      // 24f5: ldc_w 162.833
      // 24f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2501: aastore
      // 2502: dup
      // 2503: bipush 15
      // 2505: new net/minecraft/client/animation/Keyframe
      // 2508: dup
      // 2509: ldc 0.6
      // 250b: ldc_w 177.416
      // 250e: ldc_w 57.345
      // 2511: ldc_w 165.464
      // 2514: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2517: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 251a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 251d: aastore
      // 251e: dup
      // 251f: bipush 16
      // 2521: new net/minecraft/client/animation/Keyframe
      // 2524: dup
      // 2525: ldc 0.63
      // 2527: ldc_w 179.427
      // 252a: ldc_w 68.086
      // 252d: ldc_w 166.8
      // 2530: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2539: aastore
      // 253a: dup
      // 253b: bipush 17
      // 253d: new net/minecraft/client/animation/Keyframe
      // 2540: dup
      // 2541: ldc 0.7
      // 2543: ldc_w 181.605
      // 2546: ldc_w 87.532
      // 2549: ldc_w 168.17
      // 254c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 254f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2552: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2555: aastore
      // 2556: dup
      // 2557: bipush 18
      // 2559: new net/minecraft/client/animation/Keyframe
      // 255c: dup
      // 255d: ldc 0.81
      // 255f: ldc_w 176.45
      // 2562: ldc_w 104.063
      // 2565: ldc_w 175.653
      // 2568: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 256b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 256e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2571: aastore
      // 2572: dup
      // 2573: bipush 19
      // 2575: new net/minecraft/client/animation/Keyframe
      // 2578: dup
      // 2579: ldc 0.84
      // 257b: ldc_w 175.312
      // 257e: ldc_w 86.37
      // 2581: ldc_w 175.27
      // 2584: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2587: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 258a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 258d: aastore
      // 258e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2591: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2594: ldc_w "CTR_leg_right"
      // 2597: new net/minecraft/client/animation/AnimationChannel
      // 259a: dup
      // 259b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 259e: bipush 20
      // 25a0: anewarray 28
      // 25a3: dup
      // 25a4: bipush 0
      // 25a5: new net/minecraft/client/animation/Keyframe
      // 25a8: dup
      // 25a9: fconst_0
      // 25aa: ldc_w -3.76
      // 25ad: ldc_w 19.189
      // 25b0: ldc_w -5.073
      // 25b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25bc: aastore
      // 25bd: dup
      // 25be: bipush 1
      // 25bf: new net/minecraft/client/animation/Keyframe
      // 25c2: dup
      // 25c3: ldc 0.07
      // 25c5: ldc_w -2.783
      // 25c8: ldc_w 7.41
      // 25cb: ldc_w -5.416
      // 25ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25d7: aastore
      // 25d8: dup
      // 25d9: bipush 2
      // 25da: new net/minecraft/client/animation/Keyframe
      // 25dd: dup
      // 25de: ldc 0.1
      // 25e0: ldc_w -1.634
      // 25e3: ldc_w 0.298
      // 25e6: ldc_w -5.626
      // 25e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25f2: aastore
      // 25f3: dup
      // 25f4: bipush 3
      // 25f5: new net/minecraft/client/animation/Keyframe
      // 25f8: dup
      // 25f9: ldc 0.14
      // 25fb: ldc_w -0.273
      // 25fe: ldc_w -5.854
      // 2601: ldc_w -4.738
      // 2604: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2607: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 260a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 260d: aastore
      // 260e: dup
      // 260f: bipush 4
      // 2610: new net/minecraft/client/animation/Keyframe
      // 2613: dup
      // 2614: ldc 0.18
      // 2616: ldc_w 0.72
      // 2619: ldc_w -10.196
      // 261c: ldc_w -2.732
      // 261f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2622: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2625: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2628: aastore
      // 2629: dup
      // 262a: bipush 5
      // 262b: new net/minecraft/client/animation/Keyframe
      // 262e: dup
      // 262f: ldc 0.21
      // 2631: ldc_w 1.714
      // 2634: ldc_w -14.539
      // 2637: ldc_w -0.726
      // 263a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 263d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2640: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2643: aastore
      // 2644: dup
      // 2645: bipush 6
      // 2647: new net/minecraft/client/animation/Keyframe
      // 264a: dup
      // 264b: ldc 0.24
      // 264d: ldc_w 1.69
      // 2650: ldc_w -14.433
      // 2653: ldc_w -0.775
      // 2656: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2659: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 265c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 265f: aastore
      // 2660: dup
      // 2661: bipush 7
      // 2663: new net/minecraft/client/animation/Keyframe
      // 2666: dup
      // 2667: ldc 0.28
      // 2669: ldc_w 1.619
      // 266c: ldc_w -14.121
      // 266f: ldc_w -0.919
      // 2672: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2675: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2678: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 267b: aastore
      // 267c: dup
      // 267d: bipush 8
      // 267f: new net/minecraft/client/animation/Keyframe
      // 2682: dup
      // 2683: ldc 0.32
      // 2685: ldc_w 1.503
      // 2688: ldc_w -13.616
      // 268b: ldc_w -1.152
      // 268e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2691: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2694: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2697: aastore
      // 2698: dup
      // 2699: bipush 9
      // 269b: new net/minecraft/client/animation/Keyframe
      // 269e: dup
      // 269f: ldc 0.35
      // 26a1: ldc_w 1.346
      // 26a4: ldc_w -12.928
      // 26a7: ldc_w -1.47
      // 26aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26b3: aastore
      // 26b4: dup
      // 26b5: bipush 10
      // 26b7: new net/minecraft/client/animation/Keyframe
      // 26ba: dup
      // 26bb: ldc 0.42
      // 26bd: ldc_w 0.916
      // 26c0: ldc_w -11.052
      // 26c3: ldc_w -2.337
      // 26c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26cf: aastore
      // 26d0: dup
      // 26d1: bipush 11
      // 26d3: new net/minecraft/client/animation/Keyframe
      // 26d6: dup
      // 26d7: ldc 0.45
      // 26d9: ldc_w 0.649
      // 26dc: ldc_w -9.885
      // 26df: ldc_w -2.876
      // 26e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26eb: aastore
      // 26ec: dup
      // 26ed: bipush 12
      // 26ef: new net/minecraft/client/animation/Keyframe
      // 26f2: dup
      // 26f3: ldc 0.49
      // 26f5: ldc_w 0.351
      // 26f8: ldc_w -8.583
      // 26fb: ldc_w -3.477
      // 26fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2701: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2704: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2707: aastore
      // 2708: dup
      // 2709: bipush 13
      // 270b: new net/minecraft/client/animation/Keyframe
      // 270e: dup
      // 270f: ldc 0.52
      // 2711: ldc_w -0.06
      // 2714: ldc_w -6.417
      // 2717: ldc_w -3.897
      // 271a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 271d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2720: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2723: aastore
      // 2724: dup
      // 2725: bipush 14
      // 2727: new net/minecraft/client/animation/Keyframe
      // 272a: dup
      // 272b: ldc 0.56
      // 272d: ldc_w -0.581
      // 2730: ldc_w -3.311
      // 2733: ldc_w -3.644
      // 2736: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2739: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 273c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 273f: aastore
      // 2740: dup
      // 2741: bipush 15
      // 2743: new net/minecraft/client/animation/Keyframe
      // 2746: dup
      // 2747: ldc 0.6
      // 2749: ldc_w -1.052
      // 274c: ldc_w -0.871
      // 274f: ldc_w -3.167
      // 2752: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2755: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2758: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 275b: aastore
      // 275c: dup
      // 275d: bipush 16
      // 275f: new net/minecraft/client/animation/Keyframe
      // 2762: dup
      // 2763: ldc 0.63
      // 2765: ldc_w -1.487
      // 2768: ldc_w 0.924
      // 276b: ldc_w -2.765
      // 276e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2777: aastore
      // 2778: dup
      // 2779: bipush 17
      // 277b: new net/minecraft/client/animation/Keyframe
      // 277e: dup
      // 277f: ldc 0.7
      // 2781: ldc_w -2.258
      // 2784: ldc_w 3.598
      // 2787: ldc_w -2.677
      // 278a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 278d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2790: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2793: aastore
      // 2794: dup
      // 2795: bipush 18
      // 2797: new net/minecraft/client/animation/Keyframe
      // 279a: dup
      // 279b: ldc 0.81
      // 279d: ldc_w -2.439
      // 27a0: ldc_w 8.247
      // 27a3: ldc_w -1.832
      // 27a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27af: aastore
      // 27b0: dup
      // 27b1: bipush 19
      // 27b3: new net/minecraft/client/animation/Keyframe
      // 27b6: dup
      // 27b7: ldc 0.84
      // 27b9: ldc_w -2.576
      // 27bc: ldc_w 14.069
      // 27bf: ldc_w -0.949
      // 27c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27cb: aastore
      // 27cc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 27cf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 27d2: ldc_w "CTR_leg_right.002"
      // 27d5: new net/minecraft/client/animation/AnimationChannel
      // 27d8: dup
      // 27d9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 27dc: bipush 20
      // 27de: anewarray 28
      // 27e1: dup
      // 27e2: bipush 0
      // 27e3: new net/minecraft/client/animation/Keyframe
      // 27e6: dup
      // 27e7: fconst_0
      // 27e8: ldc_w 2.425
      // 27eb: fconst_0
      // 27ec: ldc -22.403
      // 27ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27f7: aastore
      // 27f8: dup
      // 27f9: bipush 1
      // 27fa: new net/minecraft/client/animation/Keyframe
      // 27fd: dup
      // 27fe: ldc 0.07
      // 2800: ldc_w 2.425
      // 2803: fconst_0
      // 2804: ldc -10.417
      // 2806: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2809: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 280c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 280f: aastore
      // 2810: dup
      // 2811: bipush 2
      // 2812: new net/minecraft/client/animation/Keyframe
      // 2815: dup
      // 2816: ldc 0.1
      // 2818: ldc_w 2.425
      // 281b: fconst_0
      // 281c: ldc -4.175
      // 281e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2821: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2824: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2827: aastore
      // 2828: dup
      // 2829: bipush 3
      // 282a: new net/minecraft/client/animation/Keyframe
      // 282d: dup
      // 282e: ldc 0.14
      // 2830: ldc_w 2.425
      // 2833: fconst_0
      // 2834: ldc 2.125
      // 2836: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 283c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 283f: aastore
      // 2840: dup
      // 2841: bipush 4
      // 2842: new net/minecraft/client/animation/Keyframe
      // 2845: dup
      // 2846: ldc 0.18
      // 2848: ldc_w 2.425
      // 284b: fconst_0
      // 284c: ldc 8.391
      // 284e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2851: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2854: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2857: aastore
      // 2858: dup
      // 2859: bipush 5
      // 285a: new net/minecraft/client/animation/Keyframe
      // 285d: dup
      // 285e: ldc 0.21
      // 2860: ldc_w 2.425
      // 2863: fconst_0
      // 2864: ldc 14.501
      // 2866: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2869: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 286c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 286f: aastore
      // 2870: dup
      // 2871: bipush 6
      // 2873: new net/minecraft/client/animation/Keyframe
      // 2876: dup
      // 2877: ldc 0.24
      // 2879: ldc_w 2.344
      // 287c: ldc_w 0.194
      // 287f: ldc 18.962
      // 2881: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2884: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2887: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 288a: aastore
      // 288b: dup
      // 288c: bipush 7
      // 288e: new net/minecraft/client/animation/Keyframe
      // 2891: dup
      // 2892: ldc 0.28
      // 2894: ldc_w 2.126
      // 2897: ldc_w 0.713
      // 289a: ldc 21.301
      // 289c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 289f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28a5: aastore
      // 28a6: dup
      // 28a7: bipush 8
      // 28a9: new net/minecraft/client/animation/Keyframe
      // 28ac: dup
      // 28ad: ldc 0.32
      // 28af: ldc_w 1.805
      // 28b2: ldc_w 1.465
      // 28b5: ldc_w 21.779
      // 28b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28c1: aastore
      // 28c2: dup
      // 28c3: bipush 9
      // 28c5: new net/minecraft/client/animation/Keyframe
      // 28c8: dup
      // 28c9: ldc 0.35
      // 28cb: ldc_w 1.414
      // 28ce: ldc_w 2.357
      // 28d1: ldc 20.649
      // 28d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28dc: aastore
      // 28dd: dup
      // 28de: bipush 10
      // 28e0: new net/minecraft/client/animation/Keyframe
      // 28e3: dup
      // 28e4: ldc 0.42
      // 28e6: ldc_w 0.565
      // 28e9: ldc_w 4.187
      // 28ec: ldc 14.544
      // 28ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28f7: aastore
      // 28f8: dup
      // 28f9: bipush 11
      // 28fb: new net/minecraft/client/animation/Keyframe
      // 28fe: dup
      // 28ff: ldc 0.45
      // 2901: ldc_w 0.175
      // 2904: ldc_w 4.939
      // 2907: ldc 10.046
      // 2909: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 290c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 290f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2912: aastore
      // 2913: dup
      // 2914: bipush 12
      // 2916: new net/minecraft/client/animation/Keyframe
      // 2919: dup
      // 291a: ldc 0.49
      // 291c: ldc_w -0.147
      // 291f: ldc_w 5.458
      // 2922: ldc 4.895
      // 2924: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2927: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 292a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 292d: aastore
      // 292e: dup
      // 292f: bipush 13
      // 2931: new net/minecraft/client/animation/Keyframe
      // 2934: dup
      // 2935: ldc 0.52
      // 2937: ldc_w -0.365
      // 293a: ldc_w 5.652
      // 293d: ldc -0.678
      // 293f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2942: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2945: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2948: aastore
      // 2949: dup
      // 294a: bipush 14
      // 294c: new net/minecraft/client/animation/Keyframe
      // 294f: dup
      // 2950: ldc 0.56
      // 2952: ldc_w -0.445
      // 2955: ldc_w 5.582
      // 2958: ldc -6.446
      // 295a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 295d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2960: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2963: aastore
      // 2964: dup
      // 2965: bipush 15
      // 2967: new net/minecraft/client/animation/Keyframe
      // 296a: dup
      // 296b: ldc 0.6
      // 296d: ldc_w -0.326
      // 2970: ldc_w 5.372
      // 2973: ldc_w -12.184
      // 2976: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2979: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 297c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 297f: aastore
      // 2980: dup
      // 2981: bipush 16
      // 2983: new net/minecraft/client/animation/Keyframe
      // 2986: dup
      // 2987: ldc 0.63
      // 2989: ldc_w -0.012
      // 298c: ldc_w 5.017
      // 298f: ldc -17.665
      // 2991: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2994: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2997: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 299a: aastore
      // 299b: dup
      // 299c: bipush 17
      // 299e: new net/minecraft/client/animation/Keyframe
      // 29a1: dup
      // 29a2: ldc 0.7
      // 29a4: ldc_w 0.941
      // 29a7: ldc_w 3.802
      // 29aa: ldc_w -26.953
      // 29ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29b6: aastore
      // 29b7: dup
      // 29b8: bipush 18
      // 29ba: new net/minecraft/client/animation/Keyframe
      // 29bd: dup
      // 29be: ldc 0.81
      // 29c0: ldc_w 2.207
      // 29c3: fconst_0
      // 29c4: ldc -33.269
      // 29c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29cf: aastore
      // 29d0: dup
      // 29d1: bipush 19
      // 29d3: new net/minecraft/client/animation/Keyframe
      // 29d6: dup
      // 29d7: ldc 0.84
      // 29d9: ldc_w 2.326
      // 29dc: fconst_0
      // 29dd: ldc_w -28.017
      // 29e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29e9: aastore
      // 29ea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 29ed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 29f0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 29f3: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiRun.RUN Lnet/minecraft/client/animation/AnimationDefinition;
      // 29f6: return
   }
}
