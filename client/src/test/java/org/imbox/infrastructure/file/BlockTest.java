package org.imbox.infrastructure.file;

import org.junit.Assert;
import org.junit.Test;

public class BlockTest{
    @Test
    public void test(){
	byte[] bs = new byte[1];
	bs[0] = (byte)'a';
	Block b1 = Block.genBlock(bs);
	Assert.assertEquals(b1.hasPrefix(),false);
    };
}
