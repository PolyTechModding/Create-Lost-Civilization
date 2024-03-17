package com.polytechmodding.createlostcivilization.mixins;

import com.polytechmodding.createlostcivilization.entities.AirEntity;
import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean canStandOnFluid(FluidState fluidState);
    @Shadow
    protected abstract boolean isAffectedByFluids();
    @Shadow
    protected abstract float getWaterSlowDown();
    @Shadow
    public abstract float getSpeed();
    @Shadow
    public abstract boolean onClimbable();
    @Shadow
    public abstract Vec3 getFluidFallingAdjustedMovement(double d, boolean bl, Vec3 vec3);
    @Shadow
    protected abstract void jumpInLiquid(TagKey<Fluid> tagKey);
    @Shadow protected abstract void jumpFromGround();
    @Shadow private int noJumpDelay;

    @Shadow public abstract boolean hasEffect(MobEffect mobEffect);

    @Shadow public abstract boolean isFallFlying();

    @Shadow protected abstract SoundEvent getFallDamageSound(int i);

    @Shadow public abstract Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 vec3, float f);

    @Shadow public abstract @Nullable MobEffectInstance getEffect(MobEffect mobEffect);

    @Shadow public abstract boolean shouldDiscardFriction();

    @Shadow public abstract void calculateEntityAnimation(boolean bl);

    @Unique
    private float createLostCivilization$waterSlowDown = 0.8F;

    @Inject(at = @At("HEAD"), method = "getWaterSlowDown()F", cancellable = true)
    protected void slowDownInject(@NotNull CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(createLostCivilization$waterSlowDown);
    }

    @Inject(method = "aiStep()V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidJumpThreshold()D"))
    public void injectJump(CallbackInfo ci) {
        double currentHeight = ((AirEntity)this).getCreateLostCivilization$airHeight();
        boolean valid = ((AirEntity)this).createLostCivilization$isInAir() && currentHeight > 0.0;
        // System.out.println("Jump code was called");
        if(valid && (!this.onGround() || currentHeight > this.getFluidJumpThreshold())) {
            // System.out.println("Fluid Jump");
            this.jumpInLiquid(FluidTags.WATER);
        } else if ((this.onGround() || valid && currentHeight <= this.getFluidJumpThreshold())
                && this.noJumpDelay == 0) {
            this.jumpFromGround();
            this.noJumpDelay = 10;
        }
    }

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"),
    cancellable = true)
    public void injectElseIf(Vec3 vec3, CallbackInfo ci) {
        if (this.isControlledByLocalInstance()) {
            boolean bl;
            double d = 0.08;
            boolean bl2 = bl = this.getDeltaMovement().y <= 0.0;
            if (bl && this.hasEffect(MobEffects.SLOW_FALLING)) {
                d = 0.01;
            }
            FluidState fluidState = this.level().getFluidState(this.blockPosition());
            if (this.isInWater() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidState)) {
                double e = this.getY();
                float f = this.isSprinting() ? 0.9f : this.getWaterSlowDown();
                float g = 0.02f;
                float h = EnchantmentHelper.getDepthStrider((LivingEntity)(Object) this);
                if (h > 3.0f) {
                    h = 3.0f;
                }
                if (!this.onGround()) {
                    h *= 0.5f;
                }
                if (h > 0.0f) {
                    f += (0.54600006f - f) * h / 3.0f;
                    g += (this.getSpeed() - g) * h / 3.0f;
                }
                if (this.hasEffect(MobEffects.DOLPHINS_GRACE)) {
                    f = 0.96f;
                }
                this.moveRelative(g, vec3);
                this.move(MoverType.SELF, this.getDeltaMovement());
                Vec3 vec32 = this.getDeltaMovement();
                if (this.horizontalCollision && this.onClimbable()) {
                    vec32 = new Vec3(vec32.x, 0.2, vec32.z);
                }
                this.setDeltaMovement(vec32.multiply(f, 0.8f, f));
                Vec3 vec33 = this.getFluidFallingAdjustedMovement(d, bl, this.getDeltaMovement());
                this.setDeltaMovement(vec33);
                if (this.horizontalCollision && this.isFree(vec33.x, vec33.y + (double)0.6f - this.getY() + e, vec33.z)) {
                    this.setDeltaMovement(vec33.x, 0.3f, vec33.z);
                }
            } else if (((AirEntity) this).createLostCivilization$isInAir() && this.isAffectedByFluids()) {
                // System.out.println("I got called");
                double e = this.getY();
                float f = this.isSprinting() ? 0.9f : this.getWaterSlowDown();
                float g = 0.02f;
                float h = EnchantmentHelper.getDepthStrider((LivingEntity)(Object) this);
                if (h > 3.0f) {
                    h = 3.0f;
                }
                if (!this.onGround()) {
                    h *= 0.5f;
                }
                if (h > 0.0f) {
                    f += (0.54600006f - f) * h / 3.0f;
                    g += (this.getSpeed() - g) * h / 3.0f;
                }
                this.moveRelative(g, vec3);
                this.move(MoverType.SELF, this.getDeltaMovement());
                Vec3 vec32 = this.getDeltaMovement();
                if (this.horizontalCollision && this.onClimbable()) {
                    vec32 = new Vec3(vec32.x, 0.2, vec32.z);
                }
                this.setDeltaMovement(vec32.multiply(f, 0.8f, f));
                Vec3 vec33 = this.getFluidFallingAdjustedMovement(0.08, this.getDeltaMovement().y <= 0.0, this.getDeltaMovement());
                this.setDeltaMovement(vec33);
                if (this.horizontalCollision && this.isFree(vec33.x, vec33.y + (double)0.6f - this.getY() + e, vec33.z)) {
                    this.setDeltaMovement(vec33.x, 0.3f, vec33.z);
                }
            } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidState)) {
                Vec3 vec34;
                double e = this.getY();
                this.moveRelative(0.02f, vec3);
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.8f, 0.5));
                    vec34 = this.getFluidFallingAdjustedMovement(d, bl, this.getDeltaMovement());
                    this.setDeltaMovement(vec34);
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
                }
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0, -d / 4.0, 0.0));
                }
                vec34 = this.getDeltaMovement();
                if (this.horizontalCollision && this.isFree(vec34.x, vec34.y + (double)0.6f - this.getY() + e, vec34.z)) {
                    this.setDeltaMovement(vec34.x, 0.3f, vec34.z);
                }
            } else if (this.isFallFlying()) {
                double n;
                float o;
                double m;
                this.checkSlowFallDistance();
                Vec3 vec35 = this.getDeltaMovement();
                Vec3 vec36 = this.getLookAngle();
                float f = this.getXRot() * ((float)Math.PI / 180);
                double i = Math.sqrt(vec36.x * vec36.x + vec36.z * vec36.z);
                double j = vec35.horizontalDistance();
                double k = vec36.length();
                double l = Math.cos(f);
                l = l * l * Math.min(1.0, k / 0.4);
                vec35 = this.getDeltaMovement().add(0.0, d * (-1.0 + l * 0.75), 0.0);
                if (vec35.y < 0.0 && i > 0.0) {
                    m = vec35.y * -0.1 * l;
                    vec35 = vec35.add(vec36.x * m / i, m, vec36.z * m / i);
                }
                if (f < 0.0f && i > 0.0) {
                    m = j * (double)(-Mth.sin(f)) * 0.04;
                    vec35 = vec35.add(-vec36.x * m / i, m * 3.2, -vec36.z * m / i);
                }
                if (i > 0.0) {
                    vec35 = vec35.add((vec36.x / i * j - vec35.x) * 0.1, 0.0, (vec36.z / i * j - vec35.z) * 0.1);
                }
                this.setDeltaMovement(vec35.multiply(0.99f, 0.98f, 0.99f));
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.horizontalCollision && !this.level().isClientSide && (o = (float)((n = j - (m = this.getDeltaMovement().horizontalDistance())) * 10.0 - 3.0)) > 0.0f) {
                    this.playSound(this.getFallDamageSound((int)o), 1.0f, 1.0f);
                    this.hurt(this.damageSources().flyIntoWall(), o);
                }
                if (this.onGround() && !this.level().isClientSide) {
                    this.setSharedFlag(7, false);
                }
            } else {
                BlockPos blockPos = this.getBlockPosBelowThatAffectsMyMovement();
                float p = this.level().getBlockState(blockPos).getBlock().getFriction();
                float f = this.onGround() ? p * 0.91f : 0.91f;
                Vec3 vec37 = this.handleRelativeFrictionAndCalculateMovement(vec3, p);
                double q = vec37.y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    q += (0.05 * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec37.y) * 0.2;
                } else if (!this.level().isClientSide || this.level().hasChunkAt(blockPos)) {
                    if (!this.isNoGravity()) {
                        q -= d;
                    }
                } else {
                    q = this.getY() > (double)this.level().getMinBuildHeight() ? -0.1 : 0.0;
                }
                if (this.shouldDiscardFriction()) {
                    this.setDeltaMovement(vec37.x, q, vec37.z);
                } else {
                    this.setDeltaMovement(vec37.x * (double)f, q * (double)0.98f, vec37.z * (double)f);
                }
            }
        }
        this.calculateEntityAnimation(this instanceof FlyingAnimal);
        ci.cancel();
    }
}
