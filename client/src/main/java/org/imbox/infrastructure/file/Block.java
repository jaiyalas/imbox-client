package org.imbox.infrastructure.file;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils; 
import org.apache.commons.io.IOUtils;

import org.imbox.infrastructure.*;
import org.imbox.infrastructure.file.*;

public class Block{

    private byte[] content;
    private String name; // via md5
    private int    position;

    public byte[] getContent() {return this.content;}
    public String getName()    {return this.name;}
    public int    getPos()     {return this.position;}

    private Block(){};
    private Block(byte[] _c){this.content = _c;};

    public static Block genBlock(byte[] _content,
				 int _pos){
	Block b      = new Block(_content);
	b.position   = _pos;
	b.name       = Hash.getMD5String(_content);
	return b;
    };
    public static Block genBlock(String _name, 
				 byte[] _content,
				 int _pos){
	Block b      = new Block(_content);
	b.position   = _pos;
	b.name       = _name;
	return b;
    };    

    /** ------------------------- **/

    public static byte[] readBlockFromHD(String _name)throws IOException{
	InputStream is    = new FileInputStream(new File(_name));
	byte[]      bytes = IOUtils.toByteArray(is);
	is.close();
	return bytes;
    };
    public static Block readBlockFromHD(BlockRec br)throws IOException{
	InputStream in = new FileInputStream(new File(br.getName()));
	byte[]   bytes = IOUtils.toByteArray(in);
	in.close();
	return genBlock(br.getName(),bytes,br.getPos());
    };
    public static Block readBlockFromHD(String _name, 
					int _pos)throws IOException{
	return Block.readBlockFromHD(new BlockRec(_name,_pos));
    };

    /** ------------------------- **/

    public static byte[] joinBlocks(List<BlockRec> brs)throws IOException{
	Collections.sort(brs,(BlockRec  b0, BlockRec b1) -> {
		return b0.getPos() - b1.getPos();});
	List<Byte> byteList = new Vector<Byte>();
	brs.forEach(br -> {
		try{
		    byte[] bytes = readBlockFromHD(br.getName());
		    for(byte b : bytes){byteList.add(new Byte(b));}
		}catch(IOException e){}
	    });
	byte[] res = new byte[byteList.size()];
	for(int i = 0;i<byteList.size();i++){
	    res[i] = byteList.get(i).byteValue();
	}
	return res;
    };

    /** ------------------------- **/

    public static void writeBlock(Block b,String path) throws IOException{
	OutputStream os = new FileOutputStream(new File(path+b.getName()));
	IOUtils.write(b.getContent(), os);
	os.flush();os.close();
    };
    public static void writeBlock(byte[] bs, String path) throws IOException{
	OutputStream os = new FileOutputStream
	    (new File(path+Hash.getMD5String(bs)));
	IOUtils.write(bs, os);
	os.flush();os.close();
    };
}
