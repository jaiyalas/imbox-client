import join.*;

import java.util.*;
import java.io.*;

import org.apache.commons.io.IOUtils;

public class Join {
  // Your program begins with a call to main().
  public static void main(String args[]){
      String dir = "/Users/jaiyalas/blockyard_backup/";
      List<Block> bs = new Vector<Block>();
      List<BlockRec> brs = new Vector<BlockRec>();
      try{

	  /** CLIENT **/
      bs.add(Block.readBlockFromHD
	     (dir,new BlockRec("3c3ddf981871d1ad7b33630255115ce0",2)));
      bs.add(Block.readBlockFromHD
	     (dir,new BlockRec("4e4b036c810b7d2a4d03eb1ab124d0e8",0)));
      bs.add(Block.readBlockFromHD
	     (dir,new BlockRec("47cd0be18d3456e83cc1b7d7823c8def",1)));
      FileHandler.writeFileFromBlocks
	  (bs,dir,"writeFileFromBlocks_testing.pdf");

	  /** SERVER **/
      brs.add(new BlockRec("4e4b036c810b7d2a4d03eb1ab124d0e8",0));
      brs.add(new BlockRec("3c3ddf981871d1ad7b33630255115ce0",2));
      brs.add(new BlockRec("47cd0be18d3456e83cc1b7d7823c8def",1));
      byte[] fileContent = FileHandler.joinBlocksFromHD(dir,brs);
      File file2 = new File(dir+"joinBlocksFromHD_testing.pdf"); 
      OutputStream os = new FileOutputStream(file2);
      IOUtils.write(fileContent, os);
      os.flush();os.close();
      }catch(IOException e){}
  }
}

