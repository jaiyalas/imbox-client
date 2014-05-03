package org.imbox.infrastructure.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.*;

import org.apache.commons.io.IOUtils;

import org.imbox.infrastructure.file.*;
import org.imbox.infrastructure.*;

public class FileHandler{
    

    /**
     * Load blocks for sindle file from given FileChannel.
     *
     * @param FileChannel  an FileChannel which is obtained from target file.
     * @return List<Block> an linear container which consists of Blocks. 
     */
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
        };
	return bs;
    };

    /**
     * Generate a byte[] which consists of whole content of some file which 
     * is indicated by geven list of block records. 
     * 
     * @param _sysDir  an directory where blocks are restored.
     * @return _brs    an list of block records. 
     */
    public static byte[] joinBlocksFromHD(String _sysDir,
					  List<BlockRec> _brs)throws IOException{

	// sorting list of block record by position-(sequence-)number.
	Collections.sort(_brs,
			 (BlockRec  b0, BlockRec b1) -> {
			     return b0.getPos() - b1.getPos();});

	// a list of Byte that restores byte data which loaded from blocks.
	List<Byte> byteList = new Vector<Byte>();
	_brs.forEach(br -> { try{
		    byte[] bytes = Block.readBlockFromHD(_sysDir,br.getName());
		    for(byte b : bytes){byteList.add(new Byte(b));}
		}catch(IOException e){
		    throw new RuntimeException(e);
		}
	    });

	// translate List<Byte> to byte[]
	byte[] res = new byte[byteList.size()];
	for(int i = 0;i<byteList.size();i++){res[i] = byteList.get(i).byteValue();}
	return res;
    };

    public static void writeFileFromBlocks(List<Block> _bs, 
					   String _targetDir,
					   String _filename)throws IOException{
	File file_ = new File(_targetDir+_filename); 
	if(file_.exists()){
	    file_.delete();
	}

	Collections.sort(_bs,(Block  b0, Block b1) -> {
		return b0.getPos() - b1.getPos();});
	int fileSize = _bs.stream().mapToInt(Block::getSize).sum();
	byte[] fileContent = new byte[fileSize];
	int k = 0;
	for(int i = 0 ; i < _bs.size() ; i++){
	    byte[] temp_byarr = _bs.get(i).getContent(); // one byte[]
	    for(int j = 0 ; j < temp_byarr.length ; j++){
		fileContent[k] = temp_byarr[j];
		k += 1;
	    };
	};
	OutputStream os = new FileOutputStream(new File(_targetDir+_filename));
	IOUtils.write(fileContent, os);
	os.flush();os.close();
    };
}
