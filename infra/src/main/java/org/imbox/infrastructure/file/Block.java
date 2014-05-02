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
    public int    getSize()    {return this.content.length;}

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

    public static byte[] readBlockFromHD(String _sysDir,
					 String _name)throws IOException{
	InputStream is    = new FileInputStream(new File(_sysDir+_name));
	byte[]      bytes = IOUtils.toByteArray(is);
	is.close();
	return bytes;
    };
    public static Block readBlockFromHD(String   _sysDir, 
					BlockRec _br)throws IOException{
	InputStream in = new FileInputStream(new File(_sysDir+_br.getName()));
	byte[]   bytes = IOUtils.toByteArray(in);
	in.close();
	return genBlock(_br.getName(),bytes,_br.getPos());
    };
    public static Block readBlockFromHD(String _sysDir, 
					String _name, 
					int    _pos)throws IOException{
	return Block.readBlockFromHD(_sysDir,new BlockRec(_name,_pos));
    };

    /** ------------------------- **/

    public static void writeBlock(String _sysDir, Block _b) throws IOException{
	OutputStream os = new FileOutputStream(new File(_sysDir+_b.getName()));
	IOUtils.write(_b.getContent(), os);
	os.flush();os.close();
    };
    public static void writeBlock(String _sysDir, byte[] _bs) throws IOException{
	OutputStream os = new FileOutputStream
	    (new File(_sysDir+Hash.getMD5String(_bs)));
	IOUtils.write(_bs, os);
	os.flush();os.close();
    };
}
