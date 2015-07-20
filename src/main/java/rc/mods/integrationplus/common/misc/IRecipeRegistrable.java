package rc.mods.integrationplus.common.misc;

/**
 * Define a block or an item that can register their own recipes
 */
public interface IRecipeRegistrable {

    /**
     * Register the recipes for the block or item
     */
    void registerRecipes();
}
