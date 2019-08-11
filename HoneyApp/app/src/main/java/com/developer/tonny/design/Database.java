package com.developer.tonny.design;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 21/07/17.
 */

public class Database extends SQLiteOpenHelper {

    public static final String databaseName="Diabetes";

    String table1="medicine";
    String table1Column0="id";
    String table1Column1="name";
    String table1Column2="date";
    String table1Column3="hour";
    String table1Column4="amount";
    String table1Column5="user";

    String table2="dates";
    String table2Column0="id";
    String table2Column1="name";
    String table2Column2="date";
    String table2Column3="hour";
    String table2Column4="description";
    String table2Column5="user";

    String table3="userInfo";
    String table3Column0="id";
    String table3Column1="name";
    String table3Column2="date";
    String table3Column3="height";
    String table3Column4="weight";
    String table3Column5="phone";
    String table3Column6="age";
    String table3Column7="user";

    String table4="records";
    String table4Column0="id";
    String table4Column1="date";
    String table4Column2="glucose";
    String table4Column3="insulinAmount";
    String table4Column4="user";

    String table5="registry";
    String table5Column0="id";
    String table5Column1="user";

    public Database(Context context)
    {
        super(context, databaseName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       db.execSQL("CREATE TABLE "+table1+
                "("+table1Column0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ""+table1Column1+" TEXT, "+
                ""+table1Column2+" TEXT, "+
                ""+table1Column3+" TEXT, "+
                ""+table1Column4+" TEXT, "+
                ""+table1Column5+" TEXT) ");
        db.execSQL("CREATE TABLE "+table2+
                "("+table2Column0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ""+table2Column1+" TEXT, "+
                ""+table2Column2+" TEXT, "+
                ""+table2Column3+" TEXT, "+
                ""+table2Column4+" TEXT, "+
                ""+table2Column5+" TEXT) ");
        db.execSQL("CREATE TABLE "+table3+
                "("+table3Column0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ""+table3Column1+" TEXT, "+
                ""+table3Column2+" TEXT, "+
                ""+table3Column3+" TEXT, "+
                ""+table3Column4+" TEXT, "+
                ""+table3Column5+" TEXT, "+
                ""+table3Column6+" TEXT, "+
                ""+table3Column7+" TEXT)");
        db.execSQL("CREATE TABLE "+table4+
                "("+table4Column0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ""+table4Column1+" TEXT, "+
                ""+table4Column2+" TEXT, "+
                ""+table4Column3+" TEXT, "+
                ""+table4Column4+" TEXT) ");
        db.execSQL("CREATE TABLE "+table5+
                "("+table5Column0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ""+table5Column1+" TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       db.execSQL("DROP TABLE IF EXISTS "+table1);
        db.execSQL("DROP TABLE IF EXISTS "+table2);
        db.execSQL("DROP TABLE IF EXISTS "+table3);
        db.execSQL("DROP TABLE IF EXISTS "+table4);
        db.execSQL("DROP TABLE IF EXISTS "+table5);
    }

    public boolean setMedicineINSERT(String name, String date, String hour, String amount, String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table1Column1,name);
        cv.put(table1Column2,date);
        cv.put(table1Column3,hour);
        cv.put(table1Column4,amount);
        cv.put(table1Column5,user);

        long result = db.insert(table1,null,cv);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setDatesINSERT(String name, String date, String hour, String description, String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table2Column1,name);
        cv.put(table2Column2,date);
        cv.put(table2Column3,hour);
        cv.put(table2Column4,description);
        cv.put(table2Column5,user);

        long result = db.insert(table2,null,cv);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setUserinfoINSERT(String name, String date, String height, String weight, String phone,String age,String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table3Column1,name);
        cv.put(table3Column2,date);
        cv.put(table3Column3,height);
        cv.put(table3Column4,weight);
        cv.put(table3Column5,phone);
        cv.put(table3Column6,age);
        cv.put(table3Column7,user);

        long result = db.insert(table3,null,cv);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setRecordsINSERT(String date,String glucose, String insulinAmount,String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table4Column1,date);
        cv.put(table4Column2,glucose);
        cv.put(table4Column3,insulinAmount);
        cv.put(table4Column4,user);

        long result = db.insert(table4,null,cv);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setRegistryINSERT(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(table5Column1,user);

        long result = db.insert(table5,null,cv);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    public Cursor getMedicineSELECT(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT*FROM "+table1+" WHERE "+table1Column5+" = '"+user+"' ",null);

        return queryResult;
    }

    public Cursor getDatesSELECT(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT*FROM "+table2+" WHERE "+table2Column5+" = '"+user+"' ",null);

        return queryResult;
    }

    public Cursor getUserinfoSELECT(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT*FROM "+table3+" WHERE "+table3Column7+" = '"+user+"' ",null);

        return queryResult;
    }
    public Cursor getRegistrySELECT()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT*FROM "+table5,null);

        return queryResult;
    }

    public Cursor getRecordsSELECT(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryResult = db.rawQuery("SELECT*FROM "+table4+" WHERE "+table4Column4+" = '"+user+"' ",null);

        return queryResult;
    }


    public boolean setMedicineUPDATE(String id,String name, String date, String amount, String hour)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(table1Column1,name);
        cv.put(table1Column2,date);
        cv.put(table1Column3,hour);
        cv.put(table1Column4,amount);

        long result = db.update(table1,cv,"id = ?",new String [] {id});


        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setDatesUPDATE(String id,String name, String date, String description, String hour)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(table2Column1,name);
        cv.put(table2Column2,date);
        cv.put(table2Column3,hour);
        cv.put(table2Column4,description);

        long result = db.update(table2,cv,"id = ?",new String [] {id});


        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean setUserinfoUPDATE(String name, String date, String height, String weight,String phone,String age,String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(table3Column1,name);
        cv.put(table3Column2,date);
        cv.put(table3Column3,height);
        cv.put(table3Column4,weight);
        cv.put(table3Column5,phone);
        cv.put(table3Column6,age);

        long result = db.update(table3,cv,"user = ?",new String [] {user});


        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Integer setMedicineDELETE(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result = db.delete(table1, "id = ?", new String[]{id});
        return result;
    }

    public Integer setDatesDELETE(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result = db.delete(table2, "id = ?", new String[]{id});
        return result;
    }

    public Integer setRegistryDELETE()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result = db.delete(table5, "id >= ?", new String[]{"0"});
        return result;
    }
}
