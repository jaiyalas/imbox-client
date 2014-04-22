data FileEvent = F_Delete
               | F_Create
               | F_Modify
               | D_Delete
               | D_Create
               | D_Modify






data Log = (FileEvent, Time, 
