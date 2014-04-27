### Client

  * Filename -> List<Block>
  * Filename -> List<BlockRec>
  
  * server.getSnapshot() 
  
### Server

  * Block to HD(?)
  * writeBlock()
  
  
  +"/"
  +Workspace.FSEP
  
  
  
  
---

1. C向S提出同步請求
2. S回應[(fname,fmd5)] ↦ Sfs
3. C取得本地最新 Cfs 
4. 比較 Cfs 與 Sfs 之差異並產生 [fname] ↦ fs
5. C 拿著 fs 去跟 S 要 [(filename,[BlockRec])] ↦ Sbss
6. C取得本地最新 Cbss
7. 比較 Sbss 和 Cbss 並產出 [(BlockRec)] ↦ bs
8. 產生 *更新清單*