 ///*
        try {
            SQLiteDatabase myDB = this.openOrCreateDatabase("DATABASE", MODE_PRIVATE, null);

   /* Create a Table in the Database. */
            String TableName = "NUMETABEL";
            myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TableName
                    + " (Field1 VARCHAR, Field2 INT(3));");

   /* Insert data to a Table*/
            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES ('Saranga', 22);");

            myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES ('Casanga', 21);");

   /*retrieve data from database */
            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE Field2 = ?", new String[]{"21"});

            int Column1 = c.getColumnIndex("Field1");
            int Column2 = c.getColumnIndex("Field2");

            // Check if our result was valid.
            c.moveToFirst();
            String Data = null;

            if (c != null) {
                // Loop through all Results
                do {
                    String Name = c.getString(Column1);
                    int Age = c.getInt(Column2);
                    Data = Name + "/" + Age + "\n";
                    Result = Data;
                } while (c.moveToNext());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    //*/