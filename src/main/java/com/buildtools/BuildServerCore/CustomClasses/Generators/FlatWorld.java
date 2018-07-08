package com.buildtools.BuildServerCore.CustomClasses.Generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class FlatWorld extends ChunkGenerator {

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        for(int xpos=0; xpos<16; xpos++){
            for (int zpos=0; zpos<16; zpos++){
                biome.setBiome(xpos, zpos, Biome.PLAINS);
                chunk.setBlock(xpos, 0, zpos, Material.BEDROCK);
                chunk.setBlock(xpos, 1, zpos, Material.DIRT);
                chunk.setBlock(xpos, 2, zpos, Material.DIRT);
                chunk.setBlock(xpos, 3, zpos, Material.DIRT);
                chunk.setBlock(xpos, 4, zpos, Material.GRASS);
            }
        }

        return chunk;

    }
}
