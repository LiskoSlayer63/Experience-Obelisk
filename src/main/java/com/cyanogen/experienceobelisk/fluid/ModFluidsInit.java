package com.cyanogen.experienceobelisk.fluid;

import java.util.function.Consumer;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import com.cyanogen.experienceobelisk.block.ModBlocksInit;
import com.cyanogen.experienceobelisk.item.ModItemsInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidsInit {
    public static final ResourceLocation flowingTexture = new ResourceLocation("experienceobelisk:custom_models/green");
    public static final ResourceLocation stillTexture = new ResourceLocation("experienceobelisk:custom_models/green");
    public static final ResourceLocation overlay = new ResourceLocation("minecraft:block/water_overlay");
    
    public static final DeferredRegister<Fluid> FLUIDS = 
    		DeferredRegister.create(ForgeRegistries.FLUIDS, ExperienceObelisk.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = 
			DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ExperienceObelisk.MOD_ID);

	public static final RegistryObject<FluidType> FLUID_TYPE = FLUID_TYPES.register("raw_experience", () -> 
	typeWithTextures(FluidType.Properties.create()
			.density(1000)
    		.lightLevel(10)
    		.viscosity(1000)
    		.temperature(300)
    		.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
    		.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY), 
    stillTexture, flowingTexture, overlay));

    //registering fluid
    public static final RegistryObject<Source> RAW_EXPERIENCE
            = FLUIDS.register("raw_experience", () -> new ForgeFlowingFluid.Source(ModFluidsInit.RAW_EXPERIENCE_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> RAW_EXPERIENCE_FLOWING
            = FLUIDS.register("raw_experience_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluidsInit.RAW_EXPERIENCE_PROPERTIES));

    //setting properties for raw experience fluid
    public static final ForgeFlowingFluid.Properties RAW_EXPERIENCE_PROPERTIES = new ForgeFlowingFluid.Properties(
    			FLUID_TYPE,
            	RAW_EXPERIENCE,
            	RAW_EXPERIENCE_FLOWING)
            .bucket(ModItemsInit.RAW_EXPERIENCE_BUCKET)
            .block(ModBlocksInit.RAW_EXPERIENCE);

    public static void register(IEventBus eventBus){
		FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
    
    private static FluidType typeWithTextures(
    		FluidType.Properties builder, 
    		ResourceLocation stillTexture, 
    		ResourceLocation flowTexture, 
    		ResourceLocation overlayTexture) {
    	return new FluidType(builder) {
    		@Override
			public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
			{
				consumer.accept(new IClientFluidTypeExtensions()
				{
					@Override
					public ResourceLocation getStillTexture()
					{
						return stillTexture;
					}

					@Override
					public ResourceLocation getFlowingTexture()
					{
						return flowTexture;
					}

					@Override
					public ResourceLocation getOverlayTexture()
					{
						return overlayTexture;
					}
				});
			}
    	};
    }
}
