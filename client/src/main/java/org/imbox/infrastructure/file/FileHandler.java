package org.imbox.infrastructure;

import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.Vector;

import org.imbox.infrastructure.Block;
import org.imbox.infrastructure.Const;

public class FileHandler{
    
    
    /* as using int as index, the file size must less than or equals to 2GB */
    /* as a matter of fact, the max length of array in Java is */
    public static List<Block> genBlocksFromChannel(FileChannel channel){
	List<Block> bs = new Vector();
	
	ByteBuffer bb  = bb.allocate(Const.blocksize);
	int bIdx = 0;
	int bNum = (int) Math.ceil(channel.size() / (double) Const.blocksize);
    };
}
