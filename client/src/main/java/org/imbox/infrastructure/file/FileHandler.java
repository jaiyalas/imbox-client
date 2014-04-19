package org.imbox.infrastructure.file;

import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.Vector;
import java.util.List;

import org.imbox.infrastructure.file.Block;
import org.imbox.infrastructure.Const;
import org.imbox.infrastructure.Hash;

public class FileHandler{
    
    /* as using int as index, the file size must less than or equals to 2GB */
    /* as a matter of fact, the max length of array in Java is */
    public static List<Block> genBlocksFromChannel(FileChannel channel, 
						   String filename) throws IOException{
	List<Block> bs = new Vector<Block>();
	ByteBuffer bb  = ByteBuffer.allocate(Const.blocksize);
	int bIdx = 0;
	int bNum = (int) Math.ceil(channel.size() / (double) Const.blocksize);

        while(channel.read(bb) > 0){
            bb.flip(); // reset index of ByteBuffer
            bIdx += 1;       // succ block index
	    byte[] bytes = new byte[bb.limit()];
	    for (int i = 0; i < bb.limit(); i++){bytes[i] = bb.get();}
	    //save one block from byte[]
	    Block block = Block.genBlock(bytes,bNum,bIdx,filename);
	    bs.add(block);
	    bb.clear(); 
            
        }
	
	return bs;
    };
}
