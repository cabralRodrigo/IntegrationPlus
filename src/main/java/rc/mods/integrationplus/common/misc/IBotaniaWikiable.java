package rc.mods.integrationplus.common.misc;

/**
 * Define a block that can be provide their info to the botania's wiki system
 */
public interface IBotaniaWikiable {

    /**
     * Gets the display name of the block
     *
     * @return The display name of the block
     */
    String getWikiBlockName(int meta);

    /**
     * Gets the wiki url suffix to compose the full link to the block's wiki page
     *
     * @return The wiki url suffix of the block
     */
    String getBlockWikiUrlSuffix(int meta);
}
