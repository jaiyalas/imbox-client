package org.imbox.infrastructure.file;

// BlockRec is a block withour content
public class BlockRec{
    private String name; // via md5
    private int    position;

    public String getName()    {return this.name;}
    public int    getPos()     {return this.position;}

    private BlockRec(){};
    public BlockRec(String _n,int _p){
	this.name = _n;
	this.position = _p;
    };
};
