package org.imbox.infrastructure.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.*;

import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.*;

public class FileHandler{
    
    public static List<Block> genBlocksFromChannel(FileChannel channel)throws IOException{
	List<Block> bs = new Vector<Block>();
	ByteBuffer bb  = ByteBuffer.allocate(Const.blocksize);
	int bIdx = 0;
	int bNum = (int) Math.ceil(channel.size() / (double) Const.blocksize);

        while(channel.read(bb) > 0){
            bb.flip(); // reset index of ByteBuffer
	    byte[] bytes = new byte[bb.limit()];
	    for (int i = 0; i < bb.limit(); i++){bytes[i] = bb.get();}
	    //save one block from byte[]
	    Block block = Block.genBlock(bytes,bIdx);
	    bs.add(block);
	    bb.clear(); 
            bIdx += 1;       // succ block index
            
        }
	
	return bs;
    };
}
