package commands;

import client.game.Block;
import client.game.Blocks;

import java.util.List;

public class BlockArgument extends Argument<Block> {
    @Override
    public Block parse(List<String> s) {
        String in = s.removeFirst();
        Block b = Blocks.getBlockByName(in);
        if (b == null) throw new IllegalArgumentException("Invalid block name specified : " + in);
        return b;
    }

}
