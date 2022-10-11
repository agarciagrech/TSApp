/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import java.sql.Connection;

/**
 *
 * @author agarc
 */
public class SQLiteSignalManager {
    private Connection c;
    
     public SQLiteSignalManager(Connection c){
        this.c = c;
    }
      public SQLiteSignalManager() {
        super();
    }

   
}
