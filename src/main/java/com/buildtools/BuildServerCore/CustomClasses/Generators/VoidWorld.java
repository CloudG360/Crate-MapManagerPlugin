package com.buildtools.BuildServerCore.CustomClasses.Generators;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VoidWorld extends ChunkGenerator {

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);



        return chunk;

    }
}
