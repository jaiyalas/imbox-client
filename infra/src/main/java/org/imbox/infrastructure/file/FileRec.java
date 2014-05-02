package org.imbox.infrastructure.file;

// BlockRec is a block withour content
public class FileRec{
    private String name;
    private String md5;

    public String getName() {return this.name;}
    public String getMd5()  {return this.md5;}

    private FileRec(){};
    public FileRec(String _n,String _p){
	this.name = _n;
	this.md5 = _p;
    };
};
