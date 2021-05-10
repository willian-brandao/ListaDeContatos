package com.everis.listadecontatos.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
    context: Context?

) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object{
        private val NOME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val COLLUMNS_ID = "id"
    val COLLUMNS_NAME = "name"
    val COLLUMNS_PHONE = "phone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "$COLLUMNS_ID INTEGER  NOT NULL"+
            "$COLLUMNS_NAME TEXT NOT NULL"+
            "$COLLUMNS_PHONE TEXT NOT NULL"+
            ""+
            "PRIMARY KEY($COLLUMNS_ID AUTOINCREMENT)"+
            ")"

    //create the instance of database, this function is use when the database is create on first time
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    //upgrade database. every time when this application is starts, call tha function to verify the atual version
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, nerVersion: Int) {
        if( oldVersion != nerVersion){
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }
    //function to find elements of list
    fun buscarContatos(buscar: String) : List<ContatosVO> {
        val db : SQLiteDatabase = readableDatabase ?: return mutableListOf()
        var lista : MutableList<ContatosVO> = mutableListOf<ContatosVO>()
        val sql = "SELECT * FROM $TABLE_NAME "
        var cursor = db.rawQuery(sql, arrayOf()) ?: return mutableListOf()
        while( cursor.moveToNext()){
            var contato = ContatosVO(
                    cursor.getInt( cursor.getColumnIndex(COLLUMNS_ID)),
                    cursor.getString( cursor.getColumnIndex(COLLUMNS_NAME)),
                    cursor.getString( cursor.getColumnIndex(COLLUMNS_PHONE))
            )
            lista.add(contato)
        }
        return lista
    }
}